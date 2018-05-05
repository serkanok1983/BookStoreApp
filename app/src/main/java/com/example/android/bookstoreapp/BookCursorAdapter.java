package com.example.android.bookstoreapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstoreapp.data.BookContract.BookEntry;

/**
 * Created by serka on 30.04.2018.
 */

public class BookCursorAdapter extends CursorAdapter {

    private int bookQuantity;

    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.name);
        TextView summaryTextView = view.findViewById(R.id.summary);
        final TextView summaryQuantityTextView = view.findViewById(R.id.summary_quantity);
        TextView summaryPriceTextView = view.findViewById(R.id.summary_price);
        int titleColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_TITLE);
        int authorColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_AUTHOR);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);
        String bookTitle = cursor.getString(titleColumnIndex);
        final String bookAuthor = cursor.getString(authorColumnIndex);
        bookQuantity = cursor.getInt(quantityColumnIndex);
        double bookPrice = cursor.getDouble(priceColumnIndex);
        nameTextView.setText(bookTitle);
        summaryTextView.setText(bookAuthor);
        summaryQuantityTextView.setText(Integer.toString(bookQuantity));
        summaryPriceTextView.setText(Double.toString(bookPrice));
        Button mSaleButton = view.findViewById(R.id.list_item_sale_button);
        mSaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(bookQuantity < 1)) {
                    bookQuantity = Integer.parseInt(summaryQuantityTextView.getText().toString().trim());
                    bookQuantity -= 1;
                } else {
                    Toast.makeText(context, context.getString(R.string.editor_quantity_must_be_positive), Toast.LENGTH_SHORT).show();
                }
                summaryQuantityTextView.setText(Integer.toString(bookQuantity));
            }
        });
    }
}
