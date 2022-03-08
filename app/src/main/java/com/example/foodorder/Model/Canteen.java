package com.example.foodorder.Model;

public class Canteen {
    private String name, canteen, password;
    public Canteen()
    {

    }

    public Canteen(String name, String canteen, String password) {
        this.name = name;
        this.canteen = canteen;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCanteen() {
        return canteen;
    }

    public void setCanteen(String email) {
        this.canteen = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
