package com.example.l09v10;

public class SampleModel {
    private int idSample;
    private int idSensor;
    private float value_1;
    private float value_2;
    private float value_3;
    private String timestamp;

    public SampleModel(int idSensor, float value_1, float value_2, float value_3, String timestamp) {
        this.idSensor = idSensor;
        this.value_1 = value_1;
        this.value_2 = value_2;
        this.value_3 = value_3;
        this.timestamp = timestamp;
    }

    public SampleModel(int idSample, int idSensor, float value_1, float value_2, float value_3, String timestamp) {
        this.idSample = idSample;
        this.idSensor = idSensor;
        this.value_1 = value_1;
        this.value_2 = value_2;
        this.value_3 = value_3;
        this.timestamp = timestamp;
    }


    public int getIdSample() {
        return idSample;
    }

    public void setIdSample(int idSample) {
        this.idSample = idSample;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public float getValue_1() {
        return value_1;
    }

    public void setValue_1(float value_1) {
        this.value_1 = value_1;
    }

    public float getValue_2() {
        return value_2;
    }

    public void setValue_2(float value_2) {
        this.value_2 = value_2;
    }

    public float getValue_3() {
        return value_3;
    }

    public void setValue_3(float value_3) {
        this.value_3 = value_3;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

