package com.happymaau.MathRef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;

public class EquationDatasource {

    // Database fields
    private SQLiteDatabase database;
    private EquationDatastore dbHelper;
    private Context mContext;

    private String[] allColumns = {
        EquationDatastore.COLUMN_ID,
        EquationDatastore.COLUMN_EQ_ID_DB,
        EquationDatastore.COLUMN_EQ_ID,
        EquationDatastore.COLUMN_NUMBER,
        EquationDatastore.COLUMN_DELTA,
        EquationDatastore.COLUMN_NAME,
        EquationDatastore.COLUMN_NOTE,
        EquationDatastore.COLUMN_SEARCH_TAGS,
        EquationDatastore.COLUMN_WIKI_LINK,
        EquationDatastore.COLUMN_SECTION,
        EquationDatastore.COLUMN_SUB_SECTION,
        EquationDatastore.COLUMN_SUB_SECTION_PATH,
        EquationDatastore.COLUMN_IS_FREE,
        EquationDatastore.COLUMN_EX_DELTA
    };

    public EquationDatasource() {
        this(GlobalHelpers.mContext);
    }

    public EquationDatasource(Context context) {
        mContext = context;
        dbHelper = EquationDatastore.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Equation getEquationForID(int id) {
        String query = "SELECT * FROM " + EquationDatastore.TABLE_EQUATIONS +
                " WHERE " + EquationDatastore.COLUMN_EQ_ID_DB + " = " + id + "";
        Log.d("Query", query);
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor != null) {
            return cursorToEquation(cursor);
        }
        return null;
    }

    public List<String> getSubSectionNamesFor(String section) {
        List<String> names = new ArrayList<String>();
        // select distinct subSection from equations where section='Algebra'
        String query ="SELECT DISTINCT "+ EquationDatastore.COLUMN_SUB_SECTION +
                        " FROM " + EquationDatastore.TABLE_EQUATIONS +
                        " WHERE " + EquationDatastore.COLUMN_SECTION +
                        " = '" + section + "'";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            names.add(cursor.getString(0));
            cursor.moveToNext();
        }

        cursor.close();

        return names;
    }

    public List<Equation> getEquationsFor(String section, String subsection) {
        List<Equation> equations = new ArrayList<Equation>();

        String query ="SELECT * FROM " + EquationDatastore.TABLE_EQUATIONS +
                " WHERE " + EquationDatastore.COLUMN_SECTION + " = '" + section + "'" +
                " AND " + EquationDatastore.COLUMN_SUB_SECTION + " = '" + subsection + "'";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Equation equation = cursorToEquation(cursor);
            equations.add(equation);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();

        Collections.sort(equations, new Comparator<Equation>() {
            @Override
            public int compare(Equation equation, Equation equation2) {
                if(equation.eqId == equation2.eqId)
                    return 0;
                return (equation.eqId < equation2.eqId) ? -1 : 1;
            }
        });

        return equations;
    }

    public List<Equation> getEquationsForSearchTerm(String searchTerm) {
        List<Equation> equations = new ArrayList<Equation>();

        String query ="SELECT * FROM " + EquationDatastore.TABLE_EQUATIONS +
                " WHERE " + EquationDatastore.COLUMN_NAME + " LIKE '%" + searchTerm + "%'" +
                " OR " + EquationDatastore.COLUMN_NOTE + " LIKE '%" + searchTerm + "%'";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Equation equation = cursorToEquation(cursor);
            equations.add(equation);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();

        return equations;
    }

    private Equation cursorToEquation(Cursor cursor) {
        Equation equation = new Equation();

        equation.id             = (cursor.getInt(EquationDatastore.COLUMN_ID_INDEX));
        equation.eqId           = (cursor.getInt(EquationDatastore.COLUMN_EQ_ID_INDEX));
        equation.eqIdDB         = (cursor.getInt(EquationDatastore.COLUMN_EQ_ID_DB_INDEX));
        equation.number         = (cursor.getInt(EquationDatastore.COLUMN_NUMBER_INDEX));
        equation.isFree         = (cursor.getInt(EquationDatastore.COLUMN_IS_FREE_INDEX)) == 1;
        equation.name           = (cursor.getString(EquationDatastore.COLUMN_NAME_INDEX));
        equation.note           = (cursor.getString(EquationDatastore.COLUMN_NOTE_INDEX));
        equation.section        = (cursor.getString(EquationDatastore.COLUMN_SECTION_INDEX));
        equation.subSection     = (cursor.getString(EquationDatastore.COLUMN_SUB_SECTION_INDEX));
        equation.subSectionPath = (cursor.getString(EquationDatastore.COLUMN_SUB_SECTION_PATH_INDEX));
        equation.delta          = (cursor.getFloat(EquationDatastore.COLUMN_DELTA_INDEX));
        equation.exDelta        = (cursor.getFloat(EquationDatastore.COLUMN_EX_DELTA_INDEX));

        return equation;
    }

}