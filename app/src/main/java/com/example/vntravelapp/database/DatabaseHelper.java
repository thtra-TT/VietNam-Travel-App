package com.example.vntravelapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.vntravelapp.R;
import com.example.vntravelapp.models.Combo;
import com.example.vntravelapp.models.Hotel;
import com.example.vntravelapp.models.Tour;
import com.example.vntravelapp.models.TicketOffer;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "vntravel.db";
    private static final int DATABASE_VERSION = 16; // Tăng version để cập nhật bảng users

    private static final String TABLE_TOURS = "tours";
    private static final String TABLE_HOTELS = "hotels";
    private static final String TABLE_COMBOS = "combos";
    private static final String TABLE_TICKETS = "tickets";
    private static final String TABLE_USERS = "users";

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
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_FULLNAME = "fullname";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_USER_IMAGE = "user_image";

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
        seedData(db);
    }

    private void seedData(SQLiteDatabase db) {
        insertTour(db, "Vịnh Hạ Long", "Quảng Ninh", "2N1Đ", "2.990.000đ", "Mô tả Hạ Long", 0, "https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg", 4.8f, 150);
        insertTour(db, "Phố Cổ Hội An", "Quảng Nam", "3N2Đ", "3.500.000đ", "Mô tả Hội An", 0, "https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg", 4.9f, 200);
        insertTour(db, "Chinh phục Fansipan", "Lào Cai", "3N2Đ", "4.200.000đ", "Trải nghiệm cáp treo và chạm tay vào nóc nhà Đông Dương tại Sa Pa.", 0, "https://res.cloudinary.com/dzjlcbwwh/image/upload/v1773626520/1a3903f1-7c6a-4458-9fc6-519589789751.png", 4.7f, 85);
        insertTour(db, "Cố Đô Huế", "Thừa Thiên Huế", "2N1Đ", "1.500.000đ", "Tìm về vẻ đẹp trầm mặc của Đại Nội và các lăng tẩm triều Nguyễn.", 0, "https://res.cloudinary.com/dzjlcbwwh/image/upload/v1773626555/2f7a9346-9a04-4efc-b96a-73652ed4c945.png", 4.6f, 120);
        insertTour(db, "Thành phố Ngàn Hoa", "Đà Lạt", "3N2Đ", "2.800.000đ", "Tận hưởng không khí se lạnh và check-in các vườn hoa đẹp mê hồn.", 0, "https://res.cloudinary.com/dzjlcbwwh/image/upload/v1773626599/9e94999d-423a-4d23-a187-41317434956c.png", 4.8f, 310);
        insertTour(db, "Miền Tây Sông Nước", "Cần Thơ", "2N1Đ", "1.200.000đ", "Trải nghiệm Chợ nổi Cái Răng và vườn trái cây trĩu quả.", 0, "https://res.cloudinary.com/dzjlcbwwh/image/upload/v1773626660/54cda170-8e46-4faf-b0c4-1a2af78f777e.png", 4.5f, 95);

        insertHotel(db, "Vinpearl Phú Quốc", "Phú Quốc", "Mô tả Vinpearl", "2.500.000đ", 0, "https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg", 4.7f, 300);
        insertHotel(db, "InterContinental Đà Nẵng", "Đà Nẵng", "Tọa lạc tại Bán đảo Sơn Trà, thiết kế bởi kiến trúc sư lừng danh Bill Bensley.", "8.500.000đ", 0, "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb", 4.9f, 120);
        insertHotel(db, "Hotel de la Coupole", "Sa Pa", "Sự kết hợp hoàn hảo giữa thời trang Pháp và văn hóa dân tộc thiểu số Sa Pa.", "3.200.000đ", 0, "https://res.cloudinary.com/dzjlcbwwh/image/upload/v1773591886/8c59f89a-64c9-45e0-8b54-7e513f8b25b1.png", 4.8f, 450);
        insertHotel(db, "Caravelle Saigon", "TP. Hồ Chí Minh", "Khách sạn biểu tượng lịch sử ngay trung tâm thành phố với view nhìn ra Nhà hát lớn.", "4.100.000đ", 0, "https://res.cloudinary.com/dzjlcbwwh/image/upload/v1773626268/4ae52bfa-a1b2-42b6-9b70-df0f458f479b.png", 4.6f, 890);
        insertHotel(db, "Amanoi Resort", "Ninh Thuận", "Khu nghỉ dưỡng 6 sao ẩn mình trong Vườn quốc gia Núi Chúa, yên bình và riêng tư.", "25.000.000đ", 0, "https://res.cloudinary.com/dzjlcbwwh/image/upload/v1773626323/a2a6d3b5-01de-4fba-924d-c3f152302981.png", 5.0f, 50);
        insertHotel(db, "Six Senses Ninh Van Bay", "Nha Trang", "Tận hưởng không gian thiên nhiên hoang sơ với các villa nằm sát mép biển.", "12.000.000đ", 0, "https://res.cloudinary.com/dzjlcbwwh/image/upload/v1773626387/fc826700-1d72-4f78-b7bc-f54f9293a31d.png", 4.9f, 210);


        insertCombo(db, "Siêu Combo Đà Nẵng", "Đà Nẵng", "Vé máy bay khứ hồi + Khách sạn 4 sao + Ăn sáng buffet.", "5.000.000đ", "3.990.000đ", 0, "https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg", 4.8f, "HOT");
        insertCombo(db, "Combo Kỳ Nghỉ Phú Quốc", "Kiên Giang", "Vé máy bay + VinOasis 3N2Đ + Vé VinWonders & Safari.", "7.500.000đ", "5.850.000đ", 0, "https://res.cloudinary.com/dzjlcbwwh/image/upload/v1773626789/834c6022-0daa-4f57-82d4-c1907850ea7e.png", 4.9f, "BEST SELLER");
        insertCombo(db, "Combo Sapa Mờ Sương", "Lào Cai", "Xe giường nằm InterBus + Khách sạn view núi + Ăn sáng.", "2.200.000đ", "1.650.000đ", 0, "https://res.cloudinary.com/dzjlcbwwh/image/upload/v1773626828/9dd0a628-4ddf-44bb-a760-87b8f78f8b67.png", 4.7f, "GIÁ TỐT");
        insertCombo(db, "Combo Quy Nhơn Biển Nhớ", "Bình Định", "Vé máy bay + FLC Quy Nhơn 3N2Đ + Đưa đón sân bay.", "6.200.000đ", "4.990.000đ", 0, "https://res.cloudinary.com/dzjlcbwwh/image/upload/v1773626858/e8527db1-1289-49bb-b720-89660d3a6bd8.png", 4.6f, "NEW");
        insertCombo(db, "Combo Nha Trang Hè Rực Rỡ", "Khánh Hòa", "Khách sạn mặt biển + Tour 4 đảo + Tiệc hải sản.", "4.500.000đ", "3.200.000đ", 0, "https://res.cloudinary.com/dzjlcbwwh/image/upload/v1773626895/0ab8e4e8-af86-40ce-80fe-5113f04f1693.png", 4.8f, "GIẢM 30%");
    }

    private void insertTour(SQLiteDatabase db, String t, String l, String d, String p, String desc, int r, String u, float rat, int rev) {
        ContentValues v = new ContentValues();
        v.put(COLUMN_TITLE, t); v.put(COLUMN_LOCATION, l); v.put(COLUMN_DURATION, d); v.put(COLUMN_PRICE, p); v.put(COLUMN_DESCRIPTION, desc); v.put(COLUMN_IMAGE_RES, r); v.put(COLUMN_IMAGE_URL, u); v.put(COLUMN_RATING, rat); v.put(COLUMN_REVIEWS, rev);
        db.insert(TABLE_TOURS, null, v);
    }
    private void insertHotel(SQLiteDatabase db, String t, String l, String d, String p, int r, String u, float rat, int rev) {
        ContentValues v = new ContentValues();
        v.put(COLUMN_TITLE, t); v.put(COLUMN_LOCATION, l); v.put(COLUMN_DESCRIPTION, d); v.put(COLUMN_PRICE, p); v.put(COLUMN_IMAGE_RES, r); v.put(COLUMN_IMAGE_URL, u); v.put(COLUMN_RATING, rat); v.put(COLUMN_REVIEWS, rev);
        db.insert(TABLE_HOTELS, null, v);
    }
    private void insertCombo(SQLiteDatabase db, String t, String l, String d, String o, String p, int r, String u, float rat, String b) {
        ContentValues v = new ContentValues();
        v.put(COLUMN_TITLE, t); v.put(COLUMN_LOCATION, l); v.put(COLUMN_DESCRIPTION, d); v.put(COLUMN_ORIGINAL_PRICE, o); v.put(COLUMN_PRICE, p); v.put(COLUMN_IMAGE_RES, r); v.put(COLUMN_IMAGE_URL, u); v.put(COLUMN_RATING, rat); v.put(COLUMN_BADGE, b);
        db.insert(TABLE_COMBOS, null, v);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int n) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMBOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Auth methods
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
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
}
