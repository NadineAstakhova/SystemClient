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
import android.widget.TextView;

import nadineastakhova.systemclient.R;

/**
 * Created by Nadine on 03.11.2016.
 */

public class EditSubjectDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener  {

    private View form = null;
    private static String idChangedItem = "";
    private static String nameChangedItem = "";
    private ListView listView;

    public EditSubjectDialogFragment() {super();}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        form = getActivity().getLayoutInflater()
                .inflate(R.layout.edit_subject_form, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        TextView textView = (TextView) form.findViewById(R.id.oldSubjectName);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            idChangedItem =  bundle.getString("idChangedItem");
            nameChangedItem =  bundle.getString("nameChangedItem");
        }

        textView.setText("Set new name for " + nameChangedItem);

         return(builder.setTitle("Change name").setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null).create());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        try {
            EditText newNameBox = (EditText) form.findViewById(R.id.newNameForSubject);
            String newName = newNameBox.getText().toString();
            if (newName.length() < 1){
                throw new NullPointerException("No data");
            }
            else {
                Subject changedSubject = new Subject();
                changedSubject.editName(idChangedItem, newName);

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
