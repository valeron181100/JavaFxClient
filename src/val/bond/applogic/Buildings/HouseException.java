package val.bond.applogic.Buildings;

public class HouseException extends Exception {

    private String throwedClass;
    private String fileName;
    private String methodName;
    private int lineNum;
    private Object obj;         //Object cause of exception was called

    public HouseException(){
        throwedClass = this.getStackTrace()[1].getClassName();
        fileName = this.getStackTrace()[1].getFileName();
        methodName = this.getStackTrace()[1].getMethodName();
        lineNum = this.getStackTrace()[1].getLineNumber();
    }

    public HouseException(Object obj){
        this.obj = obj;
    }

    public HouseException(String message) {
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
