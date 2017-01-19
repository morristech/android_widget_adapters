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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Extended version of {@link android.widget.BaseAdapter}. This version of BaseAdapter implements
 * optimized algorithm for the {@link #getView(int, View, ViewGroup)} method
 * using the holder pattern as described below:
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
 * placed directly within these item views, this adapter provides simple management which allows to
 * notify the attached {@link universum.studios.android.widget.adapter.OnDataSetActionListener OnDataSetActionListener}
 * to which can be dispatched callback about these actions. This listener can be notified that a
 * particular action has been performed by calling {@link #notifyDataSetActionSelected(int, int, Object)}
 * method with id of the performed action and also allows to pass some additional data for the action.
 *
 * <h3>State saving</h3>
 * <pre>
 * public class MyAdapter extends BaseAdapter&lt;String, View&gt; {
 *
 *     // ..
 *
 *     &#64;Override
 *     protected Parcelable onSaveInstanceState() {
 *         final MyAdapterState state = new MyAdapterState(super.onSaveInstanceState());
 *
 *         // ..
 *         // Pass here all data of this adapter which need to be saved to the state.
 *         // ..
 *
 *         return state;
 *     }
 *
 *     &#64;Override
 *     protected void onRestoreInstanceState(Parcelable savedState) {
 *          if (!(savedState instanceof MyAdapterState)) {
 *              // Passed savedState is not our state, let super to process it.
 *              super.onRestoreInstanceState(savedState);
 *              return;
 *          }
 *
 *          final MyAdapterState state = (MyAdapterState) savedState;
 *          // Pass superState to super to process it.
 *          super.onRestoreInstanceState(state.getSuperState());
 *
 *          // ..
 *          // Set here all data of this adapter which need to be restored from the savedState.
 *          // ..
 *     }
 *
 *     // ..
 *
 *     // Implementation of WidgetSavedState for this adapter.
 *     static class MyAdapterState extends WidgetSavedState {
 *
 *         // Each implementation of saved state need to have its own CREATOR provided.
 *         public static final Creator&lt;MyAdapterState&gt; CREATOR = new Creator&lt;MyAdapterState&gt; {
 *
 *              &#64;Override
 *              public MyAdapterState createFromParcel(Parcel source) {
 *                  return new MyAdapterState(source);
 *              }
 *
 *              &#64;Override
 *              public MyAdapterState[] newArray(int size) {
 *                  return new MyAdapterState[size];
 *              }
 *         }
 *
 *         MyAdapterState(Parcel source) {
 *              super(source);
 *              // Restore state here.
 *         }
 *
 *         // Constructor used to chain the state of derived classes.
 *         MyAdapterState(Parcelable superState) {
 *              super(superState);
 *         }
 *
 *         &#64;Override
 *         public void writeToParcel(Parcel dest, int flags) {
 *              super.writeToParcel(dest, flags);
 *              // Save state here.
 *         }
 *     }
 * }
 * </pre>
 *
 * @param <Item> A type of the item presented within a data set of a subclass of this BaseAdapter.
 * @param <VH>   A type of the view holder used within a subclass of this BaseAdapter.
 * @author Martin Albedinsky
 * @see BaseSpinnerAdapter
 * @see SimpleAdapter
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
	 * Application resources.
	 */
	protected final Resources mResources;

	/**
	 * Item view type for the current {@link #getView(int, View, ViewGroup)}
	 * iteration.
	 */
	private int mCurrentViewType;

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
	public BaseAdapter(@NonNull Context context) {
		this.mContext = context;
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mResources = context.getResources();
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Saves the current state of this adapter.
	 *
	 * @return Saved state of this adapter or an <b>empty</b> state if this adapter does not need to
	 * save its state.
	 */
	@NonNull
	public Parcelable saveInstanceState() {
		return onSaveInstanceState();
	}

	/**
	 * Invoked immediately after {@link #saveInstanceState()} was called, to save the current state
	 * of this adapter.
	 * <p>
	 * If you decide to override this method, do not forget to call {@code super.onSaveInstanceState()}
	 * and pass super state obtained from the super to constructor of your saved state implementation
	 * with such a parameter to ensure the state of all classes along the chain is saved.
	 *
	 * @return Instance of adapter's saved state if this adapter saves its state, otherwise no
	 * implementation of this method is required.
	 */
	@NonNull
	protected Parcelable onSaveInstanceState() {
		return AdapterSavedState.EMPTY_STATE;
	}

	/**
	 * Restores the previous state, saved via {@link #saveInstanceState()}, of this adapter.
	 *
	 * @param savedState Should be the same state as obtained via {@link #saveInstanceState()} before.
	 */
	public void restoreInstanceState(@NonNull Parcelable savedState) {
		if (!AdapterSavedState.EMPTY_STATE.equals(savedState)) onRestoreInstanceState(savedState);
	}

	/**
	 * Called immediately after {@link #restoreInstanceState(Parcelable)} was called,
	 * to restore the previous state, (saved in {@link #onSaveInstanceState()}), of this adapter.
	 * <p>
	 * <b>Note</b>, that if the saved state passed to {@link #restoreInstanceState(Parcelable)} method
	 * is {@link AdapterSavedState#EMPTY_STATE} this method will not be invoked.
	 *
	 * @param savedState Previously saved state of this adapter.
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
	@Override
	@SuppressWarnings("unchecked")
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		if (mDataSetListener != null) mDataSetListener.onDataSetChanged(this);
	}

	/**
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void notifyDataSetInvalidated() {
		super.notifyDataSetInvalidated();
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
	 * @return {@code True} if the action has been handled internally by this adapter or by the
	 * attached listener, {@code false} otherwise.
	 */
	@SuppressWarnings("unchecked")
	protected boolean notifyDataSetActionSelected(int action, int position, @Nullable Object data) {
		return onDataSetActionSelected(action, position, data) ||
				(mDataSetActionListener != null && mDataSetActionListener.onDataSetActionSelected(
						this, action, position, getItemId(position), data)
				);
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
	 * Same as {@link #getItemCount()}.
	 */
	@Override
	public final int getCount() {
		return getItemCount();
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
	@SuppressWarnings("unchecked")
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		Object viewHolder;
		// Obtain current item view type.
		this.mCurrentViewType = getItemViewType(position);
		if (convertView == null) {
			// Dispatch to create new view.
			convertView = onCreateView(parent, position);
			// Resolve holder for the newly created view.
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
		// Dispatch to bind view holder with data.
		onBindViewHolder((VH) viewHolder, position);
		return convertView;
	}

	/**
	 * Returns a type of an item's view for the currently iterated position.
	 *
	 * @return View type provided by {@link #getItemViewType(int)} for the currently iterated position.
	 */
	protected final int currentViewType() {
		return mCurrentViewType;
	}

	/**
	 * Invoked to create a view for an item from the current data set at the specified position.
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
	protected final View inflate(@LayoutRes int resource, @NonNull ViewGroup parent) {
		return mLayoutInflater.inflate(resource, parent, false);
	}

	/**
	 * Invoked to create a view holder for a view of an item from the current data set at the
	 * specified position.
	 * <p>
	 * This is invoked only if <var>convertView</var> for the specified <var>position</var> in
	 * {@link #getView(int, View, ViewGroup)} was {@code null}, so as
	 * view also holder need to be created.
	 *
	 * @param itemView An instance of the same view as obtained from  {@link #onCreateView(ViewGroup, int)}
	 *                 for the specified position.
	 * @param position Position of the item from the current data set for which should be a new view
	 *                 holder created.
	 * @return New instance of the requested view holder.
	 */
	@Nullable
	@SuppressWarnings("unchecked")
	protected VH onCreateViewHolder(@NonNull View itemView, int position) {
		// Return null holder, so view created by onCreateView(..) will be passed as holder to onBindView(..).
		return null;
	}

	/**
	 * Ensures that the specified <var>viewHolder</var> has updated position to the specified one.
	 * <p>
	 * <b>Note</b>, that position will be updated only for holder instance of {@link ViewHolder}.
	 *
	 * @param viewHolder The view holder of which position to update.
	 * @param position   The position of the holder.
	 */
	final void ensureViewHolderPosition(Object viewHolder, int position) {
		if (viewHolder instanceof ViewHolder)
			((ViewHolder) viewHolder).updateAdapterPosition(position);
	}

	/**
	 * Invoked to set up and populate a view of an item from the current data set at the specified
	 * position. This is invoked whenever {@link #getView(int, View, ViewGroup)}
	 * is called.
	 * <p>
	 * <b>Note</b>, that if {@link #onCreateViewHolder(View, int)} returns {@code null}
	 * for the specified <var>position</var> here passed <var>viewHolder</var> will be the view created
	 * by {@link #onCreateView(ViewGroup, int)} for the specified position or just recycled
	 * view for such position. This approach can be used, when a view hierarchy of the specific item
	 * is represented by one custom view, where such a view represents a holder for all its child views.
	 *
	 * @param viewHolder An instance of the same holder as provided by {@link #onCreateViewHolder(View, int)}
	 *                   for the specified position or converted view as holder as described above.
	 * @param position   Position of the item from the current data set of which view to set up.
	 */
	protected abstract void onBindViewHolder(@NonNull VH viewHolder, int position);

	/**
	 * Inner classes ===============================================================================
	 */
}
