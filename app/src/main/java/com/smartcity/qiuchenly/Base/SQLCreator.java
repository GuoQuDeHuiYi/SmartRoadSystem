package com.smartcity.qiuchenly.Base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qiuchenly on 2017/12/14.
 */

public class SQLCreator {
  public static final String DB_EXEC_SIMPLE_CREATE_TABLE_START =
          "create table if not exists %s (id integer primary key autoincrement,",
          DB_EXEC_SIMPLE_CREATE_TABLE_END = ");";

  private String exec;

  public SQLCreator(String exec) {
    this.exec = exec;
  }

  public String toString() {
    return exec;
  }


  /**
   * Builder设计模式 自动创建一个数据表
   */
  public static class CreateTableBuilder {

    private String mTableName;
    private static final String M_IF_NOT_EXISTS = "if not exists ";
    private boolean isSetIfNotExists = true;
    private boolean isNoID = false;
    private Map<String, String> mAttribute;
    private boolean mNoPrimaryKey = false;

    public CreateTableBuilder(String tableName) {
      mTableName = tableName;
      mAttribute = new HashMap<>();
    }

    public CreateTableBuilder setIfNotExists(boolean ifNotExists) {
      isSetIfNotExists = ifNotExists;
      return this;
    }

    public CreateTableBuilder setNoID(boolean isNoID) {
      this.isNoID = isNoID;
      mNoPrimaryKey = true;
      return this;
    }

    public CreateTableBuilder setField(String fieldName,
                                       String FieldAttribute) {
      mAttribute.put(fieldName, FieldAttribute);
      return this;
    }

    public CreateTableBuilder setField(String fieldName,
                                       String FieldAttribute,
                                       boolean isPrimaryKey) {
      mAttribute.put(fieldName,
              FieldAttribute + (isPrimaryKey && mNoPrimaryKey ? " primary key" : ""));
      return this;
    }

    public CreateTableBuilder setField(String fieldName,
                                       String FieldAttribute,
                                       boolean isPrimaryKey,
                                       boolean autoIncrement) {
      mAttribute.put(fieldName, FieldAttribute + (isPrimaryKey && mNoPrimaryKey ? " primary key" : (
              autoIncrement && mNoPrimaryKey ? " autoincrement" : "")));
      return this;
    }

    public SQLCreator Build() {
      return new SQLCreator(Build_Text());
    }

    public String Build_Text() {
      StringBuilder exec = new StringBuilder("create table");
      if (isSetIfNotExists)
        exec.append(" " + M_IF_NOT_EXISTS);
      exec.append(mTableName).append("(");
      if (!isNoID) {
        exec.append("id integer primary key autoincrement,");
      }

      for (Map.Entry<String, String> map : mAttribute.entrySet()) {
        exec.append(map.getKey()).append(" ").append(map.getValue()).append(",");
      }
      //去掉最后一个多余的逗号
      exec = new StringBuilder(exec.substring(0, exec.length() - 1) + ");");
      return exec.toString();
    }
  }

  public interface TableFieldAttributeList {
    String S_Int = "integer";
    String S_String = "TEXT";
    String S_Real = "REAL";//值是一个浮点值，存储为 8 字节的 IEEE 浮点数字。
    String S_Null = "NULL";
  }


  public static class InsertItemBuilder {
    private String mTableName = "";
    private Map<String, String> map;

    public InsertItemBuilder(String tableName) {
      this.mTableName = tableName;
      map = new HashMap<>();
    }

    public InsertItemBuilder addAttribute(String attrName, String attrValue) {
      map.put(attrName, attrValue);
      return this;
    }

    public SQLCreator Build() {
      String exec = "";
      exec = "insert into " + mTableName + "(";

      String values = "values(";
      for (Map.Entry<String, String> m : map.entrySet()) {
        values += m.getValue() + ",";
        exec += m.getKey() + ",";
      }
      exec = exec.substring(0, exec.length() - 1) + ")";
      values = values.substring(0, values.length() - 1) + ");";
      exec += values;
      return new SQLCreator(exec);
    }
  }


  public class UpdateBuilder {
    String mTableName;
    Map<String, String> mAttribute;

    List<String> mAnds, mOrs;//针对不同条件语句的设置变量

    public UpdateBuilder(String tableName) {
      this.mTableName = tableName;
      mAttribute = new HashMap<>();
      mAnds = new ArrayList<>();
      mOrs = new ArrayList<>();
    }

    public UpdateBuilder addUpdateAttr(String attrName, String attrValue) {
      mAttribute.put(attrName, attrValue);
      return this;
    }

    public UpdateBuilder addWhere(String wheres) {
      mAnds.add(wheres);
      return this;
    }

    public UpdateBuilder addWhere(String wheres, boolean isOr) {
      (isOr ? mOrs : mAnds).add(wheres);
      return this;
    }

    public SQLCreator Build() {
      String exec = "update from " + mTableName + " set ";
      for (Map.Entry<String, String> m : mAttribute.entrySet()) {
        exec += m.getKey() + " " + m.getValue() + ",";
      }
      exec = exec.substring(0, exec.length() - 1) + " and ";

      //start Resolves and symbols
      for (String str : mAnds) {
        exec += str + " and ";
      }
      exec = exec.substring(0, exec.length() - " and ".length()) + " or ";

      //start resolves or symbol
      for (String str : mOrs)
        exec += str + " or ";
      exec = exec.substring(0, exec.length() - " or ".length()) + ";";
      return new SQLCreator(exec);
    }
  }

  public static class GetItemsBuilder {
    private String mTableName;
    private Map<String, String> map;

    //select * from tableName where id = 123
    public GetItemsBuilder(String tableName) {
      this.mTableName = tableName;
      map = new HashMap<>();
    }

    public GetItemsBuilder AddAttribute(String attrName, String attrValue) {
      map.put(attrName, attrValue);
      return this;
    }

    public SQLCreator Build() {
      String exec = "select * from " + mTableName + " where ";
      for (Map.Entry<String, String> m : map.entrySet()) {
        exec += m.getKey() + " = " + m.getValue() + ",";
      }
      exec = exec.substring(0, exec.length() - 1);
      return new SQLCreator(exec);
    }
  }
}