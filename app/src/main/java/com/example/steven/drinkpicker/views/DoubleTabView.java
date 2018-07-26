package com.example.steven.drinkpicker.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.steven.drinkpicker.R;

public class DoubleTabView extends LinearLayout {

    View rootView;
    TextView leftTab;
    TextView rightTab;

    public DoubleTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.double_tab_view, this);
        leftTab = rootView.findViewById(R.id.left_tab);
        rightTab = rootView.findViewById(R.id.right_tab);

        leftTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                leftTab.setText("Click");
                rightTab.setText("NOT");
                leftTab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                rightTab.setBackgroundColor(getResources().getColor(R.color.colorDivider));
            }
        });

        rightTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                leftTab.setText("Not");
                rightTab.setText("Click");
                rightTab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                leftTab.setBackgroundColor(getResources().getColor(R.color.colorDivider));
            }
        });
    }

}
