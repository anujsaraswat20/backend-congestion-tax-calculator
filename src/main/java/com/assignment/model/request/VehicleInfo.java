package com.assignment.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@Getter
@Setter
public class VehicleInfo {
    private String vehicleType;
    private List<XMLGregorianCalendar> dates;

    public VehicleInfo(String vehicleType, List<XMLGregorianCalendar> dates) {
        this.vehicleType = vehicleType;
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "VehicleInfo{" +
                "vehicleType='" + vehicleType + '\'' +
                '}';
    }
}
