
//
package gov.nih.nlm.nls.trie_ner;

import java.util.List;
import java.util.ArrayList;
import gov.nih.nlm.nls.tools.TokenBasedReferenceTrie;
import gov.nih.nlm.nls.metamap.prefix.Tokenize;
import gov.nih.nlm.nls.metamap.prefix.Token;
import gov.nih.nlm.nls.metamap.prefix.PosToken;

/**
 *
 */

public class TokenTrieNER {
  int maxTokenScan = 30;

  public List<Result> maximalRightScanIterative(TokenBasedReferenceTrie trie, 
					  List<Token> inputTokenlist) {
    List<Token> tokenlist = inputTokenlist.subList(0, Math.min(maxTokenScan, inputTokenlist.size()));
    List<Result> resultList = new ArrayList<Result>();
    /*
      check term using expanding window of tokens expanding to right.
     */
    for (int n = 1; n < Math.min(inputTokenlist.size(), maxTokenScan); n++) {
      List<Token> candidate = inputTokenlist.subList(0,n);
      if (trie.has(candidate)) {
	if (trie.hasReference(candidate) != null) {
	  resultList.add(new Result(Tokenize.getTextFromTokenList(candidate),
				    new Span(((PosToken)candidate.get(0)).getPosition(),
					     ((PosToken)candidate.get(0)).getPosition() + 
					     Tokenize.getTextFromTokenList(candidate).length()),
				    trie.hasReference(candidate)));
	} else {
	  resultList.add(new Result(Tokenize.getTextFromTokenList(candidate),
				    new Span(((PosToken)candidate.get(0)).getPosition(),
					     ((PosToken)candidate.get(0)).getPosition() + 
					     Tokenize.getTextFromTokenList(candidate).length())));
	}
      }
    }
    return resultList;
  }

  public List<Result> tagText(TokenBasedReferenceTrie trie, String inputText) {
    List<Token> tokenlist = Tokenize.mmPosTokenize(inputText, 0);
    List<Result> resultList = new ArrayList<Result>();
    for (int idx = 0; idx< tokenlist.size(); idx++) {
      // System.out.println("prefix: " + tokenlist.subList(idx, idx+1));
      if (trie.hasPrefix(tokenlist.subList(idx, idx+1))) {
	resultList.addAll(maximalRightScanIterative(trie, tokenlist.subList(idx, tokenlist.size())));
      }
    }
    return resultList;
  }

  public static void main(String[] args) {
    TokenBasedReferenceTrie t = new TokenBasedReferenceTrie();
    t.insert(Tokenize.mmPosTokenize("d(2) dopamine receptor", 0), new Integer(1813));
    t.insert(Tokenize.mmPosTokenize("d2dr", 0), new Integer(1813));
    t.insert(Tokenize.mmPosTokenize("d2r", 0), new Integer(33007));
    t.insert(Tokenize.mmPosTokenize("Fas antigen", 0), new Integer(355));
    t.insert(Tokenize.mmPosTokenize("fas antigen", 0), new Integer(355));
    t.insert(Tokenize.mmPosTokenize("Fas ligand",  0), new Integer(356));
    t.insert(Tokenize.mmPosTokenize("fas ligand",  0), new Integer(356));
    t.insert(Tokenize.mmPosTokenize("d(2) dopamine receptor", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("d2dr", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("d2r", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor", 0), new Integer(99000));
    t.insert(Tokenize.mmPosTokenize("dopamine d1 receptor", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine d2 receptor", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine d3 receptor", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine d4 receptor", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine d5 receptor", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine and camp-regulated neuronal phosphoprotein 32", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine beta-hydroxylase (dopamine beta-monooxygenase)", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine beta-hydroxylase", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine d2 receptor", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor d1 interacting protein", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor d1", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor d1b", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor d2 isoform", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor d2", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor d3", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor d4", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor d5 pseudogene 1", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor d5 pseudogene 2", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor d5", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor d2 isoform", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor d2", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor interacting protein 1", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor interacting protein 2", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor interacting protein 3", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor interacting protein 4", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor interacting protein 5", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor interacting protein", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor regulating factor", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor-interacting protein 1", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor-interacting protein 3", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor-interacting protein 5", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine receptor-interacting protein of 78 kda", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine responsive protein", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine-oxygenase", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine-responsive gene 1 protein", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("dopamine-specific sulfotransferase", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("drd2", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("seven transmembrane helix receptor", 0), new Integer(0));
    t.insert(Tokenize.mmPosTokenize("neurotrophic factor", 0), new Integer(99001));
    t.insert(Tokenize.mmPosTokenize("brain-derived neurotrophic factor", 0), new Integer(99002));

    TokenTrieNER ner = new TokenTrieNER();

    List<String> doclist = new ArrayList<String>();
    doclist.add("We tested for the presence of association between clinical features and polymorphisms in the genes for the serotonin 2A receptor (HT2A), dopamine receptor types 2 and 4, dopamine transporter (SLC6A3), and brain-derived neurotrophic factor (brain-derived neurotrophic factor). |We tested for the presence of association between clinical features and polymorphisms in the genes for the serotonin 2A receptor (HT2A), dopamine receptor types 2 and 4, dopamine transporter (SLC6A3), and brain-derived neurotrophic factor (BDNF).");
    doclist.add("Cytotoxicity of Fas ligand against lymphoma cells with radiation-induced Fas antigen.|Cytotoxicity of Fas ligand against lymphoma cells with radiation-induced Fas antigen.");
    System.out.println("-----------------------------");
    for (String text: doclist) {
      List<Result> resultList = ner.tagText(t, text);
      System.out.println("text: " + text);
      System.out.println("result: ");
      for (Result result: resultList) {
	System.out.println("  " + result);
      }
      System.out.println("-----------------------------");
    }

  }
}
