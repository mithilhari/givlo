package givlo.android.com.givlo.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class OpenSansSemiBoldTextview extends TextView {

	public OpenSansSemiBoldTextview(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public OpenSansSemiBoldTextview(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public OpenSansSemiBoldTextview(Context context) {
		super(context);
		init();
	}

	private void init() {


			Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/open-sans.semibold.ttf");
			setTypeface(tf);
		// Log.d("Typeface", ""+tf.toString());

	}

}
