package cn.sino.share.limit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计数器限流
 *
 * @auth winter
 * */
@RequestMapping("/limit")
@RestController
public class CounterLimit {
    // 时间间隔 秒
    private  Integer burstCapacity = 60;
    // 速率限制  请求数
    private  Integer replenishRate = 5;
    // 请求数量
    private  AtomicInteger requestCount = new AtomicInteger(0);
    // 请求开始时间
    public  LocalDateTime requestTime;
    // 和结束时间
    public  LocalDateTime requestEndTime;

    public void setRequestTime(){
        LocalDateTime now = LocalDateTime.now();
        if (requestTime == null || requestEndTime.isBefore(now)){
            requestCount =  new AtomicInteger(0);
            requestTime = now;
            requestEndTime= LocalDateTime.now().plusSeconds(burstCapacity);
        }
    }

    @GetMapping("/rate")
    public Boolean rateTest(){
        // 设置开始时间
        this.setRequestTime();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return Boolean.FALSE;
        }
        HttpServletRequest request = attributes.getRequest();
        String requestUrl = request.getRequestURI();
        return this.rateLimit(requestUrl);
    }
    /**
     * 限流
     * @param uri 请求接口
     * */
    public final Boolean rateLimit(String uri){
        if (requestCount.get() >= replenishRate){
            System.out.println("FALSE");
            return Boolean.FALSE;
        }
        int current;
        int next;
        for (;;){
            current = requestCount.get();
            if (current > replenishRate){
                System.out.println("FALSE");
                return Boolean.FALSE;
            }
            next = current + 1;
            if (requestCount.compareAndSet(current, next)){
                System.out.println("TRUE");
                return Boolean.TRUE;
            }
        }
    }



}
