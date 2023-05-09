# InfoParse
ReadMe-en
Project url：https://github.com/Coder-RSL/InfoParse
Project Functional Specification
1. User Module:
1.1 User Login Module: Users can log in by entering their username and password. After logging in successfully, they can access and operate the functions within their permission scope.
1.2 User Registration Module: Users can register by entering necessary registration information (such as username, password, email, etc.) and can log in to the system after completing registration.
1.3 User Personal Information Modification Module: Users can modify their personal information (such as name, contact information, etc.).
1.4 User Account Cancellation Function: Users can choose to cancel their account and exit the system after completing their operations.
1.5 Log Management: Users can view the system log to understand the system's running status.

2. Administrator Module:
2.1 Member Role Management: Administrators can assign different roles to users and grant them different operation permissions.
2.2 Department Management Function: Administrators can add, delete, modify, and view department information.
2.3 Member Position Management: Administrators can add, delete, modify, and view position information, including adjusting the department in which the position is located.
2.4 Menu Management: Administrators can add, delete, modify, and view display menus.
2.5 Role Permission Management: Administrators can manage the permissions of each role and control the functions that the role can access and operate.

3. Main Functions of the System:
3.1 User Excel Sample Import Function: Users can upload Excel spreadsheets containing seed growth information.
3.2 User Input Expected Sample: Users can input the expected data processing and prediction result samples.
3.3 User Export Successfully Converted Excel Files: After processing and predicting, users can export Excel files containing prediction results.
3.4 User Upload Excel Spreadsheet: Users can upload Excel spreadsheets for data preprocessing and growth prediction.
3.5 Excel Spreadsheet Preprocessing:
a. Separation: Separates an Excel file containing partial marks but multiple tables into multiple independent tables for input to the next step of processing.
b. Unified and Standardized Processing: Unifies and standardizes multiple tables that have undergone semantic segmentation processing, making them directly applicable for analysis and decision-making.
c. Basic Format Conversion: Further processes the dataset that has been unified and standardized to make it more suitable for analysis and decision-making.

4. Enhanced Features:
  4.1 Crop Growth Prediction: Based on the processed dataset, predicts the future growth height of the seedlings and provides reference for agricultural production decisions.

Technology Stack
Backend Technology Stack:
1. Spring Boot
2. Spring Security
3. MyBatis
4. MySQL
5. Redis
6. Spring Cache
7. WebSocket

Frontend Technology Stack:
1. Vue
2. ElementUI
3. axios
4. vue-router
5. Vuex
6. WebSocket
7. vue-cli4

Environment Deployment
Preparation:
JDK >= 1.8
Mysql >= 5.7.0
Maven >= 3.0
Python 2.7
Python 3.8
g++
Boost.Python (Mac, Linux)
setuptools
sys 1.147
numpy 1.22.3
matplotlib 3.6.1
xlwt 1.3.0
scipy 1.8.1
pandas 1.4.2
sklearn 0.0.post1
keras 2.3.1
math
datetime
pip 23.0.1
xlrd 2.0.1

Running the System:
1. Go to Github to download the project (Note: the following operations assume that the basic environment such as python, java, node, mysql, redis, etc. areinstalled and configured properly).
2. Run the SQL script to create the database and tables.
3. Configure the Redis connection information in the application.yml file.
4. Configure the MySQL connection information in the application.yml file.
5. Build the project using Maven.
6. Start the backend server by running the Spring Boot application.
7. Install the required packages using npm.
8. Start the frontend server by running the Vue application.
9. Open the browser and enter the URL to access the system.

Testing the System:
1. Perform unit tests for all modules.
2. Perform integration tests for the entire system.
3. Perform stress tests to ensure system performance.
4. Perform security tests to ensure the system is secure and user data is protected.

Maintenance:
1. Regularly update and maintain the system to ensure it remains secure and up-to-date.
2. Monitor system performance and take appropriate actions to improve it.
3. Handle bug reports and fix them in a timely manner.
4. Provide technical support to users and address their questions and concerns.

