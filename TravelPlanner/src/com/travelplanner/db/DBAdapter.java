/**
 * @author Shilpi Agarwal
 * @author Hema Kumar
 */
/** This file is part of TravelPlanner.

TravelPlanner is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

TravelPlanner is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Travelplanner. If not, see <http://www.gnu.org/licenses/>.
*/

package com.travelplanner.db;

	import android.content.ContentValues;
	import android.content.Context;
	import android.database.Cursor;
	import android.database.SQLException;
	import android.database.sqlite.SQLiteDatabase;
	import android.database.sqlite.SQLiteOpenHelper;
	import android.util.Log;
	public class DBAdapter   {
		
		 public static final String KEY_ROWID = "_id";
		    public static final String KEY_Name = "name";
		    public static final String KEY_Travel_Mode = "mode";
		    public static final String KEY_Travel_Type = "type";
		    public static final String KEY_Date = "date"; 
		    public static final String KEY_Time = "time"; 
		    public static final String KEY_From = "fromCity"; 
		    public static final String KEY_To = "toCity"; 
		    
		    public static final String KEY_TRAVEL_ID = "travelid";
		    public static final String KEY_PLAN_NAME = "planname"; 
		    public static final String KEY_PLAN_Status = "status"; 
		    
		    private static final String TAG = "DBAdapter";
		    
		    private static final String DATABASE_NAME = "TravelCheckListDB.db";
		    private static final String DATABASE_TABLE_TRAVEL_PLANS = "travelplan";
		    private static final String DATABASE_TABLE_CHECKLIST = "travelchecklist";
		    private static final int DATABASE_VERSION = 1;

		    private static final String DATABASE_CREATE_TRAVEL_PLANS =
		        "create table travelplan (_id integer primary key autoincrement, "
		        + "name text , mode text ,type text, date text ,time text , fromCity text, " 
		        + "toCity text );";
		    
		    private static final String DATABASE_CREATE_TRAVEL_CHECKLIST =
		        "create table travelchecklist (_id integer primary key autoincrement,travelid integer references travelplan(KEY_ROWID), "
		        + "planname text not null, " 
		        + "status integer not null);";
		        
		   		   
		    private SQLiteDatabase db;
		    private Context context;


		    public DBAdapter(Context ctx) 
		    {
		    	this.context = ctx;
		    	DatabaseHelper dbHelper = new DatabaseHelper(this.context);
		        this.db = dbHelper.getWritableDatabase();

		        
		    }
		   		     
		    private static class DatabaseHelper extends SQLiteOpenHelper 
		    {
		        DatabaseHelper(Context context) 
		        {
		            super(context, DATABASE_NAME, null, DATABASE_VERSION);
		        }

		        @Override
		        public void onCreate(SQLiteDatabase db) 
		        {
		            db.execSQL(DATABASE_CREATE_TRAVEL_PLANS);
		            db.execSQL(DATABASE_CREATE_TRAVEL_CHECKLIST);
		        }

		        @Override
		        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
		                              int newVersion) 
		        {
		            Log.w(TAG, "Upgrading database from version " + oldVersion 
		                  + " to "
		                  + newVersion + ", which will destroy all old data");
		            db.execSQL("DROP TABLE IF EXISTS titles");
		            onCreate(db);
		        }
		    }    
		   
		    

		    //---closes the database---    
		    public void close() 
		    {
		    	db.close();
		    }
		    
		    
		    //---insert a title into the database---
		    public long insertTravelPlan(String name, String mode, String type,String date,String time,String from,String to) 
		    {
		        ContentValues initialValues = new ContentValues();
		        initialValues.put(KEY_Name, name);
		        initialValues.put(KEY_Travel_Mode, mode);
		        initialValues.put(KEY_Travel_Type, type);
		        
		        initialValues.put(KEY_Date, date);
		        initialValues.put(KEY_Time, time);
		        initialValues.put(KEY_From, from);
		        initialValues.put(KEY_To, to);
		        return db.insert(DATABASE_TABLE_TRAVEL_PLANS, null, initialValues);
		    }

		    //---deletes a particular title---
		    public boolean deleteTravelPlan(long rowId) 
		    {
		        return db.delete(DATABASE_TABLE_TRAVEL_PLANS, KEY_ROWID + "=" + rowId, null) > 0;
		    }

		    //---retrieves all the titles---
		    public Cursor getAllTravelPlan() 
		    {
		        return db.query(DATABASE_TABLE_TRAVEL_PLANS, new String[] {
		        		KEY_ROWID, 
		        		KEY_Name,
		        		KEY_Travel_Mode,
		        		KEY_Travel_Type,
		        		KEY_Date,
		        		KEY_Time,
		        		KEY_From,
		        		KEY_To			}, 
		                null, 
		                null, 
		                null, 
		                null, 
		                null);
		    }

		    //---retrieves a particular title---
		    public Cursor getTravelPlan(long rowId) throws SQLException 
		    {
		        Cursor mCursor =
		                db.query(true, DATABASE_TABLE_TRAVEL_PLANS, new String[] {
		                		KEY_ROWID,
		                		KEY_Name,
		    	        		KEY_Travel_Mode,
		    	        		KEY_Travel_Type,
		    	        		KEY_Date,
		    	        		KEY_Time,
		    	        		KEY_From,
		    	        		KEY_To	
		                		}, 
		                		KEY_ROWID + "=" + rowId, 
		                		null,
		                		null, 
		                		null, 
		                		null, 
		                		null);
		        
		        
		        return mCursor;
		    }

		    //---updates a title---
		    public boolean updateTravelPlan(long rowId, String name, String mode, String type,String date,String time,String from,String to) 
		    {
		        ContentValues args = new ContentValues();
		        args.put(KEY_Name, name);
		        args.put(KEY_Travel_Mode, mode);
		        args.put(KEY_Travel_Type, type);
		        
		        args.put(KEY_Date, date);
		        args.put(KEY_Time, time);
		        args.put(KEY_From, from);
		        args.put(KEY_To, to);
		        return db.update(DATABASE_TABLE_TRAVEL_PLANS, args, 
		                         KEY_ROWID + "=" + rowId, null) > 0;
		    }
		    
		    
			
		    public long insertCheckListItem(long travelid,String planName, int status) 
		    {
		        ContentValues initialValues = new ContentValues();
		        initialValues.put(KEY_TRAVEL_ID, travelid);
		        initialValues.put(KEY_PLAN_NAME, planName);
		        
		        initialValues.put(KEY_PLAN_Status, status);
		        
		        return db.insert(DATABASE_TABLE_CHECKLIST, null, initialValues);
		    }

		    //---deletes a particular title---
		    public boolean delete(long id) 
		    {
		        return db.delete(DATABASE_TABLE_CHECKLIST, KEY_ROWID + "=" + id, null) > 0;
		    }

		    //---retrieves all the titles---
		    public Cursor getAllCheckListItem(long travelId) 
		    {
		    	Cursor mCursor = db.query(DATABASE_TABLE_CHECKLIST, new String[] {
		    			KEY_ROWID,
		    			KEY_PLAN_NAME,
		        		KEY_PLAN_Status
		        		},
		        		KEY_TRAVEL_ID + "=" + travelId, 
		                null, 
		                null, 
		                null, 
		                null, 
		                null);
		        if (mCursor != null) {
		            mCursor.moveToFirst();
		        }
		        return mCursor;
		    }

		    

		    //---updates a title---
		    public boolean updatePlan(long id,String planName, int status) 
		    {
		        ContentValues args = new ContentValues();
		        args.put(KEY_PLAN_NAME, planName);
		        args.put(KEY_PLAN_Status, status);
		        
		        return db.update(DATABASE_TABLE_CHECKLIST, args, 
		                         KEY_ROWID + "=" + id, null) > 0;
		    }
		    
		  //---updates a title---
		    public boolean updateStatus(long id, int status) 
		    {
		        ContentValues args = new ContentValues();
		        args.put(KEY_PLAN_Status, status);
		        return db.update(DATABASE_TABLE_CHECKLIST, args, 
		                         KEY_ROWID + "=" + id, null) > 0;
		    }
		}
