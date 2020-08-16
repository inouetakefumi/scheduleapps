package to.msn.wings.listmyadapter;

import android.app.Application;

import io.realm.Realm;

import io.realm.RealmConfiguration;

public class ScheduleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);


    }
}
