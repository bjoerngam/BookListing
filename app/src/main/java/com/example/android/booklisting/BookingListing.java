package com.example.android.booklisting;

/**
 * Created by bjoern on 12.02.17.
 */

public class BookingListing
{
    private String BookTitle, BookAuthor, BookPages;

    /**
     *
     * @param title The title of the book
     * @param author The name of the author
     * @param pages the number of pages
     */
    public BookingListing(String title, String author, String pages){

        BookTitle = title;
        BookAuthor = author;
        BookPages = pages;
    }

    public String getBookTitle(){return BookTitle;}
    public String getBookAuthor() {return BookAuthor;}
    public String getBookPages() {return BookPages;}

    /**
     * Always usefull for debuging
     * @return all of the local variables
     */
    @Override
    public String toString() {
        return BookTitle + " "+ BookAuthor + " "+ BookPages;
    }
}
