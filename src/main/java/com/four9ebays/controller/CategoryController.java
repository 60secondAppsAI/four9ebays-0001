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

import com.four9ebays.domain.Category;
import com.four9ebays.dto.CategoryDTO;
import com.four9ebays.dto.CategorySearchDTO;
import com.four9ebays.dto.CategoryPageDTO;
import com.four9ebays.service.CategoryService;
import com.four9ebays.dto.common.RequestDTO;
import com.four9ebays.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/category")
@RestController
public class CategoryController {

	private final static Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	CategoryService categoryService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Category> getAll() {

		List<Category> categorys = categoryService.findAll();
		
		return categorys;	
	}

	@GetMapping(value = "/{categoryId}")
	@ResponseBody
	public CategoryDTO getCategory(@PathVariable Integer categoryId) {
		
		return (categoryService.getCategoryDTOById(categoryId));
	}

 	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = categoryService.addCategory(categoryDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/categorys")
	public ResponseEntity<CategoryPageDTO> getCategorys(CategorySearchDTO categorySearchDTO) {
 
		return categoryService.getCategorys(categorySearchDTO);
	}	

	@RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
	public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = categoryService.updateCategory(categoryDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
