package ismart.is.com.ismart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ismart.is.com.ismart.R;
import ismart.is.com.ismart.model.Post;
import ismart.is.com.ismart.model.listMain;

public class MyCourseRecyclerAdapter extends RecyclerView.Adapter<MyCourseRecyclerAdapter.ContactViewHolder> {

    Context context;
    ArrayList<Post> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;
    public MyCourseRecyclerAdapter(Context context, ArrayList<Post> list) {
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
//        Picasso.with(context)
//                .load(item.getPost().get(i).getFile_img())
//                .fit().centerCrop()
//                .into(contactViewHolder.image_logo);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_course_recycleview, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image_logo;
        TextView title_tv;

        public ContactViewHolder(View v) {
            super(v);
            image_logo = (ImageView) v.findViewById(R.id.image_logo);
            title_tv = (TextView) v.findViewById(R.id.title_tv);
            v.setOnClickListener(this);
            image_logo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_logo:
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