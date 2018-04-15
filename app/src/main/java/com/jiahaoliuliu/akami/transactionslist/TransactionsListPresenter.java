package com.jiahaoliuliu.akami.transactionslist;

import android.annotation.SuppressLint;
import android.util.Log;

import com.jiahaoliuliu.akami.modelviewpresenter.BaseView;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
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

    private CompositeDisposable compositeDisposable;

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

    @SuppressLint("LongLogTag")
    @Override
    public void onViewCreated() {
        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(Observable
                .just(mModel.getTransactionsList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> mView.showLoadingScreen())
                .subscribe(transactionsList -> {
                    Log.v(TAG, "List of transactions get " + transactionsList.size());
                    if (transactionsList.isEmpty()) {
                        mView.showNoSmsScreen();
                        return;
                    }
                    HashMap<Long, Float> transactionsPerMonth = mModel.getTransactionsPerMonth();

                    // FIXME: For some reason, the list need to be set before the header is set.
                    // Show the List of transactions
                    mView.showTransactionsList(transactionsList,
                            transactionsPerMonth, mModel.getCompaniesMap());
                    mView.setupHeader(transactionsList, transactionsPerMonth);
                }));
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
        compositeDisposable.dispose();
    }

    @Override
    public void onShowMonthlyGraphRequested() {
        mView.showMonthlyGraphs(mModel.getTransactionsPerMonth());
    }
}
