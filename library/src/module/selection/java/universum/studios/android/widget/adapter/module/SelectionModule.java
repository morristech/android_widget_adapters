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

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import universum.studios.android.widget.adapter.AdapterSavedState;

/**
 * An {@link AdapterModule AdapterModule}
 * implementation which can be used to support selection within the adapter.
 * <p>
 * This module supports both, {@link #SINGLE} and {@link #MULTIPLE} selection modes.
 * The selection mode can be changed by {@link #setMode(int)}.
 * <p>
 * Hosting adapter needs to dispatch this module whether an item at with a specific id has been
 * selected or not using {@link #toggleSelection(long)} or calling directly {@link #setSelected(long, boolean)}.
 * To obtain currently selected single id call {@link #getSelectedId()} and to obtain currently
 * selected multiple ids call {@link #getSelectedIds()}. The code sample belows shows basic usage of
 * this module within adapter:
 * <pre>
 * public class MyAdapter extends BaseAdapter {
 *
 *      // Selection module providing selection feature for this adapter.
 *      private SelectionModule mSelectionModule;
 *
 *      // other adapter's members ...
 *
 *      public MyAdapter(@NonNull Context context) {
 *          super(context);
 *          this.mSelectionModule = new SelectionModule();
 *          this.mSelectionModule.setMode(SelectionModule.MULTIPLE);
 *          this.mSelectionModule.attachToAdapter(this);
 *      }
 *
 *      // Call this from OnItemClickListener.onItemClick(AdapterView, View, int, long) callback.
 *      public void toggleItemSelection(long id) {
 *          mSelectionModule.toggleSelection(id);
 *          // no need to call notifyDataSetChanged() if adapter auto notification is enabled
 *      }
 *
 *      &#64;NonNull
 *      protected void onBindViewHolder(@NonNull Object viewHolder, int position) {
 *          // Use mSelectionModule.isSelected(getItemId(position)) to check if item is selected or not.
 *      }
 *
 *      // other adapter's specific methods ...
 * }
 * </pre>
 *
 * @author Martin Albedinsky
 */
public class SelectionModule extends AdapterModule {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "SelectionModule";

	/**
	 * Defines an annotation for determining set of allowed modes for {@link #setMode(int)} method.
	 */
	@IntDef({SINGLE, MULTIPLE})
	@Retention(RetentionPolicy.SOURCE)
	public @interface SelectionMode {
	}

	/**
	 * Mode that allows only a single item to be selected.
	 *
	 * @see #getSelectedId()
	 */
	public static final int SINGLE = 0x01;

	/**
	 * Mode that allows multiple items to be selected.
	 *
	 * @see #getSelectedIds()
	 */
	public static final int MULTIPLE = 0x02;

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * List containing all currently selected ids.
	 */
	private final List<Long> SELECTION = new ArrayList<>(10);

	/**
	 * Current selection mode of this module.
	 */
	private int mMode = SINGLE;

