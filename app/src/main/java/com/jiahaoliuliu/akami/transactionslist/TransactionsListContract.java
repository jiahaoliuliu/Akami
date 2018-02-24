package com.jiahaoliuliu.akami.transactionslist;

import com.jiahaoliuliu.akami.model.Company;
import com.jiahaoliuliu.akami.model.ITransactions;
import com.jiahaoliuliu.akami.modelviewpresenter.BaseModel;
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

        void setupHeader(List<ITransactions> transactionsList,
                         HashMap<Long, Float> transactionsPerMonth);

        /**
         * Show a loading screen
         */
        void showLoadingScreen();

        /**
         * Show this screen simulating there is not SMS
         * For now this is used to show error message
         * TODO: ADD error screen
         */
        void showNoSmsScreen();
    }

    interface Presenter extends BasePresenter {

        /**
         * Action triggered when it is required to show the monthly graphs
         */
        void onShowMonthlyGraphRequested();
    }

    interface Model extends BaseModel {

        /**
         * Get the list of companies
         * @return
         */
        Map<String, Company> getCompaniesMap();

        /**
         * Get the list of transactions from the SMS. This operation is highly costly. It
         * need to be run on other thread than main thread
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
