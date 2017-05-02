package com.link_sharing.project

import com.link_sharing.project.User as User
import com.link_sharing.project.Topic as Topic


abstract class Resource {

    String description
    User createdBy
    Topic topic
    Date dateCreated
    Date lastUpdated

    static mapping = {
        description(type: 'text')
    }

    static belongsTo = [topic: Topic]

    static constraints = {
        description(blank: false)
    }

    String toString() {
        return "${description}"
    }
}
