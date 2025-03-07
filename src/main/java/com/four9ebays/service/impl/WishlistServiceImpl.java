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
import com.four9ebays.dao.WishlistDAO;
import com.four9ebays.domain.Wishlist;
import com.four9ebays.dto.WishlistDTO;
import com.four9ebays.dto.WishlistSearchDTO;
import com.four9ebays.dto.WishlistPageDTO;
import com.four9ebays.dto.WishlistConvertCriteriaDTO;
import com.four9ebays.dto.common.RequestDTO;
import com.four9ebays.dto.common.ResultDTO;
import com.four9ebays.service.WishlistService;
import com.four9ebays.util.ControllerUtils;





@Service
public class WishlistServiceImpl extends GenericServiceImpl<Wishlist, Integer> implements WishlistService {

    private final static Logger logger = LoggerFactory.getLogger(WishlistServiceImpl.class);

	@Autowired
	WishlistDAO wishlistDao;

	


	@Override
	public GenericDAO<Wishlist, Integer> getDAO() {
		return (GenericDAO<Wishlist, Integer>) wishlistDao;
	}
	
	public List<Wishlist> findAll () {
		List<Wishlist> wishlists = wishlistDao.findAll();
		
		return wishlists;	
		
	}

	public ResultDTO addWishlist(WishlistDTO wishlistDTO, RequestDTO requestDTO) {

		Wishlist wishlist = new Wishlist();

		wishlist.setWishlistId(wishlistDTO.getWishlistId());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		wishlist = wishlistDao.save(wishlist);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Wishlist> getAllWishlists(Pageable pageable) {
		return wishlistDao.findAll(pageable);
	}

	public Page<Wishlist> getAllWishlists(Specification<Wishlist> spec, Pageable pageable) {
		return wishlistDao.findAll(spec, pageable);
	}

	public ResponseEntity<WishlistPageDTO> getWishlists(WishlistSearchDTO wishlistSearchDTO) {
	
			Integer wishlistId = wishlistSearchDTO.getWishlistId(); 
 			String sortBy = wishlistSearchDTO.getSortBy();
			String sortOrder = wishlistSearchDTO.getSortOrder();
			String searchQuery = wishlistSearchDTO.getSearchQuery();
			Integer page = wishlistSearchDTO.getPage();
			Integer size = wishlistSearchDTO.getSize();

	        Specification<Wishlist> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, wishlistId, "wishlistId"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<Wishlist> wishlists = this.getAllWishlists(spec, pageable);
		
		//System.out.println(String.valueOf(wishlists.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(wishlists.getTotalPages()));
		
		List<Wishlist> wishlistsList = wishlists.getContent();
		
		WishlistConvertCriteriaDTO convertCriteria = new WishlistConvertCriteriaDTO();
		List<WishlistDTO> wishlistDTOs = this.convertWishlistsToWishlistDTOs(wishlistsList,convertCriteria);
		
		WishlistPageDTO wishlistPageDTO = new WishlistPageDTO();
		wishlistPageDTO.setWishlists(wishlistDTOs);
		wishlistPageDTO.setTotalElements(wishlists.getTotalElements());
		return ResponseEntity.ok(wishlistPageDTO);
	}

	public List<WishlistDTO> convertWishlistsToWishlistDTOs(List<Wishlist> wishlists, WishlistConvertCriteriaDTO convertCriteria) {
		
		List<WishlistDTO> wishlistDTOs = new ArrayList<WishlistDTO>();
		
		for (Wishlist wishlist : wishlists) {
			wishlistDTOs.add(convertWishlistToWishlistDTO(wishlist,convertCriteria));
		}
		
		return wishlistDTOs;

	}
	
	public WishlistDTO convertWishlistToWishlistDTO(Wishlist wishlist, WishlistConvertCriteriaDTO convertCriteria) {
		
		WishlistDTO wishlistDTO = new WishlistDTO();
		
		wishlistDTO.setWishlistId(wishlist.getWishlistId());

	

		
		return wishlistDTO;
	}

	public ResultDTO updateWishlist(WishlistDTO wishlistDTO, RequestDTO requestDTO) {
		
		Wishlist wishlist = wishlistDao.getById(wishlistDTO.getWishlistId());

		wishlist.setWishlistId(ControllerUtils.setValue(wishlist.getWishlistId(), wishlistDTO.getWishlistId()));



        wishlist = wishlistDao.save(wishlist);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public WishlistDTO getWishlistDTOById(Integer wishlistId) {
	
		Wishlist wishlist = wishlistDao.getById(wishlistId);
			
		
		WishlistConvertCriteriaDTO convertCriteria = new WishlistConvertCriteriaDTO();
		return(this.convertWishlistToWishlistDTO(wishlist,convertCriteria));
	}







}
