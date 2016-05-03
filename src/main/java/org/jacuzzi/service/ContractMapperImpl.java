package org.jacuzzi.service;

import org.jacuzzi.core.Notification;
import org.jacuzzi.core.NotificationImpl;
import org.jacuzzi.model.NotificationData;
import org.jacuzzi.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by girish.desai on 3/18/2016.
 */
public class ContractMapperImpl implements ContractMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationImpl.class.getName());

    Notification notification;

    ContractMapperImpl(Notification notification) {
        this.notification = notification;
    }

    public Map executeMethod(Map mapRequest) {

        Map<String, Object> mapResponse;
        String request = (String) mapRequest.get(JSONConst.REQUEST);
        if (Utils.isEmpty(request)) {
            LOGGER.error("Invalid request");
            return formResponse(JSONConst.REQUEST_UNSUPPORTED, JSONConst.INVALID_PARAMS);
        }

        Map<String, Object> mapParams = (Map)mapRequest.get(JSONConst.PARAMS);
        if(mapParams == null)
            return formResponse(JSONConst.REQUEST_UNSUPPORTED, JSONConst.INVALID_PARAMS);

        switch (request) {
            case JSONConst.ADD_NOTIFICATION:
                mapResponse = addNotification(mapParams);
                break;
            case JSONConst.GET_NOTIFICATION:
                mapResponse = getNotification(mapParams);
                break;
            case JSONConst.SET_NOTIFICATION_STATUS:
                mapResponse = setNotificationStatus(mapParams);
                break;
            default:
                mapResponse = formResponse(JSONConst.REQUEST_UNSUPPORTED, JSONConst.INVALID_PARAMS);
                break;
        }

        return mapResponse;
    }

    private Map<String, Object> addNotification(Map mapParams) {

        String mobileNumber = (String)mapParams.get(JSONConst.MOBILE_NUMBER);
        String message = (String)mapParams.get(JSONConst.MESSAGE);
        String messageId = (String)mapParams.get(JSONConst.MESSAGE_ID);

        NotificationData nd = new NotificationData(mobileNumber, message, messageId);

        if(!notification.addNotification(nd))
            return formResponse(JSONConst.ADD_NOTIFICATION, JSONConst.FAILURE);
        return formResponse(JSONConst.ADD_NOTIFICATION, JSONConst.SUCCESS);
    }

    private Map<String, Object> getNotification(Map mapParams) {
        String mobileNumber = (String)mapParams.get(JSONConst.MOBILE_NUMBER);
        List<NotificationData> lst = notification.getNotification(mobileNumber);
        List<Map> messageList = new ArrayList<>();
        for(NotificationData nd : lst) {
            Map<String, Object> mapMessage = new HashMap<>();
            mapMessage.put(JSONConst.MESSAGE_ID, nd.getMessageId());
            mapMessage.put(JSONConst.MESSAGE, nd.getMessage());
            mapMessage.put(JSONConst.MOBILE_NUMBER, nd.getMobileNumber());
            mapMessage.put(JSONConst.MESSAGE_STATUS, nd.getMessageStatus());
            mapMessage.put(JSONConst.MESSAGE_DATE, Utils.dateToString(nd.getReceivedOn(), "dd-MMM-yyyy hh:mm:ss"));
            messageList.add(mapMessage);
        }
        Map<String, Object> mapResponse = formResponse(JSONConst.GET_NOTIFICATION, JSONConst.SUCCESS);
        mapResponse.put(JSONConst.MESSAGE_LIST, messageList);
        return mapResponse;
    }

    private Map<String, Object> setNotificationStatus(Map mapParams) {
        String messageId = (String)mapParams.get(JSONConst.MESSAGE_ID);
        String status = (String)mapParams.get(JSONConst.MESSAGE_STATUS);
        MessageStatus messageStatus;
        if(status.equals(JSONConst.SET_STATUS_READ))
            messageStatus = MessageStatus.READ;
        else
            messageStatus = MessageStatus.UNREAD;
        if(!notification.setNotificationStatus(messageId, messageStatus))
            return formResponse(JSONConst.SET_NOTIFICATION_STATUS, JSONConst.FAILURE);
        return formResponse(JSONConst.SET_NOTIFICATION_STATUS, JSONConst.SUCCESS);
    }

    private Map<String, Object> formResponse(String request, String status) {
        Map<String, Object> response = new HashMap<>();
        response.put(JSONConst.RESPONSE, request);
        response.put(JSONConst.STATUS_CODE, status);
        return response;
    }

}
