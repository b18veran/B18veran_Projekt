package com.example.b18veran_projekt;

public class Gymnaster {
    private String ID;
    private String name;
    private String location;
    private String company;
    private String auxdata;
    private String category;

    public Gymnaster()
    {
        ID="Saknar ID";
        name="Saknar övning";
        location="Saknar övning";
        company="Saknar övning";
        auxdata="Saknar övning";
        category="Saknar övning";
    }
    public Gymnaster(String n,String u, String l, String s, String a, String c)
    {
        ID = n;
        name = u;
        location = l;
        company = s;
        auxdata = a;
        category = c;
    }
    public String info()
    {
        String tmp=new String();
        tmp+=ID+"\n"+"\n"+"1. "+name+"\n"+"\n"+ "2. "+ location+"\n"+"\n"+ "3. "+ company+"\n"+"\n"+ "4. "+ auxdata+"\n"+"\n"+ "*. "+ category;
        return tmp;
    }
    public void setID(String n)
    {
        ID=n;
    }

    public String getID()
    {
        return ID;
    }
    public String getLocation()
    {
        return location;
    }

    public String getCompany()
    {
        return company;
    }
    public String getName()
    {
        return ID;
    }

    @Override
    public String toString(){
        return ID;
    }
}

