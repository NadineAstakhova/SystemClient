package nadineastakhova.systemclient.Groups;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import nadineastakhova.systemclient.ConnectToServer;
import nadineastakhova.systemclient.MainActivity;
import nadineastakhova.systemclient.Profile.ProfileActivity;
import nadineastakhova.systemclient.R;


/**
 * Created by Nadine on 20.11.2016.
 */

public class GroupsListActivity extends AppCompatActivity {

    public GroupsListAdapter mAdapter;
    private ActionMode mActionMode;

    String idProf ="";
    private static String username;
    private static String password;

    private ListView listView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = (ListView)findViewById(R.id.listView);
        registerForContextMenu(listView);

        idProf =  getIntent().getExtras().getString("idProf");
        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");
        textView =  (TextView) findViewById(R.id.textView);
        setListView(this);
    }


    boolean flag;
    ConnectToServer connect;
  //  boolean whenLongClick = false;

    public void setListView(final GroupsListActivity act)
    {
        flag = false;
        final String enterToGroup = "groups";
        Runnable runb = new Runnable() {
            @Override
            public void run() {
                connect = new ConnectToServer(MainActivity.GET, enterToGroup);
                flag = true;
            }
        };

        Thread thr =  new Thread(runb);
        thr.start();

        while (!flag);

        GroupsList groupList = new GroupsList(connect.getResultJSON(), textView);

        final List<Group> groups = new ArrayList<>(groupList.getList());

        mAdapter = new GroupsListAdapter(act,
                R.layout.list_item, groups);

        listView.setAdapter(mAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //if(whenLongClick) return;
                /*Intent intent = new Intent(SubjectListActivity.this, TaskListActivity.class);
                intent.putExtra("idSubjectForTask", groups.get(position).getId());
                intent.putExtra("idProf", idProf);
                startActivity(intent);*/
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view, int position, long id) {
                onListItemSelect(position);
                //whenLongClick = true;
                return true;
            }

        });
    }



    private void onListItemSelect(int position) {
        mAdapter.toggleSelection(position);
        boolean hasCheckedItems = mAdapter.getSelectedCount() > 0;

        if (hasCheckedItems && mActionMode == null)
            // there are some selected items, start the actionMode
            mActionMode = startSupportActionMode( new GroupsListActivity.ActionBarCallBack());
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
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Add:
                AddGroupDialogFragment dialog = new AddGroupDialogFragment();
                dialog.show(getFragmentManager(),"newName");
                mAdapter.notifyDataSetChanged();
                return true;
            case android.R.id.home:
                Intent intent = new Intent(this, ProfileActivity.class);
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
                            Group selectedItem = mAdapter
                                    .getItem(selected.keyAt(i));
                            selectedId = selectedItem.getIdGroup();
                            arrSelected.add(selectedItem.getIdGroup());
                        }
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("idDeletedItem", selectedId);
                    bundle.putStringArrayList("arrGroupDeletedItem", arrSelected);
                    DeleteGroupDialogFragment dialog = new DeleteGroupDialogFragment();
                    dialog.setArguments(bundle);
                    dialog.show(getFragmentManager(),"nameDeletedItem");
                    mAdapter.notifyDataSetChanged();

                    mode.finish(); // Action picked, so close the CAB
                    return true;
                case R.id.Edit:
                    // retrieve selected items and delete them out
                    SparseBooleanArray selectedEdit = mAdapter
                            .getSelectedIds();
                    String selectId = "";
                    String selectName = "";
                    for (int i = (selectedEdit.size() - 1); i >= 0; i--) {
                        if (selectedEdit.valueAt(i)) {
                            Group selectedItem = mAdapter
                                    .getItem(selectedEdit.keyAt(i));
                            selectId = selectedItem.getIdGroup();
                            selectName = selectedItem.toString();
                        }
                    }
                    System.out.print("SIZE" + selectedEdit.size());
                    Bundle bundleEdit = new Bundle();
                    bundleEdit.putString("idChangedItem", selectId);
                    bundleEdit.putString("nameChangedItem", selectName);
                    EditGroupDialogFragment dialogEdit = new EditGroupDialogFragment();
                    dialogEdit.setArguments(bundleEdit);
                    dialogEdit.show(getFragmentManager(),"idChangedItem");
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
