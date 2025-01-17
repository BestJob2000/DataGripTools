# 数据抽取工具设计

Date: 2021/11/1

Made By 杨皓天

_______

[toc]

## 目标

数据抽取整合工具，疫情期间各商场、银行等公共场所要求来访人员登记，数据以Excel格式进行记录保存，但是每天出入人员的数据可能有重复的，或者信息不全，也可能有其他异常情况，==要求将电子表格整合在一起==(原本的表格可能是每个地点一个表格)
，识别并删除重复数据，提供一个可用的、干净的数据集，存入数据库，并具备特定查询功能的数据展示功能。

![数据抽取工具设计](数据抽取工具设计.png)

### 目标分析

#### 第一阶段，数据存储

1. 从Excel中获取数据（建立模拟数据） 模拟数据的格式：
    1. 地点
    2. 人名
    3. 身份证号
    4. 来访时间
2. 数据分类，根据地点对数据进行分类
3. 对不同地点的数据根据身份证号筛除重复数据： 定义重复数据概念：1. 在同一天同一人多次出现 2. 在某一段时间内同一人多次出现。
4. 筛选信息不全数据，如果缺失数据是是来访时间，根据上下数据来进行补充，若上下数据时间在同一天，则取上下时间中某一个作为补充。若不为同一天，则以该数据作为新数据插入，旧的记录以上一条记录的时间填充，新的一条以下一条数据时间填充，将该人看作两天都来访，若连续多条缺失，同理以边缘记录为起点向内进行填充。如果缺失数据是人名（身份证号），直接删除该记录。
5. 对于其他异常情况：后续进行分析。
6. 利用Mybatis将数据存入数据库（暂定mysql）中



##### MyBatis 的映射器被连接在控制器对应的接口上：

1. 进入 ==\AddTrace接口==时后端程序从根位置"src/main/resources/进入登记.xlsx"读取并上传EXCEL文件中的数据。加入需要新增进出入登记数据则需要通过前端的文件上传功能将文件导出至对应的包位置，并覆盖原文件（需在覆盖前确认当前文件中数据是否已被上传，若并未上传则执行上传操作后再覆盖）->需要一个文件上传功能，并对前后端执行顺序做出有效控制。

2. 进入==\QueryById接口==时，需要接受从数据库中传入的数据，并放在指定位置。并需要一个输入框和按钮来输入对应的查询用的id号。此处需要注意前后端的数据通信，尤其是执行顺序，在打开接口时首先应先让控制器接收到指定的id号后，再进行数据展示，并对控制器返回的数据进行有效接收。

   > 参考：
   >
   > 1. [springboot的Controller层接收前端页面表单传入数据的三种方法](https://blog.csdn.net/weixin_41902922/article/details/107939455)
   >
   > 2. [SpringBoot实现前后端数据交互、json数据交互、Controller接收参数的几种常用方式](https://blog.csdn.net/qq_20957669/article/details/89227840)

3. 进入==Add_Trace接口==时，前端页面接受从数据库传入的数据并利用Echarts结合表格对数据进行可视化展示。

###### 当前问题

前端页面可以通过对数据库的调用来直接获取数据库中数据，现在的问题在于：

1. 如何在JavaScript中调用MyBatis映射器。
2. 如何在前端控制数据上传数据库而不是必须进入/AddTrace接口后由控制器对Data_Processsing对象进行初始化并上传数据。即如何在前端对Java对象进行调用。可否将DataProcessing独立作为项目组件并在DataProcessing中调用Mabatis映射器，并通过前端界面操作对该类进行独立的实例化调用。
3. 如何通过前端页面直接将文件上传并对原始数据文件进行覆盖。

以上问题亟待解决。



#### 第二阶段 数据展示


#### 前端页面功能
##### 登录/初始界面
进行登录  
登陆完成后进入初始界面  
初始界面可进行功能选择进入不同界面  
1.上传数据  
2.查看数据  
3.下载数据  

##### 上传界面
选择上传文件的文件框    
上传按钮    
上传成功显示    

##### 查看界面
选择需要查看的数据日期  
选择需要查看的数据类型  
数据显示界面  

##### 下载界面
选择需要下载的数据日期  
选择需要下载的数据类型  
下载按钮  

### 
