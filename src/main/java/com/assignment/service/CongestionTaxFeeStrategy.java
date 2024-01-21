package com.assignment.service;

import com.assignment.exception.CustomException;
import com.assignment.model.request.VehicleInfo;

/**
 * This class exposes a basic contract to calculate Congestion Tax as per location/city
 */
public interface CongestionTaxFeeStrategy {
    int getTaxStatement(VehicleInfo vehicleInfo) throws CustomException;
}
