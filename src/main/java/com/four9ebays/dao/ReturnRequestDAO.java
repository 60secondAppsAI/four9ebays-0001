package com.four9ebays.dao;

import java.util.List;

import com.four9ebays.dao.GenericDAO;
import com.four9ebays.domain.ReturnRequest;





public interface ReturnRequestDAO extends GenericDAO<ReturnRequest, Integer> {
  
	List<ReturnRequest> findAll();
	






}


