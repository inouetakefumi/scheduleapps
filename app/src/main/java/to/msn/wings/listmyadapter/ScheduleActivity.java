package to.msn.wings.listmyadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.Toast;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Intent i = getIntent();
        int position = i.getIntExtra("ID",0);
        String msg = position + "番目のアイテムがクリックされました";
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
