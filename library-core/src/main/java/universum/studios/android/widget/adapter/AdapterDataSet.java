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

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link DataSet} implementation that may be used to wrap a set of items and use it as data
 * set in adapters.
 *
 * @param <A> Type of the adapter where this data set will be used.
 * @author Martin Albedinsky
 */
final class AdapterDataSet<A extends DataSetAdapter<I>, I> implements DataSet<I> {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "AdapterDataSet";

	/**
	 * Flag used to identify {@link OnDataChangeListener}.
	 */
	static final int LISTENER_DATA_CHANGE = 0x00000001;

	/**
	 * Flag used to identify {@link OnDataSetListener}.
	 */
	static final int LISTENER_DATA_SET = 0x00000001 << 1;

	/**
	 * Flag used to identify {@link OnDataSetActionListener}.
	 */
	static final int LISTENER_DATA_SET_ACTION = 0x00000001 << 2;

	/**
	 * Defines an annotation for determining set of flags that may be used for listeners related
	 * flagging.
	 */
	@IntDef(flag = true, value = {
			LISTENER_DATA_CHANGE,
			LISTENER_DATA_SET,
			LISTENER_DATA_SET_ACTION
	})
	@Retention(RetentionPolicy.SOURCE)
	public @interface Listener {
	}

	/**
	 * Flag grouping all listener flags defined by {@link Listener @Listener} annotation.
	 */
	private static final int LISTENER_ALL = LISTENER_DATA_CHANGE | LISTENER_DATA_SET | LISTENER_DATA_SET_ACTION;

	/**
	 * Flag used to identify {@link OnDataChangeListener#onDataChange(Object, Object)} callback.
	 */
	static final int CALLBACK_DATA_CHANGE = 0x00000001;

	/**
	 * Flag used to identify {@link OnDataChangeListener#onDataChanged(Object, Object)} callback.
	 */
	static final int CALLBACK_DATA_CHANGED = 0x00000001 << 1;

	/**
	 * Flag used to identify {@link OnDataSetListener#onDataSetChanged(Object)} callback.
	 */
	static final int CALLBACK_DATA_SET_CHANGED = 0x00000001 << 2;

	/**
	 * Flag used to identify {@link OnDataSetListener#onDataSetInvalidated(Object)} callback.
	 */
	static final int CALLBACK_DATA_SET_INVALIDATED = 0x00000001 << 3;

	/**
	 * Flag used to identify {@link OnDataSetActionListener#onDataSetActionSelected(Object, int, int, long, Object)}
	 * callback.
	 */
	static final int CALLBACK_DATA_SET_ACTION_SELECTED = 0x00000001 << 4;

	/**
	 * Defines an annotation for determining set of flags that may be used for listener callbacks
	 * related flagging.
	 */
	@IntDef(flag = true, value = {
			CALLBACK_DATA_CHANGE,
			CALLBACK_DATA_CHANGED,
			CALLBACK_DATA_SET_CHANGED,
			CALLBACK_DATA_SET_INVALIDATED,
			CALLBACK_DATA_SET_ACTION_SELECTED
	})
	@Retention(RetentionPolicy.SOURCE)
	public @interface ListenerCallback {
	}

	/**
	 * Flag grouping all listener callback flags defined by {@link ListenerCallback @ListenerCallback}
	 * annotation.
	 */
	private static final int CALLBACK_ALL = CALLBACK_DATA_CHANGE | CALLBACK_DATA_CHANGED |
			CALLBACK_DATA_SET_CHANGED | CALLBACK_DATA_SET_INVALIDATED |
			CALLBACK_DATA_SET_ACTION_SELECTED;

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
	 * Adapter to which is this data set attached.
	 */
	private final A mAdapter;

	/**
	 * List with registered data change listeners.
	 */
	private List<OnDataChangeListener> mDataChangeListeners;

	/**
	 * List with registered data set listeners.
	 */
	private List<OnDataSetListener> mDataSetListeners;

	/**
	 * List with registered data set action listeners.
	 */
	private List<OnDataSetActionListener> mDataSetActionListeners;

	/**
	 * Data attached to this data set.
	 */
	private List<I> mData;

	/**
	 * Listener flags determining which listeners are enabled to be notified.
	 */
	private int mEnabledListeners = LISTENER_ALL;

