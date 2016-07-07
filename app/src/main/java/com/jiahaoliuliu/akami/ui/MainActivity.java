package com.jiahaoliuliu.akami.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jiahaoliuliu.akami.R;
import com.jiahaoliuliu.akami.model.Company;
import com.jiahaoliuliu.akami.model.Expense;
import com.jiahaoliuliu.akami.model.Sms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
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
    private TextView mHeaderDateTextView;
    private TextView mHeaderExpensesTextView;
    private RecyclerView mExpensesRecyclerView;

    // Internal variables
    private Context mContext;

    private List<Expense> mExpensesList;
    private ExpensesListAdapter mExpensesListAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    // The expenses per month
    private Map<Long, Float> mExpensesPerMonth;

    // The list of companies
    private Map<String, Company> mCompaniesMap;

    // The header date formatter
    private SimpleDateFormat mHeaderDateFormatter;
    // The month of the first element shown in the header
    private long mFirstElementMonthlyKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set variables
        this.mContext = this;
        mHeaderDateFormatter = new SimpleDateFormat(HEADER_DATE_FORMAT);

        // Link the views
        mHeaderDateTextView = (TextView) findViewById(R.id.header_date_text_view);
        mHeaderExpensesTextView = (TextView) findViewById(R.id.header_expenses_text_view);

        mExpensesRecyclerView = (RecyclerView) findViewById(R.id.expenses_recycler_view);
        mExpensesRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mExpensesRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Create the list of companies
        mCompaniesMap = generateComapniesList();

        // Parse the list of expenses from the device
        parseExpenses();
    }

    private void parseExpenses() {
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), PROJECTION, SELECTION_CLAUSE, SELECTION_ARGS, SORT_ORDER);
        if (cursor.moveToFirst()) {
            mExpensesList = new ArrayList<Expense>(cursor.getCount());
            do {
                try {
                    Sms sms = new Sms();
                    //                sms.set_id(cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_ID)));
                    sms.setDate((cursor.getLong(cursor.getColumnIndexOrThrow(Sms.COLUMN_DATE))));
                    sms.setBody((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_BODY))));

                    //                Log.v(TAG, "SMS read " + sms);
                    try {
                        Expense expense = new Expense(sms);
                        mExpensesList.add(expense);
                        updateExpensePerMonth(expense);
                    } catch (IllegalArgumentException illegalArgumentException) {
//                        Log.w(TAG, "Expense unknown " + illegalArgumentException.getMessage());
                    }
                // To catch any error on Getting the data from the cursor
                } catch (IllegalArgumentException illegalArgumentException) {
                    Log.w(TAG, "Error getting sms message from content resolver ", illegalArgumentException);
                }
            } while (cursor.moveToNext());
            cursor.close();

            mExpensesListAdapter = new ExpensesListAdapter(mContext, mExpensesList, mCompaniesMap);
            mExpensesRecyclerView.setAdapter(mExpensesListAdapter);
            mExpensesRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int firstElementPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                    Expense firstExpense = mExpensesList.get(firstElementPosition);

                    // Update the header if needed
                    long currentMonthlyKey = getHeaderMonthlyKeyByExpense(firstExpense);
                    if (currentMonthlyKey != mFirstElementMonthlyKey) {
                        // Update the month
                        mHeaderDateTextView.setText(mHeaderDateFormatter.format(firstExpense.getDate()));

                        // Update the expenses
                        mFirstElementMonthlyKey = currentMonthlyKey;
                        mHeaderExpensesTextView.setText(String.format("%.02f", mExpensesPerMonth.get(mFirstElementMonthlyKey))
                                    + " " + getResources().getString(R.string.currency_aed));
                    }
                }
            });
        } else {
            Log.v(TAG, "The user does not have any sms");
        }
    }

    private void updateExpensePerMonth(Expense expense) {
        // Intialize expense per month if needed
        if (mExpensesPerMonth == null) {
            mExpensesPerMonth = new HashMap<>();
        }

        long key = getHeaderMonthlyKeyByExpense(expense);

        if (!mExpensesPerMonth.containsKey(key)) {
            mExpensesPerMonth.put(key, 0.00f);
        } else {
            float monthExpense = mExpensesPerMonth.get(key);
            monthExpense += expense.getQuantity();
            mExpensesPerMonth.put(key, monthExpense);
        }
    }

    private long getHeaderMonthlyKeyByExpense(Expense expense) {
        // Generate the key
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expense.getDate());
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
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



        return companiesMap;
    }

}
