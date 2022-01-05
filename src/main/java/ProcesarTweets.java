/**
 * Nombre del fichero: ProcesarTweets.java
 * Autor: (Pendiente de rellenar)
 * TitulaciÃ³n/Grupo: (Pendiente de rellenar)
 * Fecha: (Pendiente de rellenar)
 * HistÃ³rico de cambios: (Pendiente de rellenar)
 */

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.Desktop;

public class ProcesarTweets {

	
	/**
	 * filtroContenido: elimina del fichero los caracteres no deseables
	 * @param input   Nombre del fichero de entrada a procesar
	 * @param output  Nombre del fichero resultado 
	 */
	public static void filtroContenido(String input, String output){	
		int veceselse=0;
		try {
			Scanner entrada= new Scanner(new FileReader(input));
			PrintWriter salida= new PrintWriter(new FileWriter(output));
			while(entrada.hasNext()) {
				char [] caracteres= entrada.nextLine().toCharArray();
				int i=0;
				while(i<caracteres.length) {
					int digito=(int)caracteres[i];
					if((digito >= (int)'a' && digito <= (int)'z')
							||(digito >= (int)'A' && digito <= (int)'Z')
							||(digito==(int)'_')||(digito==(int)'-')||(digito==(int)'#')//PARTIRLO EN BINARIAS EN UN METODO CON IF ELSE
							||(digito==(int)'@')||(digito==(int)'/')||(digito==(int)' ')
							||(digito>=(int)'0'&& digito<=(int)'9')||(digito==(int)'.')
							||(digito==(int)';')||(digito==(int)',')||(digito==(int)':')) {
						salida.print(caracteres[i]);
					}else {
						salida.print(" ");
						veceselse++;
					}
					i++;
				}
				salida.println();
			}
			
			entrada.close();
			salida.close();
		}catch(java.io.IOException ioex) {
			
		}
		System.out.println("--------------------------------------");
		System.out.println("(Tarea 1) Estadísticas del filtro por contenido: ");
		System.out.println("Número de caracteres sustituidos = " + veceselse);
		System.out.println("--------------------------------------");

	}
	
	/**
	 * filtroEstructura: elimina del fichero los tweets que no constan
	 * de 4 campos de informaciÃ³n
	 * @param name Nombre del fichero de entrada a procesar
	 * @param res  Nombre de fichero de resultado
	 */
	public static void filtroEstructura(String input, String output){
		int borrados=0;
		int buenos=0;
		try {
			Scanner entrada= new Scanner(new FileReader(input));
			PrintWriter salida= new PrintWriter(new FileWriter(output));
			while(entrada.hasNext()) {
				String tweet=entrada.nextLine();
				String [] caracteres= tweet.split(";");
				if(caracteres.length>=4){
				salida.println(tweet);
				borrados++;
				}else {
					salida.println();
					buenos++;
				}
			}
			
			entrada.close();
			salida.close();
			}catch(java.io.IOException ioex) {
			
		}
		System.out.println("--------------------------------------");
		System.out.println("(Tarea 2) Estadísticas del filtro por estructura: ");
		System.out.println("Número de tweets incorrectos eliminados ="+borrados);
		System.out.println("Número de tweets correctos = " + buenos);
		System.out.println("--------------------------------------");	
	
	}
	
	/**
	 * compactaBlancos: sustituye dos o mÃ¡s caracteres ' ' consecutivos
	 * por una sola ocurrencia
	 * @param input   Nombre del fichero de entrada a procesar
	 * @param output  Nombre del fichero de resultado
	 */
	public static void compactaBlancos(String input, String output) {
		int compactados=0;
		try {
			Scanner entrada= new Scanner(new FileReader(input));
			PrintWriter salida= new PrintWriter(new FileWriter(output));
			while(entrada.hasNext()) {
				char [] caracteres= entrada.nextLine().toCharArray();
				int i=0;
				while(i<caracteres.length) {
					int digito=(int)caracteres[i];
					int digito2=0;
					if(i>0) {
						digito2=(int)caracteres[i-1];
					}
					if(digito==(int)' '&&digito2==(int)' '){
						compactados++;
					}else {
						salida.print(caracteres[i]);				
					}
					i++;
				}
				salida.println();
			}
			
			entrada.close();
			salida.close();
		}catch(java.io.IOException ioex) {
			
		}
		System.out.println("--------------------------------------");
		System.out.println("(Tarea 3) Estadísticas del filtro de secuencias: ");
		System.out.println("Número de secuencias compactadas = " + compactados);
		System.out.println("--------------------------------------");	
	}
	
