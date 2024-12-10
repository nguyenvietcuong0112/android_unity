package com.lockscreen.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.lockscreen.myapplication.R;
import com.unity3d.player.CallbackManager;
import com.unity3d.player.ReciverMessageFromUnity;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class MainActivity extends AppCompatActivity {

    boolean sound = true;
    boolean vibra = true;

    private static final String PREF_NAME = "AppPreferences";
    private static final String KEY_SOUND = "sound";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        sound = getSoundPreference();

        ImageView play = findViewById(R.id.play_play);
        ImageView playOrder = findViewById(R.id.play_order_now);
        ImageView playFill = findViewById(R.id.play_fill);
        ImageView icVibra = findViewById(R.id.ic_vibra);
        ImageView icSound = findViewById(R.id.ic_sound);

        ReciverMessageFromUnity.callbackManager = new CallbackManager();

        ReciverMessageFromUnity.callbackManager.setCallback(message -> {
            switch (message) {
                case "back":
                    SplashActivity.isDelayBegin = true;
                    getSoundPreference();
                    UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnOFFSound", String.valueOf(sound));
                    Intent intent = new Intent(ReciverMessageFromUnity.GetCOntext(), MainActivity.class);
                    startActivity(intent);
                    break;
                case "showAds_inter_default":
                    UnityPlayer.UnitySendMessage("AdsFlutterAndUnityManager", "OnReciverCallbackAdsInter", "showAds_default_true");
                    break;
                case "showAds_inter_reciver_item":
                    UnityPlayer.UnitySendMessage("AdsFlutterAndUnityManager", "OnReciverCallbackAdsInter", "showAds_reciver_item_true");
                    break;
                case "HomeScene":
                    break;
                case "SceneGame":
                    break;
                default:
                    Log.d("MessageHandler", "Unknown message: " + message);
                    break;
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openUnityActivity();
                if (!SplashActivity.isDelayBegin) {
                    SplashActivity.isDelayBegin = true;
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnPlayGame", "play");
                            UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnOFFSound", String.valueOf(sound));
                        }
                    }, 2000); // 2000ms = 2 giây
                } else {
                    UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnPlayGame", "play");
                    UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnOFFSound", String.valueOf(sound));
                }
            }
        });
        playOrder.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openUnityActivity();
                if (!SplashActivity.isDelayBegin) {
                    SplashActivity.isDelayBegin = true;
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnPlayGame", "drink");
                            UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnOFFSound", String.valueOf(sound));
                        }
                    }, 2000); // 2000ms = 2 giây
                } else {
                    UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnPlayGame", "drink");
                    UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnOFFSound", String.valueOf(sound));
                }
            }
        });
        playFill.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openUnityActivity();
                if (!SplashActivity.isDelayBegin) {
                    SplashActivity.isDelayBegin = true;
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnPlayGame", "buffet");
                            UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnOFFSound", String.valueOf(sound));
                        }
                    }, 2000);
                } else {
                    UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnPlayGame", "buffet");
                    UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnOFFSound", String.valueOf(sound));
                }
            }
        });

        if (sound) {
            icSound.setImageResource(R.drawable.icon_sound);
        } else {
            icSound.setImageResource(R.drawable.icon_no_sound);
        }

        icSound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sound = !sound;
                if (sound) {
                    icSound.setImageResource(R.drawable.icon_sound);
                } else {
                    icSound.setImageResource(R.drawable.icon_no_sound);
                }
                saveSoundPreference(sound);
            }
        });
        icVibra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibra = !vibra;
                if (vibra) {
                    vibra = false;
                    icVibra.setImageResource(R.drawable.icon_vibration);
                } else {
                    icVibra.setImageResource(R.drawable.icon_no_vibration);

                }
            }
        });
    }

    private void openUnityActivity() {
        Intent intent = new Intent(MainActivity.this, UnityPlayerActivity.class);
        startActivity(intent);
    }

    private void saveSoundPreference(boolean soundValue) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_SOUND, soundValue);
        editor.apply();
    }

    private boolean getSoundPreference() {
        return sharedPreferences.getBoolean(KEY_SOUND, true);
    }

}