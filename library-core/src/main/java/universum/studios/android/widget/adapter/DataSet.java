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

import android.support.annotation.NonNull;

/**
 * Interface specifying API for data sets that contain a simple set of items.
 *
 * @param <I> Item model that represents data structure of the data provided by this data set.
 * @author Martin Albedinsky
 */
public interface DataSet<I> {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Constant that identifies invalid/unspecified position.
	 */
	int NO_POSITION = -1;

	/**
	 * Constant that identifies invalid/unspecified id.
	 */
	long NO_ID = -1;

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Registers a callback to be invoked when a data change occurs in this data set.
	 *
	 * @param listener The desired listener callback to register.
	 * @see #unregisterOnDataChangeListener(OnDataChangeListener)
	 */
	void registerOnDataChangeListener(@NonNull OnDataChangeListener listener);

	/**
	 * Unregisters the given callback from the data change listeners, so it will not receive any
	 * callbacks further.
	 *
	 * @param listener The desired listener callback to unregister.
	 * @see #registerOnDataChangeListener(OnDataChangeListener)
	 */
	void unregisterOnDataChangeListener(@NonNull OnDataChangeListener listener);

	/**
	 * Registers a callback to be invoked when a data set event occurs. The occurred event may be a
	 * local change in the data set, or an invalidation of the data set.
	 *
	 * @param listener The desired listener callback to register.
	 * @see #unregisterOnDataSetListener(OnDataSetListener)
	 */
	void registerOnDataSetListener(@NonNull OnDataSetListener listener);

	/**
	 * Unregisters the given callback from the data set listeners, so it will not receive any
	 * callbacks further.
	 *
	 * @param listener The desired listener callback to unregister.
	 * @see #registerOnDataSetListener(OnDataSetListener)
	 */
	void unregisterOnDataSetListener(@NonNull OnDataSetListener listener);

	/**
	 * Registers a callback to be invoked when a specific data set action is selected within this
	 * data set.
	 *
	 * @param listener The desired listener callback to register.
	 * @see #unregisterOnDataSetActionListener(OnDataSetActionListener)
	 */
	void registerOnDataSetActionListener(@NonNull OnDataSetActionListener listener);

	/**
	 * Unregisters the given callback from the data set action listeners, so it will not receive any
	 * callbacks further.
	 *
	 * @param listener The desired listener callback to unregister.
	 * @see #registerOnDataSetActionListener(OnDataSetActionListener)
	 */
	void unregisterOnDataSetActionListener(@NonNull OnDataSetActionListener listener);

	/**
	 * Returns a boolean flag indicating whether this data set is empty or not.
	 *
	 * @return {@code True} if cursor attached to this data set has no rows or it is {@code null},
	 * {@code false} otherwise.
	 * @see #getItemCount()
	 */
	boolean isEmpty();

	/**
	 * Returns the count of items available within this data set.
	 *
	 * @return Count of rows of the attached cursor or {@code 0} if no cursor is attached or it has
	 * no rows.
	 * @see #isEmpty()
	 * @see #hasItemAt(int)
	 * @see #getItem(int)
	 */
	int getItemCount();

	/**
	 * Returns a boolean flag indicating whether this data set has item that can provide data for the
	 * specified <var>position</var> or not.
	 *
	 * @param position The position of item to check.
	 * @return {@code True} if {@link #getItem(int)} can be called 'safely', {@code false} otherwise.
	 */
	boolean hasItemAt(int position);

	/**
	 * Returns the item (model) containing data from this data set for the specified <var>position</var>.
	 *
	 * @param position Position of the item to obtain.
	 * @return Item with data for the requested position.
	 * @throws IndexOutOfBoundsException If the specified position is out of bounds of the current
	 *                                   data set.
	 * @see #hasItemAt(int)
	 * @see #getItemId(int)
	 */
	@NonNull
	I getItem(int position);

	/**
	 * Returns the ID of the item at the specified <var>position</var>.
	 *
	 * @param position The position of item of which id to obtain.
	 * @return Item's id or {@link #NO_ID} if there is no item at the specified position.
	 * @see #hasItemAt(int)
	 * @see #getItem(int)
	 */
	long getItemId(int position);
}
