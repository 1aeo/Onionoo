package org.torproject.metrics.onionoo.docs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DetailsDocumentTest {

  private static ObjectMapper objectMapper = new ObjectMapper();

  private DetailsDocument createDetailsDocumentRelay() {
    return new DetailsDocument();
  }

  @Test()
  public void testDocumentExists() throws JsonProcessingException {
    DetailsDocument relay = this.createDetailsDocumentRelay();
    relay.setFingerprint("BE45FFE2F55E29DA327346E9D44A5203086E25B0");
    relay.setRunning(true);
    relay.setOverloadGeneralTimestamp(1628168400000L);
    assertEquals(
        "BE45FFE2F55E29DA327346E9D44A5203086E25B0",
        relay.getFingerprint());
    assertEquals(
        "{\"fingerprint\":\"BE45FFE2F55E29DA327346E9D44A5203086E25B0\","
        + "\"running\":true,\"overload_general_timestamp\":1628168400000}",
        objectMapper.writeValueAsString(relay));
  }

  @Test()
  public void testOverloadTimestampDoesNotExists()
        throws JsonProcessingException {
    DetailsDocument relay = this.createDetailsDocumentRelay();
    relay.setFingerprint("BE45FFE2F55E29DA327346E9D44A5203086E25B0");
    relay.setRunning(true);
    assertEquals(
        "BE45FFE2F55E29DA327346E9D44A5203086E25B0",
        relay.getFingerprint());
    assertEquals(
        "{\"fingerprint\":\"BE45FFE2F55E29DA327346E9D44A5203086E25B0\","
        + "\"running\":true}",
        objectMapper.writeValueAsString(relay));
  }

  @Test()
  public void testFamilyIdsInJson() throws JsonProcessingException {
    DetailsDocument relay = this.createDetailsDocumentRelay();
    relay.setFingerprint("BE45FFE2F55E29DA327346E9D44A5203086E25B0");
    relay.setRunning(true);
    List<String> familyIds = Arrays.asList(
        "Or+tKhdy84NCOAz/Qqk6KPxAdK4Thw/meE58qv1jjY8",
        "wweKJrJxUDs1EdtFFHCDtvVgTKftOC/crUl1mYJv830");
    relay.setFamilyIds(familyIds);
    String json = objectMapper.writeValueAsString(relay);
    assertTrue("JSON should contain family_ids field.",
        json.contains("\"family_ids\""));
    assertTrue("JSON should contain the first family id value.",
        json.contains("Or+tKhdy84NCOAz/Qqk6KPxAdK4Thw/meE58qv1jjY8"));
    assertTrue("JSON should contain the second family id value.",
        json.contains("wweKJrJxUDs1EdtFFHCDtvVgTKftOC/crUl1mYJv830"));
    assertEquals(familyIds, relay.getFamilyIds());
  }

  @Test()
  public void testFamilyCertInJson() throws JsonProcessingException {
    DetailsDocument relay = this.createDetailsDocumentRelay();
    relay.setFingerprint("BE45FFE2F55E29DA327346E9D44A5203086E25B0");
    relay.setRunning(true);
    String certBlock = "-----BEGIN ED25519 CERT-----\n"
        + "AQoABkGXZm9vYmF6YmF6YmF6\n"
        + "-----END ED25519 CERT-----";
    relay.setFamilyCert(certBlock);
    String json = objectMapper.writeValueAsString(relay);
    assertTrue("JSON should contain family_cert field.",
        json.contains("\"family_cert\""));
    assertTrue("JSON should contain the cert content.",
        json.contains("AQoABkGXZm9vYmF6YmF6YmF6"));
  }

  @Test()
  public void testFamilyIdsAndCertInJson() throws JsonProcessingException {
    DetailsDocument relay = this.createDetailsDocumentRelay();
    relay.setFingerprint("BE45FFE2F55E29DA327346E9D44A5203086E25B0");
    relay.setRunning(true);
    List<String> familyIds = Arrays.asList(
        "wweKJrJxUDs1EdtFFHCDtvVgTKftOC/crUl1mYJv830");
    relay.setFamilyIds(familyIds);
    String certBlock = "-----BEGIN ED25519 CERT-----\n"
        + "AQoABkGXZm9vYmF6YmF6YmF6\n"
        + "-----END ED25519 CERT-----";
    relay.setFamilyCert(certBlock);
    String json = objectMapper.writeValueAsString(relay);
    assertTrue("JSON should contain both family_ids and family_cert.",
        json.contains("\"family_ids\"") && json.contains("\"family_cert\""));
  }

  @Test()
  public void testFamilyIdsOmittedWhenEmpty() throws JsonProcessingException {
    DetailsDocument relay = this.createDetailsDocumentRelay();
    relay.setFingerprint("BE45FFE2F55E29DA327346E9D44A5203086E25B0");
    relay.setRunning(true);
    relay.setFamilyIds(Arrays.asList());
    String json = objectMapper.writeValueAsString(relay);
    assertFalse("JSON should not contain family_ids when set to empty list.",
        json.contains("family_ids"));
  }

  @Test()
  public void testFamilyIdsOmittedWhenNull() throws JsonProcessingException {
    DetailsDocument relay = this.createDetailsDocumentRelay();
    relay.setFingerprint("BE45FFE2F55E29DA327346E9D44A5203086E25B0");
    relay.setRunning(true);
    relay.setFamilyIds(null);
    relay.setFamilyCert(null);
    String json = objectMapper.writeValueAsString(relay);
    assertFalse("JSON should not contain family_ids when null.",
        json.contains("family_ids"));
    assertFalse("JSON should not contain family_cert when null.",
        json.contains("family_cert"));
  }
}
