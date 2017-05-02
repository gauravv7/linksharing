package com.link_sharing.project

import com.link_sharing.project.Topic as Topic
import com.link_sharing.project.User as User
import com.link_sharing.project.Seriousness

class Subscription {

    Topic topic
    Seriousness seriousness
    User createdBy
    Date dateCreated
    Date lastUpdated

    static constraints = {
        topic(unique: ['createdBy'])
        seriousness(nullable: false)
        createdBy(nullable: false)
    }

    static belongsTo = [createdBy:User,
                        topic:Topic]

    String toString() {

        return "${createdBy} subscribed ${topic}"
    }
}
