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

import android.support.annotation.Nullable;

import java.util.List;

/**
 * A {@link DataSetAdapter} interface extension for simple adapters that provide set of items
 * as theirs data set.
 *
 * @author Martin Albedinsky
 */
public interface ItemsAdapter<I> extends DataSetAdapter<I> {

	/**
	 * Same as {@link #swapItems(List)} without returning the old data set of items.
	 *
	 * @param items The desired items to be changed. May be {@code null} to clear the current ones.
	 * @see #getItems()
	 */
	void changeItems(@Nullable List<I> items);

	/**
	 * Changes items data set of this adapter and returns the old items data set.
	 *
	 * @param items The desired items to be changed. May be {@code null} to clear the current ones.
	 * @return The old items data set or {@code null} if this adapter does not have items data set
	 * specified.
	 * @see #getItems()
	 */
	@Nullable
	List<I> swapItems(@Nullable List<I> items);

	/**
	 * Returns the current items data set of this adapter.
	 *
	 * @return This adapter's items data set or {@code null} if there is no items data set provided
	 * by this adapter.
	 * @see #changeItems(List)
	 * @see #swapItems(List)
	 */
	@Nullable
	List<I> getItems();
}
