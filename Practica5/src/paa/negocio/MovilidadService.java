package paa.negocio;

import java.util.*;
import javax.persistence.*;
import paa.modelo.Coordenada;
import paa.modelo.Estacion;
import paa.modelo.Servicio;

public class MovilidadService implements IMovilidadService {
	
	EntityManagerFactory factory;

	public MovilidadService (String name, String path) {
		
		   @SuppressWarnings("rawtypes")
		    Map properties = new HashMap();
		    properties.put("javax.persistence.jdbc.url", "jdbc:ucanaccess://"+path);
		    factory = Persistence.createEntityManagerFactory(name, properties);
		   
		}
	
	@Override
	public Servicio createServicio(String codigo, String nombre, String zona) {
		
		EntityManager mg = factory.createEntityManager();
	
		
		try {
			
			mg.getTransaction().begin();
			Servicio s = new Servicio (codigo, nombre, zona);
			mg.persist(s);
			mg.getTransaction().commit();
			mg.close();
			return s;
			
		} catch (PersistenceException e) {
			
			e.printStackTrace();
			if (mg.getTransaction().isActive()) {
				mg.getTransaction().rollback();
			}
			
			mg.close();
			return null;

		}
		
	}

	@Override
	public Servicio findServicio(String codigoServicio) {
		
		EntityManager mg = factory.createEntityManager();
		
		try {
			Servicio s = mg.find(Servicio.class, codigoServicio);
			
			mg.close();
			
			return s;
		
		}catch(PersistenceException e){
			
			e.printStackTrace();
			if(mg.getTransaction().isActive()) {
				mg.getTransaction().rollback();
			}
			
			mg.close();
			return null;
			
		}
	}

	@Override
	public List<Servicio> findServicios() {
		
		EntityManager mg = factory.createEntityManager();
		
		try{
		List<Servicio> lista = mg.createQuery("SELECT s FROM Servicio s", Servicio.class).getResultList();
		mg.close();
		
		return lista;
		}catch (PersistenceException e) {
			
			e.printStackTrace();
			if(mg.getTransaction().isActive()) {
				mg.getTransaction().rollback();
			}
			
			return null;
		}
	}

	@Override
	public Estacion createEstacion(String codigo, String descripcion, int disponibilidad, Coordenada coordenada,
			int capacidad, boolean habilitada, Servicio servicio) {
		
		EntityManager mg = factory.createEntityManager();
	
		Servicio s = mg.find(Servicio.class, servicio.getCodigo());
		
		
			
			try {
				
				mg.getTransaction().begin();
				Estacion e = new Estacion(codigo, descripcion, disponibilidad, coordenada, servicio, habilitada, capacidad);
				mg.persist(e);
				mg.refresh(s);
				mg.getTransaction().commit();
				mg.close();
				return e;
				
			} catch (PersistenceException e) {
				
				e.printStackTrace();
				if (mg.getTransaction().isActive()) {
					mg.getTransaction().rollback();
				}
				
				mg.close();
				return null;
	
			}
		
		
	}

	@Override
	public boolean removeEstacion(String codigoEstacion) {
		
		EntityManager mg = factory.createEntityManager();

			
			try {
				
				mg.getTransaction().begin();
				Estacion e = mg.find(Estacion.class, codigoEstacion);
				Servicio s = mg.find(Servicio.class, e.getServicio().getCodigo());
				
				if(e.isHabilitada()) {
					return false;
				}else {
					mg.remove(e);
					mg.refresh(s);
					mg.getTransaction().commit();
					mg.close();
					return true;
				}

			} catch (PersistenceException e) {
				
				e.printStackTrace();
				if (mg.getTransaction().isActive())
					mg.getTransaction().rollback();
				mg.close();
				return false;
			}
		
		
		
	}

