package mission1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe d'ouverture/fermeture de fichier
 * @author Ludovic Fastré, Damiano Siciliano
 */
public class IO {

	private static BufferedReader reader=null;
	private static PrintWriter writer=null;
	
	/**
	﻿  * @pre path est non null et non vide
	﻿  * @post ouvre le reader, lie au fichier path
	  */
	public static void openReader(String path) throws FileNotFoundException{
		assert path!=null;
		assert !(path.equals(""));
		reader=new BufferedReader(new FileReader(path));
	}
	
	/**
	  * @pre le reader a ete ouvert avec openReader
	﻿  * @post lit une ligne et place les "mots" de cette ligne dans un tableau
	﻿  */
	public static String[] readInstruction() throws IOException{
		assert reader!=null;
		String line=reader.readLine();
		return line.split(" ");
	}
	
	/**
	﻿ * @pre /
	﻿ * @post ferme le reader s'il est ouvert, ne fait rien s'il est ferme
	﻿ */
	public static void closeReader()throws IOException{
		if(reader!=null){
			reader.close();
			reader=null;
		}
	}

	/**
	 * @pre path est non null et non vide
	﻿ * @post ouvre le writer, lie au fichier path
	﻿ */
	public static void openWriter(String path) throws FileNotFoundException {
		assert path!=null;
		assert !(path.equals(""));
		writer=new PrintWriter(path);
	}

	/**
	 * @pre le writer a ete ouvert
	 * @post ecrit d dans le fichier lie
	 */
	public static void writeResult(double d){
		assert writer!=null;
		writer.println(d);
		writer.flush();
	}

	/**
	﻿ * @pre le writer a ete ouvert
	﻿ * @post ecrit b dans le fichier lie
     */
	public static void writeResult(boolean b){
		assert writer!=null;
		writer.println(b);
		writer.flush();
	}

	/**
	﻿ * @pre /
	﻿ * @post ferme le writer s'il est ouvert, ne fait rien sinon
     */
	public static void closeWriter(){
		if(writer!=null){
			writer.close();
			writer=null;
		}
	}
}
