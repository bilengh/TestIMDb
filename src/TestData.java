import java.util.Vector;
import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class TestData {
	
	private int testMethodCount;
	private int paramCount;
	private String testDataFilePath;

	private Vector  paramData=new Vector();
	private Vector  testMethod=new Vector();
	
	public TestData(String filepath) {
		testDataFilePath=filepath;
		testMethodCount = 0;
		paramCount = 0;
	}
	
    
	public void getDatafromXL()throws IOException {
		int startrow=1;
		boolean exitvar=false;
		File inputWorkbook = new File(testDataFilePath);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			for (int i=startrow;i<sheet.getRows();i++){
				for (int j=0;j<sheet.getColumns();j++){
					Cell cell=sheet.getCell(j,i);
					if (cell.getContents().equalsIgnoreCase("END")){
						exitvar=true;
						break;
					}
					if (j==0){
					this.testMethod.add(cell.getContents());
					testMethodCount++;
					}else {
						if ((cell.getContents().equalsIgnoreCase("##"))||(cell.getContents().equalsIgnoreCase("#")))break;
						this.paramData.add(cell.getContents());
						paramCount++;
					}
				}// end for j loop
				if (exitvar)break;
			}// end for i loop
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}//End parseXLTestCase
	
	public int getTestMethodCount(){
		return testMethodCount;
	}
	
	public int getParamCount(){
		return paramCount;
	}
	
	public Vector <String> getParamData(){
		return paramData;
	}

	public Vector <String> getTestMethods(){
		return testMethod;
	}


}