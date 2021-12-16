package com.kk.vultrmanage.entity;

import java.util.Objects;

public class Server  {
    private String id;
    private String main_ip;
    private String region;
    private String status;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return id.equals(server.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Server{" +
                "id='" + id + '\'' +
                ", main_ip='" + main_ip + '\'' +
                ", region='" + region + '\'' +
                ", plan='" + status + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain_ip() {
        return main_ip;
    }

    public void setMain_ip(String main_ip) {
        this.main_ip = main_ip;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Server(String id) {
        this.id = id;
    }


    public Server(String id, String main_ip) {
        this.id = id;
        this.main_ip = main_ip;
    }

    public Server() {
    }

    public Server(String id, String main_ip, String region, String status) {
        this.id = id;
        this.main_ip = main_ip;
        this.region = region;
        this.status = status;
    }
}
