package com.link_sharing.project.co

import com.link_sharing.project.User
import com.link_sharing.project.Visibility
import grails.validation.Validateable

class TopicCO implements Validateable {
    String topicName
    Visibility visibility
    User createdBy

    static constraints = {

        topicName(nullable: false,unique:['createdBy'],blank: false)
        topicName(unique: 'createdBy')
        visibility(inList: Visibility.values().toList())
        createdBy(nullable: false)

    }
}
