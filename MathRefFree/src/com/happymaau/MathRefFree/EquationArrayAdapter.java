package com.happymaau.MathRefFree;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EquationArrayAdapter extends ArrayAdapter<Equation> {
    Context mContext;
    List<Equation> mEquations;

    public EquationArrayAdapter(Context context,
                                int textViewResourceId,
                                String section,
                                String subsection)
    {
        super(context, textViewResourceId);
        mContext = context;
        EquationDatasource ds = new EquationDatasource(context);
        ds.open();
        this.mEquations = ds.getEquationsFor(section, subsection);
    }

    public EquationArrayAdapter(Context context,
                                int textViewResourceId,
                                String searchTerm)
    {
        super(context, textViewResourceId);
        mContext = context;
        EquationDatasource ds = new EquationDatasource(context);
        ds.open();
        this.mEquations = ds.getEquationsForSearchTerm(searchTerm);
    }

    public void updateWithSearchTerm(String searchTerm) {
        EquationDatasource ds = new EquationDatasource(mContext);
        ds.open();
        this.mEquations = ds.getEquationsForSearchTerm(searchTerm);
        notifyDataSetChanged();
    }
    
    public void updateWithWidth(float width) {
    	GlobalHelpers.tableWidth = width;
    	notifyDataSetChanged();
    }
    
    public int getCount()
    {
        return mEquations.size();
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null)
        {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_item_icon_text, null);

            holder = new ViewHolder();
            holder.text = (TextView)convertView.findViewById(R.id.text1);
            holder.icon = (ImageView)convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        Equation eq = mEquations.get(position);
        holder.text.setText(eq.name);
        holder.icon.setImageBitmap(eq.getEquationImageForType(Equation.TableViewType));
        holder.icon.setAdjustViewBounds(true);
        holder.icon.setMaxWidth((int)eq.eqImageWidth);

        return convertView;
    }
    
    static class ViewHolder
    {
        TextView text;
        ImageView icon;
    }
}
