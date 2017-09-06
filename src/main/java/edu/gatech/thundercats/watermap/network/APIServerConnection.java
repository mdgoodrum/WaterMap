package edu.gatech.thundercats.watermap.network;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.BaseRequest;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import edu.gatech.thundercats.watermap.util.Callback;

/**
 * Created by Matthieu Gay-Bellile on 10/27/16.
 */
public class APIServerConnection {

    private static APIServerConnection instance = null;
    public static APIServerConnection getInstance() {
        if (instance == null) {
            instance = new APIServerConnection("https://thundercats-water-map.herokuapp.com/api");
        }
        return instance;
    }

    private final String apiBase;
    private String authToken = null;

    public APIServerConnection(String apiBase) {
        this.apiBase = apiBase;

        Unirest.setObjectMapper(new ObjectMapper() {
            private Gson gson = new Gson();

            @Override
            public <T> T readValue(String json, Class<T> clazz) {
                return gson.fromJson(json, clazz);
            }

            @Override
            public String writeValue(Object obj) {
                return gson.toJson(obj);
            }
        });
    }

    public JSONObject get(String path, Callback<String> errorMessage) throws Exception {
        return request(HttpMethod.GET, path, JsonNode.class, null, errorMessage).getObject();
    }

    public <U> JSONObject post(String path, U body, Callback<String> errorMessage) throws Exception {
        return request(HttpMethod.POST, path, JsonNode.class, body, errorMessage).getObject();
    }

    public <U> JSONObject put(String path, U body, Callback<String> errorMessage) throws Exception {
        return request(HttpMethod.PUT, path, JsonNode.class, body, errorMessage).getObject();
    }

    public <T> T getAsObject(String path, Class<T> responseClass, Callback<String> errorMessage) throws Exception {
        return request(HttpMethod.GET, path, responseClass, null, errorMessage);
    }

    public <T, U> T postAsObject(String path, Class<T> responseClass, U body, Callback<String> errorMessage) throws Exception {
        return request(HttpMethod.POST, path, responseClass, body, errorMessage);
    }

    public <T, U> T putAsObject(String path, Class<T> responseClass, U body, Callback<String> errorMessage) throws Exception {
        return request(HttpMethod.PUT, path, responseClass, body, errorMessage);
    }

    public <T, U> T request(HttpMethod method, String path, Class<T> responseClass, U body, Callback<String> errorCallback) throws Exception {
        Map<String, String> headers = new HashMap<>(2, 1);
        headers.put("Content-Type", "application/json");
        if (authToken != null) {
            headers.put("Authorization", "JWT " + authToken);
        }

        T response;
        try {
            BaseRequest request;
            if (method == HttpMethod.GET) {
                request = Unirest.get(apiBase + path)
                        .headers(headers);
            } else if (method == HttpMethod.POST) {
                request = Unirest.post(apiBase + path)
                        .headers(headers)
                        .body(body);
            } else if (method == HttpMethod.PUT) {
                request = Unirest.put(apiBase + path)
                        .headers(headers)
                        .body(body);
            } else {
                throw new IllegalArgumentException("HTTP Method not supported: " + method);
            }
            response = request.asObjectAsync(responseClass)
                    .get(5, TimeUnit.SECONDS)
                    .getBody();
        } catch (InterruptedException e) {
            throw e;
        } catch (TimeoutException e) {
            errorCallback.call("Timed out.");
            throw e;
        } catch (ExecutionException e) {
            if (e.getMessage().contains("java.net.ConnectException"))
                System.out.println(e.getMessage().split(": ")[1]); // TODO: Deal with ConnectException cleanly
            throw e;
        } catch (Exception e) {
            errorCallback.call("An error occurred." + e.getMessage());
            throw e;
        }

        return response;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}