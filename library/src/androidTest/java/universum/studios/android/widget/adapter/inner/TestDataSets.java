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
package universum.studios.android.widget.adapter.inner;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Albedinsky
 */
public final class TestDataSets {

	@SuppressWarnings("unused")
	private static final String TAG = "TestDataSets";

	@NonNull
	public static List<String> textItems(int count) {
		final List<String> items = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			items.add("Test item no. " + (i + 1));
		}
		return items;
	}
}
