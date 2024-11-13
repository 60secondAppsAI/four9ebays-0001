package com.four9ebays.dao;

import java.util.List;

import com.four9ebays.dao.GenericDAO;
import com.four9ebays.domain.Transaction;





public interface TransactionDAO extends GenericDAO<Transaction, Integer> {
  
	List<Transaction> findAll();
	






}


