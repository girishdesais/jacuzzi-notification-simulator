package org.jacuzzi.core;

import org.jacuzzi.contract.NotificationRepository;
import org.jacuzzi.model.NotificationData;
import org.jacuzzi.service.MessageStatus;
import org.jacuzzi.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by girish.desai on 3/18/2016.
 */
@Service
public class NotificationImpl implements Notification {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationImpl.class.getName());
    NotificationRepository notificationRepository;

    @Autowired
    public NotificationImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public boolean addNotification(NotificationData nd) {
        if(nd == null) {
            return false;
        }
        try {
            notificationRepository.save(nd);
        } catch (Exception e) {
            LOGGER.error("Unable to add Notification for message Id: \"" + nd.getMessageId() + "\" Exception:" + e);
            return false;
        }
        return true;
    }

    public List<NotificationData> getNotification(String mobileNumber) {
        if(Utils.isEmpty(mobileNumber))
            return Collections.EMPTY_LIST;

        List<NotificationData> lst;
        try {
            Pageable page = new PageRequest(0, 20);
            lst = notificationRepository.findByMobileNumberOrderByReceivedOnDesc(mobileNumber, page);
        } catch (Exception e) {
            LOGGER.error("Exception: Unable to get notification " + e);
            return Collections.EMPTY_LIST;
        }
        return lst;
    }

    public boolean setNotificationStatus(String messageId, MessageStatus messageStatus) {
        if(Utils.isEmpty(messageId) || messageStatus == null)
            return false;
        try {
            List<NotificationData> lst = notificationRepository.findByMessageId(messageId);
            if(!lst.isEmpty()) {
                NotificationData nd = lst.get(0);
                nd.setMessageStatus(messageStatus);
                notificationRepository.save(nd);
            }
        } catch (Exception e) {
            LOGGER.error("Exception: Unable to set/unset notification status "+ e);
            return false;
        }

        return true;
    }
}
