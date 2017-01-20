package nadineastakhova.systemclient.Groups;

import android.app.Activity;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import nadineastakhova.systemclient.CustomListAdapter;
import nadineastakhova.systemclient.R;

/**
 * Created by Nadine on 20.11.2016.
 */

public class GroupsListAdapter extends CustomListAdapter<Group> {
    Activity context;
    List<Group> groups;
    private SparseBooleanArray mSelectedItemsIds;

    public GroupsListAdapter(Activity context, int resId, List<Group> groups) {
        super(context, resId, groups);
        this.mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.groups = groups;
    }

    private class ViewHolder {
        TextView groupTxt;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.groupTxt = (TextView) convertView
                    .findViewById(R.id.subject_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Group group = getItem(position);
        holder.groupTxt.setText(group.toString());
        convertView
                .setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4
                        : Color.TRANSPARENT);

        return convertView;
    }

    @Override
    public void add(Group group) {
        groups.add(group);
        notifyDataSetChanged();
        Toast.makeText(context, groups.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void remove(Group object) {
        groups.remove(object);
        notifyDataSetChanged();
    }
}
