package css.cis3334.sqliteplanets;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PlanetTableDAO planetDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        planetDB = new PlanetTableDAO(this);
        planetDB.open();

        // create a new planet
        Planet saturn = planetDB.createPlanet("Saturn", 18.5f);

        // read all the planet records into an arraylist
        List<Planet> planetList = planetDB.getAllPlanets();
        // Display the first planet in the arraylist from the database
        TextView tvPlanet = (TextView) findViewById(R.id.textViewPlanet);
        tvPlanet.setText( "The first planet in the database is " + planetList.get(0).getName() );

        // Update Saturn's gravity in the database
        saturn.setGravity(10.4f);
        planetDB.updatePlanet(saturn);

        // Delete the planet mars
        Planet mars = planetDB.getOnePlanet("Mars");
        planetDB.deletePlanet(mars);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        planetDB.close();
    }

}
