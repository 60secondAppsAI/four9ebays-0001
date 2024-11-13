package com.four9ebays.dao;

import java.util.List;

import com.four9ebays.dao.GenericDAO;
import com.four9ebays.domain.Order;





public interface OrderDAO extends GenericDAO<Order, Integer> {
  
	List<Order> findAll();
	






}


