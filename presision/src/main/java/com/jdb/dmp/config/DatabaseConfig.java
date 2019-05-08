package com.jdb.dmp.config;

import com.jdb.dmp.datasource.DataSourceAspect;
import com.jdb.dmp.datasource.DynamicDataSource;
import com.jdb.dmp.datasource.DynamicDataSourceHolder;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qimwang on 6/16/16.
 */
@Configuration
@EnableAspectJAutoProxy
public class DatabaseConfig {

    @Value("${datasource.dmp.url}")
    String dmpUrl;

    @Value("${datasource.dmp.username}")
    String dmpUsername;

    @Value("${datasource.dmp.password}")
    String dmpPassword;

    @Value("${datasource.bigdata1.jdb.url}")
    String bigdata1JdbUrl;

    @Value("${datasource.bigdata1.jdb.username}")
    String bigdata1JdbUsername;

    @Value("${datasource.bigdata1.jdb.password}")
    String bigdata1JdbPassword;

    @Value("${datasource.bigdata1.passport.url}")
    String bigdata1PassportUrl;

    @Value("${datasource.bigdata1.passport.username}")
    String bigdata1PassportUsername;

    @Value("${datasource.bigdata1.passport.password}")
    String bigdata1PassportPassword;

    @Value("${datasource.bigdata2.jdb.url}")
    String bigdata2JdbUrl;

    @Value("${datasource.bigdata2.jdb.username}")
    String bigdata2JdbUsername;

    @Value("${datasource.bigdata2.jdb.password}")
    String bigdata2JdbPassword;

    @Value("${datasource.dhb1.url}")
    String dhb1Url;

    @Value("${datasource.dhb2.url}")
    String dhb2Url;

    @Value("${datasource.dhb3.url}")
    String dhb3Url;

    @Value("${datasource.dhb4.url}")
    String dhb4Url;

    @Value("${datasource.dhb5.url}")
    String dhb5Url;

    @Value("${datasource.dhb6.url}")
    String dhb6Url;

    @Value("${datasource.dhb7.url}")
    String dhb7Url;

    @Value("${datasource.dhb8.url}")
    String dhb8Url;

    @Value("${datasource.dhb9.url}")
    String dhb9Url;

    @Value("${datasource.dhb10.url}")
    String dhb10Url;

    @Value("${datasource.dhb11.url}")
    String dhb11Url;

    @Value("${datasource.dhb12.url}")
    String dhb12Url;

    @Value("${datasource.dhb13.url}")
    String dhb13Url;

    @Value("${datasource.dhb14.url}")
    String dhb14Url;

    @Value("${datasource.dhb15.url}")
    String dhb15Url;

    @Value("${datasource.dhb16.url}")
    String dhb16Url;

    @Value("${datasource.ur.url}")
    String urUrl;



