package dataprocess;

import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class processdata {
	
	 static Calendar begin = Calendar.getInstance();
	 static Calendar end = Calendar.getInstance();
	
	 public static void main(String[] args) throws IOException, ParseException {
		 
 
		      getGA("C:/Users/Alan/Desktop/dataprocessing/data/2015-08-03.txt","C:/Users/Alan/Desktop/dataprocessing/data/ga2015-08-03.txt");

		    SplitGA("C:/Users/Alan/Desktop/dataprocessing/data/","ga2015-08-03.txt");
		     toprocessData("C:/Users/Alan/Desktop/dataprocessing/data/","Walking-Onbag_ga2015-08-03.txt","Walking");
	
	 }
	 
	 public static  void getGA(String Input, String Output) throws IOException, ParseException {
		 
	
			
			
			File file = new File(Output);
			if (!file.exists()) {
				file.createNewFile();
			}
			
			BufferedReader in = new BufferedReader(new FileReader(Input));
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));		 
			String line;		
							
			while((line = in.readLine()) != null){
				String[] strs = line.split(" ");	
					
				if (strs[2].equals("GA")){					 
					bw.append(line+'\n');
					//System.out.println(line);				
				}
			}
			
			bw.close();		
			in.close();
			
	 
	 }
	 
	 
	 public  static  void SplitGA(String Path,String filename) throws IOException, ParseException {
		 
		    BufferedReader in = new BufferedReader(new FileReader(Path+filename));
			String line,currentactivity,tempactivity = null;
			FileWriter fw ;
 
			BufferedWriter bw = null;		
			
			boolean firstline=true;
			while((line = in.readLine()) != null){
				
				String[] strs0 = line.split(" ");				 
				currentactivity=strs0[1];  				 
				 
				
				if (firstline){
					firstline=false;
					tempactivity=currentactivity;
					File file = new File(Path+tempactivity+"_"+filename);
					if (!file.exists()) {
						file.createNewFile();
					}
					bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile(),true));
					bw.append(line+'\n');
					
					 
	
					
				}else{
					if (tempactivity.equals(currentactivity)) {
						bw.append(line+'\n');
						//System.out.println(bw.toString()+ line);
					}
					else{
						 
						tempactivity=currentactivity;
						 
						File file = new File(Path+tempactivity+"_"+filename);
						if (!file.exists()) {
							file.createNewFile();
						}
						bw.close();
						bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile(),true));
						bw.append(line+'\n');						
					}
					
					
				}
			
			
			}	
			bw.close();
	 }
	 
	 
	 public static void toprocessData(String Path,String filename,String expectedactivity) throws IOException, ParseException {
		 
				 
		   
 
			GetBeginandEnd(Path,filename);
			String datatime = null,  currentactivity,frameactivity = "";
			Float currentintetval=(float) 0, tempinterval=(float) 0;

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
			Calendar start = Calendar.getInstance();
			Calendar currentline = Calendar.getInstance();
			Calendar nextline = Calendar.getInstance();
			String line1;
            float right=0,wrong=0;
            boolean firstline=true;
		 

			
			BufferedReader in1 = new BufferedReader(new FileReader(Path+filename));		
			 
			while((line1 = in1.readLine()) != null){
				

				
				String[] strs0 = line1.split(" ");	
				datatime=strs0[0];
				currentactivity=strs0[strs0.length-2];  
				String last=strs0[strs0.length-1];
				currentintetval = Float.valueOf(last.substring(0, last.length()-1));
				currentline.setTime(simpleDateFormat.parse(datatime));
				 if (!currentactivity.equals("foot")&&(currentline.compareTo(end)<0)&&(currentline.compareTo(begin)>0)){
					 
					 System.out.println(line1);
				 
					if (firstline){
						start.setTime(simpleDateFormat.parse(datatime));
						nextline.setTime(start.getTime());
						nextline.add(Calendar.SECOND, 40);
						tempinterval=currentintetval;
						firstline=false;			 
					}
					else{					
						
						if (currentline.compareTo(nextline)<0){
							if(currentintetval>tempinterval){
								frameactivity=currentactivity;		
								tempinterval=currentintetval;
							}
						 
						}
						else{
							System.out.println(frameactivity);
							if (frameactivity.equals(expectedactivity)){
								right++;
							}
							else{
								wrong++;
							}
							nextline.add(Calendar.SECOND, 5);	
							frameactivity=currentactivity;		
							tempinterval=currentintetval;
							 
						}																	
					}
				 
			}	
		
			
			
			}
			System.out.println(right/(right+wrong));					
			in1.close();
		 
	 }
	 
	 public  static  void GetBeginandEnd(String Path,String filename) throws IOException, ParseException {
		BufferedReader in1 = new BufferedReader(new FileReader(Path+filename));			
		String line1;
		boolean firstline=true;
		String datatime = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		 
		while((line1 = in1.readLine()) != null){
			String[] strs0 = line1.split(" ");	
			datatime=strs0[0];
			if (firstline){
				begin.setTime(simpleDateFormat.parse(datatime)); 
				begin.add(Calendar.SECOND, 15);
				firstline=false;						
			}else{
				end.setTime(simpleDateFormat.parse(datatime)); 
			}

		}
		end.add(Calendar.SECOND, -15);
		in1.close();
		System.out.println("begin:   "+begin.getTime()+"  end:   "+end.getTime());
		
	 }

}
