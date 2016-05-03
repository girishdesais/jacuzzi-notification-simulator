package org.jacuzzi.model;

import org.jacuzzi.service.MessageStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by girish.desai on 3/18/2016.
 */
@Entity
@Table(name = "NOTIFICATIONS")
public class NotificationData {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int sysId;

    @Column(name = "MESSAGE_ID")
    private String messageId;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "STATUS")
    private MessageStatus messageStatus;

    @Column(name = "RECEIVED_ON")
    private Date receivedOn;

    public NotificationData() {}

    public NotificationData(String mobileNumber, String message, String messageId) {
        this.receivedOn = new Date();
        this.mobileNumber = mobileNumber;
        this.message = message;
        this.messageId = messageId;
        this.messageStatus = MessageStatus.UNREAD;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getMessage() {
        return message;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public Date getReceivedOn() {
        return receivedOn;
    }
}
