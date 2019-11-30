package com.example.ebookreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.InputStream;

public class ReaderActivity extends AppCompatActivity {
    public static final String BOOK_EXTRA = "book";

    String book;

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
                locationBar.setMax(etContents.getHeight()*17000);
                //locationBar.setMax(textContainer.getTop());
                locationBar.setProgress(textContainer.getScrollY());

            } else {
                // had a problem
                //Toast.makeText( getApplicationContext(), "Failure", Toast.LENGTH_LONG ).show();
            }
        }
    }
}
