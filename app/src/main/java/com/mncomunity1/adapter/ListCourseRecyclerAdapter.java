package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.mncomunity1.model.Post;
import com.mncomunity1.model.listMain;
import com.mncomunity1t.R;

public class ListCourseRecyclerAdapter extends RecyclerView.Adapter<ListCourseRecyclerAdapter.ContactViewHolder> {

    private List<listMain> contactList;
    Context context;
    ArrayList<Post> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;
    public ListCourseRecyclerAdapter(Context context, ArrayList<Post> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Post item = list.get(i);
        contactViewHolder.title_tv.setText(item.getPost().get(i).getTitle());
        contactViewHolder.title_count.setText(item.getPost().get(i).getCount());
//        Picasso.with(context)
//                .load(item.getPost().get(i).getFile_img())
//                .fit().centerCrop()
//                .into(contactViewHolder.image_logo);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.list_recycleview, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image_logo;
        TextView title_tv,title_count;
        CardView card_view;

        public ContactViewHolder(View v) {
            super(v);
            image_logo = (ImageView) v.findViewById(R.id.image_logo);
            title_tv = (TextView) v.findViewById(R.id.title_tv);
            card_view = (CardView) v.findViewById(R.id.card_view);
            title_count = (TextView) v.findViewById(R.id.title_count);
            v.setOnClickListener(this);
           // image_logo.setOnClickListener(this);
            card_view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card_view:
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }

            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

    }

    public void SetOnItemVideiosClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}