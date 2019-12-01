/*
    Author: Kodi Durham

    Course: CSC 309

    Date: Dec. 1, 2019

    Class: Read Activity

    Purpose: Its suppose to be able to get books online and download them and read them and delete them.
        keeping track of the readers place and allowing them to jumping to points in the book. Also
        allow the user to change the text size.

    Class Purpose: This class allows the user to read the books and jump to certain place with a slider.
        It should start where the reader left off and if there wasn't one it starts at the beginning.
*/

package com.example.ebookreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReaderActivity extends AppCompatActivity {
    public static final String BOOK_EXTRA = "book";
    public static final String LOCATION_EXTRA = "location";

    List<String> downloadedBooks;
    List<Integer> progress;

    String LISTS_DOWNLOADED="books";
    String LISTS_PROGRESS="progress";

    String book;
    int location;

    TextView etContents;
    SeekBar locationBar;
    ScrollView textContainer;


    String SETTING_TEXTSIZE="size";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        SharedPreferences preferencesGet = PreferenceManager.getDefaultSharedPreferences( this );
        int textSize = preferencesGet.getInt( SETTING_TEXTSIZE, 14);

        etContents = findViewById( R.id.tv_bookText );
        locationBar= findViewById(R.id.sb_location);
        textContainer = findViewById(R.id.sV_bookreader);

        etContents.setTextSize(textSize);

        Intent intent = getIntent();
        book = intent.getStringExtra( BOOK_EXTRA );
        location= intent.getIntExtra(LOCATION_EXTRA,0);

        new ReadTask().execute();

        TextView titleTv=findViewById(R.id.tv_bookTitle);
        titleTv.setText(book);



        locationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textContainer.setScrollY(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ViewTreeObserver vto = textContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                textContainer.setScrollY(location);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    // respond to a menu item click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // which item did they click?
        switch ( item.getItemId() ) {
            case R.id.menu_settings:

                // want to change a setting
                Intent settingsIntent = new Intent(
                        getApplicationContext(), SettingActivity.class );
                // start the activity, getting a response
                startActivity(settingsIntent);
                return true;

            default:
                // unknown item
                return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences preferencesGet = PreferenceManager.getDefaultSharedPreferences( this );
        int textSize = preferencesGet.getInt( SETTING_TEXTSIZE, 14);

        etContents.setTextSize(textSize);
        textContainer.setScrollY( location);

    }

    @Override
    public void onPause(){
        super.onPause();
        load();


        final int index = downloadedBooks.indexOf(book);
        ViewTreeObserver vto = textContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                progress.set(index,textContainer.getScrollY());
            }
        });

        progress.set(index,textContainer.getScrollY());

        save();
    }



    // AsyncTask to read the file in the background
    class ReadTask extends AsyncTask<Void,Void,String> {
        protected String doInBackground( Void ... data ) {
            String fileData = "";
            byte[] rawFileData = new byte[50000000];

            try {
                // open the file
                InputStream in = openFileInput( book+".txt" );
                // get the data
                int byteCount = in.read( rawFileData );

                // convert to a string
                fileData = new String( rawFileData, 0, byteCount );

                // done
                in.close();
            } catch ( Exception e ) {
                // ignore it
                return null;
            }

            // all went well
            return fileData;
        }

        // write is finished
        @Override
        protected void onPostExecute( String data ) {
            // did all go well
            if ( data != null ) {
                // no exceptions, all ok
                //Toast.makeText( getApplicationContext(), "Success", Toast.LENGTH_LONG ).show();
                // copy to the EditText
                etContents.setText( data );


                int range =etContents.getLineHeight()*etContents.getLineCount();
                locationBar.setMax(range);

                textContainer.setScrollY( location);
                locationBar.setProgress(location);


            } else {
                // had a problem
                //Toast.makeText( getApplicationContext(), "Failure", Toast.LENGTH_LONG ).show();
            }
        }
    }

    private void save(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(downloadedBooks);
        editor.putString( LISTS_DOWNLOADED, json);
        json = gson.toJson(progress);
        editor.putString( LISTS_PROGRESS, json);
        editor.apply();
    }

    private void load(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();

        String json = preferences.getString(LISTS_DOWNLOADED,null);
        Type type =new TypeToken<ArrayList<String>>() {}.getType();
        downloadedBooks = gson.fromJson(json,type);

        json = preferences.getString(LISTS_PROGRESS,null);
        type =new TypeToken<ArrayList<Integer>>() {}.getType();
        progress=gson.fromJson(json,type);

        if(downloadedBooks == null){
            downloadedBooks = new ArrayList<>();
        }
        if(progress == null){
            progress = new ArrayList<>();
        }

    }

}
