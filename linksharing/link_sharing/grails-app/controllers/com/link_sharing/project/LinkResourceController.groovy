package com.link_sharing.project

import com.link_sharing.project.co.LinkResourceCO
import com.link_sharing.project.co.ResourceSearchCO
import com.link_sharing.project.vo.RatingInfoVO
import com.link_sharing.project.vo.TopicVO

class LinkResourceController {

    def save(LinkResourceCO linkResourceCO) {
        log.info("${linkResourceCO}")
        LinkResource linkResource = new LinkResource(url: linkResourceCO.url, description: linkResourceCO.description, topic: Topic.get(linkResourceCO.topic), createdBy: session.user)
//        linkResource.createdBy = session.user
        if (linkResource.validate()) {
            if (linkResource) {
                List<User> subscribedUsers = linkResource.topic.subscribedUsers()
                subscribedUsers.each {
                    if (it.id != session.user.id) {
                        linkResource.addToReadingItems(new ReadingItem(
                                user: it,
                                resource: linkResource,
                                isRead: false)
                        )
                    } else{
                        linkResource.addToReadingItems(new ReadingItem(
                                user: it,
                                resource: linkResource,
                                isRead: true)
                        )
                    }
                }
                if(linkResource.save(flush: true)){
                    flash.message = "resource saved"
                } else{
                    flash.error = linkResource.error.allErrors.join(', ')
                }
            } else {
                flash.error = "resource not saved"
            }
        } else{
            flash.error = "Error occured"+linkResource.errors.allErrors.collect().join(", ")
        }

        redirect(url: request.getHeader("referer"))
    }

}
