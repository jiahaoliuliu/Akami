package com.jiahaoliuliu.akami.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.jiahaoliuliu.akami.R;
import com.jiahaoliuliu.akami.model.Company;
import com.jiahaoliuliu.akami.model.Expense;
import com.jiahaoliuliu.akami.model.Sms;
import com.jiahaoliuliu.akami.model.ITransactions;
import com.jiahaoliuliu.akami.model.Withdraw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String ADDRESS_ADCB = "ADCBAlert";

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
    private RecyclerView mTransactionsRecyclerView;

    // Internal variables
    private Context mContext;

    private List<ITransactions> mTransactionsList;
    private TransactionsListAdapter mTransactionsListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // The list of companies
    private Map<String, Company> mCompaniesMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set variables
        this.mContext = this;

        // Link the views
        mTransactionsRecyclerView = (RecyclerView) findViewById(R.id.transactions_recycler_view);
        mTransactionsRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mTransactionsRecyclerView.setLayoutManager(mLayoutManager);

        // Create the list of companies
        mCompaniesMap = generateComapniesList();

        // Parse the list of expenses from the device
        parseExpenses();
    }

    private void parseExpenses() {
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
//                                Log.v(TAG, "transaction parsed " + expense);
                                break;
                            case WITHDRAW:
                                Withdraw withdraw = new Withdraw(sms);
//                                Log.v(TAG, "Withdraw parsed " + withdraw);
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

            mTransactionsListAdapter = new TransactionsListAdapter(mContext, mTransactionsList, mCompaniesMap);
            mTransactionsRecyclerView.setAdapter(mTransactionsListAdapter);
        } else {
            Log.v(TAG, "The user does not have any sms");
        }
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
