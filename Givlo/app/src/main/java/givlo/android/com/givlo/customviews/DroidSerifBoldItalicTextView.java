package givlo.android.com.givlo.customviews;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class DroidSerifBoldItalicTextView extends TextView {

	public DroidSerifBoldItalicTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public DroidSerifBoldItalicTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DroidSerifBoldItalicTextView(Context context) {
		super(context);
		init();
	}

	private void init() {

			Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/DroidSerif-BoldItalic.ttf");
			setTypeface(tf);
	}

}
