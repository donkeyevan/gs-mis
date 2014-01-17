package com.youai.gamemis.util;



public class XorEncrypt {
	
	public static final String XOR_KEY = "One ring to rule them all, one ring to find them, one ring to bring them all and in the darkness bind them.";
	
	public String decrypt(String input, String key) {
	
		try {
			byte[] bdata = Base64.decodeFast(input);

			byte[] bkey = key.getBytes("UTF-8");
			byte[] result = new byte[bdata.length];
			int m = 0;
			for (int i = 0;i < bdata.length;i ++) {
				result[i] = (byte)(bdata[i] ^ bkey[m]);
				m ++;
				if (m >= bkey.length) {
					m = 0;
				}
			}

			String a= new String( result, "UTF-8" );
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String encrypt(String input, String key) {
		try {
			
			byte[] bdata =  input.getBytes();

			byte[] bkey = key.getBytes("UTF-8");
			byte[] result = new byte[bdata.length];
			int m = 0;
			for (int i = 0;i < bdata.length;i ++) {
				result[i] = (byte)(bdata[i] ^ bkey[m]);
				m ++;
				if (m >= bkey.length) {
					m = 0;
				}
			}
			//BASE64Encoder base64en = new BASE64Encoder();
			//return base64en.encode(bdata);
			//return new String( result );
			return new String(Base64.encodeToChar(result, false ));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;	
		}
}
