package givlo.android.com.givlo.customviews;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class RupeeForadianTextview extends TextView {

	public RupeeForadianTextview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RupeeForadianTextview(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RupeeForadianTextview(Context context) {
		super(context);
		init();
	}

	private void init() {

			Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/Rupee_Foradian.ttf");
			setTypeface(tf);

	}

}
