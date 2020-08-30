package to.msn.wings.listmyadapter;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.widget.AdapterView;

import java.util.Date;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;


public class ScheduleAllActivity extends AppCompatActivity {

    private String code = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_all);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        final String name = i.getStringExtra("Name");
        final long rid = i.getLongExtra("ID",0);

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        // today は　今日の日付の0時0分0.000秒
        Date todayDate = today.getTime();
        RealmResults<Schedule> result = Realm.getDefaultInstance().where(Schedule.class)
                .greaterThanOrEqualTo("date", todayDate)
                .equalTo("name", name)
                .findAll();
        ArrayList<Schedule> data = new ArrayList<>();
        for (int number = 0; number < 100; number++) {
            data.add(result.get(number));
        }
        MyListAdapter adapter = new MyListAdapter(this, data, R.layout.list_item);
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyListAdapter adapter = (MyListAdapter) parent.getAdapter();
                Schedule schedule = (Schedule) adapter.getItem(position);
                Intent intent = new Intent(ScheduleAllActivity.this, ScheduleActivity.class);
                intent.putExtra("ID",schedule.getId());
                startActivity(intent);
            }
        });

        getCode();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.schedule_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cansel:
                finish();
                break;
            case R.id.outlook_login:
                login();
        }
        return true;
    }

    private void login() {
        String url = new StringBuilder()
                .append(getString(R.string.authorize_url))
                .append("?client_id=").append(getString(R.string.client_id))
                .append("&redirect_uri=").append(getString(R.string.redirect_uri))
                .append("&response_type=code&scope=Calendars.Read")
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
        if (uri == null) {
            code = null;
            return;
        }
        String tempCode = uri.getQueryParameter("code");
        code = tempCode == null || tempCode.isEmpty() ? null : tempCode;
    }


    protected void onResume() {
        super.onResume();

        Intent i = getIntent();
        final String name = i.getStringExtra("Name");

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        // today は　今日の日付の0時0分0.000秒
        Date todayDate = today.getTime();
        RealmResults<Schedule> result = Realm.getDefaultInstance().where(Schedule.class)
                .greaterThanOrEqualTo("date", todayDate)
                .equalTo("name", name)
                .findAll();
        ArrayList<Schedule> data = new ArrayList<>();
        for (int number = 0; number < 100; number++) {
            data.add(result.get(number));
        }
        MyListAdapter adapter = new MyListAdapter(this, data, R.layout.list_item);
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);
    }
}
