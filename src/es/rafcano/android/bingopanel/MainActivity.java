/**
 * BingoPanel
 */
package es.rafcano.android.bingopanel;

import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

/**
 * The start point of the application.
 * @author Rafael Cano Parra
 * @version 1.3
 */
public class MainActivity extends ActionBarActivity {

	/**
	 * The table with the numbers of the bingo panel.
	 */
	private GridView gridViewNumbers;

	/**
	 * The text of the last active numbers.
	 */
	private TextView textViewLastNumbers;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// initial tasks
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// constructs the table for the numbers and assign the adapter that manages the table 
		gridViewNumbers = (GridView) findViewById(R.id.gridViewNumbers);
	    gridViewNumbers.setAdapter(new NumberAdapter(this));

	    // reads the active numbers and sets the color to red
		String activeNumbers = InternalStorage.getActiveNumbers(getApplicationContext());
		if (activeNumbers != null) {
			for (int i = 0; i < activeNumbers.length(); i++) {
				if (String.valueOf(activeNumbers.charAt(i)).equals("1")) {
					((TextView) gridViewNumbers.getAdapter().getItem(i)).setTextColor(getResources().getColor(R.color.dark_red));
					Log.i("BingoPanel", "The number (" + ((TextView) gridViewNumbers.getAdapter().getItem(i)).getText() + ") is active.");
				}
			}
		}
		
		// sets the last active numbers text and updates the current value
		textViewLastNumbers = (TextView) findViewById(R.id.textViewLastNumbers);
		updateLastNumbers(null);
		
	    // adds the change color after clicking a number
	    gridViewNumbers.setOnItemClickListener(new OnItemClickListener() {
	    	
	    	@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    		// changes to red or black the color text to the selected number
				TextView number = (TextView) parent.getAdapter().getItem(position);
				if (number.getCurrentTextColor() == getResources().getColor(R.color.dark_red)) {
					number.setTextColor(Color.BLACK);
					Log.i("BingoPanel", "The number " + number.getText() + " is inactive now.");
				} else {
					number.setTextColor(getResources().getColor(R.color.dark_red));
					Log.i("BingoPanel", "The number " + number.getText() + " is active now.");
				}
				
				// updates and saves the current values in the internal storage 
				updateLastNumbers(number.getText().toString());
				InternalStorage.setActiveNumbers(getApplicationContext(), getActiveNumbersSequence());
	    	}
	    	
	    });
	}
	
	
	/**
	 * Updates the last active numbers list with a new number.
	 * @param number a number to be added or deleted, or null if you want to update the text
	 */
	private void updateLastNumbers(String number) {
		// gets the current last active numbers sequence from the internal storage
		String lastNumbers = InternalStorage.getLastNumbers(getApplicationContext());
		ArrayList<String> lastNumbersList = null;
		if (lastNumbers != null) {
			lastNumbersList = new ArrayList<String>(Arrays.asList(lastNumbers.split(", ")));
		} else {
			lastNumbersList = new ArrayList<String>();
		}
		
		// adds or delete a new number from the list and saves the new list
		if (number != null) {
			if (lastNumbersList.contains(number)) {
				lastNumbersList.remove(number);
				Log.i("BingoPanel", "The number " + number + " was deleted from the last active numbers.");
			} else {
				lastNumbersList.add(0, number);
				Log.i("BingoPanel", "The number " + number + " was added in the last active numbers.");
			}
			// quits the '[' and ']' from the ArrayList string in the last active numbers list before to store
			InternalStorage.setLastNumbers(getApplicationContext(),
				lastNumbersList.toString().substring(1, lastNumbersList.toString().length() - 1));
		}
		
		// updates the text label in the interface with the last eight numbers
		if (lastNumbersList.size() > 0) {
			textViewLastNumbers.setText("");
			textViewLastNumbers.append(lastNumbersList.get(0));
			for (int i = 1; i < lastNumbersList.size() && i < 12; i++) {
				textViewLastNumbers.append(", " + lastNumbersList.get(i));
			}
		} else {
			textViewLastNumbers.setText(getResources().getString(R.string.no_numbers));
		}
		Log.i("BingoPanel", "The last active numbers were updated in the interface: " + textViewLastNumbers.getText().toString());
	}
	
	/**
	 * Generates the sequence of the active numbers to be saved in the internal storage.
	 * @return an ordered string with the sequence of active numbers (1 to active, 0 to inactive) 
	 */
	private String getActiveNumbersSequence() {
		String activeNumbers = "";
		for (int i = 0; i < gridViewNumbers.getAdapter().getCount(); i++) {
			if (((TextView) gridViewNumbers.getAdapter().getItem(i)).getCurrentTextColor() == getResources().getColor(R.color.dark_red)) {
				activeNumbers += "1";
			} else {
				activeNumbers += "0";
			}
		}
		return activeNumbers;
	}
	
	
	/**
	 * Initializes all values as a new play.
	 * @param v view
	 */
	public void onClickNewPlay(View v) {
		// sets all active numbers as inactive, saves the changes and updates the interface
		for (int i = 0; i < gridViewNumbers.getAdapter().getCount(); i++) {
			((TextView) gridViewNumbers.getAdapter().getItem(i)).setTextColor(Color.BLACK);
		}
		InternalStorage.setActiveNumbers(getApplicationContext(), getActiveNumbersSequence());
		gridViewNumbers.setAdapter(new NumberAdapter(this));
		Log.i("BingoPanel", "All numbers were defined as inactive.");
		
		// clears the last active numbers, saves the changes and updates the interface 
		textViewLastNumbers.setText(getResources().getString(R.string.no_numbers));
		InternalStorage.setLastNumbers(getApplicationContext(), "");
		Log.i("BingoPanel", "All last active numbers were deleted.");
	}

}
