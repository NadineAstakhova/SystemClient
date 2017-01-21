package nadineastakhova.systemclient.Works;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.IllegalFormatException;

import nadineastakhova.systemclient.R;

/**
 * Created by Nadine on 16.11.2016.
 * Class for dialog window
 */

public class ChangeWorkDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener  {

    private View form = null;
    private static String idChangedItem = "";
    private static String nameChangedItem = "";
    private ListView listView;
    public final String STATUS[] = {Work.NEW, Work.ACCEPT, Work.NO_ACCEPT};
    Spinner spinner;
    EditText newMarkBox;

    private boolean flag = true;

    public ChangeWorkDialogFragment() {super();}

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        form = getActivity().getLayoutInflater()
                .inflate(R.layout.change_info_work_form, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            idChangedItem =  bundle.getString("idChangedItem");
            nameChangedItem =  bundle.getString("nameChangedWorkItem");
        }

        final TextView textView = (TextView) form.findViewById(R.id.nameWork);
        textView.setText("Task: " + nameChangedItem);

        spinner = (Spinner)form.findViewById(R.id.newStatus);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(form.getContext(), android.R.layout.simple_spinner_item, STATUS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        newMarkBox = (EditText) form.findViewById(R.id.newMark);
        newMarkBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}

            //for dynamic input check 
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() > 1){
                    TextView textErr = (TextView) form.findViewById(R.id.errMark);
                    int num = Integer.parseInt(s.toString());
                    if(num < 0 || num > 100) {
                        textErr.setText("Must be less than 101");
                        flag = false;
                    }
                    else {
                        textErr.setText("");
                        flag = true;
                    }
                }
            }
        });

        return(builder.setTitle("Change information").setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null).create());
    }

    //when user click on OK button
    @Override
    public void onClick(DialogInterface dialog, int which) throws IllegalFormatException {
        try {
            String selectedStatus = spinner.getSelectedItem().toString();

            String newMark = newMarkBox.getText().toString();

            if (((newMark.length() < 1) && (selectedStatus.equals(Work.NEW))) || (!flag)){
                throw new NullPointerException("No data");
            }
            else {
                Work changedWork = new Work();
                //edit info in database from Work Class
                changedWork.editInfo(idChangedItem, selectedStatus, newMark);

                WorksListActivity act = (WorksListActivity) getActivity();
                act.setListView(act);
            }
        }
        catch(NullPointerException  e){
            WorksListActivity act = (WorksListActivity) getActivity();
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
