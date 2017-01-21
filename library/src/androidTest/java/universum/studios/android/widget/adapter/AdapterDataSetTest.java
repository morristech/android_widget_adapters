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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import universum.studios.android.widget.adapter.inner.ContextBaseTest;
import universum.studios.android.widget.adapter.inner.TestDataSets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * @author Martin Albedinsky
 */
@RunWith(AndroidJUnit4.class)
public final class AdapterDataSetTest extends ContextBaseTest {

	@SuppressWarnings("unused")
	private static final String TAG = "AdapterDataSetTest";

	private List<String> mItems = TestDataSets.textItems(10);
	private MultiDataSetListener mMultiDataSetListener = new MultiDataSetListener();
	private AdapterDataSet<TestAdapter, String> mDataSet, mEmptyDataSet;

	@Override
	public void beforeTest() throws Exception {
		super.beforeTest();
		this.mDataSet = new AdapterDataSet<>(new TestAdapter(mContext));
		this.mDataSet.attachData(mItems);
		this.mEmptyDataSet = new AdapterDataSet<>(new TestAdapter(mContext));
	}

	@Test
	public void testNotifyDataChange() {
		// Test with registered listener.
		mDataSet.registerOnDataChangeListener(mMultiDataSetListener);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataChange(null);
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_CHANGE), is(true));
		// Test without registered listener.
		mDataSet.unregisterOnDataChangeListener(mMultiDataSetListener);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataChange(null);
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_CHANGE), is(false));
		// Test with disabled callback.
		mDataSet.registerOnDataChangeListener(mMultiDataSetListener);
		mDataSet.setListenerCallbacksEnabled(AdapterDataSet.CALLBACK_DATA_CHANGE, false);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataChange(null);
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_CHANGE), is(false));
		// Test with disabled listener.
		mDataSet.registerOnDataChangeListener(mMultiDataSetListener);
		mDataSet.setListenerCallbacksEnabled(AdapterDataSet.CALLBACK_DATA_CHANGE, true);
		mDataSet.setListenersEnabled(AdapterDataSet.LISTENER_DATA_CHANGE, false);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataChange(null);
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_CHANGE), is(false));
	}

	@Test
	public void testNotifyDataChanged() {
		// Test with registered listener.
		mDataSet.registerOnDataChangeListener(mMultiDataSetListener);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataChanged(null);
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_CHANGED), is(true));
		// Test without registered listener.
		mDataSet.unregisterOnDataChangeListener(mMultiDataSetListener);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataChanged(null);
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_CHANGED), is(false));
		// Test with disabled callback.
		mDataSet.registerOnDataChangeListener(mMultiDataSetListener);
		mDataSet.setListenerCallbacksEnabled(AdapterDataSet.CALLBACK_DATA_CHANGED, false);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataChanged(null);
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_CHANGED), is(false));
		// Test with disabled listener.
		mDataSet.registerOnDataChangeListener(mMultiDataSetListener);
		mDataSet.setListenerCallbacksEnabled(AdapterDataSet.CALLBACK_DATA_CHANGED, true);
		mDataSet.setListenersEnabled(AdapterDataSet.LISTENER_DATA_CHANGE, false);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataChanged(null);
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_CHANGED), is(false));
	}

	@Test
	public void testNotifyDataSetChanged() {
		// Test with registered listener.
		mDataSet.registerOnDataSetListener(mMultiDataSetListener);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataSetChanged();
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_SET_CHANGED), is(true));
		// Test without registered listener.
		mDataSet.unregisterOnDataSetListener(mMultiDataSetListener);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataSetChanged();
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_SET_CHANGED), is(false));
		// Test with disabled callback.
		mDataSet.registerOnDataSetListener(mMultiDataSetListener);
		mDataSet.setListenerCallbacksEnabled(AdapterDataSet.CALLBACK_DATA_SET_CHANGED, false);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataSetChanged();
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_SET_CHANGED), is(false));
		// Test with disabled listener.
		mDataSet.registerOnDataSetListener(mMultiDataSetListener);
		mDataSet.setListenerCallbacksEnabled(AdapterDataSet.CALLBACK_DATA_SET_CHANGED, true);
		mDataSet.setListenersEnabled(AdapterDataSet.LISTENER_DATA_SET, false);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataSetChanged();
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_SET_CHANGED), is(false));
	}

	@Test
	public void testNotifyDataSetInvalidated() {
		// Test with registered listener.
		mDataSet.registerOnDataSetListener(mMultiDataSetListener);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataSetInvalidated();
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_SET_INVALIDATED), is(true));
		// Test without registered listener.
		mDataSet.unregisterOnDataSetListener(mMultiDataSetListener);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataSetInvalidated();
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_SET_INVALIDATED), is(false));
		// Test with disabled callback.
		mDataSet.registerOnDataSetListener(mMultiDataSetListener);
		mDataSet.setListenerCallbacksEnabled(AdapterDataSet.CALLBACK_DATA_SET_INVALIDATED, false);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataSetInvalidated();
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_SET_INVALIDATED), is(false));
		// Test with disabled listener.
		mDataSet.registerOnDataSetListener(mMultiDataSetListener);
		mDataSet.setListenerCallbacksEnabled(AdapterDataSet.CALLBACK_DATA_SET_INVALIDATED, true);
		mDataSet.setListenersEnabled(AdapterDataSet.LISTENER_DATA_SET, false);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataSetInvalidated();
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_SET_INVALIDATED), is(false));
	}

	@Test
	public void testNotifyDataSetActionSelected() {
		// Test with registered listener.
		mDataSet.registerOnDataSetActionListener(mMultiDataSetListener);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataSetActionSelected(0, 0, null);
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_SET_ACTION_SELECTED), is(true));
		// Test without registered listener.
		mDataSet.unregisterOnDataSetActionListener(mMultiDataSetListener);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataSetActionSelected(0, 0, null);
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_SET_ACTION_SELECTED), is(false));
		// Test with disabled callback.
		mDataSet.registerOnDataSetActionListener(mMultiDataSetListener);
		mDataSet.setListenerCallbacksEnabled(AdapterDataSet.CALLBACK_DATA_SET_ACTION_SELECTED, false);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataSetActionSelected(0, 0, null);
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_SET_ACTION_SELECTED), is(false));
		// Test with disabled listener.
		mDataSet.registerOnDataSetActionListener(mMultiDataSetListener);
		mDataSet.setListenerCallbacksEnabled(AdapterDataSet.CALLBACK_DATA_SET_ACTION_SELECTED, true);
		mDataSet.setListenersEnabled(AdapterDataSet.LISTENER_DATA_SET_ACTION, false);
		mMultiDataSetListener.resetReceivedCallbacks();
		mDataSet.notifyDataSetActionSelected(0, 0, null);
		assertThat(mMultiDataSetListener.hasReceivedCallback(AdapterDataSet.CALLBACK_DATA_SET_ACTION_SELECTED), is(false));
	}

	@Test
	public void testAttachData() {
		assertThat(mDataSet.getData(), is(not(nullValue())));
		assertThat(mDataSet.isEmpty(), is(false));
		assertThat(mDataSet.getItemCount(), is(mItems.size()));
		mDataSet.attachData(null);
		assertThat(mDataSet.getData(), is(nullValue()));
		assertThat(mDataSet.isEmpty(), is(true));
		assertThat(mDataSet.getItemCount(), is(0));
	}

	@Test
	public void testGetData() {
		assertThat(mDataSet.getData(), is(mItems));
		// Empty data set test.
		assertThat(mEmptyDataSet.getData(), is(nullValue()));
	}

	@Test
	public void testIsEmpty() {
		assertThat(mDataSet.isEmpty(), is(false));
		// Empty data set test.
		assertThat(mEmptyDataSet.isEmpty(), is(true));
	}

	@Test
	public void testGetItemCount() {
		assertThat(mDataSet.getItemCount(), is(mItems.size()));
		// Empty data set test.
		assertThat(mEmptyDataSet.getItemCount(), is(0));
	}

	@Test
	public void testHasItemAt() {
		assertThat(mDataSet.hasItemAt(-1), is(false));
		for (int i = 0; i < mItems.size(); i++) {
			assertThat(mDataSet.hasItemAt(i), is(true));
		}
		assertThat(mDataSet.hasItemAt(mItems.size()), is(false));
		// Empty data set test.
		for (int i = 0; i < 20; i++) {
			assertThat(mEmptyDataSet.hasItemAt(i), is(false));
		}
	}

	@Test
	public void testGetItem() {
		for (int i = 0; i < mItems.size(); i++) {
			assertThat(mDataSet.getItem(i), is(mItems.get(i)));
		}
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetItemOnEmptyDataSet() {
		mEmptyDataSet.getItem(0);
	}

	@Test
	public void testGetItemId() {
		for (int i = 0; i < 20; i++) {
			assertThat(mDataSet.getItemId(i), is((long) i));
		}
	}

	private static final class TestAdapter extends SimpleAdapter<String, View> {

		TestAdapter(@NonNull Context context) {
			super(context);
		}

		@NonNull
		@Override
		protected View onCreateView(@NonNull ViewGroup parent, int position) {
			return new View(parent.getContext());
		}

		@Override
		protected void onBindViewHolder(@NonNull View view, int position) {
		}
	}

	private static final class MultiDataSetListener
			implements
			OnDataChangeListener<TestAdapter, List<String>>,
			OnDataSetListener<TestAdapter>,
			OnDataSetActionListener<TestAdapter> {

		int receivedCallbacks;

		@Override
		public void onDataChange(@NonNull TestAdapter testAdapter, @Nullable List<String> strings) {
			this.receivedCallbacks |= AdapterDataSet.CALLBACK_DATA_CHANGE;
		}

		@Override
		public void onDataChanged(@NonNull TestAdapter testAdapter, @Nullable List<String> strings) {
			this.receivedCallbacks |= AdapterDataSet.CALLBACK_DATA_CHANGED;
		}

		@Override
		public void onDataSetChanged(@NonNull TestAdapter testAdapter) {
			this.receivedCallbacks |= AdapterDataSet.CALLBACK_DATA_SET_CHANGED;
		}

		@Override
		public void onDataSetInvalidated(@NonNull TestAdapter testAdapter) {
			this.receivedCallbacks |= AdapterDataSet.CALLBACK_DATA_SET_INVALIDATED;
		}

		@Override
		public boolean onDataSetActionSelected(@NonNull TestAdapter testAdapter, int action, int position, long id, @Nullable Object payload) {
			this.receivedCallbacks |= AdapterDataSet.CALLBACK_DATA_SET_ACTION_SELECTED;
			return false;
		}

		void resetReceivedCallbacks() {
			this.receivedCallbacks = 0;
		}

		boolean hasReceivedCallback(@AdapterDataSet.ListenerCallback  int callback) {
			return (receivedCallbacks & callback) == callback;
		}
	}
}