package val.bond.applogic.FileSystem;

public class SomethingWrongException extends Exception {

    private String throwedClass;
    private String fileName;
    private String methodName;
    private int lineNum;
    private Object obj;         //Object cause of exception was called

    public SomethingWrongException(){
        throwedClass = this.getStackTrace()[1].getClassName();
        fileName = this.getStackTrace()[1].getFileName();
        methodName = this.getStackTrace()[1].getMethodName();
        lineNum = this.getStackTrace()[1].getLineNumber();
    }

    public SomethingWrongException(Object obj){
        this.obj = obj;
    }

    public SomethingWrongException(String message) {
        super(message);
        throwedClass = this.getStackTrace()[1].getClassName();
        fileName = this.getStackTrace()[1].getFileName();
        methodName = this.getStackTrace()[1].getMethodName();
        lineNum = this.getStackTrace()[1].getLineNumber();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public String getThrowedClass() {
        return throwedClass;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getLineNum() {
        return lineNum;
    }

    public Object getObj() {
        return obj;
    }
}
