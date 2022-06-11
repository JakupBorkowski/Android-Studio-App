package com.example.l09v10;

public class SessionHasSensorModel {
    private int idSession;
    private int idSensor;

    public SessionHasSensorModel(int idSession, int idSensor) {
        this.idSession = idSession;
        this.idSensor = idSensor;
    }

    public int getIdSession() {
        return idSession;
    }

    public void setIdSession(int idSession) {
        this.idSession = idSession;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    @Override
    public String toString() {
        return "SessionHasSensorModel{" +
                "idSession=" + idSession +
                ", idSensor=" + idSensor +
                '}';
    }
}
