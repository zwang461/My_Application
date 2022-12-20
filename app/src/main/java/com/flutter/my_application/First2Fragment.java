package com.flutter.my_application;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.NavHostFragment;

import com.flutter.my_application.databinding.FragmentFirst2Binding;

import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodChannel;

public class First2Fragment extends Fragment {

    private FragmentFirst2Binding binding;
    public FlutterEngine flutterEngine;
    private static final String CHANNEL = "samples.flutter.dev/battery";
    public String receive= "";
    public String Result= "";
    String data = "";
    EditText et;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Intent i = getActivity().getIntent();
        data = i.getStringExtra("data");
        binding = FragmentFirst2Binding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        flutterEngine = new FlutterEngine(getActivity());
//        flutterEngine.getNavigationChannel().setInitialRoute("/second");
//        flutterEngine.getDartExecutor().executeDartEntrypoint(
//                DartExecutor.DartEntrypoint.createDefault()
//        );
//        FlutterEngineCache
//                .getInstance()
//                .put("my_engine_id", flutterEngine);
//        MethodChannel myMc=new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),CHANNEL);
//        EditText et = (EditText) view.findViewById(R.id.editTextTextPersonName);

        et = (EditText) view.findViewById(R.id.editTextTextPersonName);
        Log.i( "data is", data);
        et.setText(data);





        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),MainActivity3.class);
                String x = String.valueOf(et.getText());
                i.putExtra("data", x);
                startActivityForResult(i, 000);
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String x = String.valueOf(et.getText());
                Intent intent = new Intent();
                intent.putExtra("MyData", x);
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 000) {
            if (resultCode == RESULT_OK) {
                Log.i("safe word", "I am back to N2!");
                et.setText(data.getStringExtra("MyData"));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}