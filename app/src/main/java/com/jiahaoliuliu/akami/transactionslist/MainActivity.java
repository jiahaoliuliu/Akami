package com.jiahaoliuliu.akami.transactionslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiahaoliuliu.akami.MainApplication;
import com.jiahaoliuliu.akami.R;
import com.jiahaoliuliu.akami.model.Company;
import com.jiahaoliuliu.akami.model.ITransactions;
import com.jiahaoliuliu.akami.modelviewpresenter.BaseActivity;
import com.jiahaoliuliu.akami.ui.MonthlyTransactionsActivity;
import com.jiahaoliuliu.akami.utils.HeaderUtility;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class MainActivity extends BaseActivity
        implements TransactionsListContract.View {

    private static final String TAG = "MainActivity";
    private static final int MENU_ITEM_SHOW_MONTHLY_GRAPH_ID = 1000;

    // Date to be displayed as header
    private static final String HEADER_DATE_FORMAT = "MMMM yyyy";

    // Views
    private TextView mHeaderDateTextView;
    private TextView mHeaderQuantityTextView;
    private RecyclerView mTransactionsRecyclerView;
    private TextView mNoSmsTextView;
    private LinearLayout mProgressLayout;

    private TransactionsListAdapter mTransactionsListAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    // The header date formatter. This has to be static in order to be used by the adapter
    public static SimpleDateFormat sHeaderDateFormatter = new SimpleDateFormat(HEADER_DATE_FORMAT);
    // The month of the first element shown in the header
    private long mFirstElementMonthlyKey;

    @Inject
    TransactionsListContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MainApplication) getApplication()).getAppComponent().inject(this);
        mPresenter.setView(this);

        // To be used in the base activity
        setBasePresenter(mPresenter);
        linkViews();

        getBasePresenter().onViewCreated();
    }

    private void linkViews() {
        mHeaderDateTextView = (TextView) findViewById(R.id.header_date_text_view);
        mHeaderQuantityTextView = (TextView) findViewById(R.id.header_quantity_text_view);

        mTransactionsRecyclerView = (RecyclerView) findViewById(R.id.transactions_recycler_view);
        mTransactionsRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mTransactionsRecyclerView.setLayoutManager(mLinearLayoutManager);

        mNoSmsTextView = (TextView) findViewById(R.id.no_sms_text_view);

        mProgressLayout = (LinearLayout) findViewById(R.id.progress_layout);
    }

    @Override
    public void setupHeader(final List<ITransactions> transactionsList,
                              final HashMap<Long, Float> transactionsPerMonth) {
       // Set the logic for the recycler view
        mTransactionsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // TODO: This does not work for the last month when scrolls up
                int firstElementPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                ITransactions firstTransaction = transactionsList.get(firstElementPosition);

                // Update the header if needed
                long currentMonthlyKey = HeaderUtility.getHeaderMonthlyKeyByTransaction(firstTransaction);
                if (currentMonthlyKey != mFirstElementMonthlyKey) {
                    // Update the month
                    mHeaderDateTextView.setText(sHeaderDateFormatter.format(firstTransaction.getDate()));

                    // Update the quantity
                    mFirstElementMonthlyKey = currentMonthlyKey;
                    mHeaderQuantityTextView.setText(String.format("%.02f", transactionsPerMonth.get(mFirstElementMonthlyKey))
                            + " " + getResources().getString(R.string.currency_aed));
                }
            }
        });
    }

    @Override
    public void showLoadingScreen() {
        mTransactionsRecyclerView.setVisibility(View.GONE);
        mNoSmsTextView.setVisibility(View.GONE);
        mProgressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoSmsScreen() {
        mTransactionsRecyclerView.setVisibility(View.GONE);
        mNoSmsTextView.setVisibility(View.VISIBLE);
        mProgressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showTransactionsList(List<ITransactions> transactionsList,
                                     HashMap<Long, Float> transactionsPerMonth,
                                     Map<String, Company> companiesMap) {
        mTransactionsListAdapter = new TransactionsListAdapter(context,
                transactionsList, companiesMap,
                transactionsPerMonth);
        mTransactionsRecyclerView.setAdapter(mTransactionsListAdapter);
        mTransactionsRecyclerView.setVisibility(View.VISIBLE);

        // Disable the no sms view
        mNoSmsTextView.setVisibility(View.GONE);
        mProgressLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem showMonthlyGraphMenuItem = menu.add(Menu.NONE, MENU_ITEM_SHOW_MONTHLY_GRAPH_ID, Menu
            .NONE, R.string.action_bar_show_monthly_graph)
            .setIcon(R.drawable.ic_action_show_monthly_graph);
        showMonthlyGraphMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ITEM_SHOW_MONTHLY_GRAPH_ID:
                mPresenter.onShowMonthlyGraphRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMonthlyGraphs(HashMap<Long, Float> transactionsPerMonth) {
        // If the data is not ready, don't do anything
        if (transactionsPerMonth == null || transactionsPerMonth.isEmpty()) {
            Log.w(TAG, "Trying to check the monthly transactions when the data is not ready");
            return;
        }

        Intent startMonthlyExpensesActivityIntent = new Intent(context, MonthlyTransactionsActivity.class);
        startMonthlyExpensesActivityIntent.putExtra(MonthlyTransactionsActivity.INTENT_KEY_MONTHLY_TRANSACTIONS,
                transactionsPerMonth);
        startActivity(startMonthlyExpensesActivityIntent);
        return;
    }
}
