package paa.negocio;

import java.util.List;

import paa.modelo.*;

public interface IMovilidadService {

	/**
	 * Crea un nuevo servicio con los parámetros indicados y lo devuelve.
	 * 
	 * @param codigo codigo del nuevo servico
	 * @param nombre nombre del nuevo servicio
	 * @param zona   zona del nuevo servicio
	 * @return el nuevo servicio creado o null en caso de error
	 */

	Servicio createServicio(String codigo, String nombre, String zona);

	/**
	 * Busca el servicio con el código indicado y lo devuelve. En caso de que no
	 * exista devuelve null.
	 * 
	 * @param codigoServicio codigo del servicio a buscar
	 * @return el servicio con el codigo indicado o null en caso de error.
	 */
	Servicio findServicio(String codigoServicio);

	/**
	 * Devuelve todos los servicios existentes.
	 * 
	 * @return lista de servicios que cumplen el filtro indicado
	 */
	List<Servicio> findServicios();

	/**
	 * Crea una nueva estación con los parámetros indicados y la devuelve.
	 * 
	 * @param codigo         codigo de la nueva estación
	 * @param descripcion    descripcion de la nueva estación
	 * @param disponibilidad disponibilidad de vehiculos de la nueva estación
	 * @param coordenada     coordenada geográfica de la nueva estación
	 * @param capacidad      capacidad de vehiculos de la nueva estación
	 * @param habilitada     estado habilitada/deshabilitada de la nueva estación
	 * @param servicio       servicio al que pertenece la nueva estación
	 * @return la nueva estacion creada o null en caso de error
	 */
	Estacion createEstacion(String codigo, String descripcion, int disponibilidad, Coordenada coordenada, int capacidad,
			boolean habilitada, Servicio servicio);

	/**
	 * Borra la estación indicada, debe estar deshabilitada.
	 * 
	 * @param codigoEstacion código de la estación a borrar
	 */
	boolean removeEstacion(String codigoEstacion);

	/**
	 * Retira el número de vehículos indicados de la estación origen, y los mueve a
	 * la estación destino, siempre y cuando existan vehículos disponibles en el
	 * origen y huecos libres suficientes en el destino. Además las estaciones deben
	 * estar habilitadas. Devuelve si se ha realizado correctamente la operación.
	 * 
	 * @param codigoEstacionOrigen  código que identifica la estación origen
	 * @param codigoEstacionDestino código que identifica la estación destino
	 * @param numero                cantidad de vehiculos a mover
	 * @return si la operación ha sido realizada correctamente o no
	 */
	boolean moverVehiculos(String codigoEstacionOrigen, String codigoEstacionDestino, int numero);

	/**
	 * Deshabilita la estación indicada, y reparte sus vehículos entre otras
	 * estaciónes del servicio. Devuelve si se ha realizado correctamente la
	 * operación.
	 * 
	 * @param codigoEstacion código que identifica la estación
	 * @return si la operación ha sido realizada correctamente o no
	 */
	boolean deshabilitar(String codigoEstacion);

	/**
	 * Habilita la estación indicada.
	 * 
	 * @param codigoEstacion código que identifica la estación
	 * @return si la operación ha sido realizada correctamente o no
	 */
	boolean habilitar(String codigoEstacion);

	/**
	 * Actualiza el número de vehículos disponibles en la estación indicada.
	 * 
	 * @return si la operación ha sido realizada correctamente o no
	 */
	boolean actualizarDisponibles(String codigoEstacion, int disponibles);
	
	
	/**
	 * @opcional
	 */
	//List<Estacion> findPocaDisponibilidad (int numVehiculos);
	/*
	List<Estacion> findEstaciones (Servicio s);
	
	Estacion findEstacion (String descripcion);
	*/
}