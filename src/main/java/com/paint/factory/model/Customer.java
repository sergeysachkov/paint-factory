package com.paint.factory.model;

import java.util.Collections;
import java.util.List;

/**
 * User: Sergey Sachkov
 * Date: 13/07/15
 */
public class Customer {
    private long id;
    private List<Paint> paints;
    private boolean satisfied;

    public Customer(long id, List<Paint> paints) {
        this.id = id;
        paints.sort((Paint o1, Paint o2) -> {
            if (o1.getType() == o2.getType()) {
                return 0;
            } else if (o1.getType().getValue() > o2.getType().getValue()) {
                return 1;
            } else {
                return -1;
            }

        });
        this.paints = paints;
    }

    public List<Paint> getPaints() {
        return paints;
    }

    public long getId() {
        return id;
    }

    public void setSatisfied(boolean satisfied) {
        this.satisfied = satisfied;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (id != customer.id) return false;
        if (satisfied != customer.satisfied) return false;
        return !(paints != null ? !paints.equals(customer.paints) : customer.paints != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (paints != null ? paints.hashCode() : 0);
        result = 31 * result + (satisfied ? 1 : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Customer{");
        sb.append("id=").append(id);
        sb.append(", paints=").append(paints);
        sb.append(", satisfied=").append(satisfied);
        //sb.append(", hasMatte=").append(hasMatte);
        sb.append('}');
        return sb.toString();
    }
}
