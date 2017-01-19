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

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Listener which receives callbacks that may be used to listen for events before and after cursor
 * change or to intercept cursor change event for a specific adapter.
 * <p>
 * This listener may be useful for example when there need to be saved and restored current scroll
 * position of a collection view for which a specific adapter provides its cursor data set. Then in
 * such scenario we can save the current scroll position in {@link #onDataChange(Object, Cursor)}
 * and restore it in {@link #onDataChanged(Object, Cursor)}.
 * <p>
 * Both these callbacks should be guarantied to be called by the associated adapter if this listener
 * is attached to it.
 *
 * @param <Adapter> Type of the adapter to which will be this data change listener attached.
 * @author Martin Albedinsky
 */
public interface OnDataChangeListener<Adapter, Data> {

	/**
	 * Invoked whenever the specified <var>cursor</var> is about to be changed in the given <var>adapter</var>.
	 * <p>
	 * During duration of this call, the adapter still references the previous cursor.
	 *
	 * @param adapter The adapter of which cursor to change.
	 * @param data  The cursor to be changed. May be {@code null}.
	 */
	void onDataChange(@NonNull Adapter adapter, @Nullable Data data);

	/**
	 * Invoked after {@link #onDataChange(Object, Cursor)} callback has been fired and cursor change
	 * has been finished.
	 * <p>
	 * When this callback is fired, the adapter already references the changed cursor.
	 *
	 * @param adapter The adapter where the cursor has been changed.
	 * @param data  The changed cursor. May be {@code null}.
	 */
	void onDataChanged(@NonNull Adapter adapter, @Nullable Data data);
}
