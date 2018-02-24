package com.jiahaoliuliu.akami.transactionslist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.jiahaoliuliu.akami.model.ITransactions;
import com.jiahaoliuliu.akami.modelviewpresenter.BaseView;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

public class TransactionsListPresenter implements TransactionsListContract.Presenter {

    private static final String TAG = "TransactionsListPresenter";

    private TransactionsListContract.View mView;

    /**
     * The model of the MVP. Note this does not exist until the view is created
     */
    private TransactionsListContract.Model mModel;

    public TransactionsListPresenter() {
        super();
        mModel = new TransactionsListModel();
    }

    /**
     * This method has been separated from the constructor for better testability
     * @param view
     */
    public void setView(BaseView view) {
        this.mView = (TransactionsListContract.View)view;
    }

    @Override
    public void onViewCreated() {

        Disposable transactionsListDisposable = Observable
                .fromCallable(() -> mModel.getTransactionsList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ITransactions>>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onNext(List<ITransactions> transactionsList) {
                        Log.v(TAG, "List of transactions get " + transactionsList.size());
                        HashMap<Long, Float> transactionsPerMonth = mModel.getTransactionsPerMonth();

                        // Show the List of transactions
                        mView.showTransactionsList(transactionsList,
                                transactionsPerMonth, mModel.getCompaniesMap());
                        mView.setupHeader(transactionsList, transactionsPerMonth);
                    }

                    @SuppressLint("LongLogTag")
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error getting the list of transactions ", e);
                    }

                    @SuppressLint("LongLogTag")
                    @Override
                    public void onComplete() {
                        Log.e(TAG, "Operation completed");
                    }
                });

    }

    @Override
    public void onViewShown() {
        // Nothing to do here
    }

    @Override
    public void onViewHidden() {
        // Nothing to do here
    }

    @Override
    public void onViewDestroyed() {
        // Nothing to do here
    }

    @Override
    public void onShowMonthlyGraphRequested() {
        mView.showMonthlyGraphs(mModel.getTransactionsPerMonth());
    }
}