	@Override
	public boolean moverVehiculos(String codigoEstacionOrigen, String codigoEstacionDestino, int numero) {
		
		EntityManager mg = factory.createEntityManager();
		try {
			mg.getTransaction().begin();
			Estacion origen=mg.find(Estacion.class, codigoEstacionOrigen);
			Estacion destino = mg.find(Estacion.class, codigoEstacionDestino);
			if (origen.isHabilitada() 
					&& destino.isHabilitada() 
					&& origen.getServicio().equals(destino.getServicio())
					&& origen.getDisponibles()>=numero 
					&& destino.getCapacidad()-destino.getDisponibles()>=numero) {
				origen.setDisponibles(origen.getDisponibles()-numero);
				destino.setDisponibles(destino.getDisponibles()+numero);
				mg.persist(origen);
				mg.persist(destino);
				mg.getTransaction().commit();
				return true;
			} else {
				return false;
			}
		} catch (Throwable t) {
			System.err.println("ERROR CONTROLADO AL MOVER VEHICULOS: "+t);
			if(mg.getTransaction().isActive()) {
				mg.getTransaction().rollback();
			}
			return false;
		} finally {
		//	mg.close();
		}
	}

	@Override
	public boolean deshabilitar(String codigoEstacion) {

		EntityManager mg = factory.createEntityManager();
		try {
			int vehiculos =0;
			mg.getTransaction().begin();
			Estacion e=mg.find(Estacion.class, codigoEstacion);
			if (e!=null) {
				e.setHabilitada(false);
				vehiculos = e.getDisponibles();
				e.setDisponibles(0);
				mg.persist(e);


				if (vehiculos > 0) { 

					int i=0; 
					List <Estacion> lista = findPocaDisponibilidad(vehiculos); 

					while(i<lista.size() && !moverVehiculos(e.getCodigo(), lista.get(i).getCodigo(), vehiculos)) {

						i ++;
					}

				}

				mg.getTransaction().commit();
				return true;

			} else {
				mg.getTransaction().rollback();
				return false;
			}

		} catch (Throwable t) {
			System.err.println("ERROR CONTROLADO AL DESHABILITAR: "+t);
			if(mg.getTransaction().isActive()) {
				mg.getTransaction().rollback();
			}
			return false;
		} finally {
			mg.close();
		}	
	}

	@Override
	public boolean habilitar(String codigoEstacion) {
		
		EntityManager mg = factory.createEntityManager();
			try {
				mg.getTransaction().begin();
				Estacion e=mg.find(Estacion.class, codigoEstacion);
				if (e!=null) {

					e.setHabilitada(true);
					mg.persist(e);
					mg.getTransaction().commit();

					return true;
				} else {
					mg.getTransaction().rollback();
					return false;
				}

			
			} catch (Throwable t) {
				System.err.println("ERROR CONTROLADO AL HABILITAR: "+t);
				if(mg.getTransaction().isActive()) {
					mg.getTransaction().rollback();
				}
				return false;
			} finally {
				mg.close();
			}
		}

	@Override
	public boolean actualizarDisponibles(String codigoEstacion, int disponibles) {
		
		EntityManager mg = factory.createEntityManager();
	
		Estacion e = mg.find(Estacion.class, codigoEstacion);
		
		
		if(e!=null && e.getCapacidad()>=disponibles) {
		
			try {
					
				mg.getTransaction().begin();
				e.setDisponibles(disponibles);
				mg.getTransaction().commit();
				mg.close();
				return true;
					
			} catch (PersistenceException ex) {
					
				if (mg.getTransaction().isActive())
					mg.getTransaction().rollback();
				mg.close();
				return false;
		
			}
		}
	
		return false;
	}
	
	public List<Estacion> findPocaDisponibilidad (int numVehiculos){
			EntityManager mg = factory.createEntityManager();
			try {
				mg.getTransaction().begin();
				Query q = mg.createQuery("SELECT e FROM Estacion e JOIN e.servicio s WHERE e.disponibles <= :numVehiculos ORDER BY e.disponibles ASC, s.nombre ASC", Estacion.class);
				q.setParameter("numVehiculos", numVehiculos);
				List<Estacion> lista =q.getResultList();
	         	mg.getTransaction().commit();
				return lista;
			} catch (Throwable t) {
				System.err.println("ERROR CONTROLADO AL ENCONTRAR POCA DISPONIBILIDAD: "+t);
				if(mg.getTransaction().isActive()) {
					mg.getTransaction().rollback();
				}
				return null;
			} finally {
				mg.close();
			}


		}


}
