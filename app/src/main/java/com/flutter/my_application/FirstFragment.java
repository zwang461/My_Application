package com.flutter.my_application;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodChannel;

import com.flutter.my_application.databinding.FragmentFirstBinding;
import com.flutter.my_application.SecondFragment;
import java.util.HashMap;
import java.util.Map;

public class FirstFragment extends Fragment {
    private static final String CHANNEL = "samples.flutter.dev/battery";
    private FragmentFirstBinding binding;
    public static FlutterEngine flutterEngine;
    public Map<String,String> map = new HashMap<>();
    public String receive = "";
    public String nativeInfo = "";
    public EditText et;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public static FlutterEngine getFlutterEngine(){

        return flutterEngine;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
         et = (EditText) view.findViewById(R.id.editTextTextPersonName2);
        super.onViewCreated(view, savedInstanceState);
        flutterEngine = new FlutterEngine(getActivity());
        flutterEngine.getNavigationChannel().setInitialRoute("/first");
        flutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        );

        FlutterEngineCache
                .getInstance()
                .put("my_engine_id", flutterEngine);
        MethodChannel myMc=new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),CHANNEL);

        myMc.setMethodCallHandler((methodCall, result) ->
                {
                    if(methodCall.method.equals("openAct2")){
                       Intent i = new Intent(getActivity(),MainActivity2.class);
                       String x = String.valueOf(et.getText());
                       i.putExtra("data", x);
                        startActivityForResult(i,111);
                    }
                    else if(methodCall.method.equals("getNativeInfo"))
                    {
                        nativeInfo = String.valueOf(et.getText());
                        result.success(nativeInfo);
                        Log.i("NativeInfo:", nativeInfo);
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

        binding.flutterButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(
                        FlutterActivity
                                .withCachedEngine("my_engine_id")
                                .build(getActivity())
                );
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                nativeInfo = data.getStringExtra("MyData");
                Log.i("Safe word","I am back!");
                et.setText(nativeInfo);
                startActivity(
                        FlutterActivity
                                .withCachedEngine("my_engine_id")
                                .build(getActivity())
                );
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}