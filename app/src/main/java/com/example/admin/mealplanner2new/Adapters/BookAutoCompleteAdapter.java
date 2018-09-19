package com.example.admin.mealplanner2new.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.example.admin.mealplanner2new.Models.CityList;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;
import java.util.List;

public class BookAutoCompleteAdapter extends BaseAdapter implements Filterable {


    private Context mContext;
    private List<CityList> resultList = new ArrayList<CityList>();
    private ArrayList<CityList> productInfoArrayList;

    public BookAutoCompleteAdapter(Context context, ArrayList<CityList> productInfoArrayList) {
        mContext = context;
        this.productInfoArrayList = productInfoArrayList;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public CityList getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_city_name, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.tvCityName)).setText(getItem(position).getCity());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<CityList> books = findBooks(mContext, constraint.toString());


                    // Assign the data to the FilterResults
                    filterResults.values = books;
                    filterResults.count = books.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<CityList>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    /**
     * Returns a search result for the given book title.
     */
    private List<CityList> findBooks(Context context, String bookTitle) {
        // GoogleBooksProtocol is a wrapper for the Google Books API

        ArrayList<CityList> arrayList = new ArrayList<>();

        for (CityList model : productInfoArrayList) {
            final String text = model.getCity().toLowerCase().trim();

            if (text.contains(bookTitle.trim())) {
                arrayList.add(model);
            }
        }


        return arrayList;
    }
}