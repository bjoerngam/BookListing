package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<BookingListing>> {

    // The basic URL for the Google book API
    private String mGoogle_Book_API_Default_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";

    // Contains the search string
    private String mGoogle_Book_Search_String;
    // Contains the complete URL string
    private String mGoogle_Complete_String = "";

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOKS_LOADER_ID = 123;

    /** Adapter for the list of our bookinglisting */
    private BookAdapter mAdapter;
    private ListView mbooksList;

    @BindView(R.id.searchButton) Button mSearchButton;
    @BindView(R.id.searchString) EditText mSearchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Find a reference to the {@link ListView} in the layout
        mbooksList = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of bookinglisting as input
        mAdapter = new BookAdapter(this, new ArrayList<BookingListing>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        mbooksList.setAdapter(mAdapter);
        mbooksList.setEmptyView(findViewById(R.id.enptyTextView));
        mSearchString.setInputType(InputType.TYPE_CLASS_TEXT);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // We are checking if the network connection is working
                if (isNetworkConnected()) {
                    LoaderManager loaderManager = getLoaderManager();
                    mGoogle_Book_Search_String = mSearchString.getText().toString();
                    // When the mGoogle_Complete_String is bigger than zero we have to cleanup the complete string.
                    if (mGoogle_Complete_String.length() > 0){
                        mGoogle_Complete_String = "";
                        mGoogle_Complete_String = mGoogle_Book_API_Default_URL + mGoogle_Book_Search_String +"&maxResults=10";
                        loaderManager.restartLoader(BOOKS_LOADER_ID, null, MainActivity.this);
                    }else{
                        // The first launch
                        mGoogle_Complete_String = mGoogle_Book_API_Default_URL + mGoogle_Book_Search_String +"&maxResults=10";
                        loaderManager.initLoader(BOOKS_LOADER_ID, null, MainActivity.this);
                    }

                    mSearchString.setText("");
                } else {

                    TextView myEmptyText = (TextView) findViewById(R.id.enptyTextView);
                    myEmptyText.setText("No Internet Connection.");
                }

            }
        });
    }
    @Override
    public Loader<List<BookingListing>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, mGoogle_Complete_String);
    }

    @Override
    public void onLoadFinished(Loader<List<BookingListing>> loader, List<BookingListing> books) {
        // Clear the adapter of previous books data
        TextView emptySpace = (TextView) findViewById(R.id.enptyTextView);
        emptySpace.setVisibility(View.INVISIBLE);
        mAdapter.clear();

        // If there is a valid list of {@link BookingListing}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);

        }


    }

    @Override
    public void onLoaderReset(Loader<List<BookingListing>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    private boolean isNetworkConnected() {
        // The helper function for checking if the network connection is working.
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
