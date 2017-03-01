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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/**
 * A {@link BaseAdapter} implementation that may be used to provide optimized adapter also for
 * {@link android.widget.Spinner Spinner} widget. This "spinner based" adapter also supports API to
 * store current selected item and its corresponding position. The position of the selected item is
 * stored whenever {@link #getView(int, View, ViewGroup)} is called. The selected item position may
 * be obtained via {@link #getSelectedItemPosition()}.
 *
 * @param <I>   Type of the item presented within a data set of a subclass of this BaseSpinnerAdapter.
 * @param <VH>  Type of the view holder used within a subclass of this BaseSpinnerAdapter.
 * @param <DVH> Type of the drop down view holder used within a subclass of this BaseSpinnerAdapter.
 * @author Martin Albedinsky
 */
public abstract class BaseSpinnerAdapter<I, VH, DVH> extends BaseAdapter<I, VH> {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "BaseSpinnerAdapter";

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Currently selected position.
	 */
	private int mSelectedPosition;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Creates a new instance of BaseSpinnerAdapter within the given <var>context</var>.
	 *
	 * @param context Context in which will be this adapter used.
	 */
	public BaseSpinnerAdapter(@NonNull Context context) {
		super(context);
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Returns the item model of this adapter for the current selected position.
	 *
	 * @return Item obtained via {@link #getItem(int)} for the current selected position.
	 * @see #getSelectedItemPosition()
	 */
	@NonNull
	public I getSelectedItem() {
		return getItem(mSelectedPosition);
	}

	/**
	 * Returns the position of the currently selected item.
	 *
	 * @return Position of item that is currently selected within a related Spinner widget or
	 * {@link #NO_POSITION} if this adapter does not have its data set specified.
	 * @see #getSelectedItem()
	 */
	public int getSelectedItemPosition() {
		return mSelectedPosition;
	}

	/**
	 */
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		this.mSelectedPosition = position;
		return super.getView(position, convertView, parent);
	}

	/**
	 * Performs optimized algorithm for this method using the <b>Holder</b> pattern.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		Object viewHolder;
		if (convertView == null) {
			convertView = onCreateDropDownView(parent, position);
			final Object holder = onCreateDropDownViewHolder(convertView, position);
			if (holder == null) {
				viewHolder = convertView;
			} else {
				convertView.setTag(viewHolder = holder);
			}
		} else {
			final Object holder = convertView.getTag();
			viewHolder = holder == null ? convertView : holder;
		}
		ensureViewHolderPosition(viewHolder, position);
		onBindDropDownViewHolder((DVH) viewHolder, position);
		return convertView;
	}

	/**
	 * Invoked to create a drop down view for an item from the current data set at the specified
	 * <var>position</var>.
	 * <p>
	 * This is invoked only if <var>convertView</var> for the specified <var>position</var> in
	 * {@link #getDropDownView(int, View, ViewGroup)} was {@code null}.
	 *
	 * @param parent   A parent view, to resolve correct layout params for the newly creating view.
	 * @param position Position of the item from the current data set for which should be a new drop
	 *                 down view created.
	 * @return New instance of the requested drop down view.
	 * @see #inflate(int, ViewGroup)
	 */
	@NonNull
	protected View onCreateDropDownView(@NonNull ViewGroup parent, int position) {
		return onCreateView(parent, position);
	}

	/**
	 * Invoked to create a view holder for a drop down view of an item from the current data set at
	 * the specified <var>position</var>.
	 * <p>
	 * This is invoked only if <var>convertView</var> for the specified <var>position</var> in
	 * {@link #getDropDownView(int, View, ViewGroup)} was {@code null}, so drop down view along with
	 * its corresponding holder need to be created.
	 *
	 * @param itemView The same view as obtained from {@link #onCreateDropDownView(ViewGroup, int)}
	 *                 for the specified position.
	 * @param position Position of the item from the current data set for which should be a new drop
	 *                 down view holder created.
	 * @return New instance of the requested view holder or {@code null} if holder for the drop
	 * down view is not required.
	 */
	@Nullable
	@SuppressWarnings("unchecked")
	protected DVH onCreateDropDownViewHolder(@NonNull View itemView, int position) {
		return (DVH) onCreateViewHolder(itemView, position);
	}

	/**
	 * This implementation by default invokes {@link #onUpdateViewHolder(Object, I, int)} with
	 * item at the current selected position.
	 *
	 * @see #getSelectedItemPosition()
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void onBindViewHolder(@NonNull VH viewHolder, int position) {
		onUpdateViewHolder((DVH) viewHolder, getSelectedItem(), position);
	}

	/**
	 * Invoked to configure and bind a drop down view of an item from the current data set at the
	 * specified <var>position</var>. This is invoked whenever {@link #getDropDownView(int, View, ViewGroup)}
	 * is called.
	 * <p>
	 * <b>Note</b>, that if {@link #onCreateDropDownViewHolder(View, int)} returns {@code null} for
	 * the specified <var>position</var> here passed <var>viewHolder</var> will be the view created
	 * by {@link #onCreateDropDownView(ViewGroup, int)} for the specified position or just recycled
	 * view for that position. This approach may be used when a view hierarchy of a specific item is
	 * represented by single custom view, where such view represents a holder for all its child views.
	 *
	 * @param viewHolder The same holder as provided by {@link #onCreateDropDownViewHolder(View, int)}
	 *                   for the specified position or converted view as holder as described above.
	 * @param position   Position of the item from the current data set of which view to bind with data.
	 */
	protected void onBindDropDownViewHolder(@NonNull DVH viewHolder, int position) {
		onUpdateViewHolder(viewHolder, getItem(position), position);
	}

	/**
	 * Invoked to update views of the given <var>viewHolder</var> with data of the given <var>cursor</var>.
	 *
	 * @param viewHolder The view holder passed either to {@link #onBindViewHolder(Object, int)} or
	 *                   to {@link #onBindDropDownViewHolder(Object, int)}.
	 * @param item       Item from the adapter's data set at the specified position.
	 * @param position   Position of the item from the current data set of which view holder to update.
	 */
	protected abstract void onUpdateViewHolder(@NonNull DVH viewHolder, @NonNull I item, int position);

	/**
	 * Inner classes ===============================================================================
	 */
}
