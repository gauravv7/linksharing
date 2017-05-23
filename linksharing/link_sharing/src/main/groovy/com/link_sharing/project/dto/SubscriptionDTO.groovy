package com.link_sharing.project.dto

import com.link_sharing.project.Subscription
import com.link_sharing.project.Topic

/**
 * Created by gaurav on 22/5/17.
 */
class SubscriptionDTO {

    Integer getSubsciptionCount(Topic topic){
        def s = Subscription.createCriteria()

        def subscribers = s.count {
            eq("topic", topic)

        }

        return subscribers
    }
}
