package com.four9ebays.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.four9ebays.domain.Image;
import com.four9ebays.dto.ImageDTO;
import com.four9ebays.dto.ImageSearchDTO;
import com.four9ebays.dto.ImagePageDTO;
import com.four9ebays.dto.ImageConvertCriteriaDTO;
import com.four9ebays.service.GenericService;
import com.four9ebays.dto.common.RequestDTO;
import com.four9ebays.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ImageService extends GenericService<Image, Integer> {

	List<Image> findAll();

	ResultDTO addImage(ImageDTO imageDTO, RequestDTO requestDTO);

	ResultDTO updateImage(ImageDTO imageDTO, RequestDTO requestDTO);

    Page<Image> getAllImages(Pageable pageable);

    Page<Image> getAllImages(Specification<Image> spec, Pageable pageable);

	ResponseEntity<ImagePageDTO> getImages(ImageSearchDTO imageSearchDTO);
	
	List<ImageDTO> convertImagesToImageDTOs(List<Image> images, ImageConvertCriteriaDTO convertCriteria);

	ImageDTO getImageDTOById(Integer imageId);







}





