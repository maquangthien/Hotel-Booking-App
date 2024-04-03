package com.example.hotelbookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;




public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Hotel.db";
    private static final int DATABASE_VERSION = 1;

    // role
    private static final String TABLE_ROLE = "role";
    private static final String COLUMN_ROLE_ID = "roleId";
    private static final String COLUMN_ROLE_NAME = "roleName";

    // users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DOB = "DOB";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE_ID_FK = "roleId";

    // hotel
    private static final String TABLE_HOTEL = "hotel";
    private static final String COLUMN_HOTEL_ID = "id";
    private static final String COLUMN_HOTEL_NAME = "hotelName";
    private static final String COLUMN_LOCATION = "location";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng role
        String CREATE_ROLE_TABLE = "CREATE TABLE " + TABLE_ROLE + "("
                + COLUMN_ROLE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ROLE_NAME + " TEXT"
                + ")";
        db.execSQL(CREATE_ROLE_TABLE);


        // Tạo bảng users
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_DOB + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_ROLE_ID_FK + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_ROLE_ID_FK + ") REFERENCES " + TABLE_ROLE + "(" + COLUMN_ROLE_ID + ")"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // tạo bảng hotel
        String CREATE_HOTEL_TABLE = "CREATE TABLE " + TABLE_HOTEL + "("
                + COLUMN_HOTEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_HOTEL_NAME + " TEXT, "
                + COLUMN_LOCATION + " TEXT "
                + ")";
        db.execSQL(CREATE_HOTEL_TABLE);

        insertData(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTEL);
        onCreate(db);
    }

    private void insertData(SQLiteDatabase db) {
        // Thêm dữ liệu mẫu cho role
        insertRole(db, "Admin");
        insertRole(db, "Employee");
        insertRole(db, "Customer");


        String hashPass = PasswordUtils.hashPassword("123");
        // Thêm dữ liệu mẫu cho users
        insertUser(db, "Admin", "Admin@gmail.com", "086868686", hashPass, 1);

        // Thêm dữ liệu mẫu cho hotel
        insertHotel(db, "Hoang gia", "HCM");
        insertHotel(db, "Thanh nghi", "HCM");
        insertHotel(db, "Gia LONG", "HCM");
        insertHotel(db, "Mường Thanh", "HCM");
        insertHotel(db, "Mường Thanh", "Hà Nội");
    }

    public long addUser(String username, String email, String phone, String password, int roleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_PASSWORD, PasswordUtils.hashPassword(password));
        values.put(COLUMN_ROLE_ID_FK, roleId);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }


    public int getUserRoleId(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ROLE_ID_FK, COLUMN_PASSWORD};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int roleId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int passwordColumnIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
                if (passwordColumnIndex >= 0) {
                    String hashedPassword = cursor.getString(passwordColumnIndex);
                    if (PasswordUtils.checkPassword(password, hashedPassword)) {
                        int roleIdColumnIndex = cursor.getColumnIndex(COLUMN_ROLE_ID_FK);
                        if (roleIdColumnIndex >= 0) {
                            roleId = cursor.getInt(roleIdColumnIndex);
                            break;
                        }
                    }
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return roleId;
    }

    private void insertRole(SQLiteDatabase db,  String roleName ) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROLE_NAME, roleName);
        db.insert(TABLE_ROLE, null, values);
    }

    public void insertUser(SQLiteDatabase db,String username, String email, String phone, String password, int roleId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE_ID_FK, roleId);
        db.insert(TABLE_USERS, null, values);
    }


    public void insertHotel(SQLiteDatabase db,String hotelName, String location) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOTEL_NAME, hotelName);
        values.put(COLUMN_LOCATION, location);
        db.insert(TABLE_HOTEL, null, values);
    }

    public List<String> getHotelSuggestions(String query) {
        List<String> suggestions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_HOTEL_NAME};
        String selection = COLUMN_HOTEL_NAME + " LIKE ?";
        String[] selectionArgs = {"%" + query + "%"};
        Cursor cursor = db.query(TABLE_HOTEL, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int columnIndex = cursor.getColumnIndex(COLUMN_HOTEL_NAME);
                if (columnIndex >= 0) {
                    String hotelName = cursor.getString(columnIndex);
                    suggestions.add(hotelName);
                }
            }
            cursor.close();
        }
        db.close();
        return suggestions;
    }

}
