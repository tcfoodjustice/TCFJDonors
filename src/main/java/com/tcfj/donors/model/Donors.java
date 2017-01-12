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


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer donar_id;
    private String donar_name;
    private String date_started;

    public Donors(String donar_name, String date_started) {
        this.donar_name = donar_name;
        this.date_started = date_started;
    }

    public Integer getDonar_id() {
        return donar_id;
    }

    public void setDonar_id(Integer donar_id) {
        this.donar_id = donar_id;
    }

    public String getDonar_name() {
        return donar_name;
    }

    public void setDonar_name(String donar_name) {
        this.donar_name = donar_name;
    }

    public String getDate_started() {
        return date_started;
    }

    public void setDate_started(String date_started) {
        this.date_started = date_started;
    }

    @Override
    public String toString() {
        return "Donors{" +
                "donar_id=" + donar_id +
                ", donar_name='" + donar_name + '\'' +
                ", date_started='" + date_started + '\'' +
                '}';
    }
}
