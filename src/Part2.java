import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import org.lemurproject.kstem.KrovetzStemmer;




public class Part2 {

	final String PATH = "./invertedIdx.txt"; //location where invertedIndex file will be stored
	/*
	 * method takes 2 arguments,  posting lists of term1 and term2.
	 * intersects the two posting lists and return the common docIds in an ArrayList of Integer type
	 */

	public ArrayList<Integer> intersect(ArrayList<Integer> p1, ArrayList<Integer> p2) {

		ArrayList<Integer>  result = new ArrayList<>();

		int i =0, j=0;
		int p1Len = p1.size();
		int p2Len = p2.size();

		while(i < p1Len && j <  p2Len) {
			if(p1.get(i) == p2.get(j)) {
				int val = p1.get(i);
				result.add(val);
				i++;
				j++;
			}else if(p1.get(i) < p2.get(j)) {
				i++;	
			}else {
				j++;
			}
		}
		return result;
	}

	
	public ArrayList<Integer> positionalIntersect(String term1, String term2, int k, Map<String, ArrayList<Part1.Document>> dict) {
		
		ArrayList<Part1.Document> docList1 = dict.get(term1);
		ArrayList<Part1.Document> docList2 = dict.get(term2);
		
	//	int[] docIdList1 = new int[docList1.size()];
	//	int[] docIdList2 = new int[docList2.size()];	
		
	
		int dl1 = 0,dl2 =0;

		ArrayList<Integer> result = new ArrayList<>();
		
		while(dl1 < docList1.size() && dl2 < docList2.size()) {
			
			if(docList1.get(dl1).getDocId() == docList2.get(dl2).getDocId()){
				
				int docId = docList1.get(dl1).getDocId();
				ArrayList<Integer> pl1 = docList1.get(dl1).getPositionList();
				ArrayList<Integer> pl2 = docList2.get(dl2).getPositionList();
				
				System.out.print("--DOCID: " + docList1.get(dl1).getDocId());
				System.out.print(docList1.get(dl1).getTerm() + " ---  " + Arrays.toString(pl1.toArray()));
				System.out.println(docList2.get(dl2).getTerm() + "------>   " + Arrays.toString(pl2.toArray()));
				
				
				int i =0 , j=0;
				while(i < pl1.size() && j <pl2.size()) {
					
					int offset = pl1.get(i) - pl2.get(j);
					System.out.println("OFFSET: " + offset);
					
					if(offset<0 && offset>=- (k+1) ) {
						//to print out the positions
						result.add(pl1.get(i));
						result.add(pl2.get(j));				
						System.out.println("\nRESULT-->" + "  FOR DOCUMENT NUM: " + docList1.get(dl1).getDocId()  +  "--->"+ Arrays.toString(result.toArray()) + "\n");
						
					//	result.add(docId);
						break;
					}else if(offset > 0){
						j++;
					}else {
						i++;
					}
							
				}	
				
				dl1++; 
				dl2++;
			}else if(dl1<dl2) {
				dl1++;
			}else {
				dl2++;
			}
			
		}
		System.out.println("\nRESULT --->"+ Arrays.toString(result.toArray()) + "\n");
		
		return result;
		
	}
	

	/*
	 * takes two arguments - query and Dictionary
	 * preProcess the query(tokenization, normalization...)
	 * invokes method "intersect" for intersection
	 * 
	 */
	
	
	public void queryEvaluation(String query, Map<String, ArrayList<Part1.Document>> dictionary) throws IOException {


		//preProcessing query
		String[] splitStr = query.split(" ");

		for(int i =0 ; i< splitStr.length; i++) {
			splitStr[i] = splitStr[i].toLowerCase();
		}
		;
		if(splitStr[1].equals("and")) {
			KrovetzStemmer kroStemmer = new KrovetzStemmer();

			String term1 = kroStemmer.stem(splitStr[0]);
			String term2 = kroStemmer.stem(splitStr[2]);

			//Intersection
			System.out.println(query);
		//	Part2 part2 = new Part2();
			this.positionalIntersect(term1, term2, 2, dictionary);
			
		//	ArrayList<Integer> p1 = dictionary.get(term1);
		//	ArrayList<Integer> p2 = dictionary.get(term2);	

		//	Part2 part2 = new Part2();
		//	ArrayList<Integer>  result = part2.intersect(p1, p2);

//			if(result.isEmpty()) {
//				System.out.println(query + ": No common Docid");
//			}else {
//				System.out.println("Result : " + result );
//				System.out.println("");
//
//				FileWriter fileWriter = new FileWriter("./result.txt", true);
//				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//				bufferedWriter.write(query + " : "+ result + "\n");
//				bufferedWriter.close();
//			}
		}else {

			System.out.println("Cannot process query for " + splitStr[1] + "operator");
		}

	}




	/*
	 * takes 1 argument- Dictionary
	 * create and write the Dictionary to a text file , "invertedIdx" in the current directory
	 */

