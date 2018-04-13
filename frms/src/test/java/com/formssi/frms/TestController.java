package com.formssi.frms;

import org.apache.shiro.SecurityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 	perform：执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
	get:声明发送一个get请求的方法。MockHttpServletRequestBuilder get(String urlTemplate, Object... urlVariables)：根据uri模板和uri变量值得到一个GET请求方式的。另外提供了其他的请求的方法，如：post、put、delete等。
	param：添加request的参数，如上面发送请求的时候带上了了pcode = root的参数。假如使用需要发送json数据格式的时将不能使用这种方式，可见后面被@ResponseBody注解参数的解决方法
	andExpect：添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确（对返回的数据进行的判断）；
	andDo：添加ResultHandler结果处理器，比如调试时打印结果到控制台（对返回的数据进行的判断）；
	andReturn：最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理（对返回的数据进行的判断）；
	
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestController {
	private MockMvc mvc;
	
	@Autowired
    protected WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        org.apache.shiro.mgt.SecurityManager securityManager =   
            (org.apache.shiro.mgt.SecurityManager)wac.getBean("securityManager");  
        SecurityUtils.setSecurityManager(securityManager);
        //mvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); 
    }

    @Test
    public void testLogin() throws Exception {
    	mvc.perform((MockMvcRequestBuilders.post("/login"))
                .param("username", "admin")
                .param("password", "111111")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                )  
        .andExpect(MockMvcResultMatchers.status().isOk())  
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))  
        .andDo(MockMvcResultHandlers.print()); 
    }
    
    @Test
    public void testMain() throws Exception {
    	mvc.perform(MockMvcRequestBuilders.get("/main").accept(MediaType.APPLICATION_JSON))
    	.andExpect(MockMvcResultMatchers.status().isOk())
    	.andDo(MockMvcResultHandlers.print())
    	.andReturn();
    }
}
