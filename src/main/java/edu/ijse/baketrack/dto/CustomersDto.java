package edu.ijse.baketrack.dto;

public class CustomersDto {
    private int id;
    private String name;
    private String contact;
    private String address;

    public CustomersDto() {
    }

    public CustomersDto(int id, String name, String contact, String address){
     this.id=id;
     this.name=name;
     this.contact=contact;
     this.address=address;
   }

    public CustomersDto(String name, String contact, String address) {
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    public int getCustomerID(){
     return this.id;
   }
    public String getName(){
         return this.name;
    }
    public String getContact(){
         return this.contact;
    }
    public String getAddress(){
         return this.address;
    }

    public void setName(String name){
         this.name=name;
    }
    public void setContact(String contact){
         this.contact=contact;
    }
    public void setAddress(String address){
         this.address=address;
    }
    public void setCustomerID(int id){
          this.id=id;
    }
    

}