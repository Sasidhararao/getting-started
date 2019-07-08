package no.dnb.openbanking.gettingstarted;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;

import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public class JWSigning {

  public static String getSignedClientAssertion() throws Exception {


    KeyPair keyPair =  PemToJwksConverter.readKeyPairFromPem();

    String jti = ((System.currentTimeMillis()/1000)+3600)+"";
    String clientAssertion="{\"iss\":\"" + Config.get("CLIENT_ID") + "\"," +
                        "\"aud\":\"" + GettingStarted.TOKEN_ENDPOINT + "\"," +
                        "\"sub\":\"" + Config.get("CLIENT_ID") + "\"," +
                        "\"jti\":\""+jti+"\"," +
                        "\"exp\":"+jti+"}";

    JWSObject jwsObject = new JWSObject(new JWSHeader(new JWSAlgorithm(Config.get("JWS_ALGORITHM"))), new Payload(clientAssertion));
    ECPrivateKey privateKey = (ECPrivateKey)keyPair.getPrivate();
    jwsObject.sign(new ECDSASigner(privateKey));

    return  jwsObject.serialize();

  }

}
