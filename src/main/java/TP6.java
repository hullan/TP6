import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TP6 {

	public static void main(String[] args) {
		filtroContenido("test.txt", "resultado.txt");

		filtroEstructura("resultado.txt", "resultado2.txt");

		compactaBlancos("resultado2.txt", "resultado3.txt");
		
		extraccionTagsFichero("resultado3.txt");
	}

	/**
	 * filtroContenido: elimina del fichero los caracteres no deseables
	 * 
	 * @param input  Nombre del fichero de entrada a procesar
	 * @param output Nombre del fichero resultado
	 */
	public static void filtroContenido(String input, String output) {
		Path filePathInput = Paths.get(input);

		Path filePathOutput = Paths.get(output);

		try {
			Files.lines(filePathInput).findFirst().ifPresent(s -> {
				try {
					Files.writeString(filePathOutput, s + "\n", StandardOpenOption.CREATE);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			StringBuilder aux = new StringBuilder();
			Files.lines(filePathInput).skip(1).forEach(s -> {
				try {
					aux.append(s);
					Files.writeString(filePathOutput, s.replaceAll("[^a-zA-Z0-9\s:.\\/,;_\\-#@ñÑ]", " ") + "\n",
							StandardOpenOption.APPEND);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			System.out.println("--------------------------------------");
			System.out.println("(Tarea 1) Estadísticas del filtro por contenido: ");
			System.out.println("Número de caracteres sustituidos = "
					+ aux.toString().replaceAll("[a-zA-Z0-9\\s:.\\/,;_\\-#\\@ñÑ]", "").length());
			System.out.println("--------------------------------------");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * filtroEstructura: elimina del fichero los tweets que no constan de 4 campos
	 * de información
	 * 
	 * @param name Nombre del fichero de entrada a procesar
	 * @param res  Nombre de fichero de resultado
	 */
	public static void filtroEstructura(String input, String output) {
		Path filePathInput = Paths.get(input);

		Path filePathOutput = Paths.get(output);

		try {
			Files.lines(filePathInput).findFirst().ifPresent(s -> {
				try {
					Files.writeString(filePathOutput, s + "\n", StandardOpenOption.CREATE);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			long count = Files.lines(filePathInput).skip(1).filter(s -> s.split(";").length == 4).peek(s -> {
				try {
					Files.writeString(filePathOutput, s + "\n", StandardOpenOption.APPEND);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}).count();

			System.out.println("--------------------------------------");
			System.out.println("(Tarea 2) Estadísticas del filtro por estructura: ");
			System.out.println("Número de tweets incorrectos eliminados = "
					+ (Files.lines(filePathInput).skip(1).count() - count));
			System.out.println("--------------------------------------");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * compactaBlancos: sustituye dos o más caracteres ' ' consecutivos por una sola
	 * ocurrencia
	 * 
	 * @param input  Nombre del fichero de entrada a procesar
	 * @param output Nombre del fichero de resultado
	 */
	public static void compactaBlancos(String input, String output) {
		Path filePathInput = Paths.get(input);

		Path filePathOutput = Paths.get(output);

		try {
			Files.lines(filePathInput).findFirst().ifPresent(s -> {
				try {
					Files.writeString(filePathOutput, s + "\n", StandardOpenOption.CREATE);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			StringBuilder aux = new StringBuilder();
			Files.lines(filePathInput).skip(1).forEach(s -> {
				try {
					aux.append(s);
					Files.writeString(filePathOutput, s.replaceAll("\s{2,}", " ") + "\n", StandardOpenOption.APPEND);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			Matcher countWhiteSpace = Pattern.compile("\\s{2,}").matcher(aux.toString());

			System.out.println("--------------------------------------");
			System.out.println("(Tarea 3) Estadísticas del filtro de secuencias: ");
			System.out.println("Número de secuencias compactadas = " + countWhiteSpace.results().count());
			System.out.println("--------------------------------------");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * numTagLinea: devuelve el número de tags encontrados en la línea de texto de
	 * entrada
	 * 
	 * @param linea Línea de texto a procesar
	 * @return El número de tags encontrados
	 */
	public static int numTagLinea(String linea) {
		return (int) Pattern.compile("#[^\\s]+", Pattern.MULTILINE).matcher(linea).results().count();
	}

	/**
	 * extraccionTagLinea: devuelve los tag (como String) encontrados en una línea
	 * de texto de entrada
	 * 
	 * @param linea Línea de texto a procesar
	 * @return Los tags encontrados. Si no encuentra ninguno devuelve el valor null
	 */
	public static String[] extraccionTagLinea(String linea) {
		return Pattern.compile("#[^\\s]+", Pattern.MULTILINE).matcher(linea).results().map(MatchResult::group)
				.toArray(String[]::new);
	}

	/**
	 * existeTag: chequea si un tag está contenido en un conjunto de tags
	 * 
	 * @param tags Estructura que contiene el conjunto de tags
	 * @param tag  Tag a buscar
	 * @return Si el tag especificado está contenido en el conjunto
	 */
	public static boolean existeTag(ArrayList<String> tags, String tag) {
		return tags.contains(tag);
	}

	/**
	 * extraccionTagsFichero: devuelve todos los tags diferentes encontrados en un
	 * fichero de texto de entrada
	 * 
	 * @param input Nombre del fichero a procesar
	 * @return Estructura de datos que contiene los tags
	 */
	public static ArrayList<String> extraccionTagsFichero(String input) {
		ArrayList<String> tags = new ArrayList<>();

		Path filePathInput = Paths.get(input);

		try {
			StringBuilder aux = new StringBuilder();
			Files.lines(filePathInput).skip(1).forEach(s -> {
				aux.append(s);
				for (String tag : extraccionTagLinea(s)) {
					if (!existeTag(tags, tag.toLowerCase())) {
						tags.add(tag.toLowerCase());
					}

				}
			});

			System.out.println("--------------------------------------");
			System.out.println("(Tarea 4) Estadísticas de la extracción de tags ");
			System.out.println("Número de tags diferentes encontrados = " + tags.size());
			System.out.println("--------------------------------------");

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tags;
	}
}
