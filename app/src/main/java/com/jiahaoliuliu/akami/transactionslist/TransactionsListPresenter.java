package com.jiahaoliuliu.akami.transactionslist;

import android.content.Context;

import com.jiahaoliuliu.akami.model.Company;
import com.jiahaoliuliu.akami.model.ITransactions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public TransactionsListPresenter(Context context, TransactionsListContract.View view) {
        super();
        this.mView = view;
    }

    @Override
    public void onViewCreated(Context context) {
        this.mContext = context;
        mModel = new TransactionsListModel(mContext);
        // Show the List of transactions
        mView.showTransactionsList(mModel.getTransactionsPerMonth());
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

    @Override
    public Map<String, Company> getCompaniesMap() {
        return mModel.getCompaniesMap();
    }

    @Override
    public List<ITransactions> getTransactionsList() {
        return mModel.getTransactionsList();
    }

    @Override
    public HashMap<Long, Float> getTransactionsPerMonth() {
        return mModel.getTransactionsPerMonth();
    }
}
