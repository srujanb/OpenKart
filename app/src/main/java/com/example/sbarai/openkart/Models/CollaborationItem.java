package com.example.sbarai.openkart.Models;

import com.google.firebase.database.Exclude;

/**
 * Created by sbarai on 1/17/18.
 */

public class CollaborationItem {

    private String itemName;
    private String itemLink;
    private float count;
    private float ratePerUnit;
    private int unitType;
    private Boolean isDelivered;

    public String getItemName() {
        return itemName;
    }

    public void seItemName(String name) {
        this.itemName = name;
    }

    public String getItemLink() {
        return itemLink;
    }

    public void setItemLink(String itemLink) {
        this.itemLink = itemLink;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public float getRatePerUnit() {
        return ratePerUnit;
    }

    public void setRatePerUnit(float ratePerUnit) {
        this.ratePerUnit = ratePerUnit;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public Boolean isDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(Boolean delivered) {
        isDelivered = delivered;
    }

    @Exclude
    public String getUnitTypeSymbol(int unitType){
        String unitTypeUnitSymbole = getUnitTypeUnitSymbol(unitType);
        if (unitTypeUnitSymbole.equals(""))
            return "";
        else
            return unitTypeUnitSymbole.concat("(s)");
    }

    @Exclude
    public String getUnitTypeUnitSymbol(int unitType){
        switch (unitType){
            case Constants.CollaborationItem.UNITTYPE_COUNT:{
                return "packet";
            }
            case Constants.CollaborationItem.UNITTYPE_GRAM:{
                return "gram";
            }
            case Constants.CollaborationItem.UNITTYPE_KILOGRAM:{
                return "kilogram";
            }
            case Constants.CollaborationItem.UNITTYPE_MILLILITER:{
                return "milliliter";
            }
            case Constants.CollaborationItem.UNITTYPE_LITRE:{
                return "litre";
            }
            default:
                return "";
        }
    }

}
