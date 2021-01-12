package com.example.androidassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidassignment.MainActivity;
import com.example.androidassignment.R;
import com.example.androidassignment.model.Books;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Books> {

    private ArrayList<Books> dataList;
    private MainActivity activity;

    private static class ViewHolder {
        TextView title;
        TextView author;
        TextView publisher;
        TextView contributor;
        TextView description;
    }

    public CustomAdapter(ArrayList<Books> data, MainActivity activity) {
        super(activity, R.layout.row_item, data);
        this.dataList = data;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Books books = dataList.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
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
}
