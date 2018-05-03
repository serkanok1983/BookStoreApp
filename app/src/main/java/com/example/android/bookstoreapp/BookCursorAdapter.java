package com.example.android.bookstoreapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.bookstoreapp.data.BookContract.BookEntry;

/**
 * Created by serka on 30.04.2018.
 */

public class BookCursorAdapter extends CursorAdapter {

    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.name);
        TextView summaryTextView = view.findViewById(R.id.summary);
        TextView summaryQuantityTextView = view.findViewById(R.id.summary_quantity);
        TextView summaryPriceTextView = view.findViewById(R.id.summary_price);
        int titleColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_TITLE);
        int authorColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_AUTHOR);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);
        String bookTitle = cursor.getString(titleColumnIndex);
        String bookAuthor = cursor.getString(authorColumnIndex);
        int bookQuantity = cursor.getInt(quantityColumnIndex);
        double bookPrice = cursor.getDouble(priceColumnIndex);
        nameTextView.setText(bookTitle);
        summaryTextView.setText(bookAuthor);
        summaryQuantityTextView.setText(Integer.toString(bookQuantity));
        summaryPriceTextView.setText(Double.toString(bookPrice));
    }
}
