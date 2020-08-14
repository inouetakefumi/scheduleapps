package to.msn.wings.listmyadapter;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import android.widget.AdapterView;

import java.util.Date;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class ScheduleAllActivity extends AppCompatActivity {

    private Realm sRealm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_all);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("schedule.realm")
                .schemaVersion(1)
                .build();

        sRealm = Realm.getInstance(config);

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        // today は　今日の日付の0時0分0.000秒
        Date todayDate = today.getTime();
        RealmResults<Schedule> result = sRealm.where(Schedule.class)
                .greaterThanOrEqualTo("date", todayDate)
                .findAll();
        ArrayList<Schedule> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(result.get(i));
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cansel:
                finish();
                break;
        }
        return true;
    }


    protected void onResume() {
        super.onResume();

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        // today は　今日の日付の0時0分0.000秒
        Date todayDate = today.getTime();
        RealmResults<Schedule> result = sRealm.where(Schedule.class)
                .greaterThanOrEqualTo("date", todayDate)
                .findAll();
        ArrayList<Schedule> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(result.get(i));
        }
        MyListAdapter adapter = new MyListAdapter(this, data, R.layout.list_item);
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);
    }
}
