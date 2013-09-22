package er.freshbooks;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import er.xiss.ERXML;
import er.xiss.ERXML.E;
import er.xiss.ERXML.Item;

public class ERFBApi {

  public ERFBApi() {

  }


  /**
   * Create an invoice on FreshBooks. 
   * If the creation is succesful, the invoiceId will be set in the ERFBInvoice object.
   * 
   * @param invoice A ERFBInvoice object to create on FreshBooks
   */
  public void createInvoice(ERFBInvoice invoice) {
    ERXML.Doc doc = ERXML.doc();
    ERXML.E rootElement = doc.root("request").set("method", "invoice.create");
    ERXML.E clientElement = ERFBInvoice.transformToXml(invoice);
    rootElement.add(clientElement);
    ERXML.Doc result = executeRequest(doc);
    String status = result.root().get("status");
    if ("ok".equals(status)) {
      Set<E> descendents = result.root().descendents("invoice_id");
      for (ERXML.E descendent: descendents) {
        String invoiceIdValue = ((ERXML.E)descendent).text();
        if (invoiceIdValue != null) {
          invoice.setId(invoiceIdValue);
        }
      }
    }
  }

  /**
   * Return a list of invoices. 
   * You can filter the list by adding values to the following keys in the dictionary:
   * 
   * - clientId: a string that identify the client (you can get the id by calling ERFBClient.id()
   * - recurringId: a string that identify a recurring object
   * - status: sent, viewed or draft
   * - number: fetch a specific invoice by its number
   * - dateFrom: return invoices dated after this date. Use ERFreshBooks.sharedInstance.dateFormatter() to get the correct format.
   * - dateTo: return invoices dated before this date. Use ERFreshBooks.sharedInstance.dateFormatter() to get the correct format.
   * - updatedFrom: return invoices modified after this date. Use ERFreshBooks.sharedInstance.dateFormatter() to get the correct format.
   * - updatedTo: return invoices modified before this date. Use ERFreshBooks.sharedInstance.dateFormatter() to get the correct format.
   * - per_page: number of results per page, default is 25. The value in the dict must be a string, not a int
   * - folder: either active', 'archived' or 'deleted'
   * 
   * @param filtersMap Dict that contains a list of filters to apply, pass an empty dict if you don't need any filtering
   * @param pageNumber The page to fetch, if the result set have more than one page. Defaults to 1.
   * @return a list of ERFBInvoice
   */
  public NSArray<ERFBInvoice> getInvoices(NSDictionary<String, String> filtersMap, Integer pageNumber) {
    ERXML.Doc doc = ERXML.doc();
    ERXML.E person = doc.root("request").set("method", "invoice.list");
    {
      Object clientId = filtersMap.valueForKey("clientId");
      if (clientId != null) 
        person.e("client_id", (String)clientId);

      Object recurringId = filtersMap.valueForKey("recurringId");
      if (recurringId != null)
        person.e("recurring_id", (String)recurringId);

      Object status = filtersMap.valueForKey("status");
      if (status != null)
        person.e("status", (String)status);

      Object number = filtersMap.valueForKey("number");
      if (number != null)
        person.e("number", (String)number);

      if (pageNumber != null)
        person.e("page", pageNumber.toString());

      Object dateFrom = filtersMap.valueForKey("dateFrom");
      if (dateFrom != null)
        person.e("date_from", (String)dateFrom);

      Object dateTo = filtersMap.valueForKey("dateTo");
      if (dateTo != null)
        person.e("date_to", (String)dateTo);
      
      Object updatedFrom = filtersMap.valueForKey("updatedFrom");
      if (updatedFrom != null)
        person.e("updated_from", (String)updatedFrom);

      Object updatedTo = filtersMap.valueForKey("updatedTo");
      if (updatedTo != null)
        person.e("updated_to", (String)updatedTo);

      Object folder = filtersMap.valueForKey("folder");
      if (folder != null)
        person.e("folder", (String)folder);
    }

    NSMutableArray<ERFBInvoice> clients = new NSMutableArray<ERFBInvoice>();

    ERXML.Doc result = executeRequest(doc);
    for (Item child: result.children()) {
      if (child instanceof ERXML.E) {

        int currentPage = 1;

        int totalPages = 1;
        for (ERXML.E invoiceNode: ((ERXML.E) child).children("invoices")) {
          String pagesAsString = invoiceNode.get("pages");
          if (pagesAsString != null)
            totalPages = Integer.valueOf(pagesAsString);

          String pageAsString = invoiceNode.get("page");
          if (pageAsString != null)
            currentPage = Integer.valueOf(pageAsString);
        }


        if (child instanceof ERXML.E) {
          Set<E> descendents = ((ERXML.E) child).descendents("invoice");
          for (ERXML.E descendent: descendents) {
            clients.addObject(ERFBInvoice.transformFromResponse(descendent));
          }
        }

        if (currentPage < totalPages) {
          NSArray<ERFBInvoice> otherInvoices = getInvoices(filtersMap, currentPage + 1);
          clients.addAll(otherInvoices);
        } 

      }
    }

    return clients.immutableClone();    
  }

