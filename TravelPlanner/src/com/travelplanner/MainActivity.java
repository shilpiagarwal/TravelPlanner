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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        View newButton = findViewById(R.id.newPlanID);
        newButton.setOnClickListener(this);
        
        View existingButton = findViewById(R.id.existingPlanID);
        existingButton.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) 
    {
		
			switch (v.getId()) {
			case R.id.newPlanID:
			Intent newIntent = new Intent(this, NewPlan.class);
			startActivity(newIntent);
			break;
			case R.id.existingPlanID:
				Intent existingIntent = new Intent(this, ExistingPlan.class);
				startActivity(existingIntent);
				break;
			
			// More buttons go here (if any) ...
			}
			}
		
}