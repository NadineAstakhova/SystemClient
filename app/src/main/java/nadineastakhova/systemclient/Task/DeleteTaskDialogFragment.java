package nadineastakhova.systemclient.Task;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import nadineastakhova.systemclient.R;

/**
 * Created by Nadine on 09.11.2016.
 */

public class DeleteTaskDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener {
    private View form = null;
    private static String idDeletedItem ="";
    private ArrayList<String> arrDeletedItem = new ArrayList<>();

    public DeleteTaskDialogFragment() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        form = getActivity().getLayoutInflater()
                .inflate(R.layout.delete_subject_form, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        TextView textView = (TextView) form.findViewById(R.id.textSure);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            idDeletedItem =  bundle.getString("idDeletedItem");
            arrDeletedItem = bundle.getStringArrayList("arrTaskDeletedItem");
        }

        textView.setText("Are you sure?");

        return(builder.setTitle("Delete task").setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null).create());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        Task deletedTask = new Task();
        for(int i=0; i < arrDeletedItem.size(); i++)
            //deletedTask.remove(idDeletedItem);
            deletedTask.remove(arrDeletedItem.get(i));

        TaskListActivity act = (TaskListActivity)getActivity();
        act.setListView(act);

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
