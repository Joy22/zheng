package com.zheng.common.base;

import com.github.pagehelper.PageHelper;
import com.sun.org.apache.regexp.internal.RE;
import com.zheng.common.db.DataSourceEnum;
import com.zheng.common.db.DynamicDataSource;
import com.zheng.common.util.SpringContextUtil;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 实现BaseService抽象类
 *
 * @param <Mapper>
 * @param <Record>
 * @param <Example>
 * @author Joy
 * @date 2018/3/2
 */
public abstract class BaseServiceImpl<Mapper, Record, Example> implements BaseService<Record, Example> {

    public Mapper mapper;

    @Override
    public int countByExample(Example example) {
        try {
            DynamicDataSource.setDatasource(DataSourceEnum.SLAVE.getName());
            Method countByExample = mapper.getClass().getDeclaredMethod("countByExample", example.getClass());
            Object result = countByExample.invoke(mapper, example);
            return Integer.parseInt(String.valueOf(result));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int deleteByExample(Example example) {
        try {
            DynamicDataSource.setDatasource(DataSourceEnum.MASTER.getName());
            Method deleteByExample = mapper.getClass().getDeclaredMethod("deleteByExample", example.getClass());
            Object result = deleteByExample.invoke(mapper, example);
            return Integer.parseInt(String.valueOf(result));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int deletePyPrimaryKey(Integer id) {
        try {
            DynamicDataSource.setDatasource(DataSourceEnum.MASTER.getName());
            Method deleteByPrimaryKey = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
            Object result = deleteByPrimaryKey.invoke(mapper, id);
            return Integer.parseInt(String.valueOf(result));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int insert(Record record) {
        try {
            DynamicDataSource.setDatasource(DataSourceEnum.MASTER.getName());
            Method insert = mapper.getClass().getDeclaredMethod("insert", record.getClass());
            Object result = insert.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int insertSelective(Record record) {
        try {
            DynamicDataSource.setDatasource(DataSourceEnum.MASTER.getName());
            Method insertSelective = mapper.getClass().getDeclaredMethod("insertSelective", record.getClass());
            Object result = insertSelective.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public List<Record> selectByExampleWithBLOBs(Example example) {
        Object result = getMethodResult(DataSourceEnum.SLAVE, "selectByExampleWithBLOBs", example);
        if (result != null) {
            return (List<Record>) result;
        }
        return null;
    }

    @Override
    public List<Record> selectByExample(Example example) {
        Object result = getMethodResult(DataSourceEnum.SLAVE, "selectByExample", example);
        if (result != null) {
            return (List<Record>) result;
        }
        return null;
    }

    @Override
    public List<Record> selectByExampleWithBLOBsForStartPage(Example example, Integer pageNum, Integer pageSize) {
        return selectByExampleForStartPage(example, pageNum, pageSize, "selectByExampleWithBLOBs");
    }

    @Override
    public List<Record> selectByExampleForStartPage(Example example, Integer pageNum, Integer pageSize) {
        return selectByExampleForStartPage(example, pageNum, pageSize, "selectByExample");
    }

    @Override
    public List<Record> selectByExampleWithBLOBsForOffsetPage(Example example, Integer offset, Integer limit) {
        return selectByExampleForOffsetPage(example, offset, limit, "selectByExampleWithBLOBs");
    }

    @Override
    public List<Record> selectByExampleForOffsetPage(Example example, Integer offset, Integer limit) {
        return selectByExampleForOffsetPage(example, offset, limit, "selectByExample");
    }

    @Override
    public Record selectFirstByExample(Example example) {
        return selectFirstByExample(example, "selectByExample");
    }

    @Override
    public Record selectFirstByExampleWithBLOBs(Example example) {
        return selectFirstByExample(example, "selectByExampleWithBLOBs");
    }

    @Override
    public Record selectByPrimaryKey(Integer id) {
        Object result = getMethodResult(DataSourceEnum.SLAVE, "selectByPrimaryKey", id);
        if (result != null) {
            return (Record) result;
        }
        return null;
    }

    @Override
    public int updateByExampleSelective(Record record, Example example) {
        Object result = getMethodResult(DataSourceEnum.MASTER, "updateByExampleSelective", record, example);
        return getResultIntValue(result);
    }

    @Override
    public int updateByExampleWithBLOBs(Record record, Example example) {
        Object result = getMethodResult(DataSourceEnum.MASTER, "updateExampleWithBLOBs", record, example);
        return getResultIntValue(result);
    }

    @Override
    public int updateByExample(Record record, Example example) {
        Object result = getMethodResult(DataSourceEnum.MASTER, "updateByExample", record, example);
        return getResultIntValue(result);
    }

    @Override
    public int updateByPrimaryKeySelective(Record record) {
        Object result = getMethodResult(DataSourceEnum.MASTER, "updateByPrimaryKeySelective", record);
        return getResultIntValue(result);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(Record record) {
        Object result = getMethodResult(DataSourceEnum.MASTER, "updateByPrimaryKeyWithBLOBs", record);
        return getResultIntValue(result);
    }

    @Override
    public int updateByPrimaryKey(Record record) {
        Object result = getMethodResult(DataSourceEnum.MASTER, "updateByPrimaryKey", record);
        return getResultIntValue(result);
    }

    @Override
    public int deleteByPrimaryKeys(String ids) {
        try {
            if(StringUtils.isBlank(ids)){
                return 0;
            }
            DynamicDataSource.setDatasource(DataSourceEnum.MASTER.getName());
            String[] idArray = ids.split("-");
            int count = 0;
            for (String idStr : idArray){
                if(StringUtils.isBlank(idStr)){
                    continue;
                }
                Integer id = Integer.parseInt(idStr);
                Method deleteByPrimaryKey = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
                Object result = deleteByPrimaryKey.invoke(mapper, id);
                count += Integer.parseInt(String.valueOf(result));
            }
            return count;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public void initMapper() {
        this.mapper = SpringContextUtil.getBean(getMapperClass());
    }

    private Object getMethodResult(DataSourceEnum dataSource, String methodStr, Object param) {
        try {
            DynamicDataSource.setDatasource(dataSource.getName());
            Method method = mapper.getClass().getDeclaredMethod(methodStr, param.getClass());
            return method.invoke(mapper, param);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    private Object getMethodResult(DataSourceEnum dataSource, String methodStr, Object param1, Object param2) {
        try {
            DynamicDataSource.setDatasource(dataSource.getName());
            Method method = mapper.getClass().getDeclaredMethod(methodStr, param1.getClass(), param2.getClass());
            return method.invoke(mapper, param1, param2);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    private List<Record> selectByExampleForOffsetPage(Example example, Integer offset, Integer limit, String method) {
        try {
            DynamicDataSource.setDatasource(DataSourceEnum.SLAVE.getName());
            Method selectByExample = mapper.getClass().getDeclaredMethod(method, example.getClass());
            PageHelper.offsetPage(offset, limit, false);
            Object result = selectByExample.invoke(mapper, example);
            return (List<Record>) result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    private List<Record> selectByExampleForStartPage(Example example, Integer pageNum, Integer pageSize, String method) {
        try {
            DynamicDataSource.setDatasource(DataSourceEnum.SLAVE.getName());
            Method selectByExample = mapper.getClass().getDeclaredMethod(method, example.getClass());
            PageHelper.startPage(pageNum, pageSize, false);
            Object result = selectByExample.invoke(mapper, example);
            return (List<Record>) result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    private Record selectFirstByExample(Example example, String method) {
        try {
            DynamicDataSource.setDatasource(DataSourceEnum.SLAVE.getName());
            Method selectByExample = mapper.getClass().getDeclaredMethod(method, example.getClass());
            List<Record> result = (List<Record>) selectByExample.invoke(mapper, example);
            if (null != result && result.size() > 0) {
                return result.get(0);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    private int getResultIntValue(Object result){
        if(result != null){
            return  Integer.parseInt(String.valueOf(result));
        }
        return 0;
    }

    /**
     * 获取类泛型class
     * @return
     */
    private Class<Mapper> getMapperClass(){
        return (Class<Mapper>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
