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
package universum.studios.android.widget.adapter.wrapper;

import android.database.DataSetObserver;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;

import universum.studios.android.widget.adapter.inner.AdapterBaseTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * @author Martin Albedinsky
 */
@RunWith(AndroidJUnit4.class)
public final class AdapterWrapperTest extends AdapterBaseTest {

	@SuppressWarnings("unused")
	private static final String TAG = "AdapterWrapperTest";

	private final DataSetObserver mDataSetObserver = new DataSetObserver() {};

	private AdapterWrapper mAdapterWrapper, mEmptyWrapper;
	private TestAdapter mAdapter;

	@Override
	public void beforeTest() throws Exception {
		super.beforeTest();
		this.mAdapterWrapper = new AdapterWrapper(mAdapter = new TestAdapter());
		this.mEmptyWrapper = new AdapterWrapper(null);
	}

	@Test
	public void testRegisterDataSetObserver() {
		mAdapterWrapper.registerDataSetObserver(mDataSetObserver);
	}

	@Test
	public void testRegisterDataSetObserverOnEmptyWrapper() {
		// Only ensure that it does not throw a NullPointerException exception.
		mEmptyWrapper.registerDataSetObserver(mDataSetObserver);
	}

	@Test
	public void testUnregisterDataSetObserver() {
		mAdapterWrapper.unregisterDataSetObserver(mDataSetObserver);
	}

	@Test
	public void testUnregisterDataSetObserverOnEmptyWrapper() {
		// Only ensure that it does not throw a NullPointerException exception.
		mEmptyWrapper.unregisterDataSetObserver(mDataSetObserver);
	}

	@Test
	public void testIsEmpty() {
		assertThat(mAdapterWrapper.isEmpty(), is(mAdapter.isEmpty()));
	}

	@Test
	public void testIsEmptyOnEmptyWrapper() {
		assertThat(mEmptyWrapper.isEmpty(), is(true));
	}

	@Test
	public void testGetCount() {
		assertThat(mAdapterWrapper.getCount(), is(mAdapter.getCount()));
	}

	@Test
	public void testGetCountOnEmptyWrapper() {
		assertThat(mEmptyWrapper.getCount(), is(0));
	}

	@Test
	public void testGetItem() {
		for (int i = 0; i < mAdapterWrapper.getCount(); i++) {
			assertThat(mAdapterWrapper.getItem(i), is(mAdapter.getItem(i)));
		}
	}

	@Test
	public void testGetItemOnEmptyWrapper() {
		for (int i = 0; i < 20; i++) {
			assertThat(mEmptyWrapper.getItem(i), is(nullValue()));
		}
	}

	@Test
	public void testHasStableIds() {
		assertThat(mAdapterWrapper.hasStableIds(), is(mAdapter.hasStableIds()));
	}

	@Test
	public void testHasStableIdsOnEmptyWrapper() {
		assertThat(mEmptyWrapper.hasStableIds(), is(false));
	}

	@Test
	public void testGetItemId() {
		for (int i = 0; i < mAdapterWrapper.getCount(); i++) {
			assertThat(mAdapterWrapper.getItemId(i), is(mAdapter.getItemId(i)));
		}
	}

	@Test
	public void testGetItemIdOnEmptyWrapper() {
		for (int i = 0; i < 20; i++) {
			assertThat(mEmptyWrapper.getItemId(i), is(-1L));
		}
	}

	@Test
	public void testGetViewTypeCount() {
		assertThat(mAdapterWrapper.getViewTypeCount(), is(mAdapter.getViewTypeCount()));
	}

	@Test
	public void testGetViewTypeCountOnEmptyWrapper() {
		assertThat(mEmptyWrapper.getViewTypeCount(), is(0));
	}

	@Test
	public void testGetView() {
		for (int i = 0; i < mAdapterWrapper.getCount(); i++) {
			assertThat(mAdapterWrapper.getView(i, null, mContainer), is(mAdapter.getView(i, null, mContainer)));
		}
	}

	@Test
	public void testGetViewOnEmptyWrapper() {
		for (int i = 0; i < 20; i++) {
			assertThat(mEmptyWrapper.getView(0, null, mContainer), is(nullValue()));
		}
	}

	@Test
	public void testAreAllItemsEnabled() {
		assertThat(mAdapterWrapper.areAllItemsEnabled(), is(mAdapter.areAllItemsEnabled()));
	}

	@Test
	public void testAreAllItemsEnabledOnEmptyWrapper() {
		assertThat(mEmptyWrapper.areAllItemsEnabled(), is(false));
	}

	@Test
	public void testIsEnabled() {
		for (int i = 0; i < mAdapterWrapper.getCount(); i++) {
			assertThat(mAdapterWrapper.isEnabled(i), is(mAdapter.isEnabled(i)));
		}
	}

	@Test
	public void testIsEnabledOnEmptyWrapper() {
		for (int i = 0; i < 20; i++) {
			assertThat(mEmptyWrapper.isEnabled(i), is(false));
		}
	}

	private static final class TestAdapter implements ListAdapter {

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public boolean isEmpty() {
			return getCount() == 0;
		}

		@Override
		public int getCount() {
			return 20;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public int getItemViewType(int position) {
			return position % 2 == 0 ? 0 : 1;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}

		@Override
		public boolean areAllItemsEnabled() {
			return true;
		}

		@Override
		public boolean isEnabled(int position) {
			return position % 2 == 0;
		}
	}
}