package com.data.redact.service;

import opennlp.tools.tokenize.DetokenizationDictionary;
import opennlp.tools.tokenize.DetokenizationDictionary.Operation;
import opennlp.tools.tokenize.Detokenizer;
import opennlp.tools.tokenize.DictionaryDetokenizer;

public class DetokenizationUtil {

	public String deTokenize(String[] inputtokens) {
	    String tokens[] = new String[] { ".", "!", "(", ")", "\"", "-","'" };
	    Operation operations[] = new Operation[] { Operation.MOVE_LEFT, Operation.MOVE_LEFT, Operation.MOVE_RIGHT, Operation.MOVE_LEFT, Operation.RIGHT_LEFT_MATCHING, Operation.MOVE_BOTH,Operation.MOVE_BOTH };
	    DetokenizationDictionary dict = new DetokenizationDictionary(tokens, operations);
	    Detokenizer detokenizer = new DictionaryDetokenizer(dict);
	    String detokenizedString = detokenizer.detokenize(inputtokens, null);
	    return detokenizedString;

	}
	
}
