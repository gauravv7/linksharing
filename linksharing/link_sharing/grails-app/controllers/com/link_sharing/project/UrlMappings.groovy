package com.link_sharing.project

class UrlMappings {

    static mappings = {
        get "/login"(controller: 'login', action: 'index')
        name login: "/login"(controller: 'login', action: 'loginHandler', method: 'POST')
        get "/logout"(controller: 'login', action: 'logout')

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'login', action: 'index')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
