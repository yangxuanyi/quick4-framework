package org.quick4.framework;

/**
 * 提供相关配置的常量类
 * Created by yang- on 2017/5/27.
 */
public interface ConfigConstant {
    //配置文件名称
    String CONFIG_FILE="quick4.properties";

    String JDBC_DRIVER="quick.framework.jdbc.driver";
    String JDBC_URL="quick.framework.jdbc.url";
    String JDBC_USERNAME="quick.framework.jdbc.username";
    String JDBC_PASSWORD="quick.framework.jdbc.password";

    String APP_BASE_BACKAGE="quick.framework.app.base_package";
    String APP_JSP_PATH="quick.framework.app.jsp_path";
    String APP_ASSET_PATH="quick.framework.app.asset_path";
}
