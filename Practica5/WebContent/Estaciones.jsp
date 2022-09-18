<%@page import="paa.modelo.Estacion" %>
<%@page import="paa.modelo.Servicio" %>
<%@page import ="java.util.List" %>
<%@page import = "paa.modelo.Coordenada" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Estaciones</title>
</head>
<body>


<%

	
	
	List <Estacion> estaciones;
	
    estaciones = (List <Estacion>)request.getAttribute("datosEstaciones");
    String nombreServicio = (String) request.getAttribute("nombreServicio");
	
	if(estaciones!= null){
	

		String salida = "";
		salida+="<a href='http://localhost:8080/Practica5/'>Volver a Servicios</a><hr>";
		salida+="<h1>Estaciones del servicio: -- "+nombreServicio+" --</h1><br>";
		
		for (Estacion e : estaciones){
			salida+="<a href='http://localhost:8080/Practica5/?accion=estacion&codigo="+e.getCodigo()+"'>"+e.getDescripcion()+"</a>";
			
			Coordenada c = e.getCoordenada();
			salida +="<ul>";
			salida+=" <li>Codigo: "+e.getCodigo();
			salida+=" <li>Latitud: "+c.getLatitud();
			salida+=" <li>Longitud: "+c.getLongitud();
			
			salida+=" <li>Habilitada: "+(e.isHabilitada()?"Sí":"No");
			salida+=" <li>Disponibles: "+e.getDisponibles()+"/"+e.getCapacidad();

			salida+="</ul><br>";
			
			
		}
		
		salida+="<hr><a href='http://localhost:8080/Practica5/'>Volver a Servicios</a>";
		
		out.println(salida);

	}else{
		out.println("<h1> NO HAY SERVICIO DEFINIDO</h1>");
		
	}



%>






</body>
</html>