package com.jiahaoliuliu.akami.transactionslist;

import android.content.Context;

import com.jiahaoliuliu.akami.modelviewpresenter.BaseView;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

public class TransactionsListPresenter implements TransactionsListContract.Presenter {

    private Context mContext;
    private TransactionsListContract.View mView;

    /**
     * The model of the MVP. Note this does not exist until the view is created
     */
    private TransactionsListContract.Model mModel;

    public TransactionsListPresenter() {
        super();
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
        mModel = new TransactionsListModel();
        // Show the List of transactions
        mView.showTransactionsList(mModel.getTransactionsList(),
                mModel.getTransactionsPerMonth(), mModel.getCompaniesMap());
        mView.setupHeader(mModel.getTransactionsList(), mModel.getTransactionsPerMonth());
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
