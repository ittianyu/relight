package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.view.View;
import android.view.animation.Interpolator;

public class RecyclerWidget<V extends RecyclerView.Adapter> extends BaseAndroidWidget<RecyclerView, RecyclerWidget> {
    public V adapter;

    public RecyclerWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    public RecyclerView createView(Context context) {
        return new RecyclerView(context);
    }

    @Override
    public void initView(RecyclerView view) {
        super.initView(view);
        view.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initProps() {
    }

    public RecyclerWidget<V> layoutManager(RecyclerView.LayoutManager layoutManager) {
        view.setLayoutManager(layoutManager);
        return self();
    }

    public RecyclerWidget<V> adapter(V adapter) {
        this.adapter = adapter;
        view.setAdapter(adapter);
        return self();
    }

    public RecyclerWidget<V> hasFixedSize(Boolean hasFixedSize) {
        view.setHasFixedSize(hasFixedSize);
        return self();
    }

    public RecyclerWidget<V> nestedScrollingEnabled(Boolean nestedScrollingEnabled) {
        view.setNestedScrollingEnabled(nestedScrollingEnabled);
        return self();
    }

    public RecyclerWidget<V> accessibilityDelegateCompat(@Nullable RecyclerViewAccessibilityDelegate accessibilityDelegate) {
        view.setAccessibilityDelegateCompat(accessibilityDelegate);
        return self();
    }

    public RecyclerWidget<V> clipToPadding(boolean clipToPadding) {
        view.setClipToPadding(clipToPadding);
        return self();
    }

    public RecyclerWidget<V> scrollingTouchSlop(int slopConstant) {
        view.setScrollingTouchSlop(slopConstant);
        return self();
    }

    public RecyclerWidget<V> swapAdapter(@Nullable RecyclerView.Adapter adapter, boolean removeAndRecycleExistingViews) {
        view.swapAdapter(adapter, removeAndRecycleExistingViews);
        return self();
    }

    public RecyclerWidget<V> recyclerListener(@Nullable RecyclerView.RecyclerListener listener) {
        view.setRecyclerListener(listener);
        return self();
    }

    public RecyclerWidget<V> addOnChildAttachStateChangeListener(@NonNull RecyclerView.OnChildAttachStateChangeListener listener) {
        view.addOnChildAttachStateChangeListener(listener);
        return self();
    }

    public RecyclerWidget<V> removeOnChildAttachStateChangeListener(@NonNull RecyclerView.OnChildAttachStateChangeListener listener) {
        view.removeOnChildAttachStateChangeListener(listener);
        return self();
    }

    public RecyclerWidget<V> clearOnChildAttachStateChangeListeners() {
        view.clearOnChildAttachStateChangeListeners();
        return self();
    }

    public RecyclerWidget<V> onFling(@Nullable RecyclerView.OnFlingListener onFlingListener) {
        view.setOnFlingListener(onFlingListener);
        return self();
    }

    public RecyclerWidget<V> recycledViewPool(@Nullable RecyclerView.RecycledViewPool pool) {
        view.setRecycledViewPool(pool);
        return self();
    }

    public RecyclerWidget<V> viewCacheExtension(@Nullable RecyclerView.ViewCacheExtension extension) {
        view.setViewCacheExtension(extension);
        return self();
    }

    public RecyclerWidget<V> itemViewCacheSize(int size) {
        view.setItemViewCacheSize(size);
        return self();
    }

    public RecyclerWidget<V> addItemDecoration(@NonNull RecyclerView.ItemDecoration decor, int index) {
        view.addItemDecoration(decor, index);
        return self();
    }

    public RecyclerWidget<V> addItemDecoration(@NonNull RecyclerView.ItemDecoration decor) {
        view.addItemDecoration(decor);
        return self();
    }

    public RecyclerWidget<V> removeItemDecorationAt(int index) {
        view.removeItemDecorationAt(index);
        return self();
    }

    public RecyclerWidget<V> removeItemDecoration(@NonNull RecyclerView.ItemDecoration decor) {
        view.removeItemDecoration(decor);
        return self();
    }

    public RecyclerWidget<V> childDrawingOrderCallback(@Nullable RecyclerView.ChildDrawingOrderCallback childDrawingOrderCallback) {
        view.setChildDrawingOrderCallback(childDrawingOrderCallback);
        return self();
    }

    public RecyclerWidget<V> addOnScrollListener(@NonNull RecyclerView.OnScrollListener listener) {
        view.addOnScrollListener(listener);
        return self();
    }

    public RecyclerWidget<V> removeOnScrollListener(@NonNull RecyclerView.OnScrollListener listener) {
        view.removeOnScrollListener(listener);
        return self();
    }

    public RecyclerWidget<V> clearOnScrollListeners() {
        view.clearOnScrollListeners();
        return self();
    }

    public RecyclerWidget<V> scrollToPosition(int position) {
        view.scrollToPosition(position);
        return self();
    }

    public RecyclerWidget<V> smoothScrollToPosition(int position) {
        view.smoothScrollToPosition(position);
        return self();
    }

    public RecyclerWidget<V> scrollTo(int x, int y) {
        view.scrollTo(x, y);
        return self();
    }

    public RecyclerWidget<V> scrollBy(int x, int y) {
        view.scrollBy(x, y);
        return self();
    }

    public RecyclerWidget<V> computeHorizontalScrollOffset() {
        view.computeHorizontalScrollOffset();
        return self();
    }

    public RecyclerWidget<V> computeHorizontalScrollExtent() {
        view.computeHorizontalScrollExtent();
        return self();
    }

    public RecyclerWidget<V> computeHorizontalScrollRange() {
        view.computeHorizontalScrollRange();
        return self();
    }

    public RecyclerWidget<V> computeVerticalScrollOffset() {
        view.computeVerticalScrollOffset();
        return self();
    }

    public RecyclerWidget<V> computeVerticalScrollExtent() {
        view.computeVerticalScrollExtent();
        return self();
    }

    public RecyclerWidget<V> computeVerticalScrollRange() {
        view.computeVerticalScrollRange();
        return self();
    }

    public RecyclerWidget<V> layoutFrozen(boolean frozen) {
        view.setLayoutFrozen(frozen);
        return self();
    }

    public RecyclerWidget<V> smoothScrollBy(@Px int dx, @Px int dy) {
        view.smoothScrollBy(dx, dy);
        return self();
    }

    public RecyclerWidget<V> smoothScrollBy(@Px int dx, @Px int dy, @Nullable Interpolator interpolator) {
        view.smoothScrollBy(dx, dy, interpolator);
        return self();
    }

    public boolean fling(int velocityX, int velocityY) {
        return view.fling(velocityX, velocityY);
    }

    public RecyclerWidget<V> stopScroll() {
        view.stopScroll();
        return self();
    }

    public RecyclerWidget<V> edgeEffectFactory(@NonNull RecyclerView.EdgeEffectFactory edgeEffectFactory) {
        view.setEdgeEffectFactory(edgeEffectFactory);
        return self();
    }

    public RecyclerWidget<V> focusSearch(View focused, int direction) {
        view.focusSearch(focused, direction);
        return self();
    }

    public RecyclerWidget<V> requestChildFocus(View child, View focused) {
        view.requestChildFocus(child, focused);
        return self();
    }

    public boolean requestChildRectangleOnScreen(View child, Rect rect, boolean immediate) {
        return view.requestChildRectangleOnScreen(child, rect, immediate);
    }

    public boolean isAttachedToWindow() {
        return view.isAttachedToWindow();
    }

    public RecyclerWidget<V> addOnItemTouchListener(@NonNull RecyclerView.OnItemTouchListener listener) {
        view.addOnItemTouchListener(listener);
        return self();
    }

    public RecyclerWidget<V> removeOnItemTouchListener(@NonNull RecyclerView.OnItemTouchListener listener) {
        view.removeOnItemTouchListener(listener);
        return self();
    }

    public RecyclerWidget<V> itemAnimator(@Nullable RecyclerView.ItemAnimator animator) {
        view.setItemAnimator(animator);
        return self();
    }

    public RecyclerWidget<V> invalidateItemDecorations() {
        view.invalidateItemDecorations();
        return self();
    }

    public RecyclerWidget<V> preserveFocusAfterLayout(boolean preserveFocusAfterLayout) {
        view.setPreserveFocusAfterLayout(preserveFocusAfterLayout);
        return self();
    }

    public View findContainingItemView(@NonNull View view) {
        return this.view.findContainingItemView(view);
    }

    public int getChildAdapterPosition(@NonNull View child) {
        return this.view.getChildAdapterPosition(child);
    }

    public int getChildLayoutPosition(@NonNull View child) {
        return this.view.getChildLayoutPosition(child);
    }

    public long getChildItemId(@NonNull View child) {
        return this.view.getChildItemId(child);
    }

    public View findChildViewUnder(float x, float y) {
        return this.view.findChildViewUnder(x, y);
    }

    public RecyclerWidget<V> offsetChildrenVertical(@Px int dy) {
        view.offsetChildrenVertical(dy);
        return self();
    }

    public RecyclerWidget<V> offsetChildrenHorizontal(@Px int dx) {
        view.offsetChildrenHorizontal(dx);
        return self();
    }

    public boolean startNestedScroll(int axes) {
        return view.startNestedScroll(axes);
    }

    public boolean startNestedScroll(int axes, int type) {
        return view.startNestedScroll(axes, type);
    }

    public RecyclerWidget<V> stopNestedScroll() {
        view.stopNestedScroll();
        return self();
    }

    public RecyclerWidget<V> stopNestedScroll(int type) {
        view.stopNestedScroll(type);
        return self();
    }


}
