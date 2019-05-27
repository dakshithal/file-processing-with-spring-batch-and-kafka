package com.dbs.exercise.controller;

import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dbs.exercise.service.FileProcessorService;

@RestController
public class FileProcessingController {

	@Autowired
	private FileProcessorService FileProcessorService;

	/**
	 * Triggers Cash Transaction file processing (This is the external trigger point)
	 * @return The status of the triggered file process
	 */
	@RequestMapping(method=RequestMethod.GET, value ="/file")
	@ResponseStatus(HttpStatus.OK)
	public BatchStatus processFile() {
		System.out.println("Request received to process input transactions file");
		return FileProcessorService.processInputTransactionsFile();
	}
}
