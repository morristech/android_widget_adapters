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

import java.util.Comparator;

import universum.studios.android.widget.adapter.module.AlphabeticHeaders;
import universum.studios.android.widget.adapter.module.HeadersModule;

/**
 * @author Martin Albedinsky
 */
final class City extends HeadersModule.SimpleHeader implements AlphabeticHeaders.AlphabeticItem {

	@SuppressWarnings("unused")
	private static final String TAG = "City";

	static final Comparator<City> ALPHABETIC_COMPARATOR = new Comparator<City>() {

		@Override
		public int compare(@NonNull City first, @NonNull City second) {
			return first.getText().toString().compareTo(second.getText().toString());
		}
	};

	City(@NonNull CharSequence name) {
		super(name);
	}
}
