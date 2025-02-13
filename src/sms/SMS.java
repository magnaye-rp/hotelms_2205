package sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SMS {
    private String message;
    private String phone;
    private String username;
    private String password;
    private String address;
    private String port;

    public void setMessage(String message) {
        this.message = message;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPort(String port) {
        this.port = port;
    }

    public String getMessage() {
        return message;
    }
    public String getPhone() {
        return phone;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getAddress() {
        return address;
    }
    public String getPort() {
        return port;
    }
    
    public void sendSMS(){
        try{    
            URL url = new URL(
            address + ":" + port + "/SendSMS?username=" + username + 
            "&password=" + password + "&phone=" + phone + 
            "&message=" + URLEncoder.encode(message, "UTF-8"));

            URLConnection connection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while((inputLine = bufferedReader.readLine()) != null){
                System.out.println(inputLine);
            }
            bufferedReader.close();
        }catch(IOException e){
        }
    }
}
