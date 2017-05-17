package com.link_sharing.project

import com.link_sharing.project.co.ResourceSearchCO
import com.link_sharing.project.vo.RatingInfoVO

class ResourceController {

    def index() { }

    def show(Long id) {

        Resource resource = Resource.get(id)
        RatingInfoVO ratingInfoVO = resource.getRatingInfo()

        render view: 'show'
    }


    def delete(Long id) {

        Resource resource = Resource.load(id)

        log.info("resource ${resource}")

        if(resource) {
            resource.delete(flush:true)
            render "success"
        } else {
            render "Resource not found."
        }
    }

    def search(ResourceSearchCO resourceSearchCO) {
        def r
        if (resourceSearchCO.q) {
            resourceSearchCO.visibility = Visibility.PUBLIC
            r = Resource.search(resourceSearchCO).list()
        }

        render r
    }

}
