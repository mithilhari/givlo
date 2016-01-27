package givlo.android.com.givlo.customviews;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class BradleyHandITCTextView extends TextView {

	public BradleyHandITCTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public BradleyHandITCTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BradleyHandITCTextView(Context context) {
		super(context);
		init();
	}

	private void init() {

			Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/BRADHITC.TTF");
			setTypeface(tf);
	}

}
