package ipt.ipg.contactos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ContactHandler extends SQLiteOpenHelper {

    // All variables about DB
    // Database name
    private static final String DATABASE_NAME = "contactos";

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Contacts table name
    private static final String TABLE_CONTACT = "contacts";

    // Table Column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHOTOGRAPH = "photograph";

    private String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_PHONE, COLUMN_EMAIL, COLUMN_ADDRESS, COLUMN_PHOTOGRAPH};

    // Create database
    public ContactHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_CONTACT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_ADDRESS + " TEXT,"
                + COLUMN_PHOTOGRAPH + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(db);
    }

    //Add Contact
    public boolean addContactDetails(Contact contact){
        // Get db writable
        SQLiteDatabase db = this.getWritableDatabase();

        // Get the values to insert
        ContentValues vals = new ContentValues();
        vals.put(COLUMN_NAME, contact.getName());
        vals.put(COLUMN_PHONE, contact.getPhoneNumber());
        vals.put(COLUMN_EMAIL, contact.getEmail());
        vals.put(COLUMN_ADDRESS, contact.getPostalAddress());
        vals.put(COLUMN_PHOTOGRAPH, contact.getPhotograph());

        // Insert values into table
        long i = db.insert(TABLE_CONTACT, null, vals);
        // Close database
        db.close();

        if(i != 0){
            return true;
        }else{
            return false;
        }
    }


    // Reading all contacts
    public List<Contact> readAllContacts(){
        // Get db writable
        SQLiteDatabase db = this.getWritableDatabase();

        // Define contacts list
        List<Contact> contacts = new ArrayList<Contact>();

        Cursor cursor = db.query(TABLE_CONTACT, columns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Contact contact = new Contact();
            contact.setID(Integer.parseInt(cursor.getString(0)));
            contact.setName(cursor.getString(1));
            contact.setPhoneNumber(cursor.getString(2));
            contact.setEmail(cursor.getString(3));
            contact.setPostalAddress(cursor.getString(4));
            contact.setPhotograph(cursor.getString(5));
            contacts.add(contact);
            cursor.moveToNext();
        }

        // Make sure to close the cursor
        cursor.close();
        return contacts;
    }

    // Update contact contact
    public boolean editContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(COLUMN_NAME, contact.getName());
        vals.put(COLUMN_PHONE, contact.getPhoneNumber());

        // updating row
        int i = db.update(TABLE_CONTACT, vals, COLUMN_ID + " = ?",  new String[] { String.valueOf(contact.getID()) });

        db.close();

        if(i != 0){
            return true;
        }else{
            return false;
        }

    }

    // Deleting contact
    public boolean deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        int i = db.delete(TABLE_CONTACT, COLUMN_ID + " = ?",  new String[] { String.valueOf(id) });

        db.close();

        if(i != 0){
            return true;
        }else{
            return false;
        }
    }
}
