package com.jiahaoliuliu.akami.expenseslist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiahaoliuliu.akami.R;
import com.jiahaoliuliu.akami.model.Company;
import com.jiahaoliuliu.akami.model.Expense;
import com.jiahaoliuliu.akami.model.Sms;
import com.jiahaoliuliu.akami.model.ITransactions;
import com.jiahaoliuliu.akami.model.Withdraw;
import com.jiahaoliuliu.akami.modelviewpresenter.BaseActivity;
import com.jiahaoliuliu.akami.modelviewpresenter.BasePresenter;
import com.jiahaoliuliu.akami.modelviewpresenter.BaseView;
import com.jiahaoliuliu.akami.ui.MonthlyTransactionsActivity;
import com.jiahaoliuliu.akami.ui.TransactionsListAdapter;
import com.jiahaoliuliu.akami.utils.HeaderUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity
        implements ExpensesListContract.View, BaseView {

    private static final String TAG = "MainActivity";
    private static final int MENU_ITEM_SHOW_MONTHLY_GRAPH_ID = 1000;

    private static final String ADDRESS_ADCB = "ADCBAlert";

    // Date to be displayed as header
    private static final String HEADER_DATE_FORMAT = "MMMM yyyy";

    // Projection. The fields of the sms to be returned
    private static final String[] PROJECTION = {
//        Sms.COLUMN_ID,
        Sms.COLUMN_DATE,
        Sms.COLUMN_BODY
    };

    // Selection query
    private static final String SELECTION_CLAUSE = Sms.COLUMN_TYPE + "=? and " + Sms.COLUMN_ADDRESS + "=?";

    // Selection arguments
    private static final String[] SELECTION_ARGS = {"1", ADDRESS_ADCB};

    // Sort order
    private static final String SORT_ORDER = Sms.COLUMN_DATE + " DESC";

    // Views
    private LinearLayout mHeaderLinearLayout;
    private TextView mHeaderDateTextView;
    private TextView mHeaderQuantityTextView;
    private RecyclerView mTransactionsRecyclerView;
    private TextView mNoSmsTextView;

    // Internal variables
    private Context mContext;

    // The expenses per month
    private HashMap<Long, Float> mTransactionsPerMonth;
    private List<ITransactions> mTransactionsList;
    private TransactionsListAdapter mTransactionsListAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    // The list of companies
    private Map<String, Company> mCompaniesMap;

    // The header date formatter. This has to be static in order to be used by the adapter
    public static SimpleDateFormat sHeaderDateFormatter = new SimpleDateFormat(HEADER_DATE_FORMAT);
    // The month of the first element shown in the header
    private long mFirstElementMonthlyKey;

    private ExpensesListPresenter mExpensesListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set the base presenter for the base view and for this view
        // This should be done before the method onCreated because
        // it is linked on the lifecycle of the application
        mExpensesListPresenter = (ExpensesListPresenter) getPresenter();
        setPresenter(mExpensesListPresenter);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set variables
        this.mContext = this;

        // Link the views
        mHeaderDateTextView = (TextView) findViewById(R.id.header_date_text_view);
        mHeaderQuantityTextView = (TextView) findViewById(R.id.header_quantity_text_view);

        mTransactionsRecyclerView = (RecyclerView) findViewById(R.id.transactions_recycler_view);
        mTransactionsRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mTransactionsRecyclerView.setLayoutManager(mLinearLayoutManager);

        mNoSmsTextView = (TextView) findViewById(R.id.no_sms_text_view);

        // Create the list of companies
        mCompaniesMap = generateComapniesList();

        // Parse the list of transactions from the device
        parseTransactions();
    }

    private void parseTransactions() {
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), PROJECTION, SELECTION_CLAUSE, SELECTION_ARGS, SORT_ORDER);
        if (cursor.moveToFirst()) {
            mTransactionsList = new ArrayList<ITransactions>(cursor.getCount());
            do {
                try {
                    Sms sms = new Sms();
                    //                sms.set_id(cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_ID)));
                    sms.setDate((cursor.getLong(cursor.getColumnIndexOrThrow(Sms.COLUMN_DATE))));
                    sms.setBody((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_BODY))));
//                    Log.v(TAG, "SMS " + sms);
                    try {
                        switch (sms.getType()) {
                            case EXPENSE_1:
                            case EXPENSE_2:
                                Expense expense = new Expense(sms);
                                mTransactionsList.add(expense);
                                break;
                            case WITHDRAW_1:
                            case WITHDRAW_2:
                                Withdraw withdraw = new Withdraw(sms);
                                mTransactionsList.add(withdraw);
                                break;
                            case UNKNOWN:
                                Log.w(TAG, "Sms unknown " + sms.getBody());
                                break;
                        }
                    } catch (IllegalArgumentException illegalArgumentException) {
                        Log.w(TAG, "transaction unknown " + illegalArgumentException.getMessage());
                    }
                // To catch any error on Getting the data from the cursor
                } catch (IllegalArgumentException illegalArgumentException) {
                    Log.w(TAG, "Error getting sms message from content resolver ", illegalArgumentException);
                }
            } while (cursor.moveToNext());
            cursor.close();

            // Update the transactions per month
            for (ITransactions transactions : mTransactionsList) {
                updateTransactionsPerMonth(transactions);
            }

            mTransactionsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int firstElementPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                    ITransactions firstTransaction = mTransactionsList.get(firstElementPosition);

                    // Update the header if needed
                    long currentMonthlyKey = HeaderUtility.getHeaderMonthlyKeyByTransaction(firstTransaction);
                    if (currentMonthlyKey != mFirstElementMonthlyKey) {
                        // Update the month
                        mHeaderDateTextView.setText(sHeaderDateFormatter.format(firstTransaction.getDate()));

                        // Update the quantity
                        mFirstElementMonthlyKey = currentMonthlyKey;
                        mHeaderQuantityTextView.setText(String.format("%.02f", mTransactionsPerMonth.get(mFirstElementMonthlyKey))
                                    + " " + getResources().getString(R.string.currency_aed));
                    }
                }
            });
            mTransactionsListAdapter = new TransactionsListAdapter(mContext, mTransactionsList, mCompaniesMap, mTransactionsPerMonth);
            mTransactionsRecyclerView.setAdapter(mTransactionsListAdapter);

            // Disable the no sms view
            mNoSmsTextView.setVisibility(View.GONE);
        } else {
            Log.v(TAG, "The user does not have any sms");

        }
    }

    private void updateTransactionsPerMonth(ITransactions transaction) {
        // Intialize expense per month if needed
        if (mTransactionsPerMonth == null) {
            mTransactionsPerMonth = new HashMap<>();
        }

        long key = HeaderUtility.getHeaderMonthlyKeyByTransaction(transaction);

        if (!mTransactionsPerMonth.containsKey(key)) {
            mTransactionsPerMonth.put(key, 0.00f);
            transaction.setFirstTransactionOfTheMonth(true);
        } else {
            float monthExpense = mTransactionsPerMonth.get(key);
            monthExpense += transaction.getQuantity();
            mTransactionsPerMonth.put(key, monthExpense);
        }
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
                showMonthlyGraphs();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showMonthlyGraphs() {
        // If the data is not ready, don't do anything
        if (mTransactionsPerMonth == null || mTransactionsPerMonth.isEmpty()) {
            Log.w(TAG, "Trying to check the monthly transactions when the data is not ready");
            return;
        }

        Intent startMonthlyExpensesActivityIntent = new Intent(mContext, MonthlyTransactionsActivity.class);
        startMonthlyExpensesActivityIntent.putExtra(MonthlyTransactionsActivity.INTENT_KEY_MONTHLY_TRANSACTIONS,
            mTransactionsPerMonth);
        startActivity(startMonthlyExpensesActivityIntent);
        return;
    }

    // TODO: Use database instead
    private Map<String, Company> generateComapniesList() {
        Map<String, Company> companiesMap = new HashMap<>();

        // Carrefour Mall of Emirates
        String companyId = "CARREFOUR-MOE,DUBAI-AE";
        Company company = new Company(companyId, "Carrefour");
        company.setImageResourceId(R.drawable.logo_carrefour);
        companiesMap.put(companyId, company);

        // Carrefour Marina
        companyId = "CARREFOUR MARKET MAR,DUBAI-AE";
        company = new Company(companyId, "Carrefour");
        company.setImageResourceId(R.drawable.logo_carrefour);
        companiesMap.put(companyId, company);

        // Carrefour TeCom
        companyId = "CARREFOUR MARKET TEC,DUBAI-AE";
        company = new Company(companyId, "Carrefour");
        company.setImageResourceId(R.drawable.logo_carrefour);
        companiesMap.put(companyId, company);

        // Al merkaz
        companyId = "AL MERKAZ,DUBAI-AE";
        company = new Company(companyId, "Al Merkaz");
        company.setImageResourceId(R.drawable.logo_almerkaz);
        companiesMap.put(companyId, company);

        // Capella club
        companyId = "CAPELLA CLUB MARINE,DUBAI-AE";
        company = new Company(companyId, "Capella club");
        companiesMap.put(companyId, company);

        // Spinney
        companyId = "SPINNEYS DUBAI LLC 9,DUBAI-AE";
        company = new Company(companyId, "Spinneys");
        company.setImageResourceId(R.drawable.logo_spinneys);
        companiesMap.put(companyId, company);

        companyId = "SPINNEYS DUBAI LLC,DUBAI-AE";
        company = new Company(companyId, "Spinneys");
        company.setImageResourceId(R.drawable.logo_spinneys);
        companiesMap.put(companyId, company);

        // Media one hotel
        companyId = "MEDIA ONE HOTEL,DUBAI-AE";
        company = new Company(companyId, "Media one hotel");
        company.setImageResourceId(R.drawable.logo_media_one_hotel);
        companiesMap.put(companyId, company);

        // Vapiano
        companyId = "VAP HOSPITALITY -731,DUBAI-AE";
        company = new Company(companyId, "Vapiano");
        company.setImageResourceId(R.drawable.logo_vapiano);
        companiesMap.put(companyId, company);

        // RTA
        companyId = "RTA-TOM,DUBAI-AE";
        company = new Company(companyId, "RTA");
        company.setImageResourceId(R.drawable.logo_rta);
        companiesMap.put(companyId, company);

        // RTA
        companyId = "ROAD & TRANSPORT AUT,DUBAI-AE";
        company = new Company(companyId, "RTA");
        company.setImageResourceId(R.drawable.logo_rta);
        companiesMap.put(companyId, company);

        // Waitrose
        companyId = "WAITROSE-FINEFARE,DUBAI-AE";
        company = new Company(companyId, "Waitrose");
        company.setImageResourceId(R.drawable.logo_waitrose);
        companiesMap.put(companyId, company);

        // Eton institute
        companyId = "ETON EDUCATIONAL INS,DUBAI-AE";
        company = new Company(companyId, "Eton institue");
        company.setImageResourceId(R.drawable.logo_eton_institute);
        companiesMap.put(companyId, company);

        // Q Mart Mini Market JLT
        companyId = "Q MART MINI MARKET J,DUBAI-AE";
        company = new Company(companyId, "Q Mart Mini Market JLT");
        companiesMap.put(companyId, company);

        // Blue mart
        companyId = "EXPRESS BLUE MART SU,DUBAI-AE";
        company = new Company(companyId, "Express Blue Mart");
        companiesMap.put(companyId, company);

        // Indigo rent a car
        companyId = "INDIGO RENT A CAR DM,DUBAI-AE";
        company = new Company(companyId, "Indigo rent a car");
        companiesMap.put(companyId, company);

        // Borders
        companyId = "BORDERS-IBN BATT-251,DUBAI-AE";
        company = new Company(companyId, "Borders");
        companiesMap.put(companyId, company);

        // Decathlong
        companyId = "DECATHLON,DUBAI-AE";
        company = new Company(companyId, "Decathlon");
        companiesMap.put(companyId, company);

        // Alphamed Pharma
        companyId = "ALPHAMED IBN SIN-714,DUBAI-AE";
        company = new Company(companyId, "Alphamed Pharma");
        companiesMap.put(companyId, company);

        // Adidas
        companyId = "ADIDAS EMERGING -261,DUBAI-AE";
        company = new Company(companyId, "Adidas");
        companiesMap.put(companyId, company);

        // Panmeas Jewellery
        companyId = "PANMEAS JEWELLERY LL,ABU DHABI-AE";
        company = new Company(companyId, "Panmeas Jewellery");
        companiesMap.put(companyId, company);

        // Enoc
        companyId = "ENOC SITE 4010 CSTOR,DUBAI-AE";
        company = new Company(companyId, "Enoc");
        companiesMap.put(companyId, company);

        // Bershka
        companyId = "BERSHKA-FAMA TRADING,DUBAI-AE";
        company = new Company(companyId, "Bershka");
        companiesMap.put(companyId, company);

        // Careem
        companyId = "CAREEM NETWORKS FZ L,DUBAI-AE";
        company = new Company(companyId, "Careem");
        company.setImageResourceId(R.drawable.logo_careem);
        companiesMap.put(companyId, company);

        // Zoom
        companyId = "SITE 6549 ZOOM MARKE,DUBAI-AE";
        company = new Company(companyId, "Zoom");
        companiesMap.put(companyId, company);

        // Air france
        companyId = "AIR FRANCE,ROISSY CDG CE-FR";
        company = new Company(companyId, "Air France");
        companiesMap.put(companyId, company);

        // Microless Computer
        companyId = "MICROLESS DMCC,DUBAI-AE";
        company = new Company(companyId, "Microless computer");
        companiesMap.put(companyId, company);

        // Express VPN
        companyId = "EXPRESS VPN,310-601-8492-US";
        company = new Company(companyId, "Express VPN");
        companiesMap.put(companyId, company);

        return companiesMap;
    }

    // TODO: Use Dagger instead
    @Override
    public BasePresenter getPresenter() {
        return new ExpensesListPresenter();
    }
}
