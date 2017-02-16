package navigation.menunavigationmyscript.fragments;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import navigation.menunavigationmyscript.architecture.AbstractFragment;
import navigation.menunavigationmyscript.R;

@EFragment(R.layout.welcome)
public class BienvenueFragment extends AbstractFragment {

  // les éléments de l'interface visuelle
  @ViewById(R.id.editTextNom)
  public EditText editTextNom;

  // data
  private int numVisit;

  @AfterViews
  protected void afterViews() {
    // mémoire
    afterViewsDone = true;
    // log
    if (isDebugEnabled) {
      Log.d("BienvenueFragment", String.format("afterViews %s - %s", getParentInfos(), getLocalInfos()));
    }
  }

  // gestionnaire d'évt
  @Click(R.id.buttonValider)
  protected void doValider() {
    // on affiche le nom saisi
    Toast.makeText(getActivity(), String.format("Bonjour %s", editTextNom.getText().toString()), Toast.LENGTH_LONG).show();
  }

  // infos locales pour logs
  protected String getLocalInfos() {
    return String.format("numVisit=%s", numVisit);
  }

  public String getNomUser(){
    return editTextNom.getText().toString();
  }
  // mise à jour fragment
  @Override
  protected void updateFragment() {
    // incrément n° de visite
    numVisit = session.getNumVisit();
    numVisit++;
    session.setNumVisit(numVisit);
    // on affiche le n° de la visite
    Toast.makeText(getActivity(), String.format("Visite n° %s", numVisit), Toast.LENGTH_SHORT).show();
  }
}
