package com.codemagos.wallet.Spstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import java.util.HashMap;
import java.util.Map;


public class SharedPreferenceStore {
	public final static String SHARED_PREFS = "PreferenceStore";
	private Editor edit;
	private SharedPreferences settings;
	public SharedPreferenceStore(Context context){
		settings = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
	}
	public void setBank(String bank){
		edit = settings.edit();
		edit.putString("bank", bank);
		edit.commit();
	}
	public void setBalance(String balance){
		edit = settings.edit();
		edit.putString("balance", balance);
		edit.commit();
	}
	public String getBank(){
		return settings.getString("bank", "");
	}

    public String getBankBalance(){
        return settings.getString("balance", "000.00");
    }

}
