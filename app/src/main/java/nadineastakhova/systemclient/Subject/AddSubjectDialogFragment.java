package nadineastakhova.systemclient.Subject;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import nadineastakhova.systemclient.R;

/**
 * Created by Nadine on 30.10.2016.
 */

public class AddSubjectDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener {

    private View form = null;
    private static String fk_prof = "";
    private ListView listView;

    public AddSubjectDialogFragment(ListView t) {
        this.listView = t;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        form = getActivity().getLayoutInflater()
                .inflate(R.layout.add_subject_form, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return(builder.setTitle("Add new subject").setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null).create());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        try {
            EditText newNameBox = (EditText) form.findViewById(R.id.newName);
            String newName = newNameBox.getText().toString();

            if (newName.length() < 1) {
                throw new NullPointerException("No data");
            }
            else {
                Bundle bundle = this.getArguments();
                if (bundle != null) {
                    fk_prof = bundle.getString("FK_Prof");
                }

                Subject newSubject = new Subject();
                newSubject.add(newName, fk_prof);

                SubjectListActivity act = (SubjectListActivity) getActivity();
                act.setListView(act);
            }
        }
        catch (NullPointerException  e){
            Log.d("ERR", e.getMessage());
            SubjectListActivity act = (SubjectListActivity) getActivity();
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
