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

For feedback please mail at agarwal.shilpi.84@gmail.com/hemasid@gmail.com
                            
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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class ExistingPlan extends Activity implements OnClickListener{
	
	
	private DBAdapter db = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		db = new DBAdapter(this);
		setContentView(populateLayOut());
			
	}
	
	private ScrollView populateLayOut() {
		ScrollView sv = new ScrollView(this);
		sv.setBackgroundColor(Color.parseColor("#3500ffff"));
		TableLayout main = new TableLayout(this);
		
		Cursor travel = db.getAllTravelPlan();
		if (travel.moveToFirst())  {      
			displayTravelPlan(travel,main);        
		}
		
		
		        
        sv.addView(main);
        return sv;
        
		
	}
	
		
	private void displayTravelPlan(final Cursor c, TableLayout main) {
		if (c.moveToFirst())
        {
            do {   
            	TableRow tr = new TableRow(this);
        		tr.setLayoutParams(new LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));
              /* Create a Button to be the row-content. */
        		TextView name= new TextView(this);
        		name.setWidth(120);
        		name.setText(c.getString(1));
        		
        		tr.addView(name);
        		
        		Button b1 = new Button(this);
        		b1.setText("Detail");
        		b1.setHeight(10);
        		final int id = c.getInt(0);
        		b1.setId(id);
        		b1.setOnClickListener(this);
        		tr.addView(b1);
        		
        		Button delete = new Button(this);
        		delete.setText("Remove");
        		delete.setHeight(10);
       
        		delete.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						db.delete(id);
						db.deleteTravelPlan(id);
					    setContentView(populateLayOut());			
					}
        			
        			
        			
        		});
        		tr.addView(delete);
        		
        		main.addView(tr);
                
            } while (c.moveToNext());
        }
		
	}
	
	@Override
	public void onClick(View v1) {
		
		Intent showTravelCheckListIntent = new Intent(this,ShowTravelCheckList.class);
		Bundle bundle = new Bundle();
		bundle.putLong("id", v1.getId());
						  
		showTravelCheckListIntent.putExtras(bundle);
		startActivity(showTravelCheckListIntent);
		
	}
}
