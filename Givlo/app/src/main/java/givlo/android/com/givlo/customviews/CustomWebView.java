package givlo.android.com.givlo.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by pchinta on 07/10/15.
 */
public class CustomWebView extends WebView {
    /**
     * Construct a new WebView with a Context object.
     * @param context A Context object used to access application assets.
     */
    public CustomWebView(Context context) {
        super(context, null);
    }

    /**
     * Construct a new WebView with layout parameters.
     * @param context A Context object used to access application assets.
     * @param attrs An AttributeSet passed to our parent.
     */
    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Construct a new WebView with layout parameters and a default style.
     * @param context A Context object used to access application assets.
     * @param attrs An AttributeSet passed to our parent.
     * @param defStyle The default style resource ID.
     */
    public CustomWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void destroy() {
        getSettings().setBuiltInZoomControls(true);
        super.destroy();
    }
}
