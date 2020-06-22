package com.example.stichitv2;

public class
Orders {
    private  String ID,tailorname,orderdate,ordersID,image,tailorlocation,dresstype,tailorID;

    public String getOrdersID() {
        return ordersID;
    }

    public void setOrdersID(String ordersID) {
        this.ordersID = ordersID;
    }

    public void setTailorID(String tailorID) {
        this.tailorID = tailorID;
    }

    public String getTailorID() {
        return tailorID;
    }

    public void setDresstype(String dresstype) {
        this.dresstype = dresstype;
    }

    public Orders(String ID, String tailorname, String orderdate, String ordersID, String image, String tailorlocation, String dresstype,String tailorID) {
        this.ID = ID;
        this.tailorname = tailorname;
        this.orderdate = orderdate;
        this.ordersID = ordersID;
        this.image = image;
        this.tailorlocation = tailorlocation;
        this.dresstype= dresstype;
        this.tailorID= tailorID;
    }

    public Orders(String ID, String tailorname, String orderdate, String ordersID, String image, String dresstype) {
        this.ID = ID;
        this.tailorname = tailorname;
        this.orderdate = orderdate;
        this.ordersID = ordersID;
        this.image = image;
        this.dresstype = dresstype;
    }
    public Orders(String ID, String tailorname, String orderdate,  String image, String dresstype) {
        this.ID = ID;
        this.tailorname = tailorname;
        this.orderdate = orderdate;
        this.ordersID = ordersID;
        this.image = image;
        this.dresstype = dresstype;
    }

    public String getDresstype() {
        return dresstype;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTailorname(String tailorname) {
        this.tailorname = tailorname;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public void setOrderID(String orderID) {
        this.ordersID = ordersID;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTailorlocation(String tailorlocation) {
        this.tailorlocation = tailorlocation;
    }

    public String getID() {
        return ID;
    }

    public String getTailorname() {
        return tailorname;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public String getOrderID() {
        return ordersID;
    }

    public String getImage() {
        return image;
    }

    public String getTailorlocation() {
        return tailorlocation;
    }

}
