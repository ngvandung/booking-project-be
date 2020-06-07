/**
 * 
 */
package com.booking.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.booking.business.util.FileEntryBusinessFactoryUtil;
import com.booking.model.FileEntry;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class FileEntryController {

	private static final Logger log = Logger.getLogger(FileEntryController.class);

	@RequestMapping(value = "/fileentry/{fileEntryId}", method = RequestMethod.GET)
	@ResponseBody
	public FileEntry findById(HttpServletRequest request, HttpSession session,
			@PathVariable("fileEntryId") Long fileEntryId) {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return FileEntryBusinessFactoryUtil.findById(fileEntryId, userContext);
	}

	@RequestMapping(value = "/image/{className}/{classPK}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<String>> getImages(HttpServletRequest request, HttpSession session,
			@PathVariable("className") String className, @PathVariable("classPK") long classPK) {

		Map<String, List<String>> result = new HashMap<String, List<String>>();
		List<String> fileEntryNames = FileEntryBusinessFactoryUtil.findByClassName_ClassPK(className, classPK);
		result.put("data", fileEntryNames);
		return result;
	}

	@RequestMapping(value = "/fileentry", method = RequestMethod.POST)
	@ResponseBody
	public List<FileEntry> createFileEntry(HttpServletRequest request, HttpSession session,
			@RequestParam("files") MultipartFile[] files, @RequestParam("className") String className,
			@RequestParam("classPK") long classPK) throws IOException {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");
		log.info("-------> Lenght: " + files.length);

		return FileEntryBusinessFactoryUtil.createFileEntry(files, className, classPK, userContext);
	}

	@RequestMapping(value = "/updatefileentry", method = RequestMethod.POST)
	@ResponseBody
	public List<FileEntry> updateFileEntry(HttpServletRequest request, HttpSession session,
			@RequestParam("files") MultipartFile[] files, @RequestParam("className") String className,
			@RequestParam("classPK") long classPK) throws IOException {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");
		log.info("-------> Lenght: " + files.length);

		return FileEntryBusinessFactoryUtil.updateFileEntry(files, className, classPK, userContext);
	}
}
