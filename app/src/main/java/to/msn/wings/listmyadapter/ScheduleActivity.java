package to.msn.wings.listmyadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.Toast;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;

import android.widget.Spinner;
import android.widget.Toast;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;

import java.text.SimpleDateFormat;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ScheduleActivity extends AppCompatActivity {

    private TextView mTextView;
    private Realm mRealm;
    private EditText mTitle;
    private EditText mDetail;
    private Spinner mSpinnerWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("schedule.realm")
                .schemaVersion(1)
                .build();

        mRealm = Realm.getInstance(config);

        Intent i = getIntent();
        final long rid = i.getLongExtra("ID",0);

        mRealm.executeTransaction(new RealmDateDisplayTransaction(rid) {
        });

        //Spinnerで選択した際の実装
        Spinner sp = findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner sp = (Spinner) parent;
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Edittextに入力した際の実装
        mDetail = (EditText) findViewById(R.id.detail);
        mDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button input = findViewById(R.id.input);
        input.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Schedule schedule = realm.where(Schedule.class).equalTo("id", rid)
                                        .findFirst();
                                Spinner sp = findViewById(R.id.spinner);
                                schedule.work = sp.getSelectedItem().toString();

                                //選択した勤怠のspinner位置をDBに保持する
                                schedule.setWorkPosition(sp.getSelectedItemPosition());
                                schedule.setWork(sp.getSelectedItem().toString());

                                //タスクメモのテキスト内容をDBに保持する
                                EditText detail = findViewById(R.id.detail);
                                schedule.setDetail(detail.getText().toString());

                                setResult(RESULT_OK);
                                finish();
                            }

                        });
                        mRealm.close();
                    }
                }
        );



        //キャンセルボタンを押下したの実装
        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();

                    }
                }
        );

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

            // 取得した勤怠リストのポジションを取得して、spinnerの選択状態にする
            mSpinnerWork = (Spinner) findViewById(R.id.spinner);
            mSpinnerWork.setSelection(schedule.getWorkPosition());

            // 取得したタスクメモをEditTextに表示させる
            mDetail = (EditText) findViewById(R.id.detail);
            mDetail.setText(schedule.getDetail());
        }
    }

}

