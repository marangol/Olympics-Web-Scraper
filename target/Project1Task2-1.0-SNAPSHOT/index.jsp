<%--
  Author: Martin Arango (marangol)
  This file is a part of the view component of the MVC
  It is the default page of the site
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Project1Task2</title>
</head>
<body>
<h1><%= "Olympic Medal Prediction" %></h1>
<h4><%= "Created by Martin Arango" %></h4>
<h2><%= "20 Largest Countries by GDP" %></h2>

<%-- This is --%>
<form action="OlympicMedalPredictorServlet" method="get" id="IndexForm">
    <label for="country">Choose the name of a country:</label>

    <select name="country" id="country">
        <option value="United States" selected>United States</option>
        <option value="China">China</option>
        <option value="Japan">Japan</option>
        <option value="Germany">Germany</option>
        <option value="India">India</option>
        <option value="United Kingdom">United Kingdom</option>
        <option value="France">France</option>
        <option value="Brazil">Brazil</option>
        <option value="Italy">Italy</option>
        <option value="Canada">Canada</option>
        <option value="Russia">Russia</option>
        <option value="South Korea">South Korea</option>
        <option value="Australia">Australia</option>
        <option value="Spain">Spain</option>
        <option value="Mexico">Mexico</option>
        <option value="Indonesia">Indonesia</option>
        <option value="Turkey">Turkey</option>
        <option value="Netherlands">Netherlands</option>
        <option value="Saudi Arabia">Saudi Arabia</option>
        <option value="Switzerland">Switzerland</option>
    </select><br><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>