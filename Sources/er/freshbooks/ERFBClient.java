package er.freshbooks;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.xiss.ERXML;
import er.xiss.ERXML.Item;

public class ERFBClient {

  private String _id;
  private String _firstName;
  private String _lastName;
  private String _organization;
  private String _email;
  private String _username;
  private String _password;
  private NSArray<ERFBContact> _contacts;
  private String _workPhone;
  private String _homePhone;
  private String _mobilePhone;
  private String _fax;
  private String _language;
  private String _currencyCode;
  private String _notes;
  private ERFBAddress _primaryAddress;
  private ERFBAddress _secondaryAddress;
  private String _vatName;
  private String _vatNumber;
  
  public ERFBClient() {
    
  }
  
  public String id() {
    return _id;
  }

  public void setId(String clientId) {
    _id = clientId;
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

  public String organization() {
    return _organization;
  }

  public void setOrganization(String organization) {
    _organization = organization;
  }

  public String email() {
    return _email;
  }

  public void setEmail(String email) {
    _email = email;
  }

  public String username() {
    return _username;
  }

  public void setUsername(String username) {
    _username = username;
  }

  public String password() {
    return _password;
  }

  public void setPassword(String password) {
    _password = password;
  }

  public NSArray<ERFBContact> contacts() {
    return _contacts;
  }

  public void setContacts(NSArray<ERFBContact> contacts) {
    _contacts = contacts;
  }

  public String workPhone() {
    return _workPhone;
  }

  public void setWorkPhone(String workPhone) {
    _workPhone = workPhone;
  }

  public String homePhone() {
    return _homePhone;
  }

  public void setHomePhone(String homePhone) {
    _homePhone = homePhone;
  }
  
  public String mobilePhone() {
    return _mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    _mobilePhone = mobilePhone;
  }

  public String fax() {
    return _fax;
  }

  public void setFax(String fax) {
    _fax = fax;
  }

  public String language() {
    return _language;
  }

  public void setLanguage(String language) {
    _language = language;
  }

  public String currencyCode() {
    return _currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    _currencyCode = currencyCode;
  }

  public String notes() {
    return _notes;
  }

  public void setNotes(String notes) {
    _notes = notes;
  }

  public ERFBAddress primaryAddress() {
    return _primaryAddress;
  }

  public void setPrimaryAddress(ERFBAddress primaryAddress) {
    _primaryAddress = primaryAddress;
  }

  public ERFBAddress secondaryAddress() {
    return _secondaryAddress;
  }

  public void setSecondaryAddress(ERFBAddress secondaryAddress) {
    _secondaryAddress = secondaryAddress;
  }

  public String vatName() {
    return _vatName;
  }

  public void setVatName(String vatName) {
    _vatName = vatName;
  }

  public String vatNumber() {
    return _vatNumber;
  }

  public void setVatNumber(String vatNumber) {
    _vatNumber = vatNumber;
  }
    
  public static ERXML.E transformToXml(ERFBClient client) {
    ERXML.E clientXmlElement = new ERXML.E("client");
    {
      clientXmlElement.e("first_name", client.firstName());
      clientXmlElement.e("last_name", client.lastName());
      clientXmlElement.e("organization", client.organization());
      clientXmlElement.e("email", client.email());
      clientXmlElement.e("username", client.username());
      clientXmlElement.e("password", client.password());
      clientXmlElement.e("work_phone", client.workPhone());
      clientXmlElement.e("home_phone", client.homePhone());
      clientXmlElement.e("mobile", client.mobilePhone());
      clientXmlElement.e("fax", client.fax());
      
      if (client.language() != null)
        clientXmlElement.e("language", client.language());
      
      clientXmlElement.e("currency_code", client.currencyCode());
      clientXmlElement.e("notes", client.notes());
      clientXmlElement.e("vat_name", client.vatName());
      clientXmlElement.e("vat_number", client.vatNumber());
      
      if ((client.contacts() != null) && (!client.contacts().isEmpty())) {
        ERXML.E contacts = clientXmlElement.e("contacts");
        for (ERFBContact contact: client.contacts()) {
          ERXML.E xmlContact = contacts.e("contact");
          {
            xmlContact.e("username", contact.username());
            xmlContact.e("first_name", contact.firstName());
            xmlContact.e("last_name", contact.lastName());
            xmlContact.e("email", contact.email());
            xmlContact.e("phone1", contact.phone1());
            xmlContact.e("phone2", contact.phone2());
          }
        }
      }
      
      if (client.primaryAddress() != null) {
        clientXmlElement.e("p_street1", client.primaryAddress().street1());
        clientXmlElement.e("p_street2", client.primaryAddress().street2());
        clientXmlElement.e("p_city", client.primaryAddress().city());
        clientXmlElement.e("p_state", client.primaryAddress().state());
        clientXmlElement.e("p_country", client.primaryAddress().country());
        clientXmlElement.e("p_code", client.primaryAddress().postalCode());
      }
      
      if (client.secondaryAddress() != null) {
        clientXmlElement.e("s_street1", client.primaryAddress().street1());
        clientXmlElement.e("s_street2", client.primaryAddress().street2());
        clientXmlElement.e("s_city", client.primaryAddress().city());
        clientXmlElement.e("s_state", client.primaryAddress().state());
        clientXmlElement.e("s_country", client.primaryAddress().country());
        clientXmlElement.e("s_code", client.primaryAddress().postalCode());
      }
    }
    return clientXmlElement;
  }
  
  public static ERFBClient transformFromResponse(ERXML.E xmlElement) {
    ERFBClient client = new ERFBClient();
    
    client.setId(xmlElement.child("client_id").text());
    client.setFirstName(xmlElement.child("first_name").text());
    client.setLastName(xmlElement.child("last_name").text());
    client.setCurrencyCode(xmlElement.child("currency_code").text());
    client.setEmail(xmlElement.child("email").text());
    client.setFax(xmlElement.child("fax").text());
    client.setHomePhone(xmlElement.child("home_phone").text());
    client.setLanguage(xmlElement.child("language").text());
    client.setNotes(xmlElement.child("notes").text());
    client.setOrganization(xmlElement.child("organization").text());
    client.setPrimaryAddress(ERFBAddress.transformPrimaryAddressFromResponse(xmlElement));
    client.setSecondaryAddress(ERFBAddress.transformSecondaryAddressFromResponse(xmlElement));
    client.setUsername(xmlElement.child("username").text());
    client.setVatName(xmlElement.child("vat_name").text());
    client.setVatNumber(xmlElement.child("vat_number").text());
    client.setWorkPhone(xmlElement.child("work_phone").text());
    
    NSMutableArray<ERFBContact> contacts = new NSMutableArray<ERFBContact>();
    ERXML.E contactsElements = xmlElement.child("contacts");
    if (contactsElements.children() != null) {
      for (Item contactInXml: contactsElements.children()) {
        if (contactInXml instanceof ERXML.E) {
          ERFBContact contact = ERFBContact.transformFromResponse((ERXML.E)contactInXml);
          contacts.addObject(contact);
        }
      }
    }
    client.setContacts(contacts.immutableClone());
    
    return client;
  }
  
  @Override
  public String toString() {
    return "Client : " + organization();
  }
  
}
