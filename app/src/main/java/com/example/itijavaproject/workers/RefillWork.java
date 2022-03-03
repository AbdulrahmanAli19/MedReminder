package com.example.itijavaproject.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.itijavaproject.data.db.ConcreteLocalSource;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.Repository;
import com.example.itijavaproject.pojo.repo.RepositoryInterface;

import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class RefillWork extends Worker {
    private final RepositoryInterface repository;
    public RefillWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repository = Repository.getInstance(ConcreteLocalSource
                .getInstance(getApplicationContext()), getApplicationContext());

    }

    @NonNull
    @Override
    public Result doWork() {

        return null;
    }
}
