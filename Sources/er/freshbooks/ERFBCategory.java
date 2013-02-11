package er.freshbooks;

import com.webobjects.foundation.NSArray;

public class ERFBCategory {

  private String _id;
  private String _name;
  private ERFBCategory _parent;
  private NSArray<ERFBCategory> _children;
  
  public ERFBCategory() {
    
  }

  public String id() {
    return _id;
  }

  public void setId(String id) {
    _id = id;
  }

  public String name() {
    return _name;
  }

  public void setName(String name) {
    _name = name;
  }

  public ERFBCategory parent() {
    return _parent;
  }

  public void setParent(ERFBCategory parent) {
    _parent = parent;
  }

  public NSArray<ERFBCategory> children() {
    return _children;
  }

  public void setChildren(NSArray<ERFBCategory> children) {
    _children = children;
  }
  
}
