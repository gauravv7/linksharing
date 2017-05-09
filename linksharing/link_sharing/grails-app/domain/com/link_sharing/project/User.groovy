package com.link_sharing.project

import com.link_sharing.project.Topic as Topic
import com.link_sharing.project.Subscription as Subscription
import com.link_sharing.project.Resource as Resource
import com.link_sharing.project.ReadingItem as ReadingItem

class User {

    String email;
    String userName;
    String password;
    String firstName;
    String lastName;

    Byte[] photo;
    Boolean admin;
    Boolean active;
    Date dateCreated;
    Date lastUpdated;

    static hasMany = [
            topics : Topic,
            subscriptions: Subscription,
            resources: Resource,
            readingItems: ReadingItem
    ]

    static transients = ['fullName']

    static mapping = {
        photo(sqlType: 'longblob')
    }

    static constraints = {

        email(unique: true, email: true, blank: false)
        userName(unique: true, blank: false)
        password(minSize: 5, blank: false)
        firstName(blank: false)
        lastName(blank: false)
        photo(nullable: true)
        admin(nullable: true)
        active(nullable: true)

    }

    String getFullName() {
        [firstName, lastName].findAll { it }.join(' ')

    }

    String toString() {
        return this.userName
    }
}
