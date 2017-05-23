package com.link_sharing.project

import grails.transaction.Transactional

@Transactional
class ReadingItemService {

    def findAllByUserAndIsRead(User user,Boolean isRead, Map params){
        return ReadingItem.findAllByUserAndIsRead(user, isRead, params)
    }

    def countByUserAndIsRead(User user, Boolean isRead){
        return ReadingItem.countByUserAndIsRead(user, isRead)
    }
}
