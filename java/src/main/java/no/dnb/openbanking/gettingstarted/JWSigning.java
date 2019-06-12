package no.dnb.openbanking.gettingstarted;

import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.lang.JoseException;

public class JWSigning {

  public static String getSignedClientAssertion() throws Exception {

    JsonWebSignature jws = new JsonWebSignature();
    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);

    //move this to keystore
    String jsonKeys = "{"+
      "\"keys\": ["+
      "{"+
      "\"kty\": \"EC\","+
      "\"d\": \"EJMKmgQyRd4cjR0HfFoF_BBURVH8wDW3uE0UD4wwOJo\","+
      "\"crv\": \"P-256\","+
      "\"x\": \"10mRcA9EUQaXeLunpQydZiCOzVVAhSBWg9ZzCO4ZRbg\","+
      "\"y\": \"JxgF8WvaV-Agq8Vls5VLHys-guK6jt9yU-TwBDx2GqY\"" +
      "}"+
      "]"+
      "}";
    JsonWebKeySet jwks = new JsonWebKeySet(jsonKeys);
    String jti = ((System.currentTimeMillis()/1000)+3600)+"";
    String clientAssertion="{\"iss\":\"testtesttest123\",\"aud\":\"https://openBank-api.dev.ciam.tech-03.net/as/token.oauth2\",\"sub\":\"testtesttest123\",\"jti\":\""+jti+"\",\"exp\":"+jti+"}";
    EllipticCurveJsonWebKey jwk = (EllipticCurveJsonWebKey)jwks.getJsonWebKeys().get(0);
    jws.setKey(jwk.getEcPrivateKey());
    jws.setPayload(clientAssertion);
    String signedClientAssertion = jws.getCompactSerialization();
    return signedClientAssertion;
  }
}
