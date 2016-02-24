package cn.bmob.crash;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.diandi.klob.sdk.concurrent.SimpleExecutor;
import com.diandi.klob.sdk.concurrent.SimpleTask;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2014-11-29  .
 * *********    Time : 11:46 .
 * *********    Project name : Diandi1.18 .
 * *********    Version : 1.0
 * *********    Copyright @ 2014, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * 数据库升级
 */
public class BaseSqliteOpenHelper extends OrmLiteSqliteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static String TAG = "BaseSqliteOpenHelper";
    private static String DB_NAME = "diandi.db";
    public int newVersion = 4;
    public int oldVersion = 4;
    private Context mContext;

    public BaseSqliteOpenHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        mContext = context;
        //  createDataBase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, LocalCrash.class);
        } catch (SQLException e) {
            Log.e(TAG, "创建数据库失败", e);
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        oldVersion = oldVer;
        newVersion = newVer;
        try {
            TableUtils.dropTable(connectionSource, LocalCrash.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //do nothing - database already exist
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                SimpleExecutor.getInstance().execute(new CopyDatabaseTask());
            } catch
                    (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void copyDataBase() {
        //Open your local db as the input stream
        InputStream myInput = null;
        try {
            myInput = mContext.getAssets().open(DB_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Path to the just created empty db
        String outFileName = mContext.getDatabasePath(DB_NAME).getPath();
        //Open the empty db as the output stream
        OutputStream myOutput = null;
        try {
            myOutput = new FileOutputStream(outFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        if (myInput != null) {
            try {
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Close the streams
        try {
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = mContext.getDatabasePath(DB_NAME).getPath();
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //database does't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    public class CopyDatabaseTask extends SimpleTask {

        public CopyDatabaseTask() {
            super();
            Log.e(TAG, TAG);
        }

        @Override
        public void doInBackground() {
            copyDataBase();
        }

        @Override
        public void onFinish(boolean canceled) {

        }
    }


}
