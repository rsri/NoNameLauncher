package com.passtime.slauncher.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.passtime.slauncher.R;
import com.passtime.slauncher.model.AppDetail;

import java.util.List;

/**
 * Created by srikaram on 02-Nov-16.
 */
public class AppsListAdapter extends BaseAdapter {

    private List<AppDetail> appDetails;
    private boolean animate = false;
    private int animatePosition = -1;

    public AppsListAdapter(List<AppDetail> appDetails) {
        this.appDetails = appDetails;
    }

    @Override
    public int getCount() {
        return appDetails.size()+1;
    }

    public void startAnimation(int position) {
        animate = true;
        animatePosition = position;
        notifyDataSetChanged();
    }

    public void stopAnimation() {
        animate = false;
        animatePosition = -1;
        notifyDataSetChanged();
    }

    @Override
    public AppDetail getItem(int position) {
        if (position == appDetails.size()) {
            return null;
        }
        return appDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.app_list_item, parent, false);

            holder = new ViewHolder();
            holder.label = (TextView) convertView.findViewById(R.id.label_tv);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == appDetails.size()) {
            holder.label.setVisibility(View.INVISIBLE);
            holder.icon.setVisibility(View.INVISIBLE);
        } else {
            AppDetail appDetail = appDetails.get(position);
            int visibility = View.VISIBLE;
            if (animate && position != animatePosition) {
                visibility = View.INVISIBLE;
            }
            holder.label.setVisibility(visibility);
            holder.icon.setVisibility(visibility);
            holder.label.setText(appDetail.getAppName());
            holder.icon.setImageDrawable(appDetail.getAppIcon());
        }


        return convertView;
    }

    private final class ViewHolder {
        private TextView label;
        private ImageView icon;
    }
}
