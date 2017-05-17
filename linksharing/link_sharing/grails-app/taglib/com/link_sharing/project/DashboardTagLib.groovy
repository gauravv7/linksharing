package com.link_sharing.project

import com.link_sharing.project.constants.Constants

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
            log.info "from subsribed tl $userId"
            log.info "from subsribed tl $topicId"
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
            ReadingItem item = (attrs.item instanceof ReadingItem)? attrs.item: ReadingItem.findByResourceAndUser(attrs.item, User.load(attrs.item.createdBy.id))
            log.info "mark as read unread $item"
            if(item){
                if(item.isRead){
                    out << link(controller: 'readingItem', action: 'changeIsRead', params: [id: item.id]) {'Mark as unread'}
                } else {
                    out << link(controller: 'readingItem', action: 'changeIsRead', params: [id: item.id]) {'Mark as read'}
                }
            }
    }

    def showUserNameLink = {
        attrs, body ->
            User user = User.load(attrs.id)
            if(user){
                out << link(controller: 'user', action: 'profile', params: [id: user.id]) {user.userName}
            }
    }

    def showTopicNameLink = {
        attrs, body ->
            Topic topic = Topic.load(attrs.id)
            if(topic){
                out << link(controller: 'topic', action: 'show', params: [id: topic.id]) {topic.topicName}
            }
    }

    def markAsReadUnReadForTopicShow = {
        attrs, body ->
            ReadingItem item = ReadingItem.findByResourceAndUser(attrs.item, User.load(attrs.item.createdBy.id))
            log.info "mark as read unread $item"
            if(item){
                if(item.isRead){
                    out << link(controller: 'readingItem', action: 'changeIsRead', params: [id: item.id]) {'Mark as unread'}
                } else {
                    out << link(controller: 'readingItem', action: 'changeIsRead', params: [id: item.id]) {'Mark as read'}
                }
            }
    }

    def showActivateDeactiveAction = {
        attrs, body ->
            Long id = attrs.id
            User user = User.get(id)
            if(user){
                if(!user.active){
                    out << link(controller: 'user', action: 'activate', params: [id: id]) {'Activate'}
                } else {
                    out << link(controller: 'user', action: 'activate', params: [id: id]) {'Deactive'}
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

    def showProfilePic = {
        attrs, body ->
            String sc = attrs.styleClasses
            String filepath = attrs.filepath.trim()
            if(filepath.equals("null")){
                filepath = Constants.DEFAULT_USER_PHOTO
            }
            out << "<img class=\"$sc\" style=\"margin: 5px 0; border-radius: 5px;\" src=\"${createLink(controller: 'user', action: 'getImage', params: [filepath: filepath ])}\"/>"
    }

}
