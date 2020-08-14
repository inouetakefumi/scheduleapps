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

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import android.widget.AdapterView;

import java.util.Date;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Realm sRealm;
    private Realm pRealm;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RealmConfiguration profile = new RealmConfiguration.Builder()
                .name("profile.realm")
                .schemaVersion(4)
                .build();

        pRealm.deleteRealm(profile);

        pRealm = Realm.getInstance(profile);

        //profileを初期登録

        pRealm.executeTransaction(new RealmProfileTransaction() {
        });

        //adapterにprofileDBを登録
        RealmResults<Profile> profileresult = pRealm.where(Profile.class)
                .findAll();
        ArrayList<Profile> data = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            if (i < profileresult.size()) {
                data.add(profileresult.get(i));
            }
        }

        ProfileAdapter adapter = new ProfileAdapter(this, data, R.layout.list_name);
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProfileAdapter adapter = (ProfileAdapter) parent.getAdapter();
                Profile profile = (Profile) adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("ID",profile.getId());
                startActivity(intent);
            }
        });

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("schedule.realm")
                .schemaVersion(1)
                .build();

        sRealm.deleteRealm(config);

        sRealm = Realm.getInstance(config);

        //scheduleを100日初期登録
        try {
            for(int i = 0;i<100;i++){
                sRealm.executeTransaction(new RealmInitTransaction(i) {
                });
            }
        }
        catch (IllegalArgumentException e) {
            // 不正な日付の場合の処理
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, ProfileAddActivity.class);
                startActivity(intent);
                break;
        }
        return true;
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

            profile.name = "井上 武史";
            profile.kana = "イノウエ　タケフミ";
            profile.phonenumber= "090-xxxx-xxxx";
            profile.mail = "inouetakefumi@xxx";
            profile.part = "第一システム担当";

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
            schedule.name = "井上 武史";

        }
    }




}
