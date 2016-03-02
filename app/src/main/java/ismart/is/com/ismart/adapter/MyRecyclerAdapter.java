package ismart.is.com.ismart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ismart.is.com.ismart.R;
import ismart.is.com.ismart.RoundedTransformation;
import ismart.is.com.ismart.model.Post;
import ismart.is.com.ismart.model.listMain;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ContactViewHolder> {

    private ArrayList<Post> contactList = new ArrayList<>();
    Context context;
    private static OnItemClickListener mItemClickListener;
    public MyRecyclerAdapter(Context context, ArrayList<Post> contactList) {
        this.context =context;
        this.contactList = contactList;
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
       // listMain ci = contactList.get(i);
        Post item =  contactList.get(i);
        contactViewHolder.title_tv.setText(item.getPost().get(i).getTitle());
        contactViewHolder.textView11.setText(item.getPost().get(i).getCount());
        Picasso.with(context)
                .load(item.getPost().get(i).getFile_img())
                .fit().centerCrop()
                .into(contactViewHolder.image_detail);

        Picasso.with(context)
                .load(item.getPost().get(i).getFile_img())
                .centerCrop()
                .resize(200, 200)
                .transform(new RoundedTransformation(100, 4))
                .into(contactViewHolder.profile_avatar);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_feed_photo, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title_tv,textView11;
        ImageView image_detail,profile_avatar;
        Button btn_shares;

        public ContactViewHolder(View v) {
            super(v);
            title_tv = (TextView) v.findViewById(R.id.title_tv);
            image_detail = (ImageView) v.findViewById(R.id.image_detail);
            profile_avatar = (ImageView) v.findViewById(R.id.profile_avatar);
            textView11 = (TextView) v.findViewById(R.id.textView11);
            btn_shares = (Button) v.findViewById(R.id.btn_shares);
            image_detail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_detail:
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }

            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}