import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Principal {
	

	public static void main(String[] args) {
		//solicitar o nome do usuário
		Scanner teclado = new Scanner(System.in);
		System.out.println("Digite seu nome:");
		String nomeDoUsuario = teclado.nextLine();
		String nomeArquivo = nomeDoUsuario.trim() + ".txt";
		File f = new File(nomeArquivo);

		boolean continuar = true;
		
		while (continuar) {
			//o usuário digita um texto qualquer
			System.out.println("Digite seu texto:");
			String texto = teclado.nextLine();
			
			/* ignorar letras maiúsculas, minúsculas, pontos e vírgulas do texto do usuário*/
			texto = texto.toLowerCase();
			texto = texto.replaceAll("[\\\\,.?!@(){}\\[\\]/]", "");
			//System.out.println(texto);
			
			//array que vai armazenar todas as palavras erradas
			String[] palavrasErradas = new String[50000];
			int count = 0; 
			
			verificaPalavras(texto, palavrasErradas, count, f );
			
			//pergunta se deseja continuar
			System.out.println("*--------------------------------*");
			System.out.println("Deseja continuar? 1 = SIM 2 = NÃO");
			int resposta = Integer.parseInt(teclado.nextLine());
			System.out.println("*--------------------------------*");
			continuar = resposta == 1 ? true : false;
		}
		
		
		
	}
	
	// informa ao usuário (na tela) todos os erros cometidos por ele naquele texto
	public static void verificaPalavras(String texto, String[] palavrasErradas, int count, File f) {
		
		FileReader fr;
		BufferedReader br;
		
		//separar texto em array de palavras
		String [] textoSeparado = new String[100000];
		textoSeparado = texto.split("\\s");
		
		
		try {
			fr = new FileReader("palavras.txt");
			br = new BufferedReader(fr);
			
			//descobre número de linhas
			int linhas = (int) br.lines().count();
			//System.out.println(linhas);
			br.close();
			
			
			//variavel para controlar quando o sistema encontra a palavra no arquivo
			boolean encontrou = false;
			
			//for que passa por cada palavra do texto
			for (int i = 0; i < textoSeparado.length; i++) {
				encontrou = false;
				
				fr = new FileReader("palavras.txt");
				br = new BufferedReader(fr);
				
				//for que passa por cada linha no file
				for (int j = 0; j < linhas; j++) {
					String line = br.readLine();
					
					//verifica se tem no arquivo 
					if(textoSeparado[i].equals(line)) {
						//System.out.println(textoSeparado[i] + " = " + line);
						encontrou = true;
						j = linhas - 1;
					}
				}
				if(!encontrou) 
					palavrasErradas[count++] = textoSeparado[i];
			}
			//Se não tiver nenhuma palavra errada, ele imprime outra mensagem
			if (palavrasErradas[0] == null)
				System.out.println("Seu texto não possui nenhum erro.");
			else {
				System.out.println("Verifique a grafia das seguintes palavras:");
				for (int i = 0; i < palavrasErradas.length; i++) {
					if (palavrasErradas[i] != null)
						System.out.println(palavrasErradas[i]);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Escreve no novo arquivo
		escreveNoArquivo(f, texto, palavrasErradas);
		
		
		
	}
	
	public static void escreveNoArquivo(File f, String texto, String[] palavrasErradas) {
		
		
		FileWriter fw;
		try {
			fw = new FileWriter(f, true);
			PrintWriter out = new PrintWriter(fw);	
			
			out.print("[TEXTO] " + texto);
			out.println("\n-----------------------------");
			
			if (palavrasErradas[0] == null)
				out.println("Seu texto não possui nenhum erro.");
			else {
				out.println("[ERROS]");
				for (int i = 0; i < palavrasErradas.length; i++) {
					if (palavrasErradas[i] != null)
						out.println(palavrasErradas[i]);
				}
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}