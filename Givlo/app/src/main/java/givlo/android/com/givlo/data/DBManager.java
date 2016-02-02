package givlo.android.com.givlo.data;

import java.io.IOException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import givlo.android.com.givlo.beans.NGODataBean;


public class DBManager {
    Context context;
    SQLiteDatabase readableDatabase;
    DBHelper myDbHelper;

    public interface Tables {
        String TBL_NGO_LIST = "ngo_list";
    }

    public interface NGOLISTColumns {
        String ROW_ID = "rowid";
        String NAME = "name";
        String ADDRESS = "address";
        String LATITUDE = "latitude";
        String LONGITUDE = "Longitude";
        String PHONE = "phone";
        String CATEGORY = "category";
        String EMAIL = "email";
        String WEBSITE = "website";

    }

    private void manageDatabase() {
        myDbHelper = new DBHelper(context);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    private void openDatabase() {
        readableDatabase = new DBHelper(context).getReadableDatabase();
    }

    private void closeDatabase() {
        readableDatabase.close();
    }

    public DBManager(Context context) {
        super();
        this.context = context;
    }

    public ArrayList<NGODataBean> getLatitudes() {
        manageDatabase();
        openDatabase();
        Cursor cursor = readableDatabase.query(Tables.TBL_NGO_LIST,
                new String[]{NGOLISTColumns.ROW_ID, NGOLISTColumns.NAME, NGOLISTColumns.ADDRESS, NGOLISTColumns.LATITUDE, NGOLISTColumns.LONGITUDE, NGOLISTColumns.PHONE, NGOLISTColumns.EMAIL, NGOLISTColumns.CATEGORY, NGOLISTColumns.WEBSITE},
                null, null, null, null, null);
        ArrayList<NGODataBean> all = new ArrayList<NGODataBean>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NGODataBean bean = new NGODataBean();
            bean.setNgoId(cursor.getLong(0));
            bean.setNgoName(cursor.getString(1));
            bean.setNgoAddress(cursor.getString(2));
            bean.setNgoLatitude(cursor.getString(3));
            bean.setNgoLongitude(cursor.getString(4));
            bean.setNgoNumber(cursor.getString(5));
            bean.setNgoEmail(cursor.getString(6));
            bean.setNgoCategory(cursor.getString(7));
            bean.setmNgoWebsite(cursor.getString(8));
            all.add(bean);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        myDbHelper.close();
        return all;
    }
}