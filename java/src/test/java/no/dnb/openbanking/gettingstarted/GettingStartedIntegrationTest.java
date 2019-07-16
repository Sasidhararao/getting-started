package no.dnb.openbanking.gettingstarted;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class GettingStartedIntegrationTest {

  private static String accessToken;

  @BeforeAll
  static void initAll() throws Exception {

    accessToken = GettingStarted.getOauth2Token();
  }

  @Test
  void testGetApiToken() {
    assertTrue(accessToken.length() > 500);
  }

  //JSONArray
  @Test
  void testGetTestCustomersInfoAPI() throws Exception {
    JSONArray expectedTestCustomersResponse = TestUtil.parseJSONFileFromResourceToJSONArray(
        "GetTestCustomers.json");
    HttpResponse<JsonNode> actualTestCustomersResponse = GettingStarted.getTestCustomers(accessToken);

    assertThat(actualTestCustomersResponse.getStatus())
        .as("Test if status code is 200/OK").isEqualTo(200);

    JSONArray actualTestCustomersJSONResponse = actualTestCustomersResponse.getBody().getArray();

    assertThat(actualTestCustomersJSONResponse.length())
        .as("Check if objects have same amount of fields")
        .isEqualTo(expectedTestCustomersResponse.length());
    JSONAssert.assertEquals(expectedTestCustomersResponse, actualTestCustomersJSONResponse , false);
  }

  @Test
  void testGetCustomerInfoAPI() throws Exception{
    JSONObject expectedCustomerDetailsResponse = TestUtil.parseJSONFileFromResourceToJSONObject(
            "GetCustomerDetails.json");
    HttpResponse<JsonNode> actualCustomerDetailsResponse = GettingStarted.getCustomerInfo(accessToken);

    assertThat(actualCustomerDetailsResponse.getStatus())
            .as("Test if status code is 200/OK").isEqualTo(200);

    JSONObject actualCustomerDetailsJSONResponse = actualCustomerDetailsResponse.getBody().getObject();

    assertThat(actualCustomerDetailsJSONResponse.length())
            .as("Check if objects have same amount of fields")
            .isEqualTo(expectedCustomerDetailsResponse.length());
    JSONAssert.assertEquals(expectedCustomerDetailsResponse, actualCustomerDetailsJSONResponse, false);
  }

  @Test
  void testGetCardInfoAPI() throws Exception {
    JSONArray expectedCardDetailsResponse = TestUtil.parseJSONFileFromResourceToJSONArray(
            "GetCardDetails.json");
    HttpResponse<JsonNode>  actualCardDetailsResponse = GettingStarted.getCardInfo(accessToken);

    assertThat(actualCardDetailsResponse.getStatus())
            .as("Test if status code is 200/OK").isEqualTo(200);

    JSONArray actualCardDetailsJSONResponse = actualCardDetailsResponse.getBody().getArray();

    assertThat(actualCardDetailsJSONResponse.length())
            .as("Check if objects have same amount of fields")
            .isEqualTo(expectedCardDetailsResponse.length());
    JSONAssert.assertEquals(expectedCardDetailsResponse, actualCardDetailsJSONResponse, false);
  }

  @Test
  void testGetCurrencyConversions() throws Exception {
    JSONArray expectedCardDetailsResponse = TestUtil.parseJSONFileFromResourceToJSONArray(
        "GetCurrencyConversions.json");
    HttpResponse<JsonNode> actualCardDetailsResponse = GettingStarted.getCurrencyConversions("NOK",accessToken);

    assertThat(actualCardDetailsResponse.getStatus())
        .as("Test if status code is 200/OK").isEqualTo(200);

    JSONArray actualCardDetailsJSONResponse = actualCardDetailsResponse.getBody().getArray();

    assertThat(actualCardDetailsJSONResponse.length())
        .as("Check if objects have same amount of fields")
        .isEqualTo(expectedCardDetailsResponse.length());
    JSONAssert.assertEquals(expectedCardDetailsResponse, actualCardDetailsJSONResponse, false);
  }

  @Test
  void testGetCurrencyConversion() throws Exception{
    JSONObject expectedCustomerDetailsResponse = TestUtil.parseJSONFileFromResourceToJSONObject(
        "GetCurrencyConversion.json");
    HttpResponse<JsonNode> response = GettingStarted.getCurrencyConversion("NOK", "EUR",accessToken);

    assertThat(response.getStatus())
        .as("Test if status code is 200/OK").isEqualTo(200);

    JSONObject json = response.getBody().getObject();

    assertThat(json.length())
        .as("Check if objects have same amount of fields")
        .isEqualTo(expectedCustomerDetailsResponse.length());
    JSONAssert.assertEquals(expectedCustomerDetailsResponse, json, false);
  }
}