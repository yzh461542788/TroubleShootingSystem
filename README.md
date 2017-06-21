# TroubleShootingSystem
A trouble shooting system 
  
## 1. 小组成员
* 叶子豪 14302010033
* 金迪威 14302010029

## 2. 目录结构
```
.
├── README.md
├── ooad.sql
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── fudan
    │   │           └── ooad
    │   │               ├── TroubleShootingSystemApplication.java
    │   │               ├── entity
    │   │               │   ├── CheckItem.java
    │   │               │   ├── CheckItemProcess.java
    │   │               │   ├── CheckTask.java
    │   │               │   ├── Company.java
    │   │               │   ├── CompanyState.java
    │   │               │   ├── ItemState.java
    │   │               │   ├── TaskProcess.java
    │   │               │   ├── TaskProcessState.java
    │   │               │   └── Template.java
    │   │               ├── exception
    │   │               │   ├── BaseException.java
    │   │               │   ├── DuplicatedPropertyException.java
    │   │               │   ├── InvalidOperationException.java
    │   │               │   ├── InvalidPropertyException.java
    │   │               │   ├── NullEntityException.java
    │   │               │   └── SystemException.java
    │   │               ├── repository
    │   │               │   ├── CheckItemProcessRepository.java
    │   │               │   ├── CheckItemRepository.java
    │   │               │   ├── CheckTaskRepository.java
    │   │               │   ├── CompanyRepository.java
    │   │               │   ├── TaskProcessRepository.java
    │   │               │   └── TemplateRepository.java
    │   │               ├── service
    │   │               │   ├── CheckItemService.java
    │   │               │   ├── CheckTaskService.java
    │   │               │   ├── CompanyManagerService.java
    │   │               │   ├── CompanyService.java
    │   │               │   └── TemplateService.java
    │   │               └── util
    │   │                   └── DateUtil.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java
            └── com
                └── fudan
                    └── ooad
                        ├── repository
                        │   └── RepositoryTest.java
                        └── service
                            ├── CheckItemServiceTest.java
                            ├── CheckTaskServiceTest.java
                            ├── CompanyManagerTest.java
                            ├── CompanyServiceTest.java
                            └── TemplateServiceTest.java
```
* ```pom.xml```为Maven项目配置文件，可由该配置文件构建项目
* ```src/main```中包含项目实现代码
* ```src/test```中包含项目测试代码
* 包```com.fudan.ooad.entity```存放实体类
* 包```com.fudan.ooad.repository```为DAO层
* 包```com.fudan.ooad.service```实现了业务逻辑
* 包```com.fudan.ooad.exception```包含了自定义Exception
* 包```com.fudan.ooad.util```中定义了通用辅助代码
* **```src/main/resources/application.properties```为本地数据库配置文件，允许前请先修改 **
* ```ooad.sql```为本地数据表结构，运行前请先导入

## 3. 项目简要说明

本次项目使用了Spring Boot以及JPA Repository，根据期中文档对企业隐患排查系统做了相应的设计和实现。

### 实体类
* ```Company``` 为公司，其中```CompanyState```属性在其他属性存在空值时为```Incomplete```，否则为```Normal```
* ```CheckItem``` 为一个检查项目，由园区定义，可以被添加至模版，可以在模版被发放前修改和删除，但在发放后无法删除和修改
* ```Template``` 为项目检查模版，可添加多个检查项目，并可以被发放为检查任务，并可重复利用，多次发放
* ```CheckTask``` 为检查任务，由模版导出，包含模版中的所有检查项目，但之后修改模版并不会影响检查任务中的项目。一个检查任务可以被发放给多个公司
* ```TaskProcess``` 为检查任务与公司的联系集，相当于发放至某公司的某任务，它有```processState```和```finishTime```属性，在公司进行排查后可进行修改
* ```CheckItemProcess``` 为```TaskProcess```与检查项目的联系集，相当于发放至某公司的一个检查任务中的一个检查项目。它有```ItemState```属性，表示某公司对某任务中的某个项目的排查状态

### 业务逻辑层
在实现业务逻辑层之前，我们先根据文档对用例进行了总结，根据用例在业务逻辑层实现了各个方法。并在方法中考虑了多种输入情况，做了一定量的异常检查与处理

### 测试
```RepositoryTest``` 测试了不包含业务逻辑时的基本数据持久化方法。```test/.../service```中的测试了业务逻辑层的方法，并且全部通过。
测试时需要对数据库进行插入及修改，在测试结束后都要进行相应的恢复，以免污染数据库


