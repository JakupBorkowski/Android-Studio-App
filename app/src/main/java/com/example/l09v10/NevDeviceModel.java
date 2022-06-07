package com.example.l09v10;

import java.sql.Time;
import java.sql.Timestamp;

public class NevDeviceModel {

    private int idDevice;
    private String name;
    private String description;
    private String additionDate;
    private String lastActualization;

    public NevDeviceModel(String name, String description, String additionDate, String lastActualization) {
        this.name = name;
        this.description = description;
        this.additionDate = additionDate;
        this.lastActualization=lastActualization;
    }

    public NevDeviceModel(int idDevice, String name, String description, String additionDate, String lastActualization) {
        this.idDevice = idDevice;
        this.name = name;
        this.description = description;
        this.additionDate = additionDate;
        this.lastActualization = lastActualization;
    }


    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditionDate() {
        return additionDate;
    }

    public void setAdditionDate(String additionDate) {
        this.additionDate = additionDate;
    }

    public String getLastActualization() {
        return lastActualization;
    }

    public void setLastActualization(String lastActualization) {
        this.lastActualization = lastActualization;
    }

    @Override
    public String toString() {
        return "NevDeviceModel{" +
                "idDevice=" + idDevice +
                ", name=" + name +
                ", description='" + description + '\'' +
                ", additionDate='" + additionDate + '\'' +
                ", lastActualization='" + lastActualization + '\'' +
                '}';
    }
}
