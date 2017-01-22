package com.tcfj.donors.model;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by andrew.larsen on 1/11/2017.
 */
@Entity
@Table(name = "Donors")
public class Donors implements Serializable {

    protected Donors(){}

    public Donors(String donorName, String dateStarted) {
        this.donorName = donorName;
        this.dateStarted = dateStarted;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "donor_id")
    private Integer donorId;
    @Column(name = "donor_name")
    private String donorName;
    @Column(name = "date_started")
    private String dateStarted;

    public Integer getDonorId() {
        return donorId;
    }

    public void setDonorId(Integer donorId) {
        this.donorId = donorId;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    @Override
    public String toString() {
        return "Donors{" +
                "donorId=" + donorId +
                ", donorName='" + donorName + '\'' +
                ", dateStarted='" + dateStarted + '\'' +
                '}';
    }
}
