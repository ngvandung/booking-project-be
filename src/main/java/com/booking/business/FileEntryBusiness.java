/**
 * 
 */
package com.booking.business;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.booking.model.FileEntry;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public interface FileEntryBusiness {
	public FileEntry findById(long fileEntryId, UserContext userContext);

	public List<FileEntry> createFileEntry(MultipartFile[] files, String className, long classPK, UserContext userContext)
			throws IOException;

	public List<FileEntry> updateFileEntry(MultipartFile[] files, String className, long classPK,
			UserContext userContext) throws IOException;

	public String getPathImage(long fileEntryId);

	public List<String> findByClassName_ClassPK(String className, long classPK);
}
