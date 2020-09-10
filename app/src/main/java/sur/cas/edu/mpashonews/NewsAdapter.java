package sur.cas.edu.mpashonews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(@NonNull Context context, List<News> news) {
        super(context, 0, news);
    }

    /**
     * Returns a list item view that displays information about the news at the given position
     * in the list of news.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Find the news at the given position in the list of earthquakes
        News currentNews = getItem(position);

        // Find the TextView with view ID sectionName
        TextView sectionNameView = (TextView) listItemView.findViewById(R.id.sectionName);
        // Display the webSectionName  of the current news in that TextView
        sectionNameView.setText(currentNews.getSectionName());

        // Find the TextView with view ID webTitle
        TextView webTitleView = (TextView) listItemView.findViewById(R.id.webTitle);
        // Display the webTitle of the current news in that TextView
        webTitleView.setText(currentNews.getWebTitle());

        // Find the TextView with view ID webPublicationDate
        TextView webPublicationDateView =
                (TextView) listItemView.findViewById(R.id.webPublicationDate);
        // Display the webPublicationDate of the current news in that TextView
        webPublicationDateView.setText(currentNews.getWebPublicationDate());

        // Find the TextView with view ID author_text_view
        TextView newsAuthortextView = (TextView) listItemView.findViewById(R.id.author_text_view);
        // Display the author  of the current news in that TextView
        newsAuthortextView.setText(currentNews.getmNewsAuthor());


        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}