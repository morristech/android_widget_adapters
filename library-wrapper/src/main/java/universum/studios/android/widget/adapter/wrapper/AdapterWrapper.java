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
package universum.studios.android.widget.adapter.wrapper;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

/**
 * A {@link WrapperListAdapter} implementation that may be used to wrap instance of {@link ListAdapter}.
 *
 * @author Martin Albedinsky
 */
public class AdapterWrapper implements WrapperListAdapter {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "AdapterWrapper";

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
	 * Wrapped instance of ListAdapter.
	 */
	protected final ListAdapter mAdapter;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Creates a new instance of AdapterWrapper which wraps the given <var>adapter</var>.
	 *
	 * @param adapter An instance of the adapter to be wrapped. May be {@code null} to create empty
	 *                adapter wrapper.
	 */
	public AdapterWrapper(@Nullable ListAdapter adapter) {
		this.mAdapter = adapter;
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 */
	@Nullable
	@Override
	public ListAdapter getWrappedAdapter() {
		return mAdapter;
	}

	/**
	 */
	@Override
	public void registerDataSetObserver(@NonNull DataSetObserver observer) {
		if (mAdapter != null) mAdapter.registerDataSetObserver(observer);
	}

	/**
	 */
	@Override
	public void unregisterDataSetObserver(@NonNull DataSetObserver observer) {
		if (mAdapter != null) mAdapter.unregisterDataSetObserver(observer);
	}

	/**
	 */
	@Override
	public boolean isEmpty() {
		return mAdapter == null || mAdapter.isEmpty();
	}

	/**
	 */
	@Override
	public int getCount() {
		return mAdapter == null ? 0 : mAdapter.getCount();
	}

	/**
	 */
	@Nullable
	@Override
	public Object getItem(int position) {
		return mAdapter == null ? null : mAdapter.getItem(position);
	}

	/**
	 */
	@Override
	public boolean hasStableIds() {
		return mAdapter != null && mAdapter.hasStableIds();
	}

	/**
	 */
	@Override
	public long getItemId(int position) {
		return mAdapter == null ? -1 : mAdapter.getItemId(position);
	}

	/**
	 */
	@Override
	public int getViewTypeCount() {
		return mAdapter == null ? 0 : mAdapter.getViewTypeCount();
	}

	/**
	 */
	@Override
	public int getItemViewType(int position) {
		return mAdapter == null ? 0 : mAdapter.getItemViewType(position);
	}

	/**
	 */
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		return mAdapter == null ? null : mAdapter.getView(position, convertView, parent);
	}

	/**
	 */
	@Override
	public boolean areAllItemsEnabled() {
		return mAdapter != null && mAdapter.areAllItemsEnabled();
	}

	/**
	 */
	@Override
	public boolean isEnabled(int position) {
		return mAdapter != null && mAdapter.isEnabled(position);
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
