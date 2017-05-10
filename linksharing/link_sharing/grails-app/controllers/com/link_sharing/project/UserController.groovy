package com.link_sharing.project

import com.link_sharing.project.co.SearchCO

class UserController {

    def index(SearchCO searchCO) {
        List l = User.getUnReadItems(session.user, searchCO)
        log.info("$l")
        render l?: "user dashborad for user ${session.user.userName}"
    }
}
