package com.javierpintosettlin.dondeestudio;

/**
 * Created by Javier on 08/10/2015.
 */
public class Institucion {
    private int idInstitucion;
    private String nombreInstitucion;
    private String geoLatitud;
    private String geoLongitud;

    public int getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(int idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public String getGeoLatitud() {
        return geoLatitud;
    }

    public void setGeoLatitud(String geoLatitud) {
        this.geoLatitud = geoLatitud;
    }

    public String getGeoLongitud() {
        return geoLongitud;
    }

    public void setGeoLongitud(String geoLongitud) {
        this.geoLongitud = geoLongitud;
    }
}
