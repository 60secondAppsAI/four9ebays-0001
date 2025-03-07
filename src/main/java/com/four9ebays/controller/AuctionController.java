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

import com.four9ebays.domain.Auction;
import com.four9ebays.dto.AuctionDTO;
import com.four9ebays.dto.AuctionSearchDTO;
import com.four9ebays.dto.AuctionPageDTO;
import com.four9ebays.service.AuctionService;
import com.four9ebays.dto.common.RequestDTO;
import com.four9ebays.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/auction")
@RestController
public class AuctionController {

	private final static Logger logger = LoggerFactory.getLogger(AuctionController.class);

	@Autowired
	AuctionService auctionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Auction> getAll() {

		List<Auction> auctions = auctionService.findAll();
		
		return auctions;	
	}

	@GetMapping(value = "/{auctionId}")
	@ResponseBody
	public AuctionDTO getAuction(@PathVariable Integer auctionId) {
		
		return (auctionService.getAuctionDTOById(auctionId));
	}

 	@RequestMapping(value = "/addAuction", method = RequestMethod.POST)
	public ResponseEntity<?> addAuction(@RequestBody AuctionDTO auctionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = auctionService.addAuction(auctionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/auctions")
	public ResponseEntity<AuctionPageDTO> getAuctions(AuctionSearchDTO auctionSearchDTO) {
 
		return auctionService.getAuctions(auctionSearchDTO);
	}	

	@RequestMapping(value = "/updateAuction", method = RequestMethod.POST)
	public ResponseEntity<?> updateAuction(@RequestBody AuctionDTO auctionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = auctionService.updateAuction(auctionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
