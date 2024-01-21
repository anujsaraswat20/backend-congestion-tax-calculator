package test.controller;

import com.assignment.controller.CongestionTaxController;
import com.assignment.model.request.VehicleInfo;
import com.assignment.model.response.SendResponse;
import com.assignment.service.CongestionTaxCalculatorService;
import com.assignment.service.EnvironmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CongestionTaxControllerTest {
    private CongestionTaxCalculatorService congestionTaxCalculatorService = mock(CongestionTaxCalculatorService.class);
    private EnvironmentService environmentService = mock(EnvironmentService.class);
    private CongestionTaxController congestionTaxController;

    @BeforeEach
    public void beforeEach() {
        congestionTaxController = new CongestionTaxController();
        congestionTaxController.congestionTaxCalculatorService = congestionTaxCalculatorService;
        congestionTaxController.environmentService = environmentService;
    }

    @Test
    @DisplayName("Should be able to get congestionTaxFee")
    public void congestionTaxFee() throws Exception {

        when(environmentService.getAdminKey()).thenReturn("test-key");
        Set<String> vehicles = new HashSet<>();
        vehicles.add("test1");

        List<XMLGregorianCalendar> dates = new ArrayList<>();
        VehicleInfo vehicleInfo = new VehicleInfo("test1", dates);

        when(congestionTaxCalculatorService.getTaxStatement(vehicleInfo)).thenReturn(10);

        SendResponse actualResponse = congestionTaxController.congestionTaxFee(vehicleInfo);
        Assertions.assertEquals(actualResponse.getMessage(), "Total Congestion tax -> 10");
    }

    @Test
    @DisplayName("Should be able to listTollFreeVehicle")
    public void listTollFreeVehicle() throws Exception {

        when(environmentService.getAdminKey()).thenReturn("test-key");
        Set<String> vehicles = new HashSet<>();
        vehicles.add("test1");

        when(congestionTaxCalculatorService.listTollFreeVehicle()).thenReturn(vehicles);

        SendResponse actualResponse = congestionTaxController.listTollFreeVehicle("test-key");
        Assertions.assertEquals(actualResponse.getMessage(), "Returned successfully from listTollFreeVehicle() with response -> "+vehicles);
    }
}
