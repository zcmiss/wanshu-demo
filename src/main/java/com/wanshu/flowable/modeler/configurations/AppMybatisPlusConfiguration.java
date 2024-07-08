package com.wanshu.flowable.modeler.configurations;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

@MapperScan(basePackages = {
        "com.wanshu.wanshu.mapper",
        "com.wanshu.flowable.mapper"
},
        sqlSessionTemplateRef = "appSqlSessionTemplate",
        sqlSessionFactoryRef = "appSqlSessionFactory"
)
@EnableConfigurationProperties(MybatisPlusProperties.class)
@Configuration
public class AppMybatisPlusConfiguration extends AbstractMybatisPlusConfiguration {

    @Bean(name = "appSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource,
                                                   MybatisPlusProperties properties,
                                                   ResourceLoader resourceLoader,
                                                   ApplicationContext applicationContext) throws Exception {
        // 显示添加分页拦截器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return getSqlSessionFactory(dataSource,
                properties,
                resourceLoader,
                new Interceptor[]{interceptor},
                null,
                applicationContext);
    }

    @Bean(name = "appSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(MybatisPlusProperties properties,
                                                 @Qualifier("appSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return getSqlSessionTemplate(sqlSessionFactory, properties);
    }
}


