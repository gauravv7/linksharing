package com.link_sharing.project

class DashboardTagLib {

    static namespace = "dsh"

    static defaultEncodeAs = "raw"
//    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    def ifSubscribed = {
        attrs, body ->
            Long userId = attrs.userId
            Long topicId = attrs.topicId
            if(topicId && userId) {
                if(Subscription.findByTopicAndCreatedBy(Topic.load(topicId), User.load(userId))){
                    log.info "there is here"
                    out << body()
                } else {
                    log.info "there is there"
                }
            }
    }

    def subscribed = {
        attrs, body ->
            Long userId = attrs.userId
            Long topicId = attrs.topicId
            if(topicId && userId) {
                if(Subscription.findByTopicAndCreatedBy(Topic.load(topicId), User.load(userId))){
                    out << link(action:'delete',controller:'subscription', params: [topicId: topicId], absolute: true) { 'unsubscribe' }
                } else {
                    out << link(action:'save',controller:'subscription', params: [topicId: topicId]) { 'subscribe' }
                }
            }
    }

    def subscriptionCount = { attrs, body ->
        Long userId = attrs.userId
        Long topicId = attrs.topicId
        if (userId) {
            out << Subscription.countByCreatedBy(User.load(userId))
        }
        if (topicId) {
            out << Subscription.countByTopic(Topic.load(topicId))
        }
    }

    def postCount = { attrs, body ->
        Long topicId = attrs.topicId
        out << Resource.countByTopic(Topic.load(topicId))
    }

    def showInboxLinkOrDownload = {
        attrs, body ->
            Resource resource = attrs.resource
            log.info "showInboxLinkOrDownload $resource"
            if(resource){
                if(resource instanceof LinkResource ) {
                    LinkResource lr = (LinkResource)resource
                    out << "<a href='${lr.url}' target='_blank'>View Site</a>"
                } else if(resource instanceof DocumentResource){
                    DocumentResource dr = (DocumentResource)resource
                    out << link(action:'download',controller:'documentResource', params: [id: dr.id], absolute: true) { 'Download' }
                }
            }
    }

    def markAsReadUnRead = {
        attrs, body ->
            ReadingItem item = attrs.item
            log.info "mark as read unread $item"
            if(item){
                if(item.isRead){
                    out << link(controller: 'readingItem', action: 'changeIsRead', params: [id: item.id]) {'Mark as unread'}
                } else {
                    out << link(controller: 'readingItem', action: 'changeIsRead', params: [id: item.id]) {'Mark as read'}
                }
            }
    }

    def showIfAdminOrResourceCreatedBy = {
        attrs, body ->
            Resource r = Resource.load(attrs.resource)
            User u = session.user
            if(u && r){
                if(u.admin || r.createdBy.id==u.id){
                    log.info "resources equal"
                    log.info "$r.createdBy.id"
                    log.info "$u.id"
                    out << body()
                } else {
                    out << ""
                }
            }
    }

    def viewResource = {
        attrs, body ->
            Long rid = attrs.rid
            if(rid){
                out << "<a class=\"text-right\" style=\"display: block\" href=\"${createLink(controller: 'resource', action: 'show', params: [id: rid])}\">view resource</a>"
            }
    }

}
