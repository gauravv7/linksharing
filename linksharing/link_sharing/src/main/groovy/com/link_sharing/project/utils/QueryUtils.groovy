package com.link_sharing.project.utils

import com.link_sharing.project.Resource
import com.link_sharing.project.Subscription
import com.link_sharing.project.Topic
import com.link_sharing.project.User
import com.link_sharing.project.Visibility

/**
 * Created by gaurav on 16/5/17.
 */
class QueryUtils {
    static Boolean isUserAdmin(User user) {
        return user && user.isAdmin
    }

    static Boolean isUserActive(User user) {
        return user && user.isActive
    }

    static Boolean isCurrentUser(User user1, User user2) {
        return user1 && user2 && (user1.id == user2.id)
    }

    static Boolean ifUserExists(User user) {
        return (user && User.countByUserNameAndEmail(user.userName, user.password))
    }

    static Boolean isTopicPublic(Topic topic) {
        return topic && topic.visibility == Visibility.PUBLIC
    }

    static Boolean ifTopicCanbeViewdBy(Topic topic, User user) {
        return topic && (isTopicPublic(topic) || isUserAdmin(user) || isTopicSubscribedByUser(topic, user))
    }

    static Boolean ifTopicIsCreatedBy(Topic topic, User user) {
        return user && topic && (topic.createdBy.id == user.id)
    }

    static Boolean isResourceCreatedBy(Resource resource, User user) {
        return user && resource && (resource.createdBy.id == user.id)
    }

    static Boolean ifUserCanEditDeleteResource(Resource resource, User user) {
        return user && resource && (isUserAdmin(user) || isResourceCreatedBy(resource, user))
    }

    static Boolean ifResourceCanBeViewdBy(Resource resource, User user) {
        return user && resource && (ifTopicCanbeViewdBy(resource.topic, user))
    }

    static UUID uniqueFileName() {
        return UUID.randomUUID()
    }

    static Boolean isTopicSubscribedByUser(Topic topic, User user) {
        return user && topic && Subscription.findByCreatedByAndTopic(user, topic)
    }
}
