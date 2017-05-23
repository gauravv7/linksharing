package com.link_sharing.project.dto

import com.link_sharing.project.ReadingItem

/**
 * Created by gaurav on 22/5/17.
 */
class ReadingItemDTO {

    static boolean updateIsRead(long id, boolean isRead) {
        ReadingItem.executeUpdate('Update ReadingItem set isRead=:read where id=:i',[read:isRead, i:id])
    }
}
