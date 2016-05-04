package com.polybiastudios.photosweep;

import java.util.Map;

/**
 * Created by dallascharter on 7/04/16.
 */
public class RequestManager {
    private static RequestManager requestManager = null;

    private static String DEBUG_TAG = "RequestManager";

    public static RequestManager getInstance(){
        if(requestManager == null){
            requestManager = new RequestManager();
        }
        return requestManager;
    }

    public void loginRequest(Map<String, String> parameters, ApplicationManager.APIResponse response){
        ApplicationManager.getInstance().requestAPI(Constants.kServerPathLogin, "POST", parameters, response);
    }

    public void logoutRequest(Map<String, String> parameters, ApplicationManager.APIResponse response){
        ApplicationManager.getInstance().requestAPI(Constants.kServerPathLogout, "POST", parameters, response);
    }

    public void registerRequest(Map<String, String> parameters, ApplicationManager.APIResponse response){
        ApplicationManager.getInstance().requestAPI(Constants.kServerPathRegister, "POST", parameters, response);
    }
}
