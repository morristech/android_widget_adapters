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
package universum.studios.android.samples.widget.adapter.ui.module.slection;

import android.graphics.Color;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Albedinsky
 */
final class Colors {

	@NonNull
	static List<Integer> dataSet() {
		final List<Integer> colors = new ArrayList<>(10);
		colors.add(Color.parseColor("#F44336"));
		colors.add(Color.parseColor("#E91E63"));
		colors.add(Color.parseColor("#9C27B0"));
		colors.add(Color.parseColor("#673AB7"));
		colors.add(Color.parseColor("#3F51B5"));
		colors.add(Color.parseColor("#2196F3"));
		colors.add(Color.parseColor("#673AB7"));
		colors.add(Color.parseColor("#03A9F4"));
		colors.add(Color.parseColor("#00BCD4"));
		colors.add(Color.parseColor("#009688"));
		colors.add(Color.parseColor("#4CAF50"));
		colors.add(Color.parseColor("#8BC34A"));
		colors.add(Color.parseColor("#CDDC39"));
		colors.add(Color.parseColor("#FFEB3B"));
		colors.add(Color.parseColor("#FFC107"));
		colors.add(Color.parseColor("#FF9800"));
		colors.add(Color.parseColor("#FF5722"));
		colors.add(Color.parseColor("#795548"));
		colors.add(Color.parseColor("#9E9E9E"));
		colors.add(Color.parseColor("#607D8B"));
		return colors;
	}
}
