package com.link_sharing.project

import com.link_sharing.project.Visibility

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
//TODO :Please take care of indentation & code formatting

    def save(String topicName,String visibility) {

        if(session.user) {

            Topic topic = new Topic(topicName: topicName, createdBy: session.user, visibility: Visibility.checkVisibility(visibility.trim().toLowerCase()))
            //TODO: have already assigned createdBy above, why again?
            topic.createdBy = session.user

            log.info("topic: ${topic}")
            topic.save(flush:true)

            if(topic.hasErrors()) {
                flash.error = topic.errors.allErrors.join(", ")
                render([error: 'an error occurred'])
            } else {
                flash.message = "success"
                render "success"
            } // hasErrors
        } // session user
    } // save
}
