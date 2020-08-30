package to.msn.wings.listmyadapter;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jetbrains.annotations.NotNull;

public class ApiActivity extends AppCompatActivity {

    private static final String TAG = ApiActivity.class.getSimpleName();

    private Handler handler = new Handler();

    private String code = null;
    private String accessToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate intent:" + getIntent());
        setContentView(R.layout.activity_api);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.getAccessTokenButton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getAccessToken();
                    }
                }
        );
        getCode();
        updateView();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    private void getCode() {
        Uri uri = getIntent().getData();
        Log.d(TAG, "uri:" + uri);
        if (uri == null) {
            code = null;
            return;
        }
        String tempCode = uri.getQueryParameter("code");
        code = tempCode == null || tempCode.isEmpty() ? null : tempCode;
    }

    private void getAccessToken() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("grant_type", "authorization_code")
                .addFormDataPart("code", code)
                .addFormDataPart("redirect_uri", getString(R.string.redirect_uri))
                .addFormDataPart("client_id", getString(R.string.client_id))
                .addFormDataPart("client_secret", getString(R.string.client_secret))
                .build();
        Request request = new Request.Builder()
                .url(getString(R.string.access_token_url))
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                AccesstokenResponse accesstokenResponse = gson.fromJson(result, AccesstokenResponse.class);
                accessToken = accesstokenResponse.getAccessToken();

            }
        });
    }
    private void updateView() {
        ((TextView) findViewById(R.id.codeTextView)).setText(new StringBuilder().append("code:\n").append(code));
        ((TextView) findViewById(R.id.accessTokenTextView)).setText(new StringBuilder().append("accessToken:\n").append(accessToken));
    }


}