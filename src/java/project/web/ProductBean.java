/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author josephahamilton
 */

@Named(value = "ProductBean")
@SessionScoped
public class ProductBean implements Serializable {

    @EJB
    private ProductBean productBean;
    protected int discount;
    protected int averagePrice;
    private static final Logger logger = Logger.getLogger(
            "firstcup.web.productBean");
    
    public ProductBean() {
    }
    
    public int getDiscount(){         
        HttpURLConnection connection = null;
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;
        URL serverAddress = null;

        try {
            serverAddress = new URL(
                        "http://localhost:8080/DailyDiscountRS/resources/dailyDiscount");
            connection = (HttpURLConnection) serverAddress.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);

            // Make the connection to Duke's Age
            connection.connect();

            // Read in the response
            rd = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();

            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            // Convert the response to an int
            discount = Integer.parseInt(sb.toString());
        } catch (MalformedURLException e) {
            logger.warning("A MalformedURLException occurred.");
            e.printStackTrace();
        } catch (ProtocolException e) {
            logger.warning("A ProtocolException occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            logger.warning("An IOException occurred");
            e.printStackTrace();
        }

        return discount;
    }
    
    public void setDiscount(int discount) {
        this.discount = discount;
    }
    
    public int getAveragePrice() {
        return averagePrice;
    }
    

}
