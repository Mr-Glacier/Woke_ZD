package Dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;

public class FatherClass {
    private Connection conn = null;
    private Statement stmt = null;

    private String driverName;
    private String Url;
    private String userName;
    private String userPassword;
    private String primaryKey;
    private String tableName;
    private String packageName;
    //public int chose;

    public FatherClass(String tableName) {
        try {
            //String folder = this.getClass().getResource("/").toString() + "config.json";
            String folder = "F:/Workplace/Woke_ZD/config.json";
            System.out.println(folder);
            File file = new File(folder);
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuffer sb = new StringBuffer();
            String text = "";
            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
            br.close();
            JSONObject jsonRoot = JSONObject.parseObject(sb.toString());
            String PA = jsonRoot.getString("EntityPackage");
            System.out.println(PA);
            int dbType = jsonRoot.getInteger("DBPoint");
            JSONArray dbList = jsonRoot.getJSONArray("DBList");
            //JSONObject currentDB = (JSONObject) dbList.get(dbType);
            JSONObject First = dbList.getJSONObject(1);
            this.driverName = First.get("DBDriver").toString();
            this.Url = First.get("DBConnectionString").toString();
            this.userName = First.get("DBUserName").toString();
            this.userPassword = First.get("DBUserPass").toString();
            this.packageName = First.getString("DBPageName");
            this.tableName =tableName;
            //System.out.println(currentDB.get("DBDriver"));
            System.out.println(this.driverName);
            System.out.println(this.Url);
            System.out.println(this.userName);
            //System.out.println(this.primaryKey);

            //this.packageName = currentDB.getString("DBPageName");
            JSONArray EntityList = jsonRoot.getJSONArray("EntityList");
            JSONObject entity = EntityList.getJSONObject(1);
            this.packageName = PA+entity.getString("EntityName");
            System.out.println(packageName);
            this.primaryKey = entity.getString("EntityPrimaryKey");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }


    //111111111111111111111111111
//    public String get_driverName(){
//        return this.driverName;
//    }
//    public String get_Url(){
//        return  this.Url;
//    }
//    public String get_UserName(){
//        return  this.userName;
//    }
//    public String get_UserPassword(){
//        return this.userPassword;
//    }
//    public String get_PrimaryKey(){
//        return this.primaryKey;
//    }
//    public String get_TableName(){
//        return this.tableName;
//    }
//    public String get_PackageName(){
//        return this.packageName;
//    }

    public void Method_CreateSomeObject() {
        try {
//            System.out.println(this.userName);
//            System.out.println(userName);
            Class.forName(this.driverName);
            if (null == conn || conn.isClosed())
                conn = DriverManager.getConnection(Url, userName, userPassword);
            if (null == stmt || stmt.isClosed())
                stmt = conn.createStatement();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void Method_I_U_D(String sql){
        try{
            Method_CreateSomeObject();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
    }


    //插入方法
    public void Method_Insert(Object obj) {
        try {
            Class I = obj.getClass();
            //获取类方法
            Method methods[] = obj.getClass().getDeclaredMethods();
            System.out.println(methods.length);
            String colueList = "";
            String valueList = "";
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName().equals("get_" + this.primaryKey)) {
                    continue;
                }
                if (methods[i].getName().startsWith("get")) {
                    String colueName = methods[i].getName().replace("get_", "");
                    String value = methods[i].invoke(obj).toString();
                    colueList += colueName + ",";
                    //分析出数值类型,String
                    if (methods[i].getReturnType() == new String().getClass()) {
                        valueList += "'" + value + "',";
                    } else {
                        valueList += value + ",";
                    }
                }
            }
            colueList = colueList.substring(0, colueList.length() - 1);
            valueList = valueList.substring(0, valueList.length() - 1);
            String sql = "INSERT INTO " + this.tableName + " (" + colueList + ") values (" + valueList + ")";
            System.out.println(sql);
            Method_I_U_D(sql);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }

    //更新方法
    public void Method_Update(Object obj) {
        try {
            Class U = obj.getClass();
            //获取ID值进行更新
            Method methodKey = U.getDeclaredMethod("get_"+this.primaryKey);
            Method methods[] = obj.getClass().getDeclaredMethods();
            String Finallist = "";
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName().equals("get_" + this.primaryKey)) {
                    continue;
                }
                if (methods[i].getName().startsWith("get")) {
                    String colueName = methods[i].getName().replace("get_", "");
                    String vale = methods[i].invoke(obj).toString();

                    if (methods[i].getReturnType() == new String().getClass()) {
                        Finallist += colueName + "=" + "'" + vale + "',";
                    } else {
                        Finallist = colueName + "=" + vale + ",";
                    }
                }
            }
            Finallist = Finallist.substring(0, Finallist.length()-1);
            String sql = "UPDATE " + this.tableName + " set "+Finallist+" WHERE "+this.primaryKey+" = "+methodKey.invoke(obj);
            System.out.println(sql);
            Method_I_U_D(sql);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public  ArrayList<Object> Method_Find(){
        ArrayList<Object> results = new ArrayList<Object>();
        try{
            /*
            find方法较难理解,个人难点在于返回值上,查询语句并不难理解,但在后续编码中较为重要;
            在find方法中如果传入参数为Object,则使用Class F = obj.getClass()
            如果传入参数为packageName,则使用Class F = Class.forName("packageName")
             */
            String query ="SELECT*FROM "+this.tableName;
            Method_CreateSomeObject();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){

                Class F = Class.forName(packageName);
                //Class F = obj.getClass();
                //实例化
                Object Find = F.newInstance();
                //获取列名
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                //获取列数
                int lieNumber = resultSetMetaData.getColumnCount();
                System.out.println(lieNumber);

                for (int i = 0; i < lieNumber; i++) {
                    //通过序号获取列名
                    String columnName = resultSetMetaData.getColumnName(i+1);
                    //获取值
                    Object columnValue = resultSet.getObject(i+1);
                    //根据列名获取属性.getDeclaredField,获取类中所有的声明字段
                    Field field = F.getDeclaredField(columnName);
                    //可以向私有属性中写值,将private变为public
                    field.setAccessible(true);
                    //写值
                    field.set(Find, columnValue);
                }
                results.add(Find);
            }
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
        return results;
    }

    public  ArrayList<Object> Method_Find10(String query){
        ArrayList<Object> results = new ArrayList<Object>();
        try{
            /*
            find方法较难理解,个人难点在于返回值上,查询语句并不难理解,但在后续编码中较为重要;
            在find方法中如果传入参数为Object,则使用Class F = obj.getClass()
            如果传入参数为packageName,则使用Class F = Class.forName("packageName")
             */
            Method_CreateSomeObject();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){

                Class F = Class.forName(packageName);
                //Class F = obj.getClass();
                //实例化
                Object Find = F.newInstance();
                //获取列名
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                //获取列数
                int lieNumber = resultSetMetaData.getColumnCount();
                System.out.println(lieNumber);

                for (int i = 0; i < lieNumber; i++) {
                    //通过序号获取列名
                    String columnName = resultSetMetaData.getColumnName(i+1);
                    //获取值
                    Object columnValue = resultSet.getObject(i+1);
                    //根据列名获取属性.getDeclaredField,获取类中所有的声明字段
                    Field field = F.getDeclaredField(columnName);
                    //可以向私有属性中写值,将private变为public
                    field.setAccessible(true);
                    //写值
                    field.set(Find, columnValue);
                }
                results.add(Find);
            }
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
        return results;
    }




}
