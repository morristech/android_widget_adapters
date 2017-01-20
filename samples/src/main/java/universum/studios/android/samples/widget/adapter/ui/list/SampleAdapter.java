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
package universum.studios.android.samples.widget.adapter.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import universum.studios.android.samples.widget.adapter.R;
import universum.studios.android.samples.widget.adapter.databinding.ItemListWithActionBinding;
import universum.studios.android.widget.adapter.SimpleAdapter;
import universum.studios.android.widget.adapter.ViewHolder;

/**
 * @author Martin Albedinsky
 */
final class SampleAdapter extends SimpleAdapter<String, SampleAdapter.ItemHolder> {

	@SuppressWarnings("unused")
	private static final String TAG = "SampleAdapter";
	static final int ACTION = 0x01;

	SampleAdapter(@NonNull Context context) {
		super(context);
	}

	void removeItemAt(int position) {
		final List<String> items = getItems();
		if (items != null) {
			items.remove(position);
			notifyDataSetChanged();
		}
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull ViewGroup parent, int position) {
		return inflate(R.layout.item_list_with_action, parent);
	}

	@Nullable
	@Override
	protected ItemHolder onCreateViewHolder(@NonNull View itemView, int position) {
		return new ItemHolder(itemView);
	}

	@Override
	protected void onBindViewHolder(@NonNull ItemHolder holder, int position) {
		holder.binding.setText(getItem(position));
		holder.binding.executePendingBindings();
	}

	final class ItemHolder extends ViewHolder implements View.OnClickListener {

		final ItemListWithActionBinding binding;

		ItemHolder(@NonNull View itemView) {
			super(itemView);
			this.binding = ItemListWithActionBinding.bind(itemView);
			itemView.findViewById(R.id.action).setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.action:
					notifyDataSetActionSelected(ACTION, getAdapterPosition(), null);
					break;
			}
		}
	}
}
