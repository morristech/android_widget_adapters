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
 * Listener which receives callbacks about changed and invalidated adapter's data set.
 *
 * @param <Adapter> A type of the Adapter to which will be this data set listener attached.
 * @author Martin Albedinsky
 */
public interface OnDataSetListener<Adapter> {

	/**
	 * Invoked whenever the current data set within the passed <var>adapter</var> was changed.
	 *
	 * @param adapter An instance of the adapter of which current data set was just changed.
	 */
	void onDataSetChanged(@NonNull Adapter adapter);

	/**
	 * Invoked whenever the current data set within the passed <var>adapter</var> was invalidated.
	 *
	 * @param adapter An instance of the adapter of which current data set was just invalidated.
	 */
	void onDataSetInvalidated(@NonNull Adapter adapter);
}
