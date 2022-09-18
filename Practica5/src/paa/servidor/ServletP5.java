package paa.servidor;


import java.io.*;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import paa.modelo.Estacion;
import paa.modelo.Servicio;
import paa.negocio.MovilidadService;

/**
 * Servlet implementation class Practica5
 */
@WebServlet("/")
public class ServletP5 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MovilidadService dao;
	String Persistence_Name="Practica5";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletP5() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());


		String accion = request.getParameter("accion");
		String formato = request.getParameter("formato");
		String servicio = request.getParameter("servicio");
		System.out.println(servicio);

		String salida ="";

		if ("servicios".equals(accion)) {
			List <Servicio> serv_list = dao.findServicios();

			if ("json".contentEquals(formato)) {
				response.setContentType("text/plain; charset=UTF-8");
				salida="[";

				for (Iterator<Servicio> serv_it=serv_list.iterator();serv_it.hasNext();) {
					Servicio s = serv_it.next();
					salida+=toJSON(s);
					if(serv_it.hasNext()) {
						salida+=", ";
					}
				}
			} else {
				List <Servicio> datos = dao.findServicios();

				request.setAttribute("datosServicios", datos);

				RequestDispatcher d = request.getRequestDispatcher("/Servicios.jsp");
				d.forward(request, response);
			}
		} else if ("estaciones".equals(accion)) {
			String code = request.getParameter("servicio");
			Servicio datos = dao.findServicio(code);
			List<Estacion> lista = datos.getEstaciones();
			
			request.setAttribute("datosEstaciones", lista);
			request.setAttribute("nombreServicio", datos.getNombre());
			
			RequestDispatcher d = request.getRequestDispatcher("/Estaciones.jsp");
			d.forward(request, response);

		} else {
			List <Servicio> datos = dao.findServicios();

			request.setAttribute("datosServicios", datos);

			RequestDispatcher d = request.getRequestDispatcher("/Servicios.jsp");
			d.forward(request, response);
		}

		PrintWriter out = response.getWriter();
		out.println(salida);
	}

	private String toJSON (Servicio s) {
		String json="{";
		json+="\"codigo\":\""+s.getCodigo()+"\",";
		json+="\"nombre\":\""+s.getNombre()+"\",";
		json+="\"zona\":\""+s.getZona()+"\"";
		json+="}";
		return json;
	}



	public void init() {
		String absoluteDiskPath = getServletContext().getRealPath("./WEB-INF/bdatos/PAA_Servicios.mdb");

		absoluteDiskPath.replaceAll("WEB.+INF", "WEBxxxxxINF");

		System.out.println("WEB-INF:"+absoluteDiskPath);

		try {

			System.out.println("antes de crear el dao");
			dao = new MovilidadService(Persistence_Name, absoluteDiskPath);
			System.out.println("dao creado con exito");
		}
		catch (Exception e) {
			System.out.println ("Error al crear el dao.\n");
			e.printStackTrace();
		}
	}

}
