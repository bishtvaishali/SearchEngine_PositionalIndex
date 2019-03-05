import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import org.lemurproject.kstem.KrovetzStemmer;


public class Part1 {

	
	private  int noOfDocs = 0;
	
	public int getDocNum() {
		return noOfDocs;
	}

	
	/*
	 * method is used to created Inverted Index. It takes a text file as a parameter.
	 * It parses the file step by step(tokenization, normalization, stemming, stop words removal) 
	 * and stores the terms, Doc Freq and Posting list in a dictionary(TreeMap). 
	 */
	public Map<String, ArrayList<Document>> createInvertedIndex (File file) throws IOException {

		Map<String, ArrayList<Document>> dictionary = new TreeMap<>(); 	

		//we create a uniqueDocuments for every document and check for duplicate words.

		HashSet<String> stopWords = new HashSet<>();
		stopWords.add("the");
		stopWords.add("is");
		stopWords.add("at");
		stopWords.add("of");
		stopWords.add("on");
		stopWords.add("and");
		stopWords.add("a");

		KrovetzStemmer stemmer = new KrovetzStemmer();

		BufferedReader br = new BufferedReader(new FileReader(file));
		String[] splitStr;
		String st;
		int docId = 0;



		while ((st = br.readLine()) != null) {
			if(st.contains("</")) {
				//do nothing
			}else if(st.contains("<")) {
				noOfDocs++;
				docId = Integer.parseInt(st.substring(5, st.length() - 1));
			}else {		
				splitStr = st.split("\\W+");

				for(int i =0 ; i< splitStr.length; i++) {
					splitStr[i] = splitStr[i].toLowerCase();
				}
				//System.out.println(Arrays.toString(splitStr));

				int wordPosition = 0;
				for (String word: splitStr) {
					String stemmedWord = stemmer.stem(word);

					//	System.out.println("\nWORD POSITION: [" + stemmedWord + "] POS:" + wordPosition );

					if(!stopWords.contains(stemmedWord) && !stemmedWord.equals("")) {
						//System.out.println("stemmedWord ->"+stemmedWord+"--docId==>"+docId);
						if(!dictionary.containsKey(stemmedWord)) {
							dictionary.put(stemmedWord, new ArrayList<Document>()); 
						}


						ArrayList<Document> docList = dictionary.get(stemmedWord);
						if(docList.isEmpty()) {
							docList.add(new Document(stemmedWord, docId));
						}

						boolean notFound = true;

						for (Document doc : docList) {
							if(doc.getDocId() == docId) {
								doc.getPositionList().add(wordPosition);
								//System.out.println("PL--:" + Arrays.toString(doc.getPositionList().toArray()));
								notFound = false;
							}	
						}

						if(notFound) {
							docList.add(new Document(stemmedWord,docId));
							for (Document doc : docList) {
								if(doc.getDocId() == docId) {	
									doc.getPositionList().add(wordPosition);
									//System.out.println("PL--:" + Arrays.toString(doc.getPositionList().toArray()));
									notFound = false;
								}	
							}

						}
					}
					//wordPosition += word.length() + 1;
					wordPosition++;
				}

			}
		}

		br.close();

		return dictionary;

	}	


	/*
	 * class sorts the terms alphabetically and by docId.
	 * sorting is done by implementing comparable.
	 */

	public class Document implements Comparable<Document>{

		private String term;
		private Integer docId;
		private Integer termFreq;
		private ArrayList<Integer> positionList;


		
		
		public Document(String word, int docId) {
			this.term = word;
			this.docId = docId;
			this.positionList = new ArrayList<Integer>();
		}
		
		public Document(String word, int docId, int termFreq) {
			this.term = word;
			this.docId = docId;
			this.termFreq = termFreq;
			this.positionList = new ArrayList<Integer>();
		}

		public Integer getTermFreq() {
			return termFreq;
		}
		public void setTermFreq(Integer termFreq) {
			this.termFreq = termFreq;
		}
		public ArrayList<Integer> getPositionList() {
			return positionList;
		}

		public String getTerm() {
			return term;
		}

		public void setTerm(String term) {
			this.term = term;
		}
		public Integer getDocId() {
			return docId;
		}
		public void setDocId(Integer docId) {
			this.docId = docId;
		}

		//for sorting terms alphabetically and by docId
		@Override
		public int compareTo(Document o) {
			if(this.term.equals(o.term)) {
				return this.docId - o.docId;
			} else {
				return this.term.compareTo(o.term);
			}
		}

	}

}
