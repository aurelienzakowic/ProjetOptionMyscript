package navigation.menunavigationmyscript.architecture;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Ali on 13/02/2017.
 */

// Cette classe permet de redéfinir le conteneur de fragment en désactivant certaines fonctionnalités :
// désactiver le balayage ou le scrolling

public class MyPager extends ViewPager {

    // contrôle le swipe
    private boolean isSwipeEnabled;
    // contrôle le scrolling
    private boolean isScrollingEnabled;

    // constructeurs
    public MyPager(Context context) {
        super(context);
    }

    public MyPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // méthodes à redéfinir pour gérer le swipe
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // swipe autorisé ?
        if (isSwipeEnabled) {
            return super.onInterceptTouchEvent(event);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // swipe autorisé ?
        if (isSwipeEnabled) {
            return super.onTouchEvent(event);
        } else {
            return false;
        }
    }

    // scrolling
    @Override
    public void setCurrentItem(int position){
        super.setCurrentItem(position,isScrollingEnabled);
    }

    // setters
    public void setSwipeEnabled(boolean isSwipeEnabled) {
        this.isSwipeEnabled = isSwipeEnabled;
    }

    public void setScrollingEnabled(boolean scrollingEnabled) {
        isScrollingEnabled = scrollingEnabled;
    }
}
