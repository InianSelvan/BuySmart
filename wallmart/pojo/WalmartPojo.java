package com.buysmart.wallmart.pojo;

public class WalmartPojo
{
    private Items[] items;

    public Items[] getItems ()
    {
        return items;
    }

    public void setItems (Items[] items)
    {
        this.items = items;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [items = "+items+"]";
    }
}
