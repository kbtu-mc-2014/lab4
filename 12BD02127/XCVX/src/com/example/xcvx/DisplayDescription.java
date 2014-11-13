package com.example.xcvx;

import com.example.xcvx.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayDescription extends Activity {
	
	private static final String NAME = "name";
	private static final String AUTHOR = "author";
	private static final String YEAR = "year";
	private static final String GENRE = "genre";
	private static final String COST = "cost";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_desc);
		
		 // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String name = in.getStringExtra(NAME);
        String author = in.getStringExtra(AUTHOR);
        String year = in.getStringExtra(YEAR);
        String genre = in.getStringExtra(GENRE);
        String cost = in.getStringExtra(COST);
        
        // Displaying all values on the screen
        TextView BName = (TextView) findViewById(R.id.name_book);
        TextView BAuthor = (TextView) findViewById(R.id.author_book);
        TextView BYear = (TextView) findViewById(R.id.year_book);
        TextView BGENRE = (TextView) findViewById(R.id.genre_book);
        TextView BCOST = (TextView) findViewById(R.id.cost_book);
        
        BName.setText(name);
        BAuthor.setText(author);
        BYear.setText(year);
        BGENRE.setText(genre);
        BCOST.setText(cost);
	}

}
