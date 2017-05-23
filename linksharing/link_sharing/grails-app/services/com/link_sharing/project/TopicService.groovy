package com.link_sharing.project

import com.link_sharing.project.dto.TopicDTO
import grails.transaction.Transactional

@Transactional
class TopicService {

    def getTrendingTopics(def params){
        return TopicDTO.getTrendingTopics(params)
    }

    def findAllByCreatedByAndVisibility(user, Visibility visibility){
        return Topic.findAllByCreatedByAndVisibility(user, visibility)
    }

    def subscribedUsers(Topic topic){
        return TopicDTO.subscribedUsers(topic)
    }

}
