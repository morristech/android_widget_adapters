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
package universum.studios.android.samples.widget.adapter.ui.recycler;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import universum.studios.android.samples.widget.adapter.R;
import universum.studios.android.samples.widget.adapter.ui.DataSetAdapterFragment;
import universum.studios.android.samples.widget.adapter.ui.DataSets;

/**
 * @author Martin Albedinsky
 */
public final class RecyclerFragment extends DataSetAdapterFragment<SampleRecyclerAdapter, List<String>> {

	@SuppressWarnings("unused")
	private static final String TAG = "ListFragment";

	@BindView(android.R.id.list) RecyclerView recyclerView;
	@BindView(android.R.id.empty) View emptyView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestFeature(FEATURE_DEPENDENCIES_INJECTION);
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_recycler, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
		final SampleRecyclerAdapter adapter = new SampleRecyclerAdapter(getActivity());
		adapter.registerOnDataChangeListener(this);
		adapter.registerOnDataSetListener(this);
		adapter.registerOnDataSetActionListener(this);
		recyclerView.setAdapter(adapter);
		adapter.changeItems(DataSets.textItems(20));
	}

	@Override
	public void onDataSetChanged(@NonNull SampleRecyclerAdapter adapter) {
		super.onDataSetChanged(adapter);
		emptyView.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.INVISIBLE);
	}

	@Override
	public boolean onDataSetActionSelected(@NonNull SampleRecyclerAdapter adapter, int action, int position, long id, @Nullable Object payload) {
		switch (action) {
			case SampleRecyclerAdapter.CLICK:
				Toast.makeText(getActivity(), "Clicked " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
				return true;
			case SampleRecyclerAdapter.ACTION:
				adapter.removeItemAt(position);
				return true;
		}
		return super.onDataSetActionSelected(adapter, action, position, id, payload);
	}
}
