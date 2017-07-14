package com.jiahaoliuliu.akami.dependencyinjection;

import android.content.Context;

import com.jiahaoliuliu.akami.transactionslist.TransactionsListContract;
import com.jiahaoliuliu.akami.transactionslist.TransactionsListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        this.mContext = context;
    }

    @Singleton
    @Provides
    TransactionsListContract.Presenter provideRetrofitTransactionsListContractPresenter() {
        return new TransactionsListPresenter();
    }
}
