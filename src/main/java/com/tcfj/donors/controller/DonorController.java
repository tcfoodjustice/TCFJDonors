package com.tcfj.donors.controller;

import com.tcfj.donors.model.Donors;
import com.tcfj.donors.repository.DonorRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew.larsen on 1/11/2017.
 */
@RestController
@RequestMapping(value = "tcfj/v1")
public class DonorController {

    private DonorRepository repository;

    public DonorController(DonorRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/donors")
    public List<Donors> getAllDonors(){
        List<Donors> response = new ArrayList<>();
        //this calls an returns the response from the getAllShifts method
        Iterable<Donors> donors =  repository.findAll();
        donors.forEach(response::add);
        //return response
        return response;
    }
}
