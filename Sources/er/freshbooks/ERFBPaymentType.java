package er.freshbooks;

import java.util.HashMap;
import java.util.Map;

public enum ERFBPaymentType {

  CASH("Cash"),
  CHECK("Check"),
  CREDIT("Credit"),
  CREDIT_CARD("Credit Card"), 
  BANK_TRANSFER("Bank Transfer"), 
  DEBIT("Debit"),
  PAYPAL("PayPal"),
  TWO_CHECKOUT("2Checkout"),
  VISA("VISA"),
  MASTERCARD("MASTERCARD"), 
  DISCOVER("DISCOVER"), 
  NOVA("NOVA"),
  AMEX("AMEX"), 
  DINERS("DINERS"), 
  EUROCARD("EUROCARD"), 
  JCB("JCB"),
  ACH("ACH");
  
  private String _description;
  private static final Map<String,ERFBPaymentType> descriptionLookup = new HashMap<String,ERFBPaymentType>();

  private ERFBPaymentType(String description) {
    _description = description;
  }
  
  public String description() {
    return _description;
  }
  
  public static ERFBPaymentType getByDescription(String description) { 
    return descriptionLookup.get(description); 
  }

}
