package fr.univtours.info.pocdatastory;

public class Answer {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public Answer(int code, String message){
        this.code=code;
        this.message=message;
    }


}
