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

import universum.studios.android.widget.adapter.BaseAdapter;
import universum.studios.android.widget.adapter.SimpleSpinnerAdapter;

/**
 * A {@link BaseAdapter} implementation to provide optimized adapter also for {@link android.widget.Spinner}
 * widget. This "spinner based" adapter also supports API to store current selected item. The position
 * of selected item is stored whenever {@link #getView(int, View, ViewGroup)}
 * is called. Position of the current selected item can be obtained via {@link #getSelectedPosition()}.
 *
 * @param <Item> A type of the item presented within a data set of a subclass of this BaseSpinnerAdapter.
 * @param <VH>   A type of the view holder used within a subclass of this BaseSpinnerAdapter.
 * @param <DVH>  A type of the drop down view holder used within a subclass of this BaseSpinnerAdapter.
 * @author Martin Albedinsky
 * @see SimpleSpinnerAdapter
 */
public abstract class BaseSpinnerAdapter<Item, VH, DVH> extends BaseAdapter<Item, VH> {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "BaseSpinnerAdapter";

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
	 * Returns the currently selected item.
	 *
	 * @return An item obtained via {@link #getItem(int)} for the current selected position.
	 * @see #getSelectedPosition()
	 */
	@Nullable
	public Item getSelectedItem() {
		return getItem(mSelectedPosition);
	}

	/**
	 * Returns the currently selected position.
	 *
	 * @return Position of item that is currently selected within a related Spinner widget.
	 * @see #getSelectedItem()
	 */
	public int getSelectedPosition() {
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
			// Dispatch to create new drop down view.
			convertView = onCreateDropDownView(parent, position);
			// Resolve holder for the newly created drop down view.
			final Object holder = onCreateDropDownViewHolder(convertView, position);
			if (holder != null) {
				convertView.setTag(viewHolder = holder);
			} else {
				viewHolder = convertView;
			}
		} else {
			final Object holder = convertView.getTag();
			viewHolder = holder != null ? holder : convertView;
		}
		ensureViewHolderPosition(viewHolder, position);
		// Dispatch to bind drop down view with data.
		onBindDropDownViewHolder((DVH) viewHolder, position);
		return convertView;
	}

	/**
	 * Invoked to create a drop down view for an item from the current data set at the specified position.
	 * <p>
	 * This is invoked only if <var>convertView</var> for the specified <var>position</var> in
	 * {@link #getDropDownView(int, View, ViewGroup)} was {@code null}.
	 * <p>
	 * Default implementation invokes {@link #onCreateView(ViewGroup, int)}.
	 *
	 * @param parent   A parent view, to resolve correct layout params for the newly creating view.
	 * @param position Position of the item from the current data set for which should be a new drop
	 *                 down view created.
	 * @return New instance of the requested view.
	 * @see #inflate(int, ViewGroup)
	 */
	@NonNull
	protected View onCreateDropDownView(@NonNull ViewGroup parent, int position) {
		return onCreateView(parent, position);
	}

	/**
	 * Invoked to create a view holder for a drop down view of an item from the current data set at
	 * the specified position.
	 * <p>
	 * This is invoked only if <var>convertView</var> for the specified <var>position</var> in
	 * {@link #getDropDownView(int, View, ViewGroup)} was {@code null},
	 * so as view also holder need to be created.
	 *
	 * @param itemView An instance of the same view as obtained from {@link #onCreateDropDownView(ViewGroup, int)}
	 *                 for the specified position.
	 * @param position Position of the item from the current data set for which should be a new drop
	 *                 down view holder created.
	 * @return New instance of the requested drop down view holder.
	 */
	@Nullable
	@SuppressWarnings("unchecked")
	protected DVH onCreateDropDownViewHolder(@NonNull View itemView, int position) {
		return (DVH) onCreateViewHolder(itemView, position);
	}

	/**
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void onBindViewHolder(@NonNull VH viewHolder, int position) {
		final Item selectedItem = getSelectedItem();
		if (selectedItem != null) onUpdateViewHolder((DVH) viewHolder, selectedItem, position);
	}

	/**
	 * Invoked to set up and populate a drop down view of an item from the current data set at the
	 * specified position. This is invoked whenever {@link #getDropDownView(int, View, ViewGroup)}
	 * is called.
	 * <p>
	 * <b>Note</b>, that if {@link #onCreateDropDownViewHolder(View, int)} returns {@code null}
	 * for the specified <var>position</var> here passed <var>viewHolder</var> will be the view created
	 * by {@link #onCreateDropDownView(ViewGroup, int)} for the specified position or
	 * just recycled view for such a position. This approach can be used, when a view hierarchy of
	 * the specific spinner drop down item is represented by one custom view, where such a view
	 * represents a holder for all its child views.
	 * <p>
	 * By default this will call {@link #onUpdateViewHolder(Object, Object, int)} with an item obtained via
	 * {@link #getItem(int)} for the specified position.
	 *
	 * @param viewHolder An instance of the same holder as provided by {@link #onCreateDropDownViewHolder(View, int)}
	 *                   for the specified position or converted view as holder as described above.
	 * @param position   Position of the item from the current data set of which drop down view to
	 *                   set up.
	 */
	protected void onBindDropDownViewHolder(@NonNull DVH viewHolder, int position) {
		onUpdateViewHolder(viewHolder, getItem(position), position);
	}

	/**
	 * Invoked to update views within the given <var>viewHolder</var> with data of the given <var>item</var>.
	 *
	 * @param viewHolder An instance of the same holder as provided by {@link #onCreateDropDownViewHolder(View, int)}
	 *                   or {@link #onCreateViewHolder(View, int)} for the specified position.
	 * @param item       Always valid item obtained via {@link #getItem(int)} for the specified position.
	 * @param position   Position of the item from the current data set.
	 */
	protected abstract void onUpdateViewHolder(@NonNull DVH viewHolder, @NonNull Item item, int position);

	/**
	 * Inner classes ===============================================================================
	 */
}
