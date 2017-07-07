package com.jiahaoliuliu.akami.transactionslist;

import android.content.Context;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

public class TransactionsListPresenter implements TransactionsListContract.Presenter {

    private Context mContext;
    private final TransactionsListContract.View mView;

    /**
     * The model of the MVP. Note this does not exist until the view is created
     */
    private TransactionsListContract.Model mModel;


    public TransactionsListPresenter(TransactionsListContract.View view) {
        super();
        this.mView = view;
    }

    @Override
    public void onViewCreated(Context context) {
        this.mContext = context;
        mModel = new TransactionsListModel(mContext);
        // Show the List of transactions
        mView.showTransactionsList(
                mModel.getTransactionsList(),
                mModel.getTransactionsPerMonth(),
                mModel.getCompaniesMap());
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
