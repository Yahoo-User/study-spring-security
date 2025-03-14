package org.zerock.myapp.util;

import java.util.Arrays;
import java.util.Random;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class RandomNumberGenerator {
	private static Random randomNumberGenerator;
	
	
	static {
		randomNumberGenerator = new Random();
	}	// static initializer
	
	
	public static int generateInt(int originInclusive, int boundExclusive) {
		log.trace("generateInt({}, {}) invoked.", originInclusive, boundExclusive);
		return randomNumberGenerator.nextInt(originInclusive, boundExclusive);
	} // generateInt
	
	public static int[] generateIntArray(int size, int originalInclusive, int boundExclusive) {
		log.trace("generateIntArray({}, {}, {}) invoked.", size, originalInclusive, boundExclusive);
		
		randomNumberGenerator.setSeed(randomNumberGenerator.nextInt());
		
		int[] randomNumberArray = new int[size];
		for(int index = 0; index < size; ++index) {
			randomNumberArray[index] = randomNumberGenerator.nextInt(originalInclusive, boundExclusive);		// Half-open
		} // Classical for
		
		log.info("  + randomNumberArray: {}", Arrays.toString(randomNumberArray));
		return randomNumberArray;
	} // generateIntArray
	
	
	public static long[] generateLongArray(int size, int originalInclusive, int boundExclusive) {
		log.trace("generateLongArray({}, {}, {}) invoked.", size, originalInclusive, boundExclusive);
		
		randomNumberGenerator.setSeed(randomNumberGenerator.nextInt());
		
		long[] randomNumberArray = new long[size];
		for(int index = 0; index < size; ++index) {
			randomNumberArray[index] = randomNumberGenerator.nextLong(originalInclusive, boundExclusive);		// Half-open
		} // Classical for
		
		log.info("  + randomNumberArray: {}", Arrays.toString(randomNumberArray));
		return randomNumberArray;
	} // generateIntArray

} // end class
