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
package universum.studios.android.samples.widget.adapter.ui.spinner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import universum.studios.android.samples.widget.adapter.R;
import universum.studios.android.samples.widget.adapter.databinding.ItemListSimpleBinding;
import universum.studios.android.widget.adapter.SimpleSpinnerAdapter;
import universum.studios.android.widget.adapter.ViewHolder;

/**
 * @author Martin Albedinsky
 */
final class SampleSpinnerAdapter extends SimpleSpinnerAdapter<String, SampleSpinnerAdapter.ItemHolder, SampleSpinnerAdapter.ItemHolder> {

	@SuppressWarnings("unused")
	private static final String TAG = "SampleSpinnerAdapter";

	SampleSpinnerAdapter(@NonNull Context context) {
		super(context);
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull ViewGroup parent, int position) {
		return inflate(R.layout.item_list_simple, parent);
	}

	@Nullable
	@Override
	protected ItemHolder onCreateViewHolder(@NonNull View itemView, int position) {
		return new ItemHolder(itemView);
	}

	@Override
	protected void onUpdateViewHolder(@NonNull ItemHolder holder, @NonNull String item, int position) {
		holder.binding.setText(item);
		holder.itemView.setAlpha(getSelectedItemPosition() == position ? 0.5f : 1.0f);
	}

	final class ItemHolder extends ViewHolder {

		final ItemListSimpleBinding binding;

		ItemHolder(@NonNull View itemView) {
			super(itemView);
			this.binding = ItemListSimpleBinding.bind(itemView);
		}
	}
}
