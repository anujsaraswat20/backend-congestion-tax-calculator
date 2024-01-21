package test.service;

import com.assignment.exception.CustomException;
import com.assignment.model.request.VehicleInfo;
import com.assignment.service.CongestionTaxCalculatorService;
import com.assignment.service.EnvironmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CongestionTaxCalculatorServiceTest {
    private EnvironmentService environmentService = mock(EnvironmentService.class);
    private CongestionTaxCalculatorService congestionTaxCalculatorService;

    @BeforeEach
    public void beforeEach(){
        congestionTaxCalculatorService = new CongestionTaxCalculatorService();
        congestionTaxCalculatorService.environmentService = environmentService;
    }

    @Test
    @DisplayName("Should be able to get tax fee for tax free vehicles")
    public void testGetTaxStatementForTollFreeVehicles() throws CustomException, DatatypeConfigurationException {
        when(environmentService.getFirstHour()).thenReturn(6);
        when(environmentService.getSecondHour()).thenReturn(7);
        when(environmentService.getFirstHourFeeFor0To29Mins()).thenReturn(8);
        when(environmentService.getFirstHourFeeFor30To59Mins()).thenReturn(13);
        List l = new ArrayList<>();
        l.add("EMERGENCY");
        when(environmentService.getTollfreeVehicles()).thenReturn(l);

        List<XMLGregorianCalendar> dates = new ArrayList<>();

        GregorianCalendar cal1 = new GregorianCalendar();
        XMLGregorianCalendar date1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);

        dates.add(date1);

        VehicleInfo vehicleInfo = new VehicleInfo("EMERGENCY", dates);

        int actual = congestionTaxCalculatorService.getTaxStatement(vehicleInfo);
        Assertions.assertEquals(actual, 0);
    }

    @Test
    @DisplayName("Should be able to get tax fee for vehicles")
    public void testGetTaxStatement() throws CustomException, DatatypeConfigurationException {
        when(environmentService.getFirstHour()).thenReturn(6);
        when(environmentService.getSecondHour()).thenReturn(7);
        when(environmentService.getFirstHourFeeFor0To29Mins()).thenReturn(8);
        when(environmentService.getFirstHourFeeFor30To59Mins()).thenReturn(13);

        List<XMLGregorianCalendar> dates = new ArrayList<>();

        String dateTimeString = "2013-01-14T08:00:00";

        GregorianCalendar cal1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString).toGregorianCalendar();

        XMLGregorianCalendar date1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);

        dates.add(date1);

        VehicleInfo vehicleInfo = new VehicleInfo("CAR", dates);

        int actual = congestionTaxCalculatorService.getTaxStatement(vehicleInfo);
        Assertions.assertEquals(actual, 13);
    }
}
