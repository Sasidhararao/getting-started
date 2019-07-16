package no.dnb.openbanking.gettingstarted;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;

public class GettingStarted {

  // Open Banking constants
  private static final String OPENBANKING_ENDPOINT = "https://openBank-api.dev.ciam.tech-03.net";
  private static final String CLIENT_ASSERTION_TYPE = "urn:ietf:params:oauth:client-assertion-type:jwt-bearer";
  private static final String SCOPE = "currencies";
  static final String TOKEN_ENDPOINT = "https://openBank-api.dev.ciam.tech-03.net/as/token.oauth2";



  public static String getOauth2Token() throws Exception {

    final String requestBody = "grant_type=client_credentials" +
            "&client_id=" + Config.get("CLIENT_ID") +
            "&client_assertion="+ JWSigning.getSignedClientAssertion() +
            "&client_assertion_type=" + CLIENT_ASSERTION_TYPE +
            "&scope=" + SCOPE;

    HttpResponse<JsonNode> response;
    response = Unirest.post(TOKEN_ENDPOINT)
            .header("content-type", "application/x-www-form-urlencoded")
            .body(requestBody)
            .asJson();

    if(response.getStatus() == 200) {
      final  JSONObject token  = response.getBody().getObject();
      return token.getString("access_token");
    }
    throw new Exception("Error in getting access token");
  }

  public static HttpResponse<JsonNode> ExecuteGet(final String Uri, final String accessToken)  throws  Exception{

    return   Unirest.get(OPENBANKING_ENDPOINT + Uri)
            .header("Authorization", "Bearer " + accessToken)
            .asJson();

  }

  // JSONArray
  public static HttpResponse<JsonNode> getTestCustomers(final  String accessToken)  throws Exception{

    return ExecuteGet("/test-customers" , accessToken);
  }

  // JSONObject
  public static HttpResponse<JsonNode> getCustomerInfo(final String accessToken) throws Exception{

    return ExecuteGet("/customers/current" , accessToken);
  }

  //JSONArray
  public static HttpResponse<JsonNode> getCurrencyConversions(String quoteCurrency, String accessToken) throws  Exception{

    return ExecuteGet("/currencies/v1/convert/" + quoteCurrency, accessToken);

  }

  // JSONObject
  public static HttpResponse<JsonNode> getCurrencyConversion (
      String quoteCurrency, String baseCurrency, String accessToken) throws Exception {

    return ExecuteGet("/currencies/v1/" + baseCurrency + "convert" + quoteCurrency, accessToken);

  }
  // JSONArray
  public static HttpResponse<JsonNode> getCardInfo(final String accessToken) throws Exception{

    return ExecuteGet("/cards" , accessToken);

  }

  public static void main(final String[] args) throws Exception {

    final String accessToken = getOauth2Token();

    final HttpResponse<JsonNode> testCustomers = getTestCustomers(accessToken);
    System.out.println("Test customers: " + testCustomers.getBody().getArray().toString(4));

    final HttpResponse<JsonNode> currenciesResponse = getCurrencyConversions("NOK", accessToken);
    System.out.println("Currencies: " + currenciesResponse.getBody().getArray().toString(4));

    final HttpResponse<JsonNode> currencyResponse = getCurrencyConversion("NOK", "EUR", accessToken);
    System.out.println("Currency: " + currencyResponse.getBody().getObject().toString(4));

    final HttpResponse<JsonNode> customerResponse = getCustomerInfo(accessToken);
    System.out.println("Customer info: " + customerResponse.getBody().getObject().toString(4));

    final HttpResponse<JsonNode> cardResponse = getCardInfo(accessToken);
    System.out.println("Card info: " + cardResponse.getBody().getArray().toString(4));

  }

}
