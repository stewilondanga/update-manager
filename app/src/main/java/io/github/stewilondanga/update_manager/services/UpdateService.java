package io.github.stewilondanga.update_manager.services;

import javax.security.auth.callback.Callback;
import io.github.stewilondanga.update_manager.Constants;
import io.github.stewilondanga.update_manager.models.Update;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by stewart on 7/2/18.
 */

public class UpdateService {
    private static OkHttpClient client = new OkHttpClient();

    public static void findUpdates(okhttp3.Callback callback) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GUARDIAN_BASE_URL).newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .header("Authorization", Constants.API_KEY)
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public List<Update> processResults(Response response) {
        List<Update> updates = new ArrayList<>();

            try {
                String jsonData = response.body().string();

                if (response.isSuccessful()) {
                    //The response JSON is an array of business objects within an object so we need to get that array
                    JSONObject guardianJSON = new JSONObject(jsonData);
                    JSONArray briefsJSON = guardianJSON.getJSONArray("briefs");

                    Type collectionType = new TypeToken<List<Update>> () {}.getType();
                    Gson gson = new GsonBuilder().create();
                    updates = gson.fromJson(briefsJSON.toString(),collectionType);
                }
            }catch (JSONException | NullPointerException | IOException e) {
                e.printStackTrace();
            }

            return updates;
    }
}
