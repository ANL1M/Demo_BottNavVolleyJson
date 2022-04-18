package it.anlim.demobottnavvolleyjson;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ServerConnection {
    public static final String BASIC_URL = "https://apivegans.veganslab.xyz";
    public static final String DATA_URL = "/test.json";

    // Callback interface
    public interface ResponseCallback {
        void onSuccessResponse(String result);
    }

    // Send a request
    public void sendRequest(Context context, String basic_url, String data_url, ResponseCallback callback) {
        RequestQueue queue;

        // If response is successful return data otherwise null
        StringRequest request = new StringRequest(Request.Method.GET, basic_url + data_url,
                callback::onSuccessResponse,
                error -> callback.onSuccessResponse(null)){
        };

        queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

}
