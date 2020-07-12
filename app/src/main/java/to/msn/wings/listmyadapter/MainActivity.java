package to.msn.wings.listmyadapter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import android.widget.Toast;


import android.widget.AdapterView;

import java.util.Date;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Realm mRealm;
    private Realm pRealm;
    private ListView list;
    private int mPosition;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                .schemaVersion(1)
                .build();

        pRealm.deleteRealm(profile);

        pRealm = Realm.getInstance(profile);

        //データを10日初期登録
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

        RealmResults<Schedule> result = mRealm.where(Schedule.class).findAll();
        ArrayList<Schedule> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Schedule schedule = new Schedule();
            schedule.setId(result.get(i).getId());
            schedule.setName(result.get(i).getName());
            schedule.setDate(result.get(i).getDate());
//            data.add(result.get(i));
            data.add(schedule);
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
//                startActivity(intent);
                mPosition = position;
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ListView list = findViewById(R.id.list);
            MyListAdapter adapter = (MyListAdapter) list.getAdapter();
            Schedule schedule = (Schedule)adapter.getItem(mPosition);
            RealmResults<Schedule> result = mRealm.where(Schedule.class).findAll();
            String work = result.get(mPosition).getWork();
            schedule.setWork(work);
        }
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
            schedule.name = "井上武史";

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


            profile.name = "井上武史";
            profile.phonenumber= "090-6978-0495";

        }
    }


}
