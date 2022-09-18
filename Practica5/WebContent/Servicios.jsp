<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="paa.modelo.Coordenada" %>
<%@page	import="paa.negocio.MovilidadService"%>
<%@page	import="paa.modelo.Servicio"%>
<%@page import="java.util.List" %>
<%@page import ="java.io.IOException"%>
<%@page import ="java.io.PrintWriter"%>


<%!//Declaración de variables
	MovilidadService dao;
	String Persistence_Name = "Practica5";
	private String pathBD = "./WEB-INF/bdatos/PAA_Servicios.mdb";
	//Declaración de métodos, si existen%>
<html>
<head>
<title>Servicios</title>
</head>
<body>
<%
	
	
	List<Servicio> datos = (List<Servicio>) request.getAttribute("datosServicios"); 

	
	if (datos !=null ){
		String salida ="";
		
		
		for (Servicio s : datos){
			salida+="<a href='http://localhost:8080/Practica5/?accion=estaciones&servicio="+s.getCodigo()+"&formato=html'>"+s.getNombre()+"</a>";
			salida+="<ul>";
			salida+="<li>codigo: "+s.getCodigo();
			salida+="<li>nombre: "+s.getNombre();
			salida+="<li>zona: "+s.getZona();
			salida+="</ul>";
			salida+="<br>";
			
		}
		
		out.println(salida);
		} else{
		out.println("<h1> NO HAY SERVICIOS </h1>");

	}
	
	%>
</body>
</html>
