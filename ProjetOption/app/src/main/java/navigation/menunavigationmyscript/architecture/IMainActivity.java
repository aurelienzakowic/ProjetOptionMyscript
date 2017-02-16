package navigation.menunavigationmyscript.architecture;

public interface IMainActivity {

  // accès à la session
  Session getSession();

  // changement de vue
  void navigateToView(int position);

  // mode debug
  boolean IS_DEBUG_ENABLED = true;
}
