package com.link_sharing.project

class UserController {

    def index() {
        render "user dashboard for user ${session.user}"
    }
}
