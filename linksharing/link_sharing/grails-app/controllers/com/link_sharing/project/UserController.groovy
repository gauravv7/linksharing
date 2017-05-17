package com.link_sharing.project

import com.link_sharing.project.co.InviteCO
import com.link_sharing.project.co.SearchCO
import com.link_sharing.project.co.UserCO
import com.link_sharing.project.constants.Constants
import com.link_sharing.project.utils.EncryptUtils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class UserController {

    def mailService
    def fileUploadService

    def index(SearchCO searchCO) {
        List unreadItems = ReadingItem.findAllByUserAndIsRead(session.user, false, [sort: "dateCreated", order: "desc"])
        List tt = Topic.getTrendingTopics()
        log.info("user id is from uc: $session.user.id")
        log.info("unread items\n$unreadItems")
        log.info("sbs topics \n${session.user?.getSubscribedTopics()}")
        log.info("user sbs \n${session.user?.userSubscriptions()}")
        log.info("user sbs size \n${session.user?.userSubscriptions().size()}")
        log.info("user trdntopic \n${tt}")
        def subscriptionCount = Subscription.countByCreatedBy(session.user)
        def topicCount = Topic.countByCreatedBy(session.user)
        render view: 'dashboard', model: [
                trendingTopics: Topic.getTrendingTopics(),
                listVisibility: Visibility.values().toList(),
                unreadItems: unreadItems,
                userSubscriptions: session.user?.userSubscriptions(),
                subscriptions: subscriptionCount,
                topicCount: topicCount,
                trendingTopics: tt,
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
        User user = User.findByEmail(inviteCO.email)
        if(user){
            Subscription subscription = Subscription.findByCreatedByAndTopic(user, Topic.load(inviteCO.topic))
            if(!subscription){

                String hashed = EncryptUtils.encryptSHA256("${session.user}${inviteCO.email}${inviteCO.topic}${Constants.SALT}" as String)

                Invitation invitation = new Invitation(invitee: session.user, invited: inviteCO.email, topic: inviteCO.topic, urlHash: hashed)
                if(invitation.validate()){
                    String text1 = createLink(controller: 'user', action: 'invite', params: [code: hashed], absolute: true)
                    log.info text1
                    runAsync {
                        mailService.sendMail {
                            to inviteCO.email
                            from "csi.online2016@gmail.com"
                            subject 'linksharing: invitation'
                            text text1
                        }
                    }
                    invitation.save(flush: true)
                    flash.message = "Invite sent successfully"
                } else{
                    flash.error = invitation.errors.allErrors.join(', ')
                }
            } else{
                flash.error = "user already subscribed to that topic"
            }
        } else{
            flash.error = "invitation not sent, user doesn't exists with given email"
        }


        redirect(url: request.getHeader("referer"))
    }

    def getImage(){
        def path = params.filepath
        //returns an image to display
        File f = new File(Constants.LOC_PHOTO_RESOURCE +"/"+path)
        BufferedImage originalImage = ImageIO.read(f);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        def fileext = path.substring(path.indexOf(".")+1, path.length())

        ImageIO.write( originalImage, fileext, baos );
        baos.flush();

        byte[] img = baos.toByteArray();
        baos.close();
        response.setHeader('Content-length', img.length.toString())
        response.contentType = "image/"+fileext // or the appropriate image content type
        response.outputStream << img
        response.outputStream.flush()
    }

    def profile(Long id){
        User user = null
        if(id){
            user = User.get(id)
        } else if(session.user) {
            user = session.user
        }

        List topics = Topic.findAllByCreatedByAndVisibility(user, Visibility.PUBLIC)
        def subscribedTopics = Subscription.findAllByCreatedBy(user)
        def subscriptionCount = Subscription.countByCreatedBy(user)
        def topicCount = Topic.countByCreatedBy(user)
        def posts = Resource.findAllByCreatedBy(user, [max: 5, sort:'id', order: 'desc'])

        log.info "subscribedTopics for profile $subscribedTopics"
        log.info "profile topics $topics"

        render view: 'profile', model: [
                user: user,
                subscriptions: subscriptionCount,
                topicCount: topicCount,
                subscribedTopics: subscribedTopics,
                topics: topics,
                posts: posts
        ]
    }

    def edit(){
        render view: 'editProfile'
    }

    def updateProfile(UserCO userCO){
        User user = User.get(session.user.id)
        user.firstName = userCO.firstName
        user.lastName = userCO.lastName
        user.userName = userCO.userName
        user.password = user.password
        user.confirmPassword = user.password

        if(userCO.photograph.bytes.length){
            String finalFileName = fileUploadService.getUniqueFileName(userCO.photograph)

            if( fileUploadService.uploadFile(userCO.photograph, finalFileName, Constants.LOC_PHOTO_RESOURCE) ){
                user.photo = finalFileName
            } else{
                flash.error = "error while uploading photograph, try again"
            }
        }
        if(user.save(flush: true)){
            flash.message = "user updated"
        } else{
            flash.error = user.errors.allErrors.join(", ")
        }
        redirect(url: request.getHeader("referer"))
    }

    def updatePassword(String password, String confirmPassword){
        User user = User.get(session.user.id)
        if(user){
            if(password.equals(confirmPassword)){
                user.password = password
                user.confirmPassword = confirmPassword
                if(user.save(flush: true)){
                    flash.message = "Password has been reset"
                } else{
                    flash.error = user.errors.allErrors.join(", ")
                }
            } else {
                flash.error = "passwords are not equal"
            }
        } else {
            flash.error = "invalid request"
        }
        redirect(url: request.getHeader("referer"))
    }

    def all(){
        render view: '/admin_index', model: [users: User.list(params), userCount: User.count()]
    }

    def activate(Long id){

        if(id){

            User user = User.get(id)
            if(user){
                user.active = !user.active
                user.password = user.password
                user.confirmPassword = user.password
                if(user.save(flush: true)){
                    flash.message = "user activation updated"
                } else{
                    flash.error = user.errors.allErrors.join(', ')
                }
            } else{
                flash.error = "user not found"
            }
        }
        redirect(url: request.getHeader("referer"))
    }
}
