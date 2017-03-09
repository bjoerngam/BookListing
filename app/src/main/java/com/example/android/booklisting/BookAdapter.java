package com.example.android.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static com.example.android.booklisting.R.id.bookPages;
import static com.example.android.booklisting.R.id.bookTitle;

/**
 * Created by bjoern on 12.02.17.
 */

public class BookAdapter extends ArrayAdapter<BookingListing>
{
    /**
     * Constructs a new {@link BookAdapter}.
     *
     * @param context of the app
     * @param books is the list of bookinglisting, which is the data source of the adapter
     */
    public BookAdapter(Context context, List<BookingListing> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        ViewHolder holder = new ViewHolder();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
            holder.bookTitle = (TextView) convertView.findViewById(bookTitle);
            holder.bookAuthor = (TextView) convertView.findViewById(R.id.bookAutor);
            holder.bookPages = (TextView) convertView.findViewById(bookPages);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        // Find the books at the given position in the list of bookinglisting
        BookingListing currentBook = getItem(position);
        holder.bookTitle.setText(currentBook.getBookTitle());
        holder.bookAuthor.setText(currentBook.getBookAuthor());
        holder.bookPages.setText(currentBook.getBookPages());
        return convertView;
    }

    static class ViewHolder {
        // The ViewHolder design pattern
        TextView bookTitle;
        TextView bookAuthor;
        TextView bookPages;
        int position;
    }
}
