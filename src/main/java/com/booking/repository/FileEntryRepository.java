/**
 * 
 */
package com.booking.repository;

import java.util.List;

import com.booking.model.FileEntry;

/**
 * @author ddung
 *
 */
public interface FileEntryRepository {
	public FileEntry findById(long fileEntryId);

	public FileEntry createFileEntry(FileEntry fileEntry);
	
	public List<FileEntry> findByClassName_ClassPK(String className, long classPK);
	
	public boolean deleteFileEntry(String className, long classPK);
}
