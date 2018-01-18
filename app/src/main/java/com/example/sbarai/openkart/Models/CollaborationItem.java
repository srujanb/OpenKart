package com.example.sbarai.openkart.Models;

/**
 * Created by sbarai on 1/17/18.
 */

public class CollaborationItem {

    public final int UNITTYPE_COUNT = 0;
    public final int UNITTYPE_GRAM = 1;
    public final int UNITTYPE_KILOGRAM = 2;
    public final int UNITTYPE_MILLILITER = 3;
    public final int UNITTYPE_LITRE = 4;

    public String getItemName() {
        return itemName;
    }

    public void seItemName(String name) {
        this.itemName = name;
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

    String itemName;
    float count;
    float ratePerUnit;
    int unitType;

    public String getUnitTypeSymbol(int unitType){
        String unitTypeUnitSymbole = getUnitTypeUnitSymbol(unitType);
        if (unitTypeUnitSymbole.equals(""))
            return "";
        else
            return unitTypeUnitSymbole.concat("(s)");
    }

    public String getUnitTypeUnitSymbol(int unitType){
        switch (unitType){
            case UNITTYPE_COUNT:{
                return "packet";
            }
            case UNITTYPE_GRAM:{
                return "gram";
            }
            case UNITTYPE_KILOGRAM:{
                return "kilogram";
            }
            case UNITTYPE_MILLILITER:{
                return "milliliter";
            }
            case UNITTYPE_LITRE:{
                return "litre";
            }
            default:
                return "";
        }
    }

}
