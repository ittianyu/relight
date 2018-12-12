package com.ittianyu.relight.widget.stateful.rm;

import com.ittianyu.relight.widget.stateful.lcee.LceeStatus;

/**
 * Created by 86839 on 2017/10/12.
 */

public enum RmStatus {
    Refreshing,
    RefreshContent,
    RefreshError,
    RefreshEmpty,
    LoadingMore,
    LoadMoreContent,
    LoadMoreError,
    LoadMoreEmpty;

    public LceeStatus toLceeStatus() {
        switch (this) {
            case Refreshing:
                return LceeStatus.Loading;
            case RefreshContent:
                return LceeStatus.Content;
            case RefreshError:
                return LceeStatus.Error;
            case RefreshEmpty:
                return LceeStatus.Empty;
            default:
                throw new IllegalStateException("can't convert " + this + " to LceeStatus");
        }
    }
}
