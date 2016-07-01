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

import com.jiahaoliuliu.akami.model.Sms;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Projection. The fields of the sms to be returned
    private static final String[] sProjection = {
        Sms.COLUMN_ID,
        Sms.COLUMN_THREAD_ID,
        Sms.COLUMN_ADDRESS,
        Sms.COLUMN_DATE,
        Sms.COLUMN_DATE_SENT,
        Sms.COLUMN_BODY,
        Sms.COLUMN_TYPE
    };

    private Context mContext;

    // Views
    private TextView mMessagesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mContext = this;

        // Link the views
        mMessagesTextView = (TextView) findViewById(R.id.messages);

        // read messages
        readAllMessages();
    }

    private void readAllMessages() {
        List<Sms> smssList = new ArrayList<Sms>();
        Sms sms;
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), sProjection, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                sms = new Sms();
                sms.set_id(cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_ID)));
                sms.setThreadId(cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_THREAD_ID)));
                sms.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_ADDRESS)));
                sms.setDate((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_DATE))));
                sms.setDateSent((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_DATE_SENT))));
                sms.setBody((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_BODY))));

                if (cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_TYPE)).contains("1")){
                    sms.setType("inbox");
                } else {
                    sms.setType("sent---------------------------");
                    break;
                }

                Log.v(TAG, "SMS read " + sms);
                smssList.add(sms);
            } while (cursor.moveToNext());

            mMessagesTextView.setText(smssList.toString());
        } else {
            Log.v(TAG, "The user does not have any sms");
            mMessagesTextView.setText("The user does not have any sms");
        }
    }

}
