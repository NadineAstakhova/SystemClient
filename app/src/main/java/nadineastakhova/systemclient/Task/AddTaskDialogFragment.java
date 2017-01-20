package nadineastakhova.systemclient.Task;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import nadineastakhova.systemclient.R;

/**
 * Created by Nadine on 09.11.2016.
 */

public class AddTaskDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener {

    private View form = null;
    private static String fk_sub = "";
    String newDate;

    public AddTaskDialogFragment() {  }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        form = getActivity().getLayoutInflater()
                .inflate(R.layout.task_add_form, null);
        System.out.println("TASKADD");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        DatePicker newDateBox = (DatePicker) form.findViewById(R.id.newDate);
        // Get a new Calendar instance
        Calendar calendar = Calendar.getInstance();
        // Get the Calendar current year, month and day of month
        int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);

        newDateBox.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Integer.toString(year) + '-' + Integer.toString(monthOfYear+1) + '-' + Integer.toString(dayOfMonth);
            }
        });

        return(builder.setTitle("Add new task").setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null).create());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        try {
            EditText newNameBox = (EditText) form.findViewById(R.id.newName);
            String newName = newNameBox.getText().toString();
            System.out.println("llll" + newDate);
            if (newName.length() < 1){
                throw new NullPointerException("No data");
            }
            else {
                Bundle bundle = this.getArguments();
                if (bundle != null) {
                    fk_sub = bundle.getString("FK_Subject");
                }
                newName = newName.replaceAll(" ", "_");

                Task newTask = new Task();
                newTask.add(newName, newDate, fk_sub);

                TaskListActivity act = (TaskListActivity) getActivity();
                act.setListView(act);
            }
        }
        catch (NullPointerException  e){
            Log.d("ERR", e.getMessage());
            TaskListActivity act = (TaskListActivity) getActivity();
            act.setListView(act);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onDismiss(DialogInterface unused) {
        super.onDismiss(unused);
    }

    @Override
    public void onCancel(DialogInterface unused) {
        super.onCancel(unused);
    }
}
