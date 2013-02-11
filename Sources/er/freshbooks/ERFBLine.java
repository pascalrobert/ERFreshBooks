package er.freshbooks;

import er.xiss.ERXML.E;

public class ERFBLine {

  private String _id;
  private Float _amount;
  private String _name;
  private String _description;
  private Float _unitCost;
  private Integer quantity;
  private String _tax1Name;
  private String _tax2Name;
  private Float _tax1Percent;
  private Float _tax2Percent;
  private String _type;

  public ERFBLine() {

  }

  public String id() {
    return _id;
  }

  public void setId(String id) {
    _id = id;
  }
  
  public Float amount() {
    return _amount;
  }

  public void setAmount(Float unitCost) {
    this._amount = unitCost;
  }
  
  public String name() {
    return _name;
  }

  public void setName(String name) {
    _name = name;
  }

  public String description() {
    return _description;
  }

  public void setDescription(String description) {
    _description = description;
  }

  public Float unitCost() {
    return _unitCost;
  }

  public void setUnitCost(Float unitCost) {
    _unitCost = unitCost;
  }

  public Integer quantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public String tax1Name() {
    return _tax1Name;
  }

  public void setTax1Name(String tax1Name) {
    _tax1Name = tax1Name;
  }

  public String tax2Name() {
    return _tax2Name;
  }

  public void setTax2Name(String tax2Name) {
    _tax2Name = tax2Name;
  }

  public Float tax1Percent() {
    return _tax1Percent;
  }

  public void setTax1Percent(Float tax1Percent) {
    _tax1Percent = tax1Percent;
  }

  public Float tax2Percent() {
    return _tax2Percent;
  }

  public void setTax2Percent(Float tax2Percent) {
    _tax2Percent = tax2Percent;
  }

  public String type() {
    return _type;
  }

  public void setType(String type) {
    _type = type;
  }

  public static ERFBLine transformFromResponse(E xmlElement) {
    ERFBLine invoice = new ERFBLine();
    
    invoice.setName(xmlElement.child("name").text());
    invoice.setTax1Name(xmlElement.child("tax1_name").text());
    invoice.setTax2Name(xmlElement.child("tax2_name").text());

    if ((xmlElement.child("unit_cost") != null) && (xmlElement.child("unit_cost").text() != null)) {
      invoice.setUnitCost(Float.valueOf(xmlElement.child("unit_cost").text()));
    }
    
    if ((xmlElement.child("quantity") != null) && (xmlElement.child("quantity").text() != null)) {
      invoice.setQuantity(Integer.valueOf(xmlElement.child("quantity").text()));
    }
        
    if ((xmlElement.child("tax1_percent") != null) && (xmlElement.child("tax1_percent").text() != null)) {
      invoice.setTax1Percent(Float.valueOf(xmlElement.child("tax1_percent").text()));
    }
    
    if ((xmlElement.child("tax2_percent") != null) && (xmlElement.child("tax2_percent").text() != null)) {
      invoice.setTax1Percent(Float.valueOf(xmlElement.child("tax2_percent").text()));
    }

    if ((xmlElement.child("amount") != null) && (xmlElement.child("amount").text() != null)) {
      invoice.setAmount(Float.valueOf(xmlElement.child("amount").text()));
    }

    invoice.setType(xmlElement.child("type").text());
    return invoice;
  }

  @Override
  public String toString() {
    return "Line : " + name();
  }

}
