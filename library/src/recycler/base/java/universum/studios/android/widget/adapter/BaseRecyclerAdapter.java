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
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Extended version of {@link android.support.v7.widget.RecyclerView.Adapter} that provides API of
 * {@link DataSetAdapter}.
 *
 * @param <Item> Type of the item presented within a data set of a subclass of this BaseRecyclerAdapter.
 * @param <VH>   Type of the view holder used within a subclass of this BaseRecyclerAdapter.
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
	 * Application resources that may be used to obtain strings, texts, drawables, ... and other resources.
	 */
	protected final Resources mResources;

	/**
	 * Data set handling data specified for this adapter.
	 */
	final AdapterDataSet<BaseRecyclerAdapter<Item, VH>, Item> mDataSet;

	/**
	 * Data observer used to notify data set change to registered {@link OnDataSetListener OnDataSetListeners}.
	 */
	private RecyclerView.AdapterDataObserver mDataObserver;

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
		this.mDataSet = new AdapterDataSet<>(this);
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 */
	@Override
	public void registerOnDataChangeListener(@NonNull OnDataChangeListener listener) {
		mDataSet.registerOnDataChangeListener(listener);
	}

	/**
	 */
	@Override
	public void unregisterOnDataChangeListener(@NonNull OnDataChangeListener listener) {
		mDataSet.unregisterOnDataChangeListener(listener);
	}

	/**
	 */
	@Override
	public void registerOnDataSetListener(@NonNull OnDataSetListener listener) {
		mDataSet.registerOnDataSetListener(listener);
		if (mDataObserver == null) {
			registerAdapterDataObserver(mDataObserver = new RecyclerView.AdapterDataObserver() {

				/**
				 */
				@Override
				public void onChanged() {
					mDataSet.notifyDataSetChanged();
				}

				/**
				 */
				@Override
				public void onItemRangeInserted(int positionStart, int itemCount) {
					mDataSet.notifyDataSetChanged();
				}

				/**
				 */
				@Override
				public void onItemRangeRemoved(int positionStart, int itemCount) {
					mDataSet.notifyDataSetChanged();
				}

				/**
				 */
				@Override
				public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
					mDataSet.notifyDataSetChanged();
				}
			});
		}
	}

	/**
	 */
	@Override
	public void unregisterOnDataSetListener(@NonNull OnDataSetListener listener) {
		mDataSet.unregisterOnDataSetListener(listener);
		if (mDataObserver != null) {
			unregisterAdapterDataObserver(mDataObserver);
		}
	}

	/**
	 */
	@Override
	public void registerOnDataSetActionListener(@NonNull OnDataSetActionListener listener) {
		mDataSet.registerOnDataSetActionListener(listener);
	}

	/**
	 * Notifies that the given <var>action</var> has been performed for the specified <var>position</var>.
	 * <p>
	 * If {@link #onDataSetActionSelected(int, int, Object)} will not process this call, the registered
	 * {@link OnDataSetActionListener OnDataSetActionListeners} will be notified.
	 * <p>
	 * <b>Note, that invoking this method with 'invalid' position, out of bounds of the current data
	 * set, will be ignored.</b>
	 *
	 * @param action   The action that was selected.
	 * @param position The position for which was the specified action selected.
	 * @param payload  Additional payload data for the selected action. May be {@code null} if no
	 *                 payload has been specified.
	 * @return {@code True} if the action has been handled internally by this adapter or by one of
	 * the registers listeners, {@code false} otherwise.
	 */
	protected boolean notifyDataSetActionSelected(int action, int position, @Nullable Object payload) {
		// Do not notify actions for invalid (out of bounds of the current data set) positions.
		return position >= 0 && position < getItemCount() && mDataSet.notifyDataSetActionSelected(action, position, payload);
	}

	/**
	 * Invoked immediately after {@link #notifyDataSetActionSelected(int, int, Object)} was called.
	 *
	 * @return {@code True} to indicate that this event was processed here, {@code false} to dispatch
	 * this event to the registered {@link OnDataSetActionListener OnDataSetActionListeners}.
	 */
	protected boolean onDataSetActionSelected(int action, int position, @Nullable Object payload) {
		return false;
	}

	/**
	 */
	@Override
	public void unregisterOnDataSetActionListener(@NonNull OnDataSetActionListener listener) {
		mDataSet.unregisterOnDataSetActionListener(listener);
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
	public long getItemId(int position) {
		return position;
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
	protected View inflate(@LayoutRes int resource, @NonNull ViewGroup parent) {
		return mLayoutInflater.inflate(resource, parent, false);
	}

	/**
	 */
	@Override
	public abstract void onBindViewHolder(@NonNull VH viewHolder, int position);

	/**
	 * If you decide to override this method, do not forget to call {@code super.saveInstanceState()}
	 * and pass the obtained super state to the corresponding constructor of your saved state
	 * implementation to ensure the state of all classes along the chain is properly saved.
	 */
	@NonNull
	@Override
	@CallSuper
	public Parcelable saveInstanceState() {
		return AdapterSavedState.EMPTY_STATE;
	}

	/**
	 * If you decide to override this method, do not forget to call {@code super.restoreInstanceState()}
	 * and pass here the parent state obtained from the your saved state implementation to ensure the
	 * state of all classes along the chain is properly restored.
	 */
	@Override
	@CallSuper
	public void restoreInstanceState(@NonNull Parcelable savedState) {
	}

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * Simple {@link android.support.v7.widget.RecyclerView.ViewHolder} implementation used as default
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
