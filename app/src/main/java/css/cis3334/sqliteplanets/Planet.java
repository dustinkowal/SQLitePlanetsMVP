package css.cis3334.sqliteplanets;

/**
 * store data on a single planet. Used in conjunction with the planet table in the SQLite database
 *
 * The fields in the class should match the fields in the planet database table
 */

public class Planet {
    private Long _id;        // database record id
    private String name;        // planet name
    private Float gravity;      // gravity value on this planet's surface

    public Planet(Long _id, String name, Float gravity) {
        this._id = _id;
        this.name = name;
        this.gravity = gravity;
    }

    public Long get_id() {
        return _id;
    }

    public Float getGravity() {
        return gravity;
    }

    public void setGravity(Float gravity) {
        this.gravity = gravity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "name='" + name + '\'' +
                ", gravity=" + gravity +
                '}';
    }

}
