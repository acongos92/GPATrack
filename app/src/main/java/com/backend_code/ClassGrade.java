package com.backend_code;


import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class ClassGrade {

    private static final Logger logger = Logger.getLogger("ClassGrade Logger");

    private List<keyValObject> vals;


    //No Arg Constructor
    public ClassGrade(){
        this.vals = new LinkedList<keyValObject>();
    }

    public void add(String className, Double classGrade){
        keyValObject toAdd = new keyValObject(className, classGrade);
        this.vals.add(toAdd);
    }

    public String getKeyString(int index){
        return this.vals.get(index).getKey();

    }

    public Double getValueDouble(int index){
        return this.vals.get(index).getValue();
    }

    public void sortVal(){
        logger.info("!!!!!!!!!!!!!!!!!!!!!!");
        //Collections.sort(this.getKeyString(), new miniMapComparator());
    }

    public class miniMapComparator implements Comparator<String> {
        @Override
        public int compare (String map1, String map2){
            return map1.compareTo(map2);
        }
    }




    public class keyValObject{
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