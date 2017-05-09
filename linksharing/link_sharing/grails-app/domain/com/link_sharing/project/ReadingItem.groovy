package com.link_sharing.project

import com.link_sharing.project.User as User
import com.link_sharing.project.Resource as Resource

class ReadingItem {

    boolean isRead
    Resource resource
    User user
    Date dateCreated
    Date lastUpdated

    static constraints = {
        resource(unique: ['user'])
    }

    static belongsTo = [
            resource: Resource,
            user    : User
    ]

    String toString() {
        return "${user} read the ${resource}: ${isRead}"
    }
}
