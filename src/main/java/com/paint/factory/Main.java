package com.paint.factory;

import com.paint.factory.model.PaintFactory;
import com.paint.factory.builder.FilePaintFactoryBuilder;

import java.util.List;

/**
 * User: Sergey Sachkov
 * Date: 13/07/15
 */
public class Main {

    public static void main(String [] args){
        long start = System.currentTimeMillis();
        if(args.length < 1){
            System.out.println("Usage: paint-factory [FILE_NAME] ....");
        }
        String file = args[0];

        FilePaintFactoryBuilder readerUtil = new FilePaintFactoryBuilder();
        List<PaintFactory> paintFactories = readerUtil.processFile(file);
        int index = 1;
        for(PaintFactory factory : paintFactories){
            System.out.println("Case #" + index + " : " + factory.produce());
            index++;
        }
        long end = System.currentTimeMillis();
        System.out.println("Time : " + (end - start));
    }
}
