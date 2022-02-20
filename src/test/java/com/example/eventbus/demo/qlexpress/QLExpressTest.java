package com.example.eventbus.demo.qlexpress;

import com.alibaba.fastjson.JSON;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @name: task_event
 * @description:
 * @author: zhxy
 * @create: 2022/2/13 17:59
 **/
@SpringBootTest
public class QLExpressTest {


    @Test
    public void testQlExpress() throws Exception {
        ExpressRunner expressRunner = new ExpressRunner();

        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        Object error = expressRunner.execute("(false)||(true&&false)", context, Arrays.asList("error"), false, false);

        System.out.println(JSON.toJSONString(error));
    }
}
