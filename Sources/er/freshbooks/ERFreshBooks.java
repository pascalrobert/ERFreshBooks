package er.freshbooks;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import er.extensions.ERXExtensions;
import er.extensions.ERXFrameworkPrincipal;
import er.extensions.foundation.ERXProperties;
import er.xiss.ERXiss;

public class ERFreshBooks extends ERXFrameworkPrincipal {

  public final static Class<?> REQUIRES[] = new Class[] { ERXExtensions.class, ERXiss.class };
  protected final static SimpleDateFormat _dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
  protected static ERFreshBooks sharedInstance;
  private String _apiUrl;
  private String _apiToken;
  private String _apiHost;

  static {
    setUpFrameworkPrincipalClass(ERFreshBooks.class);
  }
  
  public static synchronized ERFreshBooks sharedInstance() {
    if (sharedInstance == null) {
      sharedInstance = ERXFrameworkPrincipal.sharedInstance(ERFreshBooks.class);
    }
    return sharedInstance;
  }

  @Override
  public void finishInitialization() {
    initializeFrameworkFromSystemProperties();
    log.debug("ERFreshBooks loaded");
  }

  public void initializeFrameworkFromSystemProperties() {
    String apiToken = ERXProperties.stringForKey("er.freshbook.api.token");
    setApiToken(apiToken);
    log.debug("er.freshbook.api.token: <hidden>");
    
    String apiUrl = ERXProperties.stringForKey("er.freshbook.api.url");
    setApiUrl(apiUrl);
    log.debug("er.freshbook.api.url: " + apiUrl);
  }

  public void setApiUrl(String apiUrl) {
    _apiUrl = apiUrl;
    try {
      java.net.URL url = new URL(_apiUrl);
      _apiHost = url.getHost();
    }
    catch (MalformedURLException e) {
      log.debug("Can't create URL for " + _apiUrl + ": " + e.getMessage());
    }
  }
  
  public String apiToken() {
    return _apiToken;
  }

  public void setApiToken(String apiToken) {
    _apiToken = apiToken;
  }
  
  public String apiUrl() {
    return _apiUrl;
  }
  
  public String apiHost() {
    return _apiHost;
  }
  
  public SimpleDateFormat dateFormatter() {
    return _dateFormatter;
  }
}
