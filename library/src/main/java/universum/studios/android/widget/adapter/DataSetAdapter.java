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

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * An convenience interface specifying common API for adapters with data set.
 *
 * @param <Item> Model that represents a data structure of the adapter's data set.
 * @author Martin Albedinsky
 */
public interface DataSetAdapter<Item> extends DataSet {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Constant to identify invalid position.
	 */
	int NO_POSITION = -1;

	/**
	 * Constant to identify invalid id.
	 */
	long NO_ID = -1;

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Returns a boolean flag indicating whether the data set of this adapter is empty or not.
	 *
	 * @return {@code True} if this adapter's data set is empty, {@code false} otherwise.
	 * @see #getItemCount()
	 */
	boolean isEmpty();

	/**
	 * Returns the count of items within the data set of this adapter.
	 *
	 * @return Count of items within the current data set.
	 * @see #isEmpty()
	 */
	int getItemCount();

	/**
	 * Returns a boolean flag indicating whether this adapter has item that can present data of
	 * the current data set at the specified <var>position</var> or not.
	 *
	 * @param position The position of item to check.
	 * @return {@code True} if {@link #getItem(int)} can be called 'safely', {@code false} otherwise.
	 */
	boolean hasItem(int position);

	/**
	 * Returns an item (model) with data from the current cursor data set for the specified <var>position</var>.
	 *
	 * @param position Position of the item to obtain.
	 * @return Item bound with data from the current cursor data set for the requested position.
	 * @throws IndexOutOfBoundsException If the specified position is out of bounds of the current
	 *                                   data set of this adapter.
	 * @see #hasItem(int)
	 */
	@NonNull
	Item getItem(int position);

	/**
	 * Returns the stable ID for the item at the specified <var>position</var>.
	 *
	 * @param position The position of item of which id to obtain.
	 * @return Item's id or {@link #NO_ID} if there is no item at the specified position or this
	 * adapter does not have stable ids.
	 */
	long getItemId(int position);

	/**
	 * Returns a boolean flag indicating whether an item at the specified <var>position</var> is
	 * enabled or not.
	 *
	 * @param position The position of item to check.
	 * @return {@code True} if the item is enabled, {@code false} otherwise.
	 */
	boolean isEnabled(int position);

	/**
	 * Returns a boolean flag indicating whether the data set of this adapter has stable ids or not.
	 *
	 * @return {@code True} if the data set has stable ids so each position has its unique id,
	 * {@code false} otherwise.
	 */
	boolean hasStableIds();

	/**
	 * Registers a callback to be invoked when the data set of this adapter is changed or invalidated.
	 *
	 * @param listener Listener callback. May be {@code null} to remove the current one.
	 */
	void setOnDataSetListener(@Nullable OnDataSetListener listener);

	/**
	 * Registers a callback to be invoked when there is a specific action performed above the data
	 * set of this adapter.
	 *
	 * @param listener Listener callback. May be {@code null} to remove the current one.
	 */
	void setOnDataSetActionListener(@Nullable OnDataSetActionListener listener);

	/**
	 * Notifies any registered observers that the data set of this adapter has changed.
	 * <p>
	 * This will also notify {@link OnDataSetListener} (if any).
	 */
	void notifyDataSetChanged();

	/**
	 * Notifies any registered observers that the data set of this adapter has invalidated.
	 * <p>
	 * This will also notify {@link OnDataSetListener} (if any).
	 */
	void notifyDataSetInvalidated();

	/**
	 * Saves the current state of this adapter.
	 *
	 * @return Saved state of this adapter or an <b>empty</b> state if this adapter does not need to
	 * save its state.
	 */
	@NonNull
	Parcelable saveInstanceState();

	/**
	 * Restores a previous state, saved by {@link #saveInstanceState()}, of this adapter.
	 *
	 * @param savedState Should be the same state as obtained via {@link #saveInstanceState()} before.
	 */
	void restoreInstanceState(@NonNull Parcelable savedState);
}
