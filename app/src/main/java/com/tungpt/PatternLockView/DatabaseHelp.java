package com.tungpt.PatternLockView;

import android.content.Context;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseHelp {
    public static String[] hanlldingPasscode(String jsonuserPost) {
        String[] strings = new String[20];
        try {
            JSONObject jsonObject = new JSONObject(jsonuserPost);
            String passCodeResult = jsonObject.getString("PassCodes");
            strings = passCodeResult.split(",");
            for (int i = 0; i < strings.length; i++) {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strings;
    }

    public static String getPhoneNumber(TextView textView, EditText editText) {
        String telePhone = textView.getText().toString();
        String mtelePhone = telePhone.substring(1, telePhone.length());
        String numberPhone = mtelePhone + "-" + editText.getText().toString();
        return numberPhone;
    }

    public static List<IconPasscon> getListIcon() {
        String passCode;

        passCode = "{\n" +
                "    \"0\": {\n" +
                "        \"icon\": \"0\",\n" +
                "        \"hint\": \"0\"\n" +
                "    },\n" +
                "    \"1\": {\n" +
                "        \"icon\": \"1\",\n" +
                "        \"hint\": \"1\"\n" +
                "    },\n" +
                "    \"2\": {\n" +
                "        \"icon\": \"2\",\n" +
                "        \"hint\": \"2\"\n" +
                "    },\n" +
                "    \"3\": {\n" +
                "        \"icon\": \"3\",\n" +
                "        \"hint\": \"3\"\n" +
                "    },\n" +
                "    \"4\": {\n" +
                "        \"icon\": \"4\",\n" +
                "        \"hint\": \"4\"\n" +
                "    },\n" +
                "    \"5\": {\n" +
                "        \"icon\": \"5\",\n" +
                "        \"hint\": \"5\"\n" +
                "    },\n" +
                "    \"6\": {\n" +
                "        \"icon\": \"6\",\n" +
                "        \"hint\": \"6\"\n" +
                "    },\n" +
                "    \"7\": {\n" +
                "        \"icon\": \"7\",\n" +
                "        \"hint\": \"7\"\n" +
                "    },\n" +
                "    \"8\": {\n" +
                "        \"icon\": \"8\",\n" +
                "        \"hint\": \"8\"\n" +
                "    },\n" +
                "    \"9\": {\n" +
                "        \"icon\": \"9\",\n" +
                "        \"hint\": \"9\"\n" +
                "    },\n" +
                "    \"10\": {\n" +
                "        \"icon\": " + R.drawable.icon_theme_sports_badminton + ",\n" +
                "        \"hint\": \"badminton\"\n" +
                "    },\n" +
                "    \"11\": {\n" +
                "        \"icon\": " + R.drawable.icon_theme_sports_baseball + ",\n" +
                "        \"hint\": \"baseball\"\n" +
                "    },\n" +
                "    \"12\": {\n" +
                "        \"icon\": " + R.drawable.icon_theme_sports_basketball + ",\n" +
                "        \"hint\": \"basketball\"\n" +
                "    },\n" +
                "    \"13\": {\n" +
                "        \"icon\": " + R.drawable.icon_theme_sports_bicycle + ",\n" +
                "        \"hint\": \"bicycle\"\n" +
                "    },\n" +
                "    \"14\": {\n" +
                "        \"icon\": " + R.drawable.icon_theme_sports_bowling + ",\n" +
                "        \"hint\": \"bowling\"\n" +
                "    },\n" +
                "    \"15\": {\n" +
                "        \"icon\": " + R.drawable.icon_theme_sports_football + ",\n" +
                "        \"hint\": \"football\"\n" +
                "    },\n" +
                "    \"16\": {\n" +
                "        \"icon\": " + R.drawable.icon_theme_sports_golf + ",\n" +
                "        \"hint\": \"golf\"\n" +
                "    },\n" +
                "    \"17\": {\n" +
                "        \"icon\": " + R.drawable.icon_theme_sports_pingpong + ",\n" +
                "        \"hint\": \"pingpong\"\n" +
                "    },\n" +
                "    \"18\": {\n" +
                "        \"icon\": " + R.drawable.icon_theme_sports_ski + ",\n" +
                "        \"hint\": \"ski\"\n" +
                "    },\n" +
                "    \"19\": {\n" +
                "        \"icon\": " + R.drawable.icon_theme_sports_swimming + ",\n" +
                "        \"hint\": \"swimming\"\n" +
                "    }\n" +
                "};";
        List<IconPasscon> listIcon = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(passCode);
            Iterator<String> iter = jsonObject.keys();
            while (iter.hasNext()) {
                IconPasscon iconPasscon = new IconPasscon();
                String key = iter.next();
                JSONObject jsonObject1 = new JSONObject(jsonObject.getString(key));
                int hinhAnh = jsonObject1.getInt("icon");
                String hint = jsonObject1.getString("hint");
                iconPasscon.setKey(key);
                iconPasscon.setHinhAnh(hinhAnh);
                iconPasscon.setHint(hint);
                listIcon.add(iconPasscon);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listIcon;
    }

    public static String getDeviceId(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }
}
