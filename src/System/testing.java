package System;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class testing {

	public static void main(String[] args) {
		List<Integer> sortCount = new ArrayList<Integer>();
		int fruitCount = 2;
		int vegetableCount = 5;
		int gbnCount = 3; // grains, beans and nuts
		int meatCount = 1; // meat and poultry
		int seafoodCount = 8;
		int dairyCount = 4;
		int otherCount = 6;
		
		sortCount.add(fruitCount);
		sortCount.add(vegetableCount);
		sortCount.add(gbnCount);
		sortCount.add(meatCount);
		
		for(int i = 0; i<sortCount.size(); i++) {
		System.out.print(sortCount.get(i));
		}
		System.out.println();
		Collections.sort(sortCount);
		for(int i = 0; i<sortCount.size(); i++) {
		System.out.print(sortCount.get(i));
		}
		System.out.print(sortCount.);
		
		
		
	}
	
	
}
