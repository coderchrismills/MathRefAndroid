package com.happymaau.MathRefFree;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Equation implements Parcelable {

    public static final int TableViewType   = 0;
    public static final int FullScreenType  = 1;

    public int      id              = 0;
    public int      eqId            = 0;
    public int      eqIdDB          = 0;
    public int      number          = 0;
    public boolean  isFree          = true;
    public String   name            = "";
    public String   section         = "";
    public String   subSection      = "";
    public String   subSectionPath  = "";
    public String   note            = "";
    public float    delta           = 1.f;
    public float    exDelta         = 1.f;
    public float    eqImageWidth    = 0.f;
    public float    exImageWidth    = 0.f;

    public Equation() { }

    public String getPathToEquation() {
        return subSectionPath + "/EQ_" + number + ".png";
    }

    public String getPathToExample() {
        return subSectionPath + "/EX_" + number + ".png";
    }

    public Bitmap getEquationImageForType(int type) {
        try
        {
            InputStream is = null;
            try
            {
                is = GlobalHelpers.mContext.getResources().getAssets().open(getPathToEquation());
            }
            catch (IOException e1)
            {
                is = null;
            }

            Bitmap bmp = null;
            int width = (int)(GlobalHelpers.width - 0.05*GlobalHelpers.width);
            if(type == Equation.TableViewType) {
                width = (int)(GlobalHelpers.tableWidth - 0.05*GlobalHelpers.tableWidth);
            }

            try 
            { 
            	bmp = decodeFile(is, width); 
            }
            catch (FileNotFoundException e) 
            { 
            	bmp = null; 
            }

            if(bmp != null)
            {
                float w = (float)width;
                eqImageWidth = (delta * w);
            }
            return bmp;
        }
        catch (Exception e)
        {
            Log.d("Image Loading", e.getMessage());
        }
        return null;
    }

    public Bitmap getExampleImageForType(int type) {
        try
        {
            InputStream is = null;
            try
            {
                is = GlobalHelpers.mContext.getResources().getAssets().open(getPathToExample());
            }
            catch (IOException e1)
            {
                is = null;
            }

            Bitmap bmp = null;
            int width = (int)(GlobalHelpers.width - 0.05*GlobalHelpers.width);
            try 
            {
            	bmp = decodeFile(is, width); 
            }
            catch (FileNotFoundException e) 
            { 
            	bmp = null; 
            }

            if(bmp != null)
            {
                float w = (float)width;
                exImageWidth = (exDelta * w);
            }
            return bmp;
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        return null;
    }

    private static Bitmap decodeFile(InputStream is, int target_width) throws FileNotFoundException
    {
        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is,null,o);
        
        try {
			is.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //The new size we want to scale to
        int REQUIRED_SIZE=target_width;

        //Find the correct scale value. It should be the power of 2.
        int width_tmp=o.outWidth;
        int scale=1;
        while(o.outWidth / scale / 2>=REQUIRED_SIZE)
            scale*=2;

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize=scale;
        return BitmapFactory.decodeStream(is, null, o2);
    }

    public static Bitmap decodeSampledBitmapFromStream(InputStream is, int reqWidth) throws FileNotFoundException {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
        // Raw height and width of image
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (width > reqWidth) {
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }
        return inSampleSize;
    }

    // PARCEL
    public Equation(Parcel in) {
        readFromParcel(in);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Equation createFromParcel(Parcel in) {
            return new Equation(in);
        }

        public Equation[] newArray(int size) {
            return new Equation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(eqId);
        dest.writeInt(eqIdDB);
        dest.writeInt(number);
        dest.writeByte((byte)(isFree ? 1 : 0));
        dest.writeString(name);
        dest.writeString(section);
        dest.writeString(subSection);
        dest.writeString(subSectionPath);
        dest.writeString(note);
        dest.writeFloat(delta);
        dest.writeFloat(exDelta);
        dest.writeFloat(eqImageWidth);
        dest.writeFloat(exImageWidth);
    }

    public void readFromParcel(Parcel in) {
        id              = in.readInt();
        eqId            = in.readInt();
        eqIdDB          = in.readInt();
        number          = in.readInt();
        isFree          = in.readByte() == 1;
        name            = in.readString();
        section         = in.readString();
        subSection      = in.readString();
        subSectionPath  = in.readString();
        note            = in.readString();
        delta           = in.readFloat();
        exDelta         = in.readFloat();
        eqImageWidth    = in.readFloat();
        exImageWidth    = in.readFloat();
    }

}

