package to.msn.wings.listmyadapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Schedule> data;
    private int resource;

    MyListAdapter(Context context,
                         ArrayList<Schedule> data, int resource) {
        this.context = context;
        this.data = data;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    private static class ViewHolder {
        TextView date;
        TextView work;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) context;
        Schedule schedule = (Schedule) getItem(position);
        if (convertView == null) {
            convertView = activity.getLayoutInflater()
                    .inflate(resource, null);
        }
        ((TextView) convertView.findViewById(R.id.date)).setText(schedule.getDate());
        ((TextView) convertView.findViewById(R.id.work)).setText(schedule.getWork());
        return convertView;
    }
}
