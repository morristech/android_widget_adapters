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
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Extended version of {@link android.support.v7.widget.RecyclerView.Adapter} that supports features
 * like {@link BaseAdapter}.
 *
 * @param <Item> A type of the item presented within a data set of a subclass of this BaseRecyclerAdapter.
 * @param <VH> A type of the view holder used within a subclass of this BaseRecyclerAdapter.
 * @author Martin Albedinsky
 * @see SimpleRecyclerAdapter
 */
public abstract class BaseRecyclerAdapter<Item, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements DataSetAdapter<Item> {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "BaseRecyclerAdapter";

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Context in which will be this adapter used.
	 */
	protected final Context mContext;

	/**
	 * Layout inflater used to inflate new views for this adapter.
	 */
	protected final LayoutInflater mLayoutInflater;

	/**
	 * Application resources.
	 */
	protected final Resources mResources;

	/**
	 * Registered OnDataSetListener callback.
	 */
	private OnDataSetListener mDataSetListener;

	/**
	 * Registered OnDataSetActionListener callback.
	 */
	private OnDataSetActionListener mDataSetActionListener;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Creates a new instance of BaseAdapter within the given <var>context</var>.
	 *
	 * @param context Context in which will be this adapter used.
	 */
	public BaseRecyclerAdapter(@NonNull Context context) {
		this.mContext = context;
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mResources = context.getResources();
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Called to save the current state of this adapter.
	 *
	 * @return Saved state of this adapter or an <b>empty</b> state if this adapter does not need to
	 * save its state.
	 */
	@NonNull
	public Parcelable saveInstanceState() {
		return onSaveInstanceState();
	}

	/**
	 * Invoked immediately after {@link #saveInstanceState()} was called, to save the current
	 * state of this adapter.
	 * <p>
	 * If you decide to override this method, do not forget to call {@code super.onSaveInstanceState()}
	 * and pass super state obtained from the super to constructor of your {@link AdapterSavedState}
	 * implementation with such a parameter to ensure the state of all classes along the chain is saved.
	 *
	 * @return Return here your implementation of {@link AdapterSavedState} if you want to save state
	 * of your adapter, otherwise no implementation of this method is necessary.
	 */
	@NonNull
	protected Parcelable onSaveInstanceState() {
		return AdapterSavedState.EMPTY_STATE;
	}

	/**
	 * Called to restore a previous state, saved by {@link #saveInstanceState()}, of this adapter.
	 *
	 * @param savedState Should be the same state as obtained via {@link #saveInstanceState()} before.
	 */
	public void restoreInstanceState(@NonNull Parcelable savedState) {
		onRestoreInstanceState(savedState);
	}

	/**
	 * Called immediately after {@link #restoreInstanceState(Parcelable)} was called,
	 * to restore a previous state, (saved in {@link #onSaveInstanceState()}), of this adapter.
	 *
	 * @param savedState Before saved state of this adapter.
	 */
	protected void onRestoreInstanceState(@NonNull Parcelable savedState) {
	}

	/**
	 */
	@Override
	public void setOnDataSetListener(@Nullable OnDataSetListener listener) {
		this.mDataSetListener = listener;
	}

	/**
	 */
	@Override
	public void setOnDataSetActionListener(@Nullable OnDataSetActionListener listener) {
		this.mDataSetActionListener = listener;
	}

	/**
	 */
	/*@Override
	@SuppressWarnings("unchecked")
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		if (mDataSetListener != null) mDataSetListener.onDataSetChanged(this);
	}*/

	/**
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void notifyDataSetInvalidated() {
		if (mDataSetListener != null) mDataSetListener.onDataSetInvalidated(this);
	}

	/**
	 * Notifies, that the given <var>action</var> has been performed for the specified <var>position</var>.
	 * <p>
	 * If {@link #onDataSetActionSelected(int, int, Object)} will not process this call, the current
	 * {@link OnDataSetActionListener} will be notified if it is presented.
	 *
	 * @param action   Action to be dispatched.
	 * @param position The position for which was the given action performed.
	 * @param data     Additional data for the selected action to be dispatched to the listener.
	 */
	@SuppressWarnings("unchecked")
	protected void notifyDataSetActionSelected(int action, int position, @Nullable Object data) {
		if (!onDataSetActionSelected(action, position, data)) {
			if (mDataSetActionListener != null) mDataSetActionListener.onDataSetActionSelected(
					this, action, position, getItemId(position), data
			);
		}
	}

	/**
	 * Invoked immediately after {@link #notifyDataSetActionSelected(int, int, Object)} was called.
	 *
	 * @return {@code True} to indicate that this event was processed here, otherwise the current
	 * {@link OnDataSetActionListener} will be notified about this event if it is presented.
	 */
	protected boolean onDataSetActionSelected(int action, int position, @Nullable Object data) {
		return false;
	}

	/**
	 */
	@Override
	public boolean isEmpty() {
		return getItemCount() == 0;
	}

	/**
	 */
	@Override
	public boolean hasItem(int position) {
		return position >= 0 && position < getItemCount();
	}

	/**
	 */
	@Override
	public long getItemId(int position) {
		if (!hasStableIds()) return NO_ID;
		else return position;
	}

	/**
	 */
	@Override
	public boolean isEnabled(int position) {
		return true;
	}

	/**
	 */
	@Override
	public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

	/**
	 * Inflates a new view hierarchy from the given xml resource.
	 *
	 * @param resource Resource id of a view to inflate.
	 * @param parent   A parent view, to resolve correct layout params for the newly creating view.
	 * @return The root view of the inflated view hierarchy.
	 * @see LayoutInflater#inflate(int, ViewGroup)
	 */
	@NonNull
	protected final View inflate(@LayoutRes int resource, @NonNull ViewGroup parent) {
		return mLayoutInflater.inflate(resource, parent, false);
	}

	/**
	 */
	@Override
	public abstract void onBindViewHolder(@NonNull VH viewHolder, int position);

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * Default {@link android.support.v7.widget.RecyclerView.ViewHolder} implementation used as default
	 * holder for purpose of {@link #onCreateViewHolder(ViewGroup, int)} method.
	 *
	 * @author Martin Albedinsky
	 */
	public static class SimpleViewHolder extends RecyclerView.ViewHolder {

		/**
		 * Creates a new instance of SimpleViewHolder for the specified <var>itemView</var>.
		 *
		 * @param itemView The view for which to create the new holder.
		 */
		public SimpleViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
