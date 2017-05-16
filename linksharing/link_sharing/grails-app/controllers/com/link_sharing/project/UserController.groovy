package com.link_sharing.project

import com.link_sharing.project.co.InviteCO
import com.link_sharing.project.co.SearchCO
import com.link_sharing.project.co.UserCO
import com.link_sharing.project.constants.Constants
import com.link_sharing.project.utils.EncryptUtils

class UserController {

    def mailService

    def index(SearchCO searchCO) {
        List unreadItems = ReadingItem.findAllByUserAndIsRead(session.user, false, [sort: "dateCreated", order: "desc"])
        log.info("user id is from uc: $session.user.id")
        log.info("unread items\n$unreadItems")
        log.info("sbs topics \n${session.user?.getSubscribedTopics()}")
        log.info("user sbs \n${session.user?.userSubscriptions()}")
        log.info("user sbs size \n${session.user?.userSubscriptions().size()}")
        log.info("user trdntopic \n${Topic.getTrendingTopics()}")
        def subscriptionCount = Subscription.countByCreatedBy(session.user)
        def topicCount = Topic.countByCreatedBy(session.user)
        render view: 'dashboard', model: [
                trendingTopics: Topic.getTrendingTopics(),
                listVisibility: Visibility.values().toList(),
                unreadItems: unreadItems,
                userSubscriptions: session.user?.userSubscriptions(),
                subscriptions: subscriptionCount,
                topicCount: topicCount,
                subscribedTopics: session.user?.getSubscribedTopics()
        ]

    }

    def invite(String code) {
        code = URLDecoder.decode(code, "UTF-8")
        log.info "$code"
        if(code){
            Invitation invitation = Invitation.findByUrlHash(code)
            log.info "$invitation"
            if(invitation){
                Topic topic = invitation.topic
                User user = User.findByEmail(invitation.invited)
                log.info "user is $user ${invitation.invited}"
                Subscription subscription = new Subscription(createdBy: user, topic: topic,seriousness: Seriousness.CASUAL)
                if (subscription.save(flush: true)) {
                    invitation.delete()
                    flash.message = "Subscription saved successfully"
                } else {
                    flash.error = subscription.errors.allErrors.collect { message(error: it) }.join(", ")
                }
            } else {
                flash.error = "invalid invitation request, invitation doesnot exist"
            }
        }
        render view: '/index'
    }

    def sendInvite(InviteCO inviteCO) {
        log.info("$inviteCO")

        String hashed = EncryptUtils.encryptSHA256("${session.user}${inviteCO.email}${inviteCO.topic}${Constants.SALT}" as String)

        Invitation invitation = new Invitation(invitee: session.user, invited: inviteCO.email, topic: inviteCO.topic, urlHash: hashed)
        if(invitation.validate()){
            String text1 = createLink(controller: 'user', action: 'invite', params: [code: hashed], absolute: true)
            log.info text1
            mailService.sendMail {
                to inviteCO.email
                from "csi.online2016@gmail.com"
                subject 'linksharing: invitation'
                text text1
            }
            invitation.save(flush: true)
            flash.message = "Invite sent successsfully"
        } else{
            flash.error = invitation.errors.allErrors.join(', ')
        }
        redirect(url: request.getHeader("referer"))
    }

}
