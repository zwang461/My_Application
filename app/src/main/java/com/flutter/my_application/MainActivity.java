package com.flutter.my_application;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.flutter.my_application.databinding.ActivityMainBinding;
import com.idlefish.flutterboost.FlutterBoost;
import com.idlefish.flutterboost.FlutterBoostDelegate;
import com.idlefish.flutterboost.FlutterBoostRouteOptions;
import com.idlefish.flutterboost.containers.FlutterBoostActivity;
import com.flutter.my_application.Boost;
import android.view.Menu;
import android.view.MenuItem;

import io.flutter.embedding.android.FlutterActivityLaunchConfigs;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
//import com.flutter.my_application.Boost;

public class MainActivity extends AppCompatActivity {
    public FlutterEngine flutterEngine;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        FlutterBoost.instance().setup(this, new FlutterBoostDelegate() {
//            @Override
//            public void pushNativeRoute(FlutterBoostRouteOptions options) {
//                //这里根据options.pageName来判断你想跳转哪个页面，这里简单给一个
////                Intent intent = new Intent(FlutterBoost.instance().currentActivity(), YourTargetAcitvity.class);
////                FlutterBoost.instance().currentActivity().startActivityForResult(intent, options.requestCode());
//            }
//
//            @Override
//            public void pushFlutterRoute(FlutterBoostRouteOptions options) {
//                Intent intent = new FlutterBoostActivity.CachedEngineIntentBuilder(FlutterBoostActivity.class)
//                        .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
//                        .destroyEngineWithActivity(false)
//                        .uniqueId(options.uniqueId())
//                        .url(options.pageName())
//                        .urlParams(options.arguments())
//                        .build(FlutterBoost.instance().currentActivity());
//                FlutterBoost.instance().currentActivity().startActivity(intent);
//            }
//        }, engine -> {
//        });

        flutterEngine = new FlutterEngine(this);

        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        );

        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
                .getInstance()
                .put("my_engine_id", flutterEngine);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}