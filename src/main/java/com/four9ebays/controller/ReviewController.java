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

import com.four9ebays.domain.Review;
import com.four9ebays.dto.ReviewDTO;
import com.four9ebays.dto.ReviewSearchDTO;
import com.four9ebays.dto.ReviewPageDTO;
import com.four9ebays.service.ReviewService;
import com.four9ebays.dto.common.RequestDTO;
import com.four9ebays.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/review")
@RestController
public class ReviewController {

	private final static Logger logger = LoggerFactory.getLogger(ReviewController.class);

	@Autowired
	ReviewService reviewService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Review> getAll() {

		List<Review> reviews = reviewService.findAll();
		
		return reviews;	
	}

	@GetMapping(value = "/{reviewId}")
	@ResponseBody
	public ReviewDTO getReview(@PathVariable Integer reviewId) {
		
		return (reviewService.getReviewDTOById(reviewId));
	}

 	@RequestMapping(value = "/addReview", method = RequestMethod.POST)
	public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = reviewService.addReview(reviewDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/reviews")
	public ResponseEntity<ReviewPageDTO> getReviews(ReviewSearchDTO reviewSearchDTO) {
 
		return reviewService.getReviews(reviewSearchDTO);
	}	

	@RequestMapping(value = "/updateReview", method = RequestMethod.POST)
	public ResponseEntity<?> updateReview(@RequestBody ReviewDTO reviewDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = reviewService.updateReview(reviewDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
