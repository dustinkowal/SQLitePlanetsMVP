package css.cis3334.sqliteplanets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 *  provides methods to access the planet database table in the SQLite database
 */

public class PlanetTableDAO {
    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    /**
     * This construction creates the database help object that creates the planet database and table
     * @param context - The activity calling this.
     */
    public PlanetTableDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Opens up the planet table on the database and gets a link to the SQLite database
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Closes the SQLite database
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Saves a new planet to the planet table in the SQLite database.
     * The C in CRUD for Creating a database record
     * @param planetName - name of the planet
     * @param planetGravity - gravity of the planet
     * @return Planet - returns the  planet that was created
     */
    public Planet createPlanet(String planetName, Float planetGravity) {
        // store new values into a ContentValues object
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.DB_FIELD_PLANETNAME, planetName);
        values.put(DatabaseHelper.DB_FIELD_GRAVITY, planetGravity);
        long insertId = database.insert(DatabaseHelper.DB_TABLE_NAME, null, values);
        Planet newPlanet = new Planet(insertId, planetName, planetGravity);
        return newPlanet;
    }

    /**
     * Get an list of all the planets in the database
     * The R in CRUD for Reading database records
     * @return the list of planet objects
     */
    public List<Planet> getAllPlanets() {
        List<Planet> planets = new ArrayList<Planet>();
        // query the database for all the fields of all the records in the planet table
        Cursor cursor = database.query(DatabaseHelper.DB_TABLE_NAME,
                null, null, null, null, null, null);
        // loop through the cursor converting each row into a planet object
        while (cursor.moveToNext()) {
            Planet planet = cursorToPlanet(cursor);
            planets.add(planet);
        }
        // make sure to close the cursor
        cursor.close();
        return planets;
    }

    /**
     * Find the planet with the given name and return it
     * The R in CRUD for Reading database records
     * @param name - the name of the planet to find in the database table
     * @return - return an planet object, maybe a fake null object if one is not found.
     */
    public Planet getOnePlanet(String name) {
        // create a null planet to return if one is not found
        Planet planet = new Planet(0L,"",0.0F);
        // query the database for all the fields of all the records in the planet table
        // query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
        Cursor cursor = database.query(DatabaseHelper.DB_TABLE_NAME,
                null,                                           /* all collumns */
                DatabaseHelper.DB_FIELD_PLANETNAME + " = ?",    /* where clause "title = ?" */
                new String[] { name } ,                         /* where parameters is an array of Strings including only the name */
                null, null, null);
        // check if we found a record
        if (cursor.getCount() >0) {
            cursor.moveToFirst();
            planet = cursorToPlanet(cursor);
        }
        // make sure to close the cursor
        cursor.close();
        return planet;
    }

    /**
     * Convert the current record the cursor points to into a planet object
     * @param cursor - points to a record in the planet table of the SQLite database
     * @return  a planet object
     */
    private Planet cursorToPlanet(Cursor cursor) {
        Long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.DB_FIELD_ID));
        String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DB_FIELD_PLANETNAME));
        Float gravity = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.DB_FIELD_GRAVITY));
        Planet planet = new Planet(id,name,gravity);
        return planet;
    }

    /**
     * update the matching planet record in the database to match the planet object given as a parameter
     * The U in CRUD for Updating a databsae record
     * @param planet - object with the new planet data to update the database
     * @return  number of rows updated - should generally be 1
     * */
    public int updatePlanet(Planet planet) {
        // store new values into a ContentValues object
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.DB_FIELD_PLANETNAME, planet.getName());
        values.put(DatabaseHelper.DB_FIELD_GRAVITY, planet.getGravity());

        // need an String array with the planet id in it
        String[] strId = new String[] { planet.get_id().toString() };
        // update(String table, ContentValues values, String whereClause, String[] whereArgs)
        return database.update(DatabaseHelper.DB_TABLE_NAME, values, DatabaseHelper.DB_FIELD_ID + " = ?", strId);
    }

    /**
     * Delete the given planet from the planet table in the database
     * The D in CRUD for Deleting a database record
     * @param planet
     * @return  number of rows deleted, should generally be 1
     * */
    public int deletePlanet(Planet planet) {
        // need an String array with the planet id in it
        String[] strId = new String[] { planet.get_id().toString() };
        //delete(String table, String whereClause, String[] whereArgs)
        return database.delete(DatabaseHelper.DB_TABLE_NAME, DatabaseHelper.DB_FIELD_ID + " = ?", strId);
    }





}
