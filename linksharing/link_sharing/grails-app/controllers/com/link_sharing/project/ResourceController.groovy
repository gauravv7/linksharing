package com.link_sharing.project

import com.link_sharing.project.co.ResourceSearchCO
import com.link_sharing.project.vo.RatingInfoVO

class ResourceController {

    def index() { }

    def show(Long id) {

        Resource resource = Resource.get(id)
        RatingInfoVO ratingInfoVO = null
        if(resource){
            log.info "here in resource"
            ratingInfoVO = resource.getRatingInfo()
        } else{
            flash.error = "resource not found"
            redirect(url: request.getHeader("referer"))
        }
        log.info "ratingInfo $ratingInfoVO"
        render view: 'show', model: [
                resource: resource,
                ratingInfo: ratingInfoVO
        ]
    }


    def delete(Long id) {


        if(session.user){
            Resource resource = Resource.get(id)
            log.info("resource ${resource}")
            if(resource) {
                if(session.user.admin || resource.createdBy.id == session.user.id) {
                    resource.delete(flush: true)
                    flash.message = "resource deleted"
                    redirect(controller: 'user', action: 'index')
                    return
                } else{
                    flash.error = "unauthorized request"
                }
            } else {
                flash.error = "Resource not found."
            }
        } else{
            flash.error = "please login to continue"
        }
        redirect(action: 'show', id: id)
    }

    def updateRating(Long id, Integer score) {

        String msg = ""

        if(session.user){

            if(id && score){
                Resource resource = Resource.load(id)
                if(session.user.admin || resource.createdBy.id == session.user.id) {
                    ResourceRating rating = ResourceRating.findOrCreateByCreatedByAndResource(session.user, resource)
                    //findOrCreateBy bcoz, rating doesn't exists for 1sst time
                    rating.score = score
                    if (rating.save(flush: true)) {
                        msg = "resource rating updated!"
                    } else {
                        msg = rating.errors.allErrors.join(', ')
                    }
                } else {
                    flash.error = "unauthorized request"
                }
            } else{
                msg = "invalid arguments"
//                flash.error = "invalid arguments"
            }

        } else {
            msg = "please login to continue"
//            flash.error = "please login to continue"
        }
        render msg
    }

    def edit(Long id, String description){
        Resource resource = Resource.load(id)

        log.info("resource ${resource}")
        if(session.user){
            if(resource) {
                resource.description = description
                if(resource.save(flush:true)){
                    flash.message = "success"
                } else{
                    flash.error = "error while updating, please try again"
                }
            } else {
                flash.error = "Resource not found."
            }
        } else{
            flash.error = "please login to continue"
        }

        redirect(url: request.getHeader("referer"))
    }

    def search(ResourceSearchCO co) {
        List searchPosts = []
        if (co.q && !co.topicID) {
            co.visibility = Visibility.PUBLIC
        }
        List<Resource> resources = Resource.search(co).list()

        resources?.each {
            searchPosts.add(ReadingItem.findByResource(it))
        }

        render(view: 'search', model: [
                topPosts : null,
                trendingTopics: null,
                posts : searchPosts
        ])

    }

}
