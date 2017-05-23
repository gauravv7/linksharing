package com.link_sharing.project

import com.link_sharing.project.co.SubscriptionCO
import grails.transaction.Transactional

@Transactional
class SubscriptionService {

    def countByCreatedBy(User user){
        return Subscription.countByCreatedBy(user)
    }

    def findByCreatedByAndTopic(User user, Long topicID){
        return Subscription.findByCreatedByAndTopic(user, Topic.load(topicID))
    }

    def save(SubscriptionCO subscriptionCO){
        Subscription subscription = new Subscription(user: subscriptionCO.createdBy, topic: subscriptionCO.topic, seriousness: subscriptionCO.seriousness)
        return subscription.save(flush:true)
    }

    def findAllByCreatedBy(User user){
        return Subscription.findAllByCreatedBy(user)
    }

}
