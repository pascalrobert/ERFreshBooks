package er.freshbooks;

import er.xiss.ERXML;

public class ERFBContact {

  private String _username;
  private String _firstName;
  private String _lastName;
  private String _email;
  private String _phone1;
  private String _phone2;
  private String _id;

  public ERFBContact() {

  }

  public String username() {
    return _username;
  }

  public void setUsername(String username) {
    _username = username;
  }

  public String firstName() {
    return _firstName;
  }

  public void setFirstName(String firstName) {
    _firstName = firstName;
  }

  public String lastName() {
    return _lastName;
  }

  public void setLastName(String lastName) {
    _lastName = lastName;
  }

  public String email() {
    return _email;
  }

  public void setEmail(String email) {
    _email = email;
  }

  public String phone1() {
    return _phone1;
  }

  public void setPhone1(String phone1) {
    _phone1 = phone1;
  }

  public String phone2() {
    return _phone2;
  }

  public void setPhone2(String phone2) {
    _phone2 = phone2;
  }

  public String id() {
    return _id;
  }

  public void setId(String contactId) {
    _id = contactId;
  }

  public static ERFBContact transformFromResponse(ERXML.E xmlElement) {
    ERFBContact client = new ERFBContact();
    client.setId(xmlElement.child("contact_id").text());
    client.setFirstName(xmlElement.child("first_name").text());
    client.setLastName(xmlElement.child("last_name").text());
    client.setEmail(xmlElement.child("email").text());
    client.setPhone1(xmlElement.child("phone1").text());
    client.setUsername(xmlElement.child("username").text());
    client.setPhone2(xmlElement.child("phone2").text());
    return client;
  }

  @Override
  public String toString() {
    return "Contact: " + firstName() + " " + lastName() + " <" + email() + ">";
  }

}
