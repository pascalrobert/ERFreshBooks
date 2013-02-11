package er.freshbooks;

import er.xiss.ERXML;
import er.xiss.ERXML.E;

public class ERFBAddress {

    private String _street1;
    private String _street2;
    private String _city;
    private String _state;
    private String _country;
    private String _postalCode;
    
    public ERFBAddress() {
      
    }

    public String street1() {
      return _street1;
    }

    public void setStreet1(String street1) {
      _street1 = street1;
    }

    public String street2() {
      return _street2;
    }

    public void setStreet2(String street2) {
      _street2 = street2;
    }

    public String city() {
      return _city;
    }

    public void setCity(String city) {
      _city = city;
    }

    public String state() {
      return _state;
    }

    public void setState(String state) {
      _state = state;
    }

    public String country() {
      return _country;
    }

    public void setCountry(String country) {
      _country = country;
    }

    public String postalCode() {
      return _postalCode;
    }

    public void setPostalCode(String postalCode) {
      _postalCode = postalCode;
    }
    
    public static ERFBAddress transformPrimaryAddressFromResponse(ERXML.E xmlElement) {
      ERFBAddress client = new ERFBAddress();
      client.setStreet1(xmlElement.child("p_street1").text());
      client.setStreet2(xmlElement.child("p_street2").text());
      client.setCity(xmlElement.child("p_city").text());
      client.setState(xmlElement.child("p_state").text());
      client.setCountry(xmlElement.child("p_country").text());
      client.setPostalCode(xmlElement.child("p_code").text());
      return client;
    }

    public static ERFBAddress transformSecondaryAddressFromResponse(E xmlElement) {
      ERFBAddress client = new ERFBAddress();
      client.setStreet1(xmlElement.child("s_street1").text());
      client.setStreet2(xmlElement.child("s_street2").text());
      client.setCity(xmlElement.child("s_city").text());
      client.setState(xmlElement.child("s_state").text());
      client.setCountry(xmlElement.child("s_country").text());
      client.setPostalCode(xmlElement.child("s_code").text());
      return client;
    }
  
}
