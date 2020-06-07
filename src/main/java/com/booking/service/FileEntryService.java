/**
 * 
 */
package com.booking.service;

import java.util.List;

import com.booking.model.FileEntry;

/**
 * @author ddung
 *
 */
public interface FileEntryService {
	public FileEntry findById(long fileEntryId);

	public FileEntry createFileEntry(String fileEntryName, String absolutePath, String extension, long size,
			String className, long classPK, long userId);

	public List<FileEntry> findByClassName_ClassPK(String className, long classPK);
	
	public boolean deleteFileEntry(String className, long classPK);
}
