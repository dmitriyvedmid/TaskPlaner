package com.dmitriyvedmid.taskplaner.TaskIntent;

import android.support.v4.app.Fragment;

/**
 * Created by dmitr on 4/22/2017.
 */

public class TaskListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new TaskListFragment();
    }
}
