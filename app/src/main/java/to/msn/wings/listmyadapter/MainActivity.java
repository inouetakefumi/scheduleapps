package to.msn.wings.listmyadapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import android.widget.AdapterView;

import java.util.Date;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Realm mRealm;
    private Realm pRealm;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("schedule.realm")
                .schemaVersion(1)
                .build();

        mRealm.deleteRealm(config);

        mRealm = Realm.getInstance(config);

        RealmConfiguration profile = new RealmConfiguration.Builder()
                .name("profile.realm")
                .schemaVersion(2)
                .build();

        pRealm.deleteRealm(profile);

        pRealm = Realm.getInstance(profile);

        //データを100日初期登録
        try {
            for(int i = 0;i<100;i++){
                mRealm.executeTransaction(new RealmInitTransaction(i) {
                });
            }
        }
        catch (IllegalArgumentException e) {
            // 不正な日付の場合の処理
        }

        pRealm.executeTransaction(new RealmProfileTransaction() {
        });

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        // today は　今日の日付の0時0分0.000秒
        Date todayDate = today.getTime();
        RealmResults<Schedule> result = mRealm.where(Schedule.class)
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
                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                intent.putExtra("ID",schedule.getId());
                startActivity(intent);
            }
        });
    }

    class RealmInitTransaction implements Realm.Transaction {
        int i =0;
        RealmInitTransaction(int i){
            this.i = i;
        }
        @Override
        public void execute(Realm realm) {
            Number max = realm.where(Schedule.class).max("id");
            long newId = 0;
            if(max != null) { // nullチェック
                newId = max.longValue() + 1;
            }
            Schedule schedule
                    = realm.createObject(Schedule.class, newId);

            Calendar c = Calendar.getInstance();

            //当日日付を取得
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day= c.get(Calendar.DATE);
            //日付を
            c.set(year, month , day+i);
            Date d = c.getTime();
            schedule.date = d;
            schedule.name = "井上";

        }
    }
    class RealmProfileTransaction implements Realm.Transaction {
        public void execute(Realm realm) {
            Number max = realm.where(Profile.class).max("id");
            long newId = 0;
            if(max != null) { // nullチェック
                newId = max.longValue() + 1;
            }
            Profile profile
                    = realm.createObject(Profile.class, newId);


            profile.name = "井上";
            profile.phonenumber= "090-xxxx-xxxx";

        }
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
        RealmResults<Schedule> result = mRealm.where(Schedule.class)
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
