package nadineastakhova.systemclient.Task;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import nadineastakhova.systemclient.ConnectToServer;
import nadineastakhova.systemclient.MainActivity;
import nadineastakhova.systemclient.R;
import nadineastakhova.systemclient.Subject.SubjectListActivity;
import nadineastakhova.systemclient.Works.WorksListActivity;

/**
 * Created by Nadine on 03.11.2016.
 */

public class TaskListActivity extends AppCompatActivity {

    private String idSubject = "";
    private String idProf = "";
    private static String username;
    private static String password;
    private ListView listView;
    private TextView textView;

    public TaskListAdapter mAdapter;
    private ActionMode mActionMode;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        idSubject = getIntent().getExtras().getString("idSubjectForTask");
        idProf = getIntent().getExtras().getString("idProf");
        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listView);
        textView = (TextView) findViewById(R.id.textView);
        setListView(this);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    boolean flag;
    ConnectToServer connect;
    boolean whenLongClick = false;

    public void setListView(final TaskListActivity act) {
        flag = false;
        final String enterToTask = "tasks/" + idSubject;
        Runnable runb = new Runnable() {
            @Override
            public void run() {
                connect = new ConnectToServer(MainActivity.GET, enterToTask);
                flag = true;
            }
        };

        Thread thr = new Thread(runb);
        thr.start();

        while (!flag) ;

        TaskList taskList = new TaskList(connect.getResultJSON(), textView);

        final List<Task> tasks = new ArrayList<Task>(taskList.getList());

        mAdapter = new TaskListAdapter(act,
                R.layout.list_item_task, tasks);

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                if(whenLongClick) return;
                Intent intent = new Intent(TaskListActivity.this, WorksListActivity.class);
                intent.putExtra("idTaskForWorks", tasks.get(position).getTaskId());
                intent.putExtra("idSubjectForTask", idSubject);
                intent.putExtra("idProf", idProf);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view, int position, long id) {
                onListItemSelect(position);
                whenLongClick = true;
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.AddTask:
                Bundle bundle = new Bundle();
                bundle.putString("FK_Subject", idSubject);
                AddTaskDialogFragment dialog = new AddTaskDialogFragment();
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "newName");
                mAdapter.notifyDataSetChanged();
                return true;
            case android.R.id.home:
                Intent intent = new Intent(this, SubjectListActivity.class);
                intent.putExtra("idProf", idProf);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("TaskList Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class ActionBarCallBack implements ActionMode.Callback {


        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // TODO Auto-generated method stub
            mode.getMenuInflater().inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.Delete:
                    // retrieve selected items and delete them out
                    SparseBooleanArray selected = mAdapter
                            .getSelectedIds();
                    String selectedId = "";
                    ArrayList<String> arrSelected = new ArrayList<>();
                    for (int i = (selected.size() - 1); i >= 0; i--) {
                        if (selected.valueAt(i)) {
                            Task selectedItem = mAdapter
                                    .getItem(selected.keyAt(i));
                            selectedId = selectedItem.getTaskId();
                            arrSelected.add(selectedItem.getTaskId());
                        }
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("idDeletedItem", selectedId);
                    bundle.putStringArrayList("arrTaskDeletedItem", arrSelected);
                    DeleteTaskDialogFragment dialog = new DeleteTaskDialogFragment();
                    dialog.setArguments(bundle);
                    dialog.show(getFragmentManager(), "nameDeletedItem");
                    mAdapter.notifyDataSetChanged();

                    mode.finish(); // Action picked, so close the CAB
                    return true;
                case R.id.Edit:
                    SparseBooleanArray selectedEdit = mAdapter
                            .getSelectedIds();
                    String selectedIdEdit = "";
                    String selectName = "";
                    for (int i = (selectedEdit.size() - 1); i >= 0; i--) {
                        if (selectedEdit.valueAt(i)) {
                            Task selectedItem = mAdapter
                                    .getItem(selectedEdit.keyAt(i));
                            selectedIdEdit = selectedItem.getTaskId();
                            selectName = selectedItem.toString();
                        }
                    }
                    Bundle bundleEdit = new Bundle();
                    bundleEdit.putString("idChangedItem", selectedIdEdit);
                    bundleEdit.putString("nameChangedTaskItem",selectName);
                    DatePickerFragment newEditDialog = new DatePickerFragment();
                    newEditDialog.setArguments(bundleEdit);
                    newEditDialog.show(getFragmentManager(), "datePicker");
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
            whenLongClick = false;
            mActionMode = null;
            // TODO Auto-generated method stub

        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // TODO Auto-generated method stub

            mode.setTitle("CheckBox is Checked");
            return false;
        }
    }

}
