package to.msn.wings.listmyadapter;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import android.net.Uri;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import android.widget.AdapterView;

import java.util.Date;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


//    private Realm pRealm;
//    private Realm sRealm;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });

//        RealmConfiguration profile = new RealmConfiguration.Builder()
//                .name("profile.realm")
//                .schemaVersion(4)
//                .build();
//
//        pRealm.deleteRealm(profile);
//
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name("schedule.realm")
//                .schemaVersion(1)
//                .build();
//
//        sRealm.deleteRealm(config);
//
//        pRealm = Realm.getInstance(profile);

        //adapterにprofileDBを登録
        RealmResults<Profile> profileresult = Realm.getDefaultInstance().where(Profile.class)
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
                intent.putExtra("Name",profile.getName());
                intent.putExtra("ID",profile.getId());
                startActivity(intent);
            }
        });
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

            case R.id.outlook_login:
                login();

        }
        return true;
    }

    private void login() {
        String url = new StringBuilder()
                .append(getString(R.string.authorize_url))
                .append("?client_id=").append(getString(R.string.client_id))
                .append("&redirect_uri=").append(getString(R.string.redirect_uri))
                .append("&response_type=code&scope=Calendars.Read")
                .toString();
        String encodedUrl = null;
        try {
            encodedUrl = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (encodedUrl == null)
            return;
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();

        RealmResults<Profile> profileresult = Realm.getDefaultInstance().where(Profile.class)
                .findAll();
        ArrayList<Profile> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < profileresult.size()) {
                data.add(profileresult.get(i));
            }
        }
        ProfileAdapter adapter = new ProfileAdapter(this, data, R.layout.list_item);
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);
    }
}