	/**
	 * Listener callback flags determining which listener callbacks are enabled to be notified.
	 */
	private int mEnabledListenerCallbacks = CALLBACK_ALL;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Creates a new instance of AdapterDataSet for the given <var>adapter</var>.
	 *
	 * @param adapter The adapter where the new data set will be used.
	 */
	AdapterDataSet(A adapter) {
		this.mAdapter = adapter;
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Sets whether callbacks for listeners with the specified <var>listeners</var> flags registered
	 * upon this data set should be notified or not.
	 *
	 * @param listeners The desired listener flags. One of flags defined by {@link Listener @Listener}
	 *                  annotation or theirs combination.
	 * @param enabled   {@code True} to enable listener notifications, {@code false} to disable them.
	 */
	void setListenersEnabled(@Listener int listeners, boolean enabled) {
		if (enabled) this.mEnabledListeners |= listeners;
		else this.mEnabledListeners &= ~listeners;
	}

	/**
	 * Sets whether a particular callback with the specified <var>listenerCallback</var> flags for
	 * listeners registered upon this data set should be notified or not.
	 *
	 * @param listenerCallback The desired listener callback flags. One of flags defined by
	 *                         {@link ListenerCallback @ListenerCallback} annotation or theirs combination.
	 * @param enabled          {@code True} to enable listener callback notification, {@code false}
	 *                         to disable it.
	 */
	void setListenerCallbacksEnabled(@ListenerCallback int listenerCallback, boolean enabled) {
		if (enabled) this.mEnabledListenerCallbacks |= listenerCallback;
		else this.mEnabledListenerCallbacks &= ~listenerCallback;
	}

	/**
	 */
	@Override
	public void registerOnDataChangeListener(@NonNull OnDataChangeListener listener) {
		if (mDataChangeListeners == null) this.mDataChangeListeners = new ArrayList<>(1);
		if (!mDataChangeListeners.contains(listener)) this.mDataChangeListeners.add(listener);
	}

	/**
	 * Notifies registered {@link OnDataChangeListener OnDataChangeListeners} that the given <var>data</var>
	 * are about to be changed for this data set.
	 *
	 * @param data The data that are about to be changed for this data set.
	 * @see #notifyDataChanged(Object)
	 */
	@SuppressWarnings("unchecked")
	void notifyDataChange(Object data) {
		if ((mEnabledListeners & LISTENER_DATA_CHANGE) == 0 || (mEnabledListenerCallbacks & CALLBACK_DATA_CHANGE) == 0) {
			return;
		}
		if (mDataChangeListeners != null && !mDataChangeListeners.isEmpty()) {
			for (OnDataChangeListener listener : mDataChangeListeners) {
				listener.onDataChange(mAdapter, data);
			}
		}
	}

	/**
	 * Notifies registered {@link OnDataChangeListener OnDataChangeListeners} that the given <var>data</var>
	 * have been changed for this data set.
	 *
	 * @param data The data that have been changed for this data set.
	 * @see #notifyDataChange(Object)
	 */
	@SuppressWarnings("unchecked")
	void notifyDataChanged(Object data) {
		if ((mEnabledListeners & LISTENER_DATA_CHANGE) == 0 || (mEnabledListenerCallbacks & CALLBACK_DATA_CHANGED) == 0) {
			return;
		}
		if (mDataChangeListeners != null && !mDataChangeListeners.isEmpty()) {
			for (OnDataChangeListener listener : mDataChangeListeners) {
				listener.onDataChanged(mAdapter, data);
			}
		}
	}

	/**
	 */
	@Override
	public void unregisterOnDataChangeListener(@NonNull OnDataChangeListener listener) {
		if (mDataChangeListeners != null) this.mDataChangeListeners.remove(listener);
	}

	/**
	 */
	@Override
	public void registerOnDataSetListener(@NonNull OnDataSetListener listener) {
		if (mDataSetListeners == null) this.mDataSetListeners = new ArrayList<>(1);
		if (!mDataSetListeners.contains(listener)) this.mDataSetListeners.add(listener);
	}

	/**
	 * Notifies registered {@link OnDataSetListener OnDataSetListeners} that data of this data set
	 * has been changed.
	 *
	 * @see #notifyDataSetInvalidated()
	 */
	@SuppressWarnings("unchecked")
	void notifyDataSetChanged() {
		if ((mEnabledListeners & LISTENER_DATA_SET) == 0 || (mEnabledListenerCallbacks & CALLBACK_DATA_SET_CHANGED) == 0) {
			return;
		}
		if (mDataSetListeners != null && !mDataSetListeners.isEmpty()) {
			for (OnDataSetListener listener : mDataSetListeners) {
				listener.onDataSetChanged(mAdapter);
			}
		}
	}

	/**
	 * Notifies registered {@link OnDataSetListener OnDataSetListeners} that data of this data set
	 * has been invalidated.
	 *
	 * @see #notifyDataSetChanged()
	 */
	@SuppressWarnings("unchecked")
	void notifyDataSetInvalidated() {
		if ((mEnabledListeners & LISTENER_DATA_SET) == 0 || (mEnabledListenerCallbacks & CALLBACK_DATA_SET_INVALIDATED) == 0) {
			return;
		}
		if (mDataSetListeners != null && !mDataSetListeners.isEmpty()) {
			for (OnDataSetListener listener : mDataSetListeners) {
				listener.onDataSetInvalidated(mAdapter);
			}
		}
	}

	/**
	 */
	@Override
	public void unregisterOnDataSetListener(@NonNull OnDataSetListener listener) {
		if (mDataSetListeners != null) this.mDataSetListeners.remove(listener);
	}

	/**
	 */
	@Override
	public void registerOnDataSetActionListener(@NonNull OnDataSetActionListener listener) {
		if (mDataSetActionListeners == null) this.mDataSetActionListeners = new ArrayList<>(1);
		if (!mDataSetActionListeners.contains(listener)) this.mDataSetActionListeners.add(listener);
	}

	/**
	 * Notifies registered {@link OnDataSetActionListener OnDataSetActionListeners} that the specified
	 * <var>action</var> has been selected in this data set.
	 *
	 * @param action   The action that was selected.
	 * @param position The position for which was the specified action selected.
	 * @param payload  Additional payload data for the selected action. May be {@code null} if no
	 *                 payload has been specified.
	 * @return {@code True} if the action has been handled by one of the registered listeners,
	 * {@code false} otherwise.
	 */
	@SuppressWarnings("unchecked")
	boolean notifyDataSetActionSelected(int action, int position, Object payload) {
		if ((mEnabledListeners & LISTENER_DATA_SET_ACTION) == 0 || (mEnabledListenerCallbacks & CALLBACK_DATA_SET_ACTION_SELECTED) == 0) {
			return false;
		}
		if (mDataSetActionListeners != null && !mDataSetActionListeners.isEmpty()) {
			for (OnDataSetActionListener listener : mDataSetActionListeners) {
				if (listener.onDataSetActionSelected(mAdapter, action, position, getItemId(position), payload)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 */
	@Override
	public void unregisterOnDataSetActionListener(@NonNull OnDataSetActionListener listener) {
		if (mDataSetActionListeners != null) this.mDataSetActionListeners.remove(listener);
	}

	/**
	 * Attaches the given <var>data</var> to this data set and returns the old data that has been
	 * attached.
	 *
	 * @param data The desired data to attach. May be {@code null}.
	 * @return Old data that has been attached to this data set before or {@code null} if there
	 * were no data attached.
	 * @see #getData()
	 */
	List<I> attachData(List<I> data) {
		final List<I> oldData = mData;
		this.mData = data;
		return oldData;
	}

	/**
	 * Returns the current data attached to this data set.
	 *
	 * @return Current data or {@code null} if there was no data attached yet.
	 */
	List<I> getData() {
		return mData;
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
	public int getItemCount() {
		return mData == null ? 0 : mData.size();
	}

	/**
	 */
	@Override
	public boolean hasItemAt(int position) {
		return position >= 0 && position < getItemCount();
	}

	/**
	 */
	@NonNull
	@Override
	@SuppressWarnings({"unchecked", "ConstantConditions"})
	public I getItem(int position) {
		if (!hasItemAt(position)) {
			throw new IndexOutOfBoundsException(
					"Requested item at invalid position(" + position + "). " +
							"Data set has items in count of(" + getItemCount() + ")."
			);
		}
		return mData.get(position);
	}

	/**
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
