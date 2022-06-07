package com.example.l09v10;

public class SensorModel {

    private int idSensor;
    private int idDevice;
    private int idSensor_Type;
    private String name;

    public SensorModel(int idDevice, int idSensor_Type, String name) {
        this.idDevice = idDevice;
        this.idSensor_Type = idSensor_Type;
        this.name = name;
    }

    public SensorModel(int idSensor, int idDevice, int idSensor_Type, String name) {
        this.idSensor = idSensor;
        this.idDevice = idDevice;
        this.idSensor_Type = idSensor_Type;
        this.name = name;
    }


    @Override
    public String toString() {
        return "SensorModel{" +
                "idSensor=" + idSensor +
                ", idDevice=" + idDevice +
                ", idSensor_Type=" + idSensor_Type +
                ", name='" + name + '\'' +
                '}';
    }

    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

    public int getIdSensor_Type() {
        return idSensor_Type;
    }

    public void setIdSensor_Type(int idSensor_Type) {
        this.idSensor_Type = idSensor_Type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