	/**
	 * numTagLinea: devuelve el nÃºmero de tags encontrados en la lÃ­nea
	 * de texto de entrada
	 * @param linea  LÃ­nea de texto a procesar
	 * @return 	     El nÃºmero de tags encontrados
	 */
	public static int numTagLinea(String linea) {
		int numerotags=0;
		String [] palabra=linea.split(" ");
		for(int i=0;i<palabra.length;i++) {
			if(palabra[i]!=null && palabra[i].length()>0) {
				char primeraletra=palabra[i].charAt(0);
				if(primeraletra=='#'){
					numerotags++;
				}
			}		
		}
		return numerotags;
	}
	
	/**
	 * extraccionTagLinea: devuelve los tag (como String) encontrados en
	 * la lÃ­nea de texto de entrada
	 * @param linea  Lï¿½nea de texto a procesar
	 * @return  	 Los tags encontrados. Si no encuentra ninguno devuelve
	 * 				 el valor null
	 */
	public static String[] extraccionTagLinea(String linea) {
		if(numTagLinea(linea)!=0){
			int t=0;
			String [] tags=new String[numTagLinea(linea)];
			String[] palabra=linea.split(" ");
			for(int i=0;i<palabra.length;i++) {
				char primeraletra=palabra[i].charAt(0);
				if(primeraletra=='#'){
					tags[t]=palabra[i];
					t++;
				}
			}
			return tags;
		}
		return null;
	}
	
	/**
	 * existeTag: chequea si un tag estÃ¡ contenido en un conjunto de tags 
	 * @param tags Estructura que contiene el conjunto de tags
	 * @param tag  Tag a buscar
	 * @return     Si el tag especificado estÃ¡ contenido en el conjunto
	 */
	public static boolean existeTag(ArrayList<String> tags, String tag) {
		boolean existe=tags.contains(tag);
		if(existe) {
			return true;
		}
		return false;
	}
	/**
	 * extraccionTagsFichero: devuelve todos los tags diferentes encontrados
	 * en un fichero de texto de entrada 
	 * @param input   Nombre del fichero a procesar
	 * @return		  Estructura de datos que contiene los tags	
	 */
	public static ArrayList<String> extraccionTagsFichero(String input) {
		int encontrados=0;
		ArrayList<String> conjuntodetags = new ArrayList<String>();
		try {
			Scanner entrada= new Scanner(new FileReader(input));
			while(entrada.hasNext()){
				String linea=entrada.nextLine();
				String tag = new String();
				String[] tags=extraccionTagLinea(linea);
				if(tags!=null) {
					for(int i=0;i<tags.length;i++) {
						tag=tags[i];
						if(!existeTag(conjuntodetags,tag)) {
						conjuntodetags.add(tag);
						encontrados++;
						System.out.println(tags[i]);
						}
					}
				}
			}
			entrada.close();
		}catch(java.io.IOException ioex) {
			
		}
		System.out.println("--------------------------------------");
		System.out.println("(Tarea 4)  Estadísticas de la extracción de tags: ");
		System.out.println("Número de tags diferentes encontrados = "+encontrados);
		System.out.println("--------------------------------------");
		return conjuntodetags;
}
	/**
	* Devuelve un vector de Tag’s, donde cada registro contiene un “tag”
	* diferente y el número de ocurrencias de éste en el fichero de entrada
	*
	* @param input Nombre del fichero de tweets filtrados
	* @param tags Listado de tags diferentes encontrados en el fichero
	* @return Tag[] Vector de “tags” y sus ocurrencias
	*/
	public static Tag[] ocurrenciaTagsFichero(String input, ArrayList<String> tags) {
		Tag[] resultado = new Tag[tags.size()]; // Creamos un vector con el resultado
		try {
			for(int i=0;i<tags.size();i++) { //recorremos todos los tags del vector para contar las veces que aparecen en el fichero
				String texto = tags.get(i);
				int ocurrencias = 0;
				Scanner entrada= new Scanner(new FileReader(input)); //leemos el fichero
				while(entrada.hasNext()){ //recorremos todas las lineas del fichero. Para comprobar el número de veces que aparece este tag
					String linea=entrada.nextLine(); // obtenemos la linea del fichero
					String[] tagsLinea = extraccionTagLinea(linea); // Extraemos los tags de esa linea
					if (tagsLinea != null) {
						for(int j=0;j<tagsLinea.length;j++) { //Recorremos el tag de la lina
							if (texto.equalsIgnoreCase(tagsLinea[j])) {
								ocurrencias++;
							}
						}
					}
				}
				entrada.close();
				Tag tag = new Tag(); //Creamos una nueva instancia de la variable tags
				tag.ocurrencias = ocurrencias;
				tag.texto = texto;
				resultado[i] = tag;
			}
		}catch(java.io.IOException ioex) {
		}
		return resultado;
	}
	
