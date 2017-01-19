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
import android.support.annotation.NonNull;

import universum.studios.android.widget.adapter.AdapterSavedState;

/**
 * This class represents base structure for an object which can be used as a module within an instance
 * of {@link android.widget.Adapter Adapter}. The AdapterModule class specifies base API supporting
 * state saving by {@link #saveInstanceState()} and its restoring by {@link #restoreInstanceState(Parcelable)}
 * and also allows its child implementations notify the adapter to which are these modules attached
 * using {@link #notifyAdapter()}.
 *
 * <h3>State saving</h3>
 * <pre>
 * public class MyModule extends AdapterModule {
 *
 *     // ...
 *
 *     &#64;Override
 *     protected Parcelable onSaveInstanceState() {
 *         final SavedState state = new SavedState(super.onSaveInstanceState());
 *         // ...
 *         // Pass here all data of this module which need to be saved to the state.
 *         // ...
 *         return state;
 *     }
 *
 *     &#64;Override
 *     protected void onRestoreInstanceState(Parcelable savedState) {
 *          if (!(savedState instanceof SavedState)) {
 *              // Passed savedState is not our state, let super to process it.
 *              super.onRestoreInstanceState(savedState);
 *              return;
 *          }
 *
 *          final SavedState state = (SavedState) savedState;
 *          // Pass super state to super to process it.
 *          super.onRestoreInstanceState(savedState.getSuperState());
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
 *              public SavedState createFromParcel(Parcel source) {
 *                  return new SavedState(source);
 *              }
 *
 *              &#64;Override
 *              public SavedState[] newArray(int size) {
 *                  return new SavedState[size];
 *              }
 *         }
 *
 *         SavedState(Parcel source) {
 *              super(source);
 *              // Restore here state's data.
 *         }
 *
 *         // Constructor used to chain the state of inheritance hierarchies.
 *         SavedState(Parcelable superState) {
 *              super(superState);
 *         }
 *
 *         &#64;Override
 *         public void writeToParcel(Parcel dest, int flags) {
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
	 * Interface ===================================================================================
	 */

	/**
	 * Base interface for "module based" adapter which want to use one of {@link AdapterModule} implementations.
	 *
	 * @author Martin Albedinsky
	 */
	public interface ModuleAdapter {

		/**
		 * Notifies the attached observers that the data set of this adapter has been changed and
		 * all Views reflecting these data should be refreshed.
		 */
		void notifyDataSetChanged();

		/**
		 * Return the count of items represented by this adapter.
		 *
		 * @return Count of the items in this adapter's data set.
		 */
		int getItemCount();

		/**
		 * Returns an id of the item from this adapter's data set at the specified <var>position</var>.
		 */
		long getItemId(int position);
	}

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "AdapterModule";

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
	 * that its data set should be changed due to changes made by this module.
	 */
	private boolean mNotificationEnabled = true;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Saves the current state of this module.
	 *
	 * @return Saved state of this module or an <b>empty</b> state if this module does not need to
	 * save its state.
	 */
	@NonNull
	public final Parcelable saveInstanceState() {
		return onSaveInstanceState();
	}

	/**
	 * Invoked immediately after {@link #saveInstanceState()} was called, to save the current state
	 * of this module.
	 * <p>
	 * If you decide to override this method, do not forget to call {@code super.onSaveInstanceState()}
	 * and pass super state obtained from the super to constructor of your saved state implementation
	 * with such a parameter to ensure the state of all classes along the chain is saved.
	 *
	 * @return Instance of modules's saved state if this module saves its state, otherwise no
	 * implementation of this method is required.
	 */
	@NonNull
	protected Parcelable onSaveInstanceState() {
		return AdapterSavedState.EMPTY_STATE;
	}

	/**
	 * Restores the previous state, saved via {@link #saveInstanceState()}, of this module.
	 *
	 * @param savedState Should be the same state as obtained via {@link #saveInstanceState()} before.
	 */
	public final void restoreInstanceState(@NonNull Parcelable savedState) {
		if (!AdapterSavedState.EMPTY_STATE.equals(savedState)) onRestoreInstanceState(savedState);
	}

	/**
	 * Called immediately after {@link #restoreInstanceState(Parcelable)} was called,
	 * to restore the previous state, (saved in {@link #onSaveInstanceState()}), of this module.
	 * <p>
	 * <b>Note</b>, that if the saved state passed to {@link #restoreInstanceState(Parcelable)} method
	 * is {@link AdapterSavedState#EMPTY_STATE} this method will not be invoked.
	 *
	 * @param savedState Previously saved state of this module.
	 */
	protected void onRestoreInstanceState(@NonNull Parcelable savedState) {
	}

	/**
	 * Called to attach this module to the given <var>adapter</var>.
	 *
	 * @param adapter An instance of the adapter by which will be this module used.
	 */
	public final void attachToAdapter(@NonNull ModuleAdapter adapter) {
		onAttachedToAdapter(mAdapter = adapter);
	}

	/**
	 * Invoked immediately after {@link #attachToAdapter(AdapterModule.ModuleAdapter)}.
	 *
	 * @param adapter An instance of the adapter to which was this module right now attached.
	 */
	protected void onAttachedToAdapter(@NonNull ModuleAdapter adapter) {
	}

	/**
	 * Returns flag indicating whether this module requires to be its state staved or not.
	 *
	 * @return {@code True} to call {@link #saveInstanceState()} up on this module,
	 * {@code false} otherwise.
	 */
	public boolean requiresStateSaving() {
		return false;
	}

	/**
	 * Returns flag indicating whether the adapter to which is this module attached should be notified
	 * in case, that its data set should be changed due to changes made by this module.
	 *
	 * @return {@code True} when adapter auto-notification is enabled, {@code false} otherwise.
	 * @see #setAdapterNotificationEnabled(boolean)
	 */
	public boolean isAdapterNotificationEnabled() {
		return mNotificationEnabled;
	}

	/**
	 * Sets flag indicating whether the adapter to which is this module attached should be notified in case,
	 * that its data set should be changed due to changes made by this module.
	 *
	 * @param enabled {@code True} to enable adapter auto-notification, {@code false} to
	 *                disable it.
	 * @see #isAdapterNotificationEnabled()
	 */
	public void setAdapterNotificationEnabled(boolean enabled) {
		this.mNotificationEnabled = enabled;
	}

	/**
	 * Notifies the adapter to which is this module attached by {@link ModuleAdapter#notifyDataSetChanged()}.
	 * <p>
	 * This should be called whenever the data set of the attached adapter should be reloaded due
	 * to changes made by this module.
	 *
	 * @see #isAdapterNotificationEnabled()
	 */
	protected final void notifyAdapter() {
		if (isAdapterNotificationEnabled()) mAdapter.notifyDataSetChanged();
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
