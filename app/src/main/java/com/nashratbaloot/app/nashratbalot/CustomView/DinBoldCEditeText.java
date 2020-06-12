package com.nashratbaloot.app.nashratbalot.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class DinBoldCEditeText extends EditText {

    public DinBoldCEditeText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setcustomfont(context);
    }

    public DinBoldCEditeText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setcustomfont(context);

    }

    public DinBoldCEditeText(Context context) {
            super(context);
        setcustomfont(context);
    }

    public void setcustomfont(Context _context) {



            //  english languge
            Typeface font = Typeface.createFromAsset(_context.getAssets(),
                    "fonts/font_custom.ttc");
            this.setTypeface(font);
    }

}
