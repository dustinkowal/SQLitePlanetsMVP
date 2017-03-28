package css.cis3334.sqliteplanets;

/***
 Copyright (c) 2008-2012 CommonsWare, LLC
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain	a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.

 From _The Busy Coder's Guide to Android Development_
 http://commonsware.com/Android
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="planet_constants.db";
    private static final int SCHEMA=1;
    static final String DB_TABLE_NAME="planets";
    static final String DB_FIELD_ID ="_id";
    static final String DB_FIELD_PLANETNAME ="title";
    static final String DB_FIELD_GRAVITY ="value";

    // Database creation sql statement
    private static final String DATABASE_CREATE_SQL_STRING = "create table planets ( _id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, value REAL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_SQL_STRING);
        addPlanetData(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),"Upgrading database from version " + oldVersion + " to "
             + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS planets");
        onCreate(db);
    }

    public void addPlanetData(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            ContentValues cv=new ContentValues();

            cv.put(DB_FIELD_PLANETNAME, "Earth");
            cv.put(DB_FIELD_GRAVITY, 9.807f);
            db.insert(DB_TABLE_NAME, null, cv);

            cv.put(DB_FIELD_PLANETNAME, "Jupiter");
            cv.put(DB_FIELD_GRAVITY, 24.8f);
            db.insert(DB_TABLE_NAME, null, cv);

            cv.put(DB_FIELD_PLANETNAME, "Mars");
            cv.put(DB_FIELD_GRAVITY, 3.71f);
            db.insert(DB_TABLE_NAME, null, cv);

            cv.put(DB_FIELD_PLANETNAME, "Mercury");
            cv.put(DB_FIELD_GRAVITY, 3.7f);
            db.insert(DB_TABLE_NAME, null, cv);

            cv.put(DB_FIELD_PLANETNAME, "Venus");
            cv.put(DB_FIELD_GRAVITY, 8.87f);
            db.insert(DB_TABLE_NAME, null, cv);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }


}
