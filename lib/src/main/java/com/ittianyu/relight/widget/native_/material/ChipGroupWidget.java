package com.ittianyu.relight.widget.native_.material;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.BoolRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.IdRes;
import android.support.design.chip.ChipGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.ViewGroupWidget;

public class ChipGroupWidget extends ViewGroupWidget<ChipGroup, ChipGroupWidget> {

    public ChipGroupWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    @Override
    public ChipGroup createView(Context context) {
        return new ChipGroup(context);
    }

    public ChipGroupWidget onHierarchyChange(OnHierarchyChangeListener listener) {
        view.setOnHierarchyChangeListener(listener);
        return self();
    }

    public ChipGroupWidget check(@IdRes int id) {
        view.check(id);
        return self();
    }

    public ChipGroupWidget clearCheck() {
        view.clearCheck();
        return self();
    }

    public ChipGroupWidget onCheckedChange(ChipGroup.OnCheckedChangeListener listener) {
        view.setOnCheckedChangeListener(listener);
        return self();
    }

    public ChipGroupWidget chipSpacing(@Dimension int chipSpacing) {
        view.setChipSpacing(chipSpacing);
        return self();
    }

    public ChipGroupWidget chipSpacingResource(@DimenRes int id) {
        view.setChipSpacingResource(id);
        return self();
    }

    public ChipGroupWidget chipSpacingHorizontal(@Dimension int chipSpacingHorizontal) {
        view.setChipSpacingHorizontal(chipSpacingHorizontal);
        return self();
    }

    public ChipGroupWidget chipSpacingHorizontalResource(@DimenRes int id) {
        view.setChipSpacingHorizontalResource(id);
        return self();
    }

    public ChipGroupWidget chipSpacingVertical(@Dimension int chipSpacingVertical) {
        view.setChipSpacingVertical(chipSpacingVertical);
        return self();
    }

    public ChipGroupWidget chipSpacingVerticalResource(@DimenRes int id) {
        view.setChipSpacingVerticalResource(id);
        return self();
    }

    public ChipGroupWidget singleLine(@BoolRes int id) {
        view.setSingleLine(id);
        return self();
    }

    public ChipGroupWidget singleSelection(boolean singleSelection) {
        view.setSingleSelection(singleSelection);
        return self();
    }

    public ChipGroupWidget singleSelection(@BoolRes int id) {
        view.setSingleSelection(id);
        return self();
    }


}
