package com.boba.drink.tea.drinkgame.bubble.tasty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.boba.drink.tea.drinkgame.bubble.tasty.R;
import com.boba.drink.tea.drinkgame.bubble.tasty.Utils.ConstantsAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.mallegan.ads.callback.InterCallback;
import com.mallegan.ads.util.Admob;
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
                    runOnUiThread(() -> {
                        UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnOFFSound", String.valueOf(sound));
                        Intent intent = new Intent(ReciverMessageFromUnity.GetCOntext(), MainActivity.class);
                        startActivity(intent);
                    });

//                    Intent intent = new Intent(ReciverMessageFromUnity.GetCOntext(), MainActivity.class);
//                    startActivity(intent);
                    break;
                case "showAds_inter_reciver_item":
                    System.out.println("showAds_inter_reciver_itemshowAds_inter_reciver_item");
                    showInterReciverItem();
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
        play.setOnClickListener(v -> handlePlayAction("play"));
        playOrder.setOnClickListener(v -> handlePlayAction("drink"));
        playFill.setOnClickListener(v -> handlePlayAction("buffet"));

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

        loadBanner();
        loadInterReciverItem();
    }

    private void handlePlayAction(String action) {
        openUnityActivity();
        if (!SplashActivity.isDelayBegin) {
            SplashActivity.isDelayBegin = true;
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnPlayGame", action);
                UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnOFFSound", String.valueOf(sound));
            }, 2000);
        } else {
            UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnPlayGame", action);
            UnityPlayer.UnitySendMessage("FlutterAndUnityManager", "OnOFFSound", String.valueOf(sound));
        }
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

    private void loadBanner() {
        Admob.getInstance().loadCollapsibleBanner(MainActivity.this, getString(R.string.banner_collab), "bottom");
    }

    private void loadInterReciverItem() {

        runOnUiThread(() -> {
            Admob.getInstance().loadInterAds(this, getString(R.string.inter_reciver_item), new InterCallback() {
                @Override
                public void onInterstitialLoad(InterstitialAd interstitialAd) {
                    super.onInterstitialLoad(interstitialAd);
                    ConstantsAds.interReciverItem = interstitialAd;
                }
            });
        });

    }

    private void showInterReciverItem() {

        runOnUiThread(() -> {
            try {
                Admob.getInstance().showInterAds(this, ConstantsAds.interReciverItem, new InterCallback() {
                    @Override
                    public void onNextAction() {
                        super.onNextAction();
                        loadInterReciverItem();
                        runOnUiThread(() -> {
                            UnityPlayer.UnitySendMessage(
                                    "AdsFlutterAndUnityManager",
                                    "OnReciverCallbackAdsInter",
                                    "showAds_reciver_item_true"
                            );
                        });
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}