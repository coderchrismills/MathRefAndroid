package com.happymaau.MathRef;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class EquationDatastore extends SQLiteOpenHelper {

    private static EquationDatastore instance = null;

    public static final String TABLE_EQUATIONS          = "Equations";
    public static final String COLUMN_ID                = "id";
    public static final String COLUMN_EQ_ID_DB          = "eqIdDB";
    public static final String COLUMN_EQ_ID             = "eqId";
    public static final String COLUMN_NUMBER            = "number";
    public static final String COLUMN_DELTA             = "delta";
    public static final String COLUMN_NAME              = "name";
    public static final String COLUMN_NOTE              = "note";
    public static final String COLUMN_SEARCH_TAGS       = "searchTags";
    public static final String COLUMN_WIKI_LINK         = "wikiLink";
    public static final String COLUMN_SECTION           = "section";
    public static final String COLUMN_SUB_SECTION       = "subSection";
    public static final String COLUMN_SUB_SECTION_PATH  = "subSectionPath";
    public static final String COLUMN_IS_FREE           = "isFree";
    public static final String COLUMN_EX_DELTA          = "exDelta";

    public static final int COLUMN_ID_INDEX               = 0;
    public static final int COLUMN_EQ_ID_DB_INDEX         = 1;
    public static final int COLUMN_EQ_ID_INDEX            = 2;
    public static final int COLUMN_NUMBER_INDEX           = 3;
    public static final int COLUMN_DELTA_INDEX            = 4;
    public static final int COLUMN_NAME_INDEX             = 5;
    public static final int COLUMN_NOTE_INDEX             = 6;
    public static final int COLUMN_SECTION_INDEX          = 9;
    public static final int COLUMN_SUB_SECTION_INDEX      = 10;
    public static final int COLUMN_SUB_SECTION_PATH_INDEX = 11;
    public static final int COLUMN_IS_FREE_INDEX          = 12;
    public static final int COLUMN_EX_DELTA_INDEX         = 13;
    
    private final Context mContext;
    private SQLiteDatabase myDataBase; 
    private static String DATABASE_PATH;
    private static final String DATABASE_NAME = "equations.sqlite";
    private static final int DATABASE_VERSION = 1;

    protected EquationDatastore(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        DATABASE_PATH =  mContext.getDatabasePath(DATABASE_NAME).getParent() + File.separator;
    }
   
    public static EquationDatastore getInstance(Context context) {
        if(instance == null) {
            instance = new EquationDatastore(context);
            try 
            {
            	instance.createDataBase();
            } 
            catch (IOException ioe) 
            {
            	throw new Error("Unable to create database");
            }
            try 
            {
            	instance.openDataBase();
            }
            catch(SQLException sqle)
            {
            	throw sqle;
            }
        }
        return instance;
    }
    
    public void createDataBase() throws IOException{
        boolean databaseExist = checkDataBase();
        if(databaseExist){
            this.getWritableDatabase();
        }else{
            this.getReadableDatabase();         
            copyDataBase(); 
        }
    } 
 
    public boolean checkDataBase() {
        File databaseFile = new File(DATABASE_PATH + DATABASE_NAME);
        return databaseFile.exists();        
    }
 
    private void copyDataBase() throws IOException{ 
        InputStream myInput = mContext.getAssets().open(DATABASE_NAME); 
        File outFile = mContext.getDatabasePath(DATABASE_NAME);
        String outFileName = outFile.getPath();
        OutputStream myOutput = new FileOutputStream(outFileName); 
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close(); 
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DATABASE_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
 
    @Override
    public synchronized void close() { 
        if(myDataBase != null)
        	myDataBase.close(); 
        super.close(); 
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}

