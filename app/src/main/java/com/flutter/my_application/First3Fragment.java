package com.flutter.my_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.flutter.my_application.databinding.FragmentFirst3Binding;

import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodChannel;

public class First3Fragment extends Fragment {

    private FragmentFirst3Binding binding;
    public FlutterEngine flutterEngine;
    public String data = "";
    public String receive = "";
    public String CHANNEL = "samples.flutter.dev/battery";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Intent i = getActivity().getIntent();
        data = i.getStringExtra("data");
        binding = FragmentFirst3Binding.inflate(inflater, container, false);
        return binding.getRoot();

    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        flutterEngine = FirstFragment.getFlutterEngine();
        flutterEngine = new FlutterEngine(getActivity());
        flutterEngine.getNavigationChannel().setInitialRoute("/second");
        flutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        );
        FlutterEngineCache
                .getInstance()
                .put("my_engine_id3", flutterEngine);
        MethodChannel myMc=new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),CHANNEL);
        EditText et = (EditText) view.findViewById(R.id.editTextTextPersonName4);
        Log.i( "data is", data);
        et.setText(data);
        myMc.setMethodCallHandler((methodCall, result) ->
                {
                    if(methodCall.method.equals("getNativeInfo"))
                    {
                        result.success(et.getText());
                    }
                    else if( methodCall.method.equals("returnFlutterInfo")){
                        receive = methodCall.argument("data");
                        et.setText(receive);
                    }
                    else
                    {
                        Log.i("new method came",methodCall.method);
                    }
                }
        );



        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x = String.valueOf(et.getText());
                Intent intent = new Intent();
                intent.putExtra("MyData", x);
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        FlutterActivity
                                .withCachedEngine("my_engine_id3")
                                .build(getActivity())

                );
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}