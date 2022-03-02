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
import com.example.itijavaproject.data.db.ConcreteLocalSource;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.Repository;
import com.example.itijavaproject.pojo.repo.RepositoryInterface;

import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class Window implements MaybeObserver<Medicine> {

    private static final String TAG = "Window.DEV";
    private final Context context;
    private final View mView;
    private WindowManager.LayoutParams mParams;
    private final WindowManager mWindowManager;
    private final RepositoryInterface repository;
    private final TextView medName;
    private final TextView medDesc;

    private Medicine selectedMed = null;

    @SuppressLint("InflateParams")
    public Window(Context context, String medId) {
        this.context = context;
        repository = Repository.getInstance(ConcreteLocalSource
                .getInstance(context), context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = layoutInflater.inflate(R.layout.popup_window, null);

        repository.getMedById(medId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);

        medName = mView.findViewById(R.id.txtMedName);

        medDesc = mView.findViewById(R.id.txtBody);

        mView.findViewById(R.id.btnSnooze).setOnClickListener(view -> {
            ///TODO: REMIND ME LATTER
            close();
        });

        mView.findViewById(R.id.btnRefill).setOnClickListener(view -> {
            selectedMed.setNumOfPills(selectedMed.getNumOfPills() - 1);
            repository.editMedicine(selectedMed);
            close();
        });

        mView.findViewById(R.id.btnSkip).setOnClickListener(view -> {
            ///TODO: Skip med
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


    @Override
    public void onSubscribe(Disposable d) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccess(Medicine medicine) {
        ///TODO:Update data here
        open();
        selectedMed = medicine;
        medName.setText(medicine.getName());

        ////TODO:Add tima and date
        medDesc.setText(medicine.getNoOfStrength()+" "+medicine.getStrength()+" ,take 1 Pill(s), "+medicine.getInstructions()
                +"\n,at  ");
        medDesc.append(medicine.getNumOfPills() + " ");
        medDesc.append(medicine.getTimes().size() + " ");
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
