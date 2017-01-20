package nadineastakhova.systemclient.Works;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nadineastakhova.systemclient.ConnectToServer;
import nadineastakhova.systemclient.MainActivity;
import nadineastakhova.systemclient.R;
import nadineastakhova.systemclient.Subject.SubjectListActivity;
import nadineastakhova.systemclient.Task.AddTaskDialogFragment;
import nadineastakhova.systemclient.Task.DatePickerFragment;
import nadineastakhova.systemclient.Task.DeleteTaskDialogFragment;
import nadineastakhova.systemclient.Task.Task;
import nadineastakhova.systemclient.Task.TaskList;
import nadineastakhova.systemclient.Task.TaskListActivity;
import nadineastakhova.systemclient.Task.TaskListAdapter;

/**
 * Created by Nadine on 15.11.2016.
 * Class interacts with the user
 */

public class WorksListActivity extends AppCompatActivity{

    private String idTask = "";
    private static String username;
    private static String password;
    private ListView listView;
    private TextView textView;

    public WorksListAdapter mAdapter;
    private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        //getting information about foreign keys
        idTask = getIntent().getExtras().getString("idTaskForWorks");
        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");
        System.out.println("IDTASK " + idTask);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listView);
        textView = (TextView) findViewById(R.id.textView);
        setListView(this);
    }

    boolean flag; //for thread...
    ConnectToServer connect;

    //set list for listview from server
    public void setListView(final WorksListActivity act) {
        flag = false;
        final String enterToWorks = "works/" + idTask;
        //getting information from server about works
        Runnable runb = new Runnable() {
            @Override
            public void run() {
                //send request
                connect = new ConnectToServer(MainActivity.GET, enterToWorks);
                flag = true;
            }
        };

        Thread thr = new Thread(runb);
        thr.start();
        while (!flag) ;

        //set list
        WorksList worksList = new WorksList(connect.getResultJSON(), textView);
        final List<Work> works = new ArrayList<Work>(worksList.getList());
        mAdapter = new WorksListAdapter(act,
                R.layout.list_item_work, works);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {}
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view, int position, long id) {
                System.out.println("CLICK");
                onListItemSelect(position);
                return true;
            }
        });
    }

    private void onListItemSelect(int position) {
        mAdapter.toggleSelection(position);
        boolean hasCheckedItems = mAdapter.getSelectedCount() > 0;
        if (hasCheckedItems && mActionMode == null)
            // there are some selected items, start the actionMode
            mActionMode = startSupportActionMode(new ActionBarCallBack());
        else if (!hasCheckedItems && mActionMode != null)
            // there no selected items, finish the actionMode
            mActionMode.finish();

        if (mActionMode != null)
            mActionMode.setTitle(String.valueOf(mAdapter
                    .getSelectedCount()) + " selected");
        if (mAdapter.getSelectedCount() > 1) {
            ActionMenuItemView btn = (ActionMenuItemView) findViewById(R.id.Edit);
            btn.setEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                String idSub = getIntent().getExtras().getString("idSubjectForTask");
                String idProf = getIntent().getExtras().getString("idProf");
                Intent intent = new Intent(this, TaskListActivity.class);
                intent.putExtra("idSubjectForTask", idSub);
                intent.putExtra("idProf", idProf);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

private class ActionBarCallBack implements ActionMode.Callback {
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // TODO Auto-generated method stub
        mode.getMenuInflater().inflate(R.menu.context_menu_works, menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.EditWork:
                SparseBooleanArray selectedEdit = mAdapter
                        .getSelectedIds();
                String selectedIdEdit = "";
                String selectName = "";
                for (int i = (selectedEdit.size() - 1); i >= 0; i--) {
                    if (selectedEdit.valueAt(i)) {
                        Work selectedItem = mAdapter
                                .getItem(selectedEdit.keyAt(i));
                        selectedIdEdit = selectedItem.getId();
                        selectName = selectedItem.toString();
                    }
                }
                Bundle bundleEdit = new Bundle();
                bundleEdit.putString("idChangedItem", selectedIdEdit);
                bundleEdit.putString("nameChangedWorkItem",selectName);
                ChangeWorkDialogFragment newEditDialog = new ChangeWorkDialogFragment();
                newEditDialog.setArguments(bundleEdit);
                newEditDialog.show(getFragmentManager(), "changeInfo");
                mAdapter.notifyDataSetChanged();
                mode.finish(); // Action picked, so close the CAB
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mAdapter.removeSelection();
        mActionMode = null;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        mode.setTitle("CheckBox is Checked");
        return false;
    }
}
}
