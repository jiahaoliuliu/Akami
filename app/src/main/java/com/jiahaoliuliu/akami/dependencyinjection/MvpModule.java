package com.jiahaoliuliu.akami.dependencyinjection;

import com.jiahaoliuliu.akami.transactionslist.TransactionsListContract;
import com.jiahaoliuliu.akami.transactionslist.TransactionsListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

@Module
public class MvpModule {

    @Singleton
    @Provides
    TransactionsListContract.Presenter provideTransactionsListContractPresenter() {
        return new TransactionsListPresenter();
    }
}
