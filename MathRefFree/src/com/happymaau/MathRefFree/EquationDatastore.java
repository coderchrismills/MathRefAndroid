package com.happymaau.MathRefFree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.DatabaseUtils.InsertHelper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;

public class EquationDatastore extends SQLiteOpenHelper {

    private static EquationDatastore instance = null;

    public static final String TABLE_EQUATIONS          = "equations";
    public static final String COLUMN_ID                = "_id";
    public static final String COLUMN_ID_XML            = "id";
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

    public static final int COLUMN_ID_INDEX                 = 0;
    public static final int COLUMN_EQ_ID_DB_INDEX           = 1;
    public static final int COLUMN_EQ_ID_INDEX              = 2;
    public static final int COLUMN_NUMBER_INDEX             = 3;
    public static final int COLUMN_DELTA_INDEX              = 4;
    public static final int COLUMN_NAME_INDEX               = 5;
    public static final int COLUMN_NOTE_INDEX               = 6;
    public static final int COLUMN_SEARCH_TAGS_INDEX        = 7;
    public static final int COLUMN_WIKI_LINK_INDEX          = 8;
    public static final int COLUMN_SECTION_INDEX            = 9;
    public static final int COLUMN_SUB_SECTION_INDEX        = 10;
    public static final int COLUMN_SUB_SECTION_PATH_INDEX   = 11;
    public static final int COLUMN_IS_FREE_INDEX            = 12;
    public static final int COLUMN_EX_DELTA_INDEX           = 13;

    private static final String DATABASE_NAME = "equations.db";
    private static final int DATABASE_VERSION = 1;
    private static final int DATABASE_SIZE = 1342;
    private static final int DATABASE_COLUMN_SIZE = 14;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_EQUATIONS + "(" +
            COLUMN_ID + " integer primary key, " +
            COLUMN_EQ_ID_DB + " integer, " +
            COLUMN_EQ_ID + " integer, " +
            COLUMN_NUMBER + " integer, " +
            COLUMN_DELTA + " real, " +
            COLUMN_NAME + " text, " +
            COLUMN_NOTE + " text, " +
            COLUMN_SEARCH_TAGS + " text, " +
            COLUMN_WIKI_LINK + " text, " +
            COLUMN_SECTION + " text, " +
            COLUMN_SUB_SECTION + " text, " +
            COLUMN_SUB_SECTION_PATH + " text, " +
            COLUMN_IS_FREE + " integer, " +
            COLUMN_EX_DELTA + " real);";

    private Context mContext = null;

    protected EquationDatastore(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(EquationDatastore.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUATIONS);
        onCreate(db);
    }

    public static EquationDatastore getInstance(Context context) {
        if(instance == null) {
            instance = new EquationDatastore(context);
            instance.makeDB();
        }
        return instance;
    }

