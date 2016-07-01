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
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        if (cursor.moveToFirst()) {
            String message = "";
            do {
                for (int index = 0; index < cursor.getColumnCount(); index++) {
                    sms = new Sms();
                    sms.set_id(cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_ID)));
                    sms.setThreadId(cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_THREAD_ID)));
                    sms.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_ADDRESS)));
                    sms.setPerson((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_PERSON))));
                    sms.setDate((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_DATE))));
                    sms.setDateSent((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_DATE_SENT))));
                    sms.setProtocol((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_PROTOCOL))));
                    sms.setRead((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_READ))));
                    sms.setStatus((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_STATUS))));
                    sms.setType((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_TYPE))));
                    sms.setReplyPathPresent((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_REPLY_PATH_PRESENT))));
                    sms.setSubject((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_SUBJECT))));
                    sms.setBody((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_BODY))));
                    sms.setServiceCenter((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_SERVICE_CENTER))));
                    sms.setLocked((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_LOCKED))));
                    sms.setErrorCode((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_ERROR_CODE))));
                    sms.setSeen((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_SEEN))));
                    sms.setTimed((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_TIMED))));
                    sms.setDeleted((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_DELETED))));
                    sms.setSyncState((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_SYNC_STATE))));
                    sms.setMarker((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_MARKER))));
                    sms.setSource((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_SOURCE))));
                    sms.setBindId((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_BIND_ID))));
                    sms.setMxStatus((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_MX_STATUS))));
                    sms.setMxId((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_MX_ID))));
                    sms.setOutTime((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_OUT_TIME))));
                    sms.setAccount((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_ACCOUNT))));
                    sms.setSimId((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_SIM_ID))));
                    sms.setBlockType((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_BLOCK_TYPE))));
                    sms.setAdvancedSeen((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_ADVANCED_SEEN))));
                    sms.setB2cTtl((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_B2C_TTL))));
                    sms.setB2cNumbers((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_B2C_NUMBERS))));
                    sms.setFakeCellType((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_FAKE_CELL_TYPE))));
                    sms.setUrlRiskyType((cursor.getString(cursor.getColumnIndexOrThrow(Sms.COLUMN_URL_RISKY_TYPE))));

                    Log.v(TAG, "SMS read " + sms);
                    smssList.add(sms);
                }
            } while (cursor.moveToNext());

            mMessagesTextView.setText(smssList.toString());
        } else {
            Log.v(TAG, "The user does not have any sms");
            mMessagesTextView.setText("The user does not have any sms");
        }
    }

}
