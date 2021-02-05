## 🗄Mysql

### 关系数据库

#### 设计范式

第一范式（1NF）：关系 R 属于第一范式，当且仅当R中的每一个属性A的值域只包含原子项

第二范式（2NF）：在满足 1NF 的基础上，消除非主属性对码的部分函数依赖

第三范式（3NF）：在满足 2NF 的基础上，消除非主属性对码的传递函数依赖

BC 范式（BCNF）：在满足 3NF 的基础上，消除主属性对码的部分和传递函数依赖

第四范式（4NF）：消除非平凡的多值依赖

第五范式（5NF）：消除一些不合适的连接依赖

### SQL

结构化查询语言包含 6 个部分

1. 数据查询语言（**DQL**: Data Query Language）：其语句，也称为“数据检索语句”，用以从表中获得数据，确定数据怎样在应用程序给出。保留字 SELECT 是 DQL（也是所有 SQL）用得最多的动词，其他 DQL 常用的保留字有 WHERE，ORDER BY，GROUP BY 和 HAVING。这些 DQL 保留字常与其它类型的 SQL 语句一起使用。

2. 数据操作语言（**DML**：Data Manipulation Language）：其语句包括动词 INSERT、UPDATE 和 DELETE。分别用于添加、修改和删除。

3. 事务控制语言（**TCL**）：它的语句能确保被 DML 语句影响的表的所有行及时得以更新。包括COMMIT（提交）命令、SAVEPOINT（保存点）命令、ROLLBACK（回滚）命令。

4. 数据控制语言（**DCL**）：它的语句通过 GRANT 或 REVOKE 实现权限控制，确定单个用户和用户组对数据库对象的访问。某些 RDBMS 可用 GRANT 或 REVOKE 控制对表单个列的访问。

5. 数据定义语言（**DDL**）：其语句包括动词 CREATE,ALTER 和 DROP。在数据库中创建新表或修改、删除表（CREAT TABLE 或 DROP TABLE）；为表加入索引等。

6. 指针控制语言（CCL）：像 DECLARE CURSOR，FETCH INTO 和 UPDATE WHERE CURRENT 用于对一个或多个表单独行的操作。

### Mysql原理

#### 执行流程

##### server层

###### 连接器

管理连接，权限验证，会查找缓存，如果有直接返回

###### 分析器

词法分析，语法分析，会查找缓存，如果有直接返回

###### 优化器

执行计划生产，索引选择

###### 执行器

操作引擎，返回结果

##### 引擎层

InnoDB，MyISAM等存储引擎

##### 索引原理

InnoDB 使用 B+ 树实现聚集索引，数据是按页来分块的，当一个数据被用到时，其附近的数据也通常会马上被使用。

###### InnoDB

1. 聚簇索引：树的叶子节点包含所有数据，典型就是 mysql 主键索引。

###### 问题

1. InnoDB 必须有主键，因为它存储的数据在主键 B+tree 的叶子节点

2. 推荐用整型的自增主键，因为 mysql 主键默认排序存储的，如果使用 UUID 之类的作为主键会造成数据页分裂造成空间利用率下降

###### 稀疏索引（非聚簇索引）

树上没有包含任何数据，只有只想数据列的指针，典型 mysql myISAM 引擎逐渐索引

###### 联合索引

多个字段组成的索引结构

###### 索引优化

1. 全值匹配
2. 最左前缀原则
3. 不在索引列上做任何操作（计算、函数、（自动or手动）类型转换），会导致索引失效而转 向全表扫描

4. 存储引擎不能使用索引中范围条件右边的列 

5. 尽量使用覆盖索引（只访问索引的查询（索引列包含查询列）），减少select *语句 

6. mysql 在使用不等于（！=或者<>）的时候无法使用索引会导致全表扫描，is null,is not null 也无法使用索引

7. like以通配符开头（'$abc...'）mysql索引失效会变成全表扫描操作

8. 字符串不加单引号索引失效，隐式转换问题

9. 少用or或in，用它查询时，mysql不一定使用索引，mysql内部优化器会根据检索比例、 表大小等多个因素整体评估是否使用索引

