package com.out.workout.utils;

import android.text.InputFilter;
import android.text.Spanned;

public final class DecimalDigitsInputFilter implements InputFilter {
    private final int digitsAfterDecimal;
    private final int maxDigits;

    public DecimalDigitsInputFilter(int i, int i2) {
        this.maxDigits = i;
        this.digitsAfterDecimal = i2;
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        int length = spanned.length();
        if (length > 0) {
            int i5 = 0;
            while (true) {
                int i6 = i5 + 1;
                char charAt = spanned.charAt(i5);
                if (charAt == '.' || charAt == ',' || i6 >= length) {
                    break;
                }
                i5 = i6;
            }
        }
        if (length >= this.maxDigits) {
            return "";
        }
        return null;
    }
}
