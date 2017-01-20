package nadineastakhova.systemclient.Task;

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
 * Created by Nadine on 07.11.2016.
 */

public class TaskListAdapter extends CustomListAdapter<Task> {
    Activity context;
    List<Task> tasks;
    private SparseBooleanArray mSelectedItemsIds;


    public TaskListAdapter(Activity context, int resId, List<Task> tasks) {
        super(context, resId, tasks);
        this.mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.tasks = tasks;
    }

    private class ViewHolder {
        TextView tasksTxt;
        TextView dateTxt;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TaskListAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_task, null);
            holder = new TaskListAdapter.ViewHolder();
            holder.tasksTxt = (TextView) convertView
                    .findViewById(R.id.task_name);
            holder.dateTxt = (TextView) convertView
                    .findViewById(R.id.task_date);

            convertView.setTag(holder);
        } else {
            holder = (TaskListAdapter.ViewHolder) convertView.getTag();
        }

        Task task = getItem(position);
        holder.tasksTxt.setText(task.toString());
        holder.dateTxt.setText(task.getDate());
        convertView
                .setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4
                        : Color.TRANSPARENT);

        return convertView;
    }

    @Override
    public void add(Task task) {
        tasks.add(task);
        notifyDataSetChanged();
        Toast.makeText(context, tasks.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void remove(Task object) {
        tasks.remove(object);
        notifyDataSetChanged();
    }
}
