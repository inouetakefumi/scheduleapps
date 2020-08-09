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

import android.widget.Toolbar;

public class ScheduleAllActivity extends AppCompatActivity {

    private Realm sRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_all);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("schedule.realm")
                .schemaVersion(1)
                .build();

        sRealm.deleteRealm(config);
        sRealm = Realm.getInstance(config);


    }
}
