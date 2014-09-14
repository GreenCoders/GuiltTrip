package com.swachhand.android.guilttrip;

public class CO2_calculate {
	public static double carbon_footprint_calculate(String ModeOfPublicTransport, double dist)
	{
		double mileage=0.0; //in kmpl
		double mult=0.0; //kg of co2 per litre of whatever fuel
		double co2=0.0; //final kg of co2 emitted
		if(ModeOfPublicTransport.equalsIgnoreCase("BUS") || ModeOfPublicTransport.equalsIgnoreCase("TRAIN"))
				{co2=(double)dist/(double)11;}
		if(ModeOfPublicTransport.equalsIgnoreCase("TAXI"))
				{mileage=13; mult=1.905; co2=(double)dist*mult/mileage;}
		if(ModeOfPublicTransport.equalsIgnoreCase("AUTO")) //run on cng
		{mileage=23; mult=1.51; co2=(double)(dist*mult)/mileage;}
	return co2;
	}

	public static double carbon_footprint_calculate(String ModeOfTransport, double dist, double mileage)
	{//'mileage' is in kmpl
		double mult=0.0; //kg of co2 per litre of whatever fuel
		if(ModeOfTransport.equalsIgnoreCase("Diesel car")) //you private car runs on diesel
		{mult=2.68;}
		if(ModeOfTransport.equalsIgnoreCase("Petrol car")) //your private car runs on petrol
		{mult=2.35;}
			double co2=(double)(dist*mult)/mileage;
		return co2;
	}
}
