package com.link_sharing.project

import com.link_sharing.project.Topic as Topic
import com.link_sharing.project.Subscription as Subscription
import com.link_sharing.project.Resource as Resource
import com.link_sharing.project.ReadingItem as ReadingItem
import com.link_sharing.project.co.SearchCO
import com.link_sharing.project.vo.TopicVO
import org.hibernate.criterion.CriteriaSpecification

class User {

    String email;
    String userName;
    String password;
    String firstName;
    String lastName;

    String photo;
    Boolean admin;
    Boolean active;
    Date dateCreated;
    Date lastUpdated;
    String confirmPassword

    static hasMany = [
            topics : Topic,
            subscriptions: Subscription,
            resources: Resource,
            readingItems: ReadingItem
    ]

    static transients = ['fullName', 'confirmPassword']

    static mapping = {
        id(sort: 'desc')
    }

    static constraints = {

        email(unique: true, email: true, blank: false)
        userName(unique: true, blank: false)
        password(minSize: 5, blank: false, validator: {password, obj ->
            def password2 = obj.confirmPassword
            password2 == password ? true : ['password.mismatch']
        })
        firstName(blank: false)
        lastName(blank: false)
        photo(nullable: true)
        admin(nullable: true)
        active(nullable: true)
        confirmPassword(bindable: true, nullable: true, blank: true)

    }




    String getFullName() {
        [firstName, lastName].findAll { it }.join(' ')

    }

    String toString() {
        return this.userName
    }
}