  /**
   * Create a new client on FreshBook by calling the client.create method. If the request is successful,
   * the client object is updated with the clientId created by FreshBook.
   * 
   * @param client a ERFBClient object with the fields set to create a client (the only required one is email()
   */
  public void createClient(ERFBClient client) {
    ERXML.Doc doc = ERXML.doc();
    ERXML.E rootElement = doc.root("request").set("method", "client.create");
    ERXML.E clientElement = ERFBClient.transformToXml(client);
    rootElement.add(clientElement);
    ERXML.Doc result = executeRequest(doc);
    String status = result.root().get("status");
    if ("ok".equals(status)) {
      Set<E> descendents = result.root().descendents("client_id");
      for (ERXML.E descendent: descendents) {
        String clientIdValue = ((ERXML.E)descendent).text();
        if (clientIdValue != null) {
          client.setId(clientIdValue);
        }
      }
    }
  }

  /**
   * Get a client by its Id (ERFBClient.clientId()).
   * 
   * @param clientId The id of the client
   * @return a ERFBClient that holds all data from the response
   */
  public ERFBClient getClient(String clientId) {
    ERXML.Doc doc = ERXML.doc();
    ERXML.E person = doc.root("request").set("method", "client.get");
    person.add(new ERXML.E("client_id", clientId));

    ERXML.Doc result = executeRequest(doc);
    String status = result.root().get("status");
    if ("ok".equals(status)) {
      for (Item child: result.children()) {
        if (child instanceof ERXML.E) {
          Set<E> descendents = ((ERXML.E) child).descendents("client");
          for (ERXML.E descendent: descendents) {
            return ERFBClient.transformFromResponse(descendent);
          }
        }
      }
    }
    return null;
  }

  /**
   * Fetch clients. The filtersMap dictionary is to filter the client list. 
   * You can use one of the following keys in the dictionary:
   * 
   * - email: filter clients by email (the same email address can be used for more than one client)
   * - username: filter clients by username
   * - updated_from: return only clients modified since this date
   * - updated_to: Return only clients modified before this date
   * - per_page: Number of results per page, default 25
   * - folder: One of 'active', 'archived', 'deleted'
   * - notes: Return only clients with this text in their 'notes'
   * 
   * @param filtersMap
   * @param pageNumber
   * @return an array of ERFBClient
   */
  public NSArray<ERFBClient> getClients(NSDictionary<String,String> filtersMap, Integer pageNumber) {
    ERXML.Doc doc = ERXML.doc();
    ERXML.E person = doc.root("request").set("method", "client.list");
    {
      Object email = filtersMap.valueForKey("email");
      if (email != null) 
        person.e("email", (String)email);

      Object username = filtersMap.valueForKey("username");
      if (username != null)
        person.e("username", (String)username);

      Object updatedFrom = filtersMap.valueForKey("updated_from");
      if (updatedFrom != null)
        person.e("updated_from", (String)updatedFrom);

      Object updatedTo = filtersMap.valueForKey("updated_to");
      if (updatedTo != null)
        person.e("updated_to", (String)updatedTo);

      if (pageNumber != null)
        person.e("page", pageNumber.toString());

      Object perPage = filtersMap.valueForKey("per_page");
      if (perPage != null)
        person.e("per_page", (String)perPage);

      Object folder = filtersMap.valueForKey("folder");
      if (folder != null)
        person.e("folder", (String)folder);

      Object notes = filtersMap.valueForKey("notes");
      if (notes != null)
        person.e("notes", (String)notes);
    }

    NSMutableArray<ERFBClient> clients = new NSMutableArray<ERFBClient>();

    ERXML.Doc result = executeRequest(doc);
    for (Item child: result.children()) {
      if (child instanceof ERXML.E) {

        int currentPage = 1;
        String pageAsString = ((ERXML.E) child).get("page");
        if (pageAsString != null)
          Integer.valueOf(pageAsString);

        int totalPages = 1;
        String pagesAsString = ((ERXML.E) child).get("pages");
        if (pagesAsString != null)
          Integer.valueOf(pagesAsString);

        if (currentPage < totalPages) {
          NSArray<ERFBClient> otherClients = getClients(filtersMap, currentPage + 1);
          clients.addAll(otherClients);
        } else {
          if (child instanceof ERXML.E) {
            Set<E> descendents = ((ERXML.E) child).descendents("client");
            for (ERXML.E descendent: descendents) {
              clients.addObject(ERFBClient.transformFromResponse(descendent));
            }
          }
        }
      }
    }

    return clients.immutableClone();
  }

