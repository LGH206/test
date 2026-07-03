package com.fe.horseracing.DAO;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fe.horseracing.pojo.Notification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
@Transactional
public class NotificationDAO {
	
    @PersistenceContext
    private EntityManager em;

    // CREATE
    public void save(Notification notification) {
        em.persist(notification);
    }

    // UPDATE
    public void update(Notification notification) {
        em.merge(notification);
    }

    // DELETE
    public void delete(Long notificationId) {
        Notification notification = em.find(Notification.class, notificationId);
        if (notification != null) {
            em.remove(notification);
        }
    }

    // FIND BY ID
    public Notification findById(Long notificationId) {
        return em.find(Notification.class, notificationId);
    }

    // FIND ALL
    public List<Notification> findAll() {
        TypedQuery<Notification> query = em.createQuery(
                        "SELECT n FROM Notification n",
                        Notification.class);
        return query.getResultList();
    }

    // FIND BY USER
    public List<Notification> findByUser(Long userId) {
        TypedQuery<Notification> query = em.createQuery(
                        "SELECT n FROM Notification n " +
                        "WHERE n.user.userId = :userId " +
                        "ORDER BY n.createdAt DESC",
                        Notification.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    // FIND UNREAD
    public List<Notification> findUnreadByUser(Long userId) {
        TypedQuery<Notification> query = em.createQuery(
                        "SELECT n FROM Notification n " +
                        "WHERE n.user.userId = :userId " +
                        "AND n.isRead = false " +
                        "ORDER BY n.createdAt DESC",
                        Notification.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    // MARK AS READ
    public void markAsRead(Long notificationId) {
        Notification notification = em.find(Notification.class, notificationId);
        if (notification != null) {
            notification.setIsRead(true);
        }
    }

    // COUNT UNREAD
    public Long countUnread(Long userId) {
    	TypedQuery<Long> query = em.createQuery(
                        "SELECT COUNT(n) " +
                        "FROM Notification n " +
                        "WHERE n.user.userId = :userId " +
                        "AND n.isRead = false",
                        Long.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }
}