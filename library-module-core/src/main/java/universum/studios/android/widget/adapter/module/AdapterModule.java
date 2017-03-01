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
package universum.studios.android.widget.adapter.module;

import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import universum.studios.android.widget.adapter.AdapterSavedState;

/**
 * This class specifies base API layer for an object which can be used as a module within an instance
 * of {@link android.widget.Adapter Adapter}. AdapterModule also specifies API supporting state saving
 * via {@link #saveInstanceState()} and its restoring via {@link #restoreInstanceState(Parcelable)}
 * and also allows its inheritance hierarchies to notify the adapter to which are these modules
 * attached via {@link #notifyAdapter()}.
 *
 * <h3>State saving</h3>
 * <pre>
 * public class SampleModule extends AdapterModule {
 *
 *     // ...
 *
 *     &#64;NonNull
 *     &#64;Override
 *     public Parcelable saveInstanceState() {
 *         final SavedState state = new SavedState(super.onSaveInstanceState());
 *         // ...
 *         // Pass here all data of this module which need to be saved to the state.
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
 *          // Pass super state to super to process it.
 *          super.restoreInstanceState(savedState.getSuperState());
 *          // ...
 *          // Set here all data of this module which need to be restored from the savedState.
 *          // ...
 *     }
 *
 *     // ...
 *
 *     // Implementation of AdapterSavedState for this module.
 *     public static class SavedState extends AdapterSavedState {
 *
 *         // Each implementation of saved state need to have its own CREATOR provided.
 *         public static final Creator&lt;SavedState&gt; CREATOR = new Creator&lt;SavedState&gt; {
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
 *         }
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
 * @author Martin Albedinsky
 */
public abstract class AdapterModule {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "AdapterModule";

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Simple interface for "module based" adapter.
	 *
	 * @author Martin Albedinsky
	 * @see #attachToAdapter(ModuleAdapter)
	 */
	public interface ModuleAdapter {

		/**
		 * Notifies the registered observers that the data set of this adapter has been changed and
		 * all Views reflecting these data should be refreshed.
		 */
		void notifyDataSetChanged();

		/**
		 * Returns the count of items presented within data set of this adapter.
		 *
		 * @return Count of items in this adapter's data set.
		 */
		int getItemCount();

		/**
		 * Returns the id of the item from this adapter's data set at the specified <var>position</var>.
		 */
		long getItemId(int position);
	}

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * An instance of the adapter to which is this module attached.
	 */
	ModuleAdapter mAdapter;

	/**
	 * Flag indicating whether the adapter to which is this module attached, should be notified in case,
	 * when its data set has been changed due to changes made by this module.
	 */
	private boolean mNotificationEnabled = true;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Called to attach this module to the given <var>adapter</var>.
	 *
	 * @param adapter Instance of the adapter by which will be this module used.
	 */
	public final void attachToAdapter(@NonNull ModuleAdapter adapter) {
		onAttachedToAdapter(mAdapter = adapter);
	}

	/**
	 * Invoked immediately after {@link #attachToAdapter(ModuleAdapter)}.
	 *
	 * @param adapter Instance of the adapter to which was this module just attached.
	 */
	protected void onAttachedToAdapter(@NonNull ModuleAdapter adapter) {
		// Inheritance hierarchies may override this method in order to perform additional configuration
		// due to attachment to the adapter.
	}

	/**
	 * Asserts that this module is attached to its associated adapter, if not an exception is thrown.
	 *
	 * @throws NullPointerException If this module is not attached to any adapter.
	 * @see #attachToAdapter(ModuleAdapter)
	 */
	protected final void assertAttachedToAdapterOrThrow() {
		if (mAdapter == null) throw new NullPointerException("Not attached to adapter.");
	}

	/**
	 * Sets a boolean flag indicating whether the adapter to which is this module attached should be
	 * notified in case, when its data set has been changed due to changes made by this module.
	 * <p>
	 * Default value: <b>{@code true}</b>
	 *
	 * @param enabled {@code True} to enable adapter auto-notification, {@code false} to disable it.
	 * @see #isAdapterNotificationEnabled()
	 */
	public void setAdapterNotificationEnabled(boolean enabled) {
		this.mNotificationEnabled = enabled;
	}

	/**
	 * Returns a boolean flag indicating whether the adapter to which is this module attached should
	 * be notified in case, when its data set has been changed due to changes made by this module.
	 *
	 * @return {@code True} when adapter auto-notification is enabled, {@code false} otherwise.
	 * @see #setAdapterNotificationEnabled(boolean)
	 */
	public boolean isAdapterNotificationEnabled() {
		return mNotificationEnabled;
	}

	/**
	 * Notifies the adapter to which is this module attached via {@link ModuleAdapter#notifyDataSetChanged()}.
	 * <p>
	 * This should be called whenever the data set of the attached adapter should be reloaded due to
	 * changes made by this module.
	 * <p>
	 * This method does nothing if adapter's notifications are disabled for this module.
	 *
	 * @see #isAdapterNotificationEnabled()
	 */
	protected final void notifyAdapter() {
		if (isAdapterNotificationEnabled() && mAdapter != null) mAdapter.notifyDataSetChanged();
	}

	/**
	 * Returns a boolean flag indicating whether this module requires state saving or not.
	 * <p>
	 * This implementation by default returns {@code false}.
	 *
	 * @return {@code True} to call {@link #saveInstanceState()} upon this module to save its current
	 * state and restore it via {@link #restoreInstanceState(Parcelable)} later, {@code false} otherwise.
	 */
	public boolean requiresStateSaving() {
		return false;
	}

	/**
	 * Saves the current state of this adapter module.
	 * <p>
	 * If you decide to override this method, do not forget to call {@code super.saveInstanceState()}
	 * and pass the obtained super state to the corresponding constructor of your saved state
	 * implementation to ensure the state of all classes along the chain is properly saved.
	 *
	 * @return Saved state of this module or an <b>empty</b> state if this module does not need to
	 * save its state.
	 */
	@NonNull
	@CallSuper
	Parcelable saveInstanceState() {
		return AdapterSavedState.EMPTY_STATE;
	}

	/**
	 * Restores the previous state, saved via {@link #saveInstanceState()}, of this adapter module.
	 * <p>
	 * If you decide to override this method, do not forget to call {@code super.restoreInstanceState(Parcelable)}
	 * and pass there the parent state obtained from your saved state implementation to ensure the
	 * state of all classes along the chain is properly restored.
	 *
	 * @param savedState Should be the same state as obtained via {@link #saveInstanceState()} before.
	 */
	@CallSuper
	void restoreInstanceState(@NonNull Parcelable savedState) {
		// Inheritance hierarchies may restore theirs state here.
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
