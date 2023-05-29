package com.out.workout.ui.adapter;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


public final class SliderLayoutManager extends LinearLayoutManager {
    private OnItemSelectedListener callback;
    private RecyclerView recyclerView;

    public interface OnItemSelectedListener {
        void onItemSelected(int i);
    }

    public SliderLayoutManager(Context context) {
        super(context);
        setOrientation(RecyclerView.HORIZONTAL);
    }

    private int getRecyclerViewCenterX() {
        int val = 0;
        if (recyclerView != null) {
            int right = recyclerView.getRight();
            if (recyclerView != null) {
                int left = (right - recyclerView.getLeft()) / 2;
                if (recyclerView != null) {
                    val= left + recyclerView.getLeft();
                }
            }
        }
        return val;
    }

    private void scaleDownView() {
        float width = getWidth() / 2.0f;
        int childCount = getChildCount();
        if (childCount <= 0) {
            return;
        }
        int i = 0;
        while (true) {
            int i2 = i + 1;
            View childAt = getChildAt(i);
            float sqrt = 1.0f - (((float) Math.sqrt(Math.abs(width - ((getDecoratedLeft(childAt) + getDecoratedRight(childAt)) / 2.0f)) / getWidth())) * 0.66f);
            childAt.setScaleX(sqrt);
            childAt.setScaleY(sqrt);
            childAt.setAlpha(((double) sqrt) < 0.9d ? 0.2f : 1.0f);
            if (i2 >= childCount) {
                return;
            }
            i = i2;
        }
    }

    @Override
    public void onAttachedToWindow(RecyclerView recyclerView) {
        super.onAttachedToWindow(recyclerView);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        if (recyclerView != null) {
            linearSnapHelper.attachToRecyclerView(recyclerView);
        } else {
            throw null;
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        scaleDownView();
    }

    @Override
    public void onScrollStateChanged(int i) {
        super.onScrollStateChanged(i);
        int i2 = 0;
        if (Integer.valueOf(i).equals(0)) {
            int recyclerViewCenterX = getRecyclerViewCenterX();
            if (recyclerView != null) {
                int width = recyclerView.getWidth();
                int i3 = -1;
                if (recyclerView != null) {
                    int childCount = recyclerView.getChildCount();
                    if (childCount > 0) {
                        while (true) {
                            int i4 = i2 + 1;
                            if (recyclerView != null) {
                                View childAt = recyclerView.getChildAt(i2);
                                int abs = Math.abs((getDecoratedLeft(childAt) + ((getDecoratedRight(childAt) - getDecoratedLeft(childAt)) / 2)) - recyclerViewCenterX);
                                if (abs < width) {
                                    if (recyclerView != null) {
                                        i3 = recyclerView.getChildLayoutPosition(childAt);
                                        width = abs;
                                    } else {
                                    }
                                }
                                if (i4 >= childCount) {
                                    break;
                                }
                                i2 = i4;
                            } else {
                            }
                        }
                    }
                    OnItemSelectedListener onItemSelectedListener = this.callback;
                    if (onItemSelectedListener != null) {
                        onItemSelectedListener.onItemSelected(i3);
                    }
                }
            }
        }
    }

    @Override
    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getOrientation() != RecyclerView.HORIZONTAL) {
            return 0;
        }
        int scrollHorizontallyBy = super.scrollHorizontallyBy(i, recycler, state);
        scaleDownView();
        return scrollHorizontallyBy;
    }

    public void setCallback(OnItemSelectedListener onItemSelectedListener) {
        this.callback = onItemSelectedListener;
    }
}
