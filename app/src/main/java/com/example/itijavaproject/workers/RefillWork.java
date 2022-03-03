package com.example.itijavaproject.workers;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.itijavaproject.data.db.ConcreteLocalSource;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.Repository;
import com.example.itijavaproject.pojo.repo.RepositoryInterface;
import com.google.firebase.database.core.Tag;

import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class RefillWork extends Worker {
    private final RepositoryInterface repository;
    private static final String TAG = "RefillWork.DEV";
    public RefillWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repository = Repository.getInstance(ConcreteLocalSource
                .getInstance(getApplicationContext()), getApplicationContext());

    }
    @NonNull
    @Override
    public Result doWork() {

        repository
                .getSelectedDateMedicines(System.currentTimeMillis())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<List<Medicine>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: isDisposed" + d.isDisposed());
                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onSuccess(List<Medicine> medicines) {
                        Log.d(TAG, "onSuccess: size: " + medicines.size());
                        for (Medicine medicine : medicines) {
                            if(medicine.getNumOfPills()<5 && medicine.getIsRefillReminder())
                            {
                                AddRefillReminder.addRefill(getApplicationContext(),medicine.getMed_id());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });

        return Result.success();
    }
}
