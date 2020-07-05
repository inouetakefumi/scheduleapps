package to.msn.wings.listmyadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.Toast;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ScheduleActivity extends AppCompatActivity {

    private TextView mTextView;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("schedule.realm")
                .schemaVersion(1)
                .build();

        mRealm = Realm.getInstance(config);

        Intent i = getIntent();
        long rid = i.getIntExtra("ID",0);

        mRealm.executeTransaction(new RealmDateDisplayTransaction(rid) {
        });
    }

    class RealmDateDisplayTransaction implements Realm.Transaction {
        long rid=0;
        RealmDateDisplayTransaction(long rid){
            this.rid = rid;
        }
        @Override
        public void execute(Realm realm) {
            Schedule schedule = realm.where(Schedule.class)
                    .equalTo("id", rid)
                    .findFirst();
            mTextView = (TextView) findViewById(R.id.date);

            SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
            String formatDate = sdf.format(schedule.getDate());
            // 表示するスケジュールをTextViewに表示します。
            mTextView.setText(formatDate);
        }
    }

}

