package com.assignment.service;

import com.assignment.Utility.Utility;
import com.assignment.exception.CustomException;
import com.assignment.model.*;
import com.assignment.model.request.GeneralVehicle;
import com.assignment.model.request.VehicleInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CongestionTaxCalculatorService {

    @Autowired
    public EnvironmentService environmentService;
    private Map<String, Integer> tollFreeVehiclesMap = new HashMap<>();
    private static int tollFreeVehicleIndex = 0;

    @PostConstruct
    public void initData() {
        List tollFreeVehicles = environmentService.getTollfreeVehicles();
        tollFreeVehicles.stream().map(tollFreeVehicle -> {
            return tollFreeVehiclesMap.put(tollFreeVehicle.toString().toUpperCase(), ++tollFreeVehicleIndex);
        }).collect(Collectors.toList());
    }


    public int getTaxStatement(VehicleInfo vehicleInfo) throws CustomException {
        if(vehicleInfo.getDates().isEmpty()) {
            throw new CustomException("Entry date/time not found in request");
        }

        Vehicle vehicle = getVehicle(vehicleInfo.getVehicleType());
        XMLGregorianCalendar intervalStart = vehicleInfo.getDates().get(0);
        int totalFee = 0;

        for (int i = 0; i < vehicleInfo.getDates().size() ; i++) {
            XMLGregorianCalendar date = vehicleInfo.getDates().get(i);
            int nextFee = GetTollFee(date, vehicle);
            int tempFee = GetTollFee(intervalStart, vehicle);

            long minutes = 0;

            //confirm here is last and current dates are from same day
            if(date.getDay() == intervalStart.getDay()) {
                long diffInMillies = date.getMillisecond() - intervalStart.getMillisecond();
                minutes = diffInMillies/1000/60;
            }

            if (minutes > 0 && minutes <= 60)
            {
                if (totalFee > 0) totalFee -= tempFee;
                if (nextFee >= tempFee) tempFee = nextFee;
                totalFee += tempFee;
            }
            else
            {
                totalFee += nextFee;
            }
        }

        if (totalFee > 60) totalFee = 60;
        return totalFee;
    }

    private Vehicle getVehicle(String vehicleType) {
        Vehicle vehicle = null;
        switch (vehicleType) {
            case "Motorcycle":
                vehicle = new Motorcycle();
                break;
            case "Tractor":
                vehicle = new Tractor();
                break;
            case "Emergency":
                vehicle = new Emergency();
                break;
            case "Diplomat":
                vehicle = new Diplomat();
                break;
            case "Foreign":
                vehicle = new Diplomat();
                break;
            case "Military":
                vehicle = new Military();
                break;
            default://added default vehicle which is other than exempted
                vehicle = new GeneralVehicle(vehicleType);
                break;
        }
        return vehicle;
    }

    private boolean IsTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        String vehicleType = vehicle.getVehicleType();
        return environmentService.getTollfreeVehicles().contains(vehicleType);
    }

    public int GetTollFee(XMLGregorianCalendar date, Vehicle vehicle)
    {
        if (Utility.IsTollFreeDate(date) || IsTollFreeVehicle(vehicle)) return 0;

        int hour = date.getHour();
        int minute = date.getMinute();

        if (hour == environmentService.getFirstHour() && minute >= 0 && minute <= 29) return environmentService.getFirstHourFeeFor0To29Mins();
        else if (hour == environmentService.getFirstHour() && minute >= 30 && minute <= 59) return environmentService.getFirstHourFeeFor30To59Mins();
        else if (hour == environmentService.getSecondHour() && minute >= 0 && minute <= 59) return 18;
        else if (hour == 8 && minute >= 0 && minute <= 29) return 13;
        else if (hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59) return 8;
        else if (hour == 15 && minute >= 0 && minute <= 29) return 13;
        else if (hour == 15 && minute >= 0 || hour == 16 && minute <= 59) return 18;
        else if (hour == 17 && minute >= 0 && minute <= 59) return 13;
        else if (hour == 18 && minute >= 0 && minute <= 29) return 8;
        else return 0;
    }

    public Set addTollFreeVehicle(List<VehicleInfo> vehicleTypes) throws CustomException {
        if(vehicleTypes.isEmpty()) {
            throw new CustomException("No vehicles found in request");
        }

        vehicleTypes.stream().map(vehileType -> {
            return tollFreeVehiclesMap.put(vehileType.getVehicleType(), ++tollFreeVehicleIndex);
        }).collect(Collectors.toList());
        return tollFreeVehiclesMap.keySet();
    }

    public Set listTollFreeVehicle() {
        return tollFreeVehiclesMap.keySet();
    }
}
