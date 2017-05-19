package com.dmitriyvedmid.taskplaner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

/**
 * Created by dmitr on 4/23/2017.
 */

public class TaskPagerActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private List<Task> mTasks;
    private static final String EXTRA_TASK_ID = "com.dmitriyvedmid.taskintent.task_id";

    public static Intent newIntent(Context packageContext, UUID taskId){
        Intent intent = new Intent(packageContext, TaskPagerActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);
        mViewPager=(ViewPager)findViewById(R.id.activity_task_pager_view_pager);
        UUID taskId = (UUID)getIntent().getSerializableExtra(EXTRA_TASK_ID);

        mTasks = TaskLab.get(this).getTasks();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Task task = mTasks.get(position);
                return TaskFragment.newInstance(task.getId());
            }

            @Override
            public int getCount() {
                return mTasks.size();
            }
        });
        for (int i = 0; i<mTasks.size();i++){
            if (mTasks.get(i).getId().equals(taskId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