    @Bean(name="dataSource")
    public DataSource dataSource()
    {
        DynamicDataSource dataSource = new DynamicDataSource();

        BasicDataSource dmpDataSource = new BasicDataSource();
        dmpDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dmpDataSource.setUrl(dmpUrl);
        dmpDataSource.setUsername(dmpUsername);
        dmpDataSource.setPassword(dmpPassword);
        dmpDataSource.setInitialSize(10);
        dmpDataSource.setMaxActive(50);
        dmpDataSource.setMaxIdle(10);
        dmpDataSource.setMinIdle(8);
        dmpDataSource.setTimeBetweenEvictionRunsMillis(60000);
        dmpDataSource.setRemoveAbandoned(true);


        BasicDataSource bigdata1jdbDataSource = new BasicDataSource();
        bigdata1jdbDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        bigdata1jdbDataSource.setUrl(bigdata1JdbUrl);
        bigdata1jdbDataSource.setUsername(bigdata1JdbUsername);
        bigdata1jdbDataSource.setPassword(bigdata1JdbPassword);
        bigdata1jdbDataSource.setInitialSize(10);
        bigdata1jdbDataSource.setMaxActive(50);
        bigdata1jdbDataSource.setMinIdle(8);
        bigdata1jdbDataSource.setMaxIdle(10);
        bigdata1jdbDataSource.setTimeBetweenEvictionRunsMillis(60000);
        dmpDataSource.setRemoveAbandoned(true);

        BasicDataSource bigdata1passportDataSource = new BasicDataSource();
        bigdata1passportDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        bigdata1passportDataSource.setUrl(bigdata1PassportUrl);
        bigdata1passportDataSource.setUsername(bigdata1PassportUsername);
        bigdata1passportDataSource.setPassword(bigdata1PassportPassword);
        bigdata1passportDataSource.setInitialSize(10);
        bigdata1passportDataSource.setMaxActive(50);
        bigdata1passportDataSource.setMinIdle(8);
        bigdata1passportDataSource.setMaxIdle(10);
        bigdata1passportDataSource.setTimeBetweenEvictionRunsMillis(60000);
        bigdata1passportDataSource.setRemoveAbandoned(true);

        BasicDataSource bigdata2jdbDataSource = new BasicDataSource();
        bigdata2jdbDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        bigdata2jdbDataSource.setUrl(bigdata2JdbUrl);
        bigdata2jdbDataSource.setUsername(bigdata2JdbUsername);
        bigdata2jdbDataSource.setPassword(bigdata2JdbPassword);
        bigdata2jdbDataSource.setInitialSize(10);
        bigdata2jdbDataSource.setMaxActive(50);
        bigdata2jdbDataSource.setMinIdle(8);
        bigdata2jdbDataSource.setMaxIdle(10);
        bigdata2jdbDataSource.setRemoveAbandoned(true);


        BasicDataSource dhb1 = new BasicDataSource();
        dhb1.setDriverClassName("com.mysql.jdbc.Driver");
        dhb1.setUrl(dhb1Url);
        dhb1.setUsername(bigdata2JdbUsername);
        dhb1.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb2 = new BasicDataSource();
        dhb2.setDriverClassName("com.mysql.jdbc.Driver");
        dhb2.setUrl(dhb2Url);
        dhb2.setUsername(bigdata2JdbUsername);
        dhb2.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb3 = new BasicDataSource();
        dhb3.setDriverClassName("com.mysql.jdbc.Driver");
        dhb3.setUrl(dhb3Url);
        dhb3.setUsername(bigdata2JdbUsername);
        dhb3.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb4 = new BasicDataSource();
        dhb4.setDriverClassName("com.mysql.jdbc.Driver");
        dhb4.setUrl(dhb4Url);
        dhb4.setUsername(bigdata2JdbUsername);
        dhb4.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb5 = new BasicDataSource();
        dhb5.setDriverClassName("com.mysql.jdbc.Driver");
        dhb5.setUrl(dhb5Url);
        dhb5.setUsername(bigdata2JdbUsername);
        dhb5.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb6 = new BasicDataSource();
        dhb6.setDriverClassName("com.mysql.jdbc.Driver");
        dhb6.setUrl(dhb6Url);
        dhb6.setUsername(bigdata2JdbUsername);
        dhb6.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb7 = new BasicDataSource();
        dhb7.setDriverClassName("com.mysql.jdbc.Driver");
        dhb7.setUrl(dhb7Url);
        dhb7.setUsername(bigdata2JdbUsername);
        dhb7.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb8 = new BasicDataSource();
        dhb8.setDriverClassName("com.mysql.jdbc.Driver");
        dhb8.setUrl(dhb8Url);
        dhb8.setUsername(bigdata2JdbUsername);
        dhb8.setPassword(bigdata2JdbPassword);


        BasicDataSource dhb9 = new BasicDataSource();
        dhb9.setDriverClassName("com.mysql.jdbc.Driver");
        dhb9.setUrl(dhb9Url);
        dhb9.setUsername(bigdata2JdbUsername);
        dhb9.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb10 = new BasicDataSource();
        dhb10.setDriverClassName("com.mysql.jdbc.Driver");
        dhb10.setUrl(dhb10Url);
        dhb10.setUsername(bigdata2JdbUsername);
        dhb10.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb11 = new BasicDataSource();
        dhb11.setDriverClassName("com.mysql.jdbc.Driver");
        dhb11.setUrl(dhb11Url);
        dhb11.setUsername(bigdata2JdbUsername);
        dhb11.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb12 = new BasicDataSource();
        dhb12.setDriverClassName("com.mysql.jdbc.Driver");
        dhb12.setUrl(dhb12Url);
        dhb12.setUsername(bigdata2JdbUsername);
        dhb12.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb13 = new BasicDataSource();
        dhb13.setDriverClassName("com.mysql.jdbc.Driver");
        dhb13.setUrl(dhb13Url);
        dhb13.setUsername(bigdata2JdbUsername);
        dhb13.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb14 = new BasicDataSource();
        dhb14.setDriverClassName("com.mysql.jdbc.Driver");
        dhb14.setUrl(dhb14Url);
        dhb14.setUsername(bigdata2JdbUsername);
        dhb14.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb15 = new BasicDataSource();
        dhb15.setDriverClassName("com.mysql.jdbc.Driver");
        dhb15.setUrl(dhb15Url);
        dhb15.setUsername(bigdata2JdbUsername);
        dhb15.setPassword(bigdata2JdbPassword);

        BasicDataSource dhb16 = new BasicDataSource();
        dhb16.setDriverClassName("com.mysql.jdbc.Driver");
        dhb16.setUrl(dhb16Url);
        dhb16.setUsername(bigdata2JdbUsername);
        dhb16.setPassword(bigdata2JdbPassword);

        BasicDataSource ur = new BasicDataSource();
        ur.setDriverClassName("com.mysql.jdbc.Driver");
        ur.setUrl(urUrl);
        ur.setUsername(bigdata2JdbUsername);
        ur.setPassword(bigdata2JdbPassword);


//        BasicDataSource phoenixDataSource = new BasicDataSource();
//        phoenixDataSource.setDriverClassName("org.apache.phoenix.jdbc.PhoenixDriver");
//        phoenixDataSource.setUrl("jdbc:phoenix:100.73.42.4:2181");
//        phoenixDataSource.setUsername("root");
//        phoenixDataSource.setPassword("123456");

        Map<Object, Object> targetDataSources = new HashMap<>(20);
        targetDataSources.put(DynamicDataSourceHolder.DMP_DATA_SOURCE, dmpDataSource);
        targetDataSources.put(DynamicDataSourceHolder.BIGDATA1_JDB_DATA_SOURCE, bigdata1jdbDataSource);
        targetDataSources.put(DynamicDataSourceHolder.BIGDATA1_PASSPORT_DATA_SOURCE, bigdata1passportDataSource);
        targetDataSources.put(DynamicDataSourceHolder.BIGDATA2_JDB_DATA_SOURCE, bigdata2jdbDataSource);
        targetDataSources.put(DynamicDataSourceHolder.DHB1_DATASOURCE, dhb1);
        targetDataSources.put(DynamicDataSourceHolder.DHB2_DATASOURCE, dhb2);
        targetDataSources.put(DynamicDataSourceHolder.DHB3_DATASOURCE, dhb3);
        targetDataSources.put(DynamicDataSourceHolder.DHB4_DATASOURCE, dhb4);
        targetDataSources.put(DynamicDataSourceHolder.DHB5_DATASOURCE, dhb5);
        targetDataSources.put(DynamicDataSourceHolder.DHB6_DATASOURCE, dhb6);
        targetDataSources.put(DynamicDataSourceHolder.DHB7_DATASOURCE, dhb7);
        targetDataSources.put(DynamicDataSourceHolder.DHB8_DATASOURCE, dhb8);
        targetDataSources.put(DynamicDataSourceHolder.DHB9_DATASOURCE, dhb9);
        targetDataSources.put(DynamicDataSourceHolder.DHB10_DATASOURCE, dhb10);
        targetDataSources.put(DynamicDataSourceHolder.DHB11_DATASOURCE, dhb11);
        targetDataSources.put(DynamicDataSourceHolder.DHB12_DATASOURCE, dhb12);
        targetDataSources.put(DynamicDataSourceHolder.DHB13_DATASOURCE, dhb13);
        targetDataSources.put(DynamicDataSourceHolder.DHB14_DATASOURCE, dhb14);
        targetDataSources.put(DynamicDataSourceHolder.DHB15_DATASOURCE, dhb15);
        targetDataSources.put(DynamicDataSourceHolder.DHB16_DATASOURCE, dhb16);

        targetDataSources.put(DynamicDataSourceHolder.UR_DATASOURCE, ur);


//        targetDataSources.put(DynamicDataSourceHolder.PHOENIX_DATA_SOURCE, phoenixDataSource);
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(dmpDataSource);

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception
    {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        return sessionFactory.getObject();
    }

    @Bean
    public DataSourceAspect dataSourceAspect()
    {
        return new DataSourceAspect();
    }
////    @Bean(name="dmpSqlSessionFactoryBean")
////    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dmpDataSource) throws Exception
////    {
////        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
////        sessionFactoryBean.setDataSource(dmpDataSource);
////        return sessionFactoryBean;
////    }
//
//
//    public static SqlSessionFactory sqlSessionFactory1() throws Exception
//    {
//        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//        return sessionFactory.getObject();
//    }
//
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer1()
//    {
//        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
//        configurer.setBasePackage("com.jdb.dmp.ditui.dao");
//        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory1");
//        return configurer;
//    }
//
//   @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer2()
//   {
//       MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//       mapperScannerConfigurer.setBasePackage("com.jdb.dmp.dao");
//       mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory2");
//       return mapperScannerConfigurer;
//   }

}
