package cn.sino.share.limit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 令牌桶限流
 */
@RequestMapping("/token")
@RestController
@Slf4j
public class TokenLimit {
    //  每秒向桶内 装多少令牌
    private Integer capacity = 2;
    // 桶内能装多少令牌
    private Integer totalToken = 100;
    //现在桶内令牌数量
    private Integer curTokenNum = 0;
    // 每次消耗数量
    private Integer consume = 1;

    // 时间戳
    private LocalDateTime outTime;
    // 周期 结束时间
    public LocalDateTime requestEndTime;

    // 请求数量
    private AtomicInteger requestCount = new AtomicInteger(0);

    @GetMapping("/rate")
    public Boolean rateTest() {
        return tryAcquire();
    }

    public synchronized boolean tryAcquire() {
        LocalDateTime now = LocalDateTime.now();
        int intoToken = 0;
        if (outTime == null || requestEndTime.isBefore(now)) {
            // 加入时间计算 每秒钟 累加入令牌桶
            // 不和leakyBucket中一样直接 outTime = now; 以防止上一秒钟一直在请求导致下一秒和上一秒的计算差值拉不开。
            LocalDateTime last = requestEndTime == null ? now.plusSeconds(-1) : requestEndTime;
            outTime = now;
            requestEndTime = now.plusSeconds(1);
            long seconds = Duration.between(last, requestEndTime).toSeconds();
            intoToken = (int) (seconds * capacity);
        }

        if (intoToken + curTokenNum > totalToken) {
            // 令牌已满
            curTokenNum = totalToken - consume;
            System.out.println("TRUE");
            return true;
        } else if (intoToken + curTokenNum >= consume) {
            // 令牌还有余量
            curTokenNum = intoToken + curTokenNum - consume;
            System.out.println("TRUE");
            return true;
        } else {
            System.out.println("FALSE");
            return false;
        }
    }

}
