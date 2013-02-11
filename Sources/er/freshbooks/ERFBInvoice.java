package er.freshbooks;

import java.text.ParseException;
import java.util.Date;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import er.xiss.ERXML;
import er.xiss.ERXML.Item;

public class ERFBInvoice {

  private String _id;
  private ERFBClient _client;
  private NSArray<ERFBContact> _contacts;
  private String _invoiceNumber;
  private String _status; // sent, viewed or draft [default]
  private NSTimestamp _date;
  private String poNumber;
  private Integer _discount;
  private String _notes;
  private String _currencyCode;
  private String _language;
  private String _terms;
  private String _returnUrl;
  private String _firstName;
  private String _lastName;
  private String _organization;
  private ERFBAddress _primaryAddress;
  private String _vatName;
  private String _vatNumber;
  private NSArray<ERFBLine> _lines;

  public ERFBInvoice() {

  }

  public String id() {
    return _id;
  }

  public void setId(String id) {
    _id = id;
  }
  
  public ERFBClient client() {
    return _client;
  }

  public void setClient(ERFBClient client) {
    _client = client;
  }

  public NSArray<ERFBContact> contacts() {
    return _contacts;
  }

  public void setContacts(NSArray<ERFBContact> contacts) {
    _contacts = contacts;
  }

  public String invoiceNumber() {
    return _invoiceNumber;
  }

  public void setInvoiceNumber(String invoiceNumber) {
    _invoiceNumber = invoiceNumber;
  }

  public String status() {
    return _status;
  }

  public void setStatus(String status) {
    _status = status;
  }

  public NSTimestamp date() {
    return _date;
  }

  public void setDate(NSTimestamp date) {
    _date = date;
  }

