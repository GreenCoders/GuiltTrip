package com.swachhand.android.guilttrip;

import java.util.Date;
import java.util.Random;

public class Insults {
	public static String[] INSULTS;
	//Level 0 to 4 in worst impact to least impact on environment
	
	static {
		INSULTS = new String[25];
		//Level 0
		INSULTS[0] = "The world would be devastated if you keep living like this. Your parents would be unsurprised.";
		INSULTS[1] = "The EPA is trying to convince NASA to put you on their next mission to Mars....";
		INSULTS[2] = "Nazis could have used your car instead of gas chambers.";
		INSULTS[3] = "You've wasted more oil than Exxon.";
		INSULTS[4] = "People feared that another Bhopal event took place, but it was just your engine starting.";
		//Level 1
		INSULTS[5] = "Watch out! Reliance Oil now considers you a serious competitor.";
		INSULTS[6] = "Your carbon footprint can now be seen from space.";
		INSULTS[7] = "Your carbon footprint is now an independant nation.";
		INSULTS[8] = "Scientists say we're 86% carbon. You're 100%.";
		INSULTS[9] = "Sceintists say that within a year, your car emmsions will have become a sentient organism.";
		//Level 2
		INSULTS[10] = "Polar bears and penguins are picketing outside your house.";
		INSULTS[11] = "Don't go near any fireplaces anytime soon.";
		INSULTS[12] = "Even the poor children in Africa don't want your car.";
		INSULTS[13] = "If the Earth could slap you, it would.";
		INSULTS[14] = "Even the poor children in Africa don't want your car.";
		//Level 3
		INSULTS[15] = "You're doing okay! You've only killed 5.76 polar bears so far.";
		INSULTS[16] = "You're doing okay! You've only killed 3.54 polar bears so far.";
		INSULTS[17] = "You're doing okay! You've only killed 1.79 polar bears so far.";
		INSULTS[18] = "You're doing okay!...maybe.";
		INSULTS[19] = "You're doing okay!...maybe.";
		//Level 4
		INSULTS[20] = "You're doing okay! the EPA now only breaks into your house to watch TV while you're gone.";
		INSULTS[21] = "PETA has decided to migrate polar bears from the arctic to your house :)";
		INSULTS[22] = "If the Earth could give high-fives, you'd get one.";
		INSULTS[23] = "Your PUC is as clean as a bride's wedding dress. ";
		INSULTS[24] = "*Kiss!* That's from the environment. Thanks for helping out!";
	}
	
	public static String getInsult(int modeOfTransport) {
		String insult;
		int level = 0;
		switch(modeOfTransport) {
		//Walking index = 0
		case 0:
			level = 4;
			break;
		//Train index = 1
		case 1:
			level = 3;
			break;
		//Bus index = 2
		case 2:
			level = 2;
			break;
		//Taxi index = 5
		case 5:
			level = 1;
			break;
		//Cars index = 3, 4
		default:
			level = 0;
		}
		Random rand = new Random((new Date()).getTime());
		int index =  rand.nextInt(5);
		insult = INSULTS[index + level*5];
		return insult;
	}
}