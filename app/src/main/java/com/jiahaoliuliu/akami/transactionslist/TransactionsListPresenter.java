package com.jiahaoliuliu.akami.transactionslist;

import com.jiahaoliuliu.akami.model.Company;

import java.util.Map;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

public class TransactionsListPresenter implements TransactionsListContract.Presenter {

    private TransactionsListContract.Model mModel;

    public TransactionsListPresenter() {
        super();
        mModel = new TransactionsListModel();
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
    public Map<String, Company> getCompaniesList() {
        return mModel.getCompaniesList();
    }
}
