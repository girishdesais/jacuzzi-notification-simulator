package org.jacuzzi.core;

import org.jacuzzi.model.NotificationData;
import org.jacuzzi.service.MessageStatus;

import java.util.List;

/**
 * Created by girish.desai on 3/18/2016.
 */
public interface Notification {
    boolean addNotification(NotificationData nd);

    List<NotificationData> getNotification(String mobileNumber);

    boolean setNotificationStatus(String messageId, MessageStatus messageStatus);
}
