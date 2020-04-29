package com.nguyendinhdoan.myapp.data.sharepref;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.StringDef;

import com.nguyendinhdoan.myapp.BuildConfig;
import com.nguyendinhdoan.myapp.data.models.UserDTO;

public class SharePref {

    private static final String PREF_NAME = "MyApps" + BuildConfig.VERSION_NAME;

    @StringDef({
            PREF_KEY.UID,
            PREF_KEY.NAME,
            PREF_KEY.ADDRESS,
            PREF_KEY.PHONE,
    })
    public @interface PREF_KEY {
        String UID = "UID";
        String NAME = "NAME";
        String ADDRESS = "ADDRESS";
        String PHONE = "PHONE";
    }

    private static SharedPreferences sharePref;

    public SharePref(Context context) {
        sharePref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * save user to shared preference
     * @param userDTO user model
     */
    public void saveUser(UserDTO userDTO) {
        sharePref.edit().putString(PREF_KEY.UID, userDTO.getUId()).apply();
        sharePref.edit().putString(PREF_KEY.NAME, userDTO.getName()).apply();
        sharePref.edit().putString(PREF_KEY.ADDRESS, userDTO.getAddress()).apply();
        sharePref.edit().putString(PREF_KEY.PHONE, userDTO.getPhone()).apply();
    }

    /**
     * get user from shared preference
     * @return userDTO user model
     */
    public UserDTO getUser() {
       UserDTO userDTO = new UserDTO();
       userDTO.setUId(sharePref.getString(PREF_KEY.UID, ""));
       userDTO.setName(sharePref.getString(PREF_KEY.NAME, ""));
       userDTO.setAddress(sharePref.getString(PREF_KEY.ADDRESS, ""));
       userDTO.setPhone(sharePref.getString(PREF_KEY.PHONE, ""));
       return userDTO;
    }
}
