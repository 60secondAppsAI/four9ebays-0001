package com.four9ebays.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.four9ebays.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.four9ebays.domain.ReturnRequest;
import com.four9ebays.dto.ReturnRequestDTO;
import com.four9ebays.dto.ReturnRequestSearchDTO;
import com.four9ebays.dto.ReturnRequestPageDTO;
import com.four9ebays.service.ReturnRequestService;
import com.four9ebays.dto.common.RequestDTO;
import com.four9ebays.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/returnRequest")
@RestController
public class ReturnRequestController {

	private final static Logger logger = LoggerFactory.getLogger(ReturnRequestController.class);

	@Autowired
	ReturnRequestService returnRequestService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ReturnRequest> getAll() {

		List<ReturnRequest> returnRequests = returnRequestService.findAll();
		
		return returnRequests;	
	}

	@GetMapping(value = "/{returnRequestId}")
	@ResponseBody
	public ReturnRequestDTO getReturnRequest(@PathVariable Integer returnRequestId) {
		
		return (returnRequestService.getReturnRequestDTOById(returnRequestId));
	}

 	@RequestMapping(value = "/addReturnRequest", method = RequestMethod.POST)
	public ResponseEntity<?> addReturnRequest(@RequestBody ReturnRequestDTO returnRequestDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = returnRequestService.addReturnRequest(returnRequestDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/returnRequests")
	public ResponseEntity<ReturnRequestPageDTO> getReturnRequests(ReturnRequestSearchDTO returnRequestSearchDTO) {
 
		return returnRequestService.getReturnRequests(returnRequestSearchDTO);
	}	

	@RequestMapping(value = "/updateReturnRequest", method = RequestMethod.POST)
	public ResponseEntity<?> updateReturnRequest(@RequestBody ReturnRequestDTO returnRequestDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = returnRequestService.updateReturnRequest(returnRequestDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
