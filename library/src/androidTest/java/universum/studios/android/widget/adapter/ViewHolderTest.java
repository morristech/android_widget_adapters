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

import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;

import universum.studios.android.widget.adapter.inner.ContextBaseTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Martin Albedinsky
 */
@RunWith(AndroidJUnit4.class)
public final class ViewHolderTest extends ContextBaseTest {

	@SuppressWarnings("unused")
	private static final String TAG = "ViewHolderTest";

	private View mItemView;
	private ViewHolder mHolder;

	@Override
	public void beforeTest() throws Exception {
		super.beforeTest();
		this.mItemView = new View(mContext);
		this.mHolder = new ViewHolder(mItemView);
	}

	@Test
	public void testInstantiation() {
		final ViewHolder holder = new ViewHolder(mItemView);
		assertThat(holder.itemView, is(mItemView));
		assertThat(holder.getAdapterPosition(), is(DataSetAdapter.NO_POSITION));
	}

	@Test
	public void testUpdateAdapterPosition() {
		mHolder.updateAdapterPosition(13);
		assertThat(mHolder.getAdapterPosition(), is(13));
	}
}