package com.link_sharing.project

import com.link_sharing.project.constants.Constants
import com.link_sharing.project.utils.EncryptUtils
import com.link_sharing.project.vo.LoginVO
import com.link_sharing.project.vo.TopicVO

class LoginController {

    def mailService

    def index() {
        if (session?.user) {
            forward(controller: 'user', action: 'index')
        } else {
            render view: '/index'
        }
    }

    def loginHandler(LoginVO loginVO) {
        User user = User.findByUserNameAndPassword(loginVO.username, loginVO.password)

        if (user) {
            if (user.active) {
                session.user = user
                redirect(controller: 'login', action: 'index')
            } else {
                render flash.error = "User is not active"
            }
        } else {
            render flash.error = "User not found"
        }
    }

    def logout() {
        session.invalidate()
        redirect(action: 'index')
    }

    def register(String fname,String lname, String email, String pass, String confirm,String uname) {
        User user = new User(firstName: fname, lastName: lname, email: email, userName: uname,password: pass,confirmPassword: confirm)
        if(!user.save(flush:true)){
            flash.message='Cannot be registered'
            flash.errors = user.errors.allErrors.join(', ')
            render flash.errors
        } else{
            render flash.message='Successfully registered!'
        }
    }

    def forgotPassword() {
        render view: '/auth/forgotPassword'
    }

    def reset(String code) {
        code = URLDecoder.decode(code, "UTF-8")
        log.info "$code"
        if(code){
            ResetPassword resetPassword = ResetPassword.findByUrlHash(code)
            log.info "$resetPassword"
            if(resetPassword){
                User user = User.findByEmail(resetPassword.email)
                log.info "user is $user ${resetPassword.email}"
                if(user){
                    log.info "rendering view resetpassword"
                    render view: '/auth/resetPassword', model: [code: code]
                    return
                } else {
                    flash.error = "invalid request"
                }
            } else {
                flash.error = "invalid invitation request, invitation doesnot exist"
            }
        }
        render view: '/index'
    }

    def resetPasswordProcess(String code, String password, String confirmPassword) {
        if(code){
            ResetPassword resetPassword = ResetPassword.findByUrlHash(code)
            log.info "$resetPassword"
            if(resetPassword){
                User user = User.findByEmail(resetPassword.email)
                log.info "user is $user ${resetPassword.email}"
                if(user){
                    if(password.equals(confirmPassword)){
                        user.password = password
                        user.confirmPassword = confirmPassword
                        if(user.save(flush: true)){
                            flash.message = "Password has been reset"
                        } else{
                            flash.error = user.errors.allErrors.join(", ")
                            redirect(url: request.getHeader("referer"))
                            return
                        }
                        redirect(url: '/login')
                    } else {
                        flash.error = "passwords are not equal"
                        redirect(url: request.getHeader("referer"))
                    }
                } else {
                    flash.error = "invalid request"
                }
            } else {
                flash.error = "invalid invitation request, invitation doesnot exist"
            }
        }

        render view: '/index'
    }

    def resetPassword(String email){
        User user = User.findByEmail(email)
        if(email){
            if(user) {
                String hashed = EncryptUtils.encryptSHA256("${email}${Constants.SALT}" as String)
                ResetPassword rpass = new ResetPassword(email: email, urlHash: hashed)
                if(rpass.validate()){
                    String text1 = createLink(controller: 'login', action: 'reset', params: [code: hashed], absolute: true)
                    log.info text1
                    mailService.sendMail {
                        to email
                        from "csi.online2016@gmail.com"
                        subject 'linksharing: reset password'
                        text text1
                    }
                    rpass.save(flush: true)
                    flash.message = "a mail has been sent to the provided email Id containing the link to reset password"
                } else{
                    flash.error = rpass.errors.allErrors.join(", ")
                }
            } else {
                flash.error = "No user exists with email id: $email"
            }
        } else {
            flash.error = "please enter an email"
        }
        redirect(url: request.getHeader("referer"))
    }
}
