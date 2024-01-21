package com.assignment.model;

public enum VehicleTypes {

    EMERGENCY("Emergency"),
    BUSSES("Busses"),
    DIPLOMAT("Diplomat"),
    MOTORCYCLE("Motorcycle"),
    MILITARY("Military"),
    FOREIGN("Foreign");

    private final String name;

    private VehicleTypes(String name) {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
