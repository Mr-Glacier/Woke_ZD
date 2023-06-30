package Dao;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.SortedMap;

public class H_Dao {
    private Connection conn = null;
    private Statement stmt = null;

    private String driverName;
    private String Url;
    private String userName;
    private String uerPassword;
    private String primaryKey;
    private String tableName;

    //构造函数
    public H_Dao(String _driverName, String _url, String _userName, String _userPassword, String _primaryKey, String _tableName) {
        // "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        this.driverName = _driverName;
        this.Url = _url;
        this.userName = _userName;
        this.uerPassword = _userPassword;
        this.tableName = _tableName;
        this.primaryKey = _primaryKey;
    }

    public void Method_CreatSomeObject() {
        try {
            Class.forName(driverName);
            if (null == conn || conn.isClosed())
                conn = DriverManager.getConnection(Url, userName, uerPassword);
            if (null == stmt || stmt.isClosed())
                stmt = conn.createStatement();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void Method_I_U_D(String sql) {
        try {
            Method_CreatSomeObject();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (Exception ex) {
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
}
