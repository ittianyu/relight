package com.ittianyu.relight.utils;

import android.animation.Animator;
import android.animation.StateListAnimator;
import android.content.res.ColorStateList;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;

public abstract class StateListBuilder<V, T> {
    protected List<Integer> states = new ArrayList<>();
    protected List<V> values = new ArrayList<>();

    public StateListBuilder<V, T> normal(V value) {
        return add(0, value);
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public StateListBuilder<V, T> listAnimator(V value) {
        return add(android.R.attr.stateListAnimator, value);
    }

    public StateListBuilder<V, T> notNeeded(V value) {
        return add(android.R.attr.stateNotNeeded, value);
    }

    public StateListBuilder<V, T> aboveAnchor(V value) {
        return add(android.R.attr.state_above_anchor, value);
    }

    public StateListBuilder<V, T> accelerated(V value) {
        return add(android.R.attr.state_accelerated, value);
    }

    public StateListBuilder<V, T> activated(V value) {
        return add(android.R.attr.state_activated, value);
    }

    public StateListBuilder<V, T> active(V value) {
        return add(android.R.attr.state_active, value);
    }

    public StateListBuilder<V, T> checkable(V value) {
        return add(android.R.attr.state_checkable, value);
    }

    public StateListBuilder<V, T> checked(V value) {
        return add(android.R.attr.state_checked, value);
    }

    public StateListBuilder<V, T> dragCanAccept(V value) {
        return add(android.R.attr.state_drag_can_accept, value);
    }

    public StateListBuilder<V, T> dragHovered(V value) {
        return add(android.R.attr.state_drag_hovered, value);
    }

    public StateListBuilder<V, T> empty(V value) {
        return add(android.R.attr.state_empty, value);
    }

    public StateListBuilder<V, T> enabled(V value) {
        return add(android.R.attr.state_enabled, value);
    }

    public StateListBuilder<V, T> expanded(V value) {
        return add(android.R.attr.state_expanded, value);
    }

    public StateListBuilder<V, T> first(V value) {
        return add(android.R.attr.state_first, value);
    }

    public StateListBuilder<V, T> focused(V value) {
        return add(android.R.attr.state_focused, value);
    }

    public StateListBuilder<V, T> hovered(V value) {
        return add(android.R.attr.state_hovered, value);
    }

    public StateListBuilder<V, T> last(V value) {
        return add(android.R.attr.state_last, value);
    }

    public StateListBuilder<V, T> longPressable(V value) {
        return add(android.R.attr.state_long_pressable, value);
    }

    public StateListBuilder<V, T> middle(V value) {
        return add(android.R.attr.state_middle, value);
    }

    public StateListBuilder<V, T> multiline(V value) {
        return add(android.R.attr.state_multiline, value);
    }

    public StateListBuilder<V, T> pressed(V value) {
        return add(android.R.attr.state_pressed, value);
    }

    public StateListBuilder<V, T> selected(V value) {
        return add(android.R.attr.state_selected, value);
    }

    public StateListBuilder<V, T> single(V value) {
        return add(android.R.attr.state_single, value);
    }

    public StateListBuilder<V, T> windowFocused(V value) {
        return add(android.R.attr.state_window_focused, value);
    }

    protected StateListBuilder<V, T> add(int state, V value) {
        states.add(state);
        values.add(value);
        return this;
    }

    protected int[][] getStates() {
        int[][] result = new int[states.size()][1];
        for (int i = 0; i < states.size(); i++) {
            result[i] = new int[] {states.get(i)};
        }
        return result;
    }

    protected V[] getValues(V[] result) {
        for (int i = 0; i < result.length; i++) {
            result[i] = values.get(i);
        }
        return result;
    }

    protected int[] toIntArray(List<Integer> list) {
        int[] result = new int[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public abstract T build();

    public static StateListBuilder<Integer, ColorStateList> colorBuilder() {
        return new StateListBuilder<Integer, ColorStateList>() {
            @Override
            public ColorStateList build() {
                return new ColorStateList(getStates(), toIntArray(values));
            }
        };
    }

    public static StateListBuilder<Drawable, StateListDrawable> drawableBuilder() {
        return new StateListBuilder<Drawable, StateListDrawable>() {
            @Override
            public StateListDrawable build() {
                StateListDrawable drawable = new StateListDrawable();
                int[][] states = getStates();
                Drawable[] values = getValues(new Drawable[this.values.size()]);
                for (int i = 0; i < states.length; i++) {
                    drawable.addState(states[i], values[i]);
                }
                return drawable;
            }
        };
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public static StateListBuilder<Drawable, AnimatedStateListDrawable> animatedDrawableBuilder() {
        return new StateListBuilder<Drawable, AnimatedStateListDrawable>() {
            @Override
            public AnimatedStateListDrawable build() {
                AnimatedStateListDrawable drawable = new AnimatedStateListDrawable();
                int[][] states = getStates();
                Drawable[] values = getValues(new Drawable[this.values.size()]);
                for (int i = 0; i < states.length; i++) {
                    drawable.addState(states[i], values[i]);
                }
                return drawable;
            }
        };
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public static StateListBuilder<Animator, StateListAnimator> animatorBuilder() {
        return new StateListBuilder<Animator, StateListAnimator>() {
            @Override
            public StateListAnimator build() {
                StateListAnimator drawable = new StateListAnimator();
                int[][] states = getStates();
                Animator[] values = getValues(new Animator[this.values.size()]);
                for (int i = 0; i < states.length; i++) {
                    drawable.addState(states[i], values[i]);
                }
                return drawable;
            }
        };
    }

}
