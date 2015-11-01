package com.groupon.maygupta.imagesearch.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.groupon.maygupta.imagesearch.R;
import com.groupon.maygupta.imagesearch.models.FilterParams;


/**
 * Created by maygupta on 10/31/15.
 */
public class SearchFilterFragment extends DialogFragment {

    private View view;
    private int position;
    Bundle args;
    OnDialogResultHandler dialogResultHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
    }

    private void populateFilter() {
        if ( args.getString("color") != null ) {
            Spinner color = (Spinner) view.findViewById(R.id.etColor);
            color.setSelection(FilterParams.getIndex(
                        args.getString("color"),
                        getResources().getStringArray(R.array.filter_colors)
            ));
        }

        if ( args.getString("size") != null ) {
            Spinner size = (Spinner) view.findViewById(R.id.etSize);
            size.setSelection(FilterParams.getIndex(
                    args.getString("size"),
                    getResources().getStringArray(R.array.filter_sizes)
            ));
        }

        if ( args.getString("type") != null ) {
            Spinner type = (Spinner) view.findViewById(R.id.etType);
            type.setSelection(FilterParams.getIndex(
                    args.getString("type"),
                    getResources().getStringArray(R.array.filter_types)
            ));
        }

        if ( args.getString("domain") != null ) {
            TextView domain = (TextView) view.findViewById(R.id.etDomain);
            domain.setText(args.getString("domain"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set title for this dialog
        getDialog().setTitle(R.string.advanced_filter);
        view = inflater.inflate(R.layout.filter_dialog, null);
        populateFilter();
        view.findViewById(R.id.save).setOnClickListener(new SaveListener());

        return view;
    }

    public FilterParams getFilterParams() {
        FilterParams filterParams = new FilterParams();

        Spinner color = (Spinner) view.findViewById(R.id.etColor);
        String colorText = color.getSelectedItem().toString();
        if (!colorText.equals("any")) {
            filterParams.color = colorText;
        }

        Spinner size = (Spinner) view.findViewById(R.id.etSize);
        String sizeText = size.getSelectedItem().toString();
        if (!sizeText.equals("any")) {
            filterParams.size = sizeText;
        }

        Spinner type = (Spinner) view.findViewById(R.id.etType);
        String typeText = type.getSelectedItem().toString();
        if (!typeText.equals("any")) {
            filterParams.type = typeText;
        }

        TextView domain = (TextView) view.findViewById(R.id.etDomain);
        String domainText = domain.getText().toString();
        if ( domainText != null && !domainText.equals("")) {
            filterParams.domain = domainText;
        }

        return filterParams;
    }

    public void setDialogResultHandler(OnDialogResultHandler handler){
        dialogResultHandler = handler;
    }

    /**
     * LISTENERS FOR ACTION BUTTONS
     **/
    private class SaveListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v) {
            dialogResultHandler.finish(getFilterParams());
            SearchFilterFragment.this.dismiss();
        }
    }

    public interface OnDialogResultHandler {
        void finish(FilterParams filterParams);
    }

}

