package ismart.is.com.ismart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ismart.is.com.ismart.R;
import ismart.is.com.ismart.model.listMain;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ContactViewHolder> {

    private List<listMain> contactList;
    Context context;
    String[] title;
    String[] imageUrl;

    public MyRecyclerAdapter(Context context, String[] title, String[] imageUrl) {
        this.context =context;
        this.title = title;
        this.imageUrl = imageUrl;

    }


    @Override
    public int getItemCount() {
        return title.length;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
       // listMain ci = contactList.get(i);
        contactViewHolder.title_tv.setText(title[i]);
        Picasso.with(context)
                .load(imageUrl[i])
                .fit().centerCrop()
                .into(contactViewHolder.image_detail);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_layout_main, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView title_tv;
        ImageView image_detail;

        public ContactViewHolder(View v) {
            super(v);
            title_tv = (TextView) v.findViewById(R.id.title_tv);
            image_detail = (ImageView) v.findViewById(R.id.image_detail);
        }
    }
}