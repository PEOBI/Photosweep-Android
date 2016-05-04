package com.polybiastudios.photosweep;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dallascharter on 29/03/16.
 */
public class ApplicationManager {
    private static ApplicationManager applicationManager = null;

    public String DEBUG_TAG = "ApplicationManger";

    public static ApplicationManager getInstance(){
        if(applicationManager == null){
            applicationManager = new ApplicationManager();
        }
        return applicationManager;
    }

    public interface APIResponse{
        void responseReceived(JSONObject jsonObject);
        void responseReceived(JSONArray jsonArray);
        void errorReceived(String e, int code);
    }

    public void requestAPI(String route, String method, Map<String, String> paramaters, APIResponse apiResponse){
        new APITask(route, method, paramaters, apiResponse).execute();
    }

    private class APITask extends AsyncTask<Void, Void, String> {
        private String route;
        private String method;
        private Map<String, String> parameters;
        private APIResponse response;
        public APITask(String route, String method, Map<String, String> paramaters, APIResponse apiResponse) {
            this.route = route;
            this.method = method;
            if (paramaters == null) {
                this.parameters = new HashMap<>();
            }
            else{
                this.parameters = paramaters;
            }
            this.response = apiResponse;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {
            StringBuilder stringBuffer = new StringBuilder();

            try {
                URL url = new URL(Constants.kServerHostHTTPS + route);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(false);
                urlConnection.setRequestMethod(method);

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), Constants.kUTF8StringEncoding));
                writer.write(buildPOSTString(parameters));
                writer.flush();
                writer.close();

                urlConnection.getOutputStream().close();

                //Read in the server response
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = reader.readLine()) != null){
                    stringBuffer.append(line);
                }
                reader.close();
                inputStream.close();

                return stringBuffer.toString();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            if(string == null){
                response.errorReceived("Null string returned", -1);
                return;
            }
            Log.d(DEBUG_TAG, string);

            try {
                JSONObject responseObject = new JSONObject(string);
                if(responseObject.has(Constants.kErrorJSON)){
                    //server returned an error
                    if(Integer.parseInt(responseObject.getString(Constants.kErrorJSON)) != 0){
                        if(response != null){
                            response.errorReceived(generateErrorForJSONResponse(responseObject), responseObject.getInt(Constants.kErrorJSON));
                        }
                    }
                    else {
                        if(response != null){
                            response.responseReceived(responseObject);
                        }
                    }
                }
                else {
                    if(response != null){
                        response.responseReceived(responseObject);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();

                if(response != null){
                    response.errorReceived(e.getMessage(), -1);
                }
            }

        }
    }

    private String buildPOSTString(Map<String, String> postMap){
        StringBuilder stringBuilder = new StringBuilder();

        for(Map.Entry<String, String> entry : postMap.entrySet()){
            if(stringBuilder.length() > 0){
                stringBuilder.append("&");
            }
            try {
                stringBuilder.append(URLEncoder.encode(entry.getKey(), Constants.kUTF8StringEncoding));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(entry.getValue(), Constants.kUTF8StringEncoding));
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    private String generateErrorForJSONResponse(JSONObject object){
        try {
            return "CBAPIError - Code " + object.getString(Constants.kErrorJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
