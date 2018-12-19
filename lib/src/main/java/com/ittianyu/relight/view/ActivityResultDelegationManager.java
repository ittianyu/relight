package com.ittianyu.relight.view;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

public class ActivityResultDelegationManager {

    private static WeakHashMap<Activity, Set<ActivityResultDelegation>> map = new WeakHashMap<>();

    public static void register(Activity activity, ActivityResultDelegation delegation) {
        add(activity, delegation);
    }

    public static void unregister(Activity activity, ActivityResultDelegation delegation) {
        remove(activity, delegation);
    }

    private static void add(Activity activity, ActivityResultDelegation delegation) {
        Set<ActivityResultDelegation> set = map.get(activity);
        if (null == set) {
            set = new HashSet<>();
            map.put(activity, set);
        }
        set.add(delegation);
    }

    private static void remove(Activity activity, ActivityResultDelegation delegation) {
        Set<ActivityResultDelegation> set = map.get(activity);
        if (null == set) {
            return;
        }
        set.remove(delegation);
        if (set.isEmpty()) {
            map.remove(activity);
        }
    }

    public static void onActivityResult(Activity activity, int requestCode, int resultCode,
        @Nullable Intent data) {
        Set<ActivityResultDelegation> set = map.get(activity);
        if (null == set) {
            return;
        }

        for (ActivityResultDelegation delegation : set) {
            delegation.onActivityResult(requestCode, resultCode, data);
        }
    }
}