  public String poNumber() {
    return poNumber;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public Integer discount() {
    return _discount;
  }

  public void setDiscount(Integer discount) {
    _discount = discount;
  }

  public String notes() {
    return _notes;
  }

  public void setNotes(String notes) {
    _notes = notes;
  }

  public String currencyCode() {
    return _currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    _currencyCode = currencyCode;
  }

  public String language() {
    return _language;
  }

  public void setLanguage(String language) {
    _language = language;
  }

  public String terms() {
    return _terms;
  }

  public void setTerms(String terms) {
    _terms = terms;
  }

  public String returnUrl() {
    return _returnUrl;
  }

  public void setReturnUrl(String returnUrl) {
    _returnUrl = returnUrl;
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

  public ERFBAddress primaryAddress() {
    return _primaryAddress;
  }

  public void setPrimaryAddress(ERFBAddress primaryAddress) {
    _primaryAddress = primaryAddress;
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

  public NSArray<ERFBLine> lines() {
    return _lines;
  }

  public void setLines(NSArray<ERFBLine> lines) {
    _lines = lines;
  }
  
  public static ERXML.E transformToXml(ERFBInvoice invoice) {
    ERXML.E invoiceXmlElement = new ERXML.E("client");
    {
      
      invoiceXmlElement.e("invoice_id", invoice.id());
      invoiceXmlElement.e("first_name", invoice.firstName());
      invoiceXmlElement.e("last_name", invoice.lastName());
      invoiceXmlElement.e("currency_code", invoice.currencyCode());
      invoiceXmlElement.e("language", invoice.language());
      invoiceXmlElement.e("notes", invoice.notes());
      invoiceXmlElement.e("organization", invoice.organization());
      invoiceXmlElement.e("vat_name", invoice.vatName());
      invoiceXmlElement.e("vat_number", invoice.vatNumber());
      invoiceXmlElement.e("vat_number", invoice.notes());
      invoiceXmlElement.e("return_uri", invoice.returnUrl());
      invoiceXmlElement.e("status", invoice.status());
      invoiceXmlElement.e("terms", invoice.terms());
      invoiceXmlElement.e("po_number", invoice.poNumber());
      if (invoice.client() != null) {
        invoiceXmlElement.e("client_id", invoice.client().id());
      }
      if (invoice.date() != null) {
        invoiceXmlElement.e("date", ERFreshBooks.sharedInstance().dateFormatter().format(invoice.date()));      
      }
      invoiceXmlElement.e("discount", invoice.discount().toString());
      invoiceXmlElement.e("number", invoice.invoiceNumber());

      if ((invoice.contacts() != null) && (!invoice.contacts().isEmpty())) {
        ERXML.E contacts = invoiceXmlElement.e("contacts");
        for (ERFBContact contact: invoice.contacts()) {
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
      
      if (invoice.primaryAddress() != null) {
        invoiceXmlElement.e("p_street1", invoice.primaryAddress().street1());
        invoiceXmlElement.e("p_street2", invoice.primaryAddress().street2());
        invoiceXmlElement.e("p_city", invoice.primaryAddress().city());
        invoiceXmlElement.e("p_state", invoice.primaryAddress().state());
        invoiceXmlElement.e("p_country", invoice.primaryAddress().country());
        invoiceXmlElement.e("p_code", invoice.primaryAddress().postalCode());
      }
      
      if ((invoice.lines() != null) && (!invoice.lines().isEmpty())) {
        ERXML.E lines = invoiceXmlElement.e("lines");
        for (ERFBLine line: invoice.lines()) {
          ERXML.E xmlContact = lines.e("line");
          {
            xmlContact.e("line_id", line.id());
            xmlContact.e("amount", line.amount().toString());
            xmlContact.e("name", line.name());
            xmlContact.e("description", line.description());
            xmlContact.e("quantity", line.quantity().toString());
            xmlContact.e("tax1_name", line.tax1Name());
            xmlContact.e("tax2_name", line.tax2Name());
            xmlContact.e("tax1_percent", line.tax1Percent().toString());
            xmlContact.e("tax2_percent", line.tax2Percent().toString());
            xmlContact.e("type", line.type());
          }
        }
      }
    }
    return invoiceXmlElement;
  }
  
  public static ERFBInvoice transformFromResponse(ERXML.E xmlElement) {
    ERFBInvoice invoice = new ERFBInvoice();
    ERFBApi api = new ERFBApi();
    
    invoice.setId(xmlElement.child("invoice_id").text());
    invoice.setFirstName(xmlElement.child("first_name").text());
    invoice.setLastName(xmlElement.child("last_name").text());
    invoice.setCurrencyCode(xmlElement.child("currency_code").text());
    invoice.setLanguage(xmlElement.child("language").text());
    invoice.setNotes(xmlElement.child("notes").text());
    invoice.setOrganization(xmlElement.child("organization").text());
    invoice.setPrimaryAddress(ERFBAddress.transformPrimaryAddressFromResponse(xmlElement));
    invoice.setVatName(xmlElement.child("vat_name").text());
    invoice.setVatNumber(xmlElement.child("vat_number").text());
    invoice.setNotes(xmlElement.child("notes").text());
    invoice.setReturnUrl(xmlElement.child("return_uri").text());
    invoice.setStatus(xmlElement.child("status").text());
    invoice.setTerms(xmlElement.child("terms").text());
    invoice.setPoNumber(xmlElement.child("po_number").text());
    invoice.setClient(api.getClient(xmlElement.child("client_id").text()));
    
    if ((xmlElement.child("date") != null) && (xmlElement.child("date").text() != null)) {
      Date dateAsString;
      try {
        dateAsString = ERFreshBooks.sharedInstance().dateFormatter().parse(xmlElement.child("date").text());
        invoice.setDate(new NSTimestamp(dateAsString));      
      }
      catch (ParseException e) {
        NSLog.err.appendln("Can't format invoice date: " + e.getMessage());
      }
    }
    
    if ((xmlElement.child("discount") != null) && (xmlElement.child("discount").text() != null)) {
      invoice.setDiscount(Integer.valueOf(xmlElement.child("discount").text()));      
    }
    
    invoice.setInvoiceNumber(xmlElement.child("number").text());
    
    NSMutableArray<ERFBContact> contacts = new NSMutableArray<ERFBContact>();
    ERXML.E contactsElement = xmlElement.child("contacts");
    if (contactsElement.children() != null) {
      for (Item contactInXml: contactsElement.children()) {
        if (contactInXml instanceof ERXML.E) {
          ERXML.E contactId = ((ERXML.E)contactInXml).child("contact_id");
          if (contactId != null) {
            for (ERFBContact contact: invoice.client().contacts()) {
              if (contactId.text().equals(contact.id())) {
                contacts.addObject(contact);                
              }
            }
          } else {
            ERFBContact contact = ERFBContact.transformFromResponse((ERXML.E)contactInXml);
            contacts.addObject(contact);
          }
        }
      }
    }
    invoice.setContacts(contacts.immutableClone());

    NSMutableArray<ERFBLine> items = new NSMutableArray<ERFBLine>();
    ERXML.E linesElement = xmlElement.child("lines");
    if (linesElement.children() != null) {
      for (Item lineInXml: linesElement.children()) {
        if (lineInXml instanceof ERXML.E) {
          ERFBLine line = ERFBLine.transformFromResponse((ERXML.E)lineInXml);
          items.addObject(line);
        }
      }
    }
    invoice.setLines(items.immutableClone());
        
    return invoice;
  }
  
  @Override
  public String toString() {
    return "Invoice of id " + id() + " for " + organization();
  }

}
