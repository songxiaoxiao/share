package cn.sino.share.limit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 漏桶限流
 * */
@RequestMapping("/bucket")
@RestController
public class LeakyBucket {
    // 水容量
    private Integer totalWater = 5;
    // 流速
    private Integer capacity = 1;
    // 留出时间
    private LocalDateTime outTime = LocalDateTime.now();

    @GetMapping("/rate")
    public Boolean rateTest() {
        return tryAcquire();
    }

    /**
     * 尝试 获得请求机会
     * */
    public synchronized boolean tryAcquire() {
        LocalDateTime now = LocalDateTime.now();

        long seconds = Duration.between(outTime, now).toSeconds();
        int outWater = Math.toIntExact(seconds * capacity);           // 计算这段时间匀速流出的水
        outTime = now;
        if (outWater > totalWater) {
            // 请求已全部处理完毕/
            totalWater = 1;
            System.out.println(Boolean.TRUE);
            return true;
        } else {
            // 还有未处理的请求
            totalWater -= outWater;
            if (totalWater + 1 <= capacity) {
                totalWater += 1;
                System.out.println(totalWater);
                System.out.println(Boolean.TRUE);
                return true;
            } else {
                System.out.println(Boolean.FALSE);
                return false;
            }
        }
    }
}
