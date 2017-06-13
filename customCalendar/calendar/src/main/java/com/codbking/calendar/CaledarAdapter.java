package com.codbking.calendar;

import android.view.View;
import android.view.ViewGroup;

import com.codbking.calendar.CalendarBean;

/**
 * Created by codbking on 2016/12/22.
 * email:codbking@gmail.com
 * github:https://github.com/codbking
 * blog:http://www.jianshu.com/users/49d47538a2dd/latest_articles
 */

public abstract class CaledarAdapter {
    private OnDataChangedListener mOnDataChangedListener;

    public abstract View getView(View convertView, ViewGroup parentView,int selectPosition, int position, CalendarBean bean);

    public abstract boolean isEnabled(int position, CalendarBean bean);

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public void notifyDataChanged() {
        mOnDataChangedListener.onChanged();
    }

    interface OnDataChangedListener {
        void onChanged();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
