package com.example.excel.ioc;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IOC实现原理
 * Tomcat容器 - 容器启动 - 初始化spring容器 - 获取扫描包下的所有class - 解析class中的注解信息
 * - 封装类反射后实例化对象 - 以beanId,bean实例化类 对象形式保存集合 - getBean(beanId) -
 * bean的实例化对象 - bean的具体方法及执行结果
 *
 *    笔记 day49 : springspring对bean的管理
 *              1.创建bean的三种方式
 *              2.bean对象的作用范围
 *              3.bean对象的生命周期
 *
 * Bean元素:使用该元素描述需要spring容器管理的对象
 *     class属性:被管理对象的完整类名.
 *     name属性:给被管理的对象起个名字.获得对象时根据该名称获得对象.
 *     id属性: 与name属性作用相同.
 *
 * 第一种方式：使用默认构造函数创建。
 *     在spring的配置文件中使用bean标签，配以id和class属性之后，且没有其他属性和标签时。
 *     采用的就是默认构造函数创建bean对象，此时如果类中没有默认构造函数，则对象无法创建。
 * <bean id="userDao" class="com.qf.dao.UserDaoImpl"></bean>
 *
 * 第二种方式：使用普通工厂中的方法创建对象（使用某个类中的方法创建对象，并存入spring容器）
 * <bean id="UserFactory" class="com.qf.factory.UserFactory"></bean>
 * <bean id="getUserDao" factory-bean="UserFactory" factory-method="getuserDao"></bean>
 *
 * 第三种方式：使用工厂中的静态方法创建对象（使用某个类中的静态方法创建对象，并存入spring容器)
 * <bean id="userDao" class="com.qf.factory.UserStaticFactory" factory-method="UserDao"></bean>
 *
 *   ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
 *   例：SelfPathXmlApplicationContext app = new SelfPathXmlApplicationContext("com.congge.service.impl");
 */
@Data       //IOC(反转控制)实现原理 及 DI依赖注入注解原理
public class SelfPathXmlApplicationContext {

    private String packageName;

    //封装所有的bean容器
    private ConcurrentHashMap<String, Object> beans = null;

    public SelfPathXmlApplicationContext(String packageName)throws Exception{
        beans = new ConcurrentHashMap<>();
        this.packageName = packageName;
        initBeans();
        initEntryField();
    }

    //初始化bean的实例对象的所有属性   DI依赖注入注解原理
    private void initEntryField() throws Exception {
        // 1.遍历所有的bean容器对象
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            // 2.判断属性上面是否有加注解
            Object bean = entry.getValue();
            attriAssign(bean);
        }
    }

    //根据beanId获取bean名称
    public Object getBean(String beanId) throws Exception {
        if (StringUtils.isEmpty(beanId)) {
            throw new Exception("beanId参数不能为空");
        }
        // 1.从spring容器获取bean
        Object object = beans.get(beanId);
        // attriAssign(object);
        return object;
    }

    //获取扫描包下面的所有bean
    private void initBeans() throws Exception{
        //1.使用java的反射机制扫包，获取当前包下的所有类
        Set<Class<?>> classes = ClassParseUtil.getClasses(packageName);
        // 2、判断类上面是否存在注入的bean的注解
        ConcurrentHashMap<String, Object> classExisAnnotation = findClassExisAnnotation(classes);
        if (classExisAnnotation == null || classExisAnnotation.isEmpty()) {
            throw new Exception("该包下没有任何类加上注解");
        }
    }

    //判断类上是否存在注入自定义的bean的注解
    public ConcurrentHashMap<String, Object> findClassExisAnnotation(Set<Class<?>> classes) throws Exception {
        for (Class<?> classInfo : classes) {
            // 判断类上是否有注解 [获取自定义的service注解]
            SelfService annotation = classInfo.getAnnotation(SelfService.class);
            if (annotation != null) {
                // 获取当前类的类名
                String className = classInfo.getSimpleName();
                String beanId = toLowerCaseFirstOne(className);
                Object newInstance = newInstance(classInfo);
                beans.put(beanId, newInstance);
            }

        }
        return beans;
    }

    // 首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    //通过class名称获取类的实例化对象
    public Object newInstance(Class<?> classInfo)throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return classInfo.newInstance();
    }

    //依赖注入注解原理
    public void attriAssign(Object object) throws Exception {

        // 1.使用反射机制,获取当前类的所有属性
        Class<? extends Object> classInfo = object.getClass();
        Field[] declaredFields = classInfo.getDeclaredFields();

        // 2.判断当前类属性是否存在注解
        for (Field field : declaredFields) {
            SelfAutowired extResource = field.getAnnotation(SelfAutowired.class);
            if (extResource != null) {
                // 获取属性名称
                String beanId = field.getName();
                Object bean = getBean(beanId);
                if (bean != null) {
                    // 3.默认使用属性名称，查找bean容器对象 1参数 当前对象 2参数给属性赋值
                    field.setAccessible(true); // 允许访问私有属性
                    field.set(object, bean);
                }
            }
        }
    }

}
