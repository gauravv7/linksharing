package com.link_sharing.project


class ApplicationInterceptor {

    public ApplicationInterceptor() {
        matchAll()
    }

    boolean before() {

        //TODO: Simplify this using groovy truth

        if(session.user==null) {
            log.info("guest user")
        } else if (session.user.admin==true) {
            log.info("admin user")
        } else if(session.user.admin == false) {
            log.info("normal user")
        }
        log.info "controller: $controllerName, action:$actionName -> $params"
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
