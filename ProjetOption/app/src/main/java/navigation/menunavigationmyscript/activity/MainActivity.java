package navigation.menunavigationmyscript.activity;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import navigation.menunavigationmyscript.fragments.AideFragment_;
import navigation.menunavigationmyscript.fragments.BienvenueFragment_;
import navigation.menunavigationmyscript.fragments.Enonce1Fragment_;
import navigation.menunavigationmyscript.R;
import navigation.menunavigationmyscript.architecture.AbstractFragment;
import navigation.menunavigationmyscript.architecture.IMainActivity;
import navigation.menunavigationmyscript.architecture.MyPager;
import navigation.menunavigationmyscript.architecture.Session;
import okhttp3.OkHttpClient;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements IMainActivity {

    // le conteneur de fragments
    @ViewById(R.id.container)
    protected MyPager mViewPager;

    // le gestionnaire d'onglets
    @ViewById(R.id.tabs)
    protected TabLayout tabLayout;

    // bouton flottant
    @ViewById(R.id.fab)
    protected FloatingActionButton fab;

    // injection session
    @Bean(Session.class)
    protected Session session;

    // nombre de fragments
    private final int FRAGMENTS_COUNT = 3;

    // adjacence des fragments
    private final int OFF_SCREEN_PAGE_LIMIT = FRAGMENTS_COUNT - 1;
    //private final int OFF_SCREEN_PAGE_LIMIT = 1;

    // mode debug
    public static final boolean IS_DEBUG_ENABLED = true;

    // le gestionnaire de fragments
    private SectionsPagerAdapter mSectionsPagerAdapter;

    // l'onglet n° 2
    private TabLayout.Tab tab2 = null;

    // constructeur
    public MainActivity() {
        Log.d("MainActivity", "constructor");
    }

    @AfterInject
    protected void afterInject() {
        Log.d("MainActivity", "afterInject");
        // initialisation session
        session.setNumVisit(0);
    }

    // getter session
    public Session getSession() {
        return session;
    }

    @Override
    public void navigateToView(int position) {
        // on affiche la vue position
        if (mViewPager.getCurrentItem() != position) {
            // affichage fragment
            mViewPager.setCurrentItem(position);
        }
    }

    @AfterViews
    protected void afterViews() {
        Log.d("MainActivity", "afterViews");

        // barre d'outils
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // le gestionnaire de fragments
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // le conteneur de fragments est associé au gestionnaire de fragments
        // c-a-d que le fragment n° i du conteneur de fragments est le fragment n° i délivré par le gestionnaire de fragments
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // offset des fragments
        //mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount() - 1);
        mViewPager.setOffscreenPageLimit(OFF_SCREEN_PAGE_LIMIT);

        // on inhibe le swipe entre fragments
        mViewPager.setSwipeEnabled(false);

        // pas de scrolling
        mViewPager.setScrollingEnabled(false);

        // affichage Ecran d'accueil (correspond au fragment 4)
        navigateToView(FRAGMENTS_COUNT - 1);

        // au départ on n'a qu'un seul onglet
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText("Ecran d'accueil");
        tabLayout.addTab(tab);

        // gestionnaire d'évt
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (IS_DEBUG_ENABLED) {
                    Log.d("onglets", "onTabSelected");
                }
                // un onglet a été sélectionné - on change le fragment affiché par le conteneur de fragments
                // position de l'onglet
                int position = tab.getPosition();
                // n° du fragment à afficher
                int numFragment;
                switch (position) {
                    case 0:
                        // n° fragment [écran d'accueil]
                        numFragment = FRAGMENTS_COUNT - 1;
                        break;
                    default:
                        // n° fragment
                        numFragment = session.getNumFragment();
                }
                // affichage fragment seulement si c'est nécessaire
                if (numFragment != mViewPager.getCurrentItem()) {
                    navigateToView(numFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // bouton flottant
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("menu", "création menu en cours");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // log
        if (IS_DEBUG_ENABLED) {
            Log.d("menu", "onOptionsItemSelected");
        }
        int id = item.getItemId();
        switch (id) {
            case R.id.action_terminate: {
                // Log
                if (IS_DEBUG_ENABLED) {
                    Log.d("menu", "action_terminate selected");
                }
                //on termine l'activité
                finish();
                break;
            }
            case R.id.fragment1: {
                showFragment(0);
                break;
            }
            case R.id.fragment2: {
                showFragment(1);
                break;
            }
        }
        // item traité
        return true;
    }

    private void showFragment(int i) {
        if (i < FRAGMENTS_COUNT && mViewPager.getCurrentItem() != i) {
            // si le 2ième onglet n'existe pas encore, on le crée
            if (tab2 == null) {
                tab2 = tabLayout.newTab();
                tabLayout.addTab(tab2);
            }
            // on fixe le titre des onglets
            if (i==0) {
                tab2.setText("Exercices");
            }
            else{
                tab2.setText("Aide");
            }
            // on change le fragment affiché
            navigateToView(i);
            // le n° du fragment affiché est mis en session
            session.setNumFragment(i);
            // on sélectionne l'onglet 2 - ne fait rien si celui-ci est déjà sélectionné
            tab2.select();
        }
    }

    // le gestionnaire de fragments
    // c'est à lui qu'on demande les fragments à afficher dans la vue principale
    // doit définir les méthodes [getItem] et [getCount] - les autres sont facultatives
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        // les fragments
        private AbstractFragment[] fragments;

        // n° de fragment
        private static final String ARG_SECTION_NUMBER = "section_number";

        // constructeur
        public SectionsPagerAdapter(FragmentManager fm) {
            // parent
            super(fm);
            // initialisation du tableau des fragments
            fragments = new AbstractFragment[FRAGMENTS_COUNT];
            for (int i = 0; i < fragments.length - 2 ; i++) {
                // on crée un fragment
                fragments[i] = new Enonce1Fragment_();
                // on peut passer des arguments au fragment
                Bundle args = new Bundle();
                args.putInt(ARG_SECTION_NUMBER, i + 1);
                fragments[i].setArguments(args);
            }
            fragments[1]=new AideFragment_();
            // Le dernier fragment correspond à l'écran d'accueil
            fragments[fragments.length - 1] = new BienvenueFragment_();
        }

        // fragment n° position
        @Override
        public AbstractFragment getItem(int position) {
            Log.d("MainActivity", String.format("getItem[%s]", position));
            return fragments[position];
        }

        // rend le nombre de fragments gérés
        @Override
        public int getCount() {
            return fragments.length;
        }

        /*
        // facultatif - donne un titre aux fragments gérés
        @Override
        public CharSequence getPageTitle(int position) {
            return String.format("Onglet n° %s", (position + 1));
        }
        */
    }
}
