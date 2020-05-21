package com.data.redact.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
@Controller
public class SentimentAnalysisService {

	private static DoccatModel model;
	private static HashMap<Integer, String> sentimentsMap = new HashMap<Integer,String>();
	
	   @RequestMapping("/sentimentanalyser")
	   public String redirectToSelf() {
	      return "sentimentanalyser";
	   }
	
	   @PostMapping("/sentimentanalyser")
	   public String processSentimentAnalysis(@RequestParam("inputText") String inputText,Model opmodel) {
		sentimentsMap.put(1, "Positive");
		sentimentsMap.put(0, "Negative");
		
        File trainedmodel = new File("en-doccat-sentiment-analyser.bin"); 
        if(trainedmodel.exists()) {
    		try {
				model = new DoccatModel(trainedmodel);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }else {
        	trainModel();
        }
		opmodel.addAttribute("results", analyseSentiment(inputText));
		return "sentimentanalyser";
	}
 
	public void trainModel() {
		try {
			InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("./src/main/resources/dataset/Sentiment_analysis_trainingdata.txt"));
			ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
			TrainingParameters params = new TrainingParameters();
			params.put(TrainingParameters.ITERATIONS_PARAM, 10);
			params.put(TrainingParameters.CUTOFF_PARAM, 2);
			model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());

			System.out.println("Model is successfully trained.");
			 
            BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream("en-doccat-sentiment-analyser.bin"));
            model.serialize(modelOut);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public String analyseSentiment(String sentence) {
		DocumentCategorizer doccat = new DocumentCategorizerME(model);
        String[] docWords = sentence.replaceAll("[^A-Za-z]", " ").split(" ");
        double[] aProbs = doccat.categorize(docWords);
        double probablity = 0;
        System.out.println("\n---------------------------------\nCategory : Probability\n---------------------------------");
        for(int i=0;i<doccat.getNumberOfCategories();i++){
            System.out.println(sentimentsMap.get(Integer.parseInt(doccat.getCategory(i)))+" : "+aProbs[i]);
            if(doccat.getBestCategory(aProbs).equals(doccat.getCategory(i))) {
            	probablity = aProbs[i];
            }
            	
        }
        System.out.println("---------------------------------");

         return sentimentsMap.get(Integer.parseInt(doccat.getBestCategory(aProbs)))+" : is the predicted sentiment at the "
         		+ " probablity of "+Math.round(probablity*100) +"% for the given sentence: <br>"+sentence;

	}


}
