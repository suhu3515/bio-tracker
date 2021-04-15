package com.example.biotracker;

public class Farm
{
    int farm_id;
    String fish_type, fish_count, tank_volume, start_date, est_time;

    public Farm(int farm_id, String fish_type, String fish_count, String tank_volume, String start_date, String est_time) {
        this.farm_id = farm_id;
        this.fish_type = fish_type;
        this.fish_count = fish_count;
        this.tank_volume = tank_volume;
        this.start_date = start_date;
        this.est_time = est_time;
    }

    public int getFarm_id() {
        return farm_id;
    }

    public String getFish_type() {
        return fish_type;
    }

    public String getFish_count() {
        return fish_count;
    }

    public String getTank_volume() {
        return tank_volume;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEst_time() {
        return est_time;
    }
}
