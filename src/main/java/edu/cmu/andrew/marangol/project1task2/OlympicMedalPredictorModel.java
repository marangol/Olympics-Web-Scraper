package edu.cmu.andrew.marangol.project1task2;

/*
 * @author Martin Arango (marangol)
 *
 * Part of code and comments copied from Lab2 InterestingPicture
 * This file is the Model component of the MVC
 * It scrapes each of the sources and stores the data in the member variables.
 * It also computes the predicted medal count
 * For this to work remember to change the deployment to:
 * /Project1Task2-1.0-SNAPSHOT
 */

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class OlympicMedalPredictorModel {
private String population;
private String gdp;
private String goldMedals;
private String silverMedals;
private String bronzeMedals;
private String wheightedMedalCount;
private String expectedMedalCount;
private String flagHTML;

    /**
     * Method that scrapes from worldometer website population and gdp for selected country.
     * It then updates population and gdp attributes of model object
     */
    public void doWorldometer(String country)
            throws UnsupportedEncodingException {

        String response = "";

        // Create a URL for the page to be screen scraped
        String URL = "https://www.worldometers.info/gdp/gdp-by-country/";

        // Fetch the page
        response = fetch(URL,"TLSV1.3");

        /*
         * Search the page to find the GDP and population of the country
         */
        // row of the selected country
        int cutLeft = response.indexOf(country);
        int cutRight = response.length();
        String countrySubstring = response.substring(cutLeft, cutRight);
        // text on column cell starts with this string
        String clue = "text-align:right;\">";
        cutLeft = countrySubstring.indexOf(clue)+clue.length();
        clue = "</td>";
        // first appearance
        int first = countrySubstring.indexOf(clue);
        // second appearance
        // used help from https://stackoverflow.com/questions/19035893/finding-second-occurrence-of-a-substring-in-a-string-in-java
        cutRight = countrySubstring.indexOf(clue,first+1);
        // save scrapped gdp
        gdp = countrySubstring.substring(cutLeft, cutRight);

        /*
         * Now we search for the population column
         * we start by finding a clue for the cut left index.
         * Then we find the fourth occurence using a for loop.
         * Finally we adjust index to consider clue length
         */
        clue = "text-align:right;\">";
        cutLeft = countrySubstring.indexOf(clue);
        for(int i=0; i<3; i++){
            cutLeft = countrySubstring.indexOf(clue,cutLeft+1);
        }
        cutLeft+=clue.length();
        //Repeat the process for the cut right
        clue = "</td>";
        cutRight = countrySubstring.indexOf(clue);
        for(int i=0; i<4; i++){
            cutRight = countrySubstring.indexOf(clue,cutRight+1);
        }
        population = countrySubstring.substring(cutLeft, cutRight);
    }

    /**
     * Method that scrapes from the Olympics website won medals for selected country.
     * It then updates the medals attributes of model object
     */
    public void doOlympics(String country) {

        String response = "";

        // Create a URL for the page to be screen scraped
        String URL = "https://olympics.com/tokyo-2020/olympic-games/en/results/all-sports/medal-standings.htm";

        // Fetch the page
        response = fetch(URL,"TLSV1.3");

        //updates country attribute with Olympics codification
        String countryOlympics = getCountryOlympics(country);

        /*
         * Search the page to find the medal counts
         */

        //Take away the first lines of the html to prevent mistakes looking for Japan
        response = response.substring(5000);

        // look for row of the selected country
        int cutLeft = response.indexOf(countryOlympics);
        int cutRight = response.length();
        String countrySubstring = response.substring(cutLeft, cutRight);
        // look for text on column cell starts with this string
        String clue = "center\">";
        cutLeft = countrySubstring.indexOf(clue);
        cutLeft+=clue.length();

        clue = "</td>";
        // first appearance
        int first = countrySubstring.indexOf(clue);
        // second appearance
        cutRight = countrySubstring.indexOf(clue,first+1);
        // save scrapped gold medals
        goldMedals = countrySubstring.substring(cutLeft, cutRight);
        //correct if there were not 0 medals
        if(goldMedals.charAt(0) == '<'){
            clue = "Total\">";
            cutLeft = goldMedals.indexOf(clue);
            cutLeft += clue.length();
            cutRight = goldMedals.length() - 4;
            goldMedals = goldMedals.substring(cutLeft, cutRight);
        }

        /*
         * Now we search for the silver medals column
         * we start by finding a clue for the cut left index.
         * Then we find the desired occurence using a for loop.
         */
        clue = "center\">";
        cutLeft = countrySubstring.indexOf(clue);
        for(int i=0; i<1; i++){
            cutLeft = countrySubstring.indexOf(clue,cutLeft+1);
        }
        cutLeft+=clue.length();

        //Repeat the process for the cut right
        clue = "</td>";
        cutRight = countrySubstring.indexOf(clue);
        for(int i=0; i<2; i++){
            cutRight = countrySubstring.indexOf(clue,cutRight+1);
        }
        // save scrapped silver medals
        silverMedals = countrySubstring.substring(cutLeft, cutRight);
        //correct if there were not 0 medals
        if(silverMedals.charAt(0) == '<'){
            clue = "Total\">";
            cutLeft = silverMedals.indexOf(clue);
            cutLeft += clue.length();
            cutRight = silverMedals.length() - 4;
            silverMedals = silverMedals.substring(cutLeft, cutRight);
        }

        /*
         * Now we search for the bronze medals column
         * we start by finding a clue for the cut left index.
         * Then we find the desired occurence using a for loop.
         * Finally we adjust index to consider clue length
         */
        clue = "center\">";
        cutLeft = countrySubstring.indexOf(clue);
        for(int i=0; i<2; i++){
            cutLeft = countrySubstring.indexOf(clue,cutLeft+1);
        }
        cutLeft+=clue.length();

        //Repeat the process for the cut right
        clue = "</td>";
        cutRight = countrySubstring.indexOf(clue);
        for(int i=0; i<3; i++){
            cutRight = countrySubstring.indexOf(clue,cutRight+1);
        }
        bronzeMedals = countrySubstring.substring(cutLeft, cutRight);
        //correct if there were not 0 medals
        if(bronzeMedals.charAt(0) == '<'){
            clue = "Total\">";
            cutLeft = bronzeMedals.indexOf(clue);
            cutLeft += clue.length();
            cutRight = bronzeMedals.length() - 4;
            bronzeMedals = bronzeMedals.substring(cutLeft, cutRight);
        }

        // Finally we compute the weighted count of medals
        wheightedMedalCount = String.valueOf((3*Integer.parseInt(goldMedals))+
                (2*Integer.parseInt(silverMedals))+
                Integer.parseInt(bronzeMedals));
    }

    /**
     * Method that calculates the expected medal count with the formula provided
     * S = 0.1*(P*G^2)^(1/3)
     */
    public void doExpectedMedalCount(){
        double P = Double.parseDouble(population.replaceAll(",",""))/1000000;
        double G = Double.parseDouble(gdp.replaceAll(",","").replace("$",""))/1000000000;
        double result = 0.1*Math.cbrt((P*Math.pow(G,2)));
        // rounding with the help of https://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java
        expectedMedalCount = String.valueOf(Math.round(result * 100.0) / 100.0);
    }

    /**
     * Method that scrapes the flag of the selected country
     */
    public void doFlag(String country){
        String response = "";

        // Create a URL for the page to be screen scraped
        String URL = "https://commons.wikimedia.org/wiki/Animated_GIF_flags";

        // Fetch the page
        response = fetch(URL,"TLSV1.3");

        /*
         * Search the page to find the medal counts
         */
        // row of the selected country
        // We add the <p> to make sure we are looking for the flag label and not other text
        int cutRight = response.indexOf("<p>"+country);
        int cutLeft = 0;
        String countrySubstring = response.substring(cutLeft, cutRight);
        // Use of lastIndexOf(); to start looking from the end of the substring
        //String clue = "</a>"; for cut right
        //String clue = "<img alt src="; for cut left
        String clue = "</a>";
        cutRight = countrySubstring.lastIndexOf(clue);
        cutRight -= 1;

        clue = "<img alt=";
        cutLeft = countrySubstring.lastIndexOf(clue);
        cutLeft+=clue.length();
        flagHTML = countrySubstring.substring(cutLeft, cutRight);
    }

    /**
     * Getters for model's attributes
     */
    public String getExpectedMedalCount() {
        return expectedMedalCount;
    }

    public String getGoldMedals() {
        return goldMedals;
    }

    public String getSilverMedals() {
        return silverMedals;
    }

    public String getBronzeMedals() {
        return bronzeMedals;
    }

    public String getWheightedMedalCount() {
        return wheightedMedalCount;
    }

    public String getFlagHTML() {
        return flagHTML;
    }

    /**
     * Method that maps the name of the selected Country to the name used in the Olympics
     */
    public String getCountryOlympics(String country){
        if(country.equals("United States")){return "United States of America";}
        else if(country.equals(("China"))){return "People's Republic of China";}
        else if(country.equals(("United Kingdom"))){return "Great Britain";}
        else if(country.equals(("Russia"))){return "ROC";}
        else if(country.equals(("South Korea"))){return "Republic of Korea";}
        return country;
    }


    /*
     * Make an HTTP request to a given URL. Copied from InterestingPicture
     *
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.  This is identical
     * to what would be returned from using curl on the command line.
     */
    private String fetch(String searchURL, String certType) {
        try {
            // Create trust manager, which lets you ignore SSLHandshakeExceptions
            createTrustManager(certType);
        } catch (KeyManagementException ex) {
            System.out.println("Shouldn't come here: ");
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Shouldn't come here: ");
            ex.printStackTrace();
        }

        String response = "";
        try {
            URL url = new URL(searchURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.err.println("Something wrong with URL");
            return null;
        }
        return response;
    }

    private void createTrustManager(String certType) throws KeyManagementException, NoSuchAlgorithmException {
        /**
         * Annoying SSLHandShakeException. After trying several methods, finally this
         * seemed to work.
         * Taken from: http://www.nakov.com/blog/2009/07/16/disable-certificate-validation-in-java-ssl-connections/
         */
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance(certType);
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getGdp() {
        return gdp;
    }

    public void setGdp(String gdp) {
        this.gdp = gdp;
    }
}
