/**
 * 
 */
package com.booking.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.FileEntry;
import com.booking.repository.FileEntryRepository;

/**
 * @author ddung
 *
 */
@Repository
public class FileEntryRepositoryImpl implements FileEntryRepository {

	private static final Logger log = Logger.getLogger(FileEntryRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public FileEntry findById(long fileEntryId) {
		FileEntry fileEntry = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		fileEntry = session.get(FileEntry.class, fileEntryId);
		transaction.commit();
		session.close();
		return fileEntry;
	}

	@Override
	public FileEntry createFileEntry(FileEntry fileEntry) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(fileEntry);
		transaction.commit();
		session.close();
		return fileEntry;
	}

	@Override
	public List<FileEntry> findByClassName_ClassPK(String className, long classPK) {
		List<FileEntry> fileEntries = new ArrayList<FileEntry>();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			if (session != null) {
				// start a transaction
				transaction = session.beginTransaction();

				// get an student object
				String hql = " FROM FileEntry S WHERE S.className = :className AND S.classPK = :classPK ";
				Query query = session.createQuery(hql);
				query.setParameter("className", className);
				query.setParameter("classPK", classPK);
				fileEntries = (List<FileEntry>) query.getResultList();

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
		return fileEntries;
	}

	@Override
	public boolean deleteFileEntry(String className, long classPK) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		List<FileEntry> fileEntries = findByClassName_ClassPK(className, classPK);
		for (FileEntry fileEntry : fileEntries) {
			session.delete(fileEntry);
		}
		transaction.commit();
		session.close();
		return true;
	}

}
