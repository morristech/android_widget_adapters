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
 * A {@link BaseAdapter} implementation which specifies simple API for data set management of items.
 * This adapter supports changing of the current data set via {@link #changeItems(List)} and obtaining
 * an item for a desired position via {@link #getItem(int)}. All items attached to the adapter may
 * be obtained via {@link #getItems()}.
 * <p>
 * In the simplest implementation case of this adapter, only {@link #onCreateView(android.view.ViewGroup, int)}
 * and {@link #onBindViewHolder(Object, int)} methods are required to be implemented to take a full
 * advantage of this adapter class.
 *
 * @param <Item> Type of the item presented within a data set of a subclass of this SimpleAdapter.
 * @param <VH>   Type of the view holder used within a subclass of this SimpleAdapter.
 * @author Martin Albedinsky
 */
public abstract class SimpleAdapter<Item, VH> extends BaseAdapter<Item, VH> implements ItemsAdapter<Item> {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "SimpleAdapter";

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
	 * Creates a new instance of SimpleAdapter without initial items data set.
	 *
	 * @param context Context in which will be this adapter used.
	 * @see #SimpleAdapter(Context, Object[])
	 * @see #SimpleAdapter(Context, List)
	 */
	public SimpleAdapter(@NonNull Context context) {
		super(context);
	}

	/**
	 * Same as {@link #SimpleAdapter(Context, List)} for array of initial <var>items</var> data set.
	 */
	public SimpleAdapter(@NonNull Context context, @NonNull Item[] items) {
		this(context, Arrays.asList(items));
	}

	/**
	 * Creates a new instance of SimpleAdapter with the given initial <var>items</var> data set.
	 *
	 * @param context Context in which will be this adapter used.
	 * @param items   List of items to be used as initial data set for this adapter.
	 */
	public SimpleAdapter(@NonNull Context context, @NonNull List<Item> items) {
		super(context);
		mDataSet.attachData(items);
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 */
	@Override
	public void changeItems(@Nullable List<Item> items) {
		swapItems(items);
	}

	/**
	 */
	@Nullable
	@Override
	public List<Item> swapItems(@Nullable List<Item> items) {
		final List<Item> oldData = mDataSet.getData();
		if (items != null) {
			mDataSet.notifyDataChange(items);
			mDataSet.attachData(items);
			notifyDataSetChanged();
		} else {
			mDataSet.notifyDataChange(null);
			mDataSet.attachData(null);
			notifyDataSetChanged();
		}
		mDataSet.notifyDataChanged(items);
		return oldData;
	}

	/**
	 */
	@Nullable
	@Override
	public List<Item> getItems() {
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
	public Item getItem(int position) {
		return mDataSet.getItem(position);
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
