package com.fe.horseracing.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fe.horseracing.pojo.Notification;
import com.fe.horseracing.pojo.User;
import com.fe.horseracing.repository.interfaces.INotificationRepository;
import com.fe.horseracing.service.interfaces.INotificationService;

@Service
public class NotificationService implements INotificationService {
    private INotificationRepository notificationRepo;

    @Autowired
    public NotificationService(
            INotificationRepository notificationRepo) {

        this.notificationRepo = notificationRepo;
    }
    
	@Override
	public void save(Notification notification) {
		// TODO Auto-generated method stub
		notificationRepo.save(notification);
	}

	@Override
	public void update(Notification notification) {
		// TODO Auto-generated method stub
		notificationRepo.update(notification);
	}

	@Override
	public void delete(Long notificationId) {
		// TODO Auto-generated method stub
		notificationRepo.delete(notificationId);
	}

	@Override
	public Notification findById(Long notificationId) {
		// TODO Auto-generated method stub
		return notificationRepo.findById(notificationId);
	}

	@Override
	public List<Notification> findAll() {
		// TODO Auto-generated method stub
		return notificationRepo.findAll();
	}

	@Override
	public List<Notification> findByUser(Long userId) {
		// TODO Auto-generated method stub
		return notificationRepo.findByUser(userId);
	}

	@Override
	public List<Notification> findUnreadByUser(Long userId) {
		// TODO Auto-generated method stub
		return notificationRepo.findUnreadByUser(userId);
	}

	@Override
	public void markAsRead(Long notificationId) {
		// TODO Auto-generated method stub
		notificationRepo.markAsRead(notificationId);
	}

	@Override
	public Long countUnread(Long userId) {
		// TODO Auto-generated method stub
		return notificationRepo.countUnread(userId);
	}

	@Override
	public void createNotification(User user, String title, String message) {
		// TODO Auto-generated method stub
    Notification notification = new Notification();
    notification.setUser(user);
    notification.setTitle(title);
    notification.setMessage(message);
    notification.setCreatedAt(LocalDateTime.now());
    notification.setIsRead(false);
    notificationRepo.save(notification);
	}

}
