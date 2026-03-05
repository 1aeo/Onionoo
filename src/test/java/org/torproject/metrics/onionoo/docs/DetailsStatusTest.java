package org.torproject.metrics.onionoo.docs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DetailsStatusTest {

  private static ObjectMapper objectMapper = new ObjectMapper();

  @Test()
  public void testDetailStatus() throws JsonProcessingException {
    DetailsStatus detailsStatus = new DetailsStatus();
    detailsStatus.setBandwidthRate(640000);
    detailsStatus.setOverloadGeneralTimestamp(1628168400000L);
    assertEquals(Long.valueOf(640000),
        Long.valueOf(detailsStatus.getBandwidthRate()));
    assertEquals(
        "{\"bandwidth_rate\":640000,"
        + "\"overload_general_timestamp\":1628168400000,"
        + "\"is_relay\":false,\"running\":false,"
        + "\"first_seen_millis\":0,\"last_seen_millis\":0,"
        + "\"or_port\":0,\"dir_port\":0,"
        + "\"consensus_weight\":0,\"last_changed_or_address_or_port\":0}",
        objectMapper.writeValueAsString(detailsStatus));
  }

  @Test()
  public void testFamilyIdsInDetailStatus() throws JsonProcessingException {
    DetailsStatus detailsStatus = new DetailsStatus();
    List<String> familyIds = Arrays.asList(
        "Or+tKhdy84NCOAz/Qqk6KPxAdK4Thw/meE58qv1jjY8",
        "wweKJrJxUDs1EdtFFHCDtvVgTKftOC/crUl1mYJv830");
    detailsStatus.setFamilyIds(familyIds);
    assertEquals(familyIds, detailsStatus.getFamilyIds());
    String json = objectMapper.writeValueAsString(detailsStatus);
    assertTrue("JSON should contain family_ids field.",
        json.contains("\"family_ids\""));
    assertTrue("JSON should contain the family id values.",
        json.contains("Or+tKhdy84NCOAz/Qqk6KPxAdK4Thw/meE58qv1jjY8")
        && json.contains("wweKJrJxUDs1EdtFFHCDtvVgTKftOC/crUl1mYJv830"));
  }

  @Test()
  public void testFamilyCertInDetailStatus() throws JsonProcessingException {
    DetailsStatus detailsStatus = new DetailsStatus();
    String certBlock = "-----BEGIN ED25519 CERT-----\n"
        + "AQoABkGXZm9vYmF6YmF6YmF6\n"
        + "-----END ED25519 CERT-----";
    detailsStatus.setFamilyCert(certBlock);
    assertEquals(certBlock, detailsStatus.getFamilyCert());
    String json = objectMapper.writeValueAsString(detailsStatus);
    assertTrue("JSON should contain family_cert field.",
        json.contains("\"family_cert\""));
  }

  @Test()
  public void testFamilyIdsNullifiedWhenEmpty() {
    DetailsStatus detailsStatus = new DetailsStatus();
    detailsStatus.setFamilyIds(Arrays.asList());
    assertNull("Empty family ids list should be stored as null.",
        detailsStatus.getFamilyIds());
  }
}