    private void makeDB() {
        SQLiteDatabase database = getWritableDatabase();
        try {
            String sql = "SELECT COUNT(*) FROM " + EquationDatastore.TABLE_EQUATIONS;
            SQLiteStatement statement = database.compileStatement(sql);
            long count = statement.simpleQueryForLong();
            if(count >= DATABASE_SIZE)
                return;

            XmlPullParser xpp = mContext.getResources().getXml(R.xml.equations);
            database.setLockingEnabled(false);
            database.beginTransaction();
            ContentValues values = new ContentValues();
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT)
            {
                if (xpp.getEventType() == XmlPullParser.END_TAG)
                {
                    String n = xpp.getName();
                    if(n.equalsIgnoreCase("row")) {
                        database.insertWithOnConflict(TABLE_EQUATIONS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                        values.clear();
                    }
                }
                else if (xpp.getEventType() == XmlPullParser.START_TAG)
                {
                    String n = xpp.getName();
                    if(n.equalsIgnoreCase("row") || n.equalsIgnoreCase("rows"))
                    {
                        xpp.next();
                        continue;
                    }

                    String tag_attrib = xpp.getAttributeValue(0);
                    if(tag_attrib.equalsIgnoreCase(COLUMN_ID_XML)) {
                        xpp.next();
                        String tag_val = xpp.getText();
                        values.put(COLUMN_ID, Integer.parseInt(tag_val));
                    }
                    else if(tag_attrib.equalsIgnoreCase(COLUMN_EQ_ID_DB)) {
                        xpp.next();
                        String tag_val = xpp.getText();
                        values.put(COLUMN_EQ_ID_DB, Integer.parseInt(tag_val));
                    }
                    else if(tag_attrib.equalsIgnoreCase(COLUMN_EQ_ID)) {
                        xpp.next();
                        String tag_val = xpp.getText();
                        values.put(COLUMN_EQ_ID, Integer.parseInt(tag_val));
                    }
                    else if(tag_attrib.equalsIgnoreCase(COLUMN_NUMBER)) {
                        xpp.next();
                        String tag_val = xpp.getText();
                        values.put(COLUMN_NUMBER, Integer.parseInt(tag_val));
                    }
                    else if(tag_attrib.equalsIgnoreCase(COLUMN_DELTA)) {
                        xpp.next();
                        String tag_val = (xpp.getText() == null) ? "0.0" : xpp.getText();
                        values.put(COLUMN_DELTA, Float.parseFloat(tag_val));
                    }
                    else if(tag_attrib.equalsIgnoreCase(COLUMN_NAME)) {
                        xpp.next();
                        String tag_val = (xpp.getText() == null) ? "" : xpp.getText();
                        String name = tag_val;
                        name = name.equalsIgnoreCase("null") ? "" : name;
                        values.put(COLUMN_NAME, name);
                    }
                    else if(tag_attrib.equalsIgnoreCase(COLUMN_NOTE)) {
                        xpp.next();
                        String tag_val = (xpp.getText() == null) ? "" : xpp.getText();
                        String note = tag_val;
                        note = note.equalsIgnoreCase("null") ? "" : note;
                        values.put(COLUMN_NOTE, note);
                    }
                    else if(tag_attrib.equalsIgnoreCase(COLUMN_SEARCH_TAGS)) {
                        xpp.next();
                        String tag_val = (xpp.getText() == null) ? "" : xpp.getText();
                        values.put(COLUMN_SEARCH_TAGS, tag_val);
                    }
                    else if(tag_attrib.equalsIgnoreCase(COLUMN_WIKI_LINK)) {
                        xpp.next();
                        String tag_val = (xpp.getText() == null) ? "" : xpp.getText();
                        values.put(COLUMN_WIKI_LINK, tag_val);
                    }
                    else if(tag_attrib.equalsIgnoreCase(COLUMN_SECTION)) {
                        xpp.next();
                        String tag_val = (xpp.getText() == null) ? "" : xpp.getText();
                        values.put(COLUMN_SECTION, tag_val);
                    }
                    else if(tag_attrib.equalsIgnoreCase(COLUMN_SUB_SECTION)) {
                        xpp.next();
                        String tag_val = (xpp.getText() == null) ? "" : xpp.getText();
                        values.put(COLUMN_SUB_SECTION, tag_val);
                    }
                    else if(tag_attrib.equalsIgnoreCase(COLUMN_SUB_SECTION_PATH)) {
                        xpp.next();
                        String tag_val = (xpp.getText() == null) ? "" : xpp.getText();
                        values.put(COLUMN_SUB_SECTION_PATH, tag_val);
                    }
                    else if(tag_attrib.equalsIgnoreCase(COLUMN_IS_FREE)) {
                        xpp.next();
                        String tag_val = (xpp.getText() == null) ? "" : xpp.getText();
                        values.put(COLUMN_IS_FREE, "1".equalsIgnoreCase(tag_val));
                    }
                    else if(tag_attrib.equalsIgnoreCase(COLUMN_EX_DELTA)) {
                        xpp.next();
                        String tag_val = (xpp.getText() == null) ? "0.0" : xpp.getText();
                        values.put(COLUMN_EX_DELTA, Float.parseFloat(tag_val));
                    }
                }
                xpp.next();
            }

            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (Throwable t) {
            Log.e("DB Error", t.getMessage());
        }
        finally {
            database.setLockingEnabled(true);
        }
    }

    private boolean checkRecordExist(SQLiteDatabase db, String tableName, String key, int id) {

        long startTime = System.nanoTime();

        String query ="SELECT DISTINCT "+ key +
                " FROM " + tableName +
                " WHERE " + key +
                " = '" + id + "'";

        Cursor cursor = db.rawQuery(query, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();

        long estimatedTime = System.nanoTime() - startTime;
        int est_time = (int)((double)estimatedTime / 1000000.0);

        return exists;
    }
}

