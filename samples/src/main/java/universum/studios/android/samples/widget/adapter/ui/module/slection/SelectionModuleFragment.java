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
package universum.studios.android.samples.widget.adapter.ui.module.slection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import universum.studios.android.samples.ui.SamplesFragment;
import universum.studios.android.samples.widget.adapter.R;
import universum.studios.android.widget.adapter.module.SelectionModule;

/**
 * @author Martin Albedinsky
 */
public final class SelectionModuleFragment extends SamplesFragment {

	@SuppressWarnings("unused")
	private static final String TAG = "SelectionModuleFragment";
	private static final String BUNDLE_ADAPTER_STATE = SelectionModuleFragment.class.getName() + ".BUNDLE.AdapterState";

	@BindView(android.R.id.list) RecyclerView recyclerView;
	private ColorsSelectionAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestFeature(FEATURE_DEPENDENCIES_INJECTION);
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_recycler, container, false);
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.adapter = new ColorsSelectionAdapter(view.getContext());
		this.adapter.changeItems(Colors.dataSet());
		if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_ADAPTER_STATE)) {
			this.adapter.restoreInstanceState(savedInstanceState.getParcelable(BUNDLE_ADAPTER_STATE));
		}
		recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.selection, menu);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		switch (adapter.getSelectionMode()) {
			case SelectionModule.SINGLE:
				menu.findItem(R.id.selection_single).setChecked(true);
				break;
			case SelectionModule.MULTIPLE:
				menu.findItem(R.id.selection_multiple).setChecked(true);
				break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.selection_single:
				adapter.setSelectionMode(SelectionModule.SINGLE);
				return true;
			case R.id.selection_multiple:
				adapter.setSelectionMode(SelectionModule.MULTIPLE);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(BUNDLE_ADAPTER_STATE, adapter.saveInstanceState());
	}
}
