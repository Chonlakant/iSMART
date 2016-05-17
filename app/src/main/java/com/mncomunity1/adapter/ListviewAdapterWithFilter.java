package com.mncomunity1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.mncomunity1.R;
import com.mncomunity1.listeners.IFilteredListListener;
import com.mncomunity1.model.IFilterableItem;
import com.mncomunity1.model.Post;
import com.mncomunity1.model.impl.ListFilter;

import java.util.List;

public class ListviewAdapterWithFilter extends BaseAdapter implements IFilteredListListener<Post.PostEntity>, IFilterableItem<Post.PostEntity> {

	private Context context;
	private List<Post.PostEntity> list;
	private ListFilter<Post.PostEntity> filter;
	private Boolean filterByTitle;

	public ListviewAdapterWithFilter(Context context, List<Post.PostEntity> listContent) {
		this.context = context;
		this.list = listContent;
		this.filterByTitle = true;
		this.filter = new ListFilter<Post.PostEntity>(list, this, this, this);
	}

	// ------------------------
	// --------BaseAdapter
	// ------------------------

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder") //TODO
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.list_recycleview, parent, false);
		Post.PostEntity task = list.get(position);
		TextView title = (TextView) itemView.findViewById(R.id.title_tv);
		title.setText(task.getTitle());
		return itemView;
	}

	public void changeFilterType(Boolean filterByTitle) {
		//We change the criterion for filtering
		this.filterByTitle = filterByTitle;
		//We have to notify filter component that filters has changed
		this.filter.filterTypeChanged();
	}

	@Override
	public Filter getFilter() {
		return filter;
	}

	@Override
	public void onSearchResult(List<Post.PostEntity> objects) {
		list = objects;
	}

	@Override
	public String getStringForFilter(Post.PostEntity item) {
		//This gets the String for filters.
		//Depending on our UI events, business logic etc. it should
		//return the appropriate String to filter for.
		
		if (filterByTitle) {
			return item.getTitle();
		} else {
			return item.getTitle();
		}
	}

}