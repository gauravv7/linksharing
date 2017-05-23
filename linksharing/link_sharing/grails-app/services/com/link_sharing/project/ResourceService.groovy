package com.link_sharing.project

import com.link_sharing.project.dto.ResourceDTO
import grails.transaction.Transactional

@Transactional
class ResourceService {

    def getRecentShares() {
        return ResourceDTO.recentPosts()
    }

    def getTopPosts(){
        return ResourceDTO.getTopPosts()
    }

    def findAllByCreatedBy(User user, Map params){
        return Resource.findAllByCreatedBy(user, params)
    }

    def countByCreatedBy(User user, Map params){
        return Resource.countByCreatedBy(user, params)
    }

    def recentPosts(){
        return ResourceDTO.recentPosts()
    }

    def findAllByTopicAndCreatedBy(Topic topic, User createdBy, Map params){
        return Resource.findAllByTopicAndCreatedBy(topic, createdBy, params)
    }

    def countByTopicAndCreatedBy(Topic topic, User createdBy){
        return Resource.countByTopicAndCreatedBy(topic, createdBy)
    }
}
