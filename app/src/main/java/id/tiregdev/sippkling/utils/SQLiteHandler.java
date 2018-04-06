package id.tiregdev.sippkling.utils;

/**
 * Created by SONY-VAIO on 10/5/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_sippkling";

    // Login table name
    private static final String TABLE_USER = "petugas";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ID_PETUGAS = "id_petugas";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_KECAMATAN = "kecamatan";
    private static final String KEY_KELURAHAN = "kelurahan";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ID_PETUGAS + " TEXT UNIQUE," + KEY_NAMA + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_KECAMATAN + " TEXT,"
                + KEY_KELURAHAN + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String id_petugas, String kecamatan, String kelurahan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAMA, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_ID_PETUGAS, id_petugas);
        values.put(KEY_KECAMATAN, kecamatan);
        values.put(KEY_KELURAHAN, kelurahan);

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }
//
//    public void updateUser(String name, String email, String uid, String alamat, String no_telp,
//                           String tanggal_lahir, String bio, String foto_user, String jenis_kelamin,
//                           String created_at, String updated_at) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, name); // Name
//        values.put(KEY_EMAIL, email); // Email
//        values.put(KEY_UID, uid);
//        values.put(KEY_ALAMAT, alamat); // Created At
//        values.put(KEY_NO_TELP, no_telp);
//        values.put(KEY_TANGGAL_LAHIR, tanggal_lahir);
//        values.put(KEY_BIO, bio);
//        values.put(KEY_FOTO_USER, foto_user);
//        values.put(KEY_JENIS_KELAMIN, jenis_kelamin);
//        values.put(KEY_CREATED_AT, created_at);
//        values.put(KEY_UPDATED_AT, updated_at);
//
//        // Inserting Row
//        long id = db.update(TABLE_USER, values, KEY_UID + " = ? ", new String[] { String.valueOf(uid) });
//        db.close(); // Closing database connection
//
//        Log.d(TAG, "user edited into sqlite: " + id);
//    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("id_petugas", cursor.getString(1));
            user.put("nama", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("kecamatan", cursor.getString(4));
            user.put("kelurahan", cursor.getString(5));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
