package com.example.steven.drinkpicker.asynctasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.steven.drinkpicker.database.DrinkBacContract;

public class DrinkBacAsyncTask extends AsyncTask<Void, Void, Cursor> {

    private Context context;
    private AsyncTaskCompleteListener<Cursor> listener;

    public DrinkBacAsyncTask(Context context, AsyncTaskCompleteListener<Cursor> listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        Cursor cursor = context.getContentResolver().query(DrinkBacContract.DrinkBacEntry.CONTENT_URI,
                null, null, null,
                DrinkBacContract.DrinkBacEntry.COLUMN_START_TIME + " DESC");
        return cursor;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        if (cursor.getCount() != 0) {
            listener.onTaskComplete(cursor);
        }
    }
}
