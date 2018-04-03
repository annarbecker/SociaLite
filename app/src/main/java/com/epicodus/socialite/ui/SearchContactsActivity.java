package com.epicodus.socialite.ui;


import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Event;

import org.parceler.Parcels;


public class SearchContactsActivity extends AppCompatActivity {

    public static final int CONTACT_QUERY_LOADER = 0;
    public static final String QUERY_KEY = "query";
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contacts);

        this.event = Parcels.unwrap(getIntent().getParcelableExtra("newEvent"));

        if (getIntent() != null) {
            handleIntent(getIntent());
        }

        if (weHavePermissionToReadContacts()) {
            readTheContacts();
        } else {
            requestReadContactsPermissionFirst();
        }
    }

    private boolean weHavePermissionToReadContacts() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void readTheContacts() {
        ContactsContract.Contacts.getLookupUri(getContentResolver(), ContactsContract.Contacts.CONTENT_URI);
    }

    private void requestReadContactsPermissionFirst() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.READ_CONTACTS)) {
            Toast.makeText(this, "We need permission so you can text your friends.",
                    Toast.LENGTH_LONG).show();
            requestForResultContactsPermission();
        } else {
            requestForResultContactsPermission();
        }
    }

    private void requestForResultContactsPermission() {
        ActivityCompat.requestPermissions(
                this, new String[]{Manifest.permission.READ_CONTACTS}, 123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            readTheContacts();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Bundle bundle = new Bundle();
            bundle.putString(QUERY_KEY, query);

            SearchLoaderCallbacks loaderCallbacks = new SearchLoaderCallbacks(this);
            getLoaderManager().restartLoader(CONTACT_QUERY_LOADER, bundle, loaderCallbacks);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==  android.R.id.home) {
            Intent intent = new Intent(SearchContactsActivity.this, PlanActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("newEvent", Parcels.wrap(this.event));
            startActivity(intent);
        }
        return true;
    }
}
