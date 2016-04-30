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
import android.widget.TextView;

import com.epicodus.socialite.R;

import java.util.HashMap;

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
        // Where the Contactables table excels is matching text queries,
        // not just data dumps from Contacts db.  One search term is used to query
        // display name, email address and phone number.  In this case, the query was extracted
        // from an incoming intent in the handleIntent() method, via the
        // intent.getStringExtra() method.

        // BEGIN_INCLUDE(uri_with_query)
        String query = args.getString(QUERY_KEY);
        Uri uri = Uri.withAppendedPath(
                ContactsContract.CommonDataKinds.Contactables.CONTENT_FILTER_URI, query);
        // END_INCLUDE(uri_with_query)


        // BEGIN_INCLUDE(cursor_loader)
        // Easy way to limit the query to contacts with phone numbers.
        String selection =
                ContactsContract.CommonDataKinds.Contactables.HAS_PHONE_NUMBER + " = " + 1;

        // Sort results such that rows for the same contact stay together.
        String sortBy = ContactsContract.CommonDataKinds.Contactables.LOOKUP_KEY;

        return new CursorLoader(
                mContext,  // Context
                uri,       // URI representing the table/resource to be queried
                null,      // projection - the list of columns to return.  Null means "all"
                selection, // selection - Which rows to return (condition rows must match)
                null,      // selection args - can be provided separately and subbed into selection.
                sortBy);   // string specifying sort order
        // END_INCLUDE(cursor_loader)
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        TextView tv  = (TextView) ((Activity)mContext).findViewById(R.id.sample_output);
        if(tv == null) {
            Log.e(TAG, "TextView null");
        } else if (mContext == null) {
            Log.e(TAG, "Context is null?");
        } else {
            Log.e(TAG, "Nothing is null?!");
        }
        // Reset text in case of a previous query
        tv.setText("Search Contacts");

        if (cursor.getCount() == 0) {
            return;
        }

        // Pulling the relevant value from the cursor requires knowing the column index to pull
        // it from.
        // BEGIN_INCLUDE(get_columns)
        int phoneColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int emailColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS);
        int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DISPLAY_NAME);
        int lookupColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.LOOKUP_KEY);
        int typeColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.MIMETYPE);
        // END_INCLUDE(get_columns)

        cursor.moveToFirst();
        // Lookup key is the easiest way to verify a row of data is for the same
        // contact as the previous row.
        String lookupKey = "";
        do {
            // BEGIN_INCLUDE(lookup_key)
            String currentLookupKey = cursor.getString(lookupColumnIndex);
            if (!lookupKey.equals(currentLookupKey)) {
                String displayName = cursor.getString(nameColumnIndex);
                tv.append(displayName + "\n");
                lookupKey = currentLookupKey;
            }
            // END_INCLUDE(lookup_key)

            // BEGIN_INCLUDE(retrieve_data)
            // The data type can be determined using the mime type column.
            String mimeType = cursor.getString(typeColumnIndex);
            if (mimeType.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                tv.append("\tPhone Number: " + cursor.getString(phoneColumnIndex) + "\n");
            } else if (mimeType.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                tv.append("\tEmail Address: " + cursor.getString(emailColumnIndex) + "\n");
            }
            // END_INCLUDE(retrieve_data)

            // Look at DDMS to see all the columns returned by a query to Contactables.
            // Behold, the firehose!

            for(String column : cursor.getColumnNames()) {
                Log.d(TAG, column + column + ": " +
                        cursor.getString(cursor.getColumnIndex(column)) + "\n");

                if((column).equals("sort_key")) {
                    Log.d("First column value: ", cursor.getString(cursor.getColumnIndex(column)));
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