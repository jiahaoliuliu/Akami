package com.jiahaoliuliu.akami.transactionslist;

import android.content.Context;

import com.jiahaoliuliu.akami.model.Company;

import java.util.Map;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

public class TransactionsListPresenter implements TransactionsListContract.Presenter {

    private Context mContext;
    private TransactionsListContract.Model mModel;

    public TransactionsListPresenter(Context context) {
        super();
        this.mContext = context;
        mModel = new TransactionsListModel(context);
    }

    @Override
    public void onViewCreated() {

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
    public Map<String, Company> getCompaniesMap() {
        return mModel.getCompaniesMap();
    }
}
