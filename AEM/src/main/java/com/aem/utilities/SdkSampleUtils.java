package com.aem.utilities;

import org.qas.api.ClientConfiguration;
import org.qas.api.http.Protocol;
import org.qas.qtest.api.auth.BasicQTestCredentials;
import org.qas.qtest.api.auth.QTestCredentials;
import org.qas.qtest.api.services.authenticate.AuthenticateService;
import org.qas.qtest.api.services.authenticate.AuthenticateServiceClient;
import org.qas.qtest.api.services.authenticate.model.OAuthAuthenticateRequest;
import org.qas.qtest.api.services.authenticate.model.OAuthTokenResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;

/**
 * @author trongle
 * @version $Id 5/17/2017 7:59 PM
 */
public class SdkSampleUtils {

  private static final String QTEST_CONFIG_FILE = "qtest.properties";
  private static final String QTEST_ENDPOINT = "service.endpoint";

  private static final String QTEST_TOKEN = "qTest.token";
  private static final String QTEST_PROJECTID = "qTest.projectId";
  private static final String QTEST_USERNAME = "qTest.username";
  private static final String QTEST_PASSWORD = "qTest.password";
  private static final Properties properties = new Properties();
  private static String accessToken = null;

  static {
    try {
      loadProperties();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getUrl() {
    return (String) get(QTEST_ENDPOINT);
  }

  public static String getUsername() {
    return (String) get(QTEST_USERNAME);
  }

  public static String getPassword() {
    return (String) get(QTEST_PASSWORD);
  }

  public static String getProjectId() {
    return (String) get(QTEST_PROJECTID);
  }

  public static Object get(String key) {
    return properties.getProperty(key);
  }

  private static void loadProperties() throws IOException {
    if (properties.size() > 0) {
      return;
    }
    InputStream in = ProjectServiceDemo.class.getClassLoader().getResourceAsStream(QTEST_CONFIG_FILE);
    if (in == null) {
      throw new FileNotFoundException(QTEST_CONFIG_FILE + " not found on classpath.");
    }

    // load the property information.
    properties.load(in);
  }

  /**
   * Constructs the {@link ClientConfiguration client configuration} object from
   * the content of qTest.properties file
   */
  public static ClientConfiguration createConfiguration() throws IOException {
    // constructs the client configuration instance.
    ClientConfiguration clientConfiguration = new ClientConfiguration();
    for (Map.Entry<Object, Object> entry : properties.entrySet()) {
      clientConfiguration.setProperty((String) entry.getKey(), entry.getValue());
    }

    clientConfiguration.withProperty(QTEST_ENDPOINT, properties.getProperty(QTEST_ENDPOINT, "demo.qtestnet.com"))
      .withProperty(QTEST_TOKEN, properties.getProperty(QTEST_TOKEN));
    return clientConfiguration;
  }

  public static QTestCredentials getCredentials() {
    ClientConfiguration configuration = null;
    try {
      configuration = createConfiguration();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new BasicQTestCredentials(getToken(configuration, getUsername(), getPassword()));
  }

  /**
   * Get token
   *
   * @param configuration
   * @param username
   * @param password
   * @return
   */
  public static String getToken(ClientConfiguration configuration, String username, String password) {
    if (null != accessToken) {
      return accessToken;
    }
    AuthenticateService authenticateService = new AuthenticateServiceClient(configuration);
    String token = new String(Base64.getEncoder().encode(("qtest-sdk-java" + ":").getBytes()));
    OAuthTokenResponse response = authenticateService.authenticate(new OAuthAuthenticateRequest.PasswordAuthenticateRequest(token, username, password));
    accessToken = response.getTokenType() + " " + response.getAccessToken();
    return accessToken;
  }
}
