package paa.modelo;

import javax.persistence.*;


@Entity
@Table(name="ESTACIONES")
public class Estacion {
	
	
	@Id
	private String codigo;
	@Column(name="DESCRIPCION")
	private String descripcion;
	@Column(name="DISPONIBLES")
	private int disponibles;
	
	@Embedded
	private Coordenada coordenada;
	
	@JoinColumn(name="IDSERVICIO")	
	private Servicio servicio;
	@Column(name="HABILITADA")
	private boolean habilitada;
	@Column(name="CAPACIDAD")
	private int capacidad;

	public Estacion() {
	}

	public Estacion(String codigo, String descripcion, int disponibles, Coordenada coordenada, Servicio servicio,
			boolean habilitada, int capacidad) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.disponibles = disponibles;
		this.coordenada = coordenada;
		this.servicio = servicio;
		this.habilitada = habilitada;
		this.capacidad = capacidad;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDisponibles() {
		return disponibles;
	}

	public void setDisponibles(int disponibles) {
		this.disponibles = disponibles;
	}

	public Coordenada getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(Coordenada coordenadas) {
		this.coordenada = coordenadas;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public boolean isHabilitada() {
		return habilitada;
	}

	public void setHabilitada(boolean habilitada) {
		this.habilitada = habilitada;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
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
		Estacion other = (Estacion) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		

		return "{\"codigo\":\"" + codigo + "\", \"servicio\":\""+servicio.getNombre()+"\", \"descripcion\":\"" + descripcion + "\", \"disponibles\":\"" + disponibles
				+ "\", \"coordenadas\":" + getCoordenada().toString() + ", \"habilitada\":\"" + habilitada + "\", \"capacidad\":\"" + capacidad + "\"}";
	}
	

	public String toHtml() {		
		return "<li> descripcion=" + descripcion +"<ul><li>codigo=" + codigo + "<li> servicio="+servicio.getNombre()+ "<li> disponibles=" + disponibles
				+ "<li> coordenadas = <ul>" + getCoordenada().toHtml() + "</ul><li> habilitada=" + habilitada + "<li> capacidad=" + capacidad +"</ul>";
	}

}
