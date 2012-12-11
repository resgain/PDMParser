PDMParser工具库说明
================
##介绍##
  PDMParser是一个解析Powerdesigner PDM文件的一个小类库，这个类库简单到只有一个方法
  ```
  PDMParser.parse("pdm文件名及路径");
  ```
  这个方法返回一个Project对象实例。
  
##Project##
  PDM文件对应Project。Project类基本属性如下:
  ```
    private String code; //项目代码
    private String name; //项目名称    
    private String author; //作者
    private String version; //版本
    private Date cdate; //创建日期
    
    private Map<String, Domain> domains;//项目中定义的domain
    private List<Category> categoryList;//项目中创建的pdm分类
    private List<Table> tableList; //项目中定义的表
  ```
  
##Table##
  Project中包含多个Table，表对应Table。Table类基本属性如下:
  ```
    private String prjId; //项目ID
    private String code;  //表代码
    private String name;  //表含义
    private List<Column> columns; //列信息
  ```
##Column##
  Table中有多个Column，字段对应Column。Column类基本属性如下:
  ```
    private String code; //字段代码
    private String name; //字段含义
    private String type; //原始类型及长度
    private String domainId; //定义的数据域ID
    private boolean pkFlag; //是否为主键
    private boolean nullFlag; //是否可以为空
    private boolean uniqueFlag; //是否唯一
    private Table ref;//关联表
    private String refId; //关联表字段ID
    private String comment; //注释
  ```
  
