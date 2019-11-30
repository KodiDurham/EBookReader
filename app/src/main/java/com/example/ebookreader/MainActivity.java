package com.example.ebookreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<String> downloadedBooks = new ArrayList<>();
    List<String> readyToBooks = new ArrayList<>();
    String[] listTitles = {"Adventures of Huckleberry Finn",
            "The Adventures of Sherlock Holmes", "The Adventures of Tom Sawyer",
            "Alice's Adventures in Wonderland", "Also sprach Zarathustra. English",
            "An Occurrence at Owl Creek Bridge", "Anne of Green Gables", "Anthem",
            "Autobiography of Benjamin Franklin", "The Awakening, and Selected Short Stories",
            "Beowulf: An Anglo-Saxon Epic Poem", "Beyond Good and Evil", "The Brothers Karamazov",
            "Candide", "The Chaldean Account of Genesis",
            "A Christmas Carol in Prose; Being a Ghost Story of Christmas", "Common Sense",
            "The Complete Works of William Shakespeare", "The Confessions of St. Augustine",
            "The Count of Monte Cristo", "Crime and Punishment",
            "The Criticism of the Fourth Gospel", "Democracy in America — Volume 1",
            "The Devil's Dictionary", "Divine Comedy, Longfellow's Translation, Hell", "A Doll's House: a play", "Dracula",
            "Dubliners", "Emma", "Essays by Ralph Waldo Emerson",
            "Essays of Michel de Montaigne — Complete", "Ethan Frome",
            "Frankenstein; Or, The Modern Prometheus", "Great Expectations", "Grimms' Fairy Tales",
            "Gulliver's Travels into Several Remote Nations of the World", "Heart of Darkness",
            "The Hound of the Baskervilles",
            "How the Other Half Lives: Studies Among the Tenements of New York", "The Iliad",
            "The Importance of Being Earnest: A Trivial Comedy for Serious People",
            "Incidents in the Life of a Slave Girl, Written by Herself",
            "The Interesting Narrative of the Life of Olaudah Equiano, Or Gustavus Vassa, The African",
            "Ion", "Jane Eyre: An Autobiography", "The Jungle", "The Kama Sutra of Vatsyayana",
            "Le Morte d'Arthur: Volume 1", "Leaves of Grass", "The Legend of Sleepy Hollow",
            "Les Misérables", "Leviathan", "The Life and Adventures of Robinson Crusoe",
            "Little Women", "Metamorphosis", "Moby Dick; Or, The Whale",
            "Modern Copper Smelting", "A Modest Proposal",
            "Narrative of the Captivity and Restoration of Mrs. Mary Rowlandson",
            "Narrative of the Life of Frederick Douglass, an American Slave",
            "On Liberty", "The Parochial History of Cornwall, Volume 1", "Peter Pan",
            "The Picture of Dorian Gray", "Pride and Prejudice", "The Prince",
            "The Problems of Philosophy", "The Prophet", "Pygmalion", "The Republic",
            "The Scarlet Letter", "Second Treatise of Government", "Sense and Sensibility",
            "Siddhartha", "Songs of Innocence, and Songs of Experience",
            "The Souls of Black Folk", "The Strange Case of Dr. Jekyll and Mr. Hyde",
            "A Study in Scarlet", "A Tale of Two Cities", "The Theory of the Leisure Class",
            "The Time Machine", "The Tragical History of Doctor Faustus", "Treasure Island",
            "The Turn of the Screw", "Ulysses", "Uncle Tom's Cabin", "The Used People Lot",
            "Walden, and On The Duty Of Civil Disobedience", "War and Peace",
            "The War of the Worlds", "The Wonderful Wizard of Oz",
            "The Works of Edgar Allan Poe — Volume 2",
            "The Works of Edgar Allan Poe, The Raven Edition", "Wuthering Heights", "The Yellow Wallpaper"
    };
    String[] listURLs = {
            "https://www.gutenberg.org/files/76/76-0.txt",
            "https://www.gutenberg.org/files/1661/1661-0.txt",
            "https://www.gutenberg.org/files/74/74-0.txt",
            "https://www.gutenberg.org/files/11/11-0.txt",
            "https://www.gutenberg.org/files/1998/1998-0.txt",
            "https://www.gutenberg.org/cache/epub/375/pg375.txt",
            "https://www.gutenberg.org/files/45/45-0.txt",
            "https://www.gutenberg.org/cache/epub/1250/pg1250.txt",
            "https://www.gutenberg.org/cache/epub/20203/pg20203.txt",
            "https://www.gutenberg.org/files/160/160-0.txt",
            "https://www.gutenberg.org/cache/epub/16328/pg16328.txt",
            "https://www.gutenberg.org/cache/epub/4363/pg4363.txt",
            "https://www.gutenberg.org/files/28054/28054-0.txt",
            "https://www.gutenberg.org/cache/epub/19942/pg19942.txt",
            "https://www.gutenberg.org/files/60559/60559-0.txt",
            "https://www.gutenberg.org/files/46/46-0.txt",
            "https://www.gutenberg.org/cache/epub/147/pg147.txt",
            "https://www.gutenberg.org/files/100/100-0.txt",
            "https://www.gutenberg.org/cache/epub/3296/pg3296.txt",
            "https://www.gutenberg.org/files/1184/1184-0.txt",
            "https://www.gutenberg.org/files/2554/2554-0.txt",
            "https://www.gutenberg.org/files/60553/60553-0.txt",
            "https://www.gutenberg.org/cache/epub/815/pg815.txt",
            "https://www.gutenberg.org/cache/epub/972/pg972.txt",
            "https://www.gutenberg.org/cache/epub/1001/pg1001.txt",
            "https://www.gutenberg.org/cache/epub/2542/pg2542.txt",
            "https://www.gutenberg.org/cache/epub/345/pg345.txt",
            "https://www.gutenberg.org/files/2814/2814-0.txt",
            "https://www.gutenberg.org/files/158/158-0.txt",
            "https://www.gutenberg.org/cache/epub/16643/pg16643.txt",
            "https://www.gutenberg.org/files/3600/3600-0.txt",
            "https://www.gutenberg.org/files/4517/4517-0.txt",
            "https://www.gutenberg.org/files/84/84-0.txt",
            "https://www.gutenberg.org/files/1400/1400-0.txt",
            "https://www.gutenberg.org/files/2591/2591-0.txt",
            "https://www.gutenberg.org/files/829/829-0.txt",
            "https://www.gutenberg.org/files/219/219-0.txt",
            "https://www.gutenberg.org/files/2852/2852-0.txt",
            "https://www.gutenberg.org/cache/epub/45502/pg45502.txt",
            "https://www.gutenberg.org/cache/epub/6130/pg6130.txt",
            "https://www.gutenberg.org/cache/epub/844/pg844.txt",
            "https://www.gutenberg.org/cache/epub/11030/pg11030.txt",
            "https://www.gutenberg.org/cache/epub/15399/pg15399.txt",
            "https://www.gutenberg.org/cache/epub/1635/pg1635.txt",
            "https://www.gutenberg.org/cache/epub/1260/pg1260.txt",
            "https://www.gutenberg.org/files/140/140-0.txt",
            "https://www.gutenberg.org/cache/epub/27827/pg27827.txt",
            "https://www.gutenberg.org/files/1251/1251-0.txt",
            "https://www.gutenberg.org/files/1322/1322-0.txt",
            "https://www.gutenberg.org/files/41/41-0.txt",
            "https://www.gutenberg.org/files/135/135-0.txt",
            "https://www.gutenberg.org/cache/epub/3207/pg3207.txt",
            "https://www.gutenberg.org/files/521/521-0.txt",
            "https://www.gutenberg.org/cache/epub/514/pg514.txt",
            "https://www.gutenberg.org/cache/epub/5200/pg5200.txt",
            "https://www.gutenberg.org/files/2701/2701-0.txt",
            "https://www.gutenberg.org/files/59328/59328-0.txt",
            "https://www.gutenberg.org/files/1080/1080-0.txt",
            "https://www.gutenberg.org/cache/epub/851/pg851.txt",
            "https://www.gutenberg.org/cache/epub/23/pg23.txt",
            "https://www.gutenberg.org/cache/epub/34901/pg34901.txt",
            "https://www.gutenberg.org/files/60555/60555-0.txt",
            "https://www.gutenberg.org/files/16/16-0.txt",
            "https://www.gutenberg.org/cache/epub/174/pg174.txt",
            "https://www.gutenberg.org/files/1342/1342-0.txt",
            "https://www.gutenberg.org/cache/epub/1232/pg1232.txt",
            "https://www.gutenberg.org/cache/epub/5827/pg5827.txt",
            "https://www.gutenberg.org/files/58585/58585-0.txt",
            "https://www.gutenberg.org/cache/epub/3825/pg3825.txt",
            "https://www.gutenberg.org/cache/epub/1497/pg1497.txt",
            "https://www.gutenberg.org/files/25344/25344-0.txt",
            "https://www.gutenberg.org/cache/epub/7370/pg7370.txt",
            "https://www.gutenberg.org/cache/epub/161/pg161.txt",
            "https://www.gutenberg.org/cache/epub/2500/pg2500.txt",
            "https://www.gutenberg.org/files/1934/1934-0.txt",
            "https://www.gutenberg.org/cache/epub/408/pg408.txt",
            "https://www.gutenberg.org/files/43/43-0.txt",
            "https://www.gutenberg.org/files/244/244-0.txt",
            "https://www.gutenberg.org/files/98/98-0.txt",
            "https://www.gutenberg.org/cache/epub/833/pg833.txt",
            "https://www.gutenberg.org/files/35/35-0.txt",
            "https://www.gutenberg.org/cache/epub/779/pg779.txt",
            "https://www.gutenberg.org/files/120/120-0.txt",
            "https://www.gutenberg.org/files/209/209-0.txt",
            "https://www.gutenberg.org/files/4300/4300-0.txt",
            "https://www.gutenberg.org/files/203/203-0.txt",
            "https://www.gutenberg.org/cache/epub/60545/pg60545.txt",
            "https://www.gutenberg.org/files/205/205-0.txt",
            "https://www.gutenberg.org/files/2600/2600-0.txt",
            "https://www.gutenberg.org/files/36/36-0.txt",
            "https://www.gutenberg.org/cache/epub/55/pg55.txt",
            "https://www.gutenberg.org/files/2148/2148-0.txt",
            "https://www.gutenberg.org/cache/epub/25525/pg25525.txt",
            "https://www.gutenberg.org/cache/epub/768/pg768.txt",
            "https://www.gutenberg.org/files/1952/1952-0.txt"};

    String LISTS_DOWNLOADED="books";

    LinearLayout lLDownloaded;
    LinearLayout lLPossible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //new ReadTask().execute(bookTitles);

        lLDownloaded=findViewById(R.id.lL_downloaded);
        lLPossible=findViewById(R.id.lL_possible);

        for (int i=0; i<listTitles.length;i++){
            if (!downloadedBooks.contains(listTitles[i])){
                readyToBooks.add(listTitles[i]);
            }
        }
        updateDownloadedLayout();

    }

    // system is ready to create the menu
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

    //update downloaded layout
    public void updateDownloadedLayout(){
        lLDownloaded.removeAllViews();
        lLPossible.removeAllViews();
        for (int i =0; i<downloadedBooks.size();i++){
            addToLlList(downloadedBooks.get(i),lLDownloaded);
        }

        for (int i =0; i<readyToBooks.size();i++){
            addToLlList(readyToBooks.get(i),lLPossible);
        }
    }

    //add button to linearList
    public void addToLlList(final String title, final LinearLayout layout){

        final Button thisButton = new Button(this);
        thisButton.setText(title);

        layout.addView(thisButton);

        //if layout is lLDownloaded
        if (layout==lLDownloaded){
            thisButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "opening "+title, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent( getApplicationContext(), ReaderActivity.class );
                    intent.putExtra( ReaderActivity.BOOK_EXTRA, title );
                    startActivity( intent );

                    updateDownloadedLayout();
                }
            });
        }else {
            //if layout islLPossible
            thisButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int index =layout.indexOfChild(thisButton);
                    new AsyncGetBook().execute(title, listURLs[indexOf(title, listTitles)]);
                    downloadedBooks.add(title);
                    Toast.makeText(getApplicationContext(), title+" Downloaded", Toast.LENGTH_LONG).show();
                    readyToBooks.remove(title);
                    updateDownloadedLayout();
                }
            });
        }
    }

    public int indexOf(String key, String[] arr){
        for (int i=0;i<arr.length;i++){
            if(arr[i].equals(key)){
                return i;
            }
        }
        return -1;
    }

    protected String fetchItem( String str_url ) {
        try {
            // assemble the string and the search request
            StringBuilder response = new StringBuilder();
            URL url = new URL(str_url);

            // make the connection
            HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();

            // did it do ok?
            if ( httpconn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(httpconn.getInputStream()), 8192);
                String strLine = null;
                while ((strLine = input.readLine()) != null) {
                    // have more data
                    response.append(strLine);
                    response.append("\n");
                }
                input.close();
                return response.toString();
            }
        } catch ( IOException e ) {
            return e.getMessage();
        }
        return "";
    }

    class AsyncGetBook extends AsyncTask<String,String,Boolean> {
        // string of the book
        String book;

        // download book
        protected Boolean doInBackground(String ... info) {
            book=fetchItem(info[1]);

            try {
                // open the file
                OutputStream out = openFileOutput( info[0]+".txt", Context.MODE_PRIVATE );
                // output the data
                out.write( book.getBytes() );
                // done
                out.close();
            } catch ( Exception e ) {
                // ignore it
                return false;
            }

            return null;
        }



        // test display
        protected void onPostExecute( Boolean isPrime ) {
            //test.setText( book );
        }
    }

}
