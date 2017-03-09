package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.booklisting.util.BookUtils;

import java.util.List;

/**
 * Created by bjoern on 12.02.17.
 */

public class BookLoader extends AsyncTaskLoader<List<BookingListing>>
{
    /** Tag for log messages */
    private static final String LOG_TAG = BookLoader.class.getName();

    private String mURL;

    public BookLoader(Context context, String url){
        super(context);
        mURL = url;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<BookingListing> loadInBackground() {
        if (mURL == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of bookinglist.
        List<BookingListing> books = BookUtils.fetchBookData(mURL);
        return books;
    }


}
