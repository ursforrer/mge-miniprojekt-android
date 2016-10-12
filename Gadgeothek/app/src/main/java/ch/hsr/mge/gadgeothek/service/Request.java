package ch.hsr.mge.gadgeothek.service;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Response;

class Request<T> extends AsyncTask<Void, Void, Pair<String, T>> {

    private static final String TAG = Request.class.getSimpleName();
    private final HttpVerb requestKind;
    private final String url;
    private final Type resultType;
    private final @Nullable HashMap<String, String> headers;
    private final @Nullable HashMap<String, String> body;
    private final Callback<T> callback;

    Request(HttpVerb type, String url, Type typeClass, @Nullable HashMap<String, String> headers, @Nullable HashMap<String, String> body, Callback<T> callback) {
        this.requestKind = type;
        this.url = url;
        this.resultType = typeClass;
        this.headers = headers;
        this.body = body;
        this.callback = callback;
    }

    protected Pair<String, T> doInBackground(Void... unused) {
        return getData(url, resultType);
    }

    private Pair<String, T> getData(String url, Type type) {
        Log.d(TAG, "Requesting " + url);

        OkHttpClient client = new OkHttpClient();

        String responseBody = "";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        try {

            Headers headers = this.headers != null ? Headers.of(this.headers) : Headers.of();

            FormBody.Builder bodyBuilder = new FormBody.Builder();
            if(body != null) {
                for (Map.Entry<String, String> entry : body.entrySet()) {
                    bodyBuilder = bodyBuilder.add(entry.getKey(), entry.getValue());
                }
            }
            FormBody body = bodyBuilder.build();

            okhttp3.Request request = new okhttp3.Request.Builder().url(url).headers(headers).build();

            switch (requestKind) {
                case POST:
                    request = new okhttp3.Request.Builder().url(url).headers(headers).post(body).build();
                    break;
                case GET:
                    request = new okhttp3.Request.Builder().url(url).headers(headers).build();
                    break;
                case DELETE:
                    request = new okhttp3.Request.Builder().url(url).headers(headers).delete(body).build();
                    break;
            }

            Response response = client.newCall(request).execute();

            responseBody = response.body().string();

            Log.d(this.getClass().getSimpleName(), "Response received: " + responseBody);

            //noinspection unchecked
            return new Pair<>(null, (T) gson.fromJson(responseBody, type));

        } catch (JsonParseException e) {
            String message = e.getMessage();
            try {
                message = gson.fromJson(responseBody, String.class);
            } catch (JsonParseException e1) {
                Log.e(TAG, "Could not parse response as JSON", e);
            }
            return new Pair<>(message, null);
        } catch (ConnectException e) {
            Log.e(TAG, "Could not connect", e);
            return new Pair<>("Could not connect to server", null);
        } catch (Exception e) {
            Log.e(TAG, "Could not perform request", e);
            return new Pair<>(e.getMessage(), null);
        }
    }

    protected void onPostExecute(Pair<String, T> result) {
        if (result.first != null) {
            callback.onError(result.first);
        } else {
            callback.onCompletion(result.second);
        }
    }
}
