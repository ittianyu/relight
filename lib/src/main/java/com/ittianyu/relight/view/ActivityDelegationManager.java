package com.ittianyu.relight.view;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

public class ActivityDelegationManager {

    private static WeakHashMap<Activity, Set<ActivityDelegation>> map = new WeakHashMap<>();

    public static void register(Activity activity, ActivityDelegation delegation) {
        add(activity, delegation);
    }

    public static void unregister(Activity activity, ActivityDelegation delegation) {
        remove(activity, delegation);
    }

    private static void add(Activity activity, ActivityDelegation delegation) {
        Set<ActivityDelegation> set = map.get(activity);
        if (null == set) {
            set = new HashSet<>();
            map.put(activity, set);
        }
        set.add(delegation);
    }

    private static void remove(Activity activity, ActivityDelegation delegation) {
        Set<ActivityDelegation> set = map.get(activity);
        if (null == set) {
            return;
        }
        set.remove(delegation);
        if (set.isEmpty()) {
            map.remove(activity);
        }
    }

    public static void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data) {
        Set<ActivityDelegation> set = map.get(activity);
        if (null == set) {
            return;
        }

        for (ActivityDelegation delegation : set) {
            delegation.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static boolean onKeyDown(Activity activity, int keyCode, KeyEvent event) {
        Set<ActivityDelegation> set = map.get(activity);
        if (null == set) {
            return false;
        }

        for (ActivityDelegation delegation : set) {
            if (delegation.onKeyDown(keyCode, event)) {
                return true;
            }
        }
        return false;
    }

}
