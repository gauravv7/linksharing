package com.link_sharing.project


class LoginCheckInterceptor {

    public LoginCheckInterceptor(){
        matchAll().excludes(controller:"login")
    }
    boolean before() {
        if (!session.user) {
            log.info("redirecting")
            redirect(controller: 'login', action: 'index')
            return false
        }
        true
    }

    void afterView() {
        // no-op
    }
}
