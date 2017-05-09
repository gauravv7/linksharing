package com.link_sharing.project.vo

import com.link_sharing.project.User
import com.link_sharing.project.Visibility

/**
 * Created by gaurav on 9/5/17.
 */
class TopicVO {

    Long id
    String name
    Visibility visibility
    Integer count
    User createdBy

    String toString() {
        return "${name}"
    }
}
