package com.nashratbaloot.app.nashratbalot.widgets;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nashratbaloot.app.R;

public class NumberKeyboardView extends LinearLayout implements OnItemClickListener {
	
	private Context mContext;
	
	private LinearLayout rootView;

	private GridView grKeyboard;
	
	private List<String> listNumbers;
	
	private ListNumbersAdapter adapter;
	
	private NumberKeyboardCallback callback;
	
	public NumberKeyboardView(Context context) {
		super(context);
		this.mContext = context;
		init();
	}
	
	public NumberKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	private void init() {
		listNumbers = new ArrayList<String>();
		listNumbers.add("1");
		listNumbers.add("2");
		listNumbers.add("3");
		listNumbers.add("4");
		listNumbers.add("5");
		listNumbers.add("6");
		listNumbers.add("7");
		listNumbers.add("8");
		listNumbers.add("9");
		listNumbers.add(null);
		listNumbers.add("0");
		listNumbers.add(null);
		
		rootView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.view_number_keyboard, null, false);
		
		grKeyboard = (GridView) rootView.findViewById(R.id.grKeyboard);
		
		adapter = new ListNumbersAdapter(mContext, R.layout.item_keyboard, listNumbers);
		grKeyboard.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		grKeyboard.setOnItemClickListener(this);
		
		addView(rootView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}
	
	class ListNumbersAdapter extends ArrayAdapter<String> {

        private Context context;
        private int resource;
        private List<String> listNumbers;

        public ListNumbersAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);

            this.context = context;
            this.resource = resource;
            this.listNumbers = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

        	String number = listNumbers.get(position);
            final ListNumbersHolder holder;

            if (convertView == null) {
                // Initialize instances
                convertView = LayoutInflater.from(context).inflate(resource, parent, false);
                holder = new ListNumbersHolder();

                // TODO Find views for every single item
                holder.tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);
                holder.ibBack = (ImageView) convertView.findViewById(R.id.back);

                convertView.setTag(holder);
            } else {
                holder = (ListNumbersHolder) convertView.getTag();
            }

            // TODO Fill views
            if (!TextUtils.isEmpty(number)){ 
            	holder.tvNumber.setText(number);
            	holder.ibBack.setVisibility(View.GONE);
            	
            } else {
            	holder.tvNumber.setBackgroundResource(R.color.bg_item_empty);
            	holder.ibBack.setVisibility(View.GONE);
            	if (position == 11) holder.ibBack.setVisibility(View.VISIBLE);
            }

            return convertView;
        }

        class ListNumbersHolder {
            private TextView tvNumber;
            private ImageView ibBack;
        }
}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String number = listNumbers.get(position);
		if (!TextUtils.isEmpty(number)) callback.onClick(number);
		else callback.onClick("");
	}
	
	public void setCallback(NumberKeyboardCallback callback) {
		this.callback = callback;
	}
	
	public interface NumberKeyboardCallback {
		
		public void onClick(String number);
		
	}
}
