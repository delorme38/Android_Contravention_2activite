package com.delorme.exercise11;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

public class SpeedLimitAdapter {
    @InverseBindingAdapter(attribute = "selectedSpeedLimit", event = "selectedSpeedLimitAttrChanged")
    public static String bindCountryInverseAdapter(Spinner spinner) {
        Object selectedItem = spinner.getSelectedItem();
        if (selectedItem instanceof String) {
            return (String) selectedItem;
        }
        throw new UnsupportedOperationException("Object in the adapter must be String");
    }

    @BindingAdapter(value = "selectedSpeedLimitAttrChanged", requireAll = false)
    public static void bindCountryChanged(Spinner spinner, final InverseBindingListener newTextAttrChanged) {
        if (newTextAttrChanged == null) {
            spinner.setOnItemSelectedListener(null);
            return;
        }

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                newTextAttrChanged.onChange();
            }
        };
        spinner.setOnItemSelectedListener(listener);
    }

    @BindingAdapter("selectedSpeedLimit")
    public static void bindCountryValue(Spinner spinner, String selectedValue) {
        SpinnerAdapter adapter = spinner.getAdapter();

        if (adapter == null) {
            return;
        }
        // I haven't tried this, but maybe setting invalid position will
        // clear the selection?
        int position = 0;

        for (int i = 0; i < adapter.getCount(); i++) {
            String test = adapter.getItem(i).toString();
            if (test.equalsIgnoreCase(selectedValue)) {
                Log.d(TAG, "bindCountryValue: " +test);
                position = i;
                break;
            }
        }

        if (spinner.getSelectedItemPosition() != position)
            spinner.setSelection(position);
    }
}

