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

import androidx.annotation.RequiresApi;

import com.example.itijavaproject.R;
import com.example.itijavaproject.data.db.ConcreteLocalSource;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.Repository;
import com.example.itijavaproject.pojo.repo.RepositoryInterface;
import com.example.itijavaproject.workers.AddRefillReminder;

import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class WindowRefill implements MaybeObserver<Medicine> {

    private static final String TAG = "Window.DEV";
    private final Context context;
    private final View mView;
    private WindowManager.LayoutParams layoutParams;
    private final WindowManager mWindowManager;
    private final RepositoryInterface repository;
    private Medicine selectedMed = null;
    private final TextView medName;
    private final TextView medDesc;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("InflateParams")
    public WindowRefill(Context context,String medId) {
        this.context = context;
        repository = Repository.getInstance(ConcreteLocalSource
                .getInstance(context), context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = layoutInflater.inflate(R.layout.refill_popup, null);
        repository.getMedById(medId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);


        medName = mView.findViewById(R.id.txtMedName);
        medDesc = mView.findViewById(R.id.txtBody);
        mView.findViewById(R.id.btnSnooze).setOnClickListener(view -> {
            AddRefillReminder.RefillSingleReminder(5,medId,context);
            close();
        });


        mView.findViewById(R.id.btnSkip).setOnClickListener(view -> {

            close();
        });

        layoutParams.gravity = Gravity.CENTER;
        mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
    }

    public void open() {
        try {

            if (mView.getWindowToken() == null) {
                if (mView.getParent() == null) {
                    mWindowManager.addView(mView, layoutParams);
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

    @Override
    public void onSubscribe(Disposable d) {

    }
    @Override
    public void onSuccess(Medicine medicine) {
        open();
        Log.d(TAG, "onSuccess med: "+medicine.getName().isEmpty());
        selectedMed=medicine;
        medName.setText(medicine.getName());
        medDesc.setText("you need to refill "+medicine.getName()+"you have only  ");
        medDesc.append(medicine.getNumOfPills() + " "+medicine.getIconType()+"'s");

    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "onError: ");
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: ");
    }
}
