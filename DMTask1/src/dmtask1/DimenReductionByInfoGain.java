package dmtask1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;

public class DimenReductionByInfoGain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  try{
	          
	           /*
	            * 1.����ѵ��
	            */
	    	   BufferedReader br_train = new BufferedReader(new FileReader(
	   				"./ExperimentalData/kddtrain2017.arff"));
	   		   Instances trainIns = new Instances(br_train);
	           trainIns.setClassIndex(trainIns.numAttributes()-1);
	          
	           /*
	            * 2.��ʼ�������㷨��search method�������������㷨��attribute evaluator��
	            */
	           Ranker rank = new Ranker();
	           InfoGainAttributeEval eval = new InfoGainAttributeEval();
	          
	           /*
	            * 3.���������㷨�����������
	            */
	           eval.buildEvaluator(trainIns);
	          
	           /*
	            * 4.�����ض������㷨�����Խ���ɸѡ
	            * ������ʹ�õ�Ranker�㷨���������԰���InfoGain�Ĵ�С��������
	            */
	           
	           for(int k = 0; k < 10; k ++){
	        	   String name="./ExperimentalData/kddtrain2017_";
	        	   name += (k+1)*10;
	        	   name += ".arff";
	        	   
	        	   BufferedWriter bw_train = new BufferedWriter(new FileWriter(name));
		           rank.setNumToSelect((k+1)*10);
		           int[] attrIndex = rank.search(eval, trainIns);
		        // ѵ���ļ���ά

			   		bw_train.write("@relation kddtrain2017_"+(k+1)*10);
			   		bw_train.newLine();
			   		for (int i = 0; i < (k+1)*10; i++) {
			   			bw_train.write("@attribute Feature" + attrIndex[i] + " real");
			   			bw_train.newLine();
			   		}
			   		bw_train.write("@attribute class {0,1,2}");
			   		bw_train.newLine();
			   		bw_train.write("@data");
			   		bw_train.newLine();
			   		br_train.close();
			   		br_train = new BufferedReader(new FileReader("./ExperimentalData/kddtrain2017.arff"));
			   		String line = br_train.readLine();
			   		while (line.startsWith("@")) {
			   			line = br_train.readLine();
			   		}
			   		
			   		while (line != null) {
			   			String str[] = line.split(" ");
			   			for (int i = 0; i < (k+1)*10; i++) {
			   				bw_train.write(str[attrIndex[i]] + ",");
			   			}
			   			bw_train.write(str[str.length - 1]);
			   			bw_train.newLine();
			   			line = br_train.readLine();
			   		}
			   		bw_train.close();
		           }

	   		System.out.println("OK");
	       }catch(Exception e){
	           e.printStackTrace();
	       }
	}

}
