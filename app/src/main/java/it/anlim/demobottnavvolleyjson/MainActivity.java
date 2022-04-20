package it.anlim.demobottnavvolleyjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.btmnavMain);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.detailsFragment, R.id.settingsFragment).build();

        NavController navController = Navigation.findNavController(this,  R.id.fragmentContainerView);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}