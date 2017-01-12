package com.tcfj.donors.controller;

import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.tcfj.donors.model.Donors;
import com.tcfj.donors.repository.DonorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by andrew.larsen on 1/11/2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(DonorController.class)
public class DonorControllerTest {
    //this is used to send fake http calls to the controller method
    @Autowired
    private MockMvc mvc;
    //the @MockBean tag will auto mock the controller dependencies
    @MockBean
    private DonorRepository repository;

    private String url = "/tcfj/v1/donors";
    private List<Donors> donors;
    private Iterable<Donors> iterable;
    private String donorName = "Seward Co-Op Friendship Store";
    private int id = 1;
    private String dateStarted = LocalDate.now().toString();
    @Before
    public void setup(){
        donors = new ArrayList<>();
        Donors donor = new Donors(donorName, dateStarted);
        donor.setDonar_id(id);
        donors.add(donor);
        Donors[] array = {};
        //this feels a bit hacky
        iterable = new ArrayIterator<>(donors.toArray(array));
    }

    @Test
    public void testUrlNotFound() throws Exception {
        this.mvc.perform(get("/notfound")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testShiftsIsOk() throws Exception {
        given(repository.findAll()).willReturn(iterable);
        this.mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    public void testShiftDonarNameReturned() throws Exception {

        given(repository.findAll()).willReturn(iterable);
        this.mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].donar_name", is(donorName)));

    }
    @Test
    public void testShiftDateStartedReturned() throws Exception {

        given(repository.findAll()).willReturn(iterable);
        this.mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].date_started", is(dateStarted)));

    }
    @Test
    public void testShiftDonarIdReturned() throws Exception {

        given(repository.findAll()).willReturn(iterable);
        this.mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].donar_id", is(id)));

    }
}