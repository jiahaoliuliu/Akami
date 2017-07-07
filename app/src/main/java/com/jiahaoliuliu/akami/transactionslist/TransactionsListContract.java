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
        void showTransactionsList(HashMap<Long, Float> transactionsPerMonth);
    }

    interface Presenter extends BasePresenter {

        void onShowMonthlyGraphRequested();

        /**
         * Get the list of companies.
         * TODO: This shouldn't be visible by the views
         * @return
         */
        Map<String, Company> getCompaniesMap();

        /**
         * Get the list of transactions from the SMS
         * TODO: This shouldn't be visible by the views
         * @return
         *      The list of the transactions that the user has
         */
        List<ITransactions> getTransactionsList();

        /**
         * Get the list of transactions per month
         * TODO: This shouldn't be visible by the views
         * @return
         *      The list of transactions per month
         */
        HashMap<Long, Float> getTransactionsPerMonth();
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
