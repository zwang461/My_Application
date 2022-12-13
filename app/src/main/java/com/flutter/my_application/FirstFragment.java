package com.flutter.my_application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import io.flutter.embedding.android.FlutterActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.FlutterActivityLaunchConfigs;
import io.flutter.embedding.android.FlutterFragment;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.common.StringCodec;

import com.flutter.my_application.databinding.FragmentFirstBinding;
import com.flutter.my_application.MainActivity;
import com.flutter.my_application.Boost;
import com.idlefish.flutterboost.FlutterBoost;
import com.idlefish.flutterboost.FlutterBoostRouteOptions;
import com.idlefish.flutterboost.containers.FlutterBoostActivity;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class FirstFragment extends Fragment {
    private static final String TAG_FLUTTER_FRAGMENT = "flutter_fragment";
    private FlutterFragment flutterFragment;
    private FragmentFirstBinding binding;
    public FlutterEngine flutterEngine;
    public Map<String,String> map = new HashMap<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flutterEngine = new FlutterEngine(getActivity());
        flutterEngine.getNavigationChannel().setInitialRoute("/");
        flutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        );

        FlutterEngineCache
                .getInstance()
                .put("my_engine_id", flutterEngine);

        map.put("data","ssssss");
        EditText et = (EditText) view.findViewById(R.id.editTextTextPersonName2);
        et.setText(map.toString());

//        final String CHANNEL = "AndySample/test";
//        MethodChannel mc=new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),CHANNEL);
//        mc.setMethodCallHandler((methodCall, result) ->
//                {
//                    if(methodCall.method.equals("test"))
//                    {
//                        et.setText("123");
//                        result.success("Hai from android and this is the data you sent me "+ methodCall.argument("data"));
////Accessing data sent from flutter
//                    }
//                    else
//                    {
//                        Log.i("new method came",methodCall.method);
//                    }
//                }
//        );


//        BasicMessageChannel<Object> basicMessageChannel = new BasicMessageChannel<>(flutterFragment.getFlutterEngine().getDartExecutor().getBinaryMessenger(),
//                "flutter_and_native_100", StandardMessageCodec.INSTANCE);

//        basicMessageChannel.setMessageHandler((message, reply) -> {
//            mTvDart.setText(message);
//            reply.reply("Received dart Data: accepted successfully");
//        });
//
////send message
//
//        basicMessageChannel.send(message, reply -> mTvDart.setText(reply));


        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);

//                FlutterBoostRouteOptions options = new FlutterBoostRouteOptions.Builder()
//                        .pageName("mainPage")
//                        .arguments(map)
//                        .requestCode(1111)
//                        .build();
//
//                FlutterBoost.instance().open(options);
                map.put("data","111");

                startActivity(
                        FlutterActivity
                                .withCachedEngine("my_engine_id")
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