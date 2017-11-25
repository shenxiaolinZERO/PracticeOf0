package dmtask1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class txt2arff {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				try {
					BufferedReader br=new BufferedReader(new FileReader("./ExperimentalData/kddtest2017.txt"));
//					BufferedReader br=new BufferedReader(new FileReader("./ExperimentalData/kddtrain2017.txt"));
					String line=null;
					BufferedWriter bw=new BufferedWriter(new FileWriter("./ExperimentalData/kddtest2017.arff"));
//					BufferedWriter bw=new BufferedWriter(new FileWriter("./ExperimentalData/kddtrain2017.arff"));
					bw.write("@relation kddtest2017");
//					bw.write("@relation kddtrain2017");
					bw.newLine();
					for(int i=0;i<100;i++){
						bw.write("@attribute Feature"+i+" real");
						bw.newLine();
					}
					bw.write("@attribute class {0,1,2}");
					bw.newLine();
					bw.write("@data");
					bw.newLine();
					while((line=br.readLine() )!= null){
						
						//------------------------test--						
						String[] str=line.split("\t");//test 文件是\t分割
						for(int i=0;i<str.length;i++){
							bw.write(str[i]+",");
						}
						bw.write("?");
						//------------------------test--
						
						//------------------------train--					
//						String[] str=line.split("\n");//train 文件是空格分割
//						for(int i=0;i<str.length;i++){
//							if (i == 0){
//								bw.write(str[i]);
//							}
//							else{
//								bw.write(","+str[i]);
//							}
//						}
						//------------------------train--
						
						bw.newLine();
					}
					br.close();
					bw.close();
					System.out.println("OK! Finished!!");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
	}

}
