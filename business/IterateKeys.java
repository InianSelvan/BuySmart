package com.buysmart.business;

import java.util.Random;

import com.buysmart.config.KeyFactory;
/**
 * 
 * @author Iniyan
 * 
 *	This is used to iterate the keys in order to minimize the 
 *call frequency on each key
 */
public class IterateKeys{

	public static <T extends ProductsBO> String getKey(T t) throws Exception {
			String key = null;
			KeyFactory keyFactory = new KeyFactory(t);
			String keys[] = keyFactory.getApiKeys();
			Random random = new Random();
			try {
				key = keys[random.nextInt(keys.length)];
			}catch(NullPointerException e) {
				e.printStackTrace();
			}
		return key;
	}

}
