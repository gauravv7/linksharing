package com.link_sharing.project

class SubscriptionController {

    def index() { }

    def save(Long topicId) {
        Topic topic = Topic.get(topicId)
        if (topic) {
            User user = session.user
            Subscription subscription = new Subscription(createdBy: user, topic: topic,seriousness: Seriousness.CASUAL)
            if (subscription.save(flush: true)) {
                flash.message = "Subscription saved successfully"
            } else {
                flash.error = subscription.errors.allErrors.collect { message(error: it) }.join(", ")
            }
        } else {
            flash.error = "Topic Does not Exist"
        }

        redirect(url: request.getHeader("referer"))
    }

    def update(Long topicId, String serious) {

        Topic topic = Topic?.get(topicId)
        User user = session.user
        Subscription subscription = Subscription.findByCreatedByAndTopic(user, topic)

        if (subscription) {
            if (Seriousness.checkSeriousness(serious) != null) {
                subscription.seriousness = Seriousness.checkSeriousness(serious)

                if (subscription.save(flush: true)) {
                    flash.message = "subscription saved successfully"

                } else {
                    flash.error = "error saving subscription"
                }
            } else {
                flash.error = "seriousness string is not correct, please try again"
            }
        } else {
            flash.error = "subscription not found"
        }

        redirect(url: request.getHeader("referer"))
    }

    def delete(Long topicId) {
        Topic topic = Topic.get(topicId)
        User user = session.user
        Subscription subscription = Subscription.findByCreatedByAndTopic(user, topic)
        if (subscription) {
            subscription.delete(flush: true)
            flash.message = "Subscription deleted"
        } else {
            flash.error = "Subscription not found"
        }

        redirect(url: request.getHeader("referer"))
    }

    def updateSeriousness(Long sid, String seriousness){
        log.info "$sid"
        log.info "$seriousness"
        if(session.user){
            Subscription subscription = Subscription.load(sid)
            subscription.seriousness = Seriousness.checkSeriousness(seriousness)
            if(subscription.save(flush: true)){
                flash.message = "seriousness updated"
            } else{
                flash.error = subscription.errors.allErrors.join(", ")
            }
        } else{
            flash.error = "user not logged in"
        }
        redirect(url: request.getHeader("referer"))
    }
}