	/** 
	* Ordena los “tag” del vector de entrada en función del número de 
	* ocurrencias, de mayor a menor ocurrencias 
	* 
	* @param inputData Vector de “tags” no ordenado 
	* @return Song[] Vector de “tags” ordenado 
	*/ 
	public static Tag[] ordernarTagsPorOcurrencias(Tag[] inputData) {
		//http://puntocomnoesunlenguaje.blogspot.com/2012/07/metodo-de-ordenacion-burbuja.html
		
		Tag aux = new Tag();
		for (int i = 0; i < inputData.length - 1; i++) {
			for (int j = 0; j < inputData.length - i - 1; j++) {
				if ((inputData[j + 1].ocurrencias > inputData[j].ocurrencias)
						|| (inputData[j + 1].ocurrencias == inputData[j].ocurrencias && inputData[j + 1].texto.compareTo(inputData[j].texto) < 0)) {
					aux = inputData[j + 1];
					inputData[j + 1] = inputData[j];
					inputData[j] = aux;
				}
			}
		}
		
		return inputData;
	}
	
	/** 
	* Escribe por pantalla los “n” tag más usados 
	* 
	* @param inputData Vector de “tags” ordenado 
	* @param n Número de “tags” a escribir por pantalla 
	*/ 
	public static void escribeTagPopulares(Tag[] inputData, int n) {
		int i = 0;
		while (n > i && i < inputData.length) {
			System.out.println("--------------------------------------");
			System.out.println(inputData[i].texto + " ocurrencias " + inputData[i].ocurrencias);
			i++;
		}
	}
	
	/** 
	* URLsUsuario: devuelve todas las URL diferentes encontradas en un fichero de 
	* texto de entrada para un usuario concreto 
	* 
	* @param input Nombre del fichero a procesar
	* @param userName Nombre del usuario 
	* @return ArrayList<String> URLs encontradas en el fichero 
	*/ 
	public static ArrayList<String> URLsUsuario(String input, String userName) {
		ArrayList<String> resultado = new ArrayList<String>();
		int tweets = 0;
		int repes = 0;
		
		try {
			Scanner entrada= new Scanner(new FileReader(input));
			while(entrada.hasNext()) {
				String tweet=entrada.nextLine();
				String [] caracteres= tweet.split(";");
				if (caracteres[2].equalsIgnoreCase(userName)) {
					tweets++;
					String texto = caracteres[3];
					String [] palabras = texto.split(" ");
					for(int i=0;i<palabras.length;i++) {
						String palabra = palabras[i];
						if (palabra.startsWith("https://") || palabra.startsWith("http://")) {
							if (resultado.contains(palabra)) {
								repes++;
							} else {
								resultado.add(palabra);
							}
						}
					}
					
				}
			}
			
		}catch(java.io.IOException ioex) {
		}
		
		
		System.out.println("--------------------------------------");
		System.out.println("(Tarea 7) Estadísticas del procesado de direcciones Web de un usuario:");
		System.out.println("Nombre del usuario: " + userName);
		System.out.println("Número de tweets publicados por el usuario = " + tweets);
		System.out.println("Número de direcciones diferentes encontradas = " + resultado.size());
		System.out.println("Número de direcciones repetidas = " + repes);
		
		return  resultado;
	}
	
