/*
 * =================================================================================================
 *                             Copyright (C) 2017 Universum Studios
 * =================================================================================================
 *         Licensed under the Apache License, Version 2.0 or later (further "License" only).
 * -------------------------------------------------------------------------------------------------
 * You may use this file only in compliance with the License. More details and copy of this License 
 * you may obtain at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * You can redistribute, modify or publish any part of the code written within this file but as it 
 * is described in the License, the software distributed under the License is distributed on an 
 * "AS IS" BASIS, WITHOUT WARRANTIES or CONDITIONS OF ANY KIND.
 * 
 * See the License for the specific language governing permissions and limitations under the License.
 * =================================================================================================
 */
package universum.studios.android.samples.widget.adapter.ui.module.header;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import universum.studios.android.samples.widget.adapter.R;
import universum.studios.android.widget.adapter.SimpleRecyclerAdapter;
import universum.studios.android.widget.adapter.module.AdapterModule;
import universum.studios.android.widget.adapter.module.AlphabeticHeaders;

/**
 * @author Martin Albedinsky
 */
final class CitiesAdapter extends SimpleRecyclerAdapter<City, RecyclerView.ViewHolder> implements AdapterModule.ModuleAdapter {

	@SuppressWarnings("unused")
	private static final String TAG = "CitiesAdapter";

	private static final int VIEW_TYPE_HEADER = 0;
	private static final int VIEW_TYPE_ITEM = 1;

	private final AlphabeticHeaders mHeaders;

	CitiesAdapter(@NonNull Context context) {
		super(context);
		this.mHeaders = new AlphabeticHeaders();
		this.mHeaders.attachToAdapter(this);
	}

	@Override
	protected boolean onItemsChange(@Nullable List<City> newItems, @Nullable List<City> oldItems) {
		mHeaders.clearHeaders();
		if (newItems != null) {
			mHeaders.fromAlphabeticList(newItems);
		}
		return super.onItemsChange(newItems, oldItems);
	}

	@Override
	public int getItemCount() {
		return super.getItemCount() + mHeaders.size();
	}

	@NonNull
	@Override
	public City getItem(int position) {
		return super.getItem(mHeaders.correctPosition(position));
	}

	@Override
	public int getItemViewType(int position) {
		return mHeaders.isHeaderAt(position) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case VIEW_TYPE_HEADER: return new SimpleRecyclerAdapter.SimpleViewHolder(inflate(R.layout.header_list, parent));
			default: return new SimpleRecyclerAdapter.SimpleViewHolder(inflate(R.layout.item_list_simple, parent));
		}
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
		switch (viewHolder.getItemViewType()) {
			case VIEW_TYPE_HEADER:
				((TextView) viewHolder.itemView).setText(mHeaders.getHeader(position).getText());
				break;
			case VIEW_TYPE_ITEM:
				((TextView) viewHolder.itemView).setText(getItem(position).getText());
				break;
		}
	}
}
