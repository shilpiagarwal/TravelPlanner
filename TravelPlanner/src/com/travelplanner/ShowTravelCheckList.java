
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ShowTravelCheckList extends Activity implements OnClickListener{
	long id;
	private DBAdapter db = null;
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
		
		Cursor travel = db.getTravelPlan(id);
		if (travel.moveToFirst())  {      
			displayTravelPlan(travel,main);        
		}
		
		Cursor c = db.getAllCheckListItem(id);
		 
		if (c.moveToFirst())  {      
            DisplayCheckList(c,main);        
		}
		
		displayHomePage(main);
		        
        sv.addView(main);
        return sv;
        
		
	}
	
	private void displayHomePage(TableLayout main) {
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
		Button homeButton = new Button(this);
		homeButton.setText("Home");
		homeButton.setOnClickListener(this) ;
        	
        tr.addView(homeButton);
        main.addView(tr);
		
	}

	private void displayTravelPlan(Cursor c, TableLayout main){
		if (c.moveToFirst())  {      
      	
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
      /* Create a Button to be the row-content. */
		TextView name= new TextView(this);
		name.setWidth(120);
		name.setText("Name");
		
		tr.addView(name);
		TextView nameValue = new TextView(this);
		nameValue.setWidth(120);
		nameValue.setText(c.getString(1));
		
		tr.addView(nameValue);
		main.addView(tr);
		TableRow tr1 = new TableRow(this);
		tr1.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
      /* Create a Button to be the row-content. */
		TextView mode= new TextView(this);
		mode.setText("Mode");
		
		tr1.addView(mode);
		TextView modeValue = new TextView(this);
		modeValue.setText(c.getString(2));
		
		tr1.addView(modeValue);
		main.addView(tr1);
		
		TableRow tr2 = new TableRow(this);
		tr2.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
      /* Create a Button to be the row-content. */
		TextView type= new TextView(this);
		type.setText("Type");
		
		tr2.addView(type);
		TextView typeValue = new TextView(this);
		typeValue.setText(c.getString(3));
		
		tr2.addView(typeValue);
		main.addView(tr2);
		
		TableRow tr3 = new TableRow(this);
		tr3.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
      /* Create a Button to be the row-content. */
		TextView date= new TextView(this);
		date.setText("Date");
		
		tr3.addView(date);
		TextView dateValue = new TextView(this);
		dateValue.setText(c.getString(4));
		
		tr3.addView(dateValue);
		main.addView(tr3);
		
		TableRow tr4 = new TableRow(this);
		tr4.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
      /* Create a Button to be the row-content. */
		TextView time= new TextView(this);
		time.setText("Time");
		
		tr4.addView(time);
		TextView timeValue = new TextView(this);
		timeValue.setText(c.getString(5));
		
		tr4.addView(timeValue);
		main.addView(tr4);
		
		TableRow tr5 = new TableRow(this);
		tr5.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
      /* Create a Button to be the row-content. */
		TextView from= new TextView(this);
		from.setText("From");
		
		tr5.addView(from);
		TextView fromValue = new TextView(this);
		fromValue.setText(c.getString(6));
		
		tr5.addView(fromValue);
		main.addView(tr5);
		
		TableRow tr6 = new TableRow(this);
		tr6.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
      /* Create a Button to be the row-content. */
		TextView to= new TextView(this);
		to.setText("To");
		
		tr6.addView(to);
		TextView toValue = new TextView(this);
		toValue.setText(c.getString(7));
		
		tr6.addView(toValue);
		main.addView(tr6);
		
		}
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
        		cb.setId(c.getInt(0));
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
        	//	TextView tv = new TextView(this);
        	//	tv.setText(c.getString(0));
        	//	tr.addView(tv);
        		main.addView(tr);
                
            } while (c.moveToNext());
        }
		
	}

	@Override
	public void onClick(View v) {
		Intent newIntent = new Intent(this, MainActivity.class);
		startActivity(newIntent);	
		
	}
			
	

}
