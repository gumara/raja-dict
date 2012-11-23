package com.gadgetplus.rajadict;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	public DatabaseHandler(Context context) {
		super(context, context.getExternalFilesDir(null).getAbsolutePath()
				+ "/" + "teenword.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS words (id INTEGER PRIMARY KEY, word STRING, meaning STRING)");
		Log.d("DB", "Create Table Successfully.");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	public String[] SelectAllData() {
		// TODO Auto-generated method stub
		try {
			String arrData[] = null;
			SQLiteDatabase db;
			db = this.getReadableDatabase(); // Read Data

			String strSQL = "SELECT word FROM  words";
			Cursor cursor = db.rawQuery(strSQL, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					arrData = new String[cursor.getCount()];
					/***
					 * [x] = Name
					 */
					int i = 0;
					do {
						arrData[i] = cursor.getString(0);
						i++;
					} while (cursor.moveToNext());

				}
			}
			cursor.close();

			return arrData;

		} catch (Exception e) {
			return null;
		}
	}

	public String[] SearchWord(String strWord) {
		// TODO Auto-generated method stub
		try {
			String arrData[] = null;

			SQLiteDatabase db;
			db = this.getReadableDatabase(); // Read Data

			Cursor cursor = db.query("words", new String[] { "*" }, "word=?",
					new String[] { String.valueOf(strWord) }, null, null, null,
					null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					arrData = new String[cursor.getColumnCount()];
					/***
					 * 0 = id 1 = word 2 = meaning
					 */
					arrData[0] = cursor.getString(0);
					arrData[1] = cursor.getString(1);
					arrData[2] = cursor.getString(2);
				}
			}
			cursor.close();
			db.close();
			return arrData;

		} catch (Exception e) {
			return null;
		}

	}

	public Integer getTotalRow() {
		// TODO Auto-generated method stub
		try {
			SQLiteDatabase db;
			db = this.getReadableDatabase(); // Read Data
			Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM words", null);
			cursor.moveToFirst();
			int count = cursor.getInt(0);
			cursor.close();
			return count;
		} catch (Exception e) {
			return 0;
		}
	}

}
