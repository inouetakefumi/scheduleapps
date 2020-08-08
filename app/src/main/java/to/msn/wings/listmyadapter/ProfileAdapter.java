package to.msn.wings.listmyadapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Profile> data;
    private int resource;

    ProfileAdapter(Context context,
                  ArrayList<Profile> data, int resource) {
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
        TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProfileAdapter.ViewHolder viewHolder;
        Activity activity = (Activity) context;
        Profile profile = (Profile) getItem(position);
        if (convertView == null) {
            convertView = activity.getLayoutInflater()
                    .inflate(R.layout.list_name, null);

            viewHolder = new ProfileAdapter.ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ProfileAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(profile.getName());

        return convertView;
    }
}
