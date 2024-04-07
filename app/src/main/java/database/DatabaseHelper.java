package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.hotelbookingapp.PasswordUtils;

import java.util.ArrayList;
import java.util.List;

import model.Hotel;
import model.User;


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
    private static final String COLUMN_STAR_RATING = "star_rating";
    private static final String COLUMN_IMAGE = "image";


    //room
    private static final String TABLE_ROOM = "room";
    private static final String COLUMN_ROOM_ID = "id";
    private static final String COLUMN_ROOM_NAME = "roomName";
    private static final String COLUMN_ROOM_TYPE = "roomType";
    private static final String COLUMN_ROOM_RATE = "rate";
    private static final String COLUMN_ROOM_IMAGE = "image";
    private static final String COLUMN_HOTEL_ID_FK = "hotelID";

    //booking
    private static final String TABLE_BOOKING = "booking";
    private static final String COLUMN_BOOKING_ID = "id";
    private static final String COLUMN_ROOM_ID_FK = "roomID";
    private static final String COLUMN_USER_ID_FK = "userID";
    private static final String COLUMN_CHECK_IN_DATE = "checkIn";
    private static final String COLUMN_CHECK_OUT_DATE = "checkOut";
    private static final String COLUMN_IS_CONFIRMED = "status";



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
                + COLUMN_LOCATION + " TEXT ,"
                + COLUMN_STAR_RATING + " INTEGER,"  // Thêm cột star_rating
                + COLUMN_IMAGE + " TEXT"
                + ")";
        db.execSQL(CREATE_HOTEL_TABLE);

        // tạo bảng room
        String CREATE_ROOM_TABLE = "CREATE TABLE " + TABLE_ROOM + "("
                + COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ROOM_NAME + " TEXT,"
                + COLUMN_ROOM_TYPE + " TEXT,"
                + COLUMN_ROOM_RATE + " REAL,"
                + COLUMN_ROOM_IMAGE + " TEXT"
                + ")";
        db.execSQL(CREATE_ROOM_TABLE);

        // taoh bảng booking
        String CREATE_BOOKING_TABLE = "CREATE TABLE " + TABLE_BOOKING + "("
                + COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ROOM_ID_FK + " INTEGER,"
                + COLUMN_USER_ID_FK + " INTEGER,"
                + COLUMN_CHECK_IN_DATE + " TEXT,"
                + COLUMN_CHECK_OUT_DATE + " TEXT,"
                + COLUMN_IS_CONFIRMED + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_ROOM_ID_FK + ") REFERENCES " + TABLE_ROOM + "(" + COLUMN_ROOM_ID + "),"
                + "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(CREATE_BOOKING_TABLE);

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
        insertUser(db, "nd", "nd@gmail.com", "321321214", hashPass, 3);


        // Thêm dữ liệu mẫu cho hotel
        insertHotel(db, "Hoang gia", "HCM", 5, "https://cdn.tgdd.vn/Products/Images/42/78124/iphone-7-plusgold-400x460-400x460.png\t");
        insertHotel(db, "Thanh nghi", "HCM", 4, "https://cdn.tgdd.vn/Products/Images/522/221775/ipad-pro-12-9-inch-wifi-128gb-2020-xam-400x460-1-400x460.png\t");
        insertHotel(db, "Gia LONG", "HCM", 4, "https://cdn.tgdd.vn/Products/Images/522/221775/ipad-pro-12-9-inch-wifi-128gb-2020-xam-400x460-1-400x460.png\t");
        insertHotel(db, "Mường Thanh", "HCM", 2, "https://cdn.tgdd.vn/Products/Images/42/214909/samsung-galaxynote-10-lite-chi-tiet-1-400x460.png\t");
        insertHotel(db, "Mường Thanh", "Hà Nội", 4, "https://cdn.tgdd.vn/Products/Images/42/214909/samsung-galaxynote-10-lite-chi-tiet-1-400x460.png\t");
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_PASSWORD, PasswordUtils.hashPassword(user.getPassword()));
        values.put(COLUMN_ROLE_ID_FK, 3);
        values.put(COLUMN_DOB, user.getDOB());
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


    private void insertHotel(SQLiteDatabase db, String hotelName, String location, int starRating, String image) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOTEL_NAME, hotelName);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_STAR_RATING, Integer.parseInt(String.valueOf(starRating)));
        values.put(COLUMN_IMAGE, image);
        db.insert(TABLE_HOTEL, null, values);
    }


    public Cursor getUserInfoById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_PHONE, COLUMN_DOB};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};
        return db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
    }

    public static String getColumnUsername() {
        return COLUMN_USERNAME;
    }
    public int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT userId FROM user WHERE username=?", new String[]{username});
        int userId = -1;
        if (cursor.moveToFirst())
            userId = cursor.getInt(0);
        cursor.close();
        db.close();
        return userId;
    }
//    public int getUserId() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {COLUMN_ID};
//        String selection = COLUMN_USERNAME + "=?";
//        String[] selectionArgs = {username};
//        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
//        int userId = 0;
//        if (cursor != null && cursor.moveToFirst()) {
//            int columnIndex = cursor.getColumnIndex(COLUMN_ID);
//            if (columnIndex >= 0) {
//                userId = cursor.getInt(columnIndex);
//            }
//            cursor.close();
//        }
//        db.close();
//        return userId;
//    }

    public static String getColumnEmail() {
        return COLUMN_EMAIL;
    }

    public static String getColumnPhone() {
        return COLUMN_PHONE;
    }

    public static String getColumnDob() {
        return COLUMN_DOB;
    }



//    public List<Hotel> getAllHotels() {
//        List<Hotel> hotelList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {COLUMN_HOTEL_NAME, COLUMN_LOCATION, COLUMN_STAR_RATING, COLUMN_IMAGE};
//        Cursor cursor = db.query(TABLE_HOTEL, columns, null, null, null, null, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                String hotelName = cursor.getString(cursor.getColumnIndex(COLUMN_HOTEL_NAME));
//                String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
//                int starRating = cursor.getInt(cursor.getColumnIndex(COLUMN_STAR_RATING));
//                String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
//                Hotel hotel = new Hotel(hotelName, location, starRating, image);
//                hotelList.add(hotel);
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//        db.close();
//        return hotelList;
//    }

}
