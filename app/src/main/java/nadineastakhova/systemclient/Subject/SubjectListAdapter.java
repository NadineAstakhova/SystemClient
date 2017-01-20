package nadineastakhova.systemclient.Subject;

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
 * Created by Nadine on 30.10.2016.
 */

public class SubjectListAdapter extends CustomListAdapter<Subject> {

    Activity context;
    List<Subject> subjects;
    private SparseBooleanArray mSelectedItemsIds;


    public SubjectListAdapter(Activity context, int resId, List<Subject> subjects) {
        super(context, resId, subjects);
        this.mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.subjects = subjects;
    }

    private class ViewHolder {
        TextView subjectTxt;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.subjectTxt = (TextView) convertView
                    .findViewById(R.id.subject_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Subject subject = getItem(position);
        holder.subjectTxt.setText(subject.toString());
        convertView
                .setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4
                        : Color.TRANSPARENT);

        return convertView;
    }

    @Override
    public void add(Subject subject) {
        subjects.add(subject);
        notifyDataSetChanged();
        Toast.makeText(context, subjects.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void remove(Subject object) {
        subjects.remove(object);
        notifyDataSetChanged();
    }
}
