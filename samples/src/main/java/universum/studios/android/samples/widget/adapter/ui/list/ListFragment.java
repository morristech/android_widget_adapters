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
package universum.studios.android.samples.widget.adapter.ui.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import universum.studios.android.samples.widget.adapter.R;
import universum.studios.android.samples.widget.adapter.ui.DataSetAdapterFragment;
import universum.studios.android.samples.widget.adapter.ui.DataSets;

/**
 * @author Martin Albedinsky
 */
public final class ListFragment extends DataSetAdapterFragment<SampleAdapter, List<String>>
		implements
		AdapterView.OnItemClickListener {

	@SuppressWarnings("unused")
	private static final String TAG = "ListFragment";

	@BindView(android.R.id.list) ListView listView;
	@BindView(android.R.id.empty) View emptyView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestFeature(FEATURE_DEPENDENCIES_INJECTION);
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_list, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView.setEmptyView(emptyView);
		listView.setOnItemClickListener(this);
		final SampleAdapter adapter = new SampleAdapter(getActivity());
		adapter.registerOnDataChangeListener(this);
		adapter.registerOnDataSetListener(this);
		adapter.registerOnDataSetActionListener(this);
		listView.setAdapter(adapter);
		adapter.changeItems(DataSets.textItems(20));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(parent.getContext(), "Clicked " + parent.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onDataSetActionSelected(@NonNull SampleAdapter adapter, int action, int position, long id, @Nullable Object payload) {
		switch (action) {
			case SampleAdapter.ACTION:
				adapter.removeItemAt(position);
				return true;
		}
		return super.onDataSetActionSelected(adapter, action, position, id, payload);
	}
}
