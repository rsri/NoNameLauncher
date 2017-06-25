package com.passtime.slauncher.fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.passtime.slauncher.R;
import com.passtime.slauncher.adapters.AppsListAdapter;
import com.passtime.slauncher.model.AppDetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppsListFragment extends Fragment {

    private List<AppDetail> mAppDetails;
    private AppsListAdapter mAppsListAdapter;
    public AppsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apps_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView appsListView = (ListView) view.findViewById(R.id.apps_list_lv);
        mAppDetails = fetchAppDetails(getActivity());
        mAppsListAdapter = new AppsListAdapter(mAppDetails);
        appsListView.setAdapter(mAppsListAdapter);
        appsListView.setOnItemClickListener(mAppItemClickListener);
    }

    private AdapterView.OnItemClickListener mAppItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position < mAppDetails.size()) {
                mAppsListAdapter.startAnimation(position);
                String selectedPackageName = mAppDetails.get(position).getAppPackage().toString();
                Intent i = getActivity().getPackageManager().getLaunchIntentForPackage(selectedPackageName);
                startActivity(i);
            }
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        mAppsListAdapter.stopAnimation();
    }

    private List<AppDetail> fetchAppDetails(Activity activity) {
        List<AppDetail> details = new ArrayList<>();
        PackageManager packageManager = activity.getPackageManager();
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
