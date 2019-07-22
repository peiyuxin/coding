package org.pyx.pyx.javax.validation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author pyx
 * @date 2019/2/20
 */
public class Car {

    @NotNull
    private String manufacturer;

    //@NotNull
    //@Size(min = 2, max = 14)
    //@CheckCase(CaseMode.UPPER)
    @ValidLicensePlate
    private String licensePlate;

    @Min(2)
    private int seatCount;

    public Car(String manufacturer, String licencePlate, int seatCount) {
        this.manufacturer = manufacturer;
        this.licensePlate = licencePlate;
        this.seatCount = seatCount;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }
}
