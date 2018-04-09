package com.zheng.common.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.velocity.VelocityContext;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.*;
import java.text.SimpleDateFormat;

import static com.zheng.common.util.StringUtil.lineToHump;

/**
 * 代码生成类
 *
 * @author Joy
 * @date 2018/2/27
 */
public class MybatisGeneratorUtil {

    /**
     * generatorConfig模板路径
     */
    private static String generatorConfig_vm = "/template/generatorConfig.vm";

    /**
     * Service模板路径
     */
    private static String service_vm = "/template/Service.vm";

    /**
     * ServiceMock模板路径
     */
    private static String serviceMock_vm = "/template/ServiceMock.vm";

    /**
     * ServiceImpl模板路径
     */
    private static String serviceImpl_vm = "/template/ServiceImpl.vm";

    /**
     * 根据模板生成generatorConfig.xml文件
     * @param jdbcDriver    驱动路径
     * @param jdbcUrl       链接
     * @param jdbcUsername  账号
     * @param jdbcPassword  密码
     * @param module        项目模块
     * @param database      数据库
     * @param tablePrefix   表前缀
     * @param packageName   包名
     * @param lastInsertIdTables
     * @throws Exception
     */
    public static void generator(String jdbcDriver, String jdbcUrl, String jdbcUsername, String jdbcPassword, String module, String database, String tablePrefix, String packageName, Map<String, String> lastInsertIdTables) throws Exception {
        String os = System.getProperty("os.name");
        generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm).getPath();
        service_vm = MybatisGeneratorUtil.class.getResource(service_vm).getPath();
        serviceMock_vm = MybatisGeneratorUtil.class.getResource(serviceMock_vm).getPath();
        serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm).getPath();

        if(os.toLowerCase().startsWith("win")){
            generatorConfig_vm = generatorConfig_vm.replaceFirst("/", "");
            service_vm = service_vm.replaceFirst("/", "");
            serviceMock_vm = serviceMock_vm.replaceFirst("/", "");
            serviceImpl_vm = serviceImpl_vm.replaceFirst("/", "");
        }

        String targetProject = module + "/" + module + "-dao";
        String basePath = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "").replace(targetProject, "").replaceFirst("/", "");
        String generatorConfigXml = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "") + "/src/main/resources/generatorConfig.xml";
        targetProject = basePath + targetProject;
        String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "' AND table_name LIKE'" + tablePrefix + "_%';";

        System.out.println("========= 开始生成generatorConfig.xml文件 =========");
        List<Map<String, Object>> tables = new ArrayList<Map<String, Object>>();
        try {
            VelocityContext context = new VelocityContext();


            // 查询定制前缀项目的所有表
            JdbcUtil jdbcUtil = new JdbcUtil(jdbcDriver, jdbcUrl, jdbcUsername, AESUtil.aesDecode(jdbcPassword));
            List<Map> result = jdbcUtil.selectByParams(sql, null);
            for(Map map : result){
                System.out.println(map.get("TABLE_NAME"));
                Map<String, Object> table = new HashMap<String, Object>(2);
                table.put("table_name", map.get("TABLE_NAME"));
                table.put("model_name", lineToHump(ObjectUtils.toString(map.get("TABLE_NAME"))));
                tables.add(table);
            }
            jdbcUtil.release();

            String targetProjectSqlMap = basePath + module + "/" + module + "-rpc-service";
            context.put("tables", tables);
            context.put("generator_javaModelGenerator_targetPackage", packageName + ".dao.model");
            context.put("generator_sqlMapGenerator_targetPackage", packageName + ".dao.mapper");
            context.put("generator_javaClientGenerator_targetPackage", packageName + ".dao.mapper");
            context.put("targetProject", targetProject);
            context.put("targetProject_sqlMap", targetProjectSqlMap);
            context.put("generator_jdbc_password", AESUtil.aesDecode(jdbcPassword));
            context.put("last_insert_id_tables", lastInsertIdTables);
            VelocityUtil.generate(generatorConfig_vm, generatorConfigXml, context);
            // 删除旧代码
            deleteDir(new File(targetProject + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/dao/model"));
            deleteDir(new File(targetProject + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/dao/mapper"));
            deleteDir(new File(targetProjectSqlMap + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/dao/mapper"));

        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("========= 结束生成generatorConfig.xml文件 =========");

        System.out.println("========= 开始运行MybatisGenerator ========");
        List<String> warnings = new ArrayList<String>();
        File configFile = new File(generatorConfigXml);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

        for(String warning : warnings){
            System.out.println(warning);
        }
        System.out.println("========= 结束运行MyBatisGenerator =========");

        System.out.println("========= 开始生成Service =========");
        String ctime = new SimpleDateFormat("yyyy/M/d").format(new Date());
        String servicePath = basePath + module + "/" + module + "-rpc-api" + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/rpc/api";
        String serviceImplPath = basePath + module + "/" + module + "-rpc-service" + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/rpc/service/impl";
        for(int i = 0; i < tables.size(); i++){
            String model = lineToHump(ObjectUtils.toString(tables.get(i).get("table_name")));
            String service = servicePath + "/" + model + "Service.java";
            String serviceMock = servicePath + "/" + model + "ServiceMock.java";
            String serviceImpl = serviceImplPath + "/" + model + "ServiceImpl.java";

            // 生成service
            MybatisGeneratorUtil.generateServiceFile(service, service_vm, packageName, model, ctime, false);

            // 生成serviceMock
            MybatisGeneratorUtil.generateServiceFile(serviceMock, serviceMock_vm, packageName, model, ctime, false);

            // 生成serviceImpl
            MybatisGeneratorUtil.generateServiceFile(serviceImpl, serviceImpl_vm, packageName, model, ctime, true);
        }
        System.out.println("========= 结束生成Service =========");
    }

    /**
     * 递归删除非空文件夹
     * @param dir
     */
    private static void deleteDir(File dir){
        if(dir.isDirectory()){
            File[] files = dir.listFiles();
            for(int i = 0; i < files.length; i++){
                deleteDir(files[i]);
            }
        }
        dir.delete();
    }

    /**
     * 生成Service文件
     * @param serviceFilePath   Service文件路径
     * @param vmFilePath        vm文件路径
     * @param packageName       包名
     * @param model             model名
     * @param ctime             创建时间
     * @param generateMapper    是否生成mapper（生成serviceImpl时为true，其他为false）
     * @throws Exception
     */
    private static void generateServiceFile(String serviceFilePath, String vmFilePath, String packageName, String model, String ctime, boolean generateMapper) throws Exception {
        File serviceFile = new File(serviceFilePath);
        if(!serviceFile.exists()){
            VelocityContext context = new VelocityContext();
            context.put("package_name", packageName);
            context.put("model", model);
            context.put("ctime", ctime);
            if(generateMapper){
                context.put("mapper", StringUtil.toLowerCaseFirstOne(model));
            }
            VelocityUtil.generate(vmFilePath, serviceFilePath, context);
            System.out.println(serviceFilePath);
        }
    }
}
