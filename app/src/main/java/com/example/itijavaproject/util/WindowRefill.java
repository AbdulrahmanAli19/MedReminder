package com.example.itijavaproject.util;

import static android.content.Context.WINDOW_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.itijavaproject.R;

public class WindowRefill {

    private static final String TAG = "Window.DEV";
    private final Context context;
    private final View mView;
    private WindowManager.LayoutParams mParams;
    private final WindowManager mWindowManager;

    @SuppressLint("InflateParams")
    public WindowRefill(Context context, String body, String title) {
        this.context = context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = layoutInflater.inflate(R.layout.popup_window, null);

        ((TextView) mView.findViewById(R.id.txtMedName)).setText(title);

        ((TextView) mView.findViewById(R.id.txtBody)).setText(body);

        mView.findViewById(R.id.btnSnooze).setOnClickListener(view -> {
            ///TODO: REMIND ME LATTER
            close();
        });

        mView.findViewById(R.id.btnTake).setOnClickListener(view -> {

            close();
        });

        mView.findViewById(R.id.btnSkip).setOnClickListener(view -> {
            ///TODO: Skip refill
            close();
        });

        mParams.gravity = Gravity.CENTER;
        mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
    }

    public void open() {
        try {

            if (mView.getWindowToken() == null) {
                if (mView.getParent() == null) {
                    mWindowManager.addView(mView, mParams);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    public void close() {
        try {
            ((WindowManager) context.getSystemService(WINDOW_SERVICE)).removeView(mView);
            mView.invalidate();
            ((ViewGroup) mView.getParent()).removeAllViews();

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}