package mission2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

/**
 * 
 * @author Ludovic Fastré
 *
 */
public class IO {
        
        /**
         * Reader servant d'input au programme 
         */
        private static BufferedReader reader=null;
        
        /**
         * @pre path est non null et non vide
         * @post ouvre le reader, lie au fichier path
         */
        public static void openReader(String path) throws FileNotFoundException{
                assert path!=null;
                assert !(path.equals(""));
                reader=new BufferedReader(new FileReader(path));
        }
        
        /**
         * @pre le reader a ete ouvert avec openReader
         * @post lit une ligne et la renvoie sous forme de string
         */
        public static String readInstruction() throws IOException{
                assert reader!=null;
                String line=reader.readLine();
                return line;
        }
        
        /**
         * @pre /
         * @post ferme le reader s'il est ouvert, ne fait rien s'il est ferme
         */
        public static void closeReader()throws IOException{
                if(reader!=null){
                        reader.close();
                        reader=null;
                }
        }
     
}