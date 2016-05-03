package com.epicodus.socialite.ui;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.socialite.R;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by arbecker on 4/29/16.
 */
public class SearchLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {
    private HashMap<String, String> contact = new HashMap<>();

    Context mContext;
    public static final String QUERY_KEY = "query";
    public static final String TAG = "SearchLoaderCallbacks";
    public SearchLoaderCallbacks(Context context) {
        mContext = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderIndex, Bundle args) {
        String query = args.getString(QUERY_KEY);
        Uri uri = Uri.withAppendedPath(
                ContactsContract.CommonDataKinds.Contactables.CONTENT_FILTER_URI, query);

        String selection = ContactsContract.CommonDataKinds.Contactables.HAS_PHONE_NUMBER + " = " + 1;
        String sortBy = ContactsContract.CommonDataKinds.Contactables.LOOKUP_KEY;

        return new CursorLoader(
                mContext,  // Context
                uri,       // URI representing the table/resource to be queried
                null,      // projection - the list of columns to return.  Null means "all"
                selection, // selection - Which rows to return (condition rows must match)
                null,      // selection args - can be provided separately and subbed into selection.
                sortBy);   // string specifying sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        final TextView tv  = (TextView) ((Activity)mContext).findViewById(R.id.sample_output);
        if(tv == null) {
            Log.e(TAG, "TextView null");
        } else if (mContext == null) {
            Log.e(TAG, "Context is null?");
        } else {
            Log.e(TAG, "Nothing is null?!");
        }
        tv.setText("Search Contacts");

        if (cursor.getCount() == 0) {
            return;
        }

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, " CONTACT CLICKED!" + tv.getText().toString());

            }
        });

        int phoneColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int emailColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS);
        int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DISPLAY_NAME);
        int lookupColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.LOOKUP_KEY);
        int typeColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.MIMETYPE);

        cursor.moveToFirst();

        String lookupKey = "";
        do {
            String currentLookupKey = cursor.getString(lookupColumnIndex);
            if (!lookupKey.equals(currentLookupKey)) {
                String displayName = cursor.getString(nameColumnIndex);
                tv.append("\n" + displayName + "\n");
                lookupKey = currentLookupKey;
            }

            String mimeType = cursor.getString(typeColumnIndex);
            if (mimeType.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                tv.append("\tPhone Number: " + cursor.getString(phoneColumnIndex) + "\n");
            } else if (mimeType.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                tv.append("\tEmail Address: " + cursor.getString(emailColumnIndex) + "\n");
            }

            for(String column : cursor.getColumnNames()) {
                Log.d(TAG, column + column + ": " +
                        cursor.getString(cursor.getColumnIndex(column)) + "\n");

                if((column).equals("sort_key")) {
                    contact.put("Name", cursor.getString(cursor.getColumnIndex(column)));

                } if (column.equals("data1")){
                    contact.put("Phone", cursor.getString(cursor.getColumnIndex(column)));
                }
                Log.d(TAG, contact.get("Name") + "");
                Log.d(TAG, contact.get("Phone") + "");
            }

        } while (cursor.moveToNext());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }
}