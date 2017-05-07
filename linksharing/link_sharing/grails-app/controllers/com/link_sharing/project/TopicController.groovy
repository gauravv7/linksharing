package com.link_sharing.project

class TopicController {

    def index() { }

    def show(Long id) {
        Topic topic = Topic?.read(id)

        if(topic) {
            if (topic.visibility == Visibility.PUBLIC) {
                render "success public"
            } else if (topic.visibility == Visibility.PRIVATE) {
                if (Subscription?.findByCreatedByAndTopic(session.user, topic)) {
                    render "success private"
                }
                else {
                    flash.error = "Cannot access private topic"
                    log.info("Cannot access private topic")
                    redirect(controller:'login' , action:'index')
                }
            }
        } else {
            flash.error = "Topic does not exist!"
            log.info("Topic does not exist")
            redirect(controller:'login' , action:'index')
        }
    }

    def delete(Long id) {

        Topic topic = Topic.load(id)

        if(topic) {
            log.info("topic ${topic}")
            topic.delete(flush:true)
            render "success"
        }
        else {
            render "topic not found."
        }
    }

    def save() { }
}
