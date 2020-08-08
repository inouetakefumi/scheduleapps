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

    private Realm pRealm;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        RealmConfiguration profile = new RealmConfiguration.Builder()
                .name("profile.realm")
                .schemaVersion(3)
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
            data.add(profileresult.get(i));
        }
        ProfileAdapter adapter = new ProfileAdapter(this, data, R.layout.list_name);
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);
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
            profile.kana = "イノウエ";
            profile.phonenumber= "090-xxxx-xxxx";
            profile.mail = "xxx@xxx";
            profile.part = "第一システム担当";

        }
    }




}
