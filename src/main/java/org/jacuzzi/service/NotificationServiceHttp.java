package org.jacuzzi.service;

import org.jacuzzi.common.stringutil.StringUtil;
import org.jacuzzi.core.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by girish.desai on 3/18/2016.
 */
@RestController
@RequestMapping("/serviceRequest")
public class NotificationServiceHttp {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceHttp.class.getName());

    Notification notification;
    ContractMapper contractMapper;

    @Autowired
    public NotificationServiceHttp(Notification notification) {
        this.notification = notification;
        contractMapper = new ContractMapperImpl(notification);
    }

    //Specific for mobiquity money 4.8
    @RequestMapping(value = "/SUBMIT_MESSAGE_MONEY48", method = RequestMethod.GET)
    public Map notificationAdd1_Money48(@RequestParam String text, @RequestParam String to) {

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put(JSONConst.MESSAGE_ID, StringUtil.randomString(30));
        mapParams.put(JSONConst.MESSAGE, text);
        mapParams.put(JSONConst.MOBILE_NUMBER, to);

        Map<String, Object> mapRequest = new HashMap<>();
        mapRequest.put(JSONConst.REQUEST, JSONConst.ADD_NOTIFICATION);
        mapRequest.put(JSONConst.PARAMS, mapParams);

        Map map = contractMapper.executeMethod(mapRequest);

        LOGGER.info("Message submitted: " + " : " + text + " : " + to);

        return map;
    }


    @RequestMapping(value = "/ADD_NOTIFICATION", method = RequestMethod.GET)
    public Map notificationAdd1(@RequestParam String message, @RequestParam String mobileNumber) {

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put(JSONConst.MESSAGE_ID, StringUtil.randomString(30));
        mapParams.put(JSONConst.MESSAGE, message);
        mapParams.put(JSONConst.MOBILE_NUMBER, mobileNumber);

        Map<String, Object> mapRequest = new HashMap<>();
        mapRequest.put(JSONConst.REQUEST, JSONConst.ADD_NOTIFICATION);
        mapRequest.put(JSONConst.PARAMS, mapParams);

        Map map = contractMapper.executeMethod(mapRequest);

        LOGGER.info("Message submitted: " + " : " + message + " : " + mobileNumber);

        return map;
    }

    @RequestMapping(value = "/ADD_NOTIFICATION_WITH_MESSAGE_ID", method = RequestMethod.GET)
    public ResponseEntity notificationAdd2(@RequestParam String messageId, @RequestParam String message, @RequestParam String mobileNumber) {

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put(JSONConst.MESSAGE_ID, messageId);
        mapParams.put(JSONConst.MESSAGE, message);
        mapParams.put(JSONConst.MOBILE_NUMBER, mobileNumber);

        Map<String, Object> mapRequest = new HashMap<>();
        mapRequest.put(JSONConst.REQUEST, JSONConst.ADD_NOTIFICATION);
        mapRequest.put(JSONConst.PARAMS, mapParams);

        Map map = contractMapper.executeMethod(mapRequest);

        String str = (String)map.get(JSONConst.STATUS_CODE);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JSONConst.REQUEST, JSONConst.ADD_NOTIFICATION);
        if(str.equals(JSONConst.FAILURE)) {
            return new ResponseEntity<>(map, httpHeaders, HttpStatus.CONFLICT);
        }
        LOGGER.info("Message submitted: " + messageId + " : " + message + " : " + mobileNumber);

        return new ResponseEntity<>(map, httpHeaders, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/GET_NOTIFICATION", method = RequestMethod.GET)
    public Map notificationGet(@RequestParam String mobileNumber) {

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put(JSONConst.MOBILE_NUMBER, mobileNumber);

        Map<String, Object> mapRequest = new HashMap<>();
        mapRequest.put(JSONConst.REQUEST, JSONConst.GET_NOTIFICATION);
        mapRequest.put(JSONConst.PARAMS, mapParams);

        Map map = contractMapper.executeMethod(mapRequest);
        LOGGER.info("Response: " + map.toString());
        return map;
    }

    @RequestMapping(value = "/SET_NOTIFICATION_STATUS", method = RequestMethod.GET)
    public Map notificationSetStatus(@RequestParam String messageId, @RequestParam String status) {

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put(JSONConst.MESSAGE_ID, messageId);
        mapParams.put(JSONConst.MESSAGE_STATUS, status);

        Map<String, Object> mapRequest = new HashMap<>();
        mapRequest.put(JSONConst.REQUEST, JSONConst.SET_NOTIFICATION_STATUS);
        mapRequest.put(JSONConst.PARAMS, mapParams);

        Map map = contractMapper.executeMethod(mapRequest);
        return map;

    }

}
