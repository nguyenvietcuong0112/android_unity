package com.boba.drink.tea.drinkgame.bubble.tasty.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.boba.drink.tea.drinkgame.bubble.tasty.adapter.LanguageHandAdapter;
import com.boba.drink.tea.drinkgame.bubble.tasty.R;
import com.boba.drink.tea.drinkgame.bubble.tasty.Utils.SharePreferenceUtils;
import com.boba.drink.tea.drinkgame.bubble.tasty.Utils.SystemUtil;
import com.boba.drink.tea.drinkgame.bubble.tasty.Utils.ToastUtils;
import com.boba.drink.tea.drinkgame.bubble.tasty.Utils.Utils;
import com.boba.drink.tea.drinkgame.bubble.tasty.base.AbsBaseActivity;
import com.boba.drink.tea.drinkgame.bubble.tasty.databinding.ActivityLanguageBinding;
import com.mallegan.ads.callback.NativeCallback;
import com.mallegan.ads.util.Admob;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class LanguageActivity extends AbsBaseActivity {
    String codeLang = "en";
    String langDevice = "en";
    LanguageHandAdapter languageAdapter;

    private boolean loadNativeSelected = true;

    private ActivityLanguageBinding binding;

    @Override
    public void bind() {
        SystemUtil.setLocale(this);
        binding = ActivityLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SystemUtil.setLocale(this);
        Configuration config = new Configuration();
        Locale locale = Locale.getDefault();
        langDevice = locale.getLanguage();
        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
        Locale.setDefault(locale);
        config.locale = locale;
        SharedPreferences preferences = getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);


        if (SharePreferenceUtils.isOrganic(this)) {
            AppsFlyerLib.getInstance().registerConversionListener(this, new AppsFlyerConversionListener() {

                @Override
                public void onConversionDataSuccess(Map<String, Object> conversionData) {
                    String mediaSource = (String) conversionData.get("media_source");
                    SharePreferenceUtils.setOrganicValue(getApplicationContext(), mediaSource == null || mediaSource.isEmpty() || mediaSource.equals("organic"));
                }

                @Override
                public void onConversionDataFail(String s) {
                    // Handle conversion data failure
                }

                @Override
                public void onAppOpenAttribution(Map<String, String> map) {
                    // Handle app open attribution
                }

                @Override
                public void onAttributionFailure(String s) {
                    // Handle attribution failure
                }
            });
        }



        languageAdapter = new LanguageHandAdapter(this, Utils.getLanguageHand(), data -> {
            binding.vTranDone.setVisibility(View.GONE);
            codeLang = data.getIsoLanguage();
            binding.ivSelect.setEnabled(true);
            binding.ivSelect.setImageDrawable(ContextCompat.getDrawable(LanguageActivity.this, R.drawable.ic_select_language));
            if (loadNativeSelected) {
                loadAdsNativeLanguageSelect();
                loadNativeSelected = false;
            }

        });
        binding.rvLanguage.setAdapter(languageAdapter);
        if (SystemUtil.isNetworkConnected(LanguageActivity.this)) {
            binding.frAds.setVisibility(View.VISIBLE);
        }
        binding.ivSelect.setEnabled(false);
        binding.ivSelect.setOnClickListener(v -> {
            SystemUtil.saveLocale(getBaseContext(), codeLang);
            preferences.edit().putBoolean("language", true).apply();
            SharePreferenceUtils.getInstance(this).setTutorial(true);
            startActivity(new Intent(LanguageActivity.this, MainActivity.class));
            finish();
        });
        binding.vTranDone.setOnClickListener(v -> {
            if (loadNativeSelected) {
                ToastUtils.getInstance(this).showToast("Please select your language to continue.");
            }
        });
        ((LinearLayoutManager) Objects.requireNonNull(binding.rvLanguage.getLayoutManager())).scrollToPositionWithOffset(1, 0);
        loadAds();
    }


    private void loadAds() {
        checkNextButtonStatus(false);
        Admob.getInstance().loadNativeAd(LanguageActivity.this, getString(R.string.native_language), new NativeCallback() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                super.onNativeAdLoaded(nativeAd);
                NativeAdView adView = new NativeAdView(LanguageActivity.this);
                if (!SharePreferenceUtils.isOrganic(LanguageActivity.this)) {
                    adView = (NativeAdView) LayoutInflater.from(LanguageActivity.this).inflate(R.layout.layout_native_language_non_organic, null);
                } else {
                    adView = (NativeAdView) LayoutInflater.from(LanguageActivity.this).inflate(R.layout.layout_native_language, null);
                }
                binding.frAds.removeAllViews();
                binding.frAds.addView(adView);
                Admob.getInstance().pushAdsToViewCustom(nativeAd, adView);
                checkNextButtonStatus(true);
            }

            @Override
            public void onAdFailedToLoad() {
                super.onAdFailedToLoad();
                binding.frAds.removeAllViews();
                checkNextButtonStatus(true);
            }

        });
    }

    public void loadAdsNativeLanguageSelect() {
        NativeAdView adView;
        if (SharePreferenceUtils.isOrganic(this)) {
            adView = (NativeAdView) LayoutInflater.from(this)
                    .inflate(R.layout.layout_native_language, null);
        } else {
            adView = (NativeAdView) LayoutInflater.from(this)
                    .inflate(R.layout.layout_native_language_non_organic, null);
        }
        checkNextButtonStatus(false);

        Admob.getInstance().loadNativeAd(LanguageActivity.this, getString(R.string.native_language_select), new NativeCallback() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                binding.frAds.removeAllViews();
                binding.frAds.addView(adView);
                Admob.getInstance().pushAdsToViewCustom(nativeAd, adView);

                checkNextButtonStatus(true);
            }

            @Override
            public void onAdFailedToLoad() {
                binding.frAds.removeAllViews();
                checkNextButtonStatus(true);
            }
        });

    }

    private void checkNextButtonStatus(boolean isReady) {
        if (isReady) {
            binding.ivSelect.setVisibility(View.VISIBLE);
            binding.btnNextLoading.setVisibility(View.GONE);
        } else {
            binding.ivSelect.setVisibility(View.GONE);
            binding.btnNextLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
