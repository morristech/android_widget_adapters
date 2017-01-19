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

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link AdapterModule}
 * implementation to provide base structure and API for modules which wants to provide additional
 * data set of headers for the hosted adapter.
 * <p>
 * The HeadersModule specifies base API to manage header items. To ensure that the hosted adapter
 * will work properly, it is necessary to call some HeadersModule API methods from within such an
 * adapter, like it is described below:
 * <pre>
 * public class MyAdapter extends BaseAdapter implements ModuleAdapter {
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
 *      // other adapter's members ...
 *
 *      public MyAdapter(@NonNull Context context) {
 *          super(context);
 *          this.mHeadersModule = new HeadersModule();
 *          mHeadersModule.attachToAdapter(this);
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
 *          // This is necessary because of this adapter does not hold the whole data set but only
 *          // its items, so without this correction this call can throw IndexOutOfBoundsException
 *          // for the positions above the dataSet.size() because of implementation of getItemCount().
 *          return super.getItem(mHeadersModule.correctPosition(position));
 *      }
 *
 *      &#64;NonNull
 *      protected View onCreateView(@NonNull ViewGroup parent, int position) {
 *          switch (currentViewType()) {
 *              case VIEW_TYPE_ITEM:
 *                  return new View(mContext); // Create here view for adapter's item.
 *              case VIEW_TYPE_HEADER:
 *                  return mHeadersModule.createView(parent, position, mLayoutInflater);
 *          }
 *          return null;
 *      }
 *
 *      &#64;NonNull
 *      protected void onBindViewHolder(@NonNull Object viewHolder, int position) {
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
 *      // other adapter's specific methods ...
 * }
 * </pre>
 *
 * @param <H> A type of the header items presented within a subclass of this HeadersModule.
 * @author Martin Albedinsky
 */
public abstract class HeadersModule<H extends HeadersModule.Header> extends AdapterModule {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Required interface for header item used by {@link HeadersModule}
	 * module.
	 *
	 * @author Martin Albedinsky
	 */
	public interface Header {

		/**
		 * Returns a text value of this header instance.
		 *
		 * @return The text value of this header item.
		 */
		@NonNull
		CharSequence getText();
	}

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	private static final String TAG = "HeadersModule";

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Set of headers managed by this module mapped to theirs positions.
	 */
	private final SparseArray<H> HEADERS = new SparseArray<>();

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
	 * Checks whether there is a header at the specified <var>position</var> or not.
	 *
	 * @param position The position to check.
	 * @return {@code True} if there is a header item at the specified position, {@code false}
	 * otherwise.
	 */
	public boolean isHeaderAt(int position) {
		return HEADERS.get(position) != null;
	}

	/**
	 * Checks whether this module has some headers or not.
	 *
	 * @return {@code True} if this module does not have any headers, {@code false} otherwise.
	 */
	public boolean isEmpty() {
		return HEADERS.size() == 0;
	}

	/**
	 * Clears the current headers data set of this module.
	 */
	public void clearHeaders() {
		HEADERS.clear();
	}

	/**
	 * Corrects the given <var>position</var> passed from the related adapter. Position will be
	 * corrected (decreased) by count of the headers counted by {@link #getHeadersCountBeforePosition(int)}.
	 * <p>
	 * This should be used within the related adapter's {@link android.widget.Adapter#getItem(int) Adapter#getItem(int)}.
	 *
	 * @param position The position to correct.
	 * @return Corrected position which can be used in the related adapter to access items from its
	 * data set.
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
		for (int i = 0; i < HEADERS.size(); i++) {
			if (HEADERS.keyAt(i) < position) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	/**
	 * Creates a view for the header item at the specified <var>position</var>.
	 * <p>
	 * <b>Note</b>, that the position passed here need to be the same position as passed to
	 * {@link android.widget.Adapter#getView(int, View, ViewGroup) Adapter#getView(int, android.view.View, android.view.ViewGroup)}.
	 *
	 * @param parent   The parent to that should be header's view eventually attached to.
	 * @param position The position for which should be view created.
	 * @param inflater Valid layout inflater to create the requested view.
	 * @return A view corresponding to the header item at the specified position.
	 */
	@NonNull
	public View createView(@NonNull ViewGroup parent, int position, @NonNull LayoutInflater inflater) {
		return new TextView(inflater.getContext(), null, mHeaderStyleAttr);
	}

	/**
	 * Create a view holder for the given header <var>view</var> at the specified <var>position</var>.
	 * <p>
	 * This method returns {@code null} by default as {@link #createView(ViewGroup, int, LayoutInflater)}
	 * method creates by default instance of {@link TextView}.
	 *
	 * @param view     The header view created via {@link #createView(ViewGroup, int, LayoutInflater)}
	 *                 for which to create holder.
	 * @param position The position for which has been the view created.
	 * @return Holder specific for the view or {@code null} if the view should act as holder.
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
	 * @param viewHolder Holder for the specified position of which view should be bind with header's
	 *                   data.
	 * @param position   The position for which to bind header data.
	 */
	public void bindViewHolder(@NonNull Object viewHolder, int position) {
		if (viewHolder instanceof TextView) {
			final Header header = getHeader(position);
			if (header != null) {
				((TextView) viewHolder).setText(header.getText());
			} else {
				Log.e(TAG, "Invalid header at position(" + position + ").");
			}
		}
	}

	/**
	 * Sets an xml attribute from the current theme, which contains a resource of style with attributes
	 * for header view which can be created by {@link #createView(ViewGroup, int, LayoutInflater)}.
	 *
	 * @param styleAttr Style xml attribute.
	 * @see #getHeaderStyleAttr()
	 */
	public void setHeaderStyleAttr(@AttrRes int styleAttr) {
		this.mHeaderStyleAttr = styleAttr;
	}

	/**
	 * Returns the xml attribute set by {@link #setHeaderStyleAttr(int)}.
	 *
	 * @return Xml attribute.
	 * @see #setHeaderStyleAttr(int)
	 */
	@AttrRes
	public int getHeaderStyleAttr() {
		return mHeaderStyleAttr;
	}

	/**
	 * Returns count of headers in the current headers data set of this module.
	 *
	 * @return Headers count.
	 */
	public int size() {
		return HEADERS.size();
	}

	/**
	 * Returns a header associated with the specified position from the current headers data set of
	 * this module.
	 *
	 * @param position Position of the desired header to obtain.
	 * @return An instance of header at the requested position or {@code null} if there is no
	 * header item at the requested position.
	 */
	@Nullable
	public H getHeader(int position) {
		return HEADERS.get(position);
	}

	/**
	 * Returns the current headers data set of this module.
	 *
	 * @return Set of headers mapped to theirs positions.
	 */
	@NonNull
	public SparseArray<H> getHeaders() {
		return HEADERS;
	}

	/**
	 * Returns the current headers data set of this module.
	 *
	 * @return List of the current headers.
	 */
	@NonNull
	public List<H> getHeadersList() {
		final List<H> headers = new ArrayList<>(HEADERS.size());
		for (int i = 0; i < HEADERS.size(); i++) {
			headers.add(HEADERS.get(HEADERS.keyAt(i)));
		}
		return headers;
	}

	/**
	 * Adds the given header at the specified position into the current headers data set of this module.
	 * If there is already header at the specified position, the current one will be replaced by the
	 * given one.
	 *
	 * @param header   Header to add.
	 * @param position The position, at which should be header added.
	 */
	protected void addHeader(@NonNull H header, int position) {
		HEADERS.append(position, header);
	}

	/**
	 * Removes a header at the specified position from the current headers data set of this module.
	 *
	 * @param position The position, at which should be header removed.
	 */
	protected void removeHeader(int position) {
		HEADERS.remove(position);
	}

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * Simple implementation of {@link universum.studios.android.widget.adapter.module.HeadersModule.Header Header}
	 * item for {@link HeadersModule}.
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
