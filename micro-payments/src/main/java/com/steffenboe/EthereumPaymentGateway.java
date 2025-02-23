package com.steffenboe;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.UUID;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

public class EthereumPaymentGateway {

    private Web3j web3j = Web3j.build(new HttpService());
    private AppRepository appRepository = new AppRepository();
    private UserRepository userRepository = new UserRepository();

    public void charge(UUID appId, String userId, Double amount)
            throws Exception {
        App app = appRepository.findById(appId).orElseThrow();
        AppUser appUser = userRepository.findById(appId, userId).orElseThrow();
        String to = app.address();

        Credentials credentials = appUser.credentials();
        Transfer.sendFunds(
                web3j, credentials,
                to, 
                BigDecimal.valueOf(amount), 
                Convert.Unit.ETHER 
        ).send();
    }

    public UUID registerApp(String name, String address) {
        UUID appId = UUID.randomUUID();
        appRepository.save(new App(appId, name, address));
        return appId;
    }

    public void registerUser(UUID appId, String userId)
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {

        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256k1");
        keyPairGenerator.initialize(ecSpec, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        ECKeyPair ecKeyPair = ECKeyPair.create(keyPair);
        String privateKeyHex = Numeric.toHexStringNoPrefix(ecKeyPair.getPrivateKey());
        Credentials credentials = Credentials.create(privateKeyHex);
        AppUser appUser = new AppUser(appId, userId, credentials);

        userRepository.save(appUser);
    }

    public String getAddress(UUID appId, String userId) {
        return userRepository.findById(appId, userId).map(user -> user.credentials().getAddress()).orElseThrow();

    }

    public BigDecimal available(UUID appId, String userId) {
        AppUser appUser = userRepository.findById(appId, userId).orElseThrow();
        String userAddress = appUser.credentials().getAddress();

        try {
            EthGetBalance balance = web3j.ethGetBalance(userAddress, DefaultBlockParameterName.LATEST).send();
            BigDecimal balanceInWei = new BigDecimal(balance.getBalance());
            return Convert.fromWei(balanceInWei, Convert.Unit.ETHER);
        } catch (Exception e) {
            System.err.println("Error retrieving balance: " + e.getMessage());
            return BigDecimal.valueOf(0.0);
        }
    }

}