  /**
   * Get all root categories. Sub-categories will also be fetched.
   * 
   * @return
   */
  public NSArray<ERFBCategory> getCategories() {
    return getCategories(null);
  }

  /**
   * Get the children categories from a parent category
   * 
   * @param parent The parent category to fetch children for
   * @return All childrend of the category
   */
  protected NSArray<ERFBCategory> getCategories(ERFBCategory parent) {

    ERXML.Doc doc = null;

    if (parent != null) {
      doc = ERXML.doc(
          ERXML.e("request",
              ERXML.a("method", "category.list"),
              ERXML.e("parent_id", parent.id())
              )
          );
    } else {
      doc = ERXML.doc(
          ERXML.e("request",
              ERXML.a("method", "category.list")
              )
          );      
    }

    NSMutableArray<ERFBCategory> categories = new NSMutableArray<ERFBCategory>();

    ERXML.Doc result = executeRequest(doc);
    for (Item child: result.children()) {
      if (child instanceof ERXML.E) {
        Set<E> descendents = ((ERXML.E) child).descendents("category");
        for (ERXML.E descendent: descendents) {
          ERFBCategory category = new ERFBCategory();
          category.setId(descendent.child("category_id").text());
          category.setName(descendent.child("name").text());

          if (parent != null) {
            category.setParent(parent);
          }

          NSArray<ERFBCategory> childrenForParent = getCategories(category);
          if (!childrenForParent.isEmpty()) {
            category.setChildren(childrenForParent);
          }

          categories.addObject(category);
        }
      }
    }


    return categories.immutableClone();
  }

  /**
   * Create a payment
   */
  public void createPayment(ERFBPayment payment) {
    ERXML.Doc doc = ERXML.doc();
    ERXML.E rootElement = doc.root("request").set("method", "payment.create");
    ERXML.E clientElement = ERFBPayment.transformToXml(payment);
    rootElement.add(clientElement);
    ERXML.Doc result = executeRequest(doc);
    String status = result.root().get("status");
    if ("ok".equals(status)) {
      Set<E> descendents = result.root().descendents("payment_id");
      for (ERXML.E descendent: descendents) {
        String invoiceIdValue = ((ERXML.E)descendent).text();
        if (invoiceIdValue != null) {
          payment.setId(invoiceIdValue);
        }
      }
    }
  }
  
  /**
   * Execute the HTTP request and return the XML doc from the response
   * 
   * @param requestBody The body of the HTTP request
   * @return the XML doc from the response
   */
  protected ERXML.Doc executeRequest(ERXML.Doc requestBody) {
    ERXML.Doc result = new ERXML.Doc();

    DefaultHttpClient httpclient = new DefaultHttpClient();
    httpclient.getCredentialsProvider().setCredentials(
        new AuthScope(ERFreshBooks.sharedInstance().apiHost(), 443),
        new UsernamePasswordCredentials(ERFreshBooks.sharedInstance().apiToken(), "x"));

    HttpPost httpPost = new HttpPost(ERFreshBooks.sharedInstance().apiUrl());
    try {
      httpPost.setEntity(new StringEntity(requestBody.toString(), "UTF-8"));
      HttpResponse response2 = httpclient.execute(httpPost);

      HttpEntity entity2 = response2.getEntity();

      StringWriter writer = new StringWriter();
      IOUtils.copy(entity2.getContent(), writer, "UTF-8");
      String theString = writer.toString();

      result = ERXML.doc(theString);
      EntityUtils.consume(entity2);
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    catch (ClientProtocolException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    } finally {
      httpclient.getConnectionManager().shutdown();
    }

    return result;
  }
}
