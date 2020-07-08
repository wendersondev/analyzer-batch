package br.com.agibank.analyzer.batch.exceptions;

public class FileDataException extends Exception {

    public FileDataException(){
        super("Data line of file is invalid.");
    }

    public FileDataException(String message){
        super(message);
    }

}
