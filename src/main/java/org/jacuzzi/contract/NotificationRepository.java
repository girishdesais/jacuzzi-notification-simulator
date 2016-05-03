package org.jacuzzi.contract;

import org.jacuzzi.model.NotificationData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by girish.desai on 3/18/2016.
 */
public interface NotificationRepository extends CrudRepository<NotificationData, Long> {
    List<NotificationData> findByMessageId(String name);

    List<NotificationData> findByMobileNumberOrderByReceivedOnDesc(String mobileNumber, Pageable page);
}
