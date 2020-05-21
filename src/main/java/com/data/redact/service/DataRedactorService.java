package com.data.redact.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;
@Controller
public class DataRedactorService {
	private static final String personModelfilePath = "/models/en-ner-person.bin";
	private static final String locationModelfilePath = "/models/en-ner-location.bin";
	private static final String OrganizationModelfilePath = "/models/en-ner-organization.bin";
	private static final String TimeModelfilePath = "/models/en-ner-time.bin";
	private static final String DateModelfilePath = "/models/en-ner-date.bin";
	
	
	   @RequestMapping("/dataredactor")
	   public String redirectToSelf() {
	      return "dataredactor";
	   }

	   @PostMapping("/dataredactor")
	   public String processRedaction(@RequestParam("inputText") String inputText,
			   				  @RequestParam(name="person", required=false) String person, 
			   				  @RequestParam(name="location", required=false) String location,
			   				  @RequestParam(name="orgs", required=false) String orgs,
			   				  @RequestParam(name="date", required=false) String date,
			   				  @RequestParam(name="time", required=false) String time,
			   				  Model model) {
	      
	      String result = redacte(inputText,person,location,orgs,date,time);
	      String redactionOptions = "Redaction options you selected were: <br> Person Names: "+String.valueOf(person==null?false:true) + " | Location Names: "+String.valueOf(location==null?false:true)
		      		+ " | Organization Names: "+String.valueOf(orgs==null?false:true) + " | Dates: "+String.valueOf(date==null?false:true) + " | Times: "+String.valueOf(time==null?false:true);
	      model.addAttribute("results","<h5> ------------------The Results --------------------</h5>");
	      model.addAttribute("redactionOptions",redactionOptions);
	      model.addAttribute("inputString","Input text is: <br> "+inputText);
	      model.addAttribute("redactedString", "Redacted text is: <br> "+result);
	      return "dataredactor";
	   }
	
	public String redacte(String inputText, String person, String location, String orgs, String date, String time) {
			boolean personNameRedaction = person==null?false:true;
			boolean locationRedaction = location==null?false:true;
			boolean organizationRedaction = orgs==null?false:true;
			boolean timeRedaction = time==null?false:true;
			boolean DateRedaction = date==null?false:true;

			SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
			String inputString = inputText;
	        String[] tokens = tokenizer.tokenize(inputString);
	        if(personNameRedaction == true) {
	        	redactTokens(personModelfilePath,tokens);
	        }
	        if(locationRedaction == true) {
	        	redactTokens(locationModelfilePath,tokens);
		    }
	        if(organizationRedaction == true) {
	        	redactTokens(OrganizationModelfilePath,tokens);
		    }
	        if(timeRedaction == true) {
	        	redactTokens(TimeModelfilePath,tokens);
		    }
	        if(DateRedaction == true) {
	        	redactTokens(DateModelfilePath,tokens);
		    }
	        
	        DetokenizationUtil detokenizationUtil = new DetokenizationUtil();
	        System.out.println(inputString);
	        String result = detokenizationUtil.deTokenize(tokens);
	        System.out.println(result);
	        return result;

     
	}


	private void redactTokens(String filepath, String[] tokens) {
        try {
			InputStream inputStreamNameFinder = getClass().getResourceAsStream(filepath);
			TokenNameFinderModel model;
			model = new TokenNameFinderModel(inputStreamNameFinder);
			NameFinderME nameFinderME = new NameFinderME(model);
			List<Span> spans = Arrays.asList(nameFinderME.find(tokens));
			for(Span span: spans) {
				for(int i = span.getStart(); i < span.getEnd(); i++)
				tokens[i] = "XXXXX";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	
}
