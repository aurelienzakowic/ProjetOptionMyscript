package navigation.menunavigationmyscript.architecture;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import navigation.menunavigationmyscript.activity.MainActivity;

/**
 * Created by Ali on 13/02/2017.
 */

public abstract class AbstractFragment extends Fragment {

    // données privées
    private boolean isVisibleToUser = false;
    private boolean updateDone = false;
    private String className;

    // données  accessibles aux classes filles
    protected boolean afterViewsDone = false;
    protected boolean isDebugEnabled = IMainActivity.IS_DEBUG_ENABLED;

    // activité
    protected IMainActivity mainActivity;
    protected Activity activity;

    // session
    protected Session session;

    // constructeur
    public AbstractFragment() {
        // init
        isDebugEnabled = MainActivity.IS_DEBUG_ENABLED;
        className = getClass().getSimpleName();
        // log
        if (isDebugEnabled) {
            Log.d("AbstractFragment", String.format("constructor %s", className));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // parent
        super.setUserVisibleHint(isVisibleToUser);
        // mémoire
        this.isVisibleToUser = isVisibleToUser;
        // log
        if (isDebugEnabled) {
            Log.d("AbstractFragment", String.format("setUserVisibleHint : %s", getParentInfos()));
        }
        // cas où le fragment va devenir visible
        if (isVisibleToUser) {
            // update fragment
            if (afterViewsDone && !updateDone) {
                update();
                updateDone = true;
            }
        } else {
            // on quitte le fragment
            updateDone = false;
        }
    }

    @Override
    public void onDestroyView() {
        // parent
        super.onDestroyView();
        // mise à jour indicateur
        afterViewsDone = false;
        // log
        if (isDebugEnabled) {
            Log.d("AbstractFragment", String.format("onDestroyView : %s", getParentInfos()));
        }
    }

    @Override
    public void onResume() {
        // parent
        super.onResume();
        // log
        if (isDebugEnabled) {
            Log.d("AbstractFragment", String.format("onResume : %s", getParentInfos()));
        }
        if (isVisibleToUser) {
            // update
            if (!updateDone) {
                update();
                updateDone = true;
            }
        }
    }

    // infos locales
    protected String getParentInfos() {
        return String.format("className=%s, isVisibleToUser=%s, updateDone=%s, afterViewsDone=%s", className, isVisibleToUser, updateDone, afterViewsDone);
    }

    // update fragment
    protected void update() {
        // on récupère l'activité et la session
        if (activity == null) {
            this.activity = getActivity();
            if (activity != null) {
                this.mainActivity = (IMainActivity) activity;
                this.session = this.mainActivity.getSession();
            }
        }
        // on demande à la classe fille de se mettre à jour
        updateFragment();
    }

    protected abstract void updateFragment();
}
