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
 * A {@link BaseAdapter} implementation which specifies simple API and structure to manage data set
 * of custom items. This adapter supports changing of the current data set via {@link #changeItems(Object[])}
 * and obtaining an item for a requested position via {@link #getItem(int)}.
 * <p>
 * In the simplest implementation case of this adapter, only {@link #onCreateView(android.view.ViewGroup, int)}
 * and {@link #onBindViewHolder(Object, int)} methods are required to be implemented to take a full
 * advantage of this adapter class.
 *
 * @param <Item> A type of the item presented within a data set of a subclass of this SimpleAdapter.
 * @param <VH>   A type of the view holder used within a subclass of this SimpleAdapter.
 * @author Martin Albedinsky
 */
public abstract class SimpleAdapter<Item, VH> extends BaseAdapter<Item, VH> {

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
	 * Data set of this adapter.
	 */
	private List<Item> mItems;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Creates a new instance of SimpleAdapter.
	 *
	 * @param context Context in which will be this adapter used.
	 */
	public SimpleAdapter(@NonNull Context context) {
		super(context);
	}

	/**
	 * Same as {@link #SimpleAdapter(Context, List)} so the given <var>items</var>
	 * array will be converted to List.
	 */
	public SimpleAdapter(@NonNull Context context, @NonNull Item[] items) {
		this(context, Arrays.asList(items));
	}

	/**
	 * Creates a new instance of SimpleAdapter with the given <var>items</var> data set.
	 *
	 * @param context Context in which will be this adapter used.
	 * @param items   List of items to be used as initial data set for this adapter.
	 */
	public SimpleAdapter(@NonNull Context context, @NonNull List<Item> items) {
		super(context);
		this.mItems = items;
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Returns the current data set of this adapter.
	 *
	 * @return Data set of this adapter or {@code null} if there is no data set presented within
	 * this adapter.
	 */
	@Nullable
	public List<Item> getItems() {
		return mItems;
	}

	/**
	 * Like {@link #changeItems(List)}, but this will also return the old data set.
	 */
	@Nullable
	public List<Item> swapItems(@Nullable List<Item> items) {
		final List<Item> oldItems = mItems;
		changeItems(items);
		return oldItems;
	}

	/**
	 * Same as {@link #changeItems(List)} so the given <var>items</var> array will be converted
	 * to List.
	 */
	public void changeItems(@NonNull Item[] items) {
		changeItems(Arrays.asList(items));
	}

	/**
	 * Changes the current data set of this adapter.
	 * <p>
	 * This will also notify data set change if the given <var>items</var> are valid, otherwise will
	 * notify data set invalidation.
	 *
	 * @param items A set of items to set as the current data set for this adapter.
	 * @see #swapItems(List)
	 * @see #clearItems()
	 */
	public void changeItems(@Nullable List<Item> items) {
		this.mItems = items;
		if (items != null) {
			notifyDataSetChanged();
		} else {
			notifyDataSetInvalidated();
		}
	}

	/**
	 * Clears the current data set of this adapter.
	 * <p>
	 * This will also notify data set change.
	 */
	public void clearItems() {
		if (mItems != null) {
			mItems.clear();
			notifyDataSetChanged();
		}
	}

	/**
	 */
	@Override
	public int getItemCount() {
		return mItems != null ? mItems.size() : 0;
	}

	/**
	 */
	@NonNull
	@Override
	public Item getItem(int position) {
		final int itemsCount = getItemCount();
		if (position < 0 || position >= itemsCount) {
			throw new IndexOutOfBoundsException(
					"Requested item at invalid position(" + position + "). " +
							"Current data set has items in count of(" + itemsCount + ")."
			);
		}
		return mItems.get(position);
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
