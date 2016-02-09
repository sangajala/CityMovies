package com.bananalabs.citymovies;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Chaitanya on 10/30/2015.
 */
public class AppConstants {

    public static String TAG = "AppConstants";
    public static String exceptionTitle = null;
    public static String exceptionMessage = null;
    public static SharedPreferences preferences;
    public static Activity _Activity;
    public static String DEVICE_ID;

    public AppConstants(Activity activity) {

        _Activity = activity;
        preferences = PreferenceManager.getDefaultSharedPreferences(_Activity);
        DEVICE_ID = Settings.Secure.getString(_Activity
                .getContentResolver(), Settings.Secure.ANDROID_ID);

    }

    // check net is connecting or net
    public static boolean isConnectingToInternet() {

        ConnectivityManager connectivity = (ConnectivityManager) _Activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }


    public static String getViewData(View v) {
        String result = "";
        if (v != null && v instanceof EditText) {
            EditText et = (EditText) v;
            result = et.getText().toString();
        } else if (v != null && v instanceof Spinner) {
            Spinner spn = (Spinner) v;
            if (spn.getSelectedItemPosition() != 0)
                result = spn.getSelectedItem().toString();
        } else if (v != null && v instanceof SeekBar) {
            SeekBar sb = (SeekBar) v;
            result = "" + sb.getProgress();
        } else if (v != null && v instanceof CheckBox) {
            CheckBox cb = (CheckBox) v;
            result = cb.getText().toString();
        } else if (v != null && v instanceof DatePicker) {
            DatePicker dt = (DatePicker) v;
            int mon = dt.getMonth() + 1, date = dt.getDayOfMonth();
            String str_Mon = "", str_Date = "";
            if (mon < 10)
                str_Mon = "0" + mon;
            else
                str_Mon = "" + mon;
            if (date < 10)
                str_Date = "0" + date;
            else
                str_Date = "" + date;

            result = dt.getYear() + "-" + str_Mon + "-" + str_Date;
        } else if (v != null && v instanceof Button) {
            Button btn = (Button) v;
            result = btn.getText().toString();
        } else if (v != null && v instanceof TextView) {
            TextView tv = (TextView) v;
            result = tv.getText().toString();
        }
        return result.trim();
    }

    public static boolean checkMobileNo(String num) {
        boolean flag = false;
        if (num.startsWith("9") || num.startsWith("8") || num.startsWith("7"))
            flag = true;
        return flag;
    }

    public static boolean hasMobileNumber(EditText editText) {

        String text = editText.getText().toString().trim();

        if (text.length() == 10) {
            return false;
        }

        return true;
    }

