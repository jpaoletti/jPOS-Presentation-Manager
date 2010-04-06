package org.jpos.ee.pm.pentaho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.jpos.q2.QBeanSupport;
import org.jpos.util.NameRegistrar;
import org.jpos.util.NameRegistrar.NotFoundException;

public class PentahoQBean extends QBeanSupport {
    private String user;
    private String password;
    private String url;
    
    public void initService () throws Exception {
        getLog().info ("Pentaho activated");
        user = cfg.get("user");
        password = cfg.get("password");
        url = cfg.get("url");
        NameRegistrar.register (getCustomName(), this);
    }
    
    public static PentahoQBean getService(){
        try {
            return (PentahoQBean)NameRegistrar.get(getCustomName());
        } catch (NotFoundException e) {
            return null;
        }
    }
    
    public static String getCustomName() {
        return "pentaho";
    }
    
    public void startService () throws Exception {
        login();
    }
    
    public String getUrlLogin(){
        return getUrl()+"/Home?userid="+getUser()+"&password="+getPassword();
    }
    
    public void login() throws IOException {
        try {
            String data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(getUser(), "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(getPassword(), "UTF-8");
   
            // Send data
            URL url = new URL(getUrl()+"/Home");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
   
            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            getLog().error("Unable to connecto to pentaho server");
            getLog().error(e);
        }
    }
    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}