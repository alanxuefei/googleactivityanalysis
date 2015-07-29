package dataprocess;

import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class processdata {
	
	 public static void main(String[] args) throws IOException, ParseException {
	        

			BufferedReader in = null;
			File file = new File("C:/Users/Alan/Desktop/dataprocessing/ga2015-07-28.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			 
			in = new BufferedReader(new FileReader("C:/Users/Alan/Desktop/dataprocessing/2015-07-28.txt"));
		 
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
			
			String datatime = null,  currentactivity,frameactivity = "";
			Float currentintetval=(float) 0, tempinterval=(float) 0;
 
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
			Calendar start = Calendar.getInstance();
			Calendar currentline = Calendar.getInstance();
			Calendar nextline = Calendar.getInstance();
			 
            float right=0,wrong=0;
			 
		 
			BufferedReader in1 = new BufferedReader(new FileReader("C:/Users/Alan/Desktop/dataprocessing/ga2015-07-28.txt"));			
			String line1;
			boolean firstline=true;
			while((line1 = in1.readLine()) != null){
				

				
				String[] strs0 = line1.split(" ");	
				datatime=strs0[0];
				currentactivity=strs0[strs0.length-2];  
				String last=strs0[strs0.length-1];
				currentintetval = Float.valueOf(last.substring(0, last.length()-1));
				if (!currentactivity.equals("foot")){
				 // System.out.println(datatime+currentactivity+currentintetval);
				 
					if (firstline){
						start.setTime(simpleDateFormat.parse(datatime));
						nextline.setTime(start.getTime());
						nextline.add(Calendar.SECOND, 5);
						tempinterval=currentintetval;
						firstline=false;			 
					}
					else{					
						currentline.setTime(simpleDateFormat.parse(datatime));
						if (currentline.compareTo(nextline)<0){
							if(currentintetval>tempinterval){
								frameactivity=currentactivity;		
								tempinterval=currentintetval;
							}
						 
						}
						else{
							System.out.println(nextline.getTime()+" "+frameactivity);
							if (frameactivity.equals("Walking")){
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
			in1.close();
			
			System.out.println(right/(right+wrong));		
			
	 }

}
