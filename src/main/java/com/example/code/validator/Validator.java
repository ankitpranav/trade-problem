package com.example.code.validator;

import com.example.code.TradeRecord;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by ankitpranav on 5/8/18.
 */
@Service
public class Validator {

    public static final String AlphaNumeric_Check = "^\\s*[\\da-zA-Z][\\da-zA-Z\\s]*$";

    public boolean validateAlphaNumneric(String s){
       return s != null && s.trim().matches(AlphaNumeric_Check) ? true : false;
    }

    public boolean validateInteger(String s){
        if (s != null) {
            try {
                Integer.parseInt(s.trim());
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean validateEnum(String s){
        return  (s != null && EnumUtils.isValidEnum(TradeRecord.BuySellIndicator.class,s.trim())) ? true : false;
    }

    public boolean isValidDateFormat(String s) {
        String dateFormat = "MM/DD/YYYY";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        if (s != null) {
            try {
                sdf.parse(s);
                return true;

            } catch (ParseException e) {

                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

}
