package com.link_sharing.project

class SubscriptionController {

    def index() { }

    def save() {}

    def update() {}

    def delete(Long topicId) {
        Topic topic = Topic.get(topicId)
        User user = session.user
        Subscription subscription = Subscription.findByCreatedByAndTopic(user, topic)
        if (subscription) {
            subscription.delete(flush: true)
            render flash.message = "Subscription deleted"
        } else {
            render flash.error = "Subscription not found"
        }
    }
}
