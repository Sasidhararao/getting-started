package no.dnb.openbanking.gettingstarted;

import com.nimbusds.jose.crypto.bc.BouncyCastleProviderSingleton;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import java.io.StringReader;
import java.security.*;


public class PemToKeyPairConverter {

  public static KeyPair  readKeyPairFromPem() throws Exception {

    Security.addProvider(BouncyCastleProviderSingleton.getInstance());

    PEMParser pemParser = new PEMParser( new StringReader(Config.get("SIGNING_KEYS_PEM")));
    PEMKeyPair pemKeyPair = (PEMKeyPair)pemParser.readObject();

    JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
    KeyPair keyPair = converter.getKeyPair(pemKeyPair);

    pemParser.close();

    return keyPair;

  }

}
