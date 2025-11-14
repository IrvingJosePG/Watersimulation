package utils;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class ShortNumberFormat extends NumberFormat {
    @Override
    public StringBuffer format(double value, StringBuffer buffer, FieldPosition pos) {

        if (Math.abs(value) >= 1_000_000_000) {
            buffer.append(String.format("%.1fB", value / 1_000_000_000));
        } else if (Math.abs(value) >= 1_000_000) {
            buffer.append(String.format("%.1fM", value / 1_000_000));
        } else if (Math.abs(value) >= 1_000) {
            buffer.append(String.format("%.1fK", value / 1_000));
        } else {
            buffer.append((long) value);
        }

        return buffer;
    }

    @Override
    public Number parse(String source, ParsePosition pos) {
        return null;
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

