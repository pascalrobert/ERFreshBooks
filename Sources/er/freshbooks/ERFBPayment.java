package er.freshbooks;

import com.webobjects.foundation.NSTimestamp;

import er.xiss.ERXML;

public class ERFBPayment {

  private ERFBClient _client;
  private ERFBInvoice _invoice;
  private NSTimestamp _date;
  private Float amount;
  private ERFBPaymentType type;
  private String _id;
  
  public ERFBPayment() {

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

  public ERFBInvoice invoice() {
    return _invoice;
  }

  public void setInvoice(ERFBInvoice invoice) {
    _invoice = invoice;
  }

  public NSTimestamp date() {
    return _date;
  }

  public void setDate(NSTimestamp date) {
    _date = date;
  }

  public Float amount() {
    return amount;
  }

  public void setAmount(Float amount) {
    this.amount = amount;
  }

  public ERFBPaymentType type() {
    return type;
  }

  public void setType(ERFBPaymentType type) {
    this.type = type;
  }
  
  public static ERXML.E transformToXml(ERFBPayment payment) {
    ERXML.E paymentXmlElement = new ERXML.E("payment");
    {
      paymentXmlElement.e("payment_id", payment.id());
      paymentXmlElement.e("client_id", payment.client().id());
      paymentXmlElement.e("invoice_id", payment.invoice().id());
      if (payment.date() != null) {
        paymentXmlElement.e("date", ERFreshBooks.sharedInstance().dateFormatter().format(payment.date()));
      }
      if (payment.amount() != null) {
        paymentXmlElement.e("amount", payment.amount().toString());
      }
      paymentXmlElement.e("type", payment.type.description());
    }
    return paymentXmlElement;
  }
}
