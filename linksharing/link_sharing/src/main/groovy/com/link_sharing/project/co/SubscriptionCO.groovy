package com.link_sharing.project.co

import com.link_sharing.project.Seriousness
import com.link_sharing.project.Subscription
import com.link_sharing.project.Topic
import com.link_sharing.project.User
import grails.validation.Validateable

/**
 * Created by gaurav on 22/5/17.
 */
class SubscriptionCO implements Validateable{

    Topic topic
    Seriousness seriousness
    User createdBy

    static constraints ={
        importFrom Subscription
    }

}
