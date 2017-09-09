package com.francis.gitme;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Network {

    private static final String LOG_TAG = Network.class.getSimpleName();

    public static List<Developer> fetchJSONData(String Url){

        URL url = createUrl(Url);
        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        }catch (Exception e){
            Log.e(LOG_TAG, "Problem making http request", e);
        }

        return extractDevelopers(jsonResponse);

    }

    private static URL createUrl(String Url){
        URL url = null;
        try{
            url = new URL(Url);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the url", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null ){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromJson(inputStream);
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the developers Json file");
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromJson(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Developer> extractDevelopers(String jsonFile) {

        if (TextUtils.isEmpty(jsonFile)){
            return null;
        }
        // Create an empty ArrayList that we can start adding developers to

        List<Developer> Developers = new ArrayList<>();

        // Try to parse the jsonFile. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try {
            // build up a list of Developer objects with the corresponding data.
            JSONObject root = new JSONObject(jsonFile);
            JSONArray items = root.getJSONArray("items");

            for (int i = 0; i < items.length(); i++){

                JSONObject developer = items.getJSONObject(i);

                String imageUrl = developer.getString("avatar_url");
                String username = developer.getString("login");
                String url = developer.getString("html_url");

                Developers.add(new Developer(username, imageUrl, url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the developers JSON results", e);
        }
        return Developers;
    }
}
