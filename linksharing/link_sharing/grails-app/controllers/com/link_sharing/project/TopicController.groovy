package com.link_sharing.project

import com.link_sharing.project.Visibility
import com.link_sharing.project.co.ResourceSearchCO
import com.link_sharing.project.co.TopicCO

class TopicController {

    def index() { }

    def show(Long id, ResourceSearchCO co) {
        Topic topic = Topic?.read(id)
        params.max = 5
        params.offset = 0

        if(topic) {
            List subscribedUsers = topic.subscribedUsers()
            List posts = Resource.findAllByTopicAndCreatedBy(topic, topic.createdBy, params)
            def postsCount = Resource.countByTopicAndCreatedBy(topic, topic.createdBy)
            log.info "subscribedUsers: $subscribedUsers"
            Subscription seriousness = Subscription.findByTopicAndCreatedBy(topic, topic.createdBy)
            if (topic.visibility == Visibility.PUBLIC) {
                render view: 'show', model: [
                        subscribedUsers: subscribedUsers, topic: topic, posts: posts, subscription: seriousness, postsCount: postsCount
                ]
            } else if (topic.visibility == Visibility.PRIVATE) {
                if (Subscription?.findByCreatedByAndTopic(session.user, topic)) {
                    render view: 'show'
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

    def filterForPosts(){
        Topic topic = Topic?.read(id)
        render(template:"/topic/trendingTopics" ,model:[ posts: Resource.findAllByTopicAndCreatedBy(topic, topic.createdBy, params)])
    }

    def remove(Long topicId) {
        if(session.user){
            Topic topic = Topic.load(topicId)
            if (topic) {
                log.info "topic createdBy id $topic.createdBy.id"
                log.info "user session id $session.user.id"
                if(topic.createdBy.id == session.user.id){
                    topic.delete(flush: true)
                    flash.message = "Topic deleted"
                }
            } else {
                flash.error = "Topic not found"
            }
        } else{
            flash.error = "user not logged in"
        }

        redirect(url: request.getHeader("referer"))
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

    def updateVisibility(Long topicId, String visibility){
        log.info "$topicId"
        log.info "$visibility"
        if(session.user){
            Topic topic = Topic.load(topicId)
            topic.visibility = Visibility.checkVisibility(visibility)
            if(topic.save(flush: true)){
                flash.message = "visibility updated"
            } else{
                flash.error = topic.errors.allErrors.join(", ")
            }
        } else{
            flash.error = "user not logged in"
        }
        redirect(url: request.getHeader("referer"))
    }
}
