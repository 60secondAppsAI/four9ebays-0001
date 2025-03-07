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

import com.four9ebays.domain.CartItem;
import com.four9ebays.dto.CartItemDTO;
import com.four9ebays.dto.CartItemSearchDTO;
import com.four9ebays.dto.CartItemPageDTO;
import com.four9ebays.service.CartItemService;
import com.four9ebays.dto.common.RequestDTO;
import com.four9ebays.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/cartItem")
@RestController
public class CartItemController {

	private final static Logger logger = LoggerFactory.getLogger(CartItemController.class);

	@Autowired
	CartItemService cartItemService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<CartItem> getAll() {

		List<CartItem> cartItems = cartItemService.findAll();
		
		return cartItems;	
	}

	@GetMapping(value = "/{cartItemId}")
	@ResponseBody
	public CartItemDTO getCartItem(@PathVariable Integer cartItemId) {
		
		return (cartItemService.getCartItemDTOById(cartItemId));
	}

 	@RequestMapping(value = "/addCartItem", method = RequestMethod.POST)
	public ResponseEntity<?> addCartItem(@RequestBody CartItemDTO cartItemDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = cartItemService.addCartItem(cartItemDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/cartItems")
	public ResponseEntity<CartItemPageDTO> getCartItems(CartItemSearchDTO cartItemSearchDTO) {
 
		return cartItemService.getCartItems(cartItemSearchDTO);
	}	

	@RequestMapping(value = "/updateCartItem", method = RequestMethod.POST)
	public ResponseEntity<?> updateCartItem(@RequestBody CartItemDTO cartItemDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = cartItemService.updateCartItem(cartItemDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
