package controller;

import java.util.Arrays;

public class StatisticsController {


	    static double getMean(double[] data)
	    {
	        double sum = 0.0;
	        for(double a : data)
	            sum += a;
	        return sum/data.length;
	    }

	   static  double getVariance(double[] data)
	    {
	        double mean = getMean(data);
	        double temp = 0;
	        for(double a :data)
	            temp += (mean-a)*(mean-a);
	        return temp/data.length;
	    }

	   static  double getStdDev(double[] data)
	    {
	        return Math.sqrt(getVariance(data));
	    }

	   static  double median(double[] data) 
	    {
	       Arrays.sort(data);

	       if (data.length % 2 == 0) 
	       {
	          return (data[(data.length / 2) - 1] + data[data.length / 2]) / 2.0;
	       } 
	       else 
	       {
	          return data[data.length / 2];
	       }
	    }
	}
