![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
# 整个完整系统的参与者
对于一个完整的web项目参与者包括：

- Servlet规范的制定者（已有）
- 实现Servlet规范的Tomcat服务器（已有）
- Spring MVC框架的开发者（手写Spring MVC框架）
- 编写webapp的开发者（用Spring MVC框架的人）

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
# 基本结构搭建
## 创建Maven模块
![image.png](images/1712020799342-9ee1720f-5a83-4a2f-b5da-7fccca5b7fca.png)

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 引入Servlet依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.springmvc</groupId>
    <artifactId>myspringmvc</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>

    <dependencies>
        <!--servlet api-->
        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <version>6.0.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

</project>
```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 配置Tomcat服务器
![image.png](images/1712020903992-0aae56a0-5652-464c-9b43-bb784f75daf2.png)
## 添加web支持
![image.png](images/1712020961226-61820425-2590-4efe-bad2-6220d08a36ea.png)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
         version="6.0">
</web-app>
```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 创建基本类和接口
根据Spring MVC执行流程，目前先创建出以下的类和接口，后期如果需要其他的再添加：
![image.png](images/1712024526752-2594cbc4-c663-43cf-af59-55c43be03292.png)

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
# 部分类和接口的代码完善
## @Controller注解
```java
package org.myspringmvc.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: Controller
 * Description: 用来标注处理器，被标注的处理器，纳入IoC容器的管理。该注解只允许出现在类上，另外可以被反射机制读取。
 * Datetime: 2024/4/2 9:01
 * Author: 老杜@动力节点
 * Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## RequestMethod枚举（新建）
```java
package org.myspringmvc.web.bind.annotation;

/**
 * ClassName: RequestMethod
 * Description: 请求方式枚举
 * Datetime: 2024/4/2 10:35
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public enum RequestMethod {
    GET, POST
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## @RequestMapping注解
```java
package org.myspringmvc.web.bind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: RequestMapping
 * Description: 用来标注处理器方法，允许标注方法和类，可以被反射机制读取。
 * Datetime: 2024/4/2 8:59
 * Author: 老杜@动力节点
 * Version: 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    /**
     * 用来指定请求路径
     * @return
     */
    String[] value();

    /**
     * 用来指定请求方式
     * @return
     */
    RequestMethod method();
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## HandlerMethod
```java
package org.myspringmvc.web.method;

import java.lang.reflect.Method;

/**
 * ClassName: HandlerMethod
 * Description: 处理器方法
 * Datetime: 2024/4/2 8:53
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class HandlerMethod {
    /**
     * 处理器对象
     */
    private Object handler;
    /**
     * 要执行的方法
     */
    private Method method;

    public HandlerMethod() {
    }

    public HandlerMethod(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## HandlerMapping接口
```java
package org.myspringmvc.web.servlet;

import jakarta.servlet.http.HttpServletRequest;

/**
 * ClassName: HandlerMapping
 * Description: 主要是通过请求获取对应的处理器执行链。
 * Datetime: 2024/4/2 8:50
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public interface HandlerMapping {
    /**
     * 根据请求获取处理器执行链。
     * @param request
     * @return
     * @throws Exception
     */
    HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## RequestMappingHandlerMapping
```java
package org.myspringmvc.web.servlet.mvc.method.annotation;

import jakarta.servlet.http.HttpServletRequest;
import org.myspringmvc.web.servlet.HandlerExecutionChain;
import org.myspringmvc.web.servlet.HandlerMapping;

/**
 * ClassName: RequestMappingHandlerMapping
 * Description:
 * Datetime: 2024/4/2 9:44
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class RequestMappingHandlerMapping implements HandlerMapping {
    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        return null;
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## HandlerAdapter接口
```java
package org.myspringmvc.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ClassName: HandlerAdapter
 * Description: 通过处理器适配器调用处理器方法
 * Datetime: 2024/4/2 8:51
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public interface HandlerAdapter {
    /**
     * 执行处理器方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## RequestMappingHandlerAdapter
```java
package org.myspringmvc.web.servlet.mvc.method.annotation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.myspringmvc.web.servlet.HandlerAdapter;
import org.myspringmvc.web.servlet.ModelAndView;

/**
 * ClassName: RequestMappingHandlerAdapter
 * Description:
 * Datetime: 2024/4/2 9:44
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return null;
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## View接口
```java
package org.myspringmvc.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

/**
 * ClassName: View
 * Description:
 * Datetime: 2024/4/2 8:58
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public interface View {
    /**
     * 获取响应的内容类型
     * @return
     */
    String getContentType();

    /**
     * 渲染
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception;
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## InternalResourceView
```java
package org.myspringmvc.web.servlet.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.myspringmvc.web.servlet.View;

import java.util.Map;

/**
 * ClassName: InternalResourceView
 * Description:
 * Datetime: 2024/4/2 10:17
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class InternalResourceView implements View {
    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## ViewResolver接口
```java
package org.myspringmvc.web.servlet;

import java.util.Locale;

/**
 * ClassName: ViewResolver
 * Description:解析逻辑视图名称，返回视图对象
 * Datetime: 2024/4/2 8:58
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public interface ViewResolver {
    /**
     * 解析逻辑视图名称，返回视图对象
     * @param viewName
     * @param locale
     * @return
     * @throws Exception
     */
    View resolveViewName(String viewName, Locale locale) throws Exception;
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## InternalResourceViewResolver
```java
package org.myspringmvc.web.servlet.view;

import org.myspringmvc.web.servlet.View;
import org.myspringmvc.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * ClassName: InternalResourceViewResolver
 * Description:
 * Datetime: 2024/4/2 9:45
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class InternalResourceViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return null;
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## DispatcherServlet
```java
package org.myspringmvc.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * ClassName: DispatcherServlet
 * Description:
 * Datetime: 2024/4/2 8:50
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    /**
     * 处理请求的核心方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## HandlerExecutionChain
```java
package org.myspringmvc.web.servlet;

import java.util.List;

/**
 * ClassName: HandlerExecutionChain
 * Description:
 * Datetime: 2024/4/2 8:55
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class HandlerExecutionChain {
    private Object handler;
    private List<HandlerInterceptor> interceptorList;
    private int interceptorIndex = -1;

    public HandlerExecutionChain() {
    }

    public HandlerExecutionChain(Object handler, List<HandlerInterceptor> interceptorList) {
        this.handler = handler;
        this.interceptorList = interceptorList;
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public List<HandlerInterceptor> getInterceptorList() {
        return interceptorList;
    }

    public void setInterceptorList(List<HandlerInterceptor> interceptorList) {
        this.interceptorList = interceptorList;
    }

    public int getInterceptorIndex() {
        return interceptorIndex;
    }

    public void setInterceptorIndex(int interceptorIndex) {
        this.interceptorIndex = interceptorIndex;
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## HandlerInterceptor拦截器接口
```java
package org.myspringmvc.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ClassName: HandlerInterceptor
 * Description: 拦截器接口
 * Datetime: 2024/4/2 8:54
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public interface HandlerInterceptor {
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## ModelMap类（新建）
```java
package org.myspringmvc.ui;

import java.util.LinkedHashMap;

/**
 * ClassName: ModelMap
 * Description: 将数据存储到域中。
 * Datetime: 2024/4/2 11:07
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class ModelMap extends LinkedHashMap<String, Object> {
    public ModelMap() {
    }

    public ModelMap addAttribute(String name, String value){
        this.put(name, value);
        return this;
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## ModelAndView
```java
package org.myspringmvc.web.servlet;

import org.myspringmvc.ui.ModelMap;

/**
 * ClassName: ModelAndView
 * Description:
 * Datetime: 2024/4/2 8:57
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class ModelAndView {
    private Object view;
    private ModelMap model;

    public ModelAndView() {
    }

    public ModelAndView(Object view, ModelMap model) {
        this.view = view;
        this.model = model;
    }

    public Object getView() {
        return view;
    }

    public void setView(Object view) {
        this.view = view;
    }

    /**
     * 该方法待实现
     * @param viewName
     */
    public void setViewName(String viewName){
        // TODO
    }

    public ModelMap getModel() {
        return model;
    }

    public void setModel(ModelMap model) {
        this.model = model;
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
# webapp开发者写应用
## web.xml文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
         version="6.0">
    
    <!--配置前端控制器-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.myspringmvc.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    
</web-app>
```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
DispatcherServlet的<init-param>的contextConfigLocation可以编写代码了：
```java
@Override
public void init() throws ServletException {
    ServletConfig servletConfig = this.getServletConfig();
    String contextConfigLocation = servletConfig.getInitParameter(Constant.CONTEXT_CONFIG_LOCATION);
    String springMvcXmlPath = getSpringMvcXmlPath(contextConfigLocation);
    System.out.println("Spring MVC配置文件路径解析完成：" + springMvcXmlPath);
}

private String getSpringMvcXmlPath(String contextConfigLocation) throws UnsupportedEncodingException {
    if(contextConfigLocation.startsWith(Constant.CLASSPATH)){
        String path = contextConfigLocation.substring(Constant.CLASSPATH.length()).trim();
        String springMvcXmlPath = Thread.currentThread().getContextClassLoader().getResource(path).getPath();
        // 对路径解码，防止路径中有 % 等字符。
        return URLDecoder.decode(springMvcXmlPath, Charset.defaultCharset());
    }
    return null;
}
```
定义系统常量类：Constant
```java
package org.myspringmvc.web.constant;

/**
 * ClassName: Constant
 * Description:SpringMVC系统常量类
 * Datetime: 2024/4/2 11:28
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class Constant {
    public static final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";
    public static final String CLASSPATH = "classpath:";
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 编写处理器Controller
```java
package com.powernode.springmvc.controller;

import org.myspringmvc.stereotype.Controller;
import org.myspringmvc.web.bind.annotation.RequestMapping;
import org.myspringmvc.web.bind.annotation.RequestMethod;

/**
 * ClassName: UserController
 * Description:
 * Datetime: 2024/4/2 11:38
 * Author: 老杜@动力节点
 * Version: 1.0
 */
@Controller
public class UserController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 编写拦截器
```java
package com.powernode.springmvc.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.myspringmvc.web.servlet.HandlerInterceptor;
import org.myspringmvc.web.servlet.ModelAndView;

/**
 * ClassName: Interceptor1
 * Description:
 * Datetime: 2024/4/2 11:40
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class Interceptor1 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Interceptor1's preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Interceptor1's postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("Interceptor1's afterCompletion");
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
```java
package com.powernode.springmvc.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.myspringmvc.web.servlet.HandlerInterceptor;
import org.myspringmvc.web.servlet.ModelAndView;

/**
 * ClassName: Interceptor2
 * Description:
 * Datetime: 2024/4/2 11:41
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class Interceptor2 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Interceptor2's preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Interceptor2's postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("Interceptor2's afterCompletion");
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 编写springmvc.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans>
    <!--组件扫描-->
    <component-scan base-package="com.powernode.springmvc.controller"/>
    <!--视图解析器-->
    <bean class="org.myspringmvc.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
    <!--拦截器-->
    <interceptors>
        <bean class="com.powernode.springmvc.interceptors.Interceptor1"/>
        <bean class="com.powernode.springmvc.interceptors.Interceptor2"/>
    </interceptors>
</beans>
```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)

InternalResourceViewResolver类中添加属性：suffix和prefix
```java
package org.myspringmvc.web.servlet.view;

import org.myspringmvc.web.servlet.View;
import org.myspringmvc.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * ClassName: InternalResourceViewResolver
 * Description:
 * Datetime: 2024/4/2 9:45
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class InternalResourceViewResolver implements ViewResolver {
    private String suffix;
    private String prefix;

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return null;
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 提供视图
![image.png](images/1712029821395-5e9b0d67-f6da-4e8f-8875-45cff7eb1899.png)
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>index jsp</title>
</head>
<body>
<h1>动力节点：手写Spring MVC框架</h1>
</body>
</html>
```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
# 服务器启动阶段的处理
## 分析服务器启动阶段都需要初始化什么

1. 初始化Spring容器
   1. 组件扫描包下的类纳入IoC容器的管理。
   2. 创建视图解析器对象
   3. 创建所有的拦截器对象
   4. 扫描这个包下所有的类：org.myspringmvc.web.servlet.mvc.method.annotation，全部实例化，纳入IoC容器管理
2. 初始化HandlerMapping
3. 初始化HandlerAdapter
4. 初始化ViewResolver

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 初始化Spring容器
Spring容器：ApplicationContext
Spring Web容器：WebApplicationContext
### 组件扫描
添加解析xml文件的依赖
```xml
<!--dom4j-->
<dependency>
    <groupId>dom4j</groupId>
    <artifactId>dom4j</artifactId>
    <version>1.6.1</version>
</dependency>
<!--jaxen-->
<dependency>
    <groupId>jaxen</groupId>
    <artifactId>jaxen</artifactId>
    <version>1.1.6</version>
</dependency>
```
```java
package org.myspringmvc.context;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ApplicationContext
 * Description: Spring容器，启动服务器时，初始化
 * Datetime: 2024/4/2 13:52
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class ApplicationContext {
    private Map<String, Object> beanMap = new HashMap<>();

    public ApplicationContext(String xmlPath) throws Exception {
        // 组件扫描
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(xmlPath));
        Element componentScanElement = (Element)document.selectSingleNode("/beans/context:component-scan");
        String basePackage = componentScanElement.attributeValue("base-package");
        System.out.println("组件扫描：" + basePackage);
        componentScan(basePackage);
        System.out.println("Spring Web容器当下状态：" + beanMap);
    }

    private void componentScan(String basePackage) throws Exception{
        String dirPath = Thread.currentThread().getContextClassLoader().getResource(basePackage.replace(".", "/")).getPath();
        File file = new File(URLDecoder.decode(dirPath));
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File classFile : files){
                if(classFile.getName().endsWith(".class")){
                    String className = basePackage + "." + classFile.getName().substring(0, classFile.getName().lastIndexOf("."));
                    Class<?> clazz = Class.forName(className);
                    Constructor<?> defaultCon = clazz.getDeclaredConstructor();
                    Object bean = defaultCon.newInstance();
                    beanMap.put(firstCharLowerCase(clazz.getSimpleName()), bean);
                }
            }
        }
    }

    private String firstCharLowerCase(String simpleName) {
        return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
    }

    public Object getBean(String beanName){
        return beanMap.get(beanName);
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
```java
package org.myspringmvc.context;

import jakarta.servlet.ServletContext;

/**
 * ClassName: WebApplicationContext
 * Description:
 * Datetime: 2024/4/2 14:24
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class WebApplicationContext extends ApplicationContext{

    private ServletContext servletContext;

    public WebApplicationContext(String xmlPath, ServletContext servletContext) throws Exception {
        super(xmlPath);
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}

```
在DispatcherServlet中添加如下代码：
![image.png](images/1712041922023-4a1c3828-06cf-4443-a02a-e873e16993e1.png)
添加常量值：
![image.png](images/1712041948893-c2315381-d5a0-4ba6-b97e-ecb53436073e.png)

启动服务器测试：
![image.png](images/1712042124851-e6e6cb3c-0701-4e37-bcec-0a3fdd4d79c0.png)

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
### 创建视图解析器对象
InternalResourceViewResolver类代码改动，添加prefix和suffix属性：
```java
package org.myspringmvc.web.servlet.view;

import org.myspringmvc.web.servlet.View;
import org.myspringmvc.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * ClassName: InternalResourceViewResolver
 * Description:
 * Datetime: 2024/4/2 9:45
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class InternalResourceViewResolver implements ViewResolver {
    private String suffix;
    private String prefix;

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return null;
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
![image.png](images/1712050149246-9884a89f-d261-49b1-8a99-6a9768ca69cc.png)

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
```java
// 创建视图解析器对象
Element viewResolverBean = (Element) document.selectSingleNode("/beans/bean");
String viewResolverClassName = viewResolverBean.attributeValue("class");
Class viewResolverClass = Class.forName(viewResolverClassName);
Object viewResolverObj = viewResolverClass.newInstance();
if(viewResolverObj instanceof InternalResourceViewResolver internalResourceViewResolver){
    // 前缀
    Element prefixProperty = (Element)viewResolverBean.selectSingleNode("property[@name='prefix']");
    internalResourceViewResolver.setPrefix(prefixProperty.attributeValue("value"));
    // 后缀
    Element suffixProperty = (Element)viewResolverBean.selectSingleNode("property[@name='suffix']");
    internalResourceViewResolver.setSuffix(suffixProperty.attributeValue("value"));
}
beanMap.put(Constant.VIEW_RESOLVER, viewResolverObj);
System.out.println("Spring Web容器当下状态：" + beanMap);
```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
### 创建所有的拦截器对象
在ApplicationContext构造方法中继续添加如下代码：
![image.png](images/1712045626658-02f8ddf3-e2a4-46e7-bbc2-5c154000c7c5.png)
```java
// 创建所有拦截器对象
Element interceptorsElement = (Element) document.selectSingleNode("/beans/interceptors");
List<Element> interceptorBeans = interceptorsElement.elements("bean");
List<HandlerInterceptor> interceptors = new ArrayList<>();
for(Element interceptorBean : interceptorBeans){
    String className = interceptorBean.attributeValue("class");
    Class<?> clazz = Class.forName(className);
    interceptors.add((HandlerInterceptor) clazz.newInstance());
}
beanMap.put(Constant.INTERCEPTORS, interceptors);
System.out.println("Spring Web容器当下状态：" + beanMap);
```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
### 初始化annotation包下所有类的实例
![image.png](images/1712048048630-7038a487-0f3d-4518-9d9f-49e320e58cdd.png)
```java
// 将这个包下所有的类实例化：org.myspringmvc.web.servlet.mvc.method.annotation
String dirPath = Thread.currentThread().getContextClassLoader().getResource(Constant.PACKAGE_AUTO_CREATE.replace(".", "/")).getPath();
File file = new File(URLDecoder.decode(dirPath));
if(file.isDirectory()){
    File[] files = file.listFiles();
    for (File classFile : files){
        if(classFile.getName().endsWith(".class")){
            String className = Constant.PACKAGE_AUTO_CREATE + "." + classFile.getName().substring(0, classFile.getName().lastIndexOf("."));
            Class<?> clazz = Class.forName(className);
            Constructor<?> defaultCon = clazz.getDeclaredConstructor();
            Object bean = defaultCon.newInstance();
            if(bean instanceof HandlerMapping){
                beanMap.put(Constant.HANDLER_MAPPING, bean);
            }
            if(bean instanceof HandlerAdapter){
                beanMap.put(Constant.HANDLER_ADAPTER, bean);
            }
        }
    }
}
System.out.println("Spring Web容器当下状态：" + beanMap);
```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 初始化HandlerMapping
![image.png](images/1712050860912-9b46aa11-9b17-43df-9f38-74802dba5d59.png)

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 初始化HandlerAdapter
![image.png](images/1712050860912-9b46aa11-9b17-43df-9f38-74802dba5d59.png)

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 初始化ViewResolver
![image.png](images/1712050860912-9b46aa11-9b17-43df-9f38-74802dba5d59.png)

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
# 根据请求流程补充代码
## 根据请求获取处理器执行链
```java
private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        // 根据请求获取处理器执行链
        HandlerExecutionChain mappedHandler = handlerMapping.getHandler(request);
        System.out.println(mappedHandler);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```
```java
package org.myspringmvc.web.servlet.mvc.method.annotation;

import jakarta.servlet.http.HttpServletRequest;
import org.myspringmvc.context.WebApplicationContext;
import org.myspringmvc.web.constant.Constant;
import org.myspringmvc.web.method.HandlerMethod;
import org.myspringmvc.web.servlet.HandlerExecutionChain;
import org.myspringmvc.web.servlet.HandlerInterceptor;
import org.myspringmvc.web.servlet.HandlerMapping;
import org.myspringmvc.web.servlet.mvc.RequestMappingInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: RequestMappingHandlerMapping
 * Description:
 * Datetime: 2024/4/2 9:44
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class RequestMappingHandlerMapping implements HandlerMapping {

    private Map<RequestMappingInfo, HandlerMethod> map;

    public RequestMappingHandlerMapping(Map<RequestMappingInfo, HandlerMethod> map) {
        this.map = map;
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        RequestMappingInfo requestMappingInfo = new RequestMappingInfo(request.getServletPath(), request.getMethod());
        HandlerExecutionChain handlerExecutionChain = new HandlerExecutionChain();
        handlerExecutionChain.setHandler(map.get(requestMappingInfo));
        WebApplicationContext wac = (WebApplicationContext) request.getServletContext().getAttribute(Constant.WEB_APPLICATION_CONTEXT);
        handlerExecutionChain.setInterceptorList((List<HandlerInterceptor>)wac.getBean(Constant.INTERCEPTORS));
        return handlerExecutionChain;
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
```java
private Map<RequestMappingInfo, HandlerMethod> componentScan(String basePackage) throws Exception{
    // 初始化HandlerMethod
    Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = new HashMap<>();

    String dirPath = Thread.currentThread().getContextClassLoader().getResource(basePackage.replace(".", "/")).getPath();
    File file = new File(URLDecoder.decode(dirPath));
    if(file.isDirectory()){
        File[] files = file.listFiles();
        for (File classFile : files){
            if(classFile.getName().endsWith(".class")){
                String className = basePackage + "." + classFile.getName().substring(0, classFile.getName().lastIndexOf("."));
                Class<?> clazz = Class.forName(className);
                Constructor<?> defaultCon = clazz.getDeclaredConstructor();
                Object bean = defaultCon.newInstance();
                beanMap.put(firstCharLowerCase(clazz.getSimpleName()), bean);
                // 如果clazz被@Controller注解标注
                if(clazz.isAnnotationPresent(Controller.class)){
                    // 获取该类中所有的方法
                    Method[] methods = clazz.getDeclaredMethods();
                    for(Method method : methods){
                        if(method.isAnnotationPresent(RequestMapping.class)){
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            // 创建RequestMappingInfo对象
                            RequestMappingInfo requestMappingInfo = new RequestMappingInfo();
                            requestMappingInfo.setRequestURI(requestMapping.value()[0]);
                            requestMappingInfo.setRequestMethod(requestMapping.method().toString());
                            // 创建HandlerMethod对象
                            HandlerMethod handlerMethod = new HandlerMethod();
                            handlerMethod.setMethod(method);
                            handlerMethod.setHandler(bean);

                            handlerMethodMap.put(requestMappingInfo, handlerMethod);
                        }
                    }
                }
            }
        }
    }
    return handlerMethodMap;
}
```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
ApplicationContext代码还有以下改造：
![image.png](images/1712055939500-e4301f42-0486-43b6-b3fa-bc9780fd91c4.png)
添加一个新的类：RequestMappingInfo
```java
package org.myspringmvc.web.servlet.mvc;

import java.util.Objects;

/**
 * ClassName: RequestMappingInfo
 * Description:
 * Datetime: 2024/4/2 17:58
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class RequestMappingInfo {
    private String requestURI;
    private String requestMethod;

    public RequestMappingInfo() {
    }

    public RequestMappingInfo(String requestURI, String requestMethod) {
        this.requestURI = requestURI;
        this.requestMethod = requestMethod;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMappingInfo that = (RequestMappingInfo) o;
        return Objects.equals(requestURI, that.requestURI) && Objects.equals(requestMethod, that.requestMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestURI, requestMethod);
    }

    @Override
    public String toString() {
        return "RequestMappingInfo{" +
                "requestURI='" + requestURI + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                '}';
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 执行拦截器的preHandle
添加以下代码：
![image.png](images/1712056664132-90f25f97-bc21-4aff-851b-63d222974148.png)

HandlerExecutionChain添加以下代码：
```java
public boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    for (int i = 0; i < interceptorList.size(); i++) {
        HandlerInterceptor handlerInterceptor = interceptorList.get(i);
        boolean result = handlerInterceptor.preHandle(request, response, handler);
        if(!result){
            return false;
        }
        interceptorIndex = i;
    }
    return true;
}
```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 执行处理器方法
DispatcherServlet中的doDispatch方法：
![image.png](images/1712068532880-a22a29f7-6acf-44da-9c2e-016aa01f3756.png)
先让handle方法返回一个固定的ModelAndView，后期在详细编写 handle 方法：
![image.png](images/1712066826429-45d064ef-6649-4b7d-a504-b485143f87b6.png)

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 执行拦截器的postHandle
DispatcherServlet的doDispatch方法中：
![image.png](images/1712067006139-fe7993eb-1745-4653-92c1-173b31254417.png)

HandlerExecutionChain的方法：
![image.png](images/1712068684534-c19e8af2-4a17-4997-8e5a-3c478fa95854.png)

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 处理响应
在DispatcherServlet的 doDispatch方法中：
![image.png](images/1712067254904-79ff05f1-b27e-457a-8c43-a5999d8c47d7.png)

```java
package org.myspringmvc.web.servlet.view;

import org.myspringmvc.web.servlet.View;
import org.myspringmvc.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * ClassName: InternalResourceViewResolver
 * Description:
 * Datetime: 2024/4/2 9:45
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class InternalResourceViewResolver implements ViewResolver {
    private String suffix;
    private String prefix;

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return new InternalResourceView("text/html;charset=UTF-8", prefix + viewName + suffix);
    }
}

```
![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
```java
package org.myspringmvc.web.servlet.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.myspringmvc.web.servlet.View;

import java.util.Map;

/**
 * ClassName: InternalResourceView
 * Description:
 * Datetime: 2024/4/2 10:17
 * Author: 老杜@动力节点
 * Version: 1.0
 */
public class InternalResourceView implements View {

    private String contentType;
    private String path;

    public InternalResourceView(String contentType, String path) {
        this.contentType = contentType;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置响应内容类型
        response.setContentType(getContentType());
        // 向request域中绑定数据
        if(model != null){
            model.forEach(request::setAttribute);    
        }
        // 转发
        request.getRequestDispatcher(path).forward(request, response);
    }
}

```

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 执行拦截器的afterCompletion
在DispatcherServlet类的doDispatch方法中：
![image.png](images/1712068193170-1b47284d-c42d-45a5-ae1b-a729794a105c.png)

在HandlerExecutionChain中：
![image.png](images/1712068259935-ea02fdbe-ff19-4662-ad8c-eb349c269bf1.png)

![image.png](images/1712068277775-336dda1a-e26c-4d37-84af-545bb818f0c9.png)

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
## 初步测试
启动服务器，浏览器地址栏：http://localhost:8080/myspringmvc
![image.png](images/1712068892689-b37a04ee-3e89-44a2-b018-1f3644812b71.png)
后台效果：
![image.png](images/1712068880417-1a0839a3-84b3-459e-ab4b-77ccec5cff76.png)

如果让第二个拦截器返回false尝试一下：
![image.png](images/1712068979607-248d52d3-17fd-402b-a859-26884ed27e98.png)
![image.png](images/1712068971047-6774611e-9d91-482d-88b5-8e08478de7cb.png)
初步测试通过！！！

![标头.jpg](images/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg)
# 调用处理器方法

