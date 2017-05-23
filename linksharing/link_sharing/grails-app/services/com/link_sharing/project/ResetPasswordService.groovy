package com.link_sharing.project

import grails.transaction.Transactional

@Transactional
class ResetPasswordService {

    def findByUrlHash(String code) {
        return ResetPassword.findByUrlHash(code)
    }
}
