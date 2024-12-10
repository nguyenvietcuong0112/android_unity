package com.lockscreen.myapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {

    private static final String LAUNCHER_IOS_NAME = "AuTo_Loan";
    private static final String KEY_MortgageCalculator = "isMortgageCalculator";
    private static final String KEY_AutoLoan = "isAutoLoan";
    private static final String KEY_CalcAmortization = "isCalcAmortization";

    private static final String KEY_CalcComparison = "isCalcComparison";

    private static final String KEY_Refinance = "isCalcRefinance";

    private static final String KEY_InterestOnlyCalculator = "isInterestOnlyCalculator";
    private static final String KEY_LoanAffordabilityCalculator = "isLoanAffordabilityCalculator";

    private static final String KEY_SimpleInterestCalculator = "isSimpleInterestCalculator";

    private static final String KEY_RentalYieldCalculator = "isRentalYieldCalculator";

    private static final String KEY_PresentValue = "isPresent_PresentValue";

    private static final String KEY_NPV = "isNPV";
    private static final String KEY_Roi = "isRoi";

    private static final String KEY_TUTORIAL = "isTutorial";


    private static volatile SharePreferenceUtils instance;

    private SharedPreferences sharePreference;

    private SharePreferenceUtils(Context context) {
        sharePreference = context.getSharedPreferences(LAUNCHER_IOS_NAME, Context.MODE_PRIVATE);
    }

    public static SharePreferenceUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SharePreferenceUtils.class) {
                if (instance == null) {
                    instance = new SharePreferenceUtils(context);
                }
            }
        }
        return instance;
    }

    public void setTutorial(boolean value) {
        putBooleanValue(KEY_TUTORIAL, value);
    }

    public boolean isTutorial() {
        return getBooleanValue(KEY_TUTORIAL);
    }

    public void setFirstMortgageCalculator(boolean value) {
        putBooleanValue(KEY_MortgageCalculator, value);
    }

    public boolean isMortgageCalculator() {
        return getBooleanValue(KEY_MortgageCalculator);
    }

    public void setFirstAutoLoanCalc(boolean value) {
        putBooleanValue(KEY_AutoLoan, value);
    }

    public boolean isAutoLoanCalc() {
        return getBooleanValue(KEY_AutoLoan);
    }

    public void setCalcAmortization(boolean value) {
        putBooleanValue(KEY_CalcAmortization, value);
    }

    public boolean isCalcAmortization() {
        return getBooleanValue(KEY_CalcAmortization);
    }

    public void setCalcComparison(boolean value) {
        putBooleanValue(KEY_CalcComparison, value);
    }

    public boolean isCalcComparison() {
        return getBooleanValue(KEY_CalcComparison);
    }

    public void setCalcRefinance(boolean value) {
        putBooleanValue(KEY_Refinance, value);
    }

    public boolean isCalcCalcRefinance() {
        return getBooleanValue(KEY_Refinance);
    }

    public void setCalcInterestOnlyCalculator(boolean value) {
        putBooleanValue(KEY_InterestOnlyCalculator, value);
    }

    public boolean isInterestOnlyCalculator() {
        return getBooleanValue(KEY_InterestOnlyCalculator);
    }

    public void setLoanAffordabilityCalculator(boolean value) {
        putBooleanValue(KEY_LoanAffordabilityCalculator, value);
    }

    public boolean isLoanAffordabilityCalculator() {
        return getBooleanValue(KEY_LoanAffordabilityCalculator);
    }

    public void setSimpleInterestCalculator(boolean value) {
        putBooleanValue(KEY_SimpleInterestCalculator, value);
    }


    public boolean isSimpleInterestCalculator() {
        return getBooleanValue(KEY_SimpleInterestCalculator);
    }

    public void setRentalYieldCalculator(boolean value) {
        putBooleanValue(KEY_RentalYieldCalculator, value);
    }

    public boolean isRentalYieldCalculator() {
        return getBooleanValue(KEY_RentalYieldCalculator);
    }


    public void setPresentValue(boolean value) {
        putBooleanValue(KEY_PresentValue, value);
    }

    public boolean isPresentValue() {
        return getBooleanValue(KEY_PresentValue);
    }


    public void setNPV(boolean value) {
        putBooleanValue(KEY_NPV, value);
    }

    public boolean isNPV() {
        return getBooleanValue(KEY_NPV);
    }

    public void setRoi(boolean value) {
        putBooleanValue(KEY_Roi, value);
    }

    public boolean isRoi() {
        return getBooleanValue(KEY_Roi);
    }


    public static boolean isOrganic(Context context) {
        SharedPreferences pref = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return pref.getBoolean("organic", true);
    }

    public static void setOrganicValue(Context context, boolean value) {
        SharedPreferences pre = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean("organic", value);
        editor.apply();
    }


    private void putBooleanValue(String key, boolean value) {
        SharedPreferences.Editor editor = sharePreference.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private boolean getBooleanValue(String key) {
        return sharePreference.getBoolean(key, false);
    }


    public static boolean isFirstHome(Context context) {
        SharedPreferences pref = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return pref.getBoolean("firstHome", true);
    }

    public static void setFirstHome(Context context, boolean value) {
        SharedPreferences pre = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean("firstHome", value);
        editor.apply();
    }


    public static boolean isConfirmCurrency(Context context) {
        SharedPreferences pref = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return pref.getBoolean("ConfirmCurrency", false);
    }

    public static void setConfirmCurrency(Context context, boolean value) {
        SharedPreferences pre = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean("ConfirmCurrency", value);
        editor.apply();
    }

}
