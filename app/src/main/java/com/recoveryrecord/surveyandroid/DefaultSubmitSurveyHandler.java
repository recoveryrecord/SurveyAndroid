package com.recoveryrecord.surveyandroid;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DefaultSubmitSurveyHandler implements SubmitSurveyHandler {

    private Context mContext;
    private Response.Listener<JSONObject> mResponseListener;
    private Response.ErrorListener mErrorListener;

    public DefaultSubmitSurveyHandler(Context context) {
        mContext = context;
        mResponseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), "Response: " + response.toString(), Toast.LENGTH_LONG).show();
            }
        };
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR: " + error, Toast.LENGTH_LONG).show();
            }
        };
    }

    private Context getContext() {
        return mContext;
    }

    public void setResponseListener(Response.Listener<JSONObject> responseListener) {
        mResponseListener = responseListener;
    }

    public void setErrorListener(Response.ErrorListener errorListener) {
        mErrorListener = errorListener;
    }

    @Override
    public void submit(String url, String jsonQuestionAnswerData) {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(jsonQuestionAnswerData);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody, mResponseListener, mErrorListener);
        queue.add(jsonObjectRequest);
    }
}
