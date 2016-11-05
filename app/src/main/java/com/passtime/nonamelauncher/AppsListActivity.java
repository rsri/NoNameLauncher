package com.passtime.nonamelauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.passtime.nonamelauncher.adapters.AppsListAdapter;
import com.passtime.nonamelauncher.model.AppDetail;
import com.passtime.nonamelauncher.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppsListActivity extends Activity {

    private List<AppDetail> appDetails;
    private AppsListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_list);

        appDetails = fetchAppDetails();

        GridView gridView = (GridView) findViewById(R.id.apps_list_gv);
        gridView.setNumColumns(Constants.APPS_COLUMN_COUNT);
        listAdapter = new AppsListAdapter(appDetails);
        gridView.setAdapter(listAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                Intent i = getPackageManager().getLaunchIntentForPackage(appDetails.get(pos).getAppPackage().toString());
                AppsListActivity.this.startActivity(i);
            }
        });
    }

    private List<AppDetail> fetchAppDetails() {
        List<AppDetail> details = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> activitiesInfo = packageManager.queryIntentActivities(i, 0);
        for (ResolveInfo resolveInfo : activitiesInfo) {
            AppDetail detail = new AppDetail();
            detail.setAppName(resolveInfo.loadLabel(packageManager));
            detail.setAppPackage(resolveInfo.activityInfo.packageName);
            detail.setAppIcon(resolveInfo.loadIcon(packageManager));
            details.add(detail);
        }
        Collections.sort(details, new Comparator<AppDetail>() {
            @Override
            public int compare(AppDetail o1, AppDetail o2) {
                return o1.getAppName().toString().compareTo(o2.getAppName().toString());
            }
        });
        return details;
    }
}
