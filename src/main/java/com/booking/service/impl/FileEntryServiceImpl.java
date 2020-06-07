/**
 * 
 */
package com.booking.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.model.FileEntry;
import com.booking.repository.FileEntryRepository;
import com.booking.service.CounterService;
import com.booking.service.FileEntryService;

/**
 * @author ddung
 *
 */
@Service
public class FileEntryServiceImpl implements FileEntryService {

	@Autowired
	private FileEntryRepository fileEntryRepository;
	@Autowired
	private CounterService counterService;

	@Override
	public FileEntry findById(long fileEntryId) {
		return fileEntryRepository.findById(fileEntryId);
	}

	@Override
	public FileEntry createFileEntry(String fileEntryName, String absolutePath, String extension, long size,
			String className, long classPK, long userId) {
		FileEntry fileEntry = new FileEntry();

		long fileEntryId = counterService.increment(FileEntry.class.getName());

		Date now = new Date();
		fileEntry.setFileEntryId(fileEntryId);
		fileEntry.setFileEntryName(fileEntryName);
		fileEntry.setAbsolutePath(absolutePath);
		fileEntry.setExtension(extension);
		fileEntry.setSize(size);
		fileEntry.setClassName(className);
		fileEntry.setClassPK(classPK);
		fileEntry.setUserId(userId);
		fileEntry.setModifiedDate(now);
		fileEntry.setCreateDate(now);

		fileEntry = fileEntryRepository.createFileEntry(fileEntry);

		return fileEntry;
	}

	@Override
	public List<FileEntry> findByClassName_ClassPK(String className, long classPK) {
		return fileEntryRepository.findByClassName_ClassPK(className, classPK);
	}

	@Override
	public boolean deleteFileEntry(String className, long classPK) {
		return fileEntryRepository.deleteFileEntry(className, classPK);
	}

}
