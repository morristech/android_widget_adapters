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
package universum.studios.android.widget.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * A {@link BaseSpinnerAdapter} implementation which specifies simple API for data set management of
 * items. This adapter supports changing of the current data set via {@link #changeItems(List)} and
 * obtaining an item for a desired position via {@link #getItem(int)}. All items attached to the
 * adapter may be obtained via {@link #getItems()}.
 * <p>
 * In the simplest implementation case of this adapter, only {@link #onCreateView(android.view.ViewGroup, int)}
 * and {@link #onBindViewHolder(Object, int)} methods are required to be implemented to take a full
 * advantage of this adapter class. The simplest scenario assumes that both spinner view and drop down
 * view are the same, if not also {@link #onCreateDropDownView(android.view.ViewGroup, int)} and
 * {@link #onBindDropDownViewHolder(Object, int)} need to be implemented as well.
 *
 * @param <I>   Type of the item presented within a data set of a subclass of this SimpleSpinnerAdapter.
 * @param <VH>  Type of the view holder used within a subclass of this SimpleSpinnerAdapter.
 * @param <DVH> Type of the drop down view holder used within a subclass of this SimpleSpinnerAdapter.
 * @author Martin Albedinsky
 */
public abstract class SimpleSpinnerAdapter<I, VH, DVH> extends BaseSpinnerAdapter<I, VH, DVH> implements ItemsAdapter<I> {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "SimpleSpinnerAdapter";

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Creates a new instance of SimpleSpinnerAdapter without initial items data set.
	 *
	 * @param context Context in which will be this adapter used.
	 * @see #SimpleSpinnerAdapter(Context, Object[])
	 * @see #SimpleSpinnerAdapter(Context, List)
	 */
	public SimpleSpinnerAdapter(@NonNull Context context) {
		super(context);
	}

	/**
	 * Same as {@link #SimpleSpinnerAdapter(Context, List)} for array of initial <var>items</var>
	 * data set.
	 */
	public SimpleSpinnerAdapter(@NonNull Context context, @NonNull I[] items) {
		this(context, Arrays.asList(items));
	}

	/**
	 * Creates a new instance of SimpleSpinnerAdapter with the given initial <var>items</var>
	 * data set.
	 *
	 * @param context Context in which will be this adapter used.
	 * @param items   List of items to be used as initial data set for this adapter.
	 */
	public SimpleSpinnerAdapter(@NonNull Context context, @NonNull List<I> items) {
		super(context);
		mDataSet.attachData(items);
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 */
	@Override
	public void changeItems(@Nullable List<I> items) {
		swapItems(items);
	}

	/**
	 */
	@Nullable
	@Override
	public List<I> swapItems(@Nullable List<I> items) {
		final List<I> oldData = mDataSet.getData();
		if (items == null) {
			mDataSet.notifyDataChange(null);
			mDataSet.attachData(null);
			if (!onItemsChange(null, oldData)) {
				notifyDataSetChanged();
			}
		} else {
			mDataSet.notifyDataChange(items);
			mDataSet.attachData(items);
			if (!onItemsChange(items, oldData)) {
				notifyDataSetChanged();
			}
		}
		mDataSet.notifyDataChanged(items);
		return oldData;
	}

	/**
	 * Called from {@link #swapItems(List)} in order to handle change in items of this adapter.
	 * <p>
	 * <b>Note</b>, that during this call this adapter has already the new items attached.
	 * <p>
	 * This implementation does nothing.
	 *
	 * @param newItems The new items data set for this adapter.
	 * @param oldItems The old items data set of this adapter that has been replaced by the new one.
	 * @return {@code True} if change has been handled and appropriate callbacks has been fired to
	 * registered observers, {@code false} if default {@link #notifyDataSetChanged()} should be invoked.
	 */
	protected boolean onItemsChange(@Nullable List<I> newItems, @Nullable List<I> oldItems) {
		return false;
	}

	/**
	 */
	@Nullable
	@Override
	public List<I> getItems() {
		return mDataSet.getData();
	}

	/**
	 */
	@Override
	public int getItemCount() {
		return mDataSet.getItemCount();
	}

	/**
	 */
	@Override
	public boolean hasItemAt(int position) {
		return mDataSet.hasItemAt(position);
	}

	/**
	 */
	@NonNull
	@Override
	public I getItem(int position) {
		return mDataSet.getItem(position);
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
