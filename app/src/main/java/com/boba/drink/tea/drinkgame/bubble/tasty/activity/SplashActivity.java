package com.boba.drink.tea.drinkgame.bubble.tasty.activity;

import android.content.Intent;


import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.boba.drink.tea.drinkgame.bubble.tasty.R;
import com.boba.drink.tea.drinkgame.bubble.tasty.Utils.SharePreferenceUtils;
import com.boba.drink.tea.drinkgame.bubble.tasty.Utils.SystemUtil;
import com.boba.drink.tea.drinkgame.bubble.tasty.base.AbsBaseActivity;
import com.boba.drink.tea.drinkgame.bubble.tasty.databinding.ActivitySplashBinding;
import com.mallegan.ads.callback.InterCallback;
import com.mallegan.ads.util.Admob;
import com.mallegan.ads.util.ConsentHelper;

import java.util.Map;


public class SplashActivity extends AbsBaseActivity {


    public  static  boolean isDelayBegin = false;

    private ActivitySplashBinding binding;
    private InterCallback interCallback;

    @Override
    public void bind() {

        SystemUtil.setLocale(this);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isDelayBegin = false;
        loadAds();
    }


    private void loadAds() {
        interCallback = new InterCallback() {
            @Override
            public void onNextAction() {
                super.onNextAction();
                startActivity(new Intent(SplashActivity.this, LanguageActivity.class));
                finish();
            }
        };
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

        ConsentHelper consentHelper = ConsentHelper.getInstance(this);
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset();
        }
        consentHelper.obtainConsentAndShow(this, () -> {
            Admob.getInstance().loadSplashInterAds2(SplashActivity.this, getString(R.string.inter_splash), 3000, interCallback);
        });
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}