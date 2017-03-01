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
 * Listener which receives callbacks that may be used to listen for events before and after data
 * change for a specific adapter.
 * <p>
 * This listener may be useful for example when there need to be saved and restored current scroll
 * position of a collection view for which a specific adapter provides its data set. Then in such
 * scenario we can save the current scroll position in {@link #onDataChange(Object, Object)} and
 * restore it in {@link #onDataChanged(Object, Object)}.
 * <p>
 * Both these callbacks should be guarantied to be called by the associated adapter if this listener
 * is attached to it.
 *
 * @param <A> Type of the adapter to which will be this data change listener attached.
 * @author Martin Albedinsky
 */
public interface OnDataChangeListener<A, D> {

	/**
	 * Invoked whenever the specified <var>data</var> are about to be changed in the given <var>adapter</var>.
	 * <p>
	 * During duration of this call, the adapter still references the previous data.
	 *
	 * @param adapter The adapter of which data are about to be changed.
	 * @param data    The data to be changed. May be {@code null}.
	 */
	void onDataChange(@NonNull A adapter, @Nullable D data);

	/**
	 * Invoked after {@link #onDataChange(Object, Object)} callback has been fired and data change
	 * has been finished.
	 * <p>
	 * When this callback is fired, the adapter already references the changed data.
	 *
	 * @param adapter The adapter where the data has been changed.
	 * @param data    The changed data. May be {@code null}.
	 */
	void onDataChanged(@NonNull A adapter, @Nullable D data);
}
