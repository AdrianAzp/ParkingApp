package paa.modelo;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="SERVICIOS")
public class Servicio {

	@Id
	private String codigo;
	@Column(name="NOMBRE")
	private String nombre;
	@Column(name="ZONA")
	private String zona;
	

	@OneToMany(mappedBy="servicio", fetch = FetchType.EAGER)
	private List<Estacion> estaciones;

	public Servicio() {
	}

	public Servicio(String codigo, String nombre, String zona) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.zona = zona;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public List<Estacion> getEstaciones() {
		return estaciones;
	}

	public void setEstaciones(List<Estacion> estaciones) {
		this.estaciones = estaciones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servicio other = (Servicio) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		getEstaciones().size();
		return "{\"codigo\":\"" + codigo + "\", \"nombre\":\"" + nombre + "\", \"zona\":\"" + zona + "\", \"estaciones\":"+getEstaciones().toString()+"}";
	}
	
	public String toHtml() {
		String html= "<li>nombre = " + nombre + "<ul><li>codigo = " + codigo + "<li> zona = " + zona + "<li> estaciones = <ul> ";
		
		for (Estacion e:getEstaciones()) {	
			html+=e.toHtml();
		}
		html+="</ul></ul>";
		return html;
	}
	

	
}
