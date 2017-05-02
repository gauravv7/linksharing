package com.link_sharing.project

import com.link_sharing.project.Resource as Resource

class DocumentResource extends Resource {

    String filePath

    static constraints = {
        filePath(blank: false)
    }

    String toString() {
        return filePath
    }
}