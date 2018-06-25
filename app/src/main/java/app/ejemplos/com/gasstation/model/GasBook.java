package app.ejemplos.com.gasstation.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class GasBook {
    @SerializedName("_id")
    private String id;
    @SerializedName("calle")
    private String street;
    private String rfc;
    @SerializedName("date_insert")
    private String dateInsert;
    private String regular;
    @SerializedName("colonia")
    private String colony;
    @SerializedName("numeropermiso")
    private String numPermit;
    @SerializedName("fechaaplicacion")
    private String aplicationDate;
    @SerializedName(".permisoid")
    private String idPermit;
    private String longitude;
    private String latitude;
    private String premium;
    @SerializedName("razonsocial")
    private String businessName;
    @SerializedName("codigopostal")
    private String cp;
    private String dieasel;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDateInsert() {
        return dateInsert;
    }

    public void setDateInsert(String dateInsert) {
        this.dateInsert = dateInsert;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getNumPermit() {
        return numPermit;
    }

    public void setNumPermit(String numPermit) {
        this.numPermit = numPermit;
    }

    public String getAplicationDate() {
        return aplicationDate;
    }

    public void setAplicationDate(String aplicationDate) {
        this.aplicationDate = aplicationDate;
    }

    public String getIdPermit() {
        return idPermit;
    }

    public void setIdPermit(String idPermit) {
        this.idPermit = idPermit;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getDieasel() {
        return dieasel;
    }

    public void setDieasel(String dieasel) {
        this.dieasel = dieasel;
    }

    @Override
    public String toString() {
        return "Post{" +
                "Colonia ='" + colony + '\'' +
                ", Regular ='" + regular + '\'' +
                ", Num Permiso =" + numPermit +
                ", id =" + id +
                '}';
    }
}
