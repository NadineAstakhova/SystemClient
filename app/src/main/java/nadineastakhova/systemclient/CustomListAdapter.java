package nadineastakhova.systemclient;

import android.app.Activity;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nadine on 07.11.2016.
 * Parent class for other ListAdapter in project
 */

public class CustomListAdapter<Object>  extends ArrayAdapter<Object> {

    Activity context;

    private SparseBooleanArray mSelectedItemsIds;

    public CustomListAdapter(Activity context, int resId, List<Object> objects) {
        super(context, resId, objects);
        this.mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    public int getSelectedCount() {
        System.out.println(mSelectedItemsIds.size());
        return mSelectedItemsIds.size();
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

}
