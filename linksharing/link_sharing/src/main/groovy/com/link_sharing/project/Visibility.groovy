package com.link_sharing.project

public enum Visibility {
    PRIVATE, PUBLIC

    final String value

    Visibility(){}

    Visibility(String value){
        this.value=value
    }

    static checkVisibility(String visibility) {
        switch (visibility.toLowerCase()) {
            case "public":
                return Visibility.PUBLIC
                break
            case "private":
                return Visibility.PRIVATE
                break
        }
    }

    String toString(){
        return value
    }

    String getKey(){
        return name()
    }
}