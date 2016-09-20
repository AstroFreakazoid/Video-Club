package CapaLogica;

import java.sql.SQLException;
import java.util.ArrayList;

import Multi.MultiAlquiler;
import Multi.MultiCliente;
import Multi.MultiEjemplar;
import Multi.MultiPelicula;
import Multi.MultiPersonaAdicional;

public class Gestor {


	/* ***********************************
	 * 			*** Afiliados ***
	 * ***********************************
	 */

	//REGISTRAR
	public String afiliadoRegistrar(int pnumero, String pcedula, String pnombre, String papellido, String ptelefono, String pestado){
		try{
			if(!afiliadoBuscar(pcedula)){
				new MultiCliente().crear(pcedula, pnombre, papellido, ptelefono, pestado);
				return "�Cliente registrado!";
			}else{
				return "�Cliente ya registrado!";
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return "SQL-error: " + e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			return "Excep-error: " + e.getMessage();
		}
	}

	//MODIFICAR
	public String afiliadoModificar(int pnumero, String pcedula, String pnombre, String papellido, String ptelefono, String pestado){
		try{
			Cliente cliente = new MultiCliente().consultarXNumero(pnumero);
			if(cliente != null){
				cliente.setNombre(pnombre);
				cliente.setPrimerApellido(papellido);
				cliente.setTelefono(ptelefono);
				cliente.setEstado(pestado);
				(new MultiCliente()).actualizar(cliente);
				return "�Cliente modificado!";
			}else{
				return "�Cliente no registrado!";
			}
		}catch(SQLException e){
			return "SQL-error: "+e.getMessage();
		}catch(Exception e){
			return "Excep-error: "+e.getMessage();
		}
	}

	//ELIMINAR
	public String afiliadoEliminar(int pnumAfiliado){
		try{
			if((new MultiCliente()).consultarXNumero(pnumAfiliado).getAlquileres().size() > 0){
				return "�Tiene alquileres pendientes!";
			}else{
				new MultiCliente().borrar(pnumAfiliado);
				return "�Cliente eliminado!";
			}
		}catch(SQLException e){
			return "SQL-error: "+e.getMessage();
		}catch(Exception e){
			return "Excep-error: "+e.getMessage();
		}
	}

	//BUSCAR
	public boolean afiliadoBuscar(String pcedula) throws SQLException, Exception{
		boolean encontrado = (new MultiCliente()).buscar(pcedula);
		return encontrado;
	}

	//LISTAR
	public String[][] afiliadoConsultarLista(){
		try{
			ArrayList<Cliente> listaClientes = (new MultiCliente()).listar();
			String[][] infoClientes = new String[listaClientes.size()][3];
			int i = 0;
			for(Cliente a : listaClientes){
				infoClientes[i][0]= String.valueOf(a.getNumCliente());
				infoClientes[i][1]= a.getCedula();
				infoClientes[i][2]= a.getNombre();
				i++;
			}
			return infoClientes;
		}catch(SQLException e){
			System.out.print("SQL-error: "+e.getMessage());
		}catch(Exception e){
			System.out.print("Excep-error: "+e.getMessage());
		}
		return null;
	}

	//CONSULTAR POR N�MERO
	public String[] afiliadoConsultarXNumero(int pnumero){
		try{
			Cliente cliente = new MultiCliente().consultarXNumero(pnumero);
			if(cliente != null){
				String[] info = new String[]{cliente.getCedula(), cliente.getNombre(), cliente.getPrimerApellido(), cliente.getTelefono(), cliente.getEstado(), cliente.getMoroso()};
				return info;
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}



	/* ***********************************
	 * 	  *** Personas adicionales ***
	 * ***********************************
	 */

	//REGISTRAR
	public String personaAdicionalRegistrar(String pcedula, String pnombre, int pnumeroCliente){
		try{
			if(personaAdicionalConsultarLista(pnumeroCliente).length == 2){
				(new MultiPersonaAdicional()).crear(pcedula, pnombre, pnumeroCliente);
				return "�Persona registrada!";
			}else{
				return "�Solo 2 personas!";
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return "SQL-error: " + e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			return "Excep-error: " + e.getMessage();
		}
	}

	//MODIFICAR
	public String personaAdicionalModificar(String pcedula, String pnombre, String pidCliente){
		try{
			PersonaAdicional personaAdicional = new MultiPersonaAdicional().consultarXId(pcedula);
			if(personaAdicional != null){
				personaAdicional.setCedula(pcedula);
				personaAdicional.setNombre(pnombre);
				(new MultiPersonaAdicional()).actualizar(personaAdicional);
				return "�Persona modificada!";
			}else{
				return "�Persona no registrada!";
			}
		}catch(SQLException e){
			return "SQL-error: "+e.getMessage();
		}catch(Exception e){
			return "Excep-error: "+e.getMessage();
		}
	}

	//ELIMINAR
	public void eliminarPersonasAdicionales(int pnumero){
		String[][] personas = personaAdicionalConsultarLista(pnumero);
		for(int i=0; i<personas.length; i++){
			personaAdicionalEliminar(personas[i][0]);
		}
	}

	public String personaAdicionalEliminar(String pcedula){
		try{
			new MultiPersonaAdicional().borrar(pcedula);
			return "�Persona eliminada!";
		}catch(SQLException e){
			return "SQL-error: "+e.getMessage();
		}catch(Exception e){
			return "Excep-error: "+e.getMessage();
		}
	}

	//BUSCAR
	public boolean personaAdicionalBuscar(String pcedula) throws SQLException, Exception{
		boolean encontrado = (new MultiPersonaAdicional()).buscar(pcedula);
		return encontrado;
	}

	//LISTAR
	public String[][] personaAdicionalConsultarLista(int pnumero){
		try{
			Cliente cliente = (new MultiCliente()).consultarXNumero(pnumero);
			ArrayList<PersonaAdicional> listaPersonas = cliente.getAmigos();
			String[][] infoPersonas = new String[listaPersonas.size()][2];
			int i = 0;

			for(PersonaAdicional a : listaPersonas){
				infoPersonas[i][0]= a.getCedula();
				infoPersonas[i][1]= a.getNombre();
				i++;
			}
			return infoPersonas;
		}catch(SQLException e){
			System.out.print("SQL-error: "+e.getMessage());
		}catch(Exception e){
			System.out.print("Excep-error: "+e.getMessage());
		}
		return null;
	}

	//CONSULTAR POR C�DULA
	public String[] personaAdicionalConsultarXCedula(String pcedula){
		try{
			PersonaAdicional personaAd = new MultiPersonaAdicional().consultarXId(pcedula);
			if(personaAd != null){
				String[] info = new String[]{personaAd.getCedula(), personaAd.getNombre()};
				return info;
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}



	/* ***********************************
	 * 			*** Peliculas ***
	 * ***********************************
	 */

	//REGISTRAR
	public String peliculaRegistrar(String pid, String ptitulo, String pcategoria){
		try{
			new MultiPelicula().crear(pid, ptitulo, pcategoria);
			return "�Pel�cula registrada!";
		}catch (SQLException e) {
			e.printStackTrace();
			return "SQL-error: " + e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			return "Excep-error: " + e.getMessage();
		}
	}

	//MODIFICAR
	public String peliculaModificar(String pid, String ptitulo, String pcategoria){
		try{
			Pelicula pelicula = new MultiPelicula().consultarXId(pid);
			if(pelicula != null){
				pelicula.setId(pid);
				pelicula.setTitulo(ptitulo);
				pelicula.setCategoria(pcategoria);
				(new MultiPelicula()).actualizar(pelicula);
				return "�Pel�cula modificada!";
			}else{
				return "�Pel�cula no registrada!";
			}
		}catch(SQLException e){
			return "SQL-error: "+e.getMessage();
		}catch(Exception e){
			return "Excep-error: "+e.getMessage();
		}
	}

	//ELIMINAR
	public String peliculaEliminar(String pid) throws SQLException, Exception{
		new MultiPelicula().borrar(pid);
		return "�Pel�cula eliminada!";
	}

	//BUSCAR
	public boolean peliculaBuscar(String pid) throws SQLException, Exception{
		boolean encontrado = (new MultiPelicula()).buscar(pid);
		return encontrado;
	}

	//LISTAR
	public String[][] peliculaConsultarLista(){
		try{
			ArrayList<Pelicula> listaPeliculas = (new MultiPelicula()).listar();
			String[][] infoPelis = new String[listaPeliculas.size()][3];
			int i = 0;
			for(Pelicula a : listaPeliculas){
				infoPelis[i][0]= a.getId();
				infoPelis[i][1]= a.getTitulo();
				infoPelis[i][2]= a.getCategoria();
				i++;
			}
			return infoPelis;
		}catch(SQLException e){
			System.out.print("SQL-error: "+e.getMessage());
		}catch(Exception e){
			System.out.print("Excep-error: "+e.getMessage());
		}
		return null;
	}

	//CONSULTAR POR ID
	public String[] peliculaConsultarXId(String pid){
		try{
			Pelicula pelicula = new MultiPelicula().consultarXId(pid);
			if(pelicula != null){
				String[] info = new String[]{pelicula.getId(), pelicula.getTitulo(), pelicula.getCategoria()};
				return info;
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}



	/* ***********************************
	 * 			*** Ejemplares ***
	 * ***********************************
	 */

	//REGISTRAR
	public String ejemplarRegistrar(String pidPelicula, String pestado, String pformato) throws SQLException, Exception{
		try{
			Ejemplar ejemplar = new MultiEjemplar().crear(pidPelicula, pestado, pformato);
			ejemplar.setID(pidPelicula + "-" + ejemplar.getCantEjemplares());
			new MultiEjemplar().asignarIdEjemplar(ejemplar);
			return "�Ejemplar registrado!";
		}catch (SQLException e) {
			e.printStackTrace();
			return "SQL-error: " + e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			return "Excep-error: " + e.getMessage();
		}
	}

	//MODIFICAR
	public String ejemplarModificar(String pidEjemplar, String pestado, String pformato){
		try{
			Ejemplar ejemplar = new MultiEjemplar().consultarXCodigo(pidEjemplar);
			if(ejemplar != null){
				ejemplar.setEstado(pestado);
				ejemplar.setFormato(pformato);
				(new MultiEjemplar()).actualizar(ejemplar);
				return "�Ejemplar modificado!";
			}else{
				return "�Ejemplar no registrado!";
			}
		}catch(SQLException e){
			return "SQL-error: "+e.getMessage();
		}catch(Exception e){
			return "Excep-error: "+e.getMessage();
		}
	}

	//ELIMINAR
	public String ejemplarEliminar(String pid){
		try{
			new MultiEjemplar().borrar(pid);
			return "�Ejemplar eliminado!";
		}catch(SQLException e){
			return "SQL-error: "+e.getMessage();
		}catch(Exception e){
			return "Excep-error: "+e.getMessage();
		}
	}

	//LISTAR
	public String[][] ejemplarConsultarLista(String pid){
		try{
			ArrayList<Ejemplar> listaEjemplares = (new MultiEjemplar()).listar(pid);
			String[][] infoEjm = new String[listaEjemplares.size()][3];
			int i = 0;
			for(Ejemplar a : listaEjemplares){
				infoEjm[i][0]= a.getID();
				infoEjm[i][1]= a.getEstado();
				infoEjm[i][2]= a.getFormato();
				i++;
			}
			return infoEjm;
		}catch(SQLException e){
			System.out.print("SQL-error: "+e.getMessage());
		}catch(Exception e){
			System.out.print("Excep-error: "+e.getMessage());
		}
		return null;
	}

	//LISTAR DISPONIBLES
	public String[][] ejemplarConsultarDisponibles(String pidPelicula){
		try{
			Pelicula pelicula = (new MultiPelicula()).consultarXId(pidPelicula);
			ArrayList<Ejemplar> listaEjemplares = pelicula.getEjemplares();
			int cantEjmDisponibles = 0;

			for(int i=0; i<listaEjemplares.size(); i++){
				if(listaEjemplares.get(i).getEstado().equals("Disponible")){
					cantEjmDisponibles++;
				}
			}
			String[][] infoEjm = new String[cantEjmDisponibles][2];
			int j = 0;
			for(Ejemplar a : listaEjemplares){
				if(a.getEstado().equals("Disponible")){
					infoEjm[j][0]= a.getID();
					infoEjm[j][1]= a.getFormato();
					j++;
				}
			}
			return infoEjm;
		}catch(SQLException e){
			System.out.print("SQL-error: "+e.getMessage());
		}catch(Exception e){
			System.out.print("Excep-error: "+e.getMessage());
		}
		return null;
	}

	//CONSULTAR POR C�DIGO
	public String[] ejemplarConsultarXCodigo(String pid){
		try{
			Ejemplar ejemplar = new MultiEjemplar().consultarXCodigo(pid);
			if(ejemplar != null){
				String[] info = new String[]{ejemplar.getID(), ejemplar.getEstado(), ejemplar.getFormato(), ejemplar.getIdPelicula()};
				return info;
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	//CAMBIAR ESTADO
	public void ejemplarCambiarEstado(String pidEjm, String pestado) throws SQLException, Exception{
		Ejemplar ejemplar = new MultiEjemplar().consultarXCodigo(pidEjm);
		ejemplar.setEstado(pestado);
		(new MultiEjemplar()).cambiarEstado(ejemplar, ejemplar.getEstado());
	}



	/* ***********************************
	 * 			*** Alquileres ***
	 * ***********************************
	 */

	//REGISTRAR
	public String alquilerRegistrar(String pfechaRealizado, String pfechaDevolucion, int pnumAfiliado, String pidEjm){
		try{
			Cliente cliente = new MultiCliente().consultarXNumero(pnumAfiliado);
			if(cliente != null){
				ejemplarCambiarEstado(pidEjm, "Alquilado");
				(new MultiAlquiler()).crear(pfechaRealizado, pfechaDevolucion, pnumAfiliado, pidEjm);
				return "�Alquiler registrado!";
			}else{
				return "�Afiliado "+ pnumAfiliado +" no registrado!";
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "Excep-error: " + e.getMessage();
		}
	}

	//MODIFICAR
	public String alquilerModificar(int pnumAlq, String pfechaRealizado, String pfechaDevolucion, int pnumAfiliado, String pidEjm){
		try{
			Alquiler alquiler = (new MultiAlquiler()).consultarXNumero(pnumAlq);

			if(alquiler.getIdEjemplar() != pidEjm){
				ejemplarCambiarEstado(alquiler.getIdEjemplar(), "Disponible");
			}
			ejemplarCambiarEstado(pidEjm, "Alquilado");

			alquiler.setFechaAlquiler(pfechaRealizado);
			alquiler.setFechaDevolucion(pfechaDevolucion);
			alquiler.setIdEjemplar(pidEjm);
			alquiler.setIdAfiliado(pnumAfiliado);
			(new MultiAlquiler()).actualizar(alquiler);

			return "�Alquiler modificado!";
		}catch(SQLException e){
			return "SQL-error: "+e.getMessage();
		}catch(Exception e){
			return "Excep-error: "+e.getMessage();
		}
	}

	//DEVOLVER
	public void alquilerDevolver(int pnumAfiliado, String pidEjemplar) throws SQLException, Exception{
		new MultiAlquiler().borrar(pidEjemplar);
		ejemplarCambiarEstado(pidEjemplar, "Disponible");
	}

	//LISTAR
	public String[][] alquilerConsultarLista(){
		try{
			ArrayList<Alquiler> listaAlquis = (new MultiAlquiler()).listar();
			String[][] infoAlquis = new String[listaAlquis.size()][3];
			int i = 0;
			for(Alquiler a : listaAlquis){
				infoAlquis[i][0]= String.valueOf(a.getNumero());
				infoAlquis[i][1]= (new MultiCliente()).consultarXNumero(a.getIdAfiliado()).getCedula();
				infoAlquis[i][2]= a.getIdEjemplar();
				i++;
			}
			return infoAlquis;
		}catch(SQLException e){
			System.out.print("SQL-error: "+e.getMessage());
		}catch(Exception e){
			System.out.print("Excep-error: "+e.getMessage());
		}
		return null;
	}

	//LISTAR ALQUILERES POR CLIENTE
	public String[][] alquilerConsultarXCliente(int pnumCliente){
		try{
			ArrayList<Alquiler> listaAlquis = (new MultiAlquiler()).consultarXCliente(pnumCliente);
			String[][] infoAlquis = new String[listaAlquis.size()][4];
			int i = 0;
			for(Alquiler a : listaAlquis){
				infoAlquis[i][0]= String.valueOf(a.getNumero());
				infoAlquis[i][1]= (new MultiPelicula()).consultarXId((new MultiEjemplar()).consultarXCodigo(a.getIdEjemplar()).getIdPelicula()).getTitulo();
				infoAlquis[i][2]= (new MultiEjemplar()).consultarXCodigo(a.getIdEjemplar()).getFormato();
				infoAlquis[i][3]= (new MultiAlquiler()).consultarXNumero(a.getNumero()).getFechaAlquiler();
				i++;
			}
			return infoAlquis;
		}catch(SQLException e){
			System.out.print("SQL-error: "+e.getMessage());
		}catch(Exception e){
			System.out.print("Excep-error: "+e.getMessage());
		}
		return null;
	}

	//CONSULTAR POR N�MERO
	public String[] alquilerConsultarXNumero(int pnumero){
		try{
			Alquiler alquiler = new MultiAlquiler().consultarXNumero(pnumero);
			if(alquiler != null){
				String[] info = new String[]{alquiler.getFechaAlquiler(), alquiler.getFechaDevolucion(), String.valueOf(alquiler.getIdAfiliado()), alquiler.getIdEjemplar()};
				return info;
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
}