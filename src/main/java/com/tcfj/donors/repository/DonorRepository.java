package com.tcfj.donors.repository;

import com.tcfj.donors.model.Donors;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by andrew.larsen on 1/11/2017.
 */
public interface DonorRepository extends CrudRepository<Donors, Integer> {

}
