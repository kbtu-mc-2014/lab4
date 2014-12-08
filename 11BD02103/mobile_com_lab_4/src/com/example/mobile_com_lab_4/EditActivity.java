package com.example.mobile_com_lab_4;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends Activity {
	
	private static final String TAG_SUCCESS = "success", TAG_PRODUCT = "product", TAG_ID = "id", TAG_FULL_NAME = "full_name";
	private static String url_product_detail = "http://172.16.92.136/php/get_product_detail.php",
						  url_delete_product = "http://172.16.92.136/php/delete_product.php",
					      url_update_product = "http://172.16.92.136/php/update_product.php";
	private String id;
	private ProgressDialog dialog;
	private int success;
	private JSONParser jsonParser;
	private JSONArray products = null;
	private EditText editFullName;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_edit);
		Intent i = getIntent();
		jsonParser = new JSONParser();
		id = i.getStringExtra(TAG_ID);
		editFullName = (EditText) findViewById(R.id.editFullName);
		new MyTask().execute();
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.btnSave:
			new SaveProduct().execute();
			break;
		case R.id.btnDelete:
			new DeleteProduct().execute();
			break;
		}
	}
	
	class DeleteProduct extends AsyncTask<Void, Void, Void> {
		
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(EditActivity.this);
			dialog.setMessage("Deleting product...");
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		protected Void doInBackground(Void... params) {
			try {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair(TAG_ID, id));
				JSONObject jsonObject = jsonParser.makeHttpRequest(url_delete_product, "POST", pairs);
				success = jsonObject.getInt("success");
				if(success == 1) {
					Intent i = getIntent();
					setResult(100, i);
					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
		}
		
	}
	
	class SaveProduct extends AsyncTask<Void, Void, Void> {
		
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(EditActivity.this);
			dialog.setMessage("Saving product...");
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		protected Void doInBackground(Void... params) {
			try {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair(TAG_ID, id));
				pairs.add(new BasicNameValuePair(TAG_FULL_NAME, editFullName.getText().toString()));
				JSONObject jsonObject = jsonParser.makeHttpRequest(url_update_product, "POST", pairs);
				success = jsonObject.getInt("success");
				if(success == 1) {
					Intent i = getIntent();
					setResult(100, i);
					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
		}
		
	}
	
	class MyTask extends AsyncTask<Void, Void, Void> {
		
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(EditActivity.this);
			dialog.setMessage("Loading product details. Please wait...");
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		protected Void doInBackground(Void... params) {
			try {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair(TAG_ID, id));
				JSONObject jsonObject = jsonParser.makeHttpRequest(url_product_detail, "GET", pairs);
				Log.d("log", jsonObject.toString());
				success = jsonObject.getInt(TAG_SUCCESS);
				Log.d("log", ""+success+"");
				if(success == 1) {
					products = jsonObject.getJSONArray(TAG_PRODUCT);
					JSONObject product = products.getJSONObject(0);
					editFullName.setText(product.getString("full_name"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
		}
		
	}

}
