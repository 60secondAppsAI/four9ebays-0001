package com.four9ebays.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.four9ebays.domain.Notification;
import com.four9ebays.dto.NotificationDTO;
import com.four9ebays.dto.NotificationSearchDTO;
import com.four9ebays.dto.NotificationPageDTO;
import com.four9ebays.dto.NotificationConvertCriteriaDTO;
import com.four9ebays.service.GenericService;
import com.four9ebays.dto.common.RequestDTO;
import com.four9ebays.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface NotificationService extends GenericService<Notification, Integer> {

	List<Notification> findAll();

	ResultDTO addNotification(NotificationDTO notificationDTO, RequestDTO requestDTO);

	ResultDTO updateNotification(NotificationDTO notificationDTO, RequestDTO requestDTO);

    Page<Notification> getAllNotifications(Pageable pageable);

    Page<Notification> getAllNotifications(Specification<Notification> spec, Pageable pageable);

	ResponseEntity<NotificationPageDTO> getNotifications(NotificationSearchDTO notificationSearchDTO);
	
	List<NotificationDTO> convertNotificationsToNotificationDTOs(List<Notification> notifications, NotificationConvertCriteriaDTO convertCriteria);

	NotificationDTO getNotificationDTOById(Integer notificationId);







}





