package com.example.androidassignment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.androidassignment.MainActivity;
import com.example.androidassignment.R;
import com.example.androidassignment.model.Books;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter implements Filterable {

    private ArrayList<Books> bookList;
    private ArrayList<Books> filteredList;
    private ArrayList<Books> originalList;

    private MainActivity activity;

    private static class ViewHolder {
        TextView title;
        TextView author;
        TextView publisher;
        TextView contributor;
        TextView description;
    }

    public CustomAdapter(ArrayList<Books> data, MainActivity activity) {
        this.bookList = data;
        this.originalList = data;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Books books = bookList.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(activity);
            convertView  = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.title       = convertView.findViewById(R.id.title);
            viewHolder.author      = convertView.findViewById(R.id.author);
            viewHolder.publisher   = convertView.findViewById(R.id.publisher);
            viewHolder.contributor = convertView.findViewById(R.id.contributor);
            viewHolder.description = convertView.findViewById(R.id.description);

            viewHolder.title.setText(books.getTitle());
            viewHolder.author.setText(books.getAuthor());
            viewHolder.publisher.setText(books.getPublisher());
            viewHolder.contributor.setText(books.getContributor());
            viewHolder.description.setText(books.getDescription());

            convertView.setTag(viewHolder);
        }
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                if (results.count == 0 || constraint.toString().equals("")) {
                    bookList = originalList;
                    notifyDataSetChanged();
                } else {
                    bookList = (ArrayList<Books>) results.values;
                    notifyDataSetChanged();
                }
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                filteredList = new ArrayList<>();

                constraint = constraint.toString().toLowerCase();
                if(constraint.length() == 0){
                    results.values = bookList;
                    results.count  = bookList.size();
                } else {
                    for (int i = 0; i < bookList.size(); i++) {
                        Books books = bookList.get(i);
                        if (books.getTitle().toLowerCase().contains(constraint.toString()) ||
                                books.getAuthor().toLowerCase().contains(constraint.toString()) ||
                                books.getPublisher().toLowerCase().contains(constraint.toString()) ||
                                books.getContributor().toLowerCase().contains(constraint.toString()) ||
                                books.getDescription().toLowerCase().contains(constraint.toString())) {

                            filteredList.add(books);
                            results.count  = filteredList.size();
                            results.values = filteredList;
                        }
                    }
                }
                return results;
            }
        };
    }
}
