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
import com.four9ebays.dao.ImageDAO;
import com.four9ebays.domain.Image;
import com.four9ebays.dto.ImageDTO;
import com.four9ebays.dto.ImageSearchDTO;
import com.four9ebays.dto.ImagePageDTO;
import com.four9ebays.dto.ImageConvertCriteriaDTO;
import com.four9ebays.dto.common.RequestDTO;
import com.four9ebays.dto.common.ResultDTO;
import com.four9ebays.service.ImageService;
import com.four9ebays.util.ControllerUtils;





@Service
public class ImageServiceImpl extends GenericServiceImpl<Image, Integer> implements ImageService {

    private final static Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

	@Autowired
	ImageDAO imageDao;

	


	@Override
	public GenericDAO<Image, Integer> getDAO() {
		return (GenericDAO<Image, Integer>) imageDao;
	}
	
	public List<Image> findAll () {
		List<Image> images = imageDao.findAll();
		
		return images;	
		
	}

	public ResultDTO addImage(ImageDTO imageDTO, RequestDTO requestDTO) {

		Image image = new Image();

		image.setImageId(imageDTO.getImageId());


		image.setImageURL(imageDTO.getImageURL());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		image = imageDao.save(image);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Image> getAllImages(Pageable pageable) {
		return imageDao.findAll(pageable);
	}

	public Page<Image> getAllImages(Specification<Image> spec, Pageable pageable) {
		return imageDao.findAll(spec, pageable);
	}

	public ResponseEntity<ImagePageDTO> getImages(ImageSearchDTO imageSearchDTO) {
	
			Integer imageId = imageSearchDTO.getImageId(); 
 			String imageURL = imageSearchDTO.getImageURL(); 
 			String sortBy = imageSearchDTO.getSortBy();
			String sortOrder = imageSearchDTO.getSortOrder();
			String searchQuery = imageSearchDTO.getSearchQuery();
			Integer page = imageSearchDTO.getPage();
			Integer size = imageSearchDTO.getSize();

	        Specification<Image> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, imageId, "imageId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, imageURL, "imageURL"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("imageURL")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Image> images = this.getAllImages(spec, pageable);
		
		//System.out.println(String.valueOf(images.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(images.getTotalPages()));
		
		List<Image> imagesList = images.getContent();
		
		ImageConvertCriteriaDTO convertCriteria = new ImageConvertCriteriaDTO();
		List<ImageDTO> imageDTOs = this.convertImagesToImageDTOs(imagesList,convertCriteria);
		
		ImagePageDTO imagePageDTO = new ImagePageDTO();
		imagePageDTO.setImages(imageDTOs);
		imagePageDTO.setTotalElements(images.getTotalElements());
		return ResponseEntity.ok(imagePageDTO);
	}

	public List<ImageDTO> convertImagesToImageDTOs(List<Image> images, ImageConvertCriteriaDTO convertCriteria) {
		
		List<ImageDTO> imageDTOs = new ArrayList<ImageDTO>();
		
		for (Image image : images) {
			imageDTOs.add(convertImageToImageDTO(image,convertCriteria));
		}
		
		return imageDTOs;

	}
	
	public ImageDTO convertImageToImageDTO(Image image, ImageConvertCriteriaDTO convertCriteria) {
		
		ImageDTO imageDTO = new ImageDTO();
		
		imageDTO.setImageId(image.getImageId());

	
		imageDTO.setImageURL(image.getImageURL());

	

		
		return imageDTO;
	}

	public ResultDTO updateImage(ImageDTO imageDTO, RequestDTO requestDTO) {
		
		Image image = imageDao.getById(imageDTO.getImageId());

		image.setImageId(ControllerUtils.setValue(image.getImageId(), imageDTO.getImageId()));

		image.setImageURL(ControllerUtils.setValue(image.getImageURL(), imageDTO.getImageURL()));



        image = imageDao.save(image);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ImageDTO getImageDTOById(Integer imageId) {
	
		Image image = imageDao.getById(imageId);
			
		
		ImageConvertCriteriaDTO convertCriteria = new ImageConvertCriteriaDTO();
		return(this.convertImageToImageDTO(image,convertCriteria));
	}







}
