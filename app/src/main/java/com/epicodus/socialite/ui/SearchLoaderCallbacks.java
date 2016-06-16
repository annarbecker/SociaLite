package com.epicodus.socialite.ui;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.socialite.Constants;
import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Person;
import com.firebase.client.Firebase;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by arbecker on 4/29/16.
 */
public class SearchLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {
    private HashMap<String, String> contact = new HashMap<>();
    private HashMap<String, String> phones = new HashMap<>();
    private Context mContext;
    public static final String QUERY_KEY = "query";
    public static final String TAG = "SearchLoaderCallbacks";
    private List<String> names = new ArrayList<String>();
    private String displayName;
    private String phoneNumber;
    private SharedPreferences mSharedPreferences;
    private String mEvent;
    private SharedPreferences.Editor mSharedPreferencesEditor;
    private ArrayList<String> phoneNumbersList = new ArrayList<>();




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
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEvent = mSharedPreferences.getString(Constants.PREFERENCES_EVENT, null);
        mSharedPreferencesEditor = mSharedPreferences.edit();


        if(tv == null) {
            Log.e(TAG, "TextView null");
        } else if (mContext == null) {
            Log.e(TAG, "Context is null?");
        } else {
            Log.e(TAG, "Nothing is null?!");
        }
        tv.setText("Select Contact to Invite");

        if (cursor.getCount() == 0) {
            return;
        }

        int phoneColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DISPLAY_NAME);
        int lookupColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.LOOKUP_KEY);
        int typeColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.MIMETYPE);

        cursor.moveToFirst();

        String lookupKey = "";
        do {
            String currentLookupKey = cursor.getString(lookupColumnIndex);
            if (!lookupKey.equals(currentLookupKey)) {
                displayName = cursor.getString(nameColumnIndex);
                names.add(displayName);
                lookupKey = currentLookupKey;
            }

            String mimeType = cursor.getString(typeColumnIndex);
            if (mimeType.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                phoneNumber = cursor.getString(phoneColumnIndex);
                phones.put(displayName, phoneNumber);
            } else {
                phoneNumber = null;
            }

            for(String column : cursor.getColumnNames()) {
                Log.d(TAG, column + column + ": " +
                        cursor.getString(cursor.getColumnIndex(column)) + "\n");

                if((column).equals("sort_key")) {
                    contact.put("Name", cursor.getString(cursor.getColumnIndex(column)));

                } if (column.equals("data1")){
                    contact.put("Phone", cursor.getString(cursor.getColumnIndex(column)));
                }
            }
        } while (cursor.moveToNext());

        final ListView mListView = (ListView) ((Activity)mContext).findViewById(R.id.listView2);

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, names);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = ((TextView)view).getText().toString();
                String phone = phones.get(name);
                String event = mEvent;
                Person newContact = new Person(name, event);
                newContact.setPhone(phone);

                phoneNumbersList.add(phone);
                Log.d("phone added to array", phone);
                String phoneNumbers = TextUtils.join(", ", phoneNumbersList);
                Log.d("phone", phoneNumbers);

                addToSharedPreferences(phoneNumbers);

                Toast.makeText(mContext, name + " added to your event", Toast.LENGTH_SHORT).show();

                Firebase userEventsFirebaseRef = new Firebase(Constants.FIREBASE_URL_PERSON).child(event);
                Firebase pushRef = userEventsFirebaseRef.push();
                String eventPushId = pushRef.getKey();
                newContact.setPushId(eventPushId);
                pushRef.setValue(newContact);


            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }

    private void addToSharedPreferences(String phoneNumber) {
        mSharedPreferencesEditor.putString(Constants.INVITEE_PHONE_NUMBERS, phoneNumber).apply();
    }
}