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
import android.view.View;
import android.view.ViewGroup;

/**
 * Holder for a single item view of a specific {@link BaseAdapter} implementation that may be used
 * to support the optimized <b>holder</b> pattern for {@link BaseAdapter#getView(int, View, ViewGroup)}
 * method.
 *
 * @author Martin Albedinsky
 */
public class ViewHolder {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "ViewHolder";

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * View with which has been this holder created.
	 */
	public final View itemView;

	/**
	 * Position of an item from associated adapter's data set of which view is hold by this holder
	 * instance.
	 */
	private int mAdapterPosition = DataSetAdapter.NO_POSITION;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Creates a new instance of ViewHolder for the given <var>itemView</var>.
	 *
	 * @param itemView Instance of view to be hold by the holder.
	 */
	public ViewHolder(@NonNull View itemView) {
		this.itemView = itemView;
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Updates the current position of this holder instance.
	 *
	 * @param position The new adapter position for this holder.
	 * @see #getAdapterPosition()
	 */
	final void updateAdapterPosition(int position) {
		this.mAdapterPosition = position;
	}

	/**
	 * Returns the current position of this holder instance.
	 *
	 * @return The position of item within associated adapter's data set of which view is hold by
	 * this holder or {@link DataSetAdapter#NO_POSITION} if no position has been specified yet.
	 */
	public final int getAdapterPosition() {
		return mAdapterPosition;
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
