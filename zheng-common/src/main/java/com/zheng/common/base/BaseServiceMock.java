package com.zheng.common.base;

import java.util.List;

/**
 * 降级实现BaseService抽象类
 * @param <Mapper>
 * @param <Record>
 * @param <Example>
 * @author Joy
 * @date 2018/3/2
 */
public abstract class BaseServiceMock<Mapper, Record, Example> implements BaseService<Record, Example> {
    @Override
    public int countByExample(Example example) {
        return -1;
    }

    @Override
    public int deleteByExample(Example example) {
        return -1;
    }

    @Override
    public int deletePyPrimaryKey(Integer id) {
        return -1;
    }

    @Override
    public int insert(Record record) {
        return -1;
    }

    @Override
    public int insertSelective(Record record) {
        return -1;
    }

    @Override
    public List<Record> selectByExampleWithBLOBs(Example example) {
        return null;
    }

    @Override
    public List<Record> selectByExample(Example example) {
        return null;
    }

    @Override
    public List<Record> selectByExampleWithBLOBsForStartPage(Example example, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public List<Record> selectByExampleForStartPage(Example example, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public List<Record> selectByExampleWithBLOBsForOffsetPage(Example example, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public List<Record> selectByExampleForOffsetPage(Example example, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public Record selectFirstByExample(Example example) {
        return null;
    }

    @Override
    public Record selectFirstByExampleWithBLOBs(Example example) {
        return null;
    }

    @Override
    public Record selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByExampleSelective(Record record, Example example) {
        return -1;
    }

    @Override
    public int updateByExampleWithBLOBs(Record record, Example example) {
        return -1;
    }

    @Override
    public int updateByExample(Record record, Example example) {
        return -1;
    }

    @Override
    public int updateByPrimaryKeySelective(Record record) {
        return -1;
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(Record record) {
        return -1;
    }

    @Override
    public int updateByPrimaryKey(Record record) {
        return -1;
    }

    @Override
    public int deleteByPrimaryKeys(String ids) {
        return -1;
    }

    @Override
    public void initMapper() {

    }
}
