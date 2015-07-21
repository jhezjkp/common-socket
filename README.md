# common-socket
a common game socket module

## 包结构

[包头]+[包体]

包头：包长(4字节int，不包含本身)+模块号+命令号


## 协议消息

规则：
+ 继承com.game.socket.Message
+ 有com.game.socket.annotation.Protocol注解
+ static、final、transient修饰的字段不传输
