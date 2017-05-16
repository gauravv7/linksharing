package com.link_sharing.project.constants

import com.link_sharing.project.Seriousness

/**
 * Created by gaurav on 2/5/17.
 */
class Constants {
    static final String PASSWORD_NORMAL = "123456"
    static final String PASSWORD_ADMIN  = "admin123456"
    static final Seriousness SERIOUSNESS = Seriousness.VERY_SERIOUS
    static final Integer RATING = 5
    public static final String LOC_DOCUMENT_RESOURCE = "/opt/assets/documentResource"
    public static final String LOC_PHOTO_RESOURCE = "/opt/assets/photos"
    static final String SALT = "someSalt"
    static final String DOCUMENT_CONTENT_TYPE = "application/pdf"
    static final String DEFAULT_USER_PHOTO="default-user.png"
}
