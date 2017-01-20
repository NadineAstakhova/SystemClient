package nadineastakhova.systemclient.Task;

/**
 * Created by Nadine on 15.11.2016.
 */
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static String idChangedItem = "";
    private static String nameChangedItem = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            idChangedItem =  bundle.getString("idChangedItem");
            nameChangedItem =  bundle.getString("nameChangedTaskItem");
        }
            System.out.println("New id   "+ idChangedItem);
        DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
        dpd.setTitle("Set a Date for "  + nameChangedItem);
        // Create a new instance of DatePickerDialog and return it
        return dpd;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        String newDate = Integer.toString(year) + '-' + Integer.toString(month+1) + '-' + Integer.toString(day) ;
        System.out.println("New date "+ newDate);
        Task task = new Task();
        task.editTask(idChangedItem, newDate);
        TaskListActivity act = (TaskListActivity)getActivity();
        act.setListView(act);
    }
}
