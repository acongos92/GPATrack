package com.backend_code;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class ClassGrade {

    private static final Logger logger = Logger.getLogger("ClassGrade Logger");

    private List<keyValObject> vals;
    private int len;

    //No Arg Constructor
    public ClassGrade(){
        this.vals = new LinkedList<keyValObject>();
    }

    //Adds to the object
    public void add(String className, Double classGrade){
        keyValObject toAdd = new keyValObject(className, classGrade);
        this.vals.add(toAdd);
        len++;
    }

    //Deletes from the object
    public String delete(int index){
        String toReturn = this.vals.get(index).getKey();
        this.vals.remove(index);
        this.len--;
        return toReturn;
    }

    //Returns the string key
    public String getKeyString(int index){
        return this.vals.get(index).getKey();

    }

    //Return the string value
    public Double getValueDouble(int index){
        return this.vals.get(index).getValue();
    }

    //Sorts the entire object by key value
    public void sortVal(){
         Collections.sort(this.vals, new Comparator<keyValObject>() {
            @Override
            public int compare(keyValObject o1, keyValObject o2) {
                return o1.getKey().compareToIgnoreCase(o2.getKey());
            }
        });
    }

    //Keeps track of the total length of the arrayList
    public int length(){
        return this.len;
    }

    //deletes by class name, very sad, must rewrite, sorry gabe.
    public void delete(String index){
        int pos = 0;
        for (int i = 0; i < this.vals.size(); i++){
            if (index.equalsIgnoreCase(this.vals.get(i).getKey())){
                pos = i;
                i  = this.vals.size();
            }
        }
        delete(pos);
    }

    //Private class that stores a key - value pair
    private class keyValObject{
        private String className;
        private Double classGrade;

        //Constructor
        public keyValObject(String className, Double classGrade){
            this.className = className;
            this.classGrade = classGrade;
        }

        public String getKey(){
            return this.className;
        }
        public Double getValue(){
            return this.classGrade;
        }
    }


}
