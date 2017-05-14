package com.link_sharing.project

import com.link_sharing.project.co.InviteCO
import com.link_sharing.project.co.SearchCO
import com.link_sharing.project.constants.Constants
import com.link_sharing.project.utils.EncryptUtils

class UserController {

    def mailService

    def index(SearchCO searchCO) {
        List l = User.getUnReadItems(session.user, searchCO)
        log.info("search items\n$l")
            log.info("search items\n${session.user?.getSubscribedTopics()}")
        render view: 'dashboard'

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
