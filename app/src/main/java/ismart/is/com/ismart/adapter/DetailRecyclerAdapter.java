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
import ismart.is.com.ismart.RoundedTransformation;
import ismart.is.com.ismart.model.Post;
import ismart.is.com.ismart.model.PostDetail;
import ismart.is.com.ismart.model.listMain;

public class DetailRecyclerAdapter extends RecyclerView.Adapter<DetailRecyclerAdapter.ContactViewHolder> {

    private List<Post> contactList;
    Context context;


    public DetailRecyclerAdapter(Context context, List<Post> contactList) {
        this.context = context;
        this.contactList = contactList;

    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Post ci = contactList.get(i);
        contactViewHolder.txt_username.setText(ci.getPost().get(i).getTitle());
        contactViewHolder.txt_content.setText(ci.getPost().get(i).getDetails());

        if(ci.getPost().get(i).getFile_img() != null){
            Picasso.with(context)
                    .load(ci.getPost().get(i).getFile_img())
                    .centerCrop()
                    .resize(200, 200)
                    .transform(new RoundedTransformation(100, 4))
                    .into(contactViewHolder.imageView3);
        }else{

        }

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_comment, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView txt_username,txt_content;
        ImageView imageView3;

        public ContactViewHolder(View v) {
            super(v);
            txt_username = (TextView) v.findViewById(R.id.txt_username);
            imageView3 = (ImageView) v.findViewById(R.id.imageView3);
            txt_content = (TextView) v.findViewById(R.id.txt_content);
        }
    }
}