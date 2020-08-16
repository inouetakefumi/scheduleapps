package to.msn.wings.listmyadapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import android.content.Intent;


import io.realm.Realm;


public class ProfileActivity extends AppCompatActivity {

    private EditText pName;
    private EditText pKana;
    private EditText pPhoneNumber;
    private EditText pMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent i = getIntent();
        final long rid = i.getLongExtra("ID",0);
        final String name = i.getStringExtra("Name");

        Realm.getDefaultInstance().executeTransaction(new ProfileActivity.RealmProfileDisplayTransaction(name,rid) {
        });
    }

    class RealmProfileDisplayTransaction implements Realm.Transaction {
        String name;
        long rid=0;
        RealmProfileDisplayTransaction(String name,long rid){
            this.name = name;
            this.rid = rid;
        }
        @Override
        public void execute(Realm realm) {
            Profile profile = realm.where(Profile.class)
                    .equalTo("name", name)
                    .findFirst();

            // 取得した名前をEditTextに表示させる
            pName = (EditText) findViewById(R.id.name_edit);
            pName.setText(profile.getName());

            pKana = (EditText) findViewById(R.id.kana_edit);
            pKana.setText(profile.getKana());

            pPhoneNumber = (EditText) findViewById(R.id.phonenumber_edit);
            pPhoneNumber.setText(profile.getPhonenumber());

            pMail = (EditText) findViewById(R.id.mail_edit);
            pMail.setText(profile.getMail());

            Button schedulebutton = findViewById(R.id.schedulebutton);

            schedulebutton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ProfileActivity.this, ScheduleAllActivity.class);
                            intent.putExtra("Name",name);
                            intent.putExtra("ID",rid);
                            startActivity(intent);
                        }
                    }
            );

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
    }
}
