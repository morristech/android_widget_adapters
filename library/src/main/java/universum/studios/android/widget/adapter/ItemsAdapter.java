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
 * todo: description
 *
 * @author Martin Albedinsky
 */
public interface ItemsAdapter<Item> extends DataSetAdapter<Item> {

	/**
	 * todo:
	 *
	 * @param items
	 */
	void changeItems(@Nullable List<Item> items);

	/**
	 * todo:
	 *
	 * @param items
	 * @return
	 */
	@Nullable
	List<Item> swapItems(@Nullable List<Item> items);

	/**
	 * todo:
	 *
	 * @return
	 */
	@Nullable
	List<Item> getItems();
}
