package com.paint.factory.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * User: Sergey Sachkov
 * Date: 21/07/15
 */
public class PaintFactoryTest {


    @Test
    public void testProducePositiveResult(){
        List<Customer> customers = new ArrayList<>();
        List<Paint> paints1 = new ArrayList<>();
        Paint paint1 = new Paint(1, ColourType.MATE);
        paints1.add(paint1);
        Customer customer1 = new Customer(0, paints1);
        customers.add(customer1);

        List<Paint> paints2 = new ArrayList<>();
        Paint paint2 = new Paint(1, ColourType.GLOSSY);
        Paint paint22 = new Paint(2, ColourType.GLOSSY);
        paints2.add(paint2);
        paints2.add(paint22);
        Customer customer2 = new Customer(1, paints2);
        customers.add(customer2);

        List<Paint> paints3 = new ArrayList<>();
        Paint paint3 = new Paint(5, ColourType.GLOSSY);

        paints3.add(paint3);
        Customer customer3 = new Customer(2, paints3);
        customers.add(customer3);
        PaintFactory factory = new PaintFactory(5, customers);

        assertTrue(factory.produce().equals("1 0 0 0 0 "));
    }

    @Test
    public void testProduceImpossible(){
        List<Customer> customers = new ArrayList<>();
        List<Paint> paints1 = new ArrayList<>();
        Paint paint1 = new Paint(1, ColourType.MATE);
        paints1.add(paint1);
        Customer customer1 = new Customer(0, paints1);
        customers.add(customer1);

        List<Paint> paints2 = new ArrayList<>();
        Paint paint2 = new Paint(1, ColourType.GLOSSY);
        paints2.add(paint2);
        Customer customer2 = new Customer(1, paints2);
        customers.add(customer2);


        PaintFactory factory = new PaintFactory(5, customers);

        assertTrue(factory.produce().equals("IMPOSSIBLE"));
    }
}

