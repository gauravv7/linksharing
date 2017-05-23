package com.link_sharing.project

import com.link_sharing.project.dto.ResourceRatingDTO
import grails.transaction.Transactional

@Transactional
class ResourceRatingService {

    def getTopPosts() {
        return ResourceRatingDTO.getTopPosts()
    }

    def getRatingInfo(Long resourceId){
        return ResourceRatingDTO.getRatingInfo(resourceId)
    }
}
