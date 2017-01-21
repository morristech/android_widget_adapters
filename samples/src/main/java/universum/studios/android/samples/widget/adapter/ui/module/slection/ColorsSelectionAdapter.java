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
package universum.studios.android.samples.widget.adapter.ui.module.slection;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import universum.studios.android.samples.widget.adapter.R;
import universum.studios.android.samples.widget.adapter.databinding.ItemListColorBinding;
import universum.studios.android.widget.adapter.AdapterSavedState;
import universum.studios.android.widget.adapter.AdaptersConfig;
import universum.studios.android.widget.adapter.SimpleRecyclerAdapter;
import universum.studios.android.widget.adapter.module.AdapterModule;
import universum.studios.android.widget.adapter.module.SelectionModule;

/**
 * @author Martin Albedinsky
 */
final class ColorsSelectionAdapter extends SimpleRecyclerAdapter<Integer, ColorsSelectionAdapter.ItemHolder> implements AdapterModule.ModuleAdapter {

	@SuppressWarnings("unused")
	private static final String TAG = "ColorsSelectionAdapter";

	private static final int CLICK = 0;

	private final SelectionModule mSelection;

	ColorsSelectionAdapter(@NonNull Context context) {
		super(context);
		this.mSelection = new SelectionModule();
		this.mSelection.attachToAdapter(this);
	}

	void setSelectionMode(@SelectionModule.SelectionMode int mode) {
		mSelection.setMode(mode);
	}

	@SelectionModule.SelectionMode
	int getSelectionMode() {
		return mSelection.getMode();
	}

	@Override
	public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ItemHolder(inflate(R.layout.item_list_color, parent));
	}

	@Override
	public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
		final int color = getItem(position);
		holder.binding.setText(colorName(color, false));
		holder.binding.setColor(color);
		holder.binding.setSelected(mSelection.isSelected(getItemId(position)));
		holder.binding.executePendingBindings();
	}

	private static String colorName(@ColorInt int color, boolean withAlpha) {
		final String aHex = Integer.toString(Color.alpha(color), 16);
		final String rHex = Integer.toString(Color.red(color), 16);
		final String gHex = Integer.toString(Color.green(color), 16);
		final String bHex = Integer.toString(Color.blue(color), 16);
		String name = (withAlpha) ? (aHex.length() == 2 ? aHex : "0" + aHex) : "";
		name += rHex.length() == 2 ? rHex : "0" + rHex;
		name += gHex.length() == 2 ? gHex : "0" + gHex;
		name += bHex.length() == 2 ? bHex : "0" + bHex;
		return ("#" + name);
	}

	@Override
	protected boolean onDataSetActionSelected(int action, int position, @Nullable Object payload) {
		switch (action) {
			case CLICK:
				mSelection.toggleSelection(getItemId(position));
				return true;
		}
		return super.onDataSetActionSelected(action, position, payload);
	}

	@NonNull
	@Override
	public Parcelable saveInstanceState() {
		final SavedState state = new SavedState(super.saveInstanceState());
		state.selectionState = mSelection.saveInstanceState();
		return state;
	}

	@Override
	public void restoreInstanceState(@NonNull Parcelable savedState) {
		if (!(savedState instanceof SavedState)) {
			super.restoreInstanceState(savedState);
			return;
		}
		final SavedState state = (SavedState) savedState;
		super.restoreInstanceState(state.getSuperState());
		if (state.selectionState != null) {
			mSelection.restoreInstanceState(state.selectionState);
		}
	}

	final class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		final ItemListColorBinding binding;

		ItemHolder(View itemView) {
			super(itemView);
			this.binding = ItemListColorBinding.bind(itemView);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			notifyDataSetActionSelected(CLICK, getAdapterPosition(), null);
		}
	}

	static final class SavedState extends AdapterSavedState {

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

			@Override
			public SavedState createFromParcel(Parcel source) {
				return new SavedState(source);
			}

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};

		private Parcelable selectionState;

		SavedState(@NonNull Parcelable superState) {
			super(superState);
		}

		SavedState(@NonNull Parcel source) {
			super(source);
			this.selectionState = source.readParcelable(AdaptersConfig.class.getClassLoader());
		}

		@Override
		public void writeToParcel(@NonNull Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeParcelable(selectionState, flags);
		}
	}
}
