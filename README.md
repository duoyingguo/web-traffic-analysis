# web-traffic-analysis

#四个模块的说明

## tracker： 主要用于采集用户访问网站行为的数据

## tacker-logServer：用于接收tracker发送过来的日志信息，并且存储在本地磁盘文件中

## backend：用于数据的清洗和解析处理，最终入库到Hive数据库中

## frontent：用于数据的分析和可视化，涉及到的sql多一点