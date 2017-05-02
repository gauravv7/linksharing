package com.link_sharing.project

import com.link_sharing.project.User as User
import com.link_sharing.project.Subscription as Subscription
import com.link_sharing.project.Resource as Resource

class Topic {

    String topicName;
    User createdBy;
    Date dateCreated;
    Date lastUpdated;
    Visibility visibility

    static belongsTo = [User]

    static hasMany = [
            subscriptions: Subscription,
            resources: Resource,
    ]

    static constraints = {
        topicName(blank: false, unique: ['createdBy'])
        visibility(inList: Visibility.values().toList())
    }

    String toString() {
        return "${topicName}, ${createdBy}"
    }
}
