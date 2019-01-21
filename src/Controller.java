import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Controller extends ReadExcelFile{
	
	ReadExcelFile readIntData= new ReadExcelFile();
	String fileLocation="Scores.csv";
	ArrayList<String[]> dataInTheFile=new ArrayList<String[]>(readIntData.readCSV(fileLocation));
	
	//declaring global arraylist has the scores values in strings in one dimension
	// and declaring hashMaps to reduce number of scaning data every time we need to get support 
	List<List<String>> records = new ArrayList<>();
	
	Set<String> distnctScoresSet=new HashSet<String>();
	List<String> oneItemList=new ArrayList<String>();// has the same values of the oneItemSet map
	Map<String,Integer>oneItemMap=new HashMap<String,Integer>();
	
	List<List<String>> twoItemList=new ArrayList<List<String>>();
	Map<List<String>,Integer> twoItemMap=new HashMap<List<String>,Integer>();
	
	List<List<String>> threeItemList=new ArrayList<List<String>>();
	Map<List<String>,Integer> threeItemMap=new HashMap<List<String>,Integer>();
	
	List<String> stronglyCorrelatedList=new ArrayList<String>();


	Controller(){
		//constructor
		}	
	int minSupport;
	double minConfidence;
	//encapsulation for minimnum support and confidence variables.
	public void setMinSupport(int minSupp)
	{
		this.minSupport=minSupp;
	}
	public int getMinSupport()
	{
		return this.minSupport;
	}
	public void setConfidence(double d)
	{
		this.minConfidence=d;
	}
	public double getConfidence()
	{
		return this.minConfidence;
	}

	public List<List<String>> transformData(){
		//this function is to store the scores in an arraylist<String>.
				
			//1- create the needed variables and Scores_array_list	
			int shooting, horseRiding, swimming;
			
			//variables of the shooting scores
			String shRanking="shRanking", shTopLevel="shTopLevel" ,shSuperior="shSuperior";
			
			//variables of the horseRiding scores
			String hrRanking="hrRanking", hrTopLevel="hrTopLevel" ,hrSuperior="hrSuperior";
			
			//variables of the Swimming scores
			String swRanking="swRanking", swTopLevel="swTopLevel" ,swSuperior="swSuperior";
			// String variable for carrying the values of each line.
	        String currentLine="";
	        
	        
	        
	        
	        //	2-calling the readCSV function to get the data into allData arrayList<String[]>
	        
	        for(String[] currentArray :dataInTheFile)  
	        {  
	        //	3- convert allData(arrays of string) into one string for each line to calculate ranges.
	        	//each string has value in form like -> [11;22;33].
	        	currentLine=Arrays.toString(currentArray);
	        
	        //	4-assigning each part of the string (line)to its suitable integer variable.
	        shooting=Integer.parseInt(currentLine.substring(1,//skipping the '[' in the string.
	        		currentLine.indexOf(";")));//the length of the end location of shooting value in"[11;22;33]"
	        
	        horseRiding=Integer.parseInt(
	        		currentLine.substring(
	        				currentLine.indexOf(";")+1, //beginning of the horesRiding value in the string(+1 for skipping the first";" )
	        				currentLine.lastIndexOf(";")));//the length of the end of horesRiding value in the string.
	       
	        swimming=Integer.parseInt(
	        		currentLine.substring(currentLine.lastIndexOf(";")+1,//beginning of the swimming value in the string(+1 for skipping the second";" )
	        		currentLine.length()-1));//the length of the end of swimming value in the string.(-1 is for skipping the "]").
	        
	        /*	5- transform integers data into scores arraylist<String> based on given ranges.  
	         * Ranking : 65-74
	         * TopLevel: 75-84
	         * Superior: 85-100
	         */
	        // create ArrayList String buffer to carry the values of each line.
	        List<String> buffer=new ArrayList<String>();
	       
	        //if conditions for shooting variable to add the suitable scores
	       if(shooting >=65 && shooting <=74)
	    		buffer.add(shRanking);
	        else if(shooting >=75 && shooting <=84)
	        	buffer.add(shTopLevel);
	        else if(shooting >=85 && shooting <=100)
	        	buffer.add(shSuperior);
	        
	        //if conditions for horseRiding variable to add the suitable scores
	        
	       	if(horseRiding >=65 && horseRiding <=74)
	       		buffer.add(hrRanking);
	        else if(horseRiding >=75 && horseRiding <=84)
	        	buffer.add(hrTopLevel);
	        else if(horseRiding >=85 && horseRiding <=100)
	        	buffer.add(hrSuperior);
	        
	      //if conditions for Swimming variable to add the suitable scores
	        if(swimming >=65 && swimming <=74)
	        	buffer.add(swRanking);
	        else if(swimming >=75 && swimming <=84)
	        	buffer.add(swTopLevel);
	        else if(swimming >=85 && swimming <=100){
	        	buffer.add(swSuperior);
	        }
	        records.add(buffer);
	        }	//end of the For loop
	        
	        return records;
	}	//end of function transformData.

	//getFrequency of 1 items exists in a record in the all data. 
	public int getSupportCount(String score1, List<List<String>>rec)
	{
		transformData();
		int support =0;
		for(int i=0;i<rec.size();i++)
		{
			if(rec.get(i).contains(score1))
				support++;
		}
		return support;
	}
	
	//getFrequency of 2 items exists in a record in the all data. 
	public int getSupportCount(String score1 ,String score2 , List<List<String>>rec)
	{
		transformData();
		int support =0;
		for(int i=0;i<rec.size();i++){
			if( rec.get(i).contains(score1)&&
				rec.get(i).contains(score2))
				support++;
		}
		return support;
	}
	
	//getFrequency of 3 items exists in a record in the all data. 
	public int getSupportCount(String score1 ,String score2,String score3 ,List<List<String>>rec)
	{
		transformData();
		int support =0;
		for(int i=0;i<rec.size();i++){
			if( rec.get(i).contains(score1)&& 
				rec.get(i).contains(score2)&&
				rec.get(i).contains(score3))
				support++;
		}
		return support;
	}
	
	public int getSupportCount(List<String> arrListForSearch,List<List<String>>rec)
	{
		transformData();
		int support =0;
		for(int i=0;i<rec.size();i++)
		{
			int count=0;
			//outer loop to get the List<String> of each row
			for(int j=0;j< arrListForSearch.size();j++)
			{
				if( rec.get(i).contains(arrListForSearch.get(j)))
					count++;						
			}
			
// if each element in the arrListForSearch exists in the current row of the all records
			if(count==arrListForSearch.size())
				support++;			
		}
		return support;
	}
	
	//fill the  distnctValues
	public void setDistinctScoresSet(List<List<String>>rec)
	{
		transformData();
		String temp="";
		for(int i=0;i<rec.size();i++)
		{
			for(int j=0;j<rec.get(i).size();j++)
			{
				temp= rec.get(i).get(j);
				distnctScoresSet.add(temp);
			}
		}
		
		
	}	//end of function fillDistinctScoresSet
		
	
	// set the individual values that has or exceed the minimum support in oneItemSet
	public void setOneItemList(){
		setDistinctScoresSet(records);
		
		int support=0;
		String temp="";
		//Iterator <String>itr= new distnctScoresSet.iterator();
		for( String currentValue : distnctScoresSet)
		{
			temp=currentValue;
			support=getSupportCount(temp,records);
			
			if(support<minSupport)
				oneItemList.remove(temp);
			else if(support >= minSupport)
			{
				oneItemList.add(temp);
				oneItemMap.put(temp,support);
			}
		}//end of for loop
		
		//System.out.println(oneItemList);
		//System.out.println(oneItemMap.get("swTopLevel"));
		
	}//end of function setOneItemSet
	
	// set the pair of values that has or exceed the minimum support in oneItemSet
	public void setTwoItemList()
	{
		setOneItemList();
		int support=0;
		
		String temp1="",temp2="";
		for(int i=0;i<oneItemList.size();i++)
		{
			for(int j=i+1;j<oneItemList.size()-1;j++)
			{
				
				
				temp1=oneItemList.get(i);
				temp2=oneItemList.get(j);
				
				if(!(temp1.substring(0,2).equals(temp2.substring(0,2))))
				{	//to make sure that there is no equals elements in the 2-set
					List<String> buffer2item=new ArrayList<String>();
					buffer2item.add(temp1);
					buffer2item.add(temp2);
					
				
					support=getSupportCount(temp1,temp2,records);
					if(support>=minSupport)
					{
						twoItemList.add(buffer2item);
						twoItemMap.put(buffer2item,support);
					
					}
				}
				
			}
			  
		}//end of for loop
	}//end of function setTwoItemSet
	
		
	// set the pair of values that has or exceed the minimum support in oneItemSet
	public void setThreeItemList()
	{
			 
		setTwoItemList();
		int support=0;
		for(int r=0; r <twoItemList.size();r++) // to improve building threeItemList
		{
			int supForImprove=0;
			for(int c=0;c<twoItemList.get(r).size();c++)
			{
				supForImprove = getSupportCount(twoItemList.get(r).get(c), twoItemList);
				if(supForImprove < minSupport)
				{
					oneItemList.remove(twoItemList.get(r).get(c));
				}
			}
			
			
		}
		String temp1="",temp2="",temp3="";
		for(int i=0;i<oneItemList.size();i++)
		{
			for(int j=i+1;j<oneItemList.size()-1;j++)
			{
				for(int k=j+1;k<oneItemList.size()-2;k++)
				{
					temp1=oneItemList.get(i);
					temp2=oneItemList.get(j);
					temp3=oneItemList.get(k);
					if(	!(temp1.substring(0,2).equals(temp2.substring(0,2)))&&
						!(temp2.substring(0,2).equals(temp3.substring(0,2))))
					{	//to make sure that there is no equals elements in 3-set
						List<String> buffer3item=new ArrayList<String>();
						buffer3item.add(temp1);
						buffer3item.add(temp2);
						buffer3item.add(temp3);
						//to make sure that each item exceeds min_supp in the 2-itemSet
						support=getSupportCount(temp1,temp2,temp3,records);						
						if(support>=minSupport)
						{
							threeItemList.add(buffer3item);
							threeItemMap.put(buffer3item,support);			
						}
					}
				}				
			}
		}//end of for loop
			System.out.println(oneItemList.size());
			System.out.println(twoItemList.size());
			System.out.println(threeItemList.size());
		}//end of function setTwoItemSet
	// getFrequentSet
	public List<List<String>> getFrequentSet ()
	{
		
		setThreeItemList();// this function calls the other itemSet(one &two)
		
		if(oneItemList.size()==0)
		{
			String noFreq="There is no frequent set";
			System.out.println(noFreq);
			return null;
		}
		
		else if(twoItemList.size()==0)
		{
			List<List<String>> frequentList= new ArrayList<List<String>>();
			frequentList.add(oneItemList);
			return frequentList;
		}
		
		else if (threeItemList.size()==0)
		{
			List<List<String>> frequentList= new ArrayList<List<String>>(twoItemList);
			return frequentList;
		}
		// else 
		
		List<List<String>> frequentList= new ArrayList<List<String>>(threeItemList);
		return frequentList; 
	}	//end of function getFrequentSet.
	
	
public boolean calculateConfidence(int suppA,int supp_AuB)
{
	// confidence(A->B)= (support(A u B) )/ support(A)
	double currentConfidence=(double)supp_AuB/suppA;
	System.out.println("supp_AuB "+supp_AuB);
	System.out.println("suppA "+suppA);
	System.out.println("curr Conf is "+currentConfidence);
	System.out.println("minConfidence"+minConfidence);
	System.out.println("    ___    ");
	
	/* doubles numbers cant be compared as integers numbers so
	/we've to use built in function Double.compare 
	which returns 0 if the to parameters are equal
    and returns a +ve if first parameter is greater than second parameter
    */
	if(Double.compare(currentConfidence,minConfidence)>=0)
	{
		//System.out.println("el mafrod true");
		return true;
	}
	else
	{
		//System.out.println("el mafrod false");
		return false;
	}
}//end of function calculateConfidence.

public void swapArrayLists(List<String>a,List<String>b)
{
	List<String> temp=new ArrayList<String>(a);
	a.clear();
	a.addAll(b);
	b.clear();
	b.addAll(temp);
}
//getAllCombinations of the most Frequent Item Set
public	List<String> getStrongCorrelatedItemSet()
{
	// copy frequent item set to get all combinations.
	List<List<String>> freqSet= new ArrayList<List<String>>(getFrequentSet());
	// to carry the strings of the all results with strongly correlated items.
	
	
	
	// loop on the freqSet to get The combination and confidence of two items
	for(int row=0;row<freqSet.size();row++)
	{
		//this outer loop is for each line in the freqSet
		//in each confidence calculations there are A->B
		int suppForA=0 ;
		int suppForAuB=0;
		//calculating suppForAuB for the current row data
		
		suppForAuB=getSupportCount( freqSet.get(row),records );
				
		for(int col=0; col<freqSet.get(row).size();col++)
		{
			//this inner loop is for each element in the current row data in freqSet
			
			//to Add all data of each row in freqSet to List<String>B.
			List<String>B=new ArrayList<String>();
			B.addAll(freqSet.get(row));
			
			
			//to pick a current element and remove it from B to add it to A. 
			String a=freqSet.get(row).get(col);
			//System.out.println("a = "+ a);
			B.remove(a);
			//System.out.println("B = "+ B);
			List<String>A=new ArrayList<String>();
			A.add(a);
								
			//calculate suppForA for the current element
				suppForA=getSupportCount(A,records);
				//calculateConfidence(suppForA,suppForAuB)
			if(calculateConfidence(suppForA,suppForAuB))
			{
				String Astring=A.toString();
				String Bstring=B.toString();
				String status="";
				status+=Astring;
				status+="  - > ";
				status+=Bstring;
				status+="   is strongly Correlated ";
				
				stronglyCorrelatedList.add(status);
				//System.out.println(stronglyCorrelatedList);
			}
			// to swap A with B to get all combinations
			swapArrayLists(A,B);
			
			//calculate suppForA for the current element
			suppForA=getSupportCount(A,records);
			
		if(calculateConfidence(suppForA,suppForAuB))
		{
			String Astring=A.toString();
			String Bstring=B.toString();
			String status="";
			status+=Astring;
			status+="    ----- >   ";
			status+=Bstring;
			status+="      is strongly Correlated ";
			
			stronglyCorrelatedList.add(status);
			//System.out.println(stronglyCorrelatedList);
		}
			
			
			
		}
	}

	return stronglyCorrelatedList;
}// end of getStrongCorrelated.

//print the strongly correlated items.
public void printStrongCorrelatedItems ()
{
	getStrongCorrelatedItemSet();
	System.out.println(stronglyCorrelatedList.size());
	for(int i=0;i<stronglyCorrelatedList.size();i++)
	{
		
		System.out.println(stronglyCorrelatedList.get(i));
	}
}//end of print printStrongCorrelatedItems

//end of Class Controller.	
}
	















	