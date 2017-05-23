package com.link_sharing.project

import com.link_sharing.project.co.InviteCO
import com.link_sharing.project.co.SearchCO
import com.link_sharing.project.co.SubscriptionCO
import com.link_sharing.project.co.UserCO
import com.link_sharing.project.constants.Constants
import com.link_sharing.project.utils.EncryptUtils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class UserController {

    def mailService
    def fileUploadService
    def readingItemService
    def subscriptionService
    def topicService
    def userService
    def invitationService
    def resourceService

    def index(SearchCO searchCO) {
        params.max = 5
        params.offset = 0
        List unreadItems = readingItemService.findAllByUserAndIsRead(session.user, false, params)
        def unreadItemsCount = readingItemService.countByUserAndIsRead(session.user, false)
        def subscriptionCount = subscriptionService.countByCreatedBy(session.user)
        List trendingTopics = topicService.getTrendingTopics(params)
        def trendingTopicsCount = trendingTopics.size()
        def privatelySubscribedTopics = userService.getPrivatelySubscribedTopics(session.user.id?: 1)

        log.info("user id is from uc: $session.user.id")
        log.info("unread items\n$unreadItems")
        log.info("sbs topics \n${userService.getUserSubscribedTopics(session.user.id)}")

        log.info("user sbs \n${userService.getUserSubscriptions(session.user.id, params)}")
        log.info("user sbs size \n${subscriptionCount}")
        log.info("user trdntopic \n${trendingTopics}")
        log.info("user trdntopic size\n${trendingTopicsCount}")
        render view: 'dashboard', model: [
                listVisibility: Visibility.values().toList(),
                unreadItems: unreadItems,
                userSubscriptions: userService.getUserSubscriptions(session.user.id, params),
                subscriptionCount: subscriptionCount,
                trendingTopics: trendingTopics,
                trendingTopicsCount: trendingTopicsCount,
                unreadItemsCount: unreadItemsCount,
                privatelySubscribedTopics: privatelySubscribedTopics,
                subscribedTopics: userService.getUserSubscribedTopics(session.user.id)
        ]

    }


    def filterForTrendingTopics(){
        render(template:"/topic/trendingTopics" ,model:[ trendingTopics: topicService.getTrendingTopics(params)])
    }

    def filterForSubscriptions(){
        render(template:"/user/subscriptions" ,model:[ userSubscriptions: userService.getUserSubscriptions(session.user.id, params)])
    }


    def filterForInbox(){
        render(template:"/resource/post" ,model:[ unreadItems: readingItemService.findAllByUserAndIsRead(session.user, false, params)])
    }

    def invite(String code) {
        code = URLDecoder.decode(code, "UTF-8")
        log.info "$code"
        if(code){
            Invitation invitation = invitationService.findByUrlHash(code)
            log.info "$invitation"
            if(invitation){
                Topic topic = invitation.topic
                User user = userService.findByEmail(invitation.invited)
                log.info "user is $user ${invitation.invited}"
                SubscriptionCO subscriptionCO = new SubscriptionCO(createdBy: user, topic: topic,seriousness: Seriousness.CASUAL)
                if (subscriptionCO.validate()) {
                    subscriptionService.save(subscriptionCO)
                    invitationService.delete(invitation.id)
                    flash.message = "Subscription saved successfully"
                } else {
                    flash.error = subscriptionCO.errors.allErrors.collect { message(error: it) }.join(", ")
                }
            } else {
                flash.error = "invalid invitation request, invitation doesnot exist"
            }
        }
        render view: '/index'
    }

    def sendInvite(InviteCO inviteCO) {
        log.info("$inviteCO")
        User user = userService.findByEmail(inviteCO.email)
        if(user){
            Subscription subscription = subscriptionService.findByCreatedByAndTopic(user, inviteCO.topic)
            if(!subscription){

                String hashed = EncryptUtils.encryptSHA256("${session.user}${inviteCO.email}${inviteCO.topic}${Constants.SALT}" as String)

                Invitation invitation = new Invitation(invitee: session.user, invited: inviteCO.email, topic: Topic.load(inviteCO.topic), urlHash: hashed)
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

        List topics = topicService.findAllByCreatedByAndVisibility(user, Visibility.PUBLIC)
        def subscriptions = subscriptionService.findAllByCreatedBy(user)
        params.max = 5
        params.idForProfileFilter = user.id
        params.offset = 0
        def posts = resourceService.findAllByCreatedBy(user, params)
        def postsCount = resourceService.countByCreatedBy(user, params)

        log.info "subscribedTopics for profile $subscriptions"
        log.info "profile topics $topics"

        render view: 'profile', model: [
                user: user,
                subscribedTopics: userService.getUserSubscribedTopics(session.user.id),
                subscriptions: subscriptions,
                topics: topics,
                posts: posts,
                postsCount: postsCount
        ]
    }


    def filterForProfile(){
        render(template:"/topic/topicPost" ,model:[ posts: resourceService.findAllByCreatedBy(User.get(params.idForProfileFilter), params)])
    }

    def edit(){
        render view: 'editProfile'
    }

    def updateProfile(UserCO userCO){
        Map result = userService.updateProfile(userCO, session.user.id)
        if(!response.message.empty){
            flash[(response.success)?'message': 'error'] = response.message
        }
        redirect(url: request.getHeader("referer"))
    }

    def updatePassword(String password, String confirmPassword){
        if(session.user){
            if(password.equals(confirmPassword)){
                Map response = userService.updatePassword(session.user.id, password)
                if(!response.message.empty){
                    flash[(response.success)?'message': 'error'] = response.message
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

            Map response = userService.toggleActive(id)
            if(!response.message.empty){
                flash[(response.success)?'message': 'error'] = response.message
            }
        }
        redirect(url: request.getHeader("referer"))
    }
}
