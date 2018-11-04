package com.jiahaoliuliu.akami.transactionslist;

import android.content.Context;

import com.jiahaoliuliu.akami.model.Company;
import com.jiahaoliuliu.akami.model.ITransactions;
import com.jiahaoliuliu.akami.modelviewpresenter.BasePresenter;
import com.jiahaoliuliu.akami.modelviewpresenter.BaseView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

public interface TransactionsListContract {

    interface View extends BaseView {

        /**
         * Show the monthly graphs
         *
         * @param transactionsPerMonth
         *      The list of transactions per month
         */
        void showMonthlyGraphs(HashMap<Long, Float> transactionsPerMonth);

        /**
         * Show the list of transactions
         *
         * @param transactionsPerMonth
         *      The list of transactions per month
         */
        void showTransactionsList(List<ITransactions> transactionsList,
                                  HashMap<Long, Float> transactionsPerMonth,
                                  Map<String, Company> companiesMap);
    }

    interface Presenter extends BasePresenter {

        /**
         * Action triggered when it is required to show the monthly graphs
         */
        void onShowMonthlyGraphRequested();
    }

    interface Model {

        /**
         * Get the list of companies
         * @return
         */
        Map<String, Company> getCompaniesMap();

        /**
         * Get the list of transactions from the SMS
         *
         * @return
         *      The list of the transactions that the user has
         */
        List<ITransactions> getTransactionsList();

        /**
         * Get the list of transactions per month
         * @return
         *      The list of transactions per month
         */
        HashMap<Long, Float> getTransactionsPerMonth();
    }
}
