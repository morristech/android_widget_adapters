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
package universum.studios.android.samples.widget.adapter.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import universum.studios.android.samples.ui.SamplesFragment;
import universum.studios.android.widget.adapter.OnDataChangeListener;
import universum.studios.android.widget.adapter.OnDataSetActionListener;
import universum.studios.android.widget.adapter.OnDataSetListener;

/**
 * @author Martin Albedinsky
 */
public abstract class DataSetAdapterFragment<Adapter, Data> extends SamplesFragment
		implements
		OnDataChangeListener<Adapter, Data>,
		OnDataSetListener<Adapter>,
		OnDataSetActionListener<Adapter> {

	@SuppressWarnings("unused")
	private static final String TAG = "DataSetAdapterFragment";

	@Override
	public void onDataChange(@NonNull Adapter adapter, @Nullable Data data) {
		Log.d(TAG, "onDataChange(adapter: " + adapter.getClass() + ", data: " + data + ")");
	}

	@Override
	public void onDataChanged(@NonNull Adapter adapter, @Nullable Data data) {
		Log.d(TAG, "onDataChanged(adapter: " + adapter.getClass() + ", data: " + data + ")");
	}

	@Override
	public void onDataSetChanged(@NonNull Adapter adapter) {
		Log.d(TAG, "onDataSetChanged(adapter: " + adapter.getClass() + ")");
	}

	@Override
	public void onDataSetInvalidated(@NonNull Adapter adapter) {
		Log.d(TAG, "onDataSetInvalidated(adapter: " + adapter.getClass() + ")");
	}

	@Override
	public boolean onDataSetActionSelected(@NonNull Adapter adapter, int action, int position, long id, @Nullable Object payload) {
		Log.d(TAG, "onDataSetActionSelected(adapter: " + adapter.getClass() + ", action: " + action + ", position: " + position + ", id: " + id + ", payload: " + payload + ")");
		return false;
	}
}
