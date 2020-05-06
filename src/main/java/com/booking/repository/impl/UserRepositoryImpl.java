/**
 * 
 */
package com.booking.repository.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import com.booking.constant.UserConstant;
import com.booking.model.User;
import com.booking.repository.UserRepository;
import com.booking.repository.elasticsearch.UserElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

	private static final Logger log = Logger.getLogger(UserRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserElasticsearchRepository userElasticsearchRepository;

	@Override
	public Iterable<User> getUsers(String username, String email, String phone, String firstName, String lastName,
			Integer age, Integer isHost, Integer isEnabled, Integer start, Integer end) {
		if (start == null || end == null) {
			start = 0;
			end = 15;
		}
		Pageable sortedByUserId = PageRequest.of(start, end, Sort.by("userId"));

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchAllQuery());
		if (username != null) {
			QueryBuilder queryBuilder = QueryBuilders.matchQuery(UserConstant.USERNAME, username)
					.operator(Operator.AND);
			boolQueryBuilder.must(queryBuilder);
		}
		if (email != null) {
			QueryBuilder queryBuilder = QueryBuilders.matchQuery(UserConstant.EMAIL, email).operator(Operator.AND);
			boolQueryBuilder.must(queryBuilder);
		}
		if (phone != null) {
			QueryBuilder queryBuilder = QueryBuilders.matchQuery(UserConstant.PHONE, phone).operator(Operator.AND);
			boolQueryBuilder.must(queryBuilder);
		}
		if (firstName != null) {
			QueryBuilder queryBuilder = QueryBuilders.matchQuery(UserConstant.FIRSTNAME, firstName)
					.operator(Operator.AND);
			boolQueryBuilder.must(queryBuilder);
		}
		if (lastName != null) {
			QueryBuilder queryBuilder = QueryBuilders.matchQuery(UserConstant.LASTNAME, lastName)
					.operator(Operator.AND);
			boolQueryBuilder.must(queryBuilder);
		}
		if (age != null) {
			QueryBuilder queryBuilder = QueryBuilders.termQuery(UserConstant.AGE, age);
			boolQueryBuilder.must(queryBuilder);
		}
		if (isHost != null) {
			QueryBuilder queryBuilder = QueryBuilders.termQuery(UserConstant.ISHOST, isHost);
			boolQueryBuilder.must(queryBuilder);
		}
		if (isEnabled != null) {
			QueryBuilder queryBuilder = QueryBuilders.termQuery(UserConstant.ISENABLED, isEnabled);
			boolQueryBuilder.must(queryBuilder);
		}

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
				.withPageable(sortedByUserId).build();

		return userElasticsearchRepository.search(searchQuery);
	}

	@Override
	public User deleteUser(long userId) {
		try {
			User user = sessionFactory.getCurrentSession().get(User.class, userId);
			sessionFactory.getCurrentSession().delete(user);
			return user;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public User findByUserId(long userId) {
		User user = null;
		try {
			Optional<User> optionalUser = userElasticsearchRepository.findById(userId);
			if (optionalUser.isPresent()) {
				user = optionalUser.get();
			} else {
				user = sessionFactory.getCurrentSession().get(User.class, userId);
			}
			return user;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public User updateUser(User user) {
		try {
			sessionFactory.getCurrentSession().update(user);
			return user;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public User createUser(User user) {
		try {
			sessionFactory.getCurrentSession().save(user);
			return user;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public User findByUserName(String username) {
		Transaction transaction = null;
		User user = null;
		try {
			Session session = sessionFactory.getSessionFactory().openSession();
			if (session != null) {
				// start a transaction
				transaction = session.beginTransaction();

				// get an student object
				String hql = " FROM User S WHERE S.username = :username";
				Query query = session.createQuery(hql);
				query.setParameter("username", username);
				List<User> users = query.getResultList();

				if (users != null && !users.isEmpty()) {
					user = users.get(0);
				}

				// commit transaction
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error(e);
		}
		return user;
	}

}
