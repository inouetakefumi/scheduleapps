package to.msn.wings.listmyadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import java.util.Date;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("schedule.realm")
                .schemaVersion(1)
                .build();

        mRealm.deleteRealm(config);

        mRealm = Realm.getInstance(config);

        //データを10日初期登録
        try {
            for(int i = 0;i<10;i++){
                mRealm.executeTransaction(new RealmInitTransaction(i) {
                });
            }
        }
        catch (IllegalArgumentException e) {
            // 不正な日付の場合の処理
        }

        RealmResults<Schedule> result = mRealm.where(Schedule.class).findAll();
        ArrayList<Schedule> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(result.get(i));
        }
        MyListAdapter adapter = new MyListAdapter(this, data, R.layout.list_item);
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);
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


}
