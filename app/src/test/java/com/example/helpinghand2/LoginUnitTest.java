package com.example.helpinghand2;

import android.content.Context;

import helpinghand.helpinghand2.LoginActivity;

public class LoginUnitTest {
    private static final String FAKE_SRING="Login was successfull";

    Context mMockContext;

    public void readStringFromContext_LocalizedString(){
        LoginActivity myObjectUnderTest= new LoginActivity(mMockContext);

        String result = myObjectUnderTest.validate("email","password");
    }
}
