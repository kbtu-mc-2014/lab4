package com.example.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ArrayList<HashMap<String, String >> studentList;
	private ProgressDialog dialog;
	private JSONParser jsonParser;
	private List<NameValuePair> pairs;
	private JSONObject jsonObject, student;
	private static String url_get_all_students = "http://172.16.83.206/mc_lab/getAllStudents";
	private int success, i;
	private static final String TAG_SUCCESS = "success", TAG_STUDENTS = "students", TAG_ID = "id", TAG_FULL_NAME = "full_name";
	private JSONArray students = null;
	private ListView listView;
	private ListAdapter adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
		jsonParser = new JSONParser();
		studentList = new ArrayList<HashMap<String,String>>();
		listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getApplicationContext(), EditActivity.class);
				intent.putExtra(TAG_ID, ((TextView) arg1.findViewById(R.id.id)).getText().toString());
				startActivityForResult(intent, 100);
			}
		});
		new MyTask().execute();
	}
	
	public void btnClick(View view) {
		Intent in = new Intent(this, AddActivity.class);
		startActivity(in);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 100) {
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}
	}
	
	class MyTask extends AsyncTask<Void, Void, Void> {
		
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setMessage("Loading. Please wait...");
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		protected Void doInBackground(Void... params) {
			try {
				pairs = new ArrayList<NameValuePair>();
				jsonObject = jsonParser.makeHttpRequest(url_get_all_students, "GET", pairs);
				success = jsonObject.getInt(TAG_SUCCESS);
				if(success == 1) {
					students = jsonObject.getJSONArray(TAG_STUDENTS);
					for(i=0;i<students.length();i++) {
						student = students.getJSONObject(i);
						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put(TAG_ID, student.getString(TAG_ID));
						hashMap.put(TAG_FULL_NAME, student.getString(TAG_FULL_NAME));
						studentList.add(hashMap);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
					adapter = new SimpleAdapter(MainActivity.this, studentList, R.layout.list_item,
							new String[] {TAG_ID, TAG_FULL_NAME}, new int[] {R.id.id, R.id.full_name});
					listView.setAdapter(adapter);
				}
			});
		}
		
	}

}
