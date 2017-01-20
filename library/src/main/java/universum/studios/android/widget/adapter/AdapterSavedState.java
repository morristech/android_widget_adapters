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

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Saved state implementation for adapters like {@link View.BaseSavedState} for views. This saved
 * state class should be used to implement saved states for all adapters that inherit from the adapter
 * classes from the Widget Adapters library.
 *
 * @author Martin Albedinsky
 */
public abstract class AdapterSavedState implements Parcelable {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "AdapterSavedState";

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Empty state that should be used as return value for {@link DataSetAdapter#saveInstanceState()}
	 * whenever that specific adapter does not need to save its state.
	 */
	public static final AdapterSavedState EMPTY_STATE = new AdapterSavedState() {};

	/**
	 * Creator used to create an instance or array of instances of AdapterSavedState from {@link Parcel}.
	 */
	public static final Creator<AdapterSavedState> CREATOR = new Creator<AdapterSavedState>() {

		/**
		 */
		@Override
		public AdapterSavedState createFromParcel(@NonNull Parcel source) {
			final Parcelable superState = source.readParcelable(AdaptersConfig.class.getClassLoader());
			if (superState != null) {
				throw new IllegalStateException("superState must be null");
			}
			return EMPTY_STATE;
		}

		/**
		 */
		@Override
		public AdapterSavedState[] newArray(int size) {
			return new AdapterSavedState[size];
		}
	};

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Super state supplied during initialization of this saved state or during its restoring via
	 * Parcel mechanism.
	 */
	private final Parcelable mSuperState;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Constructor only for internal use.
	 */
	private AdapterSavedState() {
		this.mSuperState = null;
	}

	/**
	 * Should be called by a derived adapter classes when creating theirs SavedState objects to allow
	 * chaining of those states via {@link DataSetAdapter#saveInstanceState()}.
	 *
	 * @param superState The state of the super class of the associated adapter.
	 */
	protected AdapterSavedState(@NonNull Parcelable superState) {
		this.mSuperState = superState != EMPTY_STATE ? superState : null;
	}

	/**
	 * Should be called from a {@link #CREATOR} of a derived class to create an instance of
	 * AdapterSavedState with super state from the given parcel <var>source</var>.
	 *
	 * @param source Source parcel for the new instance.
	 */
	protected AdapterSavedState(@NonNull Parcel source) {
		final Parcelable superState = source.readParcelable(AdaptersConfig.class.getClassLoader());
		this.mSuperState = superState != null ? superState : EMPTY_STATE;
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mSuperState, flags);
	}

	/**
	 * Returns the super state of this saved state.
	 *
	 * @return The state supplied to {@link #AdapterSavedState(Parcelable)} or a restored one via
	 * {@link #AdapterSavedState(Parcel)}.
	 */
	@NonNull
	final public Parcelable getSuperState() {
		return mSuperState != null ? mSuperState : EMPTY_STATE;
	}

	/**
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
