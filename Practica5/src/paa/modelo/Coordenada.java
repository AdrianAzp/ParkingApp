package paa.modelo;

import javax.persistence.*;

@Embeddable
public class Coordenada { 	

	
	private double longitud;
    
	private double latitud;

	public Coordenada() {
	}

	public Coordenada( double longitud, double latitud) {
		super();


		this.longitud = longitud;
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	@Override
	public String toString() {
		return "{\"longitud\":\"" + longitud + "\", \"latitud\":\"" + latitud + "\"}";
	}

	public String toHtml() {
		return "<li>longitud=" + longitud + "<li> latitud=" + latitud;
	}

	
}
