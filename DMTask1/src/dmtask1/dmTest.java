package dmtask1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IB1;
import weka.core.Instances;

public class dmTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br_train = new BufferedReader(new FileReader(
					"./ExperimentalData/kddtrain2017.arff"));
			BufferedWriter bw_train = new BufferedWriter(new FileWriter(
					"./ExperimentalData/kddtrain2017_30.arff"));
			BufferedReader br_test = new BufferedReader(new FileReader(
					"./ExperimentalData/kddtest2017.arff"));
			BufferedWriter bw_test = new BufferedWriter(new FileWriter(
					"./ExperimentalData/kddtest2017_30.arff"));
			//
			// 1.�����ļ�

			Instances trainIns = new Instances(br_train);
			trainIns.setClassIndex(trainIns.numAttributes() - 1);

			Instances testIns = new Instances(br_test);
			testIns.setClassIndex(testIns.numAttributes() - 1);

			// 2.��ʼ�������㷨��search method�������������㷨��attribute evaluator��

			Ranker rank = new Ranker();
			InfoGainAttributeEval eval = new InfoGainAttributeEval();

			// 3.���������㷨�����������

			eval.buildEvaluator(trainIns); // System.out.println(rank.search(eval,trainIns));

			// 4.�����ض������㷨�����Խ���ɸѡ // ������ʹ�õ�Ranker�㷨���������԰���InfoGain�Ĵ�С��������,ȡǰnum������

			int num = 30;
			rank.setNumToSelect(num);
			int[] attrIndex = rank.search(eval, trainIns);
			
			// ѵ���ļ���ά

			bw_train.write("@relation kddtrain2017_30");
			bw_train.newLine();
			for (int i = 0; i < num; i++) {
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
				for (int i = 0; i < num; i++) {
					bw_train.write(str[attrIndex[i]] + ",");
					
				}
				bw_train.write(str[str.length - 1]);
				bw_train.newLine();
				line = br_train.readLine();
			}

			// �����ļ���ά
			bw_test.write("@relation kddtrain2017_30");
			bw_test.newLine();
			for (int i = 0; i < num; i++) {
				bw_test.write("@attribute Feature" + attrIndex[i] + " real");
				bw_test.newLine();
			}
			bw_test.write("@attribute class {0,1,2}");
			bw_test.newLine();
			bw_test.write("@data");
			bw_test.newLine();
			br_test.close();
			br_test = new BufferedReader(new FileReader("./ExperimentalData/kddtest2017.arff"));
			line = br_test.readLine();
			while (line.startsWith("@")) {
				line = br_test.readLine();
			}
			while (line != null) {

				String str[] = line.split(",");
				System.out.println();
				for (int i = 0; i < num; i++) {
					bw_test.write(str[attrIndex[i]] + ",");
				}
				bw_test.write(str[str.length - 1]);
				bw_test.newLine();
				line = br_test.readLine();
			}

			br_test.close();
			br_train.close();
			bw_test.close();
			bw_train.close();
			System.out.println("OK");
			//���ѡ���ά��
			for (int i = 0; i < num; i++) {
				System.out.print(attrIndex[i] + ",");
			}
			// ѵ������
			br_train = new BufferedReader(new FileReader(
					"./ExperimentalData/kddtrain2017_30.arff"));
			br_test = new BufferedReader(new FileReader(
					"./ExperimentalData/kddtest2017_30.arff"));
			trainIns = new Instances(br_train);
			trainIns.setClassIndex(trainIns.numAttributes() - 1);
			testIns = new Instances(br_test);
			testIns.setClassIndex(testIns.numAttributes() - 1);
			
			//����IB1������
			Classifier cfs1 = new IB1();
			cfs1.buildClassifier(trainIns);
			
			Evaluation testingEvaluation = new Evaluation(testIns);

			int length = testIns.numInstances();
			for (int i = 0; i < length; i++) {
				testingEvaluation.evaluateModelOnceAndRecordPrediction(cfs1,
						testIns.instance(i));
			}
			File writename = new File("./TestResult.txt");
			writename.createNewFile(); // �������ļ�
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			for (int i = 0; i < length; i++) {
				out.write((int) cfs1.classifyInstance(testIns.instance(i))
						+ "\r\n");
			}
			br_test.close();
			br_train.close();
			out.close();
			System.out.println("OK");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
