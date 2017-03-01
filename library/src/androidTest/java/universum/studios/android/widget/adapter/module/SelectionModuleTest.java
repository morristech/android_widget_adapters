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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import universum.studios.android.widget.adapter.SimpleAdapter;
import universum.studios.android.widget.adapter.inner.ContextBaseTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * @author Martin Albedinsky
 */
@RunWith(AndroidJUnit4.class)
public final class SelectionModuleTest extends ContextBaseTest {

	@SuppressWarnings("unused")
	private static final String TAG = "SelectionModuleTest";

	private static final List<Long> TEST_SELECTION = Arrays.asList(0L, 8L, 9L);

	private TestAdapter mAdapter;
	private SelectionModule mModule;

	@Override
	public void beforeTest() throws Exception {
		super.beforeTest();
		this.mModule = new SelectionModule();
		this.mModule.attachToAdapter(mAdapter = new TestAdapter(mContext));
	}

	@Test
	public void testSetMode() {
		assertThat(mModule.getMode(), is(SelectionModule.SINGLE));
		mModule.setMode(SelectionModule.MULTIPLE);
		assertThat(mModule.getMode(), is(SelectionModule.MULTIPLE));
	}

	@Test
	public void testGetDefaultMode() {
		assertThat(mModule.getMode(), is(SelectionModule.SINGLE));
	}

	@Test
	public void testToggleSelectionInSingleMode() {
		// Toggle with the same id.
		mModule.toggleSelection(0L);
		assertThat(mModule.isSelected(0L), is(true));
		mModule.toggleSelection(0L);
		assertThat(mModule.isSelected(0L), is(false));
		// Toggle with different ids.
		mModule.toggleSelection(1L);
		assertThat(mModule.isSelected(1L), is(true));
		mModule.toggleSelection(2L);
		assertThat(mModule.isSelected(2L), is(true));
		assertThat(mModule.isSelected(1L), is(false));
		mModule.toggleSelection(2L);
		assertThat(mModule.isSelected(2L), is(false));
		assertThat(mModule.isSelected(1L), is(false));
	}

	@Test
	public void testToggleSelectionInMultipleMode() {
		mModule.setMode(SelectionModule.MULTIPLE);
		mModule.toggleSelection(0L);
		assertThat(mModule.isSelected(0L), is(true));
		mModule.toggleSelection(0L);
		assertThat(mModule.isSelected(0L), is(false));
		mModule.toggleSelection(0L);
		assertThat(mModule.isSelected(0L), is(true));
		mModule.toggleSelection(1L);
		assertThat(mModule.isSelected(0L), is(true));
		assertThat(mModule.isSelected(1L), is(true));
		mModule.toggleSelection(2L);
		assertThat(mModule.isSelected(0L), is(true));
		assertThat(mModule.isSelected(1L), is(true));
		assertThat(mModule.isSelected(2L), is(true));
		mModule.toggleSelection(1L);
		assertThat(mModule.isSelected(0L), is(true));
		assertThat(mModule.isSelected(1L), is(false));
		assertThat(mModule.isSelected(2L), is(true));
	}

	@Test
	public void testSetSelectedInSingleMode() {
		assertThat(mModule.isSelected(0L), is(false));
		mModule.setSelected(0L, true);
		assertThat(mModule.isSelected(0L), is(true));
		assertThat(mModule.getSelectionSize(), is(1));
		mModule.setSelected(1L, true);
		assertThat(mModule.isSelected(0L), is(false));
		assertThat(mModule.isSelected(1L), is(true));
		assertThat(mModule.getSelectionSize(), is(1));
		mModule.setSelected(0L, false);
		assertThat(mModule.isSelected(0L), is(false));
		assertThat(mModule.isSelected(1L), is(true));
		assertThat(mModule.getSelectionSize(), is(1));
		mModule.setSelected(1L, false);
		assertThat(mModule.isSelected(1L), is(false));
		assertThat(mModule.getSelectionSize(), is(0));
	}

	@Test
	public void testSetSelectedInMultipleMode() {
		mModule.setMode(SelectionModule.MULTIPLE);
		assertThat(mModule.isSelected(0L), is(false));
		mModule.setSelected(0L, true);
		assertThat(mModule.isSelected(0L), is(true));
		assertThat(mModule.getSelectionSize(), is(1));
		mModule.setSelected(1L, true);
		assertThat(mModule.isSelected(0L), is(true));
		assertThat(mModule.isSelected(1L), is(true));
		assertThat(mModule.getSelectionSize(), is(2));
		mModule.setSelected(0L, false);
		assertThat(mModule.isSelected(0L), is(false));
		assertThat(mModule.isSelected(1L), is(true));
		assertThat(mModule.getSelectionSize(), is(1));
	}

