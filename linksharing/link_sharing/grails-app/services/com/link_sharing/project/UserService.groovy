package com.link_sharing.project

import com.link_sharing.project.co.UserCO
import com.link_sharing.project.constants.Constants
import com.link_sharing.project.dto.UserDTO
import grails.transaction.Transactional

@Transactional
class UserService {

    def fileUploadService

    def findByUserNameAndPassword(String username, String password) {
       return User.findByUserNameAndPassword(username, password)
    }

    def getUserSubscriptions(Long id, Map params){
        return UserDTO.userSubscriptions(id, params)
    }

    def findByEmail(String email){
        return User.findByEmail(email)
    }

    def getUserSubscribedTopics(Long userID){
        return new UserDTO().getSubscribedTopics(userID)
    }

    def updateProfile(UserCO userCO, Long userID){
        User user = User.get(userID)
        user.firstName = userCO.firstName
        user.lastName = userCO.lastName
        user.userName = userCO.userName
        user.password = user.password
        user.confirmPassword = user.password

        Map result = initResponse()

        if(userCO.photograph.bytes.length){
            String finalFileName = fileUploadService.getUniqueFileName(userCO.photograph)

            if( fileUploadService.uploadFile(userCO.photograph, finalFileName, Constants.LOC_PHOTO_RESOURCE) ){
                user.photo = finalFileName
            } else{
                result.put('success', false)
                result.put('message', "error while uploading photograph, try again")
            }
        }

        if(user.save(flush: true)){
            result.put('success', true)
            result.put('message', "user details updated")
        } else{
            result.put('success', false)
            result.put('message',  user.errors.allErrors.join(', '))
        }

        return result
    }

    def updatePassword(Long userID, String password){
        User user = User.get(userID)
        Map result = initResponse()

        user.password = password
        user.confirmPassword = password

        if(user.save(flush: true)){
            result.success = true
            result.message = "Password has been reset"
        } else{
            result.success = false
            result.message = user.errors.allErrors.join(", ")
        }

        return result
    }

    def toggleActive(Long userID){
        User user = User.get(userID)
        Map result = initResponse()
        if(user){
            user.active = !user.active
            user.password = user.password
            user.confirmPassword = user.password
            if(user.save(flush: true)){
                result.success = true
                result.message = "user activation updated"
            } else{
                result.success = false
                flash.message = user.errors.allErrors.join(', ')
            }
        } else{
            result.message = "user not found"
        }

        return result
    }

    def getPrivatelySubscribedTopics(Long userId) {
        return new UserDTO().getPrivatelySubscribedTopics(userId)
    }

    def initResponse(){
        return [success: false, message: ""]
    }


}
