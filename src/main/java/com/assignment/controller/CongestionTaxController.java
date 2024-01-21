package com.assignment.controller;

import com.assignment.exception.CustomException;
import com.assignment.model.request.VehicleInfo;
import com.assignment.model.response.SendResponse;
import com.assignment.service.CongestionTaxCalculatorService;
import com.assignment.service.EnvironmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class CongestionTaxController {
    @Autowired
    public CongestionTaxCalculatorService congestionTaxCalculatorService;
    @Autowired
    public EnvironmentService environmentService;

    @PostMapping(value = "/congestionTaxFee", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SendResponse congestionTaxFee(@RequestBody VehicleInfo vehicleInfo) {
        log.info("congestionTaxFee() -> Start");
        Integer taxFee = null;
        SendResponse response = new SendResponse();
        try {
            taxFee = congestionTaxCalculatorService.getTaxStatement(vehicleInfo);
            response.setMessage("Total Congestion tax -> "+taxFee);
            log.info("Returned successfully from congestionTaxFee() with response: {}", taxFee);
        } catch (CustomException e) {
            log.error(e.getLocalizedMessage());
            response.setError("Error occurred while calculating Congestion tax. Error message -> "+e.getLocalizedMessage());
        }
        return response;
    }

    @GetMapping(value = "/listTollFreeVehicle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SendResponse listTollFreeVehicle(@RequestHeader(value = "x-admin-key", required = false) String adminKeyInput) {
        log.info("listTollFreeVehicle() -> Start");
        SendResponse response = new SendResponse();
        if(!StringUtils.equalsIgnoreCase(environmentService.getAdminKey(), adminKeyInput)) {
            log.error("Not authorised for this operation");
            response.setError("Error occurred while getting list of TollFree Vehicle. Error message -> "+"Not authorised for this operation");
        }

        Set tollFreeVehicles = congestionTaxCalculatorService.listTollFreeVehicle();
        response.setMessage("Returned successfully from listTollFreeVehicle() with response -> "+ tollFreeVehicles);
        log.info("Returned successfully from listTollFreeVehicle() with response: {}", tollFreeVehicles);
        return response;
    }

    @PostMapping(value = "/addTollFreeVehicle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set addTollFreeVehicle(@RequestHeader(value = "x-admin-key", required = false) String adminKeyInput,
                                  @RequestBody List<VehicleInfo> vehicleTypes) throws CustomException {
        if(!StringUtils.equalsIgnoreCase(environmentService.getAdminKey(), adminKeyInput)) {
            log.error("Not authorised for this operation");
            throw new CustomException("Not authorised for this operation");
        }
        log.info("addTollFreeVehicle() -> Start");
        Set tollFreeVehicles = congestionTaxCalculatorService.addTollFreeVehicle(vehicleTypes);
        log.info("Successfully added vehicles "+vehicleTypes);
        return tollFreeVehicles;
    }

    //TODO add controller for delete vehicles
}