### 事务

#### 事务可靠性模型 ACID:

Atomicity: 原子性, 一次事务中的操作要么全部成功, 要么全部失败。

Consistency: 一致性, 跨表、跨行、跨事务, 数据库始终保持一致状态。

Isolation: 隔离性, 可见性, 保护事务不会互相干扰, 包含4种隔离级别。

Durability:, 持久性, 事务提交成功后,不会丢数据。如电源故障, 系统崩溃。

InnoDB:  双写缓冲区、故障恢复、操作系统、fsync() 、磁盘存储、缓存、UPS、网络、备份策略

#### 锁

##### 表级锁

1. 意向锁: 表明事务稍后要进行哪种类型的锁定（加表锁时不会发生遍历行查看是否持有行锁）

   1. 共享意向锁(IS): 打算在某些行上设置共享锁

   2. 排他意向锁(IX): 打算对某些行设置排他锁

2. Insert 意向锁: Insert 操作设置的间隙锁

3. 自增锁(AUTO-IN)

4. LOCK TABLES/DDL
5. MDL：MetaDate Lock

##### 行级锁(InnoDB)

1. 记录锁(Record): 始终锁定索引记录，注意隐藏的聚簇索引; 

2. 间隙锁(Gap): 

3. 临键锁(Next-Key): 记录锁+间隙锁的组合; 可“锁定”表中不存在记录

4. 谓词锁(Predicat): 空间索引

#### 事务隔离级别(Isolation)

1. 读未提交: READ UNCOMMITTED

2. 读已提交: READ COMMITTED

3. 可重复读: REPEATABLE READ ✅

4. 可串行化: SERIALIZABLE

事务隔离是数据库的基础特征。

##### MySQL

1. 可以设置全局的默认隔离级别

2. 可以单独设置会话的隔离级别

3. InnoDB 实现与标准之间的差异

###### Mysql事务

1. 未提交: READ UNCOMMITTED

很少使用，不能保证一致性，可能的问题: 脏读、幻读、不可重复读

2. 读已提交: READ COMMITTED

每次查询都会设置和读取自己的新快照，仅支持基于行的 bin-log。UPDATE 优化: 半一致读(semi-consistent read)

不可重复读: 不加锁的情况下, 其他事务 UPDATE 或 DELETE 会对查询结果有影响

幻读(Phantom): 加锁后, 不锁定间隙, 其他事务可以 INSERT。

3. 可重复读: REPEATABLE READ

InnoDB 的默认隔离级别，使用事务第一次读取时创建的快照，多版本技术

4. 串行化: SERIALIZABLE

最严格的级别，事务串行执行，资源消耗最大；

##### 日志

###### undo log

撤消日志，保证事务的原子性。用处: **事务回滚, 一致性读、崩溃恢复**。

记录事务回滚时所需的撤消操作，一条 INSERT 语句，对应一条 DELETE 的 undo log，每个 UPDATE 语句，对应一条相反 UPDATE 的 undo log

###### redo log

重做日志，确保事务的**持久性**，防止事务提交后数据未刷新到磁盘就掉电或崩溃。

1. 事务执行过程中写入 redo log,记录事务对数据页做了哪些修改。

2. 提升性能: WAL(Write-Ahead Logging) 技术, 先写日志, 再写磁盘。

3. 日志文件: ib_logfile0, ib_logfile1

4. 日志缓冲: innodb_log_buffer_size

5. 强刷: fsync()

##### MVCC

多版本并发控制，使 InnoDB 支持一致性读: READ COMMITTED 和 REPEATABLE READ 。 让查询不被阻塞、无需等待被其他事务持有的锁，这种技术手段可以增加并发性能。

###### 流程（查询）

当执行查询 SQL 时会生成一致性视图 read-view，它由执行查询时所有未提交事务 ID 数组（数组里最小 ID 为 min_ID）和已创建的最大事务 ID（max_ID）组成，查询的数据结果要跟 read-view 做比对从而得到快照结果

#### 总结图

<img src="https://github.com/oliverschen/Java-Summarize/blob/main/images/MySQL.png" style="zoom:50%" />