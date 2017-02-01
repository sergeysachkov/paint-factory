package com.paint.factory.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: Sergey Sachkov
 * Date: 13/07/15
 */
public class PaintFactory {

    private int numberOfColours;
    private List<Customer> customers;
    private int [] colours;

    public PaintFactory(int numberOfColours, List<Customer> customers) {
        this.numberOfColours = numberOfColours;
        this.customers = customers;
        colours = new int[numberOfColours];
    }

    public int getNumberOfColours() {
        return numberOfColours;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Produces the resulting colours of types
     * @return string with colour types or IMPOSSIBLE if cannot satisfy constraints
     */
    public String produce() {
        Map<Integer, Paint> outputPaints = new HashMap<>();
        Set<Paint> bestFit;
        //todo to trigger build
        List<Customer> customersClone = new ArrayList<>(getCustomersCopy(this.customers));
        if (!customersClone.isEmpty()) {
            while (!customersClone.isEmpty()) {
                customersClone.sort((Customer o1, Customer o2) -> {
                    if (o1.getPaints().size() == o2.getPaints().size()) {
                        return 0;
                    } else if (o1.getPaints().size() > o2.getPaints().size()) {
                        return 1;
                    } else {
                        return -1;
                    }

                });
                if( customersClone.get(0).getPaints().isEmpty() || customersClone.get(0).getPaints().size() != 1){
                    break;
                }
                for (Iterator<Customer> iter = customersClone.iterator(); iter.hasNext(); ) {
                    Customer customer = iter.next();
                    if (customer.getPaints().size() == 1) {
                        if (outputPaints.containsKey(customer.getPaints().get(0).getColour())) {
                            Paint tmpPaint = outputPaints.get(customer.getPaints().get(0).getColour());
                            if (tmpPaint.getType() != customer.getPaints().get(0).getType()) {
                                return "IMPOSSIBLE";
                            }
                        } else {
                            outputPaints.put(customer.getPaints().get(0).getColour(), customer.getPaints().get(0));
                        }
                        iter.remove();
                    } else {
                        break;
                    }
                }
                for (Iterator<Customer> iter = customersClone.iterator(); iter.hasNext(); ) {
                    Customer customer = iter.next();
                    for (Iterator<Paint> iterator = customer.getPaints().iterator(); iterator.hasNext(); ) {
                        Paint p = iterator.next();
                        if (outputPaints.containsKey(p.getColour()) && p.getType() == outputPaints.get(p.getColour()).getType()) {
                            customer.setSatisfied(true);
                        } else if (outputPaints.containsKey(p.getColour()) && p.getType() != outputPaints.get(p.getColour()).getType()) {
                            //remove conflicting paints
                            iterator.remove();
                        }
                    }
                    //already satisfied and doesn't collide with single paint holder
                    if (customer.isSatisfied()) {
                        iter.remove();
                    }
                }
            }
            Set<Paint> possibleCombinations = new HashSet<>();
            if(customersClone.isEmpty() && !outputPaints.isEmpty()){
                possibleCombinations.addAll(new HashSet<>(outputPaints.values()));
            }else {
                possibleCombinations = producePaintForLeftClients(customersClone, outputPaints);
            }
                if(!possibleCombinations.isEmpty()) {
                    bestFit = possibleCombinations;
                }else {
                    return "IMPOSSIBLE";
                }
        }else {
            return "IMPOSSIBLE";
        }


        for (Paint entry : bestFit) {
            if (entry.getType() == ColourType.MATE) {
                colours[entry.getColour() - 1] = 1;
            } else {
                colours[entry.getColour() - 1] = 0;
            }
        }

        StringBuilder buffer = new StringBuilder();
        for (int i : colours) {
            buffer.append(i).append(" ");
        }

        return buffer.toString();
    }

    private Set<Paint> producePaintForLeftClients(List<Customer> customers, Map<Integer, Paint> outputPaints) {
        Set<Paint> possibleCombinations = new HashSet<>();
        if (!customers.isEmpty()) {
            Customer customer = customers.remove(0);
            List<Paint> paints = customer.getPaints();
            for(Iterator<Paint> paintIterator = paints.iterator(); paintIterator.hasNext();){
                Paint paint = paintIterator.next();
                paintIterator.remove();
                Set<Paint> set = new HashSet<>(outputPaints.values());
                set.add(paint);
                if(produceCustomerPaintList(customers, set, possibleCombinations)){
                    //combination with most glossy found
                    break;
                }
            }
        }
        return possibleCombinations;
    }

    //Find the possible paths
    private boolean produceCustomerPaintList(List<Customer> customers, Set<Paint> resultPaintList, Set<Paint> possibleCombinations) {

        if(customers.isEmpty()){
            possibleCombinations.addAll(resultPaintList);
            return true;
        }

        for (Customer customer : customers) {
            List<Customer> list = getCustomersCopy(customers);
            list.remove(customer);
            boolean success = false;
            List<Paint> paints = new ArrayList<>(customer.getPaints());
            for (Paint paint1 : paints) {
                Set<Paint> result = new HashSet<>(resultPaintList);
                if (!hasCollisionInList(result, paint1)) {
                    success = true;
                    result.add(paint1);
                    customer.setSatisfied(true);
                    if (list.isEmpty()) {
                        possibleCombinations.addAll(result);
                        return true;
                    }
                }
                if (success) {
                    if (produceCustomerPaintList(list, result, possibleCombinations)) {
                        return true;
                    } else {
                        success = false;
                        customer.setSatisfied(false);
                    }
                }
            }
            if(!customer.isSatisfied()){
                    return false;
            }
        }
        return false;
    }

    private boolean hasCollisionInList(Set<Paint> resultPaintList, Paint paint) {
        for (Paint paint1 : resultPaintList) {
            if (paint1.getColour() == paint.getColour() && paint1.getType() != paint.getType()) {
                return true;
            }
        }
        return false;
    }

    private List<Customer> getCustomersCopy(List<Customer> customers){
        List<Customer> newCustomerList = new ArrayList<>();
        for(Customer customer : customers){
            newCustomerList.add(new Customer(customer.getId(), new ArrayList<Paint>(customer.getPaints())));
        }
        return newCustomerList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaintFactory that = (PaintFactory) o;

        if (numberOfColours != that.numberOfColours) return false;
        return !(customers != null ? !customers.equals(that.customers) : that.customers != null);

    }

    @Override
    public int hashCode() {
        int result = numberOfColours;
        result = 31 * result + (customers != null ? customers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PaintFactory{");
        sb.append("numberOfColours=").append(numberOfColours);
        sb.append(", customers=").append(customers);
        sb.append('}');
        return sb.toString();
    }
}
