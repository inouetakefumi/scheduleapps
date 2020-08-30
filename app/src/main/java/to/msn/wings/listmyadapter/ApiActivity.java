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



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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

    private void updateView() {
        ((TextView) findViewById(R.id.codeTextView)).setText(new StringBuilder().append("code:\n").append(code));
        ((TextView) findViewById(R.id.accessTokenTextView)).setText(new StringBuilder().append("accessToken:\n").append(accessToken));
    }


}