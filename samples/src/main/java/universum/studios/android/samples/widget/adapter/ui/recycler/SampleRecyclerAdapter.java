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
package universum.studios.android.samples.widget.adapter.ui.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import universum.studios.android.samples.widget.adapter.R;
import universum.studios.android.samples.widget.adapter.databinding.ItemListWithActionBinding;
import universum.studios.android.widget.adapter.SimpleRecyclerAdapter;

/**
 * @author Martin Albedinsky
 */
final class SampleRecyclerAdapter extends SimpleRecyclerAdapter<String, SampleRecyclerAdapter.ItemHolder> {

	@SuppressWarnings("unused")
	private static final String TAG = "SampleRecyclerAdapter";

	static final int CLICK = 0x00;
	static final int ACTION = 0x01;

	SampleRecyclerAdapter(@NonNull Context context) {
		super(context);
	}

	void removeItemAt(int position) {
		final List<String> items = getItems();
		if (items != null) {
			items.remove(position);
			notifyItemRemoved(position);
		}
	}

	@Override
	public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ItemHolder(inflate(R.layout.item_list_with_action, parent));
	}

	@Override
	public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
		holder.binding.setText(getItem(position));
		holder.binding.executePendingBindings();
	}

	final class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		final ItemListWithActionBinding binding;

		ItemHolder(@NonNull View itemView) {
			super(itemView);
			this.binding = ItemListWithActionBinding.bind(itemView);
			itemView.setOnClickListener(this);
			itemView.findViewById(R.id.action).setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.action:
					notifyDataSetActionSelected(ACTION, getAdapterPosition(), null);
					break;
				default:
					if (view.equals(itemView)) {
						notifyDataSetActionSelected(CLICK, getAdapterPosition(), null);
					}
			}
		}
	}
}
