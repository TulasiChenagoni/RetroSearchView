package com.mansopresk.retrosearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mansopresk.retrosearch.model.DocumentCategories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mansopresk10 on 3/13/2018.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable
{
    List<DocumentCategories> documents;
    MainActivity activity;

    private ArrayList<DocumentCategories> mArrayList;
    private ArrayList<DocumentCategories> mFilteredList;

//    public DataAdapter(ArrayList<DocumentCategories> arrayList)
//    {
//        mArrayList = arrayList;
//        mFilteredList = arrayList;
//    }


    public DataAdapter(List<DocumentCategories> documents, MainActivity activity) {
        this.documents = documents;
        this.activity = activity;
    }

    public DataAdapter()
    {
    }



    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i)
    {

        viewHolder.tv_id.setText(mFilteredList.get(i).getCat_id());
        viewHolder.tv_name.setText(mFilteredList.get(i).getCat_name());
        viewHolder.tv_docs.setText(mFilteredList.get(i).getNum_docs());

        Picasso.with(activity).load(documents.get(i).getCat_background_img()).into(viewHolder.image);
        Picasso.with(activity).load(documents.get(i).getCat_icon()).into(viewHolder.icon);
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<DocumentCategories> filteredList = new ArrayList<>();

                    for (DocumentCategories androidVersion : mArrayList) {

                        if (androidVersion.getCat_id().toLowerCase().contains(charString) || androidVersion.getCat_name().toLowerCase().contains(charString) ) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<DocumentCategories>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_id,tv_name,tv_docs;
        private ImageView icon,image;
        public ViewHolder(View view)
        {
            super(view);

            tv_id = (TextView)view.findViewById(R.id.textView1);
            tv_name = (TextView)view.findViewById(R.id.textView2);
            tv_docs = (TextView)view.findViewById(R.id.textView3);

            image = (ImageView) view.findViewById(R.id.image1);
            icon = (ImageView) view.findViewById(R.id.image2);
        }
    }

}
