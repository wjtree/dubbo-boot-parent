package com.app.data.job;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @DisallowConcurrentExecution 添加到 Job 类后，Quartz 将不会同时执行多个 Job 实例
 * @PersistJobDataAfterExecution 添加到 Job 类后，
 * 表示 Quartz 将会在成功执行 execute() 方法后（没有抛出异常）更新 JobDetail 的 JobDataMap，
 * 下一次执行相同的任务（JobDetail）将会得到更新后的值，而不是原始的值
 */
@Log4j2
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class DemoJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        // 改变前日期
        String beforeDate = dataMap.getString("date");

        // 计算改变后的日期
        DateTime dateTime = StringUtils.isNotBlank(beforeDate) ? DateTime.parse(beforeDate) : DateTime.now();
        dateTime = dateTime.plusDays(1);
        // 改变后日期
        String afterDate = dateTime.toString("yyyy-MM-dd");
        // 将改变后的参数值存入作业上下文
        dataMap.put("date", afterDate);

        // 发送 RabbitMQ 消息
//        RabbitMQUtil.send(afterDate);
//        ActiveMQUtil.sendQueueMsg("hi,queue");
//        ActiveMQUtil.sendTopicMsg("hi,topic");

        if (log.isInfoEnabled())
            log.info("{} 执行 {} 定时任务完成，改变前日期：{}，改变后日期：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss.SSS"), jobKey, beforeDate, afterDate);
    }
}