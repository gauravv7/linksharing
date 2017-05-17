package com.link_sharing.project

import com.link_sharing.project.co.SearchCO

class ReadingItemController {

    def changeIsRead(Long id) {
        ReadingItem item = ReadingItem.load(id)
        if(item){
            item.isRead = !item.isRead
            if(item.save(flush: true)){
                def msg = "marked as "+((item.isRead)?"read":"unread")
                flash.message = msg
            } else{
                flash.error = item.errors.allErrors.join(", ")
            }
        } else {
            flash.error = "resource not found"
        }

        redirect(url: request.getHeader("referer"))
    }
}
