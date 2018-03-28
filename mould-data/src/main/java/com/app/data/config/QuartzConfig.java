package com.app.data.config;

import com.app.core.util.IocUtil;
import com.app.data.job.DemoJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Quartz 配置
 */
@Configuration
public class QuartzConfig {
    @Value("${spring.quartz.scheduler.name}")
    private String schedulerName;
    @Value("${spring.quartz.config.path}")
    private String path;

    /**
     * 作业配置
     *
     * @return JobDetailFactoryBean
     */
    @Bean
    public JobDetailFactoryBean demoJobDetail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setGroup("job");
        jobDetailFactoryBean.setName("demoJobDetail");
        // 任务完成之后是否依然保留到数据库，默认false
        jobDetailFactoryBean.setDurability(true);
        // 当Quartz服务被中止后，再次启动或集群中其他机器接手任务时会尝试恢复执行之前未完成的所有任务，默认false
        jobDetailFactoryBean.setRequestsRecovery(true);
        // 定时作业类
        jobDetailFactoryBean.setJobClass(DemoJob.class);
        // 定时作业参数
        Map<String, Object> map = new HashMap<>();
        map.put("date", "2018-03-10");
        jobDetailFactoryBean.setJobDataAsMap(map);
        return jobDetailFactoryBean;
    }

    /**
     * 触发器配置
     *
     * @return CronTriggerFactoryBean
     */
    @Bean
    public CronTriggerFactoryBean demoJobTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setGroup("trigger");
        cronTriggerFactoryBean.setName("demoJobTrigger");
        cronTriggerFactoryBean.setJobDetail(demoJobDetail().getObject());
        cronTriggerFactoryBean.setStartDelay(5000);
        cronTriggerFactoryBean.setCronExpression("0/5 * * * * ?");
        return cronTriggerFactoryBean;
    }

    /**
     * 调度工厂配置
     *
     * @return SchedulerFactoryBean
     */
    @Bean
    public SchedulerFactoryBean scheduler() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setSchedulerName(schedulerName);
        schedulerFactoryBean.setTriggers(demoJobTrigger().getObject());
        // quartz 配置文件路径
        schedulerFactoryBean.setConfigLocation(new ClassPathResource(path));
        // quartz 数据源
        schedulerFactoryBean.setDataSource(IocUtil.getBean(DataSource.class));
        return schedulerFactoryBean;
    }
}