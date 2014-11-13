package com.example.xcvx;

import com.example.xcvx.R;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	private ProgressDialog Dial;

	// URL
	private static String url = "http://www.json-generator.com/api/json/get/cpOtOPUgEi?indent=2";

	// JSON Tag Nodes
	private static final String BOOKS = "books";
	private static final String NAME = "name";
	private static final String AUTHOR = "author";
	private static final String ID = "id";
	private static final String GENRE = "genre";
	private static final String YEAR = "year";
	private static final String COST = "cost";

	// JSON Array
	JSONArray books = null;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> bookslist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bookslist = new ArrayList<HashMap<String, String>>();
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ListItem clicked
				String name = ((TextView) view.findViewById(R.id.name))
						.getText().toString();
				String author = ((TextView) view.findViewById(R.id.author))
						.getText().toString();
				String year = ((TextView) view.findViewById(R.id.year)).getText().toString();
				String genre = ((TextView) view.findViewById(R.id.genre)).getText().toString();
				String cost = ((TextView) view.findViewById(R.id.cost)).getText().toString();

				// Start another activity
				Intent intent = new Intent(getApplicationContext(),
						DisplayDescription.class);
				intent.putExtra(NAME, name);
				intent.putExtra(AUTHOR, author);
				intent.putExtra(YEAR, year);
				intent.putExtra(GENRE, genre);
				intent.putExtra(COST, cost);
				startActivity(intent);
			}
		});
		new GetBooks().execute();

	}

	private class GetBooks extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// Progress Dialog
			Dial = new ProgressDialog(MainActivity.this);
			Dial.setMessage("Please wait . . .");
			Dial.setCancelable(false);
			Dial.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			ServiceHandler sh = new ServiceHandler();

			// making service call and get response

			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
			Log.d("Response: ", ">" + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					// getting JSONArray node
					books = jsonObj.getJSONArray(BOOKS);

					// looping through books
					for (int i = 0; i < books.length(); i++) {
						JSONObject b = books.getJSONObject(i);
						String id = b.getString(ID);
						String name = b.getString(NAME);
						String author = b.getString(AUTHOR);
						String year = b.getString(YEAR);
						String genre = b.getString(GENRE);
						String cost = b.getString(COST);

						// tmp hashmap for single book
						HashMap<String, String> book = new HashMap<String, String>();
						// adding info to a book
						book.put(ID, id);
						book.put(GENRE, genre);
						book.put(NAME, name);
						book.put(AUTHOR, author);
						book.put(YEAR, year);
						book.put(COST, cost);

						// adding a book to book list
						bookslist.add(book);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (Dial.isShowing()) 
				Dial.dismiss();
			
			ListAdapter adapter = new SimpleAdapter(MainActivity.this,
					bookslist, R.layout.list,
					new String[] { NAME, AUTHOR, YEAR, GENRE, COST }, new int[] { R.id.name,
							R.id.author, R.id.year, R.id.genre, R.id.cost });

			setListAdapter(adapter);
		}

	}
}
