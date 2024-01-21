package com.assignment.model.request;

import com.assignment.model.Vehicle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralVehicle implements Vehicle {
    private String vehicleType;

    public GeneralVehicle(String vehicleType) {
    this.vehicleType = vehicleType;
    }

    @Override
    public String getVehicleType() {
        return vehicleType;
    }
}
