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
package universum.studios.android.samples.widget.adapter.ui.module.header;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Martin Albedinsky
 */
final class Cities {

	@NonNull
	static List<City> dataSet() {
		final List<City> cities = new ArrayList<>(10);
		cities.add(new City("Bratislava"));
		cities.add(new City("Budapest"));
		cities.add(new City("Tokyo"));
		cities.add(new City("Rome"));
		cities.add(new City("Barcelona"));
		cities.add(new City("Paris"));
		cities.add(new City("Wien"));
		cities.add(new City("Moscow"));
		cities.add(new City("Milan"));
		cities.add(new City("Oslo"));
		cities.add(new City("New York"));
		cities.add(new City("Prague"));
		cities.add(new City("Berlin"));
		cities.add(new City("Toronto"));
		Collections.sort(cities, City.ALPHABETIC_COMPARATOR);
		return cities;
	}
}
