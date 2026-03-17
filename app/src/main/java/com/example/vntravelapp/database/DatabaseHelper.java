package com.example.vntravelapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.vntravelapp.models.Combo;
import com.example.vntravelapp.models.Hotel;
import com.example.vntravelapp.models.Tour;
import com.example.vntravelapp.models.TicketOffer;
import com.example.vntravelapp.models.Trip;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "vntravel.db";
    private static final int DATABASE_VERSION = 23; // Tăng version để thêm cột status

    private static final String TABLE_TOURS = "tours";
    private static final String TABLE_HOTELS = "hotels";
    private static final String TABLE_COMBOS = "combos";
    private static final String TABLE_TICKETS = "tickets";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_TRIPS = "trips";
    private static final String TABLE_BOOKED_TICKETS = "booked_tickets";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE_RES = "image_res";
    private static final String COLUMN_IMAGE_URL = "image_url";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_REVIEWS = "reviews";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_ORIGINAL_PRICE = "original_price";
    private static final String COLUMN_BADGE = "badge";
    private static final String COLUMN_DATE_RANGE = "date_range";
    private static final String COLUMN_DISCOUNT = "discount";
    private static final String COLUMN_TYPE = "type";
    
    // User columns
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FULLNAME = "fullname";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_USER_IMAGE = "user_image";

    // Trip columns
    private static final String COLUMN_DEPARTURE = "departure";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_DEP_DATE = "dep_date";
    private static final String COLUMN_DEP_TIME = "dep_time";
    private static final String COLUMN_SEATS = "seats";
    private static final String COLUMN_BRAND = "brand";

    // Booked Ticket columns
    private static final String COLUMN_CUSTOMER_NAME = "customer_name";
    private static final String COLUMN_PICKUP_POINT = "pickup_point";
    private static final String COLUMN_CUSTOMER_PHONE = "customer_phone";
    private static final String COLUMN_TRIP_ID = "trip_id";
    private static final String COLUMN_STATUS = "status"; // upcoming, completed, cancelled

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TOURS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_LOCATION + " TEXT, " + COLUMN_DURATION + " TEXT, " + COLUMN_PRICE + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_IMAGE_RES + " INTEGER, " + COLUMN_IMAGE_URL + " TEXT, " + COLUMN_RATING + " REAL, " + COLUMN_REVIEWS + " INTEGER)");
        db.execSQL("CREATE TABLE " + TABLE_HOTELS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_LOCATION + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_PRICE + " TEXT, " + COLUMN_IMAGE_RES + " INTEGER, " + COLUMN_IMAGE_URL + " TEXT, " + COLUMN_RATING + " REAL, " + COLUMN_REVIEWS + " INTEGER)");
        db.execSQL("CREATE TABLE " + TABLE_COMBOS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_LOCATION + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_ORIGINAL_PRICE + " TEXT, " + COLUMN_PRICE + " TEXT, " + COLUMN_IMAGE_RES + " INTEGER, " + COLUMN_IMAGE_URL + " TEXT, " + COLUMN_RATING + " REAL, " + COLUMN_BADGE + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_TICKETS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_DATE_RANGE + " TEXT, " + COLUMN_PRICE + " TEXT, " + COLUMN_DISCOUNT + " TEXT, " + COLUMN_TYPE + " TEXT, " + COLUMN_IMAGE_RES + " INTEGER, " + COLUMN_IMAGE_URL + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EMAIL + " TEXT UNIQUE, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_FULLNAME + " TEXT, " + COLUMN_PHONE + " TEXT, " + COLUMN_USER_IMAGE + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_TRIPS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DEPARTURE + " TEXT, " + COLUMN_DESTINATION + " TEXT, " + COLUMN_DEP_DATE + " TEXT, " + COLUMN_DEP_TIME + " TEXT, " + COLUMN_SEATS + " INTEGER, " + COLUMN_BRAND + " TEXT, " + COLUMN_PRICE + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_BOOKED_TICKETS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TRIP_ID + " INTEGER, " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_PICKUP_POINT + " TEXT, " + COLUMN_CUSTOMER_PHONE + " TEXT, " + COLUMN_STATUS + " TEXT, FOREIGN KEY(" + COLUMN_TRIP_ID + ") REFERENCES " + TABLE_TRIPS + "(" + COLUMN_ID + "))");
        
        seedData(db);
    }

    private void seedData(SQLiteDatabase db) {
        insertTour(db, "Vịnh Hạ Long", "Quảng Ninh", "2N1Đ", "2.990.000đ", "Khám phá Hạ Long", 0, "https://res.cloudinary.com/dzjlcbwwh/image/upload/v1773626520/1a3903f1-7c6a-4458-9fc6-519589789751.png", 4.8f, 150);
        
        insertTrip(db, "Hà Nội", "Hải Phòng", "2024-08-12", "06:00", 15, "Hải Âu", "150.000đ");
        insertTrip(db, "Hà Nội", "Hải Phòng", "2024-08-12", "08:00", 12, "Hoàng Long", "160.000đ");
        insertTrip(db, "TP. Hồ Chí Minh", "Đà Lạt", "2024-08-12", "07:00", 20, "Phương Trang", "300.000đ");
        insertTrip(db, "Hà Nội", "Sa Pa", "2024-08-12", "22:00", 20, "Sao Việt", "400.000đ");

        // Seed some booked tickets with statuses
        db.execSQL("INSERT INTO " + TABLE_BOOKED_TICKETS + " (trip_id, customer_name, pickup_point, customer_phone, status) VALUES (1, 'Harry', 'Gia Lâm', '0987654321', 'upcoming')");
        db.execSQL("INSERT INTO " + TABLE_BOOKED_TICKETS + " (trip_id, customer_name, pickup_point, customer_phone, status) VALUES (2, 'Harry', 'Mỹ Đình', '0987654321', 'upcoming')");
        db.execSQL("INSERT INTO " + TABLE_BOOKED_TICKETS + " (trip_id, customer_name, pickup_point, customer_phone, status) VALUES (3, 'Harry', 'Miền Đông', '0987654321', 'completed')");
        db.execSQL("INSERT INTO " + TABLE_BOOKED_TICKETS + " (trip_id, customer_name, pickup_point, customer_phone, status) VALUES (4, 'Harry', 'Giáp Bát', '0987654321', 'cancelled')");
    }

    private void insertTour(SQLiteDatabase db, String t, String l, String d, String p, String desc, int r, String u, float rat, int rev) {
        ContentValues v = new ContentValues();
        v.put(COLUMN_TITLE, t); v.put(COLUMN_LOCATION, l); v.put(COLUMN_DURATION, d); v.put(COLUMN_PRICE, p); v.put(COLUMN_DESCRIPTION, desc); v.put(COLUMN_IMAGE_RES, r); v.put(COLUMN_IMAGE_URL, u); v.put(COLUMN_RATING, rat); v.put(COLUMN_REVIEWS, rev);
        db.insert(TABLE_TOURS, null, v);
    }

    private void insertTrip(SQLiteDatabase db, String dep, String dest, String date, String time, int seats, String brand, String price) {
        ContentValues v = new ContentValues();
        v.put(COLUMN_DEPARTURE, dep);
        v.put(COLUMN_DESTINATION, dest);
        v.put(COLUMN_DEP_DATE, date);
        v.put(COLUMN_DEP_TIME, time);
        v.put(COLUMN_SEATS, seats);
        v.put(COLUMN_BRAND, brand);
        v.put(COLUMN_PRICE, price);
        db.insert(TABLE_TRIPS, null, v);
    }

    public long bookTicket(int tripId, String customerName, String pickupPoint, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COLUMN_TRIP_ID, tripId);
        v.put(COLUMN_CUSTOMER_NAME, customerName);
        v.put(COLUMN_PICKUP_POINT, pickupPoint);
        v.put(COLUMN_CUSTOMER_PHONE, phone);
        v.put(COLUMN_STATUS, "upcoming");
        return db.insert(TABLE_BOOKED_TICKETS, null, v);
    }

    public void cancelBooking(int bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, "cancelled");
        db.update(TABLE_BOOKED_TICKETS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(bookingId)});
    }

    public List<Trip> getBookedTrips(String statusFilter) {
        List<Trip> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String query = "SELECT t.*, b." + COLUMN_ID + " as booking_id, b." + COLUMN_STATUS + " FROM " + TABLE_TRIPS + " t " +
                      "JOIN " + TABLE_BOOKED_TICKETS + " b ON t." + COLUMN_ID + " = b." + COLUMN_TRIP_ID + " " +
                      "WHERE b." + COLUMN_STATUS + " = ? " +
                      "ORDER BY t." + COLUMN_DEP_DATE + " DESC";
        
        Cursor c = db.rawQuery(query, new String[]{statusFilter});
        if (c.moveToFirst()) {
            do {
                Trip trip = new Trip(
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_ID)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DEPARTURE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DESTINATION)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DEP_DATE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DEP_TIME)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_SEATS)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_BRAND)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_PRICE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_STATUS))
                );
                // Ta có thể dùng ID của booking thay vì trip ID nếu cần huỷ chính xác booking đó
                // Ở đây tạm dùng ID chuyến đi để hiển thị, nhưng lưu booking ID vào trip nếu cần
                list.add(trip);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    // Overload for backward compatibility if needed, or update call sites
    public List<Trip> getBookedTrips() {
        return getBookedTrips("upcoming");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int n) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMBOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKED_TICKETS);
        onCreate(db);
    }

    public List<Trip> searchTrips(String departure, String destination, String date) {
        List<Trip> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TRIPS + " WHERE " + COLUMN_DEPARTURE + " = ? AND " + COLUMN_DESTINATION + " = ? AND " + COLUMN_DEP_DATE + " = ?", new String[]{departure, destination, date});
        if (c.moveToFirst()) {
            do {
                list.add(new Trip(
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_ID)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DEPARTURE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DESTINATION)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DEP_DATE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DEP_TIME)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_SEATS)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_BRAND)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_PRICE))
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public List<Tour> getAllTours() {
        List<Tour> list = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_TOURS, null);
        if (c.moveToFirst()) {
            do {
                list.add(new Tour(
                    c.getString(c.getColumnIndexOrThrow(COLUMN_TITLE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_LOCATION)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DURATION)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_PRICE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_IMAGE_RES)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_IMAGE_URL)),
                    c.getFloat(c.getColumnIndexOrThrow(COLUMN_RATING)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_REVIEWS))
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public List<Hotel> getAllHotels() {
        List<Hotel> list = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_HOTELS, null);
        if (c.moveToFirst()) {
            do {
                list.add(new Hotel(
                    c.getString(c.getColumnIndexOrThrow(COLUMN_TITLE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_LOCATION)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_PRICE)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_IMAGE_RES)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_IMAGE_URL)),
                    c.getFloat(c.getColumnIndexOrThrow(COLUMN_RATING)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_REVIEWS))
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public List<Combo> getAllCombos() {
        List<Combo> list = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_COMBOS, null);
        if (c.moveToFirst()) {
            do {
                list.add(new Combo(
                    c.getString(c.getColumnIndexOrThrow(COLUMN_TITLE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_LOCATION)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_ORIGINAL_PRICE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_PRICE)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_IMAGE_RES)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_IMAGE_URL)),
                    c.getFloat(c.getColumnIndexOrThrow(COLUMN_RATING)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_BADGE))
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public List<TicketOffer> getAllTickets() {
        List<TicketOffer> list = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_TICKETS, null);
        if (c.moveToFirst()) {
            do {
                list.add(new TicketOffer(
                    c.getString(c.getColumnIndexOrThrow(COLUMN_TITLE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DATE_RANGE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_PRICE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_DISCOUNT)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_TYPE)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_IMAGE_RES)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_IMAGE_URL))
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public boolean registerUser(String email, String password, String fullname, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_FULLNAME, fullname);
        values.put(COLUMN_PHONE, phone);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public Cursor loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
