package com.example.ebookreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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

import static com.example.ebookreader.R.id.lL_possible;

public class MainActivity extends AppCompatActivity {

    List<String> downloadedBooks = new ArrayList<>();
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
            "https://www.gutenberg.org/ebooks/375.txt.utf-8",
            "https://www.gutenberg.org/files/45/45-0.txt",
            "https://www.gutenberg.org/ebooks/1250.txt.utf-8",
            "https://www.gutenberg.org/ebooks/20203.txt.utf-8",
            "https://www.gutenberg.org/files/160/160-0.txt",
            "https://www.gutenberg.org/ebooks/16328.txt.utf-8",
            "https://www.gutenberg.org/ebooks/4363.txt.utf-8",
            "https://www.gutenberg.org/files/28054/28054-0.txt",
            "https://www.gutenberg.org/ebooks/19942.txt.utf-8",
            "https://www.gutenberg.org/files/60559/60559-0.txt",
            "https://www.gutenberg.org/files/46/46-0.txt",
            "https://www.gutenberg.org/ebooks/147.txt.utf-8",
            "https://www.gutenberg.org/files/100/100-0.txt",
            "https://www.gutenberg.org/ebooks/3296.txt.utf-8",
            "https://www.gutenberg.org/files/1184/1184-0.txt",
            "https://www.gutenberg.org/files/2554/2554-0.txt",
            "https://www.gutenberg.org/files/60553/60553-0.txt",
            "https://www.gutenberg.org/ebooks/815.txt.utf-8",
            "https://www.gutenberg.org/ebooks/972.txt.utf-8",
            "https://www.gutenberg.org/ebooks/1001.txt.utf-8",
            "https://www.gutenberg.org/ebooks/2542.txt.utf-8",
            "https://www.gutenberg.org/ebooks/345.txt.utf-8",
            "https://www.gutenberg.org/files/2814/2814-0.txt",
            "https://www.gutenberg.org/files/158/158-0.txt",
            "https://www.gutenberg.org/ebooks/16643.txt.utf-8",
            "https://www.gutenberg.org/files/3600/3600-0.txt",
            "https://www.gutenberg.org/files/4517/4517-0.txt",
            "https://www.gutenberg.org/files/84/84-0.txt",
            "https://www.gutenberg.org/files/1400/1400-0.txt",
            "https://www.gutenberg.org/files/2591/2591-0.txt",
            "https://www.gutenberg.org/files/829/829-0.txt",
            "https://www.gutenberg.org/files/219/219-0.txt",
            "https://www.gutenberg.org/files/2852/2852-0.txt",
            "https://www.gutenberg.org/ebooks/45502.txt.utf-8",
            "https://www.gutenberg.org/ebooks/6130.txt.utf-8",
            "https://www.gutenberg.org/ebooks/844.txt.utf-8",
            "https://www.gutenberg.org/ebooks/11030.txt.utf-8",
            "https://www.gutenberg.org/ebooks/15399.txt.utf-8",
            "https://www.gutenberg.org/ebooks/1635.txt.utf-8",
            "https://www.gutenberg.org/ebooks/1260.txt.utf-8",
            "https://www.gutenberg.org/files/140/140-0.txt",
            "https://www.gutenberg.org/ebooks/27827.txt.utf-8",
            "https://www.gutenberg.org/files/1251/1251-0.txt",
            "https://www.gutenberg.org/files/1322/1322-0.txt",
            "https://www.gutenberg.org/files/41/41-0.txt",
            "https://www.gutenberg.org/files/135/135-0.txt",
            "https://www.gutenberg.org/ebooks/3207.txt.utf-8",
            "https://www.gutenberg.org/files/521/521-0.txt",
            "https://www.gutenberg.org/ebooks/514.txt.utf-8",
            "https://www.gutenberg.org/ebooks/5200.txt.utf-8",
            "https://www.gutenberg.org/files/2701/2701-0.txt",
            "https://www.gutenberg.org/files/59328/59328-0.txt",
            "https://www.gutenberg.org/files/1080/1080-0.txt",
            "https://www.gutenberg.org/ebooks/851.txt.utf-8",
            "https://www.gutenberg.org/ebooks/23.txt.utf-8",
            "https://www.gutenberg.org/ebooks/34901.txt.utf-8",
            "https://www.gutenberg.org/files/60555/60555-0.txt",
            "https://www.gutenberg.org/files/16/16-0.txt",
            "https://www.gutenberg.org/ebooks/174.txt.utf-8",
            "https://www.gutenberg.org/files/1342/1342-0.txt",
            "https://www.gutenberg.org/ebooks/1232.txt.utf-8",
            "https://www.gutenberg.org/ebooks/5827.txt.utf-8",
            "https://www.gutenberg.org/files/58585/58585-0.txt",
            "https://www.gutenberg.org/ebooks/3825.txt.utf-8",
            "https://www.gutenberg.org/ebooks/1497.txt.utf-8",
            "https://www.gutenberg.org/files/25344/25344-0.txt",
            "https://www.gutenberg.org/ebooks/7370.txt.utf-8",
            "https://www.gutenberg.org/ebooks/161.txt.utf-8",
            "https://www.gutenberg.org/ebooks/2500.txt.utf-8",
            "https://www.gutenberg.org/files/1934/1934-0.txt",
            "https://www.gutenberg.org/ebooks/408.txt.utf-8",
            "https://www.gutenberg.org/files/43/43-0.txt",
            "https://www.gutenberg.org/files/244/244-0.txt",
            "https://www.gutenberg.org/files/98/98-0.txt",
            "https://www.gutenberg.org/ebooks/833.txt.utf-8",
            "https://www.gutenberg.org/files/35/35-0.txt",
            "https://www.gutenberg.org/ebooks/779.txt.utf-8",
            "https://www.gutenberg.org/files/120/120-0.txt",
            "https://www.gutenberg.org/files/209/209-0.txt",
            "https://www.gutenberg.org/files/4300/4300-0.txt",
            "https://www.gutenberg.org/files/203/203-0.txt",
            "https://www.gutenberg.org/ebooks/60545.txt.utf-8",
            "https://www.gutenberg.org/files/205/205-0.txt",
            "https://www.gutenberg.org/files/2600/2600-0.txt",
            "https://www.gutenberg.org/files/36/36-0.txt",
            "https://www.gutenberg.org/ebooks/55.txt.utf-8",
            "https://www.gutenberg.org/files/2148/2148-0.txt",
            "https://www.gutenberg.org/ebooks/25525.txt.utf-8",
            "https://www.gutenberg.org/ebooks/768.txt.utf-8",
            "https://www.gutenberg.org/files/1952/1952-0.txt"};


    LinearLayout lLDownloaded;
    LinearLayout lLPossible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //new ReadTask().execute(bookTitles);

        lLDownloaded=findViewById(R.id.lL_downloaded);
        lLPossible=findViewById(lL_possible);

        for (int i=0; i<listTitles.length;i++){
            //downloadedBooks.add(listTitles[i]);
        }
        updateDownloadedLayout();


        //new AsyncGetBook().execute( "The Adventures of Sherlock Holmes", "https://www.gutenberg.org/files/1661/1661-0.txt" );
    }


    //update downloaded layout
    public void updateDownloadedLayout(){
        lLDownloaded.removeAllViews();
        lLPossible.removeAllViews();
        for (int i =0; i<downloadedBooks.size();i++){
            addToLlList(downloadedBooks.get(i),lLDownloaded);
        }

        for (int i =0; i<listTitles.length;i++){
            addToLlList(listTitles[i],lLPossible);
        }
    }

    //add button to linearList
    public void addToLlList(final String title, final LinearLayout layout){

        final Button thistext = new Button(this);
        thistext.setText(title);

        layout.addView(thistext);

        //if layout is lLdownloaded


        //if layout islLPossible
        thistext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int index =layout.indexOfChild(thistext);
                new AsyncGetBook().execute(title,listURLs[indexOf(title,listTitles)]);
                downloadedBooks.add(title);
                Toast.makeText( getApplicationContext(), title, Toast.LENGTH_LONG ).show();
                updateDownloadedLayout();
            }
        });
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
