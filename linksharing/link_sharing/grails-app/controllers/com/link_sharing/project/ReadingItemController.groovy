package com.link_sharing.project

import com.link_sharing.project.co.SearchCO

class ReadingItemController {

    def changeIsRead(Long id, Boolean isRead) {
        if( ReadingItem.updateIsRead(id, isRead))
            render "Success"
        else
            render "Failure"
    }
}
