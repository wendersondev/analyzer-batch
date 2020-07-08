package br.com.agibank.analyzer.batch.exceptions;

public class InvalidFileDataSizeException extends Exception {

    public InvalidFileDataSizeException(){
        super("The size of file data is invalid.");
    }

    public InvalidFileDataSizeException(String message){
        super(message);
    }

}
