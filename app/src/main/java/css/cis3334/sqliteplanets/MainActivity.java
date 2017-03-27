package css.cis3334.sqliteplanets;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        // read all the planet records into an arraylist
        List<String> planetList = new ArrayList<String>();
        SQLiteDatabase database;
        database = dbHelper.getWritableDatabase();
        // query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
        Cursor cursor = database.query("planets",null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String planetName = cursor.getString(1);            // planet name is the first field in the database, the _id field is number zero
            //String planetName = cursor.getString(c.getColumnIndex("title"));
            //String planetName = cursor.getString(c.getColumnIndex(DatabaseHelper.DB_FIELD_TITLE));
            planetList.add(planetName);
        }
        // make sure to close the cursor
        cursor.close();

        // Display the first planet in the arraylist from the database
        TextView tvPlanet = (TextView) findViewById(R.id.textViewPlanet);
        tvPlanet.setText(planetList.get(0));

    }
}
