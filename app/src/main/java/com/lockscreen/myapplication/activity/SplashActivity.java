package com.lockscreen.myapplication.activity;

import android.content.Intent;


import com.lockscreen.myapplication.R;
import com.lockscreen.myapplication.Utils.SystemUtil;
import com.lockscreen.myapplication.base.AbsBaseActivity;
import com.lockscreen.myapplication.databinding.ActivitySplashBinding;
import com.mallegan.ads.callback.InterCallback;
import com.mallegan.ads.util.Admob;
import com.mallegan.ads.util.ConsentHelper;



public class SplashActivity extends AbsBaseActivity {

    private ActivitySplashBinding binding;
    private InterCallback interCallback;

    @Override
    public void bind() {
        SystemUtil.setLocale(this);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
/*        if (SharePreferenceUtils.isOrganic(this)) {
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
        }*/

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