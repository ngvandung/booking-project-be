/**
 * 
 */
package com.booking.business.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.booking.business.FileEntryBusiness;
import com.booking.business.util.HouseBusinessFactoryUtil;
import com.booking.model.FileEntry;
import com.booking.model.House;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.FileEntryService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class FileEntryBusinessImpl implements FileEntryBusiness {

	private String realPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
	private String pathFolder = realPath.substring(realPath.lastIndexOf("C:"), realPath.lastIndexOf("/booking"))
			+ "/publics/booking/";
	private static final Logger log = Logger.getLogger(FileEntryBusinessImpl.class);

	@Autowired
	private FileEntryService fileEntryService;

	@Override
	public FileEntry findById(long fileEntryId, UserContext userContext) {
		return fileEntryService.findById(fileEntryId);
	}

	@Override
	public List<FileEntry> createFileEntry(MultipartFile[] files, String className, long classPK,
			UserContext userContext) throws IOException {
		List<FileEntry> fileEntries = new ArrayList<FileEntry>();
		PermissionCheckerFactoryUtil.checkHost(userContext);
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String name = String.valueOf((new Date()).getTime()) + extension;

			byte[] bytes = file.getBytes();

			// Creating the directory to store file
			String rootPath = pathFolder;
			File dir = new File(rootPath + File.separator + "images");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			// Create the file on server
			File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();

			log.info("Server File Location=" + serverFile.getAbsolutePath());

			FileEntry fileEntry = fileEntryService.createFileEntry(name, serverFile.getAbsolutePath(),
					name.substring(name.lastIndexOf(".") + 1), serverFile.length(), className, classPK,
					userContext.getUser().getUserId());

			if (fileEntry != null) {
				fileEntries.add(fileEntry);
			}
		}
		return fileEntries;

	}

	@Override
	public String getPathImage(long fileEntryId) {
		FileEntry fileEntry = fileEntryService.findById(fileEntryId);
		return fileEntry.getAbsolutePath();
	}

	@Override
	public List<String> findByClassName_ClassPK(String className, long classPK) {
		List<FileEntry> fileEntries = fileEntryService.findByClassName_ClassPK(className, classPK);
		List<String> result = new ArrayList<String>();
		for (FileEntry fileEntry : fileEntries) {
			result.add(fileEntry.getFileEntryName());
		}
		return result;
	}

	@Override
	public List<FileEntry> updateFileEntry(MultipartFile[] files, String className, long classPK,
			UserContext userContext) throws IOException {
		House house = HouseBusinessFactoryUtil.findById(classPK);
		List<FileEntry> fileEntries = new ArrayList<FileEntry>();
		if (PermissionCheckerFactoryUtil.isOwner(userContext, house.getUserId())) {
			if (fileEntryService.deleteFileEntry(className, classPK)) {
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					String extension = file.getOriginalFilename()
							.substring(file.getOriginalFilename().lastIndexOf("."));
					String name = String.valueOf((new Date()).getTime()) + extension;

					byte[] bytes = file.getBytes();

					// Creating the directory to store file
					String rootPath = pathFolder;
					File dir = new File(rootPath + File.separator + "images");
					if (!dir.exists()) {
						dir.mkdirs();
					}

					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					log.info("Server File Location=" + serverFile.getAbsolutePath());

					FileEntry fileEntry = fileEntryService.createFileEntry(name, serverFile.getAbsolutePath(),
							name.substring(name.lastIndexOf(".") + 1), serverFile.length(), className, classPK,
							userContext.getUser().getUserId());

					if (fileEntry != null) {
						fileEntries.add(fileEntry);
					}
				}
			}
		}
		return fileEntries;
	}

}
