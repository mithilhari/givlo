package givlo.android.com.givlo.customviews;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class OpenSansLightTextview extends TextView {

	public OpenSansLightTextview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public OpenSansLightTextview(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public OpenSansLightTextview(Context context) {
		super(context);
		init();
	}

	private void init() {


			Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/open-sans.light.ttf");
			setTypeface(tf);

	}

}
