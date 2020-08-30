package to.msn.wings.listmyadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;

public class ProfileAddActivity extends AppCompatActivity {

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
                        profile.setName(((EditText)findViewById(R.id.name_edit)).getText().toString());
                        profile.setKana(((EditText)findViewById(R.id.kana_edit)).getText().toString());
                        profile.setPhonenumber(((EditText)findViewById(R.id.phonenumber_edit)).getText().toString());
                        profile.setMail(((EditText)findViewById(R.id.mail_edit)).getText().toString());

                        Profile.insertOrUpdate(profile);

                        Schedule.insertOrUpdate(makeInitSchedules(((EditText)findViewById(R.id.name_edit)).getText().toString()));
                        finish();
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

}