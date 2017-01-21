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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Extended version of {@link android.widget.BaseAdapter}. This version of BaseAdapter implements
 * optimized algorithm for the {@link #getView(int, View, ViewGroup)} method using the holder pattern
 * as described below:
 * <pre>
 * public class BaseAdapter extends android.widget.BaseAdapter {
 *
 *     // ...
 *
 *     &#64;Override
 *     public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
 *          Object viewHolder;
 *          // Obtain current item view type.
 *          this.mCurrentViewType = getItemViewType(position);
 *          if (convertView == null) {
 *              // Dispatch to create new view.
 *              convertView = onCreateView(parent, position);
 *              // Resolve holder for the newly created view.
 *              final Object holder = onCreateViewHolder(convertView, position);
 *              if (holder != null) {
 *                  convertView.setTag(viewHolder = holder);
 *              } else {
 *                  viewHolder = convertView;
 *              }
 *          } else {
 *              final Object holder = convertView.getTag();
 *              viewHolder = holder != null ? holder : convertView;
 *          }
 *          ensureViewHolderPosition(viewHolder, position);
 *          // Dispatch to bind view holder with data.
 *          onBindViewHolder(viewHolder, position);
 *          return convertView;
 *     }
 *
 *     // ...
 * }
 * </pre>
 * <p>
 * In case when an implementation of this adapter provides item views for data set upon which can be
 * performed a wide set of actions, like marking items as favorite or deleting them using action button
 * placed directly within these item views, this adapter provides simple interface which allows to
 * notify the registered {@link OnDataSetActionListener OnDataSetActionListeners} via
 * {@link #notifyDataSetActionSelected(int, int, Object)} with identifier of the performed action.
 * This method also allows to pass some additional payload data for the action.
 *
 * <h3>State saving</h3>
 * <h3>State saving</h3>
 * <pre>
 * public class SampleAdapter extends BaseAdapter {
 *
 *     // ...
 *
 *     &#64;NonNull
 *     &#64;Override
 *     public Parcelable saveInstanceState() {
 *         final SavedState state = new SavedState(super.saveInstanceState());
 *         // ...
 *         // Pass here all data of this adapter which need to be saved to the state.
 *         // ...
 *         return state;
 *     }
 *
 *     &#64;Override
 *     public void restoreInstanceState(&#64;NonNull Parcelable savedState) {
 *          if (!(savedState instanceof SavedState)) {
 *              // Passed savedState is not our state, let super to process it.
 *              super.restoreInstanceState(savedState);
 *              return;
 *          }
 *          final SavedState state = (SavedState) savedState;
 *          // Pass superState to super to process it.
 *          super.restoreInstanceState(savedState.getSuperState());
 *          // ...
 *          // Set here all data of this adapter which need to be restored from the state.
 *          // ...
 *     }
 *
 *     // ...
 *
 *     // Implementation of AdapterSavedState for this adapter.
 *     static class SavedState extends AdapterSavedState {
 *
 *         // Each implementation of saved state need to have its own CREATOR provided.
 *         public static final Creator&lt;SavedState&gt; CREATOR = new Creator&lt;SavedState&gt;() {
 *
 *              &#64;Override
 *              public SavedState createFromParcel(&#64;NonNull Parcel source) {
 *                  return new SavedState(source);
 *              }
 *
 *              &#64;Override
 *              public SavedState[] newArray(int size) {
 *                  return new SavedState[size];
 *              }
 *         };
 *
 *         // Constructor used to chain the state of inheritance hierarchies.
 *         SavedState(&#64;NonNull Parcelable superState) {
 *              super(superState);
 *         }
 *
 *         SavedState(&#64;NonNull Parcel source) {
 *              super(source);
 *              // Restore here state's data.
 *         }
 *
 *         &#64;Override
 *         public void writeToParcel(&#64;NonNull Parcel dest, int flags) {
 *              super.writeToParcel(dest, flags);
 *              // Save here state's data.
 *         }
 *     }
 * }
 * </pre>
 *
 * @param <Item> Type of the item presented within a data set of a subclass of this BaseAdapter.
 * @param <VH>   Type of the view holder used within a subclass of this BaseAdapter.
 * @author Martin Albedinsky
 */
public abstract class BaseAdapter<Item, VH> extends android.widget.BaseAdapter implements DataSetAdapter<Item> {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "BaseAdapter";

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
	final AdapterDataSet<BaseAdapter<Item, VH>, Item> mDataSet;

	/**
	 * Item view type for the current {@link #getView(int, View, ViewGroup)} iteration.
	 */
	int mCurrentViewType;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Creates a new instance of BaseAdapter within the given <var>context</var>.
	 *
	 * @param context Context in which will be this adapter used.
	 */
	public BaseAdapter(@NonNull Context context) {
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
	}

	/**
	 */
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		mDataSet.notifyDataSetChanged();
	}

	/**
	 */
	@Override
	public void notifyDataSetInvalidated() {
		super.notifyDataSetInvalidated();
		mDataSet.notifyDataSetInvalidated();
	}

	/**
	 */
	@Override
	public void unregisterOnDataSetListener(@NonNull OnDataSetListener listener) {
		mDataSet.unregisterOnDataSetListener(listener);
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
		return position >= 0 && position < getItemCount() && (
						onDataSetActionSelected(action, position, payload) ||
						mDataSet.notifyDataSetActionSelected(action, position, payload)
		);
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
	 * Same as {@link #getItemCount()}.
	 */
	@Override
	public final int getCount() {
		return getItemCount();
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
	public boolean hasStableIds() {
		return false;
	}

	/**
	 */
	@Override
	@SuppressWarnings("unchecked")
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		Object viewHolder;
		this.mCurrentViewType = getItemViewType(position);
		if (convertView == null) {
			convertView = onCreateView(parent, position);
			final Object holder = onCreateViewHolder(convertView, position);
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
		onBindViewHolder((VH) viewHolder, position);
		return convertView;
	}

	/**
	 * Returns the type of an item's view for the currently iterated position.
	 *
	 * @return View type provided by {@link #getItemViewType(int)} for the currently iterated position.
	 */
	protected final int currentViewType() {
		return mCurrentViewType;
	}

	/**
	 * Invoked to create a view for an item from the current data set at the specified <var>position</var>.
	 * <p>
	 * This is invoked only if <var>convertView</var> for the specified <var>position</var> in
	 * {@link #getView(int, View, ViewGroup)} was {@code null}.
	 *
	 * @param parent   A parent view, to resolve correct layout params for the newly creating view.
	 * @param position Position of the item from the current data set for which should be a new view
	 *                 created.
	 * @return New instance of the requested view.
	 * @see #inflate(int, ViewGroup)
	 */
	@NonNull
	protected abstract View onCreateView(@NonNull ViewGroup parent, int position);

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
	 * Invoked to create a view holder for a view of an item from the current data set at the specified
	 * <var>position</var>.
	 * <p>
	 * This is invoked only if <var>convertView</var> for the specified <var>position</var> in
	 * {@link #getView(int, View, ViewGroup)} was {@code null}, so view along with its corresponding
	 * holder need to be created.
	 *
	 * @param itemView The same view as obtained from {@link #onCreateView(ViewGroup, int)} for the
	 *                 specified position.
	 * @param position Position of the item from the current data set for which should be a new view
	 *                 holder created.
	 * @return New instance of the requested view holder or {@code null} if holder for the view is
	 * not required.
	 */
	@Nullable
	@SuppressWarnings("unchecked")
	protected VH onCreateViewHolder(@NonNull View itemView, int position) {
		// Return null holder, so view created by onCreateView(...) will be passed as holder to onBindView(...).
		return null;
	}

	/**
	 * Ensures that the specified <var>viewHolder</var> has updated position to the specified one.
	 * <p>
	 * <b>Note</b>, that position will be updated only for holder instance of {@link ViewHolder}.
	 *
	 * @param viewHolder The view holder of which position to update.
	 * @param position   The position for the holder.
	 */
	final void ensureViewHolderPosition(Object viewHolder, int position) {
		if (viewHolder instanceof ViewHolder) {
			((ViewHolder) viewHolder).updateAdapterPosition(position);
		}
	}

	/**
	 * Invoked to configure and bind a view of an item from the current data set at the specified
	 * <var>position</var>. This is invoked whenever {@link #getView(int, View, ViewGroup)} is called.
	 * <p>
	 * <b>Note</b>, that if {@link #onCreateViewHolder(View, int)} returns {@code null} for the
	 * specified <var>position</var> here passed <var>viewHolder</var> will be the view created by
	 * {@link #onCreateView(ViewGroup, int)} for the specified position or just recycled view for
	 * that position. This approach may be used when a view hierarchy of a specific item is represented
	 * by single custom view, where such view represents a holder for all its child views.
	 *
	 * @param viewHolder The same holder as provided by {@link #onCreateViewHolder(View, int)} for
	 *                   the specified position or converted view as holder as described above.
	 * @param position   Position of the item from the current data set of which view to bind with data.
	 */
	protected abstract void onBindViewHolder(@NonNull VH viewHolder, int position);

	/**
	 */
	@NonNull
	@Override
	@CallSuper
	public Parcelable saveInstanceState() {
		return AdapterSavedState.EMPTY_STATE;
	}

	/**
	 */
	@Override
	@CallSuper
	public void restoreInstanceState(@NonNull Parcelable savedState) {
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
