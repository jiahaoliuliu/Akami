package com.jiahaoliuliu.akami.transactionslist;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.jiahaoliuliu.akami.MainApplication;
import com.jiahaoliuliu.akami.R;
import com.jiahaoliuliu.akami.model.Company;
import com.jiahaoliuliu.akami.model.Expense;
import com.jiahaoliuliu.akami.model.ITransactions;
import com.jiahaoliuliu.akami.model.Sms;
import com.jiahaoliuliu.akami.model.Withdraw;
import com.jiahaoliuliu.akami.utils.HeaderUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */
public class TransactionsListModel implements TransactionsListContract.Model {

    private static final String TAG = "TransactionsListModel";

    // Projection. The fields of the sms to be returned
    private static final String[] PROJECTION = {
//        Sms.COLUMN_ID,
            Sms.COLUMN_DATE,
            Sms.COLUMN_BODY
    };

    // Selection query
    private static final String SELECTION_CLAUSE = Sms.COLUMN_TYPE + "=? and " + Sms.COLUMN_ADDRESS + "=?";

    private static final String ADDRESS_ADCB = "ADCBAlert";

    // Selection arguments
    private static final String[] SELECTION_ARGS = {"1", ADDRESS_ADCB};

    // Sort order
    private static final String SORT_ORDER = Sms.COLUMN_DATE + " DESC";

    // Internal variables
    private Map<String, Company> mCompaniesMap;
    private List<ITransactions> mTransactionsList;
    private HashMap<Long, Float> mTransactionsPerMonth;

    @Override
    public Map<String, Company> getCompaniesMap() {
        if (mCompaniesMap == null) {
            mCompaniesMap = generateCompaniesMap();
        }

        return mCompaniesMap;
    }

    @Override
    public List<ITransactions> getTransactionsList() {
        // TODO: Update the data on real time
        if (mTransactionsList == null) {
            mTransactionsList = parseTransactions();
        }

        return mTransactionsList;
    }

    @Override
    public HashMap<Long, Float> getTransactionsPerMonth() {
        return mTransactionsPerMonth;
    }

    private List<ITransactions> parseTransactions() {
        // TODO: Use RxJava here
        List<ITransactions> transactionsList = new ArrayList<>();
        Cursor cursor = MainApplication.getApplication().getContentResolver()
                .query(Uri.parse("content://sms/inbox"), PROJECTION, SELECTION_CLAUSE, SELECTION_ARGS, SORT_ORDER);

            transactionsList.addAll(getDataFromCursor(cursor));
            // Update the transactions per month
            for (ITransactions transactions : transactionsList) {
                updateTransactionsPerMonth(transactions);
            }

        return transactionsList;
    }

    private List<ITransactions> getDataFromCursor(Cursor cursor) {
        List<ITransactions> transactionsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
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
                                transactionsList.add(expense);
                                break;
                            case WITHDRAW_1:
                            case WITHDRAW_2:
                                Withdraw withdraw = new Withdraw(sms);
                                transactionsList.add(withdraw);
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
        } else {
            Log.v(TAG, "The user does not have any sms");
        }

        return transactionsList;
    }

    private void updateTransactionsPerMonth(ITransactions transaction) {
        // Initialize expense per month if needed
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

    // TODO: Use database instead -> Realm
    private Map<String, Company> generateCompaniesMap() {
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

}
