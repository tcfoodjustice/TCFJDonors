package com.tcfj.donors.model;

import com.tcfj.donors.repository.DonorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by andrew.larsen on 1/11/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext
public class DonorsTest {

    @Autowired
    private DonorRepository repository;
    private int id = 1;
    private String donorName = "Seward Co-Op Friendship Store";
    private String dateStarted = LocalDate.now().toString();
    private String newDonorName = "Seward Co-Op Friendship Store NEW";
    private String newDateStarted = LocalDate.now().plusDays(1).toString();
    private Donors donor;

    @Before
    public void setup(){
        donor = new Donors(newDonorName, newDateStarted);
    }
    @Test
    public void testDonorsEntitySetup(){
        Donors donors = repository.findOne(id);
        assertThat(donors.getDonorId(),is(id));
        assertThat(donors.getDonorName(),is(donorName));
        assertThat(donors.getDateStarted(), is(dateStarted));
    }

    //uncomment this when writing insert
//    @Test
//    public void testAutoIncrementTag(){
//        repository.save(donor);
//        //this should be number 2
//        Donors newDonor = repository.findOne(id +1);
//        assertThat(newDonor.getDate_started(), is(newDateStarted));
//        assertThat(newDonor.getDonar_name(), is(newDonorName));
//
//    }


}