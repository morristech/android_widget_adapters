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

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.List;

/**
 * A {@link HeadersModule} implementation which may be used to provide set of alphabetic headers for
 * data set of a specific adapter to which is this module attached.
 * <p>
 * Alphabetic headers are generated whenever one of {@link #fromAlphabeticList(List)} and
 * {@link #fromAlphabeticCursor(Cursor)} methods is called.
 * <p>
 * <b>Note, that this module assumes, that the data set to be processed is already ordered alphabetically.</b>
 *
 * @author Martin Albedinsky
 */
public class AlphabeticHeaders extends HeadersModule<HeadersModule.SimpleHeader> {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "AlphabeticHeaders";

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Required interface for item of which data set can be processed via {@link #fromAlphabeticList(List)}
	 * or {@link #fromAlphabeticCursor(Cursor)}.
	 *
	 * @author Martin Albedinsky
	 */
	public interface AlphabeticItem {

		/**
		 * Returns text of this alphabetic item.
		 *
		 * @return This item's text.
		 */
		@NonNull
		CharSequence getText();
	}

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Letter that was lastly processed from the current alphabetic data set.
	 * <p>
	 * This is variable holds only temporary value used in {@link #onProcessAlphabeticItem(AlphabeticItem, int)}.
	 */
	private String mLastProcessedLetter = "";

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Like {@link #fromAlphabeticList(List)} for the given <var>cursor</var> of which content
	 * will be iterated to obtain the item names.
	 *
	 * @param cursor The desired cursor with content for alphabetic items to process.
	 * @param <C>    Type of the alphabetic cursor.
	 */
	public <C extends Cursor & AlphabeticItem> void fromAlphabeticCursor(@NonNull C cursor) {
		clearHeaders();
		if (cursor.getCount() > 0 && cursor.moveToFirst()) {
			do {
				this.onProcessAlphabeticItem(cursor, cursor.getPosition());
			} while (cursor.moveToNext());
		}
		notifyAdapter();
	}

	/**
	 * Processes the given list of alphabetic items. Entire list will be iterated and for each of its
	 * items will be checked the first char of name provided via @link AlphabeticHeaders.AlphabeticItem#getText()},
	 * so created headers data set will contain all different first characters found at the first
	 * positions of the obtained item names.
	 * <p>
	 * Also, the adapter, to which is this module attached, will be notified about occurred data set
	 * change via {@link #notifyAdapter()}.
	 * <p>
	 * <b>Note</b>, that items in the given <var>list</var> should be already alphabetically sorted,
	 * otherwise the resulting headers data set may contain duplicates.
	 *
	 * @param list   The desired list of alphabetic items to process.
	 * @param <Item> Type of the alphabetic items in the list.
	 * @see #fromAlphabeticCursor(Cursor)
	 */
	public <Item extends AlphabeticItem> void fromAlphabeticList(@NonNull List<Item> list) {
		clearHeaders();
		for (int i = 0; i < list.size(); i++) {
			this.onProcessAlphabeticItem(list.get(i), i);
		}
		notifyAdapter();
	}

	/**
	 */
	@Override
	public void clearHeaders() {
		super.clearHeaders();
		this.mLastProcessedLetter = "";
	}

	/**
	 * Invoked to process the given alphabetic <var>item</var> and to create header item from it.
	 *
	 * @param item     An alphabetic item to process.
	 * @param position The position at which should be header presented within the headers data set.
	 */
	protected final void onProcessAlphabeticItem(@NonNull AlphabeticItem item, int position) {
		final String name = item.getText().toString();
		if (!TextUtils.isEmpty(name)) {
			final String firstLetter = name.substring(0, 1);
			if (!firstLetter.equals(mLastProcessedLetter)) {
				addHeader(new SimpleHeader(firstLetter), size() + position);
			}
			this.mLastProcessedLetter = firstLetter;
		}
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
