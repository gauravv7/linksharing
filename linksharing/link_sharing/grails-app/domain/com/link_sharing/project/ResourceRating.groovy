package com.link_sharing.project

import com.link_sharing.project.Resource as Resource
import com.link_sharing.project.User as User

class ResourceRating {

    Resource resource
    User createdBy
    Integer score
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
            createdBy:User,
            resource:Resource
    ]

    static constraints = {
        resource(unique: ['createdBy'], nullable: false)
        createdBy(nullable: false)
        score(min: 1, max: 5, nullable: false)
    }


    String toString() {
        return "${createdBy} rated ${resource} by ${score}"
    }
}
