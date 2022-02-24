package com.example.itijavaproject.util;

import static android.content.Context.WINDOW_SERVICE;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.itijavaproject.R;

public class Window {

    private static final String TAG = "Window.DEV";

    private Context context;
    private View mView;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    private LayoutInflater layoutInflater;

    public Window(Context context) {
        this.context = context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = layoutInflater.inflate(R.layout.popup_window, null);

        mView.findViewById(R.id.btnSnooze).setOnClickListener(view -> {
            ///TODO: REMIND ME LATTER
            close();
        });

        mView.findViewById(R.id.btnTake).setOnClickListener(view -> {
            ///TODO: TAKE THE MED
            close();
        });

        mParams.gravity = Gravity.CENTER;
        mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
    }
        public void createNotificationDialogue(){
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogue = inflater.inflate(R.layout.custom_dialogue, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogue);
        TextView header = dialogue.findViewById(R.id.txtHeader);
        TextView medName = dialogue.findViewById(R.id.medName);
        TextView details = dialogue.findViewById(R.id.txtMedDetails);
        ImageButton iconType = dialogue.findViewById(R.id.Icon);
        //FloatingActionButton skip=dialogue.findViewById(R.id.cancelBtn);
        //FloatingActionButton take=dialogue.findViewById(R.id.doneBtn);
        //FloatingActionButton snooze=dialogue.findViewById(R.id.snoozeBtn);
        AlertDialog customDialog = builder.create();
        customDialog.show();

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
