package com.link_sharing.project

import com.link_sharing.project.Visibility
import com.link_sharing.project.co.ResourceSearchCO
import com.link_sharing.project.co.TopicCO

class TopicController {

    def index() { }

    def show(Long id, ResourceSearchCO co) {
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

    def save(TopicCO topicCO) {

        if(session.user) {
            topicCO.createdBy = session.user
            log.info("$topicCO.visibility")
            log.info("${Visibility.values().toList()}")
            Topic topic = new Topic(topicName: topicCO.topicName, visibility: topicCO.visibility, createdBy: topicCO.createdBy)
            log.info("topic: ${topic}")
            topic.save(flush:true)

            if(topic.hasErrors()) {
                flash.error = "Error Occured\n" + topic.errors.allErrors.join(", ")
            } else {
                flash.message = "success"
            } // hasErrors
        } else {
            flash.error = "Unauthorized Action, no user logged in"
        } // session user
        redirect(controller: "user", action:"index")
    } // save

    def email() {}
}
