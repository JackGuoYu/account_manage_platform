package com.amp.utils;

import java.util.Random;

/**2
 * @author liujiabo
 */
public class CodeUtils {
    public static String createData(int length){
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i=0;i<length;i++){
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }
}
