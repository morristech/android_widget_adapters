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

/**
 * A convenience interface specifying common API for adapters with data set.
 *
 * @param <Item> Model that represents a data structure of the adapter's data set.
 * @author Martin Albedinsky
 */
public interface DataSetAdapter<Item> extends DataSet<Item> {

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
	 * @see #getItemId(int)
	 */
	boolean hasStableIds();

	/**
	 * Saves the current state of this adapter.
	 *
	 * @return Saved state of this adapter or an <b>empty</b> state if this adapter does not need to
	 * save its state.
	 */
	@NonNull
	Parcelable saveInstanceState();

	/**
	 * Restores the previous state, saved via {@link #saveInstanceState()}, of this adapter.
	 *
	 * @param savedState Should be the same state as obtained via {@link #saveInstanceState()} before.
	 */
	void restoreInstanceState(@NonNull Parcelable savedState);
}
