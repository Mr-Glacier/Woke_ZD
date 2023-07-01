import Dao.H_Dao;
import Entity.Bean_DModel;

import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        H_Dao dao = new H_Dao("com.microsoft.sqlserver.jdbc.SQLServerDriver","jdbc:sqlserver://localhost:1433;databaseName=T_work","sa","7777","C_ID", "T_DModel");

        //dao.Method_Find("Entity.Bean_DModel");

        System.out.println("Hello world!");

        Bean_DModel bean = new Bean_DModel();
        bean.set_C_ID(16);
       // System.out.println(dao.Method_Find(""));

        ArrayList<Object> ao = dao.Method_Find("Entity.Bean_DModel");
        for (int i = 0; i < ao.size(); i++) {
            Bean_DModel b = (Bean_DModel) ao.get(i);
            System.out.println(b.get_C_ID()+"\t"+b.get_C_BrandID()+"\t"+b.get_C_FactoryName());
            
        }
    }
}