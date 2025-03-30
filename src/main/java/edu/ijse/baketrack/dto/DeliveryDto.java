package edu.ijse.baketrack.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeliveryDto {
    private int delivery_id;
     private int vehicle_id;
     private LocalDate delivery_date;
     private String area;

    public DeliveryDto(int delivery_id, int vehicle_id, LocalDate delivery_date, String area) {
        this.delivery_id = delivery_id;
        this.vehicle_id = vehicle_id;
        this.delivery_date = delivery_date;
        this.area = area;
    }

    public DeliveryDto() {
    }

    public int getVehicleID(){
        return this.vehicle_id;
   }
   public LocalDate getDeliveryDate(){
        return this.delivery_date;
   }
   public String getDeliveryArea(){
        return this.area;
   }
   
   public void setVehicleID(int vehicle_id) {
    this.vehicle_id = vehicle_id;
   }

  public void setDeliveryDate(String delivery_date_String) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.delivery_date = LocalDate.parse(delivery_date_String, format);
  }

   public void setDeliveryArea(String area) {
    this.area = area;
   }
}
