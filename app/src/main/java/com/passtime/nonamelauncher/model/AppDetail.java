package com.passtime.nonamelauncher.model;

import android.graphics.drawable.Drawable;

/**
 * Created by srikaram on 02-Nov-16.
 */
public final class AppDetail {

    private CharSequence appName;
    private CharSequence appPackage;
    private Drawable appIcon;

    public CharSequence getAppName() {
        return appName;
    }

    public void setAppName(CharSequence appName) {
        this.appName = appName;
    }

    public CharSequence getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(CharSequence appPackage) {
        this.appPackage = appPackage;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
