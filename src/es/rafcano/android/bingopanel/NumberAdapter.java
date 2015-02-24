/**
 * BingoPanel
 */
package es.rafcano.android.bingopanel;

import java.util.Vector;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * The number adapter of the GridView.
 * @author Rafael Cano Parra
 * @version 1.2
 */
public class NumberAdapter extends BaseAdapter {
	
	/**
	 * The total of numbers defined in the table.
	 */
	static private int NUMBERS = 90;
	
	
	/**
	 * Contains the bingo numbers as a text view.
	 */
	private Vector<TextView> numbers;
	
	
	/**
	 * Class constructor.
	 * @param c context
	 */
	public NumberAdapter(Context c) {
		// constructs the bingo numbers to the table
		numbers = new Vector<TextView>();
    	for (int i = 0; i < NUMBERS; i++) {
    		TextView n = new TextView(c);
			n.setLayoutParams(new GridView.LayoutParams(80, 80));
			n.setPadding(2, 5, 2, 5);
			n.setGravity(Gravity.CENTER);
			n.setTextSize(20);
			n.setText(String.valueOf(i + 1));
			n.setTextColor(Color.BLACK);
			numbers.add(i, n);
    	}
    	Log.i("BingoPanel", "All the bingo numbers were generated.");
	}	
	
	
	@Override
	public int getCount() {
		return numbers.size();
	}

	@Override
	public Object getItem(int position) {
		return numbers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return numbers.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView number;
		if (convertView == null) {
			number = numbers.get(position);
		} else {
			number = (TextView) convertView;
		}
        return number;
	}
	
}