	@Test
	public void testSelectRange() {
		mModule.setMode(SelectionModule.MULTIPLE);
		mModule.selectRange(0, 5);
		for (int i = 0; i < mAdapter.getItemCount(); i++) {
			assertThat(mModule.isSelected(mAdapter.getItemId(i)), is(i < 5));
		}
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSelectRangeOutOfBounds() {
		mModule.setMode(SelectionModule.MULTIPLE);
		mModule.selectRange(0, mAdapter.getItemCount() + 10);
	}

	@Test(expected = IllegalStateException.class)
	public void testSelectRangeInSingleMode() {
		mModule.selectRange(0, 1);
	}

	@Test(expected = IllegalStateException.class)
	public void testSelectRangeWithoutAttachedAdapter() {
		final SelectionModule module = new SelectionModule();
		module.setMode(SelectionModule.MULTIPLE);
		module.selectRange(0, 10);
	}

	@Test(expected = IllegalStateException.class)
	public void testClearSelectionInRangeInSingleMode() {
		mModule.clearSelectionInRange(0, 1);
	}

	@Test(expected = IllegalStateException.class)
	public void testClearSelectionInRangeWithoutAttachedAdapter() {
		final SelectionModule module = new SelectionModule();
		module.setMode(SelectionModule.MULTIPLE);
		module.setSelected(0L, true);
		module.clearSelectionInRange(0, 10);
	}

	@Test
	public void testSetSelection() {
		mModule.setSelection(TEST_SELECTION);
		final List<Long> selection = mModule.getSelection();
		assertThat(selection, is(not(nullValue())));
		assertThat(selection.size(), is(3));
		assertThat(selection.get(0), is(0L));
		assertThat(selection.get(1), is(8L));
		assertThat(selection.get(2), is(9L));
	}

	@Test
	public void testGetDefaultSelection() {
		final List<Long> selection = mModule.getSelection();
		assertThat(selection, is(not(nullValue())));
		assertThat(selection.isEmpty(), is(true));
	}

	@Test
	public void testClearSelection() {
		mModule.setSelection(TEST_SELECTION);
		assertThat(mModule.getSelectionSize(), is(TEST_SELECTION.size()));
		mModule.clearSelection();
		assertThat(mModule.getSelectionSize(), is(0));
		assertThat(mModule.getSelection(), is(not(nullValue())));
	}

	@Test
	public void testClearSelectionInRange() {
		mModule.setMode(SelectionModule.MULTIPLE);
		mModule.selectRange(0, mAdapter.getItemCount());
		for (int i = 0; i < mAdapter.getItemCount(); i++) {
			assertThat(mModule.isSelected(mAdapter.getItemId(i)), is(true));
		}
		mModule.clearSelectionInRange(mAdapter.getItemCount() - 5, 5);
		for (int i = 0; i < mAdapter.getItemCount(); i++) {
			assertThat(mModule.isSelected(mAdapter.getItemId(i)), is(i < 5));
		}
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testClearSelectionInRangeOutOfBounds() {
		mModule.setMode(SelectionModule.MULTIPLE);
		mModule.setSelected(0L, true);
		mModule.clearSelectionInRange(0, mAdapter.getItemCount() + 10);
	}

	@Test
	public void testAdapterNotifications() {
		testAdapterNotifications(true);
		testAdapterNotifications(false);
	}

	private void testAdapterNotifications(boolean enabled) {
		mModule.setMode(SelectionModule.MULTIPLE);
		mModule.setAdapterNotificationEnabled(enabled);
		mAdapter.resetReceivedCallbacks();
		mModule.toggleSelection(0L);
		assertThat(mAdapter.notifyDataSetChangedReceived, is(enabled));
		mModule.clearSelection();
		// Set selected.
		mAdapter.resetReceivedCallbacks();
		mModule.setSelected(0L, true);
		assertThat(mAdapter.notifyDataSetChangedReceived, is(enabled));
		mModule.clearSelection();
		// Select all.
		mAdapter.resetReceivedCallbacks();
		mModule.selectAll();
		assertThat(mAdapter.notifyDataSetChangedReceived, is(enabled));
		mModule.clearSelection();
		// Select range.
		mAdapter.resetReceivedCallbacks();
		mModule.selectRange(0, 5);
		assertThat(mAdapter.notifyDataSetChangedReceived, is(enabled));
		mModule.clearSelection();
		// Clear selection.
		mModule.setSelected(0L, true);
		mAdapter.resetReceivedCallbacks();
		mModule.clearSelection();
		assertThat(mAdapter.notifyDataSetChangedReceived, is(enabled));
		mModule.clearSelection();
		// Clear selection in range.
		mModule.setSelection(TEST_SELECTION);
		mAdapter.resetReceivedCallbacks();
		mModule.clearSelectionInRange(0, TEST_SELECTION.size() - 1);
		assertThat(mAdapter.notifyDataSetChangedReceived, is(enabled));
		mModule.clearSelection();
	}

	private static final class TestAdapter extends SimpleAdapter<String, View> implements AdapterModule.ModuleAdapter {

		private boolean notifyDataSetChangedReceived;

		TestAdapter(@NonNull Context context) {
			super(context, new String[]{
					"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
			});
		}

		void resetReceivedCallbacks() {
			this.notifyDataSetChangedReceived = false;
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
			this.notifyDataSetChangedReceived = true;
		}

		@NonNull
		@Override
		protected View onCreateView(@NonNull ViewGroup parent, int position) {
			return new View(parent.getContext());
		}

		@Override
		protected void onBindViewHolder(@NonNull View viewHolder, int position) {
		}
	}
}