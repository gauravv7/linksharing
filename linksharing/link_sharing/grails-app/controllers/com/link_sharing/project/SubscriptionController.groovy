package com.link_sharing.project

class SubscriptionController {

    def index() { }

    def save(Long topicId) {
        Topic topic = Topic.get(topicId)
        if (topic) {
            User user = session.user
            Subscription subscription = new Subscription(createdBy: user, topic: topic,seriousness: Seriousness.CASUAL)
            if (subscription.save(flush: true)) {
                render flash.message = "Subscription saved successfully"
            } else {
                render flash.error = subscription.errors.allErrors.collect { message(error: it) }.join(", ")
            }
        } else {
            flash.error = "Topic Does not Exist"
        }
    }

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
