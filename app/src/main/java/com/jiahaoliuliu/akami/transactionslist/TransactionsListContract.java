package com.jiahaoliuliu.akami.transactionslist;

import com.jiahaoliuliu.akami.model.Company;
import com.jiahaoliuliu.akami.model.ITransactions;
import com.jiahaoliuliu.akami.modelviewpresenter.BasePresenter;
import com.jiahaoliuliu.akami.modelviewpresenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

public interface TransactionsListContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {
        /**
         * Get the list of companies.
         * TODO: This shouldn't be visible by the views
         * @return
         */
        Map<String, Company> getCompaniesMap();
    }

    interface Model {

        /**
         * Get the list of companies
         * @return
         */
        Map<String, Company> getCompaniesMap();

        List<ITransactions> getTransactionsList();
    }
}
