package com.example.l09v10;

public class GyroscopeModel {

    private int idSample;
    private int idSensor;
    private float x;
    private float y;
    private float z;
    private String timestamp;

    public GyroscopeModel(int idSensor, float x, float y, float z, String timestamp) {
        //this.idSample = idSample;
        this.idSensor = idSensor;
        this.x = x;
        this.y = y;
        this.z = z;
        this.timestamp = timestamp;
    }
    public GyroscopeModel(int idSample, int idSensor, float x, float y, float z, String timestamp) {
        this.idSample = idSample;
        this.idSensor = idSensor;
        this.x = x;
        this.y = y;
        this.z = z;
        this.timestamp = timestamp;
    }

    public int getIdSample() {
        return idSample;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setIdSample(int idSample) {
        this.idSample = idSample;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "GyroscopeModel{" +
                "idSample=" + idSample +
                ", idSensor=" + idSensor +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
