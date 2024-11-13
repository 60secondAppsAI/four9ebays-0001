package com.four9ebays.dao;

import java.util.List;

import com.four9ebays.dao.GenericDAO;
import com.four9ebays.domain.Cart;





public interface CartDAO extends GenericDAO<Cart, Integer> {
  
	List<Cart> findAll();
	






}


