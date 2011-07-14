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

package com.travelplanner;

import com.travelplanner.db.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CheckList extends Activity implements OnClickListener{
	long id;
	private DBAdapter db = null;
	private final int addButtonID =1;
	private final int doneButtonID =2;
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		id= bundle.getLong("id");
		db = new DBAdapter(this);
		setContentView(populateLayOut());
	}
	private ScrollView populateLayOut() {
		ScrollView sv = new ScrollView(this);
		sv.setBackgroundColor(Color.parseColor("#3500ffff"));
		TableLayout main = new TableLayout(this);
		
		Cursor c = db.getAllCheckListItem(id);
		 
		if (c.moveToFirst())  {      
            DisplayCheckList(c,main);        
		}
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
      /* Create a Button to be the row-content. */
		TextView tv = new TextView(this);
		tv.setText("Add Item:");
		
		tr.addView(tv);
		EditText et = new EditText(this);
		et.setWidth(200);
		et.setId(2020);
        et.setText("");
        tr.addView(et);
        main.addView(tr);
        TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
		
        Button b = new Button(this);
        b.setId(addButtonID);
		b.setText("Add");
		b.setOnClickListener(this);
        tr1.addView(b);
        Button done = new Button(this);
        done.setId(doneButtonID);
        done.setText("Done");
        done.setOnClickListener(this);
        tr1.addView(done);
        main.addView(tr1);
        sv.addView(main);
        return sv;
        
		
	}
	
	private void DisplayCheckList(Cursor c, TableLayout main) {
		if (c.moveToFirst())
        {
            do {   
            	TableRow tr = new TableRow(this);
        		tr.setLayoutParams(new LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));
        		
        		final CheckBox cb = new CheckBox(this);
        		final int id = c.getInt(0);
        		cb.setId(id);
        		cb.setOnCheckedChangeListener(new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if(isChecked){
						db.updateStatus(cb.getId(), 1);
							
						}else{
							db.updateStatus(cb.getId(), 0);
						}
					}
        	 		}
        		);
        		
                cb.setText(c.getString(1));
                if (c.getInt(2)==1) {
                    cb.setChecked(true);
                }else{
                	 cb.setChecked(false);
                }
                tr.addView(cb);
                Button deleteButton = new Button(this);
                deleteButton.setText("Delete");
                deleteButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
					db.delete(id);
					setContentView(populateLayOut());
						
					}
				});
                tr.addView(deleteButton);
        	//	TextView tv = new TextView(this);
        	//	tv.setText(c.getString(0));
        	//	tr.addView(tv);
        		main.addView(tr);
                
            } while (c.moveToNext());
        }
		
	}
			
	@Override
	public void onClick(View v) {
		
			switch (v.getId()) {
			case addButtonID:
				String name = ((EditText) findViewById(2020)).getText().toString();
				 db.insertCheckListItem(id, name, 0);
				 setContentView(populateLayOut());
			break;
			case doneButtonID:
				Intent showTravelCheckListIntent = new Intent(this, ShowTravelCheckList.class);
				Bundle bundle = new Bundle();
				bundle.putLong("id", id);
								  
				showTravelCheckListIntent.putExtras(bundle);
				startActivity(showTravelCheckListIntent);
				break;
			
			// More buttons go here (if any) ...
			}
			}
			
}
