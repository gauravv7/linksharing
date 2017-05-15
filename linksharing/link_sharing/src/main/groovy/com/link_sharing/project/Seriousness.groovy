package com.link_sharing.project

enum Seriousness {

    SERIOUS("serious"),
    VERY_SERIOUS("very serious"),
    CASUAL("casual")

    final String value

    Seriousness() {}

    Seriousness(String value){
        this.value=value
    }

    static checkSeriousness(String seriousness){
        switch (seriousness.toLowerCase()){
            case "serious":
                return Seriousness.SERIOUS
                break
            case "very_serious":
                return Seriousness.VERY_SERIOUS
                break
            case "casual":
                return Seriousness.CASUAL
        }
    }
    String toString(){
        return value
    }

    String getKey(){
        return name()
    }
}
