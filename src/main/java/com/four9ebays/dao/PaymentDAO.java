package com.four9ebays.dao;

import java.util.List;

import com.four9ebays.dao.GenericDAO;
import com.four9ebays.domain.Payment;





public interface PaymentDAO extends GenericDAO<Payment, Integer> {
  
	List<Payment> findAll();
	






}


