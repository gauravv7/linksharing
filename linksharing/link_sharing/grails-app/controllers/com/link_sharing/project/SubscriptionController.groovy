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

    def update(Long topicId, String serious) {

        Topic topic = Topic?.get(topicId)
        User user = session.user
        Subscription subscription = Subscription.findByCreatedByAndTopic(user, topic)

        if (subscription) {
            //TODO: what is the purpose of this check???
            if (Seriousness.checkSeriousness(serious) != null) {
                subscription.seriousness = Seriousness.checkSeriousness(serious)

                if (subscription.save(flush: true)) {
                    render flash.message = "subscription saved successfully"

                } else {
                    render flash.error = "error saving subscription"
                }
            } else {
                render flash.error = "seriousness string is not correct, please try again"
            }
        } else {
            render flash.error = "subscription not found"
        }
    }

    //TODO :As per exercise it is supposed to take subscription id not topic

    def delete(Long topicId) {
        Topic topic = Topic.get(topicId)
        User user = session.user
        //TODO : what will happen if topic & user does not exist ????
        Subscription subscription = Subscription.findByCreatedByAndTopic(user, topic)
        if (subscription) {
            subscription.delete(flush: true)
            render flash.message = "Subscription deleted"
        } else {
            render flash.error = "Subscription not found"
        }
    }
}
