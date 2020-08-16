package to.msn.wings.listmyadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ProfileAddActivity extends AppCompatActivity {

//    private Realm pRealm;
    private EditText pName;
    private EditText pKana;
    private EditText pPhoneNumber;
    private EditText pMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name("profile.realm")
//                .schemaVersion(4)
//                .build();
//
//        pRealm = Realm.getInstance(config);

        pName = (EditText) findViewById(R.id.name_edit);

        pKana = (EditText) findViewById(R.id.kana_edit);

        pPhoneNumber = (EditText) findViewById(R.id.phonenumber_edit);

        pMail = (EditText) findViewById(R.id.mail_edit);

        Button input = findViewById(R.id.input);
        input.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //
                        Profile profile = new Profile();
//                        EditText pName = findViewById(R.id.name_edit);.
                        profile.setName(((EditText)findViewById(R.id.name_edit)).getText().toString());
                        profile.setKana(((EditText)findViewById(R.id.kana_edit)).getText().toString());
                        profile.setPhonenumber(((EditText)findViewById(R.id.phonenumber_edit)).getText().toString());
                        profile.setMail(((EditText)findViewById(R.id.mail_edit)).getText().toString());

                        Profile.insertOrUpdate(profile);

                        Schedule.insertOrUpdate(makeInitSchedules(((EditText)findViewById(R.id.name_edit)).getText().toString()));
                        finish();
//                        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
//                            @Override
//                            public void execute(Realm realm) {
//
//                                Number max = realm.where(Profile.class).max("id");
//                                long newId = 0;
//                                if(max != null) { // nullチェック
//                                    newId = max.longValue() + 1;
//                                }
//                                Profile profile
//                                        = realm.createObject(Profile.class, newId);
//
//                                EditText pName = findViewById(R.id.name_edit);
//                                profile.setName(pName.getText().toString());
//
//                                EditText pKana = findViewById(R.id.kana_edit);
//                                profile.setKana(pKana.getText().toString());
//
//                                EditText pPhoneNumber = findViewById(R.id.phonenumber_edit);
//                                profile.setPhonenumber(pPhoneNumber.getText().toString());
//
//                                EditText pMail = findViewById(R.id.mail_edit);
//                                profile.setMail(pMail.getText().toString());
//
//                                RealmConfiguration config = new RealmConfiguration.Builder()
//                                        .name("schedule.realm")
//                                        .schemaVersion(1)
//                                        .build();
//
//                                pRealm = Realm.getInstance(config);
//
//                                //scheduleを100日初期登録
//                                try {
//                                    for(int i = 0;i<100;i++){
//                                        pRealm.executeTransaction(new ProfileAddActivity.RealmScheduleAdd(i) {
//                                        });
//                                    }
//                                }
//                                catch (IllegalArgumentException e) {
//                                    // 不正な日付の場合の処理
//                                }
//                                finish();
//                            }
//                        });
                    }
                }
        );

    }

    private List<Schedule> makeInitSchedules(String name) {
        List<Schedule> list = new ArrayList<>();
        Number max = Realm.getDefaultInstance().where(Schedule.class).max("id");
        long id = max == null ? 1L : (max.longValue() + 1L);
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 100; i++) {
            Schedule schedule = new Schedule();
            schedule.setId(id);
            schedule.setName(name);
            schedule.setDate(calendar.getTime());
            list.add(schedule);

            id ++;
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
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
//    class RealmScheduleAdd implements Realm.Transaction {
//        int i =0;
//        RealmScheduleAdd(int i){
//            this.i = i;
//        }
//        @Override
//        public void execute(Realm realm) {
//            Number max = realm.where(Schedule.class).max("id");
//            long newId = 0;
//            if(max != null) { // nullチェック
//                newId = max.longValue() + 1;
//            }
//            Schedule schedule
//                    = realm.createObject(Schedule.class, newId);
//
//            Calendar c = Calendar.getInstance();
//
//            //当日日付を取得
//            int year = c.get(Calendar.YEAR);
//            int month = c.get(Calendar.MONTH);
//            int day= c.get(Calendar.DATE);
//            //日付を
//            c.set(year, month , day + i);
//            Date d = c.getTime();
//            schedule.date = d;
//            schedule.name = pName.getText().toString();
//
//        }
//    }
}

