package com.paint.factory.builder;

import com.paint.factory.model.ColourType;
import com.paint.factory.model.Customer;
import com.paint.factory.model.Paint;
import com.paint.factory.model.PaintFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Sergey Sachkov
 * Date: 13/07/15
 */
public class FilePaintFactoryBuilder {

    public List<PaintFactory> processFile(String file){
        List<PaintFactory>paintFactories = new ArrayList<>();
        try(
            BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            long sampleNumber = Long.parseLong(line);
            for(int i = 0; i < sampleNumber; i++){
                int colours = Integer.parseInt(br.readLine());
                int customers = Integer.parseInt(br.readLine());
                PaintFactory factory = new PaintFactory(colours, new ArrayList<>());
                for(int j = 0; j < customers; j++){
                    String dataStr = br.readLine();
                    String [] data = dataStr.split(" ");
                    List<Paint> paints = new ArrayList<>();
                    for(int  val = 1; val < data.length; val += 2) {
                        Paint paint = new Paint(Integer.parseInt(data[val]), Integer.parseInt(data[val+1]) == ColourType.MATE.getValue() ? ColourType.MATE : ColourType.GLOSSY);
                        paints.add(paint);
                    }
                    Customer customer = new Customer(j, paints);
                    factory.getCustomers().add(customer);
                }
                paintFactories.add(factory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paintFactories;
    }
}
