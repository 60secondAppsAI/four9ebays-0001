package com.four9ebays.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<CartItemDTO> cartItems;
}