	/** 
	* URLsMeses: devuelve todas las URL diferentes encontradas en un fichero de 
	* texto de entrada que fueron publicadas en los meses [inicio…final] 
	* 
	* @param input Nombre del fichero a procesar
	* @param inico Mes (numérico) de inicio 
	* @param final Mes (numérico) de fin 
	* @return ArrayList<String> URLs encontradas en el fichero 
	*/ 
	public static ArrayList<String> URLsMeses(String input, int inicio, int fin){
		ArrayList<String> resultado = new ArrayList<String>();
		int repes = 0;
		try {
			Scanner entrada= new Scanner(new FileReader(input));
			entrada.nextLine();
			while(entrada.hasNext()) {
				String tweet=entrada.nextLine();
				String [] caracteres= tweet.split(";");
				String fecha = caracteres[0];
				int mes = Integer.parseInt(fecha.split("-")[1]);
				if ((inicio<=fin && mes >= inicio && mes <= fin) ||
						(inicio > fin && mes >= inicio && mes <= fin)) {
					String texto = caracteres[3];
					String [] palabras = texto.split(" ");
					for(int i=0;i<palabras.length;i++) {
						String palabra = palabras[i];
						if (palabra.startsWith("https://") || palabra.startsWith("http://")) {
							if (resultado.contains(palabra)) {
								repes++;
							} else {
								resultado.add(palabra);
							}
						}
					}
				}
			}
		}catch(java.io.IOException ioex) {
		}
		System.out.println("--------------------------------------");
		System.out.println("(Tarea 8) Estadísticas del procesado de direcciones Web por meses:");
		System.out.println("Mes inicial y final: " + inicio + "-" + fin);
		System.out.println("Número de direcciones diferentes encontradas = " + resultado.size());
		System.out.println("Número de direcciones repetidas = " + repes);
		
		return  resultado;
		
	}
	
	/** 
	* abrirEnlaceWeb: Abre una página Web en el navegador 
	* 
	* @param direccionWeb URL de la página a abrir en el navegador 
	*/ 
	public static void abrirEnlaceWeb(String direccionWeb) {
		//https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
		    try {
				Desktop.getDesktop().browse(new URI(direccionWeb));
			} catch (IOException e) {
			} catch (URISyntaxException e) {
			}
		}
	}
	
	public static void main(String[] args) {	
		Scanner entrada = new Scanner(System.in);
		System.out.println("Nombre del fichero fuente (tweets): ");
		String nombreIn = entrada.nextLine();
		System.out.println("Nombre del fichero salida: ");
		String nombreOut = entrada.nextLine();
		
		filtroContenido(nombreIn, "temp1.txt");
		filtroEstructura("temp1.txt", "temp2.txt");
		compactaBlancos("temp2.txt",nombreOut);

		ArrayList<String> tags = extraccionTagsFichero(nombreOut);
		if (tags.size()>0) {
			System.out.println("------------------------------------------");
			System.out.println("Nï¿½mero de tags diferentes: " + tags.size());
			
			// Escribir por pantalla los distintos tag
			// ...
		}
		
		
		ArrayList<String> urls = URLsUsuario(nombreOut, "chematierra");
		
		URLsMeses(nombreOut, 1, 10);
		
		abrirEnlaceWeb(urls.get(0));
		
		entrada.close();
		
	}
}