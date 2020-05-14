/**
 * 
 */
package com.booking.repository.impl;

import java.util.List;
import java.util.Optional;

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
import org.springframework.transaction.annotation.Transactional;

import com.booking.constant.RoleConstant;
import com.booking.constant.UserConstant;
import com.booking.model.User;
import com.booking.model.UserRole;
import com.booking.repository.UserRepository;
import com.booking.repository.UserRoleRepository;
import com.booking.repository.elasticsearch.UserElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class UserRepositoryImpl implements UserRepository {

	private static final Logger log = Logger.getLogger(UserRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserElasticsearchRepository userElasticsearchRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

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
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		User user = session.get(User.class, userId);
		session.delete(user);
		transaction.commit();
		session.close();
		return user;
	}

	@Override
	public User findByUserId(long userId) {
		User user = null;

		Optional<User> optionalUser = userElasticsearchRepository.findById(userId);
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
		} else {
			Transaction transaction = null;
			Session session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			user = session.get(User.class, userId);
			transaction.commit();
			session.close();
		}
		return user;
	}

	@Override
	public User updateUser(User user) {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.update(user);
		transaction.commit();
		session.close();
		return user;
	}

	@Override
	public User createUser(User user) {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.save(user);

		// Default new user is role USER
		UserRole userRole = new UserRole();
		userRole.setRoleId(RoleConstant.USER);
		userRole.setUserId(user.getUserId());
		userRoleRepository.createUserRole(userRole);

		transaction.commit();
		session.close();
		return user;
	}

	@Override
	public User findByUserName(String username) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		User user = null;
		try {
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
		session.close();
		return user;
	}

}
