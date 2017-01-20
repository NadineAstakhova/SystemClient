package nadineastakhova.systemclient.Works;

import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import nadineastakhova.systemclient.CustomListAdapter;
import nadineastakhova.systemclient.R;

/**
 * Created by Nadine on 15.11.2016.
 */

public class WorksListAdapter extends CustomListAdapter<Work> {

    Activity context;
    List<Work> works;
    private SparseBooleanArray mSelectedItemsIds;
    /*customButtonListener customListner;

    public interface customButtonListener {
        public void onButtonClickListner(int position, String value, String name);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }*/


    public WorksListAdapter(Activity context, int resId, List<Work> works) {
        super(context, resId, works);
        this.mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.works = works;
    }

    private class ViewHolder {
        TextView workTxt;
        TextView statusTxt;
        TextView dateTxt;
        TextView markTxt;
        //ImageButton img;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        WorksListAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_work, null);
            holder = new WorksListAdapter.ViewHolder();
            holder.workTxt = (TextView) convertView
                    .findViewById(R.id.work_name);
            holder.statusTxt = (TextView) convertView
                    .findViewById(R.id.work_status);
            holder.dateTxt = (TextView) convertView
                    .findViewById(R.id.work_date);
            holder.markTxt = (TextView) convertView
                    .findViewById(R.id.work_mark);
           // holder.img = (ImageButton) convertView.findViewById(R.id.imgb);


            convertView.setTag(holder);
        } else {
            holder = (WorksListAdapter.ViewHolder) convertView.getTag();
        }

        final Work work = getItem(position);
        holder.workTxt.setText(work.toString());
        holder.statusTxt.setText(String.format("Status: %s", work.getStatus()));
        holder.dateTxt.setText(String.format("Date: %s", work.getComp_date()));
        holder.markTxt.setText(String.format("Mark: %s", work.getMark()));

        convertView
                .setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4
                        : Color.TRANSPARENT);

      /*  holder.img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position,work.getId(), work.toString());
                }

            }
        });*/

        return convertView;
    }

    @Override
    public void add(Work work) {
        works.add(work);
        notifyDataSetChanged();
        Toast.makeText(context, works.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void remove(Work object) {
        works.remove(object);
        notifyDataSetChanged();
    }




}
