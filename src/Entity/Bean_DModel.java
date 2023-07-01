package Entity;

public class Bean_DModel {

    private int C_ID;
    private String C_FactoryName;
    private String C_ModelName;
    private String C_ModelID;
    private String C_BrandID;
    private String C_ModelURL;

    public void set_C_ID(int C_ID){
        this.C_ID = C_ID;
    }
    public void set_C_FactoryName(String C_FactoryName){
        this.C_FactoryName =C_FactoryName;
    }
    public void set_C_ModelID(String C_ModelID){
        this.C_ModelID = C_ModelID;
    }
    public void set_C_ModelName(String C_ModelName){
        this.C_ModelName =C_ModelName;
    }
    public void set_C_BrandID(String C_BrandID){
        this.C_BrandID = C_BrandID;
    }
    public void set_C_ModelURL(String C_ModelURL){
        this.C_ModelURL = C_ModelURL;
    }



    public int get_C_ID(){
        return this.C_ID;
    }
    public String get_C_ModelID(){
        return this.C_ModelID;
    }
    public String get_C_ModelName(){
        return this.C_ModelName;
    }
    public String get_C_FactoryName(){
        return this.C_FactoryName;
    }
    public String get_C_BrandID(){
        return this.C_BrandID;
    }
    public String get_C_ModelURL(){
        return this.C_ModelURL;
    }


}
