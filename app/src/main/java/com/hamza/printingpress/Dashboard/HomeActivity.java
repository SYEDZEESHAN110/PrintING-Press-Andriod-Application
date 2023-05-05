package com.hamza.printingpress.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.hamza.printingpress.Api.UserAuth;
import com.hamza.printingpress.MainActivity;
import com.hamza.printingpress.R;
import com.hamza.printingpress.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    NavController navController;
    AppCompatButton logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.hamza.printingpress.databinding.ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_dashboard_content_main);

        setSupportActionBar(binding.appBarDashboardMain.toolbar);
        DrawerLayout drawer = binding.drawerLayoutDashboard;
        NavigationView navigationView = binding.navDashboardView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.dashboard_new_request, R.id.dashboard_done_request)
                .setOpenableLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View header = navigationView.getHeaderView(0);
        logoutBtn = header.findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(view -> logout());
    }

    void logout() {
        UserAuth.logout(getApplicationContext());
        Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.my_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_dashboard_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}