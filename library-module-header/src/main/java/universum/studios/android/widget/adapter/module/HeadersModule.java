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

import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * An {@link AdapterModule} implementation that specifies API for modules that may provide additional
 * data set of headers for theirs associated adapter. To ensure that the associated adapter works
 * properly, it is necessary to call some of HeadersModule API methods from within such adapter as
 * it is shown below:
 * <pre>
 * public class SampleAdapter extends BaseAdapter implements ModuleAdapter {
 *
 *      // Flag indicating item view type.
 *      private static final int VIEW_TYPE_ITEM = 0x00;
 *
 *      // Flag indicating header view type.
 *      private static final int VIEW_TYPE_HEADER = 0x01;
 *
 *      // Headers module holding the header items data set.
 *      private HeadersModule mHeadersModule;
 *
 *      // ...
 *
 *      public SampleAdapter(&#64;NonNull Context context) {
 *          super(context);
 *          this.mHeadersModule = new HeadersModule();
 *          this.mHeadersModule.attachToAdapter(this);
 *      }
 *
 *      &#64;NonNull
 *      public int getItemCount() {
 *          // Expand current data set size with also size of headers data set.
 *          return super.getItemCount() + mHeadersModule.size();
 *      }
 *
 *      &#64;NonNull
 *      public Item getItem(int position) {
 *          // This is necessary because this adapter does not hold the entire data set but only
 *          // its items, so without this correction this call can throw IndexOutOfBoundsException
 *          // for the positions above super.getItemCount() because of implementation of getItemCount().
 *          return super.getItem(mHeadersModule.correctPosition(position));
 *      }
 *
 *      &#64;NonNull
 *      protected View onCreateView(&#64;NonNull ViewGroup parent, int position) {
 *          switch (currentViewType()) {
 *              case VIEW_TYPE_ITEM:
 *                  return new View(mContext); // Create here view for adapter's item.
 *              case VIEW_TYPE_HEADER:
 *                  return mHeadersModule.createView(mLayoutInflater, parent, position);
 *          }
 *          return null;
 *      }
 *
 *      &#64;NonNull
 *      protected void onBindViewHolder(&#64;NonNull Object viewHolder, int position) {
 *          switch (currentViewType()) {
 *              case VIEW_TYPE_ITEM:
 *                  // Bind here adapter's item.
 *                  break;
 *              case VIEW_TYPE_HEADER:
 *                  mHeadersModule.bindViewHolder(viewHolder, position);
 *                  break;
 *          }
 *      }
 *
 *      // ...
 * }
 * </pre>
 *
 * @param <H> Type of the header items presented within data set of a subclass of this HeadersModule.
 * @author Martin Albedinsky
 */
public abstract class HeadersModule<H extends HeadersModule.Header> extends AdapterModule {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	private static final String TAG = "HeadersModule";

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Required interface for header items of {@link HeadersModule}.
	 *
	 * @author Martin Albedinsky
	 */
	public interface Header {

		/**
		 * Returns the text to be displayed for this header instance.
		 *
		 * @return This header's text.
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
	 * Array of headers managed by this module mapped to theirs positions.
	 */
	private final SparseArray<H> mHeaders = new SparseArray<>(10);

	/**
	 * An attribute from the current theme, which should contain a resource of the style for the
	 * header view.
	 */
	private int mHeaderStyleAttr = android.R.attr.textViewStyle;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Checks whether this module has some headers or not.
	 *
	 * @return {@code True} if this module does not have any headers, {@code false} otherwise.
	 * @see #size()
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the count of headers in the current headers data set of this module.
	 *
	 * @return Headers count.
	 * @see #isEmpty()
	 */
	public int size() {
		return mHeaders.size();
	}

	/**
	 * Adds the given header at the specified position into the current headers data set of this module.
	 * If there is already header at the specified position, the current one will be replaced by the
	 * given one.
	 *
	 * @param header   The desired header to add.
	 * @param position The position at which should be header added.
	 * @see #getHeader(int)
	 * @see #removeHeaderAt(int)
	 */
	protected void addHeader(@NonNull H header, int position) {
		mHeaders.append(position, header);
	}

	/**
	 * Checks whether there is a header at the specified <var>position</var> or not.
	 *
	 * @param position The position to check.
	 * @return {@code True} if there is a header item at the specified position, {@code false} otherwise.
	 * @see #getHeader(int)
	 */
	public boolean isHeaderAt(int position) {
		return mHeaders.get(position) != null;
	}

	/**
	 * Returns the header associated with the specified position from the current headers data set
	 * of this module.
	 *
	 * @param position Position of the desired header to obtain.
	 * @return Instance of the requested header at the specified position or {@code null} if there
	 * is no header item at the requested position.
	 * @see #getHeaders()
	 * @see #isEmpty()
	 */
	@Nullable
	public H getHeader(int position) {
		return mHeaders.get(position);
	}

	/**
	 * Returns the current headers data set of this module.
	 *
	 * @return Array of headers mapped to theirs positions.
	 * @see #getHeader(int)
	 * @see #isEmpty()
	 */
	@NonNull
	public SparseArray<H> getHeaders() {
		return mHeaders;
	}

	/**
	 * Removes a header at the specified position from the current headers data set of this module.
	 *
	 * @param position The position at which should be header removed.
	 * @see #addHeader(Header, int)
	 * @see #clearHeaders()
	 */
	protected void removeHeaderAt(int position) {
		mHeaders.remove(position);
	}

	/**
	 * Clears the current headers data set of this module.
	 */
	public void clearHeaders() {
		mHeaders.clear();
	}

	/**
	 * Corrects the given <var>position</var> passed from the related adapter. Position will be
	 * corrected (decreased) by count of the headers counted by {@link #getHeadersCountBeforePosition(int)}.
	 * <p>
	 * This should be used within the associated adapter's {@link android.widget.Adapter#getItem(int) Adapter#getItem(int)}.
	 *
	 * @param position The position to correct.
	 * @return Corrected position which can be used in the associated adapter to properly/safely access
	 * items from its data set.
	 */
	public int correctPosition(int position) {
		return position - getHeadersCountBeforePosition(position);
	}

	/**
	 * Counts headers presented in the current headers data set before the requested <var>position</var>.
	 *
	 * @param position The position, to which should be headers counted.
	 * @return The count of headers before the requested position.
	 */
	public int getHeadersCountBeforePosition(int position) {
		int count = 0;
		for (int i = 0; i < mHeaders.size(); i++) {
			if (mHeaders.keyAt(i) < position) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	/**
	 * Sets an Xml attribute from the current theme, which contains a resource of style with attributes
	 * for header view which may be created via {@link #createView(LayoutInflater, ViewGroup, int)}.
	 * <p>
	 * Default value: <b>{@link android.R.attr#textViewStyle}</b>
	 *
	 * @param styleAttr Xml style attribute.
	 * @see #getHeaderStyleAttr()
	 */
	public void setHeaderStyleAttr(@AttrRes int styleAttr) {
		this.mHeaderStyleAttr = styleAttr;
	}

	/**
	 * Returns the Xml style attribute specified via {@link #setHeaderStyleAttr(int)}.
	 *
	 * @return Xml style attribute.
	 * @see #setHeaderStyleAttr(int)
	 */
	@AttrRes
	public int getHeaderStyleAttr() {
		return mHeaderStyleAttr;
	}

	/**
	 * Creates the view for the header item at the specified <var>position</var>.
	 * <p>
	 * <b>Note</b>, that the position passed here need to be the same position as passed to
	 * {@link android.widget.Adapter#getView(int, View, ViewGroup) Adapter#getView(int, android.view.View, android.view.ViewGroup)}.
	 *
	 * @param inflater Layout inflater that may be used to create the requested view.
	 * @param parent   A parent view, to resolve correct layout params for the newly creating view.
	 * @param position Position of the header from the current data set for which should be a new view
	 *                 created.
	 * @return A view corresponding to the header item at the specified position.
	 */
	@NonNull
	public View createView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int position) {
		return new TextView(inflater.getContext(), null, mHeaderStyleAttr);
	}

	/**
	 * Creates the view holder for the given header <var>view</var> at the specified <var>position</var>.
	 * <p>
	 * This method returns {@code null} by default as {@link #createView(LayoutInflater, ViewGroup, int)}
	 * method creates by default instance of {@link TextView}.
	 *
	 * @param view     The header view created via {@link #createView(LayoutInflater, ViewGroup, int)}
	 *                 for which to create its holder.
	 * @param position The position for which has been the view created.
	 * @return Holder specific for the view or {@code null} if the view itself is a holder.
	 */
	@Nullable
	public Object createViewHolder(@NonNull View view, int position) {
		return null;
	}

	/**
	 * Binds the given header <var>viewHolder</var> for the specified <var>position</var>.
	 * <p>
	 * <b>Note</b>, that the position passed here need to be the same position as passed to
	 * {@link android.widget.Adapter#getView(int, View, ViewGroup) Adapter#getView(int, android.view.View, android.view.ViewGroup)}.
	 *
	 * @param viewHolder Holder for the specified position of which view should be bound with header's
	 *                   data.
	 * @param position   The position for which to bind header data.
	 */
	public void bindViewHolder(@NonNull Object viewHolder, int position) {
		if (viewHolder instanceof TextView) {
			final Header header = getHeader(position);
			if (header == null) {
				Log.e(TAG, "Invalid header at position(" + position + ").");
			} else {
				((TextView) viewHolder).setText(header.getText());
			}
		}
	}

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * Simple implementation of {@link Header} item for {@link HeadersModule}.
	 *
	 * @author Martin Albedinsky
	 */
	public static class SimpleHeader implements Header {

		/**
		 * Text value of this header.
		 */
		private final CharSequence text;

		/**
		 * Creates a new instance of SimpleHeader with the given text value.
		 *
		 * @param text Text value for header.
		 */
		public SimpleHeader(@NonNull CharSequence text) {
			this.text = text;
		}

		/**
		 */
		@NonNull
		@Override
		public CharSequence getText() {
			return text;
		}
	}
}