	public void writeDictionaryToFile( Map<String, ArrayList<Part1.Document>> dictionary ) throws IOException {

		//saving Dictionary to a file
		FileWriter fstream; 
		BufferedWriter out; 

		fstream = new FileWriter(PATH); 
		out = new BufferedWriter(fstream); 

		for (Map.Entry<String, ArrayList<Part1.Document>> entry : dictionary.entrySet())
		{
			// System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
			ArrayList<Part1.Document> docArrList = entry.getValue();
		//	ArrayList<Integer> intArrList = new ArrayList<>();

			
			System.out.print("[" + entry.getKey()+"]--> ");
			
			String documentList = "";

			for(Part1.Document doc : docArrList) {
				System.out.print( doc.getDocId() + Arrays.toString(doc.getPositionList().toArray()));
				
				documentList += "{" + doc.getDocId() + ","+ doc.getPositionList().size() + "}"+ Arrays.toString(doc.getPositionList().toArray());
			}
			
			System.out.println();
			
			out.write(entry.getKey() + "," + docArrList.size() + documentList  + "\n"); 
		} 
		out.close(); 
	}



	/*
	 * parse InvertedIndex from a file "invertedIdx." into the Dictionary.
	 * returns map with term and posting list
	 */
	public Map<String, ArrayList<Part1.Document>> loadInvertedIdxFromFile() throws IOException {

		Map<String, ArrayList<Part1.Document>> newDict = new TreeMap<>(); 

		File file =  new File(PATH); 
		BufferedReader br = new BufferedReader(new FileReader(file));		
		String st;
	
		
		while ((st = br.readLine()) != null) {
			
			System.out.println("LINE : " + st);
			
			int termIndex = st.indexOf(",");
			//System.out.println("TERM INDEX:  " + ":" + termIndex);
			
			String term = st.substring(0, st.indexOf(","));
			System.out.println("TERM: " + term);
			newDict.put(term, new ArrayList<Part1.Document>());
			
			//DF
			String subStr = st.substring(termIndex+1);
			System.out.println("Substring 1 --->  " + subStr);
			
			//System.out.println("DF INDEX:  " + ":" + subStr.indexOf(","));
			
			boolean flag = true;
			while(flag) {
				
				int curlyIdx1 = subStr.indexOf("{");
				int curlyIdx2 = subStr.indexOf("}");
				
				String postingStr = subStr.substring(curlyIdx1+1, curlyIdx2);
				System.out.println("curlyStr --->  " + postingStr);
				
				String[] splitStr = postingStr.split(",");
				int docId =	Integer.parseInt(splitStr[0]);
				int termFreq = Integer.parseInt(splitStr[1]);
				
				System.out.print("docId --->  " + docId);
				System.out.println(",  termFreq --->  " + termFreq);
				
				
			//	ArrayList<Integer> docPositionList = new ArrayList<>();
				Part1 part1 = new Part1();
				Part1.Document document =  part1.new Document(term, docId, termFreq);
				//document(term, docId,termFreq);
				newDict.get(term).add(document);
				
				
				 
				int sqIndex1 = subStr.indexOf("[");
				int sqIndex2 = subStr.indexOf("]");
				
		
				postingStr = subStr.substring(sqIndex1+1, sqIndex2);
				System.out.println("sqStr --->  " + postingStr);
				
				splitStr = postingStr.split(", ");
				
				for(String str : splitStr) {
				
					System.out.println("postingStr +++  " + str);
					document.getPositionList().add(Integer.parseInt(str));
				}				
				subStr = subStr.substring(sqIndex2+1);
				System.out.println("LAST SUBSTRING --->  " + subStr);
			
				if(subStr.isEmpty()) {
					flag= false;
				}
				
			}
		
			System.out.println("\n------NEXT-------\n");
						
		}
		
		
		br.close();
		
		//++++
		System.out.println("\n-----DICTIONARY-------\n");
		
		for (Map.Entry<String, ArrayList<Part1.Document>> entry : newDict.entrySet())
		{

			ArrayList<Part1.Document> docArrList = entry.getValue();
		
			//System.out.print("[" + entry.getKey()+"]--> ");
			
			String documentList = "";

			for(Part1.Document doc : docArrList) {
				//System.out.print( doc.getDocId() + Arrays.toString(doc.getPositionList().toArray()));
				
				documentList += "{" + doc.getDocId() + ","+ doc.getPositionList().size() + "}"+ Arrays.toString(doc.getPositionList().toArray());
			}
			
			System.out.println();
			
			System.out.println(entry.getKey() + "," + docArrList.size() + documentList  + "\n"); 
		} 
		//+++
	

		return newDict;
	}



	/*
	 * invoke part1 to create Inverted Index and then save it to a file.
	 * Load the inverted index from the file into a dictionary, newDict.
	 * use the newDict to evaluate the queries by invoking part3 intersect function.
	 */
	public static void main(String[] args) throws Exception 
	{ 
		Part2 part2 = new Part2();

		File docfile =  new File("./documents.txt");
		String query1 = "asus AND google";
		String query2 = "bad AND screen";
		String query3 = "great AND tablet";
		
		
		String test = "this AND Most";
		

		Part1 part1 = new Part1();

		//part2 creates an inverted index and returns a dictionary
		Map<String, ArrayList<Part1.Document>> dictionary = part1.createInvertedIndex(docfile);
		part2.writeDictionaryToFile(dictionary);// a file is created with a name "invertedIdx.txt"
		Map<String, ArrayList<Part1.Document>> newDict = part2.loadInvertedIdxFromFile(); //load the invertedIndx from file "invertedIdx.txt" into a Dictionary 

	
		
		
		
		
		
		part2.queryEvaluation(test, newDict);
		part2.queryEvaluation(query2, newDict);
//		part2.queryEvaluation(query2, newDict);
//		part2.queryEvaluation(query3, newDict);
		
		
		

	} 






}
