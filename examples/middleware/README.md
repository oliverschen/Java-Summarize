## docker 中安装
### hbase

#### 安装
```bash
docker pull harisekhon/hbase:1.3
docker run -d --name hbase -p 2181:2181 -p 16010:16010 -p 16020:16020 -p 16030:16030 -p 16000:16000 harisekhon/hbase
```
#### 配置 hostname
```bash
docker exec -it hbase bash
> hostname
> 86151fc55250
```
配置 hostname 到 hosts 文件中
```properties
127.0.0.0 86151fc55250
```