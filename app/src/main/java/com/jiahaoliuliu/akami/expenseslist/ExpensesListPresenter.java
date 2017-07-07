package com.jiahaoliuliu.akami.expenseslist;

import com.jiahaoliuliu.akami.model.Company;

import java.util.Map;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

public class ExpensesListPresenter implements ExpensesListContract.Presenter {

    private ExpensesListContract.Model mModel;

    public ExpensesListPresenter() {
        super();
        mModel = new ExpensesListModel();
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
