package com.example.l09v10;

public class SessionModel {

    private int idSession;
    private String name;
    private String start;
    private int samples;
    private float tp;


    public SessionModel(int idSession, String name, String start, int samples, float tp) {
        this.idSession = idSession;
        this.name = name;
        this.start = start;
        this.samples = samples;
        this.tp = tp;
    }

    public SessionModel(String name, String start, int samples, float tp) {
        this.name = name;
        this.start = start;
        this.samples = samples;
        this.tp = tp;
    }

    public int getIdSession() {
        return idSession;
    }

    public void setIdSession(int idSession) {
        this.idSession = idSession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }

    public float getTp() {
        return tp;
    }

    public void setTp(float tp) {
        this.tp = tp;
    }

    @Override
    public String toString() {
        return "SessionModel{" +
                "idSession=" + idSession +
                ", name='" + name + '\'' +
                ", start='" + start + '\'' +
                ", samples=" + samples +
                ", tp=" + tp +
                '}';
    }
}
