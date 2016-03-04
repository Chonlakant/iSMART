package com.mncomunity1;

import android.content.SharedPreferences;

import com.tale.prettysharedpreferences.BooleanEditor;
import com.tale.prettysharedpreferences.IntegerEditor;
import com.tale.prettysharedpreferences.PrettySharedPreferences;
import com.tale.prettysharedpreferences.StringEditor;

/**
 * Created by TALE on 10/28/2014.
 */
public class PrefManager extends PrettySharedPreferences<PrefManager> {

    public PrefManager(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    public StringEditor<PrefManager> email() {
        return getStringEditor("email");
    }
    public StringEditor<PrefManager> id() {
        return getStringEditor("id");
    }

    public StringEditor<PrefManager> passWord() {
        return getStringEditor("passWord");
    }

    public StringEditor<PrefManager> confirmpassWord() {
        return getStringEditor("confirmpassWord");
    }

    public BooleanEditor<PrefManager> isLogin() {
        return getBooleanEditor("isLogin");
    }

    public BooleanEditor<PrefManager> isCheckProduct() {
        return getBooleanEditor("isCheckProduct");
    }

    public BooleanEditor<PrefManager> isCheckDialog() {
        return getBooleanEditor("isCheckDialog");
    }



    public BooleanEditor<PrefManager> isAddress() {
        return getBooleanEditor("isAddress");
    }

    public BooleanEditor<PrefManager> isAddressRegister() {
        return getBooleanEditor("isAddressRegister");
    }

    public BooleanEditor<PrefManager> isAddressRegister2() {
        return getBooleanEditor("isAddressRegister2");
    }

    public StringEditor<PrefManager> name() {
        return getStringEditor("name");
    }
    public StringEditor<PrefManager> userName() {
        return getStringEditor("userName");
    }


    public StringEditor<PrefManager> home() {
        return getStringEditor("home");
    }


    public StringEditor<PrefManager> inVite() {
        return getStringEditor("inVite");
    }
    public StringEditor<PrefManager> title() {
        return getStringEditor("title");
    }



    public IntegerEditor<PrefManager> color() {
        return getIntegerEditor("color");
    }

    public StringEditor<PrefManager> vendeName() {
        return getStringEditor("vendeName");
    }

    public StringEditor<PrefManager> token() {
        return getStringEditor("token");
    }


}
