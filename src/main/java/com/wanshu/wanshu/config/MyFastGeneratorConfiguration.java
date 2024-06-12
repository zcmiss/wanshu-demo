package com.wanshu.wanshu.config;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MyFastGeneratorConfiguration {
    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/boge?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true"
                , "root", "123456")
                .globalConfig(builder -> {
                    builder.author("wanshu") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://wanshu"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.wanshu") // 设置父包名
                            .moduleName("wanshu") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://wanshu")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_user","sys_role","sys_user_role","sys_menu","sys_role_menu","sys_oplog") // 设置需要生成的表名
                            .addTablePrefix("sys_"); // 设置过滤表前缀

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
