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
import android.support.annotation.CallSuper;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import universum.studios.android.widget.adapter.AdapterSavedState;

/**
 * An {@link AdapterModule AdapterModule} implementation that specifies API to support selection
 * feature for the associated adapter.
 * <p>
 * This module supports both, {@link #SINGLE} and {@link #MULTIPLE} selection modes. The desired
 * selection mode may be changed via {@link #setMode(int)} and obtained via {@link #getMode()}.
 * <p>
 * The associated adapter is required to dispatch to this module desired selection changes either via
 * {@link #toggleSelection(long)} or via {@link #setSelected(long, boolean)} methods. Initial selection
 * may be specified via {@link #setSelection(List)}. The current selection (selected ids) may be obtained
 * via {@link #getSelection()} where in {@link #SINGLE} selection mode the returned array will contain
 * only single item. The code snippet below shows basic usage of this module within an adapter:
 * <pre>
 * public class SampleAdapter extends BaseAdapter implements ModuleAdapter {
 *
 *      // Selection module providing selection feature for this adapter.
 *      private SelectionModule mSelectionModule;
 *
 *      // ...
 *
 *      public SampleAdapter(&#64;NonNull Context context) {
 *          super(context);
 *          this.mSelectionModule = new SelectionModule();
 *          this.mSelectionModule.setMode(SelectionModule.MULTIPLE);
 *          this.mSelectionModule.attachToAdapter(this);
 *      }
 *
 *      public void toggleItemSelection(long id) {
 *          mSelectionModule.toggleSelection(id);
 *          // no need to call notifyDataSetChanged() if adapter auto notification is enabled
 *      }
 *
 *      &#64;NonNull
 *      public List&lt;Long&gt; getSelection() {
 *          return mSelectionModule.getSelection();
 *      }
 *
 *      &#64;NonNull
 *      protected void onBindViewHolder(&#64;NonNull Object viewHolder, int position) {
 *          // Use mSelectionModule.isSelected(getItemId(position)) to check if item is selected or not.
 *      }
 *
 *      // ...
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
	 * Defines an annotation for determining set of allowed modes for {@link SelectionModule}.
	 *
	 * <h3>Modes</h3>
	 * <ul>
	 * <li>{@link #SINGLE}</li>
	 * <li>{@link #MULTIPLE}</li>
	 * </ul>
	 */
	@IntDef({SINGLE, MULTIPLE})
	@Retention(RetentionPolicy.SOURCE)
	public @interface SelectionMode {
	}

	/**
	 * Mode that allows only a single item to be selected.
	 *
	 * @see #getSelection()
	 */
	public static final int SINGLE = 0x01;

	/**
	 * Mode that allows multiple items to be selected.
	 *
	 * @see #getSelection()
	 */
	public static final int MULTIPLE = 0x02;

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Current selection mode of this module.
	 */
	private int mMode = SINGLE;

	/**
	 * List which contains all currently selected ids.
	 */
	private List<Long> mSelection;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Sets the current selection mode of this module to the specified one.
	 * <p>
	 * <b>Note, that changing of selection mode also clears the current selection of this module
	 * and notifies adapter.</b>
	 * <p>
	 * Default value: <b>{@link #SINGLE}</b>
	 *
	 * @param mode The desired selection mode. One of {@link #SINGLE} or {@link #MULTIPLE}.
	 * @see #getMode()
	 * @see #getSelection()
	 */
	public void setMode(@SelectionMode int mode) {
		if (mMode != mode) {
			this.mMode = mode;
			if (mSelection != null && !mSelection.isEmpty()) {
				this.mSelection = null;
				notifyAdapter();
			}
		}
	}

	/**
	 * Returns the current selection mode of this module.
	 *
	 * @return Current selection mode. One of {@link #SINGLE} or {@link #MULTIPLE}.
	 * @see #setMode(int)
	 */
	@SelectionMode
	public int getMode() {
		return mMode;
	}

	/**
	 * Asserts that this module is in {@link #MULTIPLE} selection mode, if not an exception is thrown.
	 *
	 * @throws IllegalStateException If the current selection mode is not {@link #MULTIPLE}.
	 */
	private void assertInMultipleSelectionModeOrThrow() {
		if (mMode != MULTIPLE) throw new IllegalStateException("Not in MULTIPLE selection mode.");
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
	 * Changes selection state of the specified <var>id</var> to the desired one and <b>notifies adapter</b>.
	 *
	 * @param id       The id of an item of which selection state to change.
	 * @param selected New selection state. {@code True} to be selected, {@code false} otherwise,
	 * @see #toggleSelection(long)
	 * @see #selectRange(int, int)
	 * @see #selectAll()
	 * @see #isAdapterNotificationEnabled()
	 */
	public void setSelected(long id, boolean selected) {
		if (selected) {
			if (mMode == SINGLE) {
				clearSelection(false);
			}
			select(id);
		} else {
			deselect(id);
		}
		notifyAdapter();
	}

	/**
	 * Same as {@link #selectRange(int, int)} with parameters {@code (0, ModuleAdapter.getItemCount())}.
	 * <p>
	 * <b>Note</b>, that calling of this method for mode other than {@link #MULTIPLE} will throw an
	 * exception.
	 *
	 * @throws IllegalStateException If the current mode is not {@link #MULTIPLE}.
	 * @see #setSelected(long, boolean)
	 */
	public void selectAll() {
		assertAttachedToAdapterOrThrow();
		selectRange(0, mAdapter.getItemCount());
	}

	/**
	 * Selects all ids in the {@code [startPosition, startPosition + count)} range and <b>notifies adapter</b>.
	 * <p>
	 * All previously selected ids will remain selected.
	 * <p>
	 * <b>Note</b>, that calling of this method for mode other than {@link #MULTIPLE} will throw an
	 * exception.
	 *
	 * @param startPosition The position from which to start selection.
	 * @param count         Count of items to select from the start position.
	 * @throws IllegalStateException     If the current mode is not {@link #MULTIPLE}.
	 * @throws IndexOutOfBoundsException If {@code startPosition + count > n}.
	 * @see #selectAll()
	 * @see #setSelected(long, boolean)
	 * @see #isAdapterNotificationEnabled()
	 */
	public void selectRange(int startPosition, int count) {
		assertInMultipleSelectionModeOrThrow();
		assertAttachedToAdapterOrThrow();
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
	 * Sets a selection for this module and <b>notifies adapter</b>. The specified selection will
	 * override any current selection of this module.
	 *
	 * @param selection The desired selection. May be {@code null} to clear the current selection.
	 * @see #getSelection()
	 * @see #isAdapterNotificationEnabled()
	 */
	public void setSelection(@Nullable List<Long> selection) {
		this.mSelection = selection != null ? new ArrayList<>(selection) : null;
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
		return mSelection != null && mSelection.contains(id);
	}

	/**
	 * Returns list containing ids that are at this time selected within this module.
	 * <p>
	 * In {@link #SINGLE} selection mode the returned list will contain maximum of 1 selected id,
	 * in {@link #MULTIPLE} selection mode all selected ids will be contained in the returned list.
	 *
	 * @return {@link List} with selected ids or <b>immutable</b> empty list if there is no selection.
	 */
	@NonNull
	public List<Long> getSelection() {
		return mSelection != null ? new ArrayList<>(mSelection) : Collections.<Long>emptyList();
	}

	/**
	 * Returns size of the current selection.
	 *
	 * @return Count of the currently selected ids.
	 * @see #getSelection()
	 */
	public int getSelectionSize() {
		return mSelection != null ? mSelection.size() : 0;
	}

	/**
	 * Deselects all currently selected ids and <b>notifies adapter</b>.
	 *
	 * @see #clearSelectionInRange(int, int)
	 * @see #isAdapterNotificationEnabled()
	 */
	public void clearSelection() {
		clearSelection(true);
	}

	/**
	 * Removes all ids form the set of the currently selected ids.
	 *
	 * @param notify {@code True} to notify the associated adapter via {@link #notifyAdapter()},
	 *               {@code false} otherwise.
	 */
	protected final void clearSelection(boolean notify) {
		if (mSelection != null) {
			mSelection.clear();
			if (notify) {
				notifyAdapter();
			}
		}
	}

	/**
	 * Deselects all currently selected ids in the {@code [startPosition, startPosition + count)}
	 * range and <b>notifies adapter</b>.
	 * <p>
	 * <b>Note</b>, that calling of this method for mode other than {@link #MULTIPLE} will throw an
	 * exception.
	 *
	 * @param startPosition The position from which to start deselection.
	 * @param count         Count of items to deselect from the start position.
	 * @throws IllegalStateException     If the current mode is not {@link #MULTIPLE}.
	 * @throws IndexOutOfBoundsException If {@code startPosition + count > n}.
	 * @see #clearSelection()
	 * @see #isAdapterNotificationEnabled()
	 */
	public void clearSelectionInRange(int startPosition, int count) {
		assertInMultipleSelectionModeOrThrow();
		assertAttachedToAdapterOrThrow();
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
	 * Adds the specified <var>id</var> into the set of the currently selected ids. If there is already
	 * same id presented, addition of the given one will be ignored.
	 *
	 * @param id The id to add into the selected ones.
	 * @see #deselect(long)
	 */
	protected final void select(long id) {
		if (mSelection == null) mSelection = new ArrayList<>(mMode == SINGLE ? 1 : 10);
		if (!mSelection.contains(id)) mSelection.add(id);
	}

	/**
	 * Removes the specified <var>id</var> form the set of the currently selected ids.
	 *
	 * @param id The id to remove from the selected ones.
	 * @see #select(long)
	 */
	protected final void deselect(long id) {
		if (mSelection != null) mSelection.remove(id);
	}

	/**
	 * @return {@code True} whether this module have some selection to save, {@code false} otherwise.
	 */
	@Override
	public boolean requiresStateSaving() {
		return mSelection != null && mSelection.size() > 0;
	}

	/**
	 */
	@NonNull
	@Override
	@CallSuper
	public Parcelable saveInstanceState() {
		final SavedState state = new SavedState(super.saveInstanceState());
		state.mode = mMode;
		if (mSelection != null && !mSelection.isEmpty()) {
			final long[] selectionArray = new long[mSelection.size()];
			for (int i = 0; i < mSelection.size(); i++) {
				selectionArray[i] = mSelection.get(i);
			}
			state.selection = selectionArray;
		}
		return state;
	}

	/**
	 */
	@Override
	@CallSuper
	public void restoreInstanceState(@NonNull Parcelable savedState) {
		if (!(savedState instanceof SavedState)) {
			super.restoreInstanceState(savedState);
			return;
		}
		final SavedState state = (SavedState) savedState;
		super.restoreInstanceState(state.getSuperState());
		this.mMode = state.mode;
		if (state.selection != null) {
			this.mSelection = new ArrayList<>(state.selection.length);
			for (long id : state.selection) {
				mSelection.add(id);
			}
		}
	}

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * An {@link AdapterSavedState} implementation used to ensure that the state of {@link SelectionModule}
	 * is properly saved.
	 *
	 * @author Martin Albedinsky
	 */
	public static class SavedState extends AdapterSavedState {

		/**
		 * Creator used to create an instance or array of instances of SavedState from {@link Parcel}.
		 */
		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

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
		private long[] selection = {};

		/**
		 * Creates a new instance of SavedState with the given <var>superState</var> to allow chaining
		 * of saved states in {@link #saveInstanceState()} and also in {@link #restoreInstanceState(Parcelable)}.
		 *
		 * @param superState The super state obtained from {@code super.saveInstanceState()} within
		 *                   {@link #saveInstanceState()}.
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
			this.selection = source.createLongArray();
		}

		/**
		 */
		@Override
		public void writeToParcel(@NonNull Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(mode);
			dest.writeLongArray(selection);
		}
	}
}
