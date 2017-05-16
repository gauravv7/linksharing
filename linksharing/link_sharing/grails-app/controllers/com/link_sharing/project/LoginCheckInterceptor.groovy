package com.link_sharing.project


class LoginCheckInterceptor {

    public LoginCheckInterceptor(){
        matchAll().excludes(controller:"login")
    }
    boolean before() {
        if(controllerName.equals("resource") && actionName.equals("show") && params.id){
            if(Resource.get(params.id).topic.visibility==Visibility.PUBLIC){
                return true
            }
        }
        if(controllerName.equals("resource") && actionName.equals("updateRating") && !session.user){
            return "please login to continue"
        }
        if(controllerName.equals("resource") && actionName.equals("search") && !session.user){
            return true
        }
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
