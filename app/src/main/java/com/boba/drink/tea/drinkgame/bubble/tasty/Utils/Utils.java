package com.boba.drink.tea.drinkgame.bubble.tasty.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.boba.drink.tea.drinkgame.bubble.tasty.model.LanguageHandModel;
import com.boba.drink.tea.drinkgame.bubble.tasty.model.LanguageModel;
import com.boba.drink.tea.drinkgame.bubble.tasty.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Utils {
    public static double Interest = 0.0d;
    public static double Paid = 0.0d;
    public static double Principal = 0.0d;
    public static final int REQUEST = 112;
    public static boolean isMonthly = true;
    public static boolean isYearly = false;
    public static double mTaxInsPMI;
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    public static DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public static boolean isAndroidR() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ;
    }

    public static boolean isAndroidTIRAMISU() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU;
    }
    public static String[] STORAGE_PERMISSION_STORAGE_SCOPE = {Manifest.permission.READ_EXTERNAL_STORAGE};
    public static String[] STORAGE_PERMISSION_READ_MEDIA_IMAGES = {Manifest.permission.READ_MEDIA_IMAGES};
    public static double getInterestOnly(double d, double d2) {
        return ((d / 12.0d) / 100.0d) * d2;
    }

    public static double getTotalInterest(double d, double d2, double d3) {
        return (d * d2) - d3;
    }

    public static boolean hasPermissions(Context context, String... strArr) {
        if (Build.VERSION.SDK_INT < 23 || context == null || strArr == null) {
            return true;
        }
        for (String str : strArr) {
            if (ActivityCompat.checkSelfPermission(context, str) != 0) {
                return false;
            }
        }
        return true;
    }


    public static boolean hasShowRequestPermissionRationale(
            Context context,
            String[] permissions
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        ((Activity) context),
                        permission
                )
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String[] getStoragePermissions() {
        if(isAndroidTIRAMISU()){
            return STORAGE_PERMISSION_READ_MEDIA_IMAGES;
        }
        else if (isAndroidR()) {
            return STORAGE_PERMISSION_STORAGE_SCOPE;
        } else {
            return STORAGE_PERMISSION_UNDER_STORAGE_SCOPE;
        }
    }
    public static String[] STORAGE_PERMISSION_UNDER_STORAGE_SCOPE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static boolean allPermissionGrant(Context context, String[] arrays) {
        boolean isGranted = true;
        for (String permission : arrays) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
            ) != PackageManager.PERMISSION_GRANTED
            ) {
                isGranted = false;
                break;
            }
        }
        return isGranted;
    }

    public static void setHideSoftKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static ArrayList<LanguageModel> getLanguage() {
        ArrayList<LanguageModel> listLanguage = new ArrayList<>();
        listLanguage.add(new LanguageModel("English", "en", false, R.drawable.flag_en));
        listLanguage.add(new LanguageModel("Hindi", "hi", false, R.drawable.flag_hi));
        listLanguage.add(new LanguageModel("Spanish", "es", false, R.drawable.flag_es));
        listLanguage.add(new LanguageModel("French", "fr", false, R.drawable.flag_fr));
        listLanguage.add(new LanguageModel("German", "de", false, R.drawable.flag_de));
        listLanguage.add(new LanguageModel("Italia", "it", false, R.drawable.flag_italia));
        listLanguage.add(new LanguageModel("Portuguese", "pt", false, R.drawable.flag_portugese));
        listLanguage.add(new LanguageModel("Korea", "ko", false, R.drawable.flag_korea));
        return listLanguage;
    }

    public static ArrayList<LanguageHandModel> getLanguageHand() {
        ArrayList<LanguageHandModel> listLanguageHand = new ArrayList<>();
        listLanguageHand.add(new LanguageHandModel("Hindi", "hi", false, R.drawable.flag_hi,false));
        listLanguageHand.add(new LanguageHandModel("Spanish", "es", false, R.drawable.flag_es,false));
        listLanguageHand.add(new LanguageHandModel("English", "en", false, R.drawable.flag_en,true));
        listLanguageHand.add(new LanguageHandModel("French", "fr", false, R.drawable.flag_fr,false));
        listLanguageHand.add(new LanguageHandModel("German", "de", false, R.drawable.flag_de,false));
        listLanguageHand.add(new LanguageHandModel("Italia", "it", false, R.drawable.flag_italia,false));
        listLanguageHand.add(new LanguageHandModel("Portuguese", "pt", false, R.drawable.flag_portugese,false));
        listLanguageHand.add(new LanguageHandModel("Korea", "ko", false, R.drawable.flag_korea,false));
        return listLanguageHand;
    }



    public static String captureViewToString(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        String base64String = null;
        if (bitmap != null) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        view.setDrawingCacheEnabled(false);
        return base64String;
    }

    public static Bitmap base64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }


    public static boolean isEmulator1(Context context) {
        return (Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.contains("generic") ||
                Build.FINGERPRINT.contains("unknown") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86") ||
                Build.MANUFACTURER.contains("Genymotion") ||
                Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") ||
                "google_sdk".equals(Build.PRODUCT) ||
                !hasTelephony(context) ||
                checkForEmulatorFiles());
    }

    public static boolean checkForEmulatorFiles() {
        String[] knownEmulatorFiles = {
                "/dev/socket/qemud",
                "/dev/qemu_pipe",
                "/system/lib/libc_malloc_debug_qemu.so",
                "/sys/qemu_trace",
                "/system/bin/qemu-props"
        };

        for (String file : knownEmulatorFiles) {
            File f = new File(file);
            if (f.exists()) {
                return true;  // Phát hiện file giả lập
            }
        }
        return false;
    }

    public static boolean isEmulator(Context context) {
        boolean result = isEmulator1(context);  //
        if (!hasTelephony(context)) {
            result = true;  // Nếu không có telephony (SIM), khả năng cao là giả lập
        }
        return result;

    }

    public static boolean hasTelephony(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }
}
