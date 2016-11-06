package com.rishabh.github.finclusionhack.utils;

import android.text.Editable;
import android.text.TextWatcher;

import org.bom.android.container.SDKUtils;

public class AllowOnlyBomWhiteListedCharactersTextWatcher implements TextWatcher {

    private boolean mIsEditing;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!mIsEditing) {
            mIsEditing = true;

            String text = SDKUtils.sanitizeText(s.toString());

            if (!s.toString().equals(text))
                s.replace(0, s.length(), text);

            mIsEditing = false;

        }
    }
}

