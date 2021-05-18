### 限流解决方案
>  Gateway 通过redis + lua 实现 |
>  Guava RateLimiter 实现   |
>  Sentinel 服务治理限流 ｜
>  nginx 扩展ngx_http_limit_req_module模块
### 限流的三种算法

##### 计数器算法
一分钟访问次数限制5个。设置一个计数器 每次进来一个请求就+1.当他达到5则对请求进行限制

![image](https://user-images.githubusercontent.com/11972980/118577863-bea33100-b7bd-11eb-8257-cea802a49bae.png)


##### 漏桶算法
水先进入到漏桶里，漏桶以一定的速度出水，当水流入速度过大会直接溢出，可以看出漏桶算法能强行限制数据的传输速率

![image](https://user-images.githubusercontent.com/11972980/118577894-cb278980-b7bd-11eb-882a-e43c70ac4856.png)



##### 令牌桶算法
以一个恒定的速度往桶里放入令牌，而如果请求需要被处理，则需要先从桶里获取一个令牌，当桶里没有令牌可取时，则拒绝服务
![image](https://user-images.githubusercontent.com/11972980/118577933-d7134b80-b7bd-11eb-84ad-1ca9de46f7d4.png)

##### 测试 限流步骤
启动Share,分别访问
1. localhost:10000/limit/rate 计数器限流
2. localhost:10000/bucket/rate 漏桶限流
3. localhost:10000/token/rate 令牌桶限流
##### 算法思考
计数器（临界值问题，时间段内集中请求）
漏桶（匀速消耗，难免有溢出问题）
令牌桶 （桶量有了，是否可以考虑递增桶容量？）
