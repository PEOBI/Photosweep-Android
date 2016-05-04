package com.polybiastudios.photosweep.user;

/**
 * Created by dallascharter on 22/03/16.
 */
public class UserData {

    private static UserData userData = null;

    public static UserData getInstance(){
        if(userData == null){
            userData = new UserData();
        }
        return userData;
    }
}
