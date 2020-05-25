package fr.univtours.info.pocdatastory;

public class AnswerViz {

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    private String filename;


    public AnswerViz(int code, String message, String filename){
        this.code=code;
        this.message=message;
        this.filename=filename;
    }


}
