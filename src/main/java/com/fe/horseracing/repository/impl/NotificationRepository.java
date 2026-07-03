package com.fe.horseracing.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fe.horseracing.DAO.NotificationDAO;
import com.fe.horseracing.pojo.Notification;
import com.fe.horseracing.repository.interfaces.INotificationRepository;

@Repository
public class NotificationRepository implements INotificationRepository  {

    @Autowired
    private NotificationDAO notificationDAO;
    
	@Override
	public void save(Notification notification) {
		// TODO Auto-generated method stub
		notificationDAO.save(notification);
	}

	@Override
	public void update(Notification notification) {
		// TODO Auto-generated method stub
		notificationDAO.update(notification);
	}

	@Override
	public void delete(Long notificationId) {
		// TODO Auto-generated method stub
		notificationDAO.delete(notificationId);
	}

	@Override
	public Notification findById(Long notificationId) {
		// TODO Auto-generated method stub
		return notificationDAO.findById(notificationId);
	}

	@Override
	public List<Notification> findAll() {
		// TODO Auto-generated method stub
		return notificationDAO.findAll();
	}

	@Override
	public List<Notification> findByUser(Long userId) {
		// TODO Auto-generated method stub
		return notificationDAO.findByUser(userId);
	}

	@Override
	public List<Notification> findUnreadByUser(Long userId) {
		// TODO Auto-generated method stub
		return notificationDAO.findUnreadByUser(userId);
	}

	@Override
	public void markAsRead(Long notificationId) {
		// TODO Auto-generated method stub
		notificationDAO.markAsRead(notificationId);
	}

	@Override
	public Long countUnread(Long userId) {
		// TODO Auto-generated method stub
		return notificationDAO.countUnread(userId);
	}

}
