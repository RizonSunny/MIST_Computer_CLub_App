

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UserInfoWindow extends front{

    public static final String NEW_LINE = "\n";

    private String lastHandle = ".";
    private boolean loaded = false;
    private boolean topSkillsLoaded = false;
    private Object lastLoadedObject = null;

    private String infoText = null;


    HashSet<Character> russianLetters;

    public UserInfoWindow() throws Exception {
       
    }
    
    
    public String loadUserInfo(String handle) throws Exception {
        String urlRequest = " http://codeforces.com/api/user.info?handles=" + handle;
        HTTPConnection connection = new HTTPConnection(urlRequest);
        ArrayList<String> result = connection.makeRequest();
        JSONParser parser = new JSONParser();
        for (String jSonQuery : result) {
            Object obj = parser.parse(jSonQuery);
            JSONObject jSon = (JSONObject) obj;
            String status = (String)jSon.get("status");
            if (status.equals("FAILED")) {
                System.out.println("Error");
            } else {
                JSONArray res = (JSONArray)jSon.get("result");
                JSONObject resObj = (JSONObject)res.get(0);
                lastLoadedObject = resObj;
                
               // handleLabel.setText("<html><div style='text-align: center;'>" + resObj.get("handle") + "</html>");
                int r = 0;
                Object rating = resObj.get("rating");
                if (rating != null) {
                    r = (int)( (long)resObj.get("rating") );
                } else {
                    System.out.println("0");;
                }
                
                Object object = null;
                
                infoText = null;
                
                object = resObj.get("firstName");
                String name = "#";
                if (object != null) {
                    name = (String)object;
                }
                object = resObj.get("lastName");
                if (object != null) {
                    if (name.equals("#")) {
                        name = (String)object;
                    }
                    name = name + " " + (String)object;
                }
                infoText ="Name: " + name + NEW_LINE;
                
                infoText = infoText + "Rating: " + r + NEW_LINE;
                object = resObj.get("rank");
                if (object != null) {
                    infoText = infoText + "Rank: " + object + NEW_LINE;
                }
                object = resObj.get("country");
                if (object != null) {
                    infoText = infoText + "Country: " + object + NEW_LINE;
                }
                object = resObj.get("city");
                if (object != null) {
                    infoText = infoText + "City: " + object + NEW_LINE;
                }
                object = resObj.get("organization");
                if (object != null) {
                    infoText = infoText + "Organization: " + object + NEW_LINE;
                }
                
                
            }
        }
        loaded = true;
        return infoText;
    }

}