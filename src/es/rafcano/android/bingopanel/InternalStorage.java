/**
 * BingoPanel
 */
package es.rafcano.android.bingopanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.util.Log;

/**
 * Manages the internal storage of the application.
 * @author Rafael Cano Parra
 * @version 1.2
 */
public class InternalStorage {
	
	/**
	 * File name to the active numbers.
	 */
	private static String ACTIVE_NUMBERS_FILE = "ActiveNumbers.dat";

	/**
	 * File name to the last active numbers.
	 */
	private static String LAST_NUMBERS_FILE = "LastNumbers.dat";
	
	
	/**
	 * Reads the active numbers from the internal storage.
	 * @param c context
	 * @return an ordered string with the active numbers (0 as inactive, 1 as active)
	 */
	public static String getActiveNumbers(Context c) {
		String activeNumbers = null;
		try {
			BufferedReader file = new BufferedReader(
				new InputStreamReader(c.openFileInput(ACTIVE_NUMBERS_FILE)));
			activeNumbers = file.readLine();
			file.close();
		} catch (IOException e) {
			Log.e("BingoPanel", "Unable to read the internal storage file: " + e);
		}
		Log.i("BingoPanel", "The active numbers were loaded from the internal storage: " + activeNumbers);
		return activeNumbers;
	}
	
	/**
	 * Saves the active numbers in the internal storage.
	 * @param c context
	 * @param activeNumbers an ordered string with the active numbers (0 as inactive, 1 as active)
	 */
	public static void setActiveNumbers(Context c, String activeNumbers) {
		try {
			OutputStreamWriter file = new OutputStreamWriter(
				c.openFileOutput(ACTIVE_NUMBERS_FILE, Context.MODE_PRIVATE));
			file.write(activeNumbers);
			file.close();
		} catch (IOException e) {
			Log.e("BingoPanel", "Unable to write in the internal storage file: " + e);
		}
		Log.i("BingoPanel", "The active numbers were saved in the internal storage: " + activeNumbers);
	}
	
	/**
	 * Reads the last active numbers from the internal storage.
	 * @param c context
	 * @return a ordered string, separated by commas, with the last active numbers (the first element is the last number)
	 */
	public static String getLastNumbers(Context c) {
		String lastNumbers = null;
		try {
			BufferedReader file = new BufferedReader(
				new InputStreamReader(c.openFileInput(LAST_NUMBERS_FILE)));
			lastNumbers = file.readLine();
			file.close();
		} catch (IOException e) {
			Log.e("BingoPanel", "Unable to read the internal storage file: " + e);
		}
		Log.i("BingoPanel", "The last numbers were loaded from the internal storage: " + lastNumbers);
		return lastNumbers;
	}
	
	/**
	 * Saves the last active numbers to the internal storage.
	 * @param c context
	 * @param lastNumbers an ordered string, separated by commas, with the last active numbers (the first element is the last number)
	 */
	public static void setLastNumbers(Context c, String lastNumbers) {
		try {
			OutputStreamWriter file = new OutputStreamWriter(
				c.openFileOutput(LAST_NUMBERS_FILE, Context.MODE_PRIVATE));
			file.write(lastNumbers);
			file.close();
		} catch (IOException e) {
			Log.e("BingoPanel", "Unable to write in the internal storage file: " + e);
		}
		Log.i("BingoPanel", "The last active numbers were saved in the internal storage: " + lastNumbers);
	}
	
}
