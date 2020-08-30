package to.msn.wings.listmyadapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;



public class loginActivity extends AppCompatActivity {

    private static final String TAG = loginActivity.class.getSimpleName();

    private Handler handler = new Handler();

    private String code = null;
    private String accessToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate intent:" + getIntent());
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.loginButton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        login();
                    }
                }
        );

        getCode();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    private void login() {
        String url = new StringBuilder()
                .append(getString(R.string.authorize_url))
                .append("?client_id=").append(getString(R.string.client_id))
                .append("&redirect_uri=").append(getString(R.string.redirect_uri))
                .append("&response_type=code&scope=openid+Calendars.Read")
                .toString();
        String encodedUrl = null;
        try {
            encodedUrl = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (encodedUrl == null)
            return;
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
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


}
