package com.god.gl.customcalendar;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codbking.calendar.CalendarBean;
import com.codbking.calendar.CalendarDateView;
import com.codbking.calendar.CalendarView;
import com.codbking.calendar.wrapper.SimpleCaledarAdapter;
import com.god.gl.customcalendar.utils.CalendarUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mTv_title;
    private ListView mListView;
    private List<String> dateList = new ArrayList<>();
    private CalendarDateView mCalendarDateView;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mListView = (ListView) findViewById(R.id.listView);
        String date= new SimpleDateFormat("yyyy-MM", Locale.CHINA).format(new Date());
        dateList.add(date+"-06");
        dateList.add(date+"-09");
        dateList.add(date+"-11");
        dateList.add(date+"-13");
        dateList.add(date+"-19");
        dateList.add(date+"-22");
        dateList.add(date+"-23");
        dateList.add(date+"-28");
        for (int i= 0;i<15;i++){
            mList.add("customcalendar");
        }
        mCalendarDateView = (CalendarDateView) findViewById(R.id.calendarDateView);

        SimpleCaledarAdapter  adapter = new SimpleCaledarAdapter() {
            //返回当天是否可点击
            @Override
            public boolean isEnabled(int position, CalendarBean bean) {
                return check(bean);
            }
        };
        mCalendarDateView.setAdapter(adapter);
        updateTitle();
        mListView.setAdapter(new DateAdapter());
        mCalendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(CalendarView parent, View view, int postion, CalendarBean bean) {
                parent.notifyDataChanged();
                Toast.makeText(MainActivity.this,bean.year+"-"+bean.moth+"-"+bean.day,Toast.LENGTH_SHORT).show();
            }
        });

        mCalendarDateView.setOnSimpleOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                updateTitle();

            }
        });
    }

    private boolean check(CalendarBean bean) {
        if (dateList == null || dateList.isEmpty()) {
            return false;
        }
        for (String date : dateList) {
            if (bean.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    private void updateTitle() {
        //获取当前年月
        Calendar calendar = mCalendarDateView.getSelectCalendar();
        mTv_title.setText(CalendarUtils.getYear(calendar) + "年" + CalendarUtils.getMonth(calendar) + "月");
    }


    class DateAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (null == view){
                view = View.inflate(MainActivity.this,R.layout.item_data,null);
            }
            TextView textView = (TextView) view.findViewById(R.id.tv_data);
            textView.setText(mList.get(i));
            return view;
        }
    }
}
