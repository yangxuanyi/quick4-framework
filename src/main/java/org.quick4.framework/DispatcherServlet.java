package org.quick4.framework;

import org.quick4.framework.bean.Data;
import org.quick4.framework.bean.Handler;
import org.quick4.framework.bean.Param;
import org.quick4.framework.bean.View;
import org.quick4.framework.helper.BeanHelper;
import org.quick4.framework.helper.ConfigHelper;
import org.quick4.framework.helper.ControllerHelper;
import org.quick4.framework.utils.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("框架正在初始化...start");
        //初始化相关helper类
        HelperLoader.init();
        //获取servletContext对象(用于注册servlet)
        ServletContext servletContext = servletConfig.getServletContext();
        //注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath()+"*");

        //注册处理静态资源的默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");

        defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"*");

        System.out.println("框架初始化完成...end");
       // super.init();
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
        System.out.println("有请求来袭..."+ request.getServletPath());
        //获取请求方法与请路径
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();
        //获取action 处理器
        Handler handler = ControllerHelper.getHandler(requestMethod,requestPath);

        if (handler != null){
            //获取controller以及bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String,Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if (StringUtil.isNotEmpty(body)){
                //
               String[] params = StringUtil.splitString(body,"&");
               if (ArrayUtil.isNotEmpty(params)){
                   for (String param : params){
                       String[] array =StringUtil.splitString(param,"=");
                       if (ArrayUtil.isNotEmpty(array) && array.length == 2){
                           String paramName = array[0];
                           String paramValue = array[1];
                           paramMap.put(paramName,paramValue);
                       }
                   }
               }


            }
            Param param = new Param(paramMap);
            //调用Action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);
            //处理action方法的返回值
            if (request instanceof View){
                //返回jsp页面
                View view = (View) request;
                String path = view.getPath();
                if (StringUtil.isNotEmpty(path)){
                    if (path.startsWith("/")){
                        response.sendRedirect(request.getContextPath()+path);
                    }else{
                        Map<String,Object> model = view.getModel();
                        for (Map.Entry<String,Object> entry: model.entrySet()){
                            request.setAttribute(entry.getKey(),entry.getValue());
                        }

                        request.getRequestDispatcher(ConfigHelper.getAppJspPath()+path)
                                .forward(request,response);
                    }
                }
            }else if (result instanceof Data){
                //返回Json数据
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null){
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    String json = JsonUtil.toString(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }

    }
}
