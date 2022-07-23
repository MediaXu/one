package com.qf.vhr.admin;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
public class MainTest {

    @Test
    void test01() {
        FastAutoGenerator.create("jdbc:mysql:///vhr?serviceTimezone=Asia/Shanghai",
                "root", "root")
                .globalConfig(builder -> {
                    builder.author("media_xu") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("E:\\work\\OnTest\\vhr\\vhr-framework\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.qf.vhr") // 设置父包名
                            .moduleName("framework") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\work\\OnTest\\vhr\\vhr-framework\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("hr") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
