package com.library.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Category {
    //in ArrayList: index 0: number of books a user can borrow, index 1: maximum number of days a user can keep a book.
    Map<String, ArrayList<Integer>> userCapacities = new HashMap<>();


    public Category() {
        this.userCapacities.put("M", new ArrayList<Integer>( Arrays.asList(6, 10))); //Manager
        this.userCapacities.put("S", new ArrayList<Integer>( Arrays.asList(0, 0)));  //redList
        this.userCapacities.put("A", new ArrayList<Integer>( Arrays.asList(4, 7)));
        this.userCapacities.put("B", new ArrayList<Integer>( Arrays.asList(3, 5)));
        this.userCapacities.put("C", new ArrayList<Integer>( Arrays.asList(2, 3)));


    }

    
    public Integer getMaxBooks(String category){
        return this.userCapacities.get(category).get(0);
    }


    public Integer getMaxDays(String category){
        return this.userCapacities.get(category).get(1);
    }
}
