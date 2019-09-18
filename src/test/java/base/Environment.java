package base;

public enum Environment
{
    QA01, QA02, UAT01, Stage, PreProd, Prod;


	Environment(){
		
	}
	
	private String fileName;
	private String environmentValue;
	
	Environment(String fileName,String environmentValue){
		this.fileName=fileName;
		this.environmentValue=environmentValue;		
	}
	
	public String environmentValue(){
		return environmentValue;
	}

    }


