package givlo.android.com.givlo.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.StringTokenizer;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private static final String DB_PATH = "/data/data/givlo.android.com.givlo/databases/";
    private static final String DATABASE_NAME = "givlo.sqlite";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase myDatabase;
    private final Context ctx;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // addDatabase(db);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DATABASE_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (myDatabase != null)
            myDatabase.close();
        super.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            copyDataBase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = ctx.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void addDatabase(SQLiteDatabase db) {
        try {
            Scanner scanner = new Scanner(ctx.getAssets().open("givlo.sql"));
            String sqlStatements = "";
            while (scanner.hasNext())
                sqlStatements += scanner.nextLine();
            scanner.close();
            StringTokenizer stringTokenizer = new StringTokenizer(
                    sqlStatements, ";", false);
            while (stringTokenizer.hasMoreTokens()) {
                try {
                    db.execSQL(stringTokenizer.nextToken());
                } catch (SQLException e) {
                    Log.d(TAG, e.getMessage());
                }
            }
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
    }
}