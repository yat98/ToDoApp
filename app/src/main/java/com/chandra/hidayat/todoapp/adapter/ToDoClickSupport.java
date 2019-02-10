package com.chandra.hidayat.todoapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chandra.hidayat.todoapp.R;

public class ToDoClickSupport {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getPosition(), v);
            }
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getPosition(), v);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mOnAttach = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }

        }

        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {

        }
    };

    public ToDoClickSupport(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mOnAttach);
    }

    public static ToDoClickSupport addTo(RecyclerView recyclerView) {
        ToDoClickSupport support = (ToDoClickSupport) recyclerView.getTag(R.id.item_click_support);
        if (support == null) {
            support = new ToDoClickSupport(recyclerView);
        }
        return support;
    }

    public static ToDoClickSupport removeTo(RecyclerView recyclerView) {
        ToDoClickSupport support = (ToDoClickSupport) recyclerView.getTag(R.id.item_click_support);
        if (support == null) {
            support.deatach(recyclerView);
        }
        return support;
    }

    private void deatach(RecyclerView recyclerView) {
        recyclerView.removeOnChildAttachStateChangeListener(mOnAttach);
        recyclerView.setTag(R.id.item_click_support, null);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}
