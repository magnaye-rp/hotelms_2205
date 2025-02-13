
package sms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class test {
    public static void main(String []args){
        SMS sms = new SMS();
        sms.setMessage("nigga");
        sms.setPhone("09913084036");
        sms.setUsername("");
        sms.setPassword("");
        sms.setAddress("");
        sms.setPort("");
        sms.sendSMS();
    }
}
