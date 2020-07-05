package to.msn.wings.listmyadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.Toast;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        Intent i = getIntent();
        int position = i.getIntExtra("ID",0);
        String msg = position + "番目のアイテムがクリックされました";
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

        mRealm.executeTransaction(new RealmDateDisplayTransaction(position) {
        });
    }

    class RealmDateDisplayTransaction implements Realm.Transaction {
        int position=0;
        RealmDateDisplayTransaction(int position){
            this.position = position;
        }
        @Override
        public void execute(Realm realm) {
            Schedule schedule = realm.where(Schedule.class)
                    .equalTo("id", position)
                    .findFirst();
            mTextView = (TextView) findViewById(R.id.date);

            SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
            String formatDate = sdf.format(schedule.getDate());
            // 表示するスケジュールをTextViewに表示します。
            mTextView.setText(formatDate);
        }
    }

}

