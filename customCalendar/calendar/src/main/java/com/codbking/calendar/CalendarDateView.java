package com.codbking.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static com.codbking.calendar.CalendarFactory.getMonthOfDayList;

/**
 * Created by codbking on 2016/12/18.
 * email:codbking@gmail.com
 * github:https://github.com/codbking
 * blog:http://www.jianshu.com/users/49d47538a2dd/latest_articles
 */

public class CalendarDateView extends ViewPager implements CalendarTopView {

    HashMap<Integer, CalendarView> views = new HashMap<>();
    private CaledarTopViewChangeListener mCaledarLayoutChangeListener;
    private CalendarView.OnItemClickListener onItemClickListener;
    private SimpleOnPageChangeListener pageChangeListener;

    private LinkedList<CalendarView> cache = new LinkedList();

    private int MAXCOUNT = 6;


    private int row = 6;

    private CaledarAdapter mAdapter;
    private int calendarItemHeight = 0;
    private Calendar mCalendar;

    public void setAdapter(CaledarAdapter adapter) {
        mAdapter = adapter;
        initData();

    }

    public void setOnItemClickListener(CalendarView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CalendarDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CalendarDateView);
        row = a.getInteger(R.styleable.CalendarDateView_cbd_calendar_row, 6);
        a.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int calendarHeight = 0;
        if (getAdapter() != null) {
            CalendarView view = (CalendarView) getChildAt(0);
            if (view != null) {
                calendarHeight = view.getMeasuredHeight();
                calendarItemHeight = view.getItemHeight();
            }
        }
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(calendarHeight, MeasureSpec.EXACTLY));
    }

    private void init() {
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date());

        setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {

                CalendarView view;

                if (!cache.isEmpty()) {
                    view = cache.removeFirst();
                } else {
                    view = new CalendarView(container.getContext(), row);
                }

                view.setOnItemClickListener(onItemClickListener);
                try {
                    view.setAdapter((CaledarAdapter) mAdapter.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                    view.setAdapter(mAdapter);
                }
                System.out.println("#month:" + getMonth(position));
                view.setData(getMonthOfDayList(getYear(), getMonth(position)), position == Integer.MAX_VALUE / 2);
                container.addView(view);
                views.put(position, view);

                return view;
            }


            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
                cache.addLast((CalendarView) object);
                views.remove(position);
            }
        });

        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (onItemClickListener != null) {
                    CalendarView view = views.get(position);
                    Object[] obs = view.getSelect();
                    //onItemClickListener.onItemClick(view, (View) obs[0], (int) obs[1], (CalendarBean) obs[2]);
                }
                if (pageChangeListener != null) {
                    pageChangeListener.onPageSelected(position);
                }
                if (mCaledarLayoutChangeListener != null) {
                    mCaledarLayoutChangeListener.onLayoutChange(CalendarDateView.this);
                }
            }
        });
    }

    public void setOnSimpleOnPageChangeListener(SimpleOnPageChangeListener pageChangeListener) {
        this.pageChangeListener = pageChangeListener;
    }

    public int getYear() {
        return CalendarUtil.getYear(mCalendar);
    }

    public int getMonth(int position) {
        return CalendarUtil.getMonth(mCalendar) + position - Integer.MAX_VALUE / 2;
    }

    public Calendar getSelectCalendar() {
        Calendar tmp = Calendar.getInstance();
        tmp.setTime(new Date());
        tmp.add(Calendar.MONTH, getCurrentItem() - Integer.MAX_VALUE / 2);
        return tmp;
    }

    public CalendarView getCurrentCalendarView() {
        return views.get(getCurrentItem());
    }

    /**
     * 通知当前月数据刷新
     */
    public void notifyCurrentCalendarDataChange(){
        getCurrentCalendarView().notifyDataChanged();
    }


    private void initData() {
        setCurrentItem(Integer.MAX_VALUE / 2, false);
        getAdapter().notifyDataSetChanged();

    }

    @Override
    public int[] getCurrentSelectPositon() {
        CalendarView view = views.get(getCurrentItem());
        if (view == null) {
            view = (CalendarView) getChildAt(0);
        }
        if (view != null) {
            return view.getSelectPostion();
        }
        return new int[4];
    }

    @Override
    public int getItemHeight() {
        return calendarItemHeight;
    }

    @Override
    public void setCaledarTopViewChangeListener(CaledarTopViewChangeListener listener) {
        mCaledarLayoutChangeListener = listener;
    }


}
