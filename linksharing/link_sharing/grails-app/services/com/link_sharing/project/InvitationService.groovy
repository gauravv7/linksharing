package com.link_sharing.project

import com.link_sharing.project.co.InviteCO
import grails.transaction.Transactional

@Transactional
class InvitationService {

    def findByUrlHash(String code) {
        return Invitation.findByUrlHash(code)
    }

    def delete(Long invitationId) {
        return Invitation.load(invitationId).delete()
    }
}
