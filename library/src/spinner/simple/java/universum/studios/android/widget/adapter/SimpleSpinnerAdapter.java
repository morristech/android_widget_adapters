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
 * A {@link BaseSpinnerAdapter} implementation which specifies simple API and structure to manage data
 * set of custom items for {@link android.widget.Spinner} widget. This adapter supports changing of
 * the current data set by {@link #changeItems(Object[])} and obtaining an item for a requested position
 * by {@link #getItem(int)} methods.
 * <p>
 * In the simplest implementation case of this adapter, only  {@link #onCreateView(android.view.ViewGroup, int)}
 * and {@link #onBindViewHolder(Object, int)} methods are need to be implemented to take a full advantage
 * of this adapter class. The simplest scenario assumes that both spinner view and drop down view
 * are the same, if not also {@link #onCreateDropDownView(android.view.ViewGroup, int)} and
 * {@link #onBindDropDownViewHolder(Object, int)} need to be implemented as well.
 *
 * @param <Item> A type of the item presented within a data set of a subclass of this SimpleSpinnerAdapter.
 * @param <VH>   A type of the view holder used within a subclass of this SimpleSpinnerAdapter.
 * @param <DVH>   A type of the drop down view holder used within a subclass of this SimpleSpinnerAdapter.
 * @author Martin Albedinsky
 */
public abstract class SimpleSpinnerAdapter<Item, VH, DVH> extends BaseSpinnerAdapter<Item, VH, DVH> {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "SimpleSpinnerAdapter";

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
	 * Creates a new instance of SimpleSpinnerAdapter.
	 *
	 * @param context Context in which will be this adapter used.
	 */
	public SimpleSpinnerAdapter(@NonNull Context context) {
		super(context);
	}

	/**
	 * Same as {@link #SimpleSpinnerAdapter(Context, List)} so the given
	 * <var>items</var> array will be converted to List.
	 */
	public SimpleSpinnerAdapter(@NonNull Context context, @NonNull Item[] items) {
		this(context, Arrays.asList(items));
	}

	/**
	 * Creates a new instance of SimpleSpinnerAdapter with the given <var>items</var> data set.
	 *
	 * @param context Context in which will be this adapter used.
	 * @param items   List of items to be used as initial data set for this adapter.
	 */
	public SimpleSpinnerAdapter(@NonNull Context context, @NonNull List<Item> items) {
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
	 * Same as {@link #changeItems(List)} so the given <var>items</var> array will be converted
	 * to List.
	 */
	public void changeItems(@NonNull Item[] items) {
		changeItems(Arrays.asList(items));
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
							"Current data set is in size of(" + itemsCount + ") items."
			);
		}
		return mItems.get(position);
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
