import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TP6 {

	public static void main(String[] args) {
		filtroContenido("test.txt", "resultado.txt");
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
					Files.writeString(filePathOutput, s.replaceAll("[^a-zA-Z0-9\s:.\\/,;_\\-#@ñÑ]", " ") + "\n", StandardOpenOption.APPEND);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			System.out.println("--------------------------------------");
			System.out.println("(Tarea 1) Estadísticas del filtro por contenido: ");
			System.out.println("Número de caracteres sustituidos = " + aux.toString().replaceAll("[a-zA-Z0-9\\s:.\\/,;_\\-#\\@ñÑ]", "").length());
			System.out.println("--------------------------------------");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		;

	}

}
