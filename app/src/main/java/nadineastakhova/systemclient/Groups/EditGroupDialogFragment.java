package nadineastakhova.systemclient.Groups;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nadineastakhova.systemclient.R;

/**
 * Created by Nadine on 21.11.2016.
 */

public class EditGroupDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener {
    private View form = null;
    private static String idChangedItem = "";
    private static String nameChangedItem = "";
    private EditText newNameBox;
    private boolean flag = true;

    public EditGroupDialogFragment(){super();}

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
        newNameBox = (EditText) form.findViewById(R.id.newNameForSubject);
        newNameBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                TextView textErr = (TextView) form.findViewById(R.id.errName);
                if(s.length() > 1){
                    Pattern p = Pattern.compile("^[A-zА-яЁё]+_{1}[a-zA-Z0-9]+$");
                    Matcher m = p.matcher(s.toString());
                    if(!m.matches()) {
                        textErr.setText("Not valid name. Example: Group_1");
                        flag = false;

                    }
                    else {
                        textErr.setText("");
                        flag = true;
                    }
                }
                else {
                    textErr.setText("");
                }

            }
        });

        return(builder.setTitle("Change name").setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null).create());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        try {

            String newName = newNameBox.getText().toString();
            if (newName.length() < 1 || !flag){
                throw new NullPointerException("No data");
            }
            else {
                Group changedGroup = new Group();
                changedGroup.editGroup(idChangedItem, newName);

                GroupsListActivity act = (GroupsListActivity) getActivity();
                act.setListView(act);
            }
        }
        catch (NullPointerException  e){
            Log.d("ERR", e.getMessage());
            GroupsListActivity act = (GroupsListActivity) getActivity();
            act.setListView(act);
        }
    }


    @Override
    public void onDismiss(DialogInterface unused) {
        super.onDismiss(unused);
    }

    @Override
    public void onCancel(DialogInterface unused) {
        super.onCancel(unused);
    }
}
