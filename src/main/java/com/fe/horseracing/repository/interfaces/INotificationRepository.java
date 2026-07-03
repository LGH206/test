package com.fe.horseracing.repository.interfaces;

import java.util.List;
import com.fe.horseracing.pojo.Notification;

public interface INotificationRepository {

    void save(Notification notification);

    void update(Notification notification);

    void delete(Long notificationId);

    Notification findById(Long notificationId);

    List<Notification> findAll();

    List<Notification> findByUser(Long userId);

    List<Notification> findUnreadByUser(Long userId);

    void markAsRead(Long notificationId);

    Long countUnread(Long userId);
}