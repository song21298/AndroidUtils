package com.example.instrument.multipurpose;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Song on 2018/4/18 0018.
 * 事件选择工具类
 */

public class DateViewUtils {
    /**
     * 时间格式类型：
     * "yyyy-MM-dd HH:mm:ss, EE"  : 2018-4-18 1:33:56 星期三
     * "yyyy-MM-dd HH:mm:ss"      : 2018-4-18 1:33:56
     * "EE-MM-dd-yyyy"            : 星期三  4-18-2018
     */
    //年月日时分秒
    private boolean[] type = new boolean[]{true, true, true, true, true, true};
    //年月日
    private boolean[] type1 = new boolean[]{true, true, true, false, false, false};
    //时分秒
    private boolean[] type2 = new boolean[]{false, false, false, true, true, true};
    //底部布局显示模式
    private boolean[][] mBoo = new boolean[][]{type, type1, type2};

    private TimePickerView pvTime;
    private String timeInfo;
    private View view;
    public String time;
    private String showDataf = "yyyy-MM-dd HH:mm:ss, EE";

    private static DateViewUtils instance = new DateViewUtils();

    private DateViewUtils() {
    }

    public static DateViewUtils getInstance() {
        return instance;
    }


    public void Show(View v) {
        this.view = v;
        if (pvTime != null)
            pvTime.show();
    }

    /**
     * String s = mCustomView.time;
     * 自定义日期格式，不能直接显示在控件上
     */
    public void Show() {
        pvTime.show();
    }

    //显示年月日时分秒
    //Dialog 模式下，在底部弹出
    public TimePickerView initTimePicker(final Context context, int num, boolean canca, boolean dialog,
                                         int backgroud, boolean BackCance, float zhezhao) {
        pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                timeInfo = getTime(date, showDataf);
                time = timeInfo;
                if (view != null) {
                    TextView tv = (TextView) view;
                    tv.setText(timeInfo);
                }
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                    }
                })
                .setType(mBoo[num])
                .setOutSideCancelable(canca)
                .setBackgroundId(backgroud)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isDialog(dialog)
                .build();
        pvTime.setKeyBackCancelable(BackCance);//系统返回键监听屏蔽掉
        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            Toast.makeText(context, "dialog", Toast.LENGTH_SHORT).show();
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                //dialog模式下去除遮罩
                dialogWindow.setDimAmount(zhezhao);
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        return pvTime;
    }


    private String getTime(Date date, String showDataf) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat(showDataf, new Locale("zh"));
        return format.format(date);
    }

    //自定义日期格式/需在弹窗之前设置--获取数据格式，与弹窗布局无关
    public void setCustomFormat(String format) {
        this.showDataf = format;
    }
}
