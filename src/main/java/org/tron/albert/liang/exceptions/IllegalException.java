package org.tron.albert.liang.exceptions;

public class IllegalException extends Exception {
    public IllegalException(){
        super("Query failed. Please check the parameters.");
    }

    public IllegalException(String message){
        super(message);
    }
}
