package cn.exrick.manager.mapper;

import cn.exrick.common.utils.ObjectId;
import cn.exrick.manager.pojo.TbMember;
import cn.exrick.manager.pojo.TbMemberExample;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
public class InitId {

    @Test
    public void test() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://192.168.31.234:3306/xmall");
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUsername("xmall");
        druidDataSource.setPassword("EusbI7pr91%h$3BCX");

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, druidDataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(TbMemberMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession session = sqlSessionFactory.openSession();
        TbMemberMapper tbMemberMapper = session.getMapper(TbMemberMapper.class);
        TbMemberExample example = new TbMemberExample();
        List<TbMember> members = tbMemberMapper.selectByExample(example);
        log.debug("Members is {}", members);
        Transaction transaction = transactionFactory.newTransaction(session.getConnection());
        for (TbMember tbMember : members) {
            tbMember.setMid(ObjectId.getId());
            tbMemberMapper.updateByPrimaryKey(tbMember);
        }
        transaction.commit();
        transaction.close();
        session.close();
    }
}
