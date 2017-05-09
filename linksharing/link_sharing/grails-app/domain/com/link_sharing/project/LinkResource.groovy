package com.link_sharing.project

import com.link_sharing.project.Resource as Resource

class LinkResource extends Resource {

    String url

    static constraints = {
        url(blank: false, url: true)
    }

    String toString() {
        return url
    }
}