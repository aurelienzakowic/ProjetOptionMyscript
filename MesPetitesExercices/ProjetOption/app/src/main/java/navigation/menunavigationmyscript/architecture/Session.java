package navigation.menunavigationmyscript.architecture;

import org.androidannotations.annotations.EBean;

/**
 * Created by Ali on 12/02/2017.
 */

// On instancie la classe une seule fois au démarrage (singleton)
@EBean(scope = EBean.Scope.Singleton)
public class Session {

    // nombre de fragments visités
    private int numVisit;

    // n° fragment de type [PlaceholderFragment] affiché dans second onglet
    private int numFragment;


    public int getNumVisit() {
        return numVisit;
    }

    public void setNumVisit(int numVisit) {
        this.numVisit = numVisit;
    }

    public int getNumFragment() {
        return numFragment;
    }

    public void setNumFragment(int numFragment) {
        this.numFragment = numFragment;
    }
}