    public static boolean emailValidator(String email) {

        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void alertMessageDialog(String title, String message,
                                   final String from) {

        Button CTE_btn_ok, CTE_btn_search, CTE_btn_cancel;
        final EditText CTE_alert_search_buy_lead;
        TextView BL_alert_text;

        final Dialog myDialog = new Dialog(_Activity, R.style.ThemeDialogCustom);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.custom_alert_dialogue);
        myDialog.setCancelable(true);
        myDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        TextView BL_alert_head = (TextView) myDialog
                .findViewById(R.id.SVN_alert_title);
        BL_alert_text = (TextView) myDialog.findViewById(R.id.CTE_alert_text);

        BL_alert_head.setText(title);
        BL_alert_text.setText(message);

        CTE_btn_ok = (Button) myDialog.findViewById(R.id.CTE_btn_ok);
        CTE_btn_search = (Button) myDialog.findViewById(R.id.CTE_btn_search);
        CTE_btn_cancel = (Button) myDialog.findViewById(R.id.CTE_btn_cancel);

        CTE_alert_search_buy_lead = (EditText) myDialog
                .findViewById(R.id.CTE_alert_search_buy_lead);
        myDialog.show();

        if (from.equals("search_buy_lead")) {
            CTE_alert_search_buy_lead.setVisibility(View.VISIBLE);
            CTE_btn_search.setVisibility(View.VISIBLE);
            CTE_btn_cancel.setVisibility(View.VISIBLE);
            BL_alert_text.setVisibility(View.GONE);
            CTE_btn_ok.setVisibility(View.GONE);
        } else if (from.equals("search_sell_lead")) {
            CTE_alert_search_buy_lead.setVisibility(View.VISIBLE);
            CTE_btn_search.setVisibility(View.VISIBLE);
            CTE_btn_cancel.setVisibility(View.VISIBLE);
            BL_alert_text.setVisibility(View.GONE);
            CTE_btn_ok.setVisibility(View.GONE);
            CTE_alert_search_buy_lead
                    .setHint("Selllead id/name/email/mobile/make/model");
        } else if (from.equals("1")) { // Last Activity
            CTE_btn_ok.setVisibility(View.GONE);
            CTE_btn_search.setText("OK");
            CTE_btn_search.setVisibility(View.VISIBLE);
            CTE_btn_cancel.setText("Back");
            CTE_btn_cancel.setVisibility(View.VISIBLE);
        }
        CTE_btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (from.equals("Login") || from.equals("register")) {
                    Intent objintent = new Intent(_Activity, HomeActivity.class);
                    _Activity.startActivity(objintent);
                    _Activity.finish();
                }

                myDialog.dismiss();
            }
        });

        CTE_btn_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (from.equals("search_buy_lead")) {
                    String serach_key = CTE_alert_search_buy_lead.getText()
                            .toString();
//                    Intent buulead_search = new Intent(AppConstants.this,
//                            ManageOnlineBuyLeadActivity.class);
//                    buulead_search.putExtra("search_key", serach_key);
//                    buulead_search.putExtra("checkleads", "yes");
                    if (serach_key.length() != 0) {
//                        startActivity(buulead_search);
                        myDialog.dismiss();
                    } else {
                        Toast.makeText(
                                _Activity,
                                "Please Enter Buy Lead Id/Name/Mobile/Email/Make...",
                                Toast.LENGTH_SHORT).show();
                    }
                } else if (from.equals("search_sell_lead")) {
                    String serach_key = CTE_alert_search_buy_lead.getText()
                            .toString();
//                    Intent sellead_search = new Intent(AppConstants.this,
//                            ManageOnLineInspection.class);
//                    sellead_search.putExtra("search_key", serach_key);
//                    sellead_search.putExtra("checkleads", "yes");

                    if (serach_key.length() != 0) {
                        //startActivity(sellead_search);
                        myDialog.dismiss();
                    } else {
                        Toast.makeText(
                                _Activity,
                                "Please Enter Sell Lead Id/Name/Mobile/Email/Make..",
                                Toast.LENGTH_SHORT).show();
                    }
                } else if (from.equals("1")) {
                    myDialog.dismiss();
                }
            }
        });

        CTE_btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                if (from.equals("1")) {
//                    Intent HomeActivityEvaluation = new Intent(
//                            getApplicationContext(), NextVehicleActivity.class);
//                    HomeActivityEvaluation
//                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(HomeActivityEvaluation);
                }
            }
        });
    }

    public static boolean validateET(EditText et) {
        boolean flag = false;
        if (et.getText().toString().trim().length() == 0) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public static String postparamsMethodUrl(final Context context,
                                             String url, List<NameValuePair> params) {

        String res = "";
        try {

            HttpPost httppost = new HttpPost(url);
            HttpParams httpParameters = new BasicHttpParams();
            // Set the timeout in milliseconds until a connection is
            // established.
            // The default value is zero, that means the timeout is not used.
            int timeoutConnection = 180000;
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 300000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            httpclient.getParams().setParameter(
                    CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

            httppost.addHeader("Accept-Encoding", "gzip, deflate");
            httppost.setEntity(new UrlEncodedFormEntity(params));
            // ResponseHandler<String> resHandler = new BasicResponseHandler();
            HttpResponse responce = httpclient.execute(httppost);
            res = "" + getIfCompressed(responce);
            Log.e("responce after decompress", res);
            // Log.e("res from post method", res);
            httpclient.getConnectionManager().shutdown();

        } catch (HttpHostConnectException e) {
            e.printStackTrace();
            Log.e(TAG, "network connection Exception");
            res = "neterror";

        } catch (SocketTimeoutException socketExaception) {
            Log.e(TAG, "Socket Exception");
            socketExaception.printStackTrace();
            res = "serverDown";
            // res = "CarTrade server is in down. please try after some time";
        } catch (ConnectTimeoutException connectionTimeOut) {
            Log.e(TAG, "connectin timeout");
            connectionTimeOut.printStackTrace();
            res = "connectinTimeOut";
            // res = "CarTrade server is in down. please try after some time";
        } catch (UnknownHostException e) {
            e.printStackTrace();
            res = "neterror";
            Log.e(TAG, "UnknownHostException");
        } catch (ClientProtocolException e) {
            Log.e(TAG, "ClientProtocolException in valid  responce");
            e.printStackTrace();
            res = "neterror";
        } catch (IOException e) {
            Log.e(TAG, "IOException");
            e.printStackTrace();
            res = "neterror";
        } catch (Exception e) {
            e.printStackTrace();
            res = "serverIssue";
        }

        return res;

    }


    public static String postMethodUrl(final Context context,
                                       String params) {

        String res = "";
        try {

            HttpPost httppost = new HttpPost(params);
            HttpParams httpParameters = new BasicHttpParams();
            // Set the timeout in milliseconds until a connection is
            // established.
            // The default value is zero, that means the timeout is not used.
            int timeoutConnection = 180000;
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 300000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            httpclient.getParams().setParameter(
                    CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

            httppost.addHeader("Accept-Encoding", "gzip, deflate");
            //httppost.setEntity(new UrlEncodedFormEntity(params));
            // ResponseHandler<String> resHandler = new BasicResponseHandler();
            HttpResponse responce = httpclient.execute(httppost);
            res = "" + getIfCompressed(responce);
            Log.e("responce after decompress", res);
            // Log.e("res from post method", res);
            httpclient.getConnectionManager().shutdown();

        } catch (HttpHostConnectException e) {
            e.printStackTrace();
            Log.e(TAG, "network connection Exception");
            res = "neterror";

        } catch (SocketTimeoutException socketExaception) {
            Log.e(TAG, "Socket Exception");
            socketExaception.printStackTrace();
            res = "serverDown";
            // res = "CarTrade server is in down. please try after some time";
        } catch (ConnectTimeoutException connectionTimeOut) {
            Log.e(TAG, "connectin timeout");
            connectionTimeOut.printStackTrace();
            res = "connectinTimeOut";
            // res = "CarTrade server is in down. please try after some time";
        } catch (UnknownHostException e) {
            e.printStackTrace();
            res = "neterror";
            Log.e(TAG, "UnknownHostException");
        } catch (ClientProtocolException e) {
            Log.e(TAG, "ClientProtocolException in valid  responce");
            e.printStackTrace();
            res = "neterror";
        } catch (IOException e) {
            Log.e(TAG, "IOException");
            e.printStackTrace();
            res = "neterror";
        } catch (Exception e) {
            e.printStackTrace();
            res = "serverIssue";
        }

        return res;

    }

    public static String GETMethodUrl(final Context context,
                                      String params) {

        String res = "";
        try {

            Log.e("params", params.toString());

            HttpGet httppost = new HttpGet(params);


            //HttpPost httppost = new HttpPost(url);
            HttpParams httpParameters = new BasicHttpParams();
            // Set the timeout in milliseconds until a connection is
            // established.
            // The default value is zero, that means the timeout is not used.
            int timeoutConnection = 180000;
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 300000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            httpclient.getParams().setParameter(
                    CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

            httppost.addHeader("Accept-Encoding", "gzip, deflate");
            //httppost.setEntity(new UrlEncodedFormEntity(params));
            // ResponseHandler<String> resHandler = new BasicResponseHandler();
            HttpResponse responce = httpclient.execute(httppost);
            res = "" + getIfCompressed(responce);
            Log.e("responce after decompress", res);
            // Log.e("res from post method", res);
            httpclient.getConnectionManager().shutdown();

        } catch (HttpHostConnectException e) {
            e.printStackTrace();
            Log.e(TAG, "network connection Exception");
            res = "neterror";

        } catch (SocketTimeoutException socketExaception) {
            Log.e(TAG, "Socket Exception");
            socketExaception.printStackTrace();
            res = "serverDown";
            // res = "CarTrade server is in down. please try after some time";
        } catch (ConnectTimeoutException connectionTimeOut) {
            Log.e(TAG, "connectin timeout");
            connectionTimeOut.printStackTrace();
            res = "connectinTimeOut";
            // res = "CarTrade server is in down. please try after some time";
        } catch (UnknownHostException e) {
            e.printStackTrace();
            res = "neterror";
            Log.e(TAG, "UnknownHostException");
        } catch (ClientProtocolException e) {
            Log.e(TAG, "ClientProtocolException in valid  responce");
            e.printStackTrace();
            res = "neterror";
        } catch (IOException e) {
            Log.e(TAG, "IOException");
            e.printStackTrace();
            res = "neterror";
        } catch (Exception e) {
            e.printStackTrace();
            res = "serverIssue";
        }

        return res;

    }

    public static String getIfCompressed(HttpResponse response) {
        if (response == null)
            return null;

        try {
            InputStream is = AndroidHttpClient.getUngzippedContent(response
                    .getEntity());
            return convertStreamToString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static byte[] compress(String string) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(string.length());
        GZIPOutputStream gos = new GZIPOutputStream(os);
        gos.write(string.getBytes());
        gos.close();
        byte[] compressed = os.toByteArray();
        os.close();
        return compressed;
    }

    public static String decompress(byte[] compressed) throws IOException {
        final int BUFFER_SIZE = 32;
        ByteArrayInputStream is = new ByteArrayInputStream(compressed);
        GZIPInputStream gis = new GZIPInputStream(is, BUFFER_SIZE);
        StringBuilder string = new StringBuilder();
        byte[] data = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = gis.read(data)) != -1) {
            string.append(new String(data, 0, bytesRead));
        }
        gis.close();
        is.close();
        return string.toString();
    }


    public static boolean isNotNull(String data) {
        boolean flag = false;
        if (data != null && data.trim().length() > 0
                && !data.equalsIgnoreCase("null"))
            flag = true;

        return flag;
    }

    public static boolean isNotNull(Object data) {
        boolean flag = false;
        if (data != null)
            flag = true;

        return flag;
    }

    public static boolean datecompare(Date inputdate) {

        boolean datecom = true;

        try {


            Date now = new Date(System.currentTimeMillis());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date curdate1 = sdf.parse(sdf.format(now));
            Date inpdate2 = sdf.parse(sdf.format(inputdate));


            if (curdate1.after(inpdate2)) {
                System.out.println("Date1 is after Date2");
                datecom = false;
            }

            if (curdate1.before(inpdate2)) {
                System.out.println("Date1 is before Date2");
            }

            if (curdate1.equals(inpdate2)) {
                System.out.println("Date1 is equal Date2");
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return datecom;
    }


    public static boolean datescompare(Date inputdate, Date outputdate) {

        boolean datecom = true;

        try {


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date checkin = sdf.parse(sdf.format(inputdate));
            Date checkout = sdf.parse(sdf.format(outputdate));


            if (checkout.after(checkin)) {
                System.out.println("Date1 is after Date2");
            }

            if (checkout.before(checkin)) {
                System.out.println("Date1 is before Date2");
                datecom = false;
            }

            if (checkout.equals(checkin)) {
                System.out.println("Date1 is equal Date2");
                datecom = false;
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return datecom;
    }

    public void exceptionHandling(String responce) {
        if (responce.equals("neterror")) {
            exceptionTitle = _Activity.getResources().getString(
                    R.string.networkErrorTitle);
            exceptionMessage = _Activity.getResources().getString(
                    R.string.networkErrorMessage);
            Log.e(TAG, "neterror");

        } else if (responce.equals("serverDown")) {
            exceptionTitle = _Activity.getResources()
                    .getString(R.string.socketErrorTitle);
            exceptionMessage = _Activity.getResources().getString(
                    R.string.socketErrorMessage);
            Log.e(TAG, "serverDown");
        } else if (responce.equalsIgnoreCase("connectinTimeOut")) {
            exceptionTitle = _Activity.getResources().getString(
                    R.string.connectionTimeoutTitle);
            exceptionMessage = _Activity.getResources().getString(
                    R.string.connectionTimeoutMessage);
            Log.e(TAG, "connectinTimeOut");
        } else if (responce.equalsIgnoreCase("serverIssue")) {
            Log.e(TAG, "serverIssue");
            exceptionTitle = _Activity.getResources().getString(
                    R.string.server_issue_title);
            exceptionMessage = _Activity.getResources().getString(
                    R.string.server_issue_message);

        } else {
            exceptionTitle = _Activity.getResources().getString(
                    R.string.server_issue_title);
            exceptionMessage = _Activity.getResources().getString(
                    R.string.server_issue_message);
            Log.e(TAG, "exception in else ");
        }

    }

    public static void svnLakeHotelNameIntoSaredPreference(String id,
                                                           String hotel_name, String price, String contact_number,
                                                           String rating, String last_booking) {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("id", id);
        editor.putString("hotel_name", hotel_name);
        editor.putString("price", price);
        editor.putString("contact_number", contact_number);
        editor.putString("rating", rating);
        editor.putString("last_booking", last_booking);

        editor.commit();
    }

    public static void svnGrandHotelNameIntoSaredPreference(String id_2, String hotel_name_2, String price_2, String contact_number_2,
                                                            String rating_2, String last_booking_2) {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("id_2", id_2);
        editor.putString("hotel_name_2", hotel_name_2);
        editor.putString("price_2", price_2);
        editor.putString("contact_number_2", contact_number_2);
        editor.putString("rating_2", rating_2);
        editor.putString("last_booking_2", last_booking_2);

        editor.commit();
    }

    public static void svnFBLogin(boolean loginsuccess) {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("loginsuccess", loginsuccess);
        editor.commit();
    }

    public static void svnFBName(String FBusername, String FBmail) {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("FBusername", FBusername);
        editor.putString("FBmail", FBmail);
        editor.commit();
    }

    public static void svnGplLogin(boolean gloginsuccess) {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("Gloginsuccess", gloginsuccess);
        editor.commit();
    }

    public static void svnserviceLogin(boolean sloginsuccess) {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("Sloginsuccess", sloginsuccess);
        editor.commit();
    }

    public static void svnGplName(String Gplusername, String Gplmail) {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("Gplusername", Gplusername);
        editor.putString("Gplmail", Gplmail);
        editor.commit();
    }

    public static void svnREGTokens(String access_token, String refresh_token, String token_type, String expires_in,
                                    String customer_id, String customer_version) {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("access_token", access_token);
        editor.putString("refresh_token", refresh_token);
        editor.putString("token_type", token_type);
        editor.putString("expires_in", expires_in);
        editor.putString("customer_id", customer_id);
        editor.putString("customer_version", customer_version);

        editor.commit();
    }

    public static void svnLogTokens(String title, String firstname, String lastname, String dpname, String access_token, String refresh_token, String token_type, String expires_in,
                                    String customer_id, String customer_version) {

        SharedPreferences.Editor editor = preferences.edit();


        editor.putString("title", title);
        editor.putString("firstname", refresh_token);
        editor.putString("lastname", lastname);
        editor.putString("dpname", dpname);

        editor.putString("access_token", access_token);
        editor.putString("refresh_token", refresh_token);
        editor.putString("token_type", token_type);
        editor.putString("expires_in", expires_in);
        editor.putString("customer_id", customer_id);
        editor.putString("customer_version", customer_version);

        editor.commit();
    }

    public static String getlakehotelIdFromSharedPreference() {
        String city = preferences.getString("id", "");
        return city;
    }

    public static String getlakehotelnameFromSharedPreference() {
        String city = preferences.getString("hotel_name", "");
        return city;
    }

    public static String getlakecontactnoFromSharedPreference() {
        String city = preferences.getString("contact_number", "");
        return city;
    }

    public static String getlakeratingFromSharedPreference() {
        String city = preferences.getString("rating", "");
        return city;
    }

    public static String getgrandhotelIdFromSharedPreference() {
        String city = preferences.getString("id_2", "");
        return city;
    }

    public static String getgrandhotelnameFromSharedPreference() {
        String city = preferences.getString("hotel_name_2", "");
        return city;
    }

    public static String getgrandcontactnoFromSharedPreference() {
        String city = preferences.getString("contact_number_2", "");
        return city;
    }

    public static String getgrandratingFromSharedPreference() {
        String city = preferences.getString("rating_2", "");
        return city;
    }

    public static boolean getFBLoginstatusFromSharedPreference() {

        boolean loginsuccess = preferences.getBoolean("loginsuccess", false);
        return loginsuccess;
    }

    public static String getFBusernameFromSharedPreference() {

        String FBusername = preferences.getString("FBusername", "");
        return FBusername;
    }

    public static String getFBmailFromSharedPreference() {

        String FBmail = preferences.getString("FBmail", "");
        return FBmail;
    }

    public static boolean getGplLoginstatusFromSharedPreference() {

        boolean gloginsuccess = preferences.getBoolean("Gloginsuccess", false);
        return gloginsuccess;
    }

    public static String getGplusernameFromSharedPreference() {

        String Gplusername = preferences.getString("Gplusername", "");
        return Gplusername;
    }

    public static String getGplmailFromSharedPreference() {

        String Gplmail = preferences.getString("Gplmail", "");
        return Gplmail;
    }

    public static boolean getSLoginstatusFromSharedPreference() {

        boolean Sloginsuccess = preferences.getBoolean("Sloginsuccess", false);
        return Sloginsuccess;
    }


    public static String getREGacctokenFromSharedPreference() {
        String raccesstoken = preferences.getString("access_token", "");
        return raccesstoken;
    }

    public static String getREGcusidFromSharedPreference() {
        String rcusid = preferences.getString("customer_id", "");
        return rcusid;
    }

    public static String getLOGdpnameFromSharedPreference() {
        String ldpname = preferences.getString("dpname", "");
        return ldpname;
    }


    public static String getLOGacctokenFromSharedPreference() {
        String laccesstoken = preferences.getString("access_token", "");
        return laccesstoken;
    }

    public static String getLOGcusidFromSharedPreference() {
        String lcusid = preferences.getString("customer_id", "");
        return lcusid;
    }


}
