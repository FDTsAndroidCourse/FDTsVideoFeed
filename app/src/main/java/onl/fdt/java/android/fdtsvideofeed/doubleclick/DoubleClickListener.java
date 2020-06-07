package onl.fdt.java.android.fdtsvideofeed.doubleclick;

import android.os.SystemClock;
import android.view.View;

// ref https://gist.github.com/srix55/ec64d2f6a371c80bbbc4

/**
 * A simple double-click listener
 * Usage:
 * 			// Scenario 1: Setting double click listener for myView
 * 			myView.setOnClickListener(new DoubleClickListener() {
 *
 *				@Override
 *				public void onDoubleClick() {
 *					// double-click code that is executed if the user double-taps
 *					// within a span of 200ms (default).
 *				}
 *			});
 *
 * 			// Scenario 2: Setting double click listener for myView, specifying a custom double-click span time
 * 			myView.setOnClickListener(new DoubleClickListener(500) {
 *
 *				@Override
 *				public void onDoubleClick() {
 *					// double-click code that is executed if the user double-taps
 *					// within a span of 500ms (default).
 *				}
 *			});
 *
 * @author	Srikanth Venkatesh
 * @version	1.0
 * @since	2014-09-15
 */
public abstract class DoubleClickListener implements View.OnClickListener {

    // The time in which the second tap should be done in order to qualify as
    // a double click
    private static final long DEFAULT_QUALIFICATION_SPAN = 200;
    private long doubleClickQualificationSpanInMillis;
    private long timestampLastClick;

    public DoubleClickListener() {
        doubleClickQualificationSpanInMillis = DEFAULT_QUALIFICATION_SPAN;
        timestampLastClick = 0;
    }

    public DoubleClickListener(long doubleClickQualificationSpanInMillis) {
        this.doubleClickQualificationSpanInMillis = doubleClickQualificationSpanInMillis;
        timestampLastClick = 0;
    }

    @Override
    public void onClick(View v) {
        if((SystemClock.elapsedRealtime() - timestampLastClick) < doubleClickQualificationSpanInMillis) {
            onDoubleClick();
        }
        timestampLastClick = SystemClock.elapsedRealtime();
    }

    public abstract void onDoubleClick();

}
