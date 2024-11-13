package com.four9ebays.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.four9ebays.dao.GenericDAO;
import com.four9ebays.service.GenericService;
import com.four9ebays.service.impl.GenericServiceImpl;
import com.four9ebays.dao.ReturnRequestDAO;
import com.four9ebays.domain.ReturnRequest;
import com.four9ebays.dto.ReturnRequestDTO;
import com.four9ebays.dto.ReturnRequestSearchDTO;
import com.four9ebays.dto.ReturnRequestPageDTO;
import com.four9ebays.dto.ReturnRequestConvertCriteriaDTO;
import com.four9ebays.dto.common.RequestDTO;
import com.four9ebays.dto.common.ResultDTO;
import com.four9ebays.service.ReturnRequestService;
import com.four9ebays.util.ControllerUtils;





@Service
public class ReturnRequestServiceImpl extends GenericServiceImpl<ReturnRequest, Integer> implements ReturnRequestService {

    private final static Logger logger = LoggerFactory.getLogger(ReturnRequestServiceImpl.class);

	@Autowired
	ReturnRequestDAO returnRequestDao;

	


	@Override
	public GenericDAO<ReturnRequest, Integer> getDAO() {
		return (GenericDAO<ReturnRequest, Integer>) returnRequestDao;
	}
	
	public List<ReturnRequest> findAll () {
		List<ReturnRequest> returnRequests = returnRequestDao.findAll();
		
		return returnRequests;	
		
	}

	public ResultDTO addReturnRequest(ReturnRequestDTO returnRequestDTO, RequestDTO requestDTO) {

		ReturnRequest returnRequest = new ReturnRequest();

		returnRequest.setReturnRequestId(returnRequestDTO.getReturnRequestId());


		returnRequest.setRequestDate(returnRequestDTO.getRequestDate());


		returnRequest.setApprovalStatus(returnRequestDTO.getApprovalStatus());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		returnRequest = returnRequestDao.save(returnRequest);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ReturnRequest> getAllReturnRequests(Pageable pageable) {
		return returnRequestDao.findAll(pageable);
	}

	public Page<ReturnRequest> getAllReturnRequests(Specification<ReturnRequest> spec, Pageable pageable) {
		return returnRequestDao.findAll(spec, pageable);
	}

	public ResponseEntity<ReturnRequestPageDTO> getReturnRequests(ReturnRequestSearchDTO returnRequestSearchDTO) {
	
			Integer returnRequestId = returnRequestSearchDTO.getReturnRequestId(); 
   			String approvalStatus = returnRequestSearchDTO.getApprovalStatus(); 
 			String sortBy = returnRequestSearchDTO.getSortBy();
			String sortOrder = returnRequestSearchDTO.getSortOrder();
			String searchQuery = returnRequestSearchDTO.getSearchQuery();
			Integer page = returnRequestSearchDTO.getPage();
			Integer size = returnRequestSearchDTO.getSize();

	        Specification<ReturnRequest> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, returnRequestId, "returnRequestId"); 
			
 			
			spec = ControllerUtils.andIfNecessary(spec, approvalStatus, "approvalStatus"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("approvalStatus")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<ReturnRequest> returnRequests = this.getAllReturnRequests(spec, pageable);
		
		//System.out.println(String.valueOf(returnRequests.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(returnRequests.getTotalPages()));
		
		List<ReturnRequest> returnRequestsList = returnRequests.getContent();
		
		ReturnRequestConvertCriteriaDTO convertCriteria = new ReturnRequestConvertCriteriaDTO();
		List<ReturnRequestDTO> returnRequestDTOs = this.convertReturnRequestsToReturnRequestDTOs(returnRequestsList,convertCriteria);
		
		ReturnRequestPageDTO returnRequestPageDTO = new ReturnRequestPageDTO();
		returnRequestPageDTO.setReturnRequests(returnRequestDTOs);
		returnRequestPageDTO.setTotalElements(returnRequests.getTotalElements());
		return ResponseEntity.ok(returnRequestPageDTO);
	}

	public List<ReturnRequestDTO> convertReturnRequestsToReturnRequestDTOs(List<ReturnRequest> returnRequests, ReturnRequestConvertCriteriaDTO convertCriteria) {
		
		List<ReturnRequestDTO> returnRequestDTOs = new ArrayList<ReturnRequestDTO>();
		
		for (ReturnRequest returnRequest : returnRequests) {
			returnRequestDTOs.add(convertReturnRequestToReturnRequestDTO(returnRequest,convertCriteria));
		}
		
		return returnRequestDTOs;

	}
	
	public ReturnRequestDTO convertReturnRequestToReturnRequestDTO(ReturnRequest returnRequest, ReturnRequestConvertCriteriaDTO convertCriteria) {
		
		ReturnRequestDTO returnRequestDTO = new ReturnRequestDTO();
		
		returnRequestDTO.setReturnRequestId(returnRequest.getReturnRequestId());

	
		returnRequestDTO.setRequestDate(returnRequest.getRequestDate());

	
		returnRequestDTO.setApprovalStatus(returnRequest.getApprovalStatus());

	

		
		return returnRequestDTO;
	}

	public ResultDTO updateReturnRequest(ReturnRequestDTO returnRequestDTO, RequestDTO requestDTO) {
		
		ReturnRequest returnRequest = returnRequestDao.getById(returnRequestDTO.getReturnRequestId());

		returnRequest.setReturnRequestId(ControllerUtils.setValue(returnRequest.getReturnRequestId(), returnRequestDTO.getReturnRequestId()));

		returnRequest.setRequestDate(ControllerUtils.setValue(returnRequest.getRequestDate(), returnRequestDTO.getRequestDate()));

		returnRequest.setApprovalStatus(ControllerUtils.setValue(returnRequest.getApprovalStatus(), returnRequestDTO.getApprovalStatus()));



        returnRequest = returnRequestDao.save(returnRequest);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ReturnRequestDTO getReturnRequestDTOById(Integer returnRequestId) {
	
		ReturnRequest returnRequest = returnRequestDao.getById(returnRequestId);
			
		
		ReturnRequestConvertCriteriaDTO convertCriteria = new ReturnRequestConvertCriteriaDTO();
		return(this.convertReturnRequestToReturnRequestDTO(returnRequest,convertCriteria));
	}







}
