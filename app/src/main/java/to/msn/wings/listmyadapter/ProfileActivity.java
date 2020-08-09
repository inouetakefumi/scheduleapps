package to.msn.wings.listmyadapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ProfileActivity extends AppCompatActivity {

    private Realm pRealm;
    private EditText pName;
    private EditText pKana;
    private EditText pPhoneNumber;
    private EditText pMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("profile.realm")
                .schemaVersion(4)
                .build();

        pRealm = Realm.getInstance(config);

        Intent i = getIntent();
        final long rid = i.getLongExtra("ID",0);

        pRealm.executeTransaction(new ProfileActivity.RealmProfileDisplayTransaction(rid) {
        });

    }

    class RealmProfileDisplayTransaction implements Realm.Transaction {
        long rid=0;
        RealmProfileDisplayTransaction(long rid){
            this.rid = rid;
        }
        @Override
        public void execute(Realm realm) {
            Profile profile = realm.where(Profile.class)
                    .equalTo("id", rid)
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



        }
    }
}
