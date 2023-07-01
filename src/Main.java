import Dao.FatherClass;
import Dao.H_Dao;
import Dao.son_mysql;
import Dao.son_sqlserver;
import Entity.Bean_DModel;
import Entity.Bean_T_AutoConfig;

import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        H_Dao dao = new H_Dao("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://localhost:1433;databaseName=T_work", "sa", "7777", "C_ID", "T_DModel", "Entity.Bean_DModel");

        //dao.Method_Find("Entity.Bean_DModel");
        System.out.println("Hello world!");

        Bean_DModel bean = new Bean_DModel();
        bean.set_C_ID(16);
        // System.out.println(dao.Method_Find(""));

//        ArrayList<Object> ao = dao.Method_Find("Entity.Bean_DModel");
//        for (int i = 0; i < ao.size(); i++) {
//            Bean_DModel b = (Bean_DModel) ao.get(i);
//            System.out.println(b.get_C_ID()+"\t"+b.get_C_BrandID()+"\t"+b.get_C_FactoryName());
//
//        }
        //FatherClass father = new FatherClass(0);

//        son_sqlserver sondao = new son_sqlserver("T_DModel");
//        ArrayList<Object> ao = sondao.Method_Find10("T_DModel");
//        for (int i = 0; i < ao.size(); i++) {
//            Bean_DModel b = (Bean_DModel) ao.get(i);
//            System.out.println(b.get_C_ID() + "\t" + b.get_C_BrandID() + "\t" + b.get_C_FactoryName());
//        }

        son_mysql mysqldao = new son_mysql("T_AutoConfig");
        ArrayList<Object> mysqlo = mysqldao.Method_Find10("T_AutoConfig");
        for (int i = 0; i < mysqlo.size(); i++) {
            Bean_T_AutoConfig b = (Bean_T_AutoConfig) mysqlo.get(i);
            System.out.println(b.get_C_ID() + "\t" + b.get_C_BrandID() + "\t" + b.get_C_FactoryName());
        }


//        ArrayList<Object> ao = father.Method_Find();
//        for (int i = 0; i < ao.size(); i++) {
//            Bean_DModel b = (Bean_DModel) ao.get(i);
//            System.out.println(b.get_C_ID() + "\t" + b.get_C_BrandID() + "\t" + b.get_C_FactoryName());
//
//        }
        //father.Method_CreateSomeObject();
    }
}