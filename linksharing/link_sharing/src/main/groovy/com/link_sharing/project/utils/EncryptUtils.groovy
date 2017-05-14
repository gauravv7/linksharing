package com.link_sharing.project.utils

import java.security.MessageDigest

/**
 * Created by gaurav on 14/5/17.
 */
class EncryptUtils {

    static byte[] encryptSHA256(String msg) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(msg.getBytes("UTF-8"));
        return hash
    }
}
