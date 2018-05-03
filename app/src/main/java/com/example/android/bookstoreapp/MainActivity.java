package com.example.android.bookstoreapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstoreapp.data.BookContract.BookEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int BOOK_LOADER = 0;
    BookCursorAdapter mCursorAdapter;
    private Button mSaleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView mBookListView = findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        mBookListView.setEmptyView(emptyView);

        mCursorAdapter = new BookCursorAdapter(this, null);
        mBookListView.setAdapter(mCursorAdapter);

        mBookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                Uri currentBookUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id);
                intent.setData(currentBookUri);
                startActivity(intent);
            }
        });

        final TextView summaryQuantityTextView = findViewById(R.id.summary_quantity);
        mSaleButton = findViewById(R.id.list_item_sale_button);
        mSaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = mCursorAdapter.getCursor();
                int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
                int bookQuantity = cursor.getInt(quantityColumnIndex);
                if (!(bookQuantity < 0)) {
                    bookQuantity -= 1;
                } else {
                    Toast.makeText(getApplicationContext(), "Quantity must be greater than 0!", Toast.LENGTH_SHORT).show();
                }
                summaryQuantityTextView.setText(Integer.toString(bookQuantity));
            }
        });

        getLoaderManager().initLoader(BOOK_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_TITLE,
                BookEntry.COLUMN_BOOK_AUTHOR,
                BookEntry.COLUMN_QUANTITY,
                BookEntry.COLUMN_PRICE};
        return new CursorLoader(this,
                BookEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_entries:
                int rowsDeleted = getContentResolver().delete(BookEntry.CONTENT_URI, null, null);
                Log.v("MainActivity", rowsDeleted + " rows deleted from book database");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