	/**
	 * Array with restored ids.
	 */
	private long[] mRestoredIds;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Sets the current selection mode of this module to the specified one.
	 *
	 * @param mode The desired selection mode. One of {@link #SINGLE} or {@link #MULTIPLE}.
	 * @see #getMode()
	 */
	public void setMode(@SelectionMode int mode) {
		switch (mode) {
			case SINGLE:
			case MULTIPLE:
				this.mMode = mode;
				break;
		}
	}

	/**
	 * Returns the current selection mode of this module.
	 *
	 * @return Current selection mode (one of {@link #SINGLE}, {@link #MULTIPLE}) or {@link #SINGLE}
	 * by default.
	 * @see #setMode(int)
	 */
	@SelectionMode
	public int getMode() {
		return mMode;
	}

	/**
	 * Changes selection state of the specified <var>id</var> to the opposite one
	 * ({@code selected -> unselected; unselected -> selected}) and <b>notifies adapter</b>.
	 *
	 * @param id The id of an item of which selection state to toggle.
	 * @return Size of the current selection.
	 * @see #setSelected(long, boolean)
	 * @see #isAdapterNotificationEnabled()
	 */
	public int toggleSelection(long id) {
		setSelected(id, !isSelected(id));
		return getSelectionSize();
	}

	/**
	 * Checks whether the specified <var>id</var> is currently selected or not.
	 *
	 * @param id The id of an item of which selection state to check.
	 * @return {@code True} if item with the specified id is selected, {@code false} otherwise.
	 * @see #setSelected(long, boolean)
	 * @see #toggleSelection(long)
	 */
	public boolean isSelected(long id) {
		this.ensureRestoredState();
		return SELECTION.contains(id);
	}

	/**
	 * Changes selection state of the specified <var>id</var> to the desired one and <b>notifies adapter</b>.
	 *
	 * @param id       The id of an item of which selection state to change.
	 * @param selected New selection state.
	 * @see #selectRange(int, int)
	 * @see #selectAll()
	 * @see #toggleSelection(long)
	 * @see #isAdapterNotificationEnabled()
	 */
	public void setSelected(long id, boolean selected) {
		this.ensureRestoredState();
		switch (mMode) {
			case MULTIPLE:
				break;
			case SINGLE:
				clearSelection(false);
				break;
		}
		if (selected) select(id);
		else deselect(id);
		notifyAdapter();
	}

	/**
	 * Adds the specified <var>id</var> into a set of the currently selected ids.
	 *
	 * @param id The id to add into the selected ones.
	 */
	protected final void select(long id) {
		if (!SELECTION.contains(id)) SELECTION.add(id);
	}

	/**
	 * Removes the specified <var>id</var> form a set of the currently selected ids.
	 *
	 * @param id The id to remove from the selected ones.
	 */
	protected final void deselect(long id) {
		SELECTION.remove(id);
	}

	/**
	 * Same as {@link #selectRange(int, int)} with parameters {@code (0, ModuleAdapter.getItemCount())}.
	 *
	 * @see #setSelected(long, boolean)
	 */
	public void selectAll() {
		this.ensureRestoredState();
		this.checkActualModeFor(MULTIPLE, "select all");
		selectRange(0, mAdapter.getItemCount());
	}

	/**
	 * Selects id in the range {@code [startPosition, startPosition + count]} and <b>notifies adapter</b>.
	 * <p>
	 * All previously selected ids will remain selected.
	 *
	 * @param startPosition The position from which to start selection.
	 * @param count         Count of items to select from the start position.
	 * @throws IllegalStateException     If the current mode is not set to {@link #MULTIPLE}.
	 * @throws IndexOutOfBoundsException If {@code startPosition + count > n}.
	 * @see #selectAll()
	 * @see #setSelected(long, boolean)
	 * @see #isAdapterNotificationEnabled()
	 */
	public void selectRange(int startPosition, int count) {
		this.ensureRestoredState();
		this.checkActualModeFor(MULTIPLE, "select range");
		// Check correct index.
		final int n = mAdapter.getItemCount();
		if (startPosition + count > n) {
			throw new IndexOutOfBoundsException("Incorrect count(" + count + ") for start position(" + startPosition + "). Adapter has only " + n + " items.");
		}
		// Select all ids in the requested range.
		for (int i = startPosition; i < startPosition + count; i++) {
			select(mAdapter.getItemId(i));
		}
		notifyAdapter();
	}

	/**
	 * Deselects all currently selected positions and <b>notifies adapter</b>.
	 *
	 * @see #clearSelectionInRange(int, int)
	 * @see #isAdapterNotificationEnabled()
	 */
	public void clearSelection() {
		this.ensureRestoredState();
		clearSelection(true);
	}

	/**
	 * Removes all ids form a set of the currently selected ids.
	 *
	 * @param notify {@code True} to notify the currently attached adapter via {@link #notifyAdapter()},
	 *               {@code false} otherwise.
	 */
	protected final void clearSelection(boolean notify) {
		SELECTION.clear();
		if (notify) notifyAdapter();
	}

	/**
	 * Deselects all currently selected ids in the range {@code [startPosition, startPosition + count]}
	 * and <b>notifies adapter</b>.
	 *
	 * @param startPosition The position from which to start deselection.
	 * @param count         Count of items to deselect from the start position.
	 * @throws IllegalStateException     If the current mode is not set to {@link #MULTIPLE}.
	 * @throws IndexOutOfBoundsException If {@code startPosition + count > n}.
	 * @see #clearSelection()
	 * @see #isAdapterNotificationEnabled()
	 */
	public void clearSelectionInRange(int startPosition, int count) {
		this.ensureRestoredState();
		this.checkActualModeFor(MULTIPLE, "clear selection in range");
		// Check correct index.
		final int n = mAdapter.getItemCount();
		if (startPosition + count > n) {
			throw new IndexOutOfBoundsException("Incorrect count(" + count + ") for start position(" + startPosition + "). Adapter has only " + n + " items.");
		}
		// Deselect all ids in the requested range.
		for (int i = startPosition; i < startPosition + count; i++) {
			deselect(mAdapter.getItemId(i));
		}
		notifyAdapter();
	}

	/**
	 * @return {@code True} whether there are some selected positions to save, {@code false}
	 * otherwise.
	 */
	@Override
	public boolean requiresStateSaving() {
		return SELECTION.size() > 0;
	}

	/**
	 */
	@NonNull
	@Override
	protected Parcelable onSaveInstanceState() {
		final SavedState state = new SavedState(super.onSaveInstanceState());
		state.mode = mMode;
		state.selectedIds = selectionToArray();
		return state;
	}

	/**
	 */
	@Override
	protected void onRestoreInstanceState(@NonNull Parcelable savedState) {
		if (!(savedState instanceof SavedState)) {
			super.onRestoreInstanceState(savedState);
			return;
		}

		final SavedState state = (SavedState) savedState;
		super.onRestoreInstanceState(state.getSuperState());
		this.mMode = state.mode;
		this.mRestoredIds = state.selectedIds;
		this.ensureRestoredState();
	}

	/**
	 * Returns the array with the currently selected ids.
	 *
	 * @return {@link List} with ids that are at this time selected.
	 * @see #getSelectedId()
	 * @see #getSelectedIds()
	 */
	@NonNull
	public List<Long> getSelection() {
		this.ensureRestoredState();
		return new ArrayList<>(SELECTION);
	}

	/**
	 * Returns the current selection size.
	 *
	 * @return Count of the currently selected ids.
	 */
	public int getSelectionSize() {
		this.ensureRestoredState();
		return SELECTION.size();
	}

	/**
	 * Returns the id that is at this time selected.
	 *
	 * @return Currently selected id or {@code -1} if there is no id selected at this time.
	 * @throws IllegalStateException If the current mode is not set to {@link #SINGLE}.
	 * @see #getSelectedIds()
	 */
	public long getSelectedId() {
		this.ensureRestoredState();
		this.checkActualModeFor(SINGLE, "obtain selected id");
		return SELECTION.size() > 0 ? SELECTION.get(0) : -1;
	}

	/**
	 * Returns an array with ids that are currently selected.
	 *
	 * @return Array with all currently selected ids.
	 * @throws IllegalStateException If the current mode is not set to {@link #MULTIPLE}.
	 * @see #getSelectedId()
	 */
	public long[] getSelectedIds() {
		this.ensureRestoredState();
		this.checkActualModeFor(MULTIPLE, "obtain selected ids");
		return selectionToArray();
	}

	/**
	 * Ensures that the state of this module is properly restored.
	 *
	 * @return {@code True} if state has been restored, {@code false} otherwise.
	 */
	private boolean ensureRestoredState() {
		if (mRestoredIds != null) {
			for (long id : mRestoredIds) {
				SELECTION.add(id);
			}
			this.mRestoredIds = null;
			notifyAdapter();
			return true;
		}
		return false;
	}

	/**
	 * Converts the current selection list into array.
	 *
	 * @return Array with ids contained within the current selection.
	 */
	private long[] selectionToArray() {
		final int n = SELECTION.size();
		final long[] selectionArray = new long[n];
		for (int i = 0; i < n; i++) {
			selectionArray[i] = SELECTION.get(i);
		}
		return selectionArray;
	}

	/**
	 * Check whether the current mode complies with the requested mode for the given action. If not
	 * the IllegalStateException is thrown.
	 *
	 * @param requiredMode Flag of the required mode.
	 * @param action       Action for which is this check performed.
	 */
	private void checkActualModeFor(int requiredMode, String action) {
		if (mMode != requiredMode) {
			throw new IllegalStateException("Can't " + action + ". Not in required(" + modeName(requiredMode) + ") mode.");
		}
	}

	/**
	 * Returns name for the given mode.
	 *
	 * @param mode Mode identifier.
	 * @return Name of the requested mode.
	 */
	private static String modeName(int mode) {
		switch (mode) {
			case MULTIPLE:
				return "MULTIPLE";
			case SINGLE:
				return "SINGLE";
		}
		return "";
	}

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * A {@link AdapterSavedState} implementation used to ensure that the state of
	 * {@link SelectionModule} is properly saved.
	 *
	 * @author Martin Albedinsky
	 */
	public static class SavedState extends AdapterSavedState {

		/**
		 * Creator used to create an instance or array of instances of SavedState from {@link Parcel}.
		 */
		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			/**
			 */
			@Override
			public SavedState createFromParcel(Parcel source) {
				return new SavedState(source);
			}

			/**
			 */
			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};

		/**
		 */
		private int mode = SINGLE;

		/**
		 */
		private long[] selectedIds = {};

		/**
		 * Creates a new instance of SavedState with the given <var>superState</var> to allow chaining
		 * of saved states in {@link #onSaveInstanceState()} and also in {@link #onRestoreInstanceState(Parcelable)}.
		 *
		 * @param superState The super state obtained from {@code super.onSaveInstanceState()} within
		 *                   {@code onSaveInstanceState()}.
		 */
		protected SavedState(@NonNull Parcelable superState) {
			super(superState);
		}

		/**
		 * Called from {@link #CREATOR} to create an instance of SavedState form the given parcel
		 * <var>source</var>.
		 *
		 * @param source Parcel with data for the new instance.
		 */
		protected SavedState(@NonNull Parcel source) {
			super(source);
			this.mode = source.readInt();
			this.selectedIds = source.createLongArray();
		}

		/**
		 */
		@Override
		public void writeToParcel(@NonNull Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(mode);
			dest.writeLongArray(selectedIds);
		}
	}
}
