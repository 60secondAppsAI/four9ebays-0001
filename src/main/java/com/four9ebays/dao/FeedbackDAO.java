package com.four9ebays.dao;

import java.util.List;

import com.four9ebays.dao.GenericDAO;
import com.four9ebays.domain.Feedback;





public interface FeedbackDAO extends GenericDAO<Feedback, Integer> {
  
	List<Feedback> findAll();
	






}


