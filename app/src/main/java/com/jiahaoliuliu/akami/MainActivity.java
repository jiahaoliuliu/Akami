package com.jiahaoliuliu.akami;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import com.jiahaoliuliu.akami.model.Expense;
import com.jiahaoliuliu.akami.model.Sms;

import java.util.ArrayList;
import java.util.List;

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
    private TextView mMessagesTextView;

    // Internal variables
    private List<Expense> expensesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link the views
        mMessagesTextView = (TextView) findViewById(R.id.messages);

        // parseExpenses
        parseExpenses();
    }

    private void parseExpenses() {
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), PROJECTION, SELECTION_CLAUSE, SELECTION_ARGS, SORT_ORDER);
        if (cursor.moveToFirst()) {
            expensesList = new ArrayList<Expense>(cursor.getCount());
            do {
                try {
                    Sms sms = new Sms();
                    //                sms.set_id(cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_ID)));
                    sms.setDate((cursor.getLong(cursor.getColumnIndexOrThrow(Sms.COLUMN_DATE))));
                    sms.setBody((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_BODY))));

                    //                Log.v(TAG, "SMS read " + sms);
                    try {
                        Expense expense = new Expense(sms);
                        Log.v(TAG, "Expense parsed " + expense);
                        expensesList.add(expense);
                    } catch (IllegalArgumentException illegalArgumentException) {
                        Log.w(TAG, "Expense unknown " + illegalArgumentException.getMessage());
                    }
                } catch (IllegalArgumentException illegalArgumentException) {
                    Log.w(TAG, "Error getting sms message from content resolver ", illegalArgumentException);
                }
            } while (cursor.moveToNext());
            cursor.close();

            // Print out the list of expenses
            mMessagesTextView.setText(expensesList.toString());
        } else {
            Log.v(TAG, "The user does not have any sms");
            mMessagesTextView.setText("The user does not have any sms");
        }
    }
}
