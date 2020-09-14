package sur.cas.edu.mpashonews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class News_MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<News>> {

    /**
     * Adapter for the list of news
     */
    private NewsAdapter mAdapter;

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_UPDATE_LOADER_ID = 1;


    //using UriBuilder to
    private static final String GUARDIAN_API_REQUEST = "http://content.guardianapis.com/search?";

    public static final String LOG_TAG = News_MainActivity.class.getName();

    ProgressBar mProgressBar;
    TextView mTextView;
    ListView newsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        newsListView = (ListView) findViewById(R.id.list);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        mTextView = (TextView) findViewById(R.id.text_view);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_UPDATE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            mProgressBar.setVisibility(View.GONE);

            //make the text visible instead
            mTextView.setVisibility(View.VISIBLE);

            // Update empty state with no connection error message
            mTextView.setText(R.string.no_internet);
        }

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected news.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current news that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri webUri = Uri.parse(currentNews.getWebUrl());

                // Create a new intent to view the web URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, webUri);
                //Before sending your implicit intent, you must check if the user's device has an app
                // that can handle it, otherwise, the call will fail and your app will crash.

                if (websiteIntent.resolveActivity(getPackageManager()) != null) {
                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);  //where intent is your intent
               }
            }
        });
    }

    //Uri builder to construct the URL string to pass into the Loader constructor from the API url
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(GUARDIAN_API_REQUEST);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, the `format=json`
        uriBuilder.appendQueryParameter("api-key", "test");
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        //log to check the wuery being passed on
        Log.i("query url", uriBuilder.toString(), null);
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        //mAdapter = new NewsAdapter(this, data);
        mProgressBar.setVisibility(View.GONE);
        newsListView.setAdapter(mAdapter);

        // Set mTextView to display "No news found " in case the url is invalid/empty
        mTextView.setText(R.string.no_news);
        // Clear the adapter of previous news data
        mAdapter.clear();

        // If there is a valid list of {@link News}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            //if news feed is available, then hide the textView no news articles available
            mTextView.setVisibility(View.GONE);


            //load all news data into the list view
            mAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}