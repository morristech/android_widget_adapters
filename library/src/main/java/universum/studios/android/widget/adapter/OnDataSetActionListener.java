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
import android.support.annotation.Nullable;

/**
 * Listener which receives callback about selected action within adapter's data set for a specific
 * position.
 *
 * @param <Adapter> A type of the Adapter to which will be this data set action listener attached.
 * @author Martin Albedinsky
 */
public interface OnDataSetActionListener<Adapter> {

	/**
	 * Invoked whenever the specified <var>action</var> was selected for the specified <var>position</var>
	 * within the passed <var>adapter</var> in which is this callback registered.
	 *
	 * @param adapter  An instance of the adapter in which was the specified action selected.
	 * @param action   The action which was currently selected.
	 * @param position The position for which was the specified action selected.
	 * @param id       An id of an item at the specified position within the current data set of the
	 *                 passed adapter.
	 * @param data     Additional data for the selected action.
	 * @return {@code True} if the action has been handled, {@code false} otherwise.
	 */
	boolean onDataSetActionSelected(@NonNull Adapter adapter, int action, int position, long id, @Nullable Object data);
}
