package com.tcfj.donors.integration;

import com.tcfj.donors.model.Donors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by andrew.larsen on 1/11/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext
public class GetAllDonorsIntegrationTests {

    @Autowired
    TestRestTemplate testRestTemplate;
    private String url = "/tcfj/v1/donors";
    private String donorName = "Seward Co-Op Friendship Store";
    private int id = 1;
    private String dateStarted = LocalDate.now().toString();
    @Test
    public void test200Ok(){
        ResponseEntity<String> resp = this.testRestTemplate.getForEntity(url,String.class);
        assertThat(resp.getStatusCode(), is (HttpStatus.OK));
    }
    @Test
    public void testShiftsReturnsCorrentNumberOfDonors(){
        ResponseEntity<List<Donors>> resp = this.testRestTemplate.exchange(url, HttpMethod.GET,null, new ParameterizedTypeReference< List <Donors>>()
        {
        });
        assertThat(resp.getBody().size(), is (1));
    }
    @Test
    public void testShiftsReturnsCorrenValueOfDonors(){
        ResponseEntity<List<Donors>> resp = this.testRestTemplate.exchange(url, HttpMethod.GET,null, new ParameterizedTypeReference< List <Donors>>()
        {
        });
        assertThat(resp.getBody().get(0).getDonar_id(), is (1));
        assertThat(resp.getBody().get(0).getDonar_name(), is (donorName));
        assertThat(resp.getBody().get(0).getDate_started(), is (dateStarted));

    }
}
