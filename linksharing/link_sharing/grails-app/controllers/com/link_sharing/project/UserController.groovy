package com.link_sharing.project

import com.link_sharing.project.co.SearchCO

class UserController {

    def index(SearchCO searchCO) {
        List l = User.getUnReadItems(session.user, searchCO)
        log.info("search items\n$l")
            log.info("search items\n${session.user?.getSubscribedTopics()}")
        render view: 'dashboard'

    }
}
