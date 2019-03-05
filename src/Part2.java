import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.lemurproject.kstem.KrovetzStemmer;




public class Part2 {

	final String PATH = "./invertedIdx.txt"; //location where invertedIndex file will be stored
	static Part1 part1;
	/*
	 * method takes 2 arguments,  posting lists of term1 and term2.
	 * intersects the two posting lists and return the common docIds in an ArrayList of Integer type
	 */

	public ArrayList<Integer> positionalIntersect(String term1, String term2, int k, Map<String, ArrayList<Part1.Document>> dict) {

		ArrayList<Part1.Document> docList1 = dict.get(term1);
		ArrayList<Part1.Document> docList2 = dict.get(term2);
		ArrayList<Integer> result1 = new ArrayList<>();
		ArrayList<Integer> result = new ArrayList<>();

		int dl1 = 0,dl2 =0;

		while(dl1 < docList1.size() && dl2 < docList2.size()) {

			if(docList1.get(dl1).getDocId() == docList2.get(dl2).getDocId()){	

				int docId = docList1.get(dl1).getDocId();
				ArrayList<Integer> pl1 = docList1.get(dl1).getPositionList();
				ArrayList<Integer> pl2 = docList2.get(dl2).getPositionList();

				//					System.out.print("--DOCID: " + docList1.get(dl1).getDocId());
				//					System.out.print(docList1.get(dl1).getTerm() + " ---  " + Arrays.toString(pl1.toArray()));
				//					System.out.println(docList2.get(dl2).getTerm() + "------>   " + Arrays.toString(pl2.toArray()));

				int i =0 , j=0;
				while(i < pl1.size() && j <pl2.size()) {
					int offset = pl1.get(i) - pl2.get(j);
					//	System.out.println("OFFSET: " + offset);

					if(offset<0 && offset>=- (k+1) ) {
						//to print out the positions
						result1.add(pl1.get(i));
						result1.add(pl2.get(j));	
						result.add(docId);
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
		System.out.println("\nRESULT --->"+ term1 + ","+ term2+ ","+ Arrays.toString(result.toArray()) + "\n");

		return result;

	}

//	public ArrayList<Integer> intersectList(ArrayList<Integer> p1, ArrayList<Integer> p2) {
//
//		ArrayList<Integer>  result = new ArrayList<>();
//
//		int i =0, j=0;
//		int p1Len = p1.size();
//		int p2Len = p2.size();
//
//		while(i < p1Len && j <  p2Len) {
//			if(p1.get(i) == p2.get(j)) {
//				int val = p1.get(i);
//				result.add(val);
//				i++;
//				j++;
//			}else if(p1.get(i) < p2.get(j)) {
//				i++;	
//			}else {
//				j++;
//			}
//		}
//		return result;
//	}
	
	
	public ArrayList<Integer> intersectList(ArrayList<Integer> p1, ArrayList<Integer> p2) {

		
		TreeSet<Integer> resultSet=  new TreeSet<>();
		resultSet.addAll(p1);
		resultSet.addAll(p2);
		
		ArrayList<Integer>  result = new ArrayList<>(resultSet);

		return result;
	}

	
	public ArrayList<Integer>  intersect(String term1, String term2, Map<String, ArrayList<Part1.Document>> dict){

	

		ArrayList<Part1.Document> docList1 = dict.get(term1);
		ArrayList<Part1.Document> docList2 = dict.get(term2);
	
		
		TreeSet<Integer> set1=  new TreeSet<>();
		//TreeSet<Integer> set2=  new TreeSet<>();
		
		
		for(Part1.Document i : docList1) {
			set1.add(i.getDocId());
		}
		
		for(Part1.Document i : docList2) {
			set1.add(i.getDocId());
		}

		
		System.out.println("INTERSECTION: TERMS: " +  term1 +",  "+ term2 );
		
		for(Integer i : set1) {
			System.out.println(i);
		}
		
		
		System.out.println("INTERSECTION: ENDDD");
		
		ArrayList<Integer> result = new ArrayList<>(set1);
		
		

//		int dl1 = 0,dl2 =0;
//
//		while(dl1 < docList1.size() && dl2 < docList2.size()) {
//
//			if(docList1.get(dl1).getDocId() == docList2.get(dl2).getDocId()){	
//				int val = docList1.get(dl1).getDocId();
//				result.add(val);
//				dl1++;
//				dl2++;
//			}else if(docList1.get(dl1).getDocId() < docList2.get(dl2).getDocId()) {
//				dl1++;	
//			}else {
//				dl2++;
//			}
//
//		}

		return result;
	}
	
	
	
	
	
	
//	public ArrayList<Integer>  intersect(String term1, String term2, Map<String, ArrayList<Part1.Document>> dict){
//
//		ArrayList<Integer> result = new ArrayList<>();
//
//		ArrayList<Part1.Document> docList1 = dict.get(term1);
//		ArrayList<Part1.Document> docList2 = dict.get(term2);
//
//
//		int dl1 = 0,dl2 =0;
//
//		while(dl1 < docList1.size() && dl2 < docList2.size()) {
//
//			if(docList1.get(dl1).getDocId() == docList2.get(dl2).getDocId()){	
//				int val = docList1.get(dl1).getDocId();
//				result.add(val);
//				dl1++;
//				dl2++;
//			}else if(docList1.get(dl1).getDocId() < docList2.get(dl2).getDocId()) {
//				dl1++;	
//			}else {
//				dl2++;
//			}
//
//		}
//
//		return result;
//	}


	public class DocScore implements Comparable<DocScore> {
		Integer docId;
		double score;

		public DocScore(int docId, double score) {
			this.docId = docId;
			this.score = score;
		}


		public int getDocId() {
			return docId;
		}
		public void setDocId(int docId) {
			this.docId = docId;
		}
		public double getScore() {
			return score;
		}
		public void setScore(double score) {
			this.score = score;
		}

		@Override
		public int compareTo(DocScore dscore) {
			if(this.score < dscore.score) {
				return 1;
			} else if(this.score > dscore.score){
				return -1;
			} else {
				return 0;
			}

		}

	}		



	public ArrayList<DocScore> score(String query, ArrayList<Integer> list , Map<String, ArrayList<Part1.Document>> dictionary ) {
				
		double score=0;
		KrovetzStemmer kroStemmer = new KrovetzStemmer();

		int N = part1.getDocNum();
		//N = 100;
		System.out.println("No of Documents: " + N);

		ArrayList<DocScore> rankedList = new ArrayList<>(); 

		query = query.replaceAll("[0-9]", "");
		query = query.replaceAll("[()]", "");

		
		String[] splitStr = query.split("\\W+");
		for(int i =0 ; i< splitStr.length; i++) {
			splitStr[i] = kroStemmer.stem(splitStr[i].toLowerCase());
		}

		
		for(int i=0; i < list.size(); i++) {

			int docId = list.get(i);

			for(int j=0; j < splitStr.length; j++) {
				ArrayList<Part1.Document> docList = dictionary.get(splitStr[j]);
				for(int k=0; k < docList.size(); k++) {

					int df =docList.size();
					if(docList.get(k).getDocId() == docId) {

						int tf = docList.get(k).getPositionList().size();
						//int tf = docList.get(k).getTermFreq();
						double l = Math.log10(N/df);
						score += (1+ Math.log10(tf)) * Math.log10(N/df);
						System.out.println("docId: "+ docId + "  ,"+  splitStr[j]  + "  , tf: "+ tf + " ,score:"+  score  + "  DF: " + df);
					} 
				}

			}
			rankedList.add(new DocScore(docId,score)); 
			
			
		}


		System.out.println("RANKED RSULT");

		for(int i=0; i < rankedList.size(); i++) {
			System.out.println(rankedList.get(i).getDocId() + " ,score: " + rankedList.get(i).getDocId());

		}


		Collections.sort(rankedList);
		System.out.println("SORTED RANKED RSULT");

		for(int i=0; i < rankedList.size(); i++) {
			System.out.println(rankedList.get(i).getDocId());

		}
		return rankedList;
	}



	public ArrayList<Integer> queryEvaluation(String query, Map<String, ArrayList<Part1.Document>> dictionary) throws IOException {		
		
		String OrigQuery = query;
		KrovetzStemmer kroStemmer = new KrovetzStemmer();
		ArrayList<Integer>  result = new ArrayList<>();
		ArrayList<Integer> ProximityResult = new ArrayList<>();
		ArrayList<Integer> FTextResult = new ArrayList<>();
		
		boolean flagProximity = false;


		for(int i =0; i< query.length(); i++) {

			if(query.charAt(0) == ' ') {
				query = query.substring(1);	
			}

			int idx1 = query.indexOf("(");


			if(idx1==1) {
				int k = Character.getNumericValue(query.charAt(0));
				int idx2 = query.indexOf(")");

				String PosQuery = query.substring(idx1+1,idx2);

				String[] splitStr = PosQuery.split(" ");
				String term1 = kroStemmer.stem(splitStr[0].toLowerCase());
				String term2 = kroStemmer.stem(splitStr[1].toLowerCase());
				String term3 = kroStemmer.stem("fixed");
				

				ProximityResult = this.positionalIntersect(term1, term2, k, dictionary);
				
				if(!ProximityResult.isEmpty() && result.isEmpty()) {
					result.addAll(ProximityResult);
				}else {
					result = this.intersectList(result, ProximityResult);
				}
					
				flagProximity = true;
				if(idx2 == query.length()-2) {
					break;
				}
				query = query.substring(idx2+1);
				
			}else {
				if(idx1>1) {
					int idx = query.indexOf("(");

					String FtxtQuery = query.substring(0,idx-1);
					String[] splitStr = FtxtQuery.split(" ");
					int loop =  splitStr.length/2;


					for(int j=0 ; j< loop; j++) {
						String term1 = kroStemmer.stem(splitStr[j].toLowerCase());
						String term2 = kroStemmer.stem(splitStr[j+1].toLowerCase());

						ArrayList<Integer> list = this.intersect(term1, term2, dictionary);
						if(!FTextResult.isEmpty()) {
							FTextResult = this.intersectList(FTextResult, list);
						}else {
							FTextResult.addAll(list);
						}
					}

					if((splitStr.length)%2 != 0) {
						String term = kroStemmer.stem(splitStr[splitStr.length-1].toLowerCase());
						ArrayList<Part1.Document> docList = dictionary.get(term);
						ArrayList<Integer> docNumlist = new ArrayList<>();

						for( int d =0; d < docList.size(); d++) {

							docNumlist.add(docList.get(d).getDocId());
						}

						if(!FTextResult.isEmpty()) {
							FTextResult = this.intersectList(FTextResult, docNumlist);
						}else {
							FTextResult.addAll(docNumlist);
						}	
					}

					query = query.substring(idx-2);
					
					
					if(!FTextResult.isEmpty() && !flagProximity && result.isEmpty()) {
						result.addAll(FTextResult);
					}else if(!FTextResult.isEmpty() && !result.isEmpty()) {
						result = this.intersectList(result, FTextResult);
					}
					
				}else {

					String[] splitStr = query.split(" ");

					int loop =  splitStr.length/2;
					int j = 0;
					int count =0;
					
					while(j< loop) {
						
						String term1 = kroStemmer.stem(splitStr[count].toLowerCase());
						String term2 = kroStemmer.stem(splitStr[count+1].toLowerCase());

						ArrayList<Integer> list = this.intersect(term1, term2, dictionary);
						if(!FTextResult.isEmpty()) {
							FTextResult = this.intersectList(FTextResult, list);
						}else {
							FTextResult.addAll(list);
						}
						count = count+2;
						j++;
						
					}

//					for(int j=0 ; j< loop; j++) {
//						String term1 = kroStemmer.stem(splitStr[j].toLowerCase());
//						String term2 = kroStemmer.stem(splitStr[++j].toLowerCase());
//
//						ArrayList<Integer> list = this.intersect(term1, term2, dictionary);
//						if(!FTextResult.isEmpty()) {
//							FTextResult = this.intersectList(FTextResult, list);
//						}else {
//							FTextResult.addAll(list);
//						}
//					}

					if((splitStr.length)%2 != 0) {
						String term = kroStemmer.stem(splitStr[splitStr.length-1].toLowerCase());
						ArrayList<Part1.Document> docList = dictionary.get(term);
						ArrayList<Integer> docNumlist = new ArrayList<>();

						for( int d =0; d < docList.size(); d++) {

							docNumlist.add(docList.get(d).getDocId());
						}

						if(!FTextResult.isEmpty()) {
							FTextResult = this.intersectList(FTextResult, docNumlist);
						}else {
							FTextResult.addAll(docNumlist);
						}	
					}
					
					//if FText is empty, don't do anything
					if(!FTextResult.isEmpty() && !flagProximity  && result.isEmpty()) {
						result.addAll(FTextResult);
					}else if(!FTextResult.isEmpty() && !result.isEmpty()) {
						result = this.intersectList(result, FTextResult);
					}
					
					break;
				}
			}
		}

		System.out.println("\n *************RESULT --->"+ Arrays.toString(result.toArray()) + "\n");
		if(result.size()>1) {
			this.score(OrigQuery, result, dictionary);
		}
		
		return result;
	}



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

				Part1.Document document =  part1.new Document(term, docId, termFreq);
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


	public static void main(String[] args) throws Exception 
	{ 
		Part2 part2 = new Part2();
		part1 = new Part1();

		File docfile =  new File("./documents.txt");
		String query1 ="nexus like love happy";
		String query2 = "asus repair";
		String query3 = "0(touch screen) fix repair";
		String query4 = "1(great tablet) 2(tablet fast)";
		String query5 = "tablet";
		


		//String test = "this AND Most";
		
		Map<String, ArrayList<Part1.Document>> dictionary = part1.createInvertedIndex(docfile);
		part2.writeDictionaryToFile(dictionary);// a file is created with a name "invertedIdx.txt"
		Map<String, ArrayList<Part1.Document>> newDict = part2.loadInvertedIdxFromFile(); //load the invertedIndx from file "invertedIdx.txt" into a Dictionary 


		//part2.queryEvaluation("this most bad 0(bad screen)", newDict);
		System.out.println("QUERY EVAL1:" +  query1);
		
		
		part2.queryEvaluation(query1, newDict);
		System.out.println("QUERY EVAL2:" + query2);
		part2.queryEvaluation(query2, newDict);
		System.out.println("QUERY EVAL3:" + query3);
		part2.queryEvaluation(query3, newDict);
		
		System.out.println("QUERY EVAL4:" + query4 );
		part2.queryEvaluation(query4, newDict); //not ok 2 intersection 10 gives null  
		
		System.out.println("QUERY EVAL5:" + query5); //ok 2.3.4.6.9.10
		part2.queryEvaluation(query5, newDict);
	} 






}
