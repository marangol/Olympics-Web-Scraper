<%--
  Author: Martin Arango (marangol)
  This file is a part of the view component of the MVC
  It is where the results displayed
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Result</title>
</head>
<%-- Use the style section to adjust the font size for each type of tag --%>
<style>
    h1 {
        font-size: 40px;
    }

    h2 {
        font-size: 30px;
    }

    p {
        font-size: 20px;
    }
</style>
<%-- The body displays the parameters passed by the controller as the result--%>
<body>
<h1>Country: ${country}</h1>
<h2>Population: ${population}<br>GDP: ${gdp}</h2>
<h4>Credit: "https://www.worldometers.info/gdp/gdp-by-country/"</h4>
<h2>Gold: ${goldMedals}<br>Silver: ${silverMedals}<br>Bronze: ${bronzeMedals}<br>
    Weighted Medal Count: ${weightedMedalCount}</h2>
<h4>Credit: "https://olympics.com/tokyo-2020/olympic-games/en/results/all-sports/medal-standings.htm"</h4>
<h2>Expected Medal Count: ${expectedMedalCount}</h2>
<h4>Credit: "Towing Icebergs, Falling Dominoes" by Robert B. Banks</h4>
<p>Flag:</p>
<img alt=${flagHTML}>
<h4>Credit: "https://commons.wikimedia.org/wiki/Animated_GIF_flags"</h4><br>
<%--Included javascript to change the behaviour of the button and call the index.jsp file
https://forums.codeguru.com/showthread.php?262671-onClick-handling-in-JSP--%>
<input type="button" value="Continue" onclick="javascript:window.location='index.jsp';">
</body>
</html>