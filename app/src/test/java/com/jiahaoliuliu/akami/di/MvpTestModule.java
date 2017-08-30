package com.jiahaoliuliu.akami.di;

import com.jiahaoliuliu.akami.transactionslist.TransactionsListContract;
import com.jiahaoliuliu.akami.transactionslist.TransactionsListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Created by jiahaoliuliu on 7/15/17.
 */

@Module
public class MvpTestModule {

    @Singleton
    @Provides
    TransactionsListContract.Presenter provideTransactionsListContractPresenter() {
        return mock(TransactionsListContract.Presenter.class);
    }
}
