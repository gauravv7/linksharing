package com.link_sharing.project


class ApplicationInterceptor {

    public ApplicationInterceptor() {
        matchAll()
    }

    boolean before() {
        log.info "controller: $controllerName, action:$actionName -> $params"
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