Running System
1. Go to Github and download the project (note that the following operations assume that the system has basic environments such as python, java, node, mysql, and redis already installed).
2. Import the project into Eclipse by going to File > Import, then selecting Maven > Existing Maven Projects. Click the Next > button, select the working directory, and click the Finish button to successfully import the project.
Eclipse will automatically load the Maven dependencies. The first time it loads may be slower depending on the network condition.
Note to refresh and download Maven packages.
3. Deploy Foofah
Only available for Linux.
Python installation:
python -m pip install -U pip setuptools
Docker deployment:
$ docker build -t foofah 
Run:
$ docker run -p 8080:8080 foofah
Foofah runs on localhost:8080.
Installation Guide:
$ cd foofah
$ python setup.py install
User Guide:
Foofah Console
To test Foofah for a single test case from the console:
$ cd foofah
$ python foofah.py --input <test_file>
Note that each test case must be a JSON file containing a JSON object with two members: InputTable and OutputTable, both of which are 2D arrays of strings representing the user-provided input and output examples.
To learn about other command-line options:
$ python foofah.py --help
$ python foofah_server.py
By default, the service is available on localhost:8080.
4. Configure Python environment for advanced features
Note:
- When configuring the environment, it is important to configure the Tsinghua mirror first.
- Except for growth prediction, other files only need to be installed with pip install.
However, if an exception is thrown during runtime, version replacement is required.
Below are the successfully executed Python environments and versions (for reference only):
- sys 1.147
- numpy 1.22.3
- matplotlib 3.6.1
- lwt 1.3.0
5. Open the project and run the Application.
6. Open a browser and enter: (http://localhost:80 (opens new window)) (default account/password: admin/admin123).
If the login page is displayed correctly, you can successfully log in, and the menus and pages are displayed normally, it means that the environment is set up correctly.
It is recommended to use Git clone because it can stay updated and synchronized at any time.
Necessary Configuration:
- Modify the database connection by editing application-druid.yml in the resources directory.
Data source configuration

spring:
 datasource:
        type: com.alibaba.druid.pool.DruidDataSource
        driverClassName: com.mysql.cj.jdbc.Driver
        druid:
            # Master database
            master:
                url: Database address
                username: Database account
                password: Database password
   
- Modify the server configuration by editing application.yml in the resources directory.
Development environment configuration
server:
  # HTTP port of the server, default is 80
  port: Port
  servlet:
    # Application access path
    context-path: /Application path

Deployment System:
- Deploy the project files.
1. Jar deployment method:
Execute the command line: java -jar projectName.jar
2. War deployment method:
Change the packaging in ruoyi/pom.xml to war and put it into the tomcat server webapps.
    <packaging>war</packaging>
Common Issues:
1. If using Mac, modify the path profile in application.yml.
2. If using Linux and getting an error message saying the table does not exist, set the case sensitivity configuration in /etc/my.cnf to
基于机器学习与优化算法的数据自动化转换平台
# 项目地址：https://github.com/Coder-RSL/InfoParse 项目功能说明

1. 用户模块： 1.1 用户登录模块：用户输入用户名和密码进行登录，登录成功后可访问和操作其权限范围内的功能。 1.2 用户注册模块：用户输入必要的注册信息（如用户名、密码、电子邮件等），完成注册后可登录系统。 1.3 用户个人信息修改模块：用户可以修改自己的个人信息（如姓名、联系方式等）。 1.4 用户注销账户功能：用户在完成操作后，可以选择注销账户，退出系统。 1.5 日志管理：用户可以查看系统日志，了解系统运行状况。
2. 管理员模块： 2.1 成员角色管理：管理员可以为用户分配不同的角色，赋予用户不同的操作权限。 2.2 部门管理功能：管理员可以对部门信息进行添加、删除、修改和查看操作。 2.3 成员岗位管理：管理员可以对岗位信息进行添加、删除、修改和查看操作，包括调整岗位所在的部门。 2.4 菜单管理：管理员可以对展示菜单进行添加、删除、修改和查看操作。 2.5 角色权限管理：管理员可以管理各个角色的权限，控制角色可以访问和操作的功能。
3. 系统主要功能： 3.1 用户 Excel 样例导入功能：用户可以上传包含种苗生长信息的 Excel 表格。 3.2 用户输入期望样例：用户可以输入期望的数据处理和预测结果样例。 3.3 用户导出转换成功的 Excel 文件：经过处理和预测后，用户可以导出包含预测结果的 Excel 文件。 3.4 用户上传 Excel 表格：用户可以上传 Excel 表格，用于数据预处理和生长预测。 3.5 Excel 表格预处理： a。 分割：将一个做了部分标记但包含多个表的 Excel 文件分隔成多个独立的表格，为下一步的处理提供输入。

b. 统一和标准化处理：将经过语义分割处理的多个表格进行统一和标准化处理，使其可以直接用于分析和决策制定。

c. 基本格式转换：进一步处理经过统一化和标准化处理的数据集，使其更加适合进行分析和决策制定。

1. 增强功能：
   1.  4.1 作物生长预测：根据处理后的数据集，预测种苗未来生长高度，为农业生产决策提供参考。

# 技术栈

#### **后端技术栈**

1. Spring Boot
2. Spring Security
3. MyBatis
4. MySQL
5. Redis
6. Spring Cache
7. WebSocket

#### **前端技术栈**

1. Vue
2. ElementUI
3. axios
4. vue-router
5. Vuex
6. WebSocket
7. vue-cli4

# 环境部署

## 准备工作

```Plaintext
JDK >= 1.8 
Mysql >= 5.7.0 
Maven >= 3.0
Python 2.7
Python 3.8
g++
Boost.Python (Mac, Linux)
setuptools
sys 1.147
numpy 1.22.3
matplotlib 3.6.1
xlwt 1.3.0
scipy 1.8.1
pandas 1.4.2
sklearn 0.0.post1
keras 2.3.1
math
datetime
pip 23.0.1
xlrd 2.0.1
```

# 运行系统

1、前往 Github 下载项目（注意下面的操作默认，系统中已有 python，java，node，mysql，redis 等基础环境） 2、导入到 Eclipse，菜单 File -> Import，然后选择 Maven -> Existing Maven Projects，点击 Next> 按钮，选择工作目录，然后点击 Finish 按钮，即可成功导入。 Eclipse 会自动加载 Maven 依赖包，初次加载会比较慢（根据自身网络情况而定）

注意刷新下载 Maven 包 3、部署 Foofah

Linux 上，其他不可部署 python -m pip install -U pip setuptools

docker 部署

```Java
$ docker build -t foofah 
```

Run

```Java
$ docker run -p 8080:8080 foofah
```

Foofah 运行在 [localhost:8080](http://0.0.0.0:8080/)。

安装指南

$ cd foofah

$ python setup.py install

用户指南

Foofah 控制台

从控制台测试 Foofah 针对单个测试用例：

$ cd foofah

$ python foofah.py --input <test_file>

请注意，每个测试用例必须是一个包含一个 JSON 对象的 JSON 文件，该对象具有两个成员 InputTable 和 OutputTable，两者都是字符串的 2D 数组，表示用户提供的输入输出示例。

要了解其他命令行参数选项：

```Java
$ python foofah.py --help
$ python foofah_server.py
```

默认情况下，服务将在 localhost：8080 上可用。

4.增强功能配置 python 环境

说明：

- 在配置环境时，需要注意先配好清华镜像
- 除了生长预测之外，其他的文件基本上只需要 pip install 即可

但如果在运行时抛出异常时，需要进行版本更换

以下是执行成功的 python 环境以及版本（仅供参考）:

- sys 1.147
- numpy 1.22.3
- matplotlib 3.6.1
- lwt 1.3.0

5、打开项目运行 Application 出现

6、打开浏览器，输入：（[http://localhost:80 （opens new window）](http://localhost:80)） （默认账户/密码 admin/admin123） 若能正确展示登录页面，并能成功登录，菜单及页面展示正常，则表明环境搭建成功

建议使用 Git 克隆，因为克隆的方式可以和随时保持更新同步

# 必要配置

- 修改数据库连接，编辑 resources 目录下的 application-druid.yml

```Plaintext
# 数据源配置
spring:
    datasource:
        type: com.alibaba.druid.pool.DruidDataSource
        driverClassName: com.mysql.cj.jdbc.Driver
        druid:
            # 主库数据源
            master:
                url: 数据库地址
                username: 数据库账号
                password: 数据库密码
```

- 修改服务器配置，编辑 resources 目录下的 application.yml

```Plaintext
# 开发环境配置
server:
  # 服务器的HTTP端口，默认为80
  port: 端口
  servlet:
    # 应用的访问路径
    context-path: /应用路径
```

## 部署系统

- 部署工程文件

1、jar 部署方式 使用命令行执行：java –jar # 项目地址：https://github.com/Coder-RSL/InfoParse 项目功能说明

1. 用户模块： 1.1 用户登录模块：用户输入用户名和密码进行登录，登录成功后可访问和操作其权限范围内的功能。 1.2 用户注册模块：用户输入必要的注册信息（如用户名、密码、电子邮件等），完成注册后可登录系统。 1.3 用户个人信息修改模块：用户可以修改自己的个人信息（如姓名、联系方式等）。 1.4 用户注销账户功能：用户在完成操作后，可以选择注销账户，退出系统。 1.5 日志管理：用户可以查看系统日志，了解系统运行状况。
2. 管理员模块： 2.1 成员角色管理：管理员可以为用户分配不同的角色，赋予用户不同的操作权限。 2.2 部门管理功能：管理员可以对部门信息进行添加、删除、修改和查看操作。 2.3 成员岗位管理：管理员可以对岗位信息进行添加、删除、修改和查看操作，包括调整岗位所在的部门。 2.4 菜单管理：管理员可以对展示菜单进行添加、删除、修改和查看操作。 2.5 角色权限管理：管理员可以管理各个角色的权限，控制角色可以访问和操作的功能。
3. 系统主要功能： 3.1 用户 Excel 样例导入功能：用户可以上传包含种苗生长信息的 Excel 表格。 3.2 用户输入期望样例：用户可以输入期望的数据处理和预测结果样例。 3.3 用户导出转换成功的 Excel 文件：经过处理和预测后，用户可以导出包含预测结果的 Excel 文件。 3.4 用户上传 Excel 表格：用户可以上传 Excel 表格，用于数据预处理和生长预测。 3.5 Excel 表格预处理： a。 分割：将一个做了部分标记但包含多个表的 Excel 文件分隔成多个独立的表格，为下一步的处理提供输入。

b. 统一和标准化处理：将经过语义分割处理的多个表格进行统一和标准化处理，使其可以直接用于分析和决策制定。

c. 基本格式转换：进一步处理经过统一化和标准化处理的数据集，使其更加适合进行分析和决策制定。

1. 增强功能：
   1.  4.1 作物生长预测：根据处理后的数据集，预测种苗未来生长高度，为农业生产决策提供参考。

# 技术栈

#### **后端技术栈**

1. Spring Boot
2. Spring Security
3. MyBatis
4. MySQL
5. Redis
6. Spring Cache
7. WebSocket

#### **前端技术栈**

1. Vue
2. ElementUI
3. axios
4. vue-router
5. Vuex
6. WebSocket
7. vue-cli4

# 环境部署

## 准备工作

```Plaintext
JDK >= 1.8 
Mysql >= 5.7.0 
Maven >= 3.0
Python 2.7
Python 3.8
g++
Boost.Python (Mac, Linux)
setuptools
sys 1.147
numpy 1.22.3
matplotlib 3.6.1
xlwt 1.3.0
scipy 1.8.1
pandas 1.4.2
sklearn 0.0.post1
keras 2.3.1
math
datetime
pip 23.0.1
xlrd 2.0.1
```

# 运行系统

1、前往 Github 下载项目（注意下面的操作默认，系统中已有 python，java，node，mysql，redis 等基础环境） 2、导入到 Eclipse，菜单 File -> Import，然后选择 Maven -> Existing Maven Projects，点击 Next> 按钮，选择工作目录，然后点击 Finish 按钮，即可成功导入。 Eclipse 会自动加载 Maven 依赖包，初次加载会比较慢（根据自身网络情况而定）

注意刷新下载 Maven 包 3、部署 Foofah

Linux 上，其他不可部署 python -m pip install -U pip setuptools

docker 部署

```Java
$ docker build -t foofah 
```

Run

```Java
$ docker run -p 8080:8080 foofah
```

Foofah 运行在 [localhost:8080](http://0.0.0.0:8080/)。

安装指南

$ cd foofah

$ python setup.py install

用户指南

Foofah 控制台

从控制台测试 Foofah 针对单个测试用例：

$ cd foofah

$ python foofah.py --input <test_file>

请注意，每个测试用例必须是一个包含一个 JSON 对象的 JSON 文件，该对象具有两个成员 InputTable 和 OutputTable，两者都是字符串的 2D 数组，表示用户提供的输入输出示例。

要了解其他命令行参数选项：

```Java
$ python foofah.py --help
$ python foofah_server.py
```

默认情况下，服务将在 localhost：8080 上可用。

4.增强功能配置 python 环境

说明：

- 在配置环境时，需要注意先配好清华镜像
- 除了生长预测之外，其他的文件基本上只需要 pip install 即可

但如果在运行时抛出异常时，需要进行版本更换

以下是执行成功的 python 环境以及版本（仅供参考）:

- sys 1.147
- numpy 1.22.3
- matplotlib 3.6.1
- lwt 1.3.0

5、打开项目运行 Application 出现

6、打开浏览器，输入：（[http://localhost:80 （opens new window）](http://localhost:80)） （默认账户/密码 admin/admin123） 若能正确展示登录页面，并能成功登录，菜单及页面展示正常，则表明环境搭建成功

建议使用 Git 克隆，因为克隆的方式可以和随时保持更新同步

# 必要配置

- 修改数据库连接，编辑 resources 目录下的 application-druid.yml

```Plaintext
# 数据源配置
spring:
    datasource:
        type: com.alibaba.druid.pool.DruidDataSource
        driverClassName: com.mysql.cj.jdbc.Driver
        druid:
            # 主库数据源
            master:
                url: 数据库地址
                username: 数据库账号
                password: 数据库密码
```

- 修改服务器配置，编辑 resources 目录下的 application.yml

```Plaintext
# 开发环境配置
server:
  # 服务器的HTTP端口，默认为80
  port: 端口
  servlet:
    # 应用的访问路径
    context-path: /应用路径
```

## 部署系统

- 部署工程文件

1、jar 部署方式 使用命令行执行：java –jar projectName.jar

2、war 部署方式 ruoyi/pom.xml 中的 packaging 修改为 war，放入 tomcat 服务器 webapps

```Plaintext
   <packaging>war</packaging>
```

## 常见问题

1. 如果使用 Mac 需要修改 application.yml 文件路径 profile
2. 如果使用 Linux 提示表不存在，设置大小写敏感配置在/etc/my.cnf 添加 lower_case_table_names=1，重启 MYSQL 服务
3. 如果提示当前权限不足，无法写入文件请检查 application.yml 中的 profile 路径或 logback.xml 中的 log.path 路径是否有可读可写操作权限projectName.jar

2、war 部署方式 ruoyi/pom.xml 中的 packaging 修改为 war，放入 tomcat 服务器 webapps

```Plaintext
   <packaging>war</packaging>
```

## 常见问题

1. 如果使用 Mac 需要修改 application.yml 文件路径 profile
2. 如果使用 Linux 提示表不存在，设置大小写敏感配置在/etc/my.cnf 添加 lower_case_table_names=1，重启 MYSQL 服务
3. 如果提示当前权限不足，无法写入文件请检查 application.yml 中的 profile 路径或 logback.xml 中的 log.path 路径是否有可读可写操作权限
