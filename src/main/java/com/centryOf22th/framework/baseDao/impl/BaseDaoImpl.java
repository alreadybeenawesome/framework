package com.centryOf22th.framework.baseDao.impl;

import com.centryOf22th.framework.baseDao.BaseDao;
import com.centryOf22th.framework.orm.assist.DbAssistant;
import com.centryOf22th.framework.orm.assist.MysqlAssistant;
import com.centryOf22th.framework.orm.assist.OracleAssistant;
import com.centryOf22th.framework.orm.metadata.TableMetaData;
import com.centryOf22th.framework.utils.PropertyReader;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by louis on 16-10-27.
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
    private static Logger logger = LogManager.getLogger(MysqlAssistant.class);
    /**
     * actualTypeOfParamT is the generic parameter T actual type that at the runtime
     */
    private Class actualTypeOfParamT = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//    ParameterizedType type =(ParameterizedType)this.getClass().getGenericSuperclass();
//    Class clzss =(Class) (type.getActualTypeArguments())[0];


    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    private DbAssistant dbAssistant;

    private static final DatabaseDialect DATABASE_DIALECT =
            Enum.valueOf(DatabaseDialect.class, PropertyReader.get("DATABASE_DIALECT", "jdbc.properties"));


    public BaseDaoImpl() {
        switch (DATABASE_DIALECT) {
            case mysql:
                dbAssistant = new MysqlAssistant();
                break;
            case oracle:
                dbAssistant = new OracleAssistant();
                break;
            default:
                throw new RuntimeException("必须要在jdbc.properties里面配置DATABASE_DIALECT");
        }

    }


    public Class getActualTypeOfParamT() {
        return actualTypeOfParamT;
    }

    public void setActualTypeOfParamT(Class actualTypeOfParamT) {
        this.actualTypeOfParamT = actualTypeOfParamT;
    }

    @Override
    public void save(T t) {
        try {
            TableMetaData tableMetaData = this.dbAssistant.resolveTable(t);
            final String sql = tableMetaData.getDefaultInsertSql().getSql();
            final Object[] values = this.dbAssistant.generateInsertValues(t, tableMetaData);
            if (tableMetaData.isWithIdentity()) {
                GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
                this.jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(sql, 1);
                        DbAssistant.resolvePrepareStatement(ps, values);
                        return ps;
                    }
                }, keyHolder);
                this.dbAssistant.resolveIdentityKey(tableMetaData, t, keyHolder);
            } else {
                this.jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps = con.prepareStatement(sql);
                        DbAssistant.resolvePrepareStatement(ps, values);
                        return ps;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(T t) {
        try {
            TableMetaData tableMetaData = this.dbAssistant.resolveTable(t);
            final Object[] values = this.dbAssistant.generateUpdateValues(t, tableMetaData);
            final String sql = tableMetaData.getDefaultUpdateSql().getSql();
            this.jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(sql);
                    DbAssistant.resolvePrepareStatement(ps, values);
                    return ps;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public T get(Object primaryKey) {
        Object obj = new Object();
        try {
            TableMetaData tableMetaData = this.dbAssistant.resolveTable(this.getActualTypeOfParamT());
            final String sql = this.dbAssistant.generateSingleObjectQuerySql(tableMetaData);
            obj = this.jdbcTemplate.query(sql,
                    new Object[]{primaryKey},
                    new ResultSetExtractor<Object>() {
                        @Override
                        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                            rs.getObject("student_name");
                            return null;
//                            return DbAssistant.resolveResultSet(BaseDaoImpl.this.getActualTypeOfParamT(), rs, tableMetaData);
                        }


                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return (T) obj;
    }


    private enum DatabaseDialect {
        mysql, oracle
    }
}
