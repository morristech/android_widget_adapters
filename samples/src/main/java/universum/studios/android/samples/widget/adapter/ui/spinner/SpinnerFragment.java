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
package universum.studios.android.samples.widget.adapter.ui.spinner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import universum.studios.android.samples.widget.adapter.R;
import universum.studios.android.samples.widget.adapter.ui.DataSetAdapterFragment;
import universum.studios.android.samples.widget.adapter.ui.DataSets;

/**
 * @author Martin Albedinsky
 */
public final class SpinnerFragment extends DataSetAdapterFragment<SampleSpinnerAdapter, List<String>>
		implements
		AdapterView.OnItemSelectedListener {

	@SuppressWarnings("unused")
	private static final String TAG = "SpinnerFragment";

	@BindView(R.id.spinner) Spinner spinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestFeature(FEATURE_DEPENDENCIES_INJECTION);
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_spinner, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		spinner.setOnItemSelectedListener(this);
		final SampleSpinnerAdapter adapter = new SampleSpinnerAdapter(getActivity());
		adapter.registerOnDataChangeListener(this);
		adapter.registerOnDataSetListener(this);
		spinner.setAdapter(adapter);
		adapter.changeItems(DataSets.textItems(10));
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(getActivity(), "Selected " + parent.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		Toast.makeText(getActivity(), "Nothing selected", Toast.LENGTH_SHORT).show();
	}
}
