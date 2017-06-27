
BeetSql��һ��ȫ����DAO���ߣ� ͬʱ����hibernate �ŵ� & Mybatis�ŵ㹦�ܣ������ڳ�����SQLΪ���ģ�ͬʱ�����󹤾����Զ������ɴ������õ�SQL��Ӧ�á�
beatlsql �ŵ�

    ����Ч��
        ����ע�⣬�Զ�ʹ�ô�������SQL�����������ɾ�Ĳ鹦�ܣ���ʡ50%�Ŀ���������
        ����ģ��֧��Pojo��Ҳ֧��Map/List���ֿ���ģ�ͣ�Ҳ֧�ֻ��ģ��
        SQL ģ�����Beetlʵ�֣�������д�͵��ԣ��Լ���չ

    ά����
        SQL �Ը����ķ�ʽ��Markdown��ʽ���й���ͬʱ������򿪷������ݿ�SQL���ԡ�
        �����Զ���sql�ļ�ӳ��Ϊdao�ӿ���
        ���ֱ�۵�֧��֧��һ��һ��һ�Զ࣬��Զ��ϵӳ��������븴�ӵ�OR Mapping����ͼ�����
        �߱�Interceptor���ܣ����Ե��ԣ��������SQL���Լ���չ��������
    ����
        ����֧���������ݿ�֧�ֵĿ�Դ����
        ֧�ֿ����ݿ�ƽ̨�����������蹤�����ٵ���С��Ŀǰ�����ݿ�֧��mysql,postgres,oracle,sqlserver,h2,sqllite,DB2.

��������


<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>com.ibeetl</groupId>
            <artifactId>beetl</artifactId>
            <version>2.3.2</version>

        </dependency>

        <dependency>
            <groupId>com.ibeetl</groupId>
            <artifactId>beetlsql</artifactId>
            <version>2.3.1</version>

        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.0.5</version>
        </dependency>

�⼸���������Ǳ���ġ�
���Ͻ׶�

����springbootû�ж� beatlsql�Ŀ�������װ�䣬������Ҫ���Լ�������ص�bean����������Դ����ɨ�裬����������ȡ�

��application�������´��룺


@Bean(initMethod = "init", name = "beetlConfig")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
        try {
            // WebAppResourceLoader ����root·���ǹؼ�
            WebAppResourceLoader webAppResourceLoader = new WebAppResourceLoader(patternResolver.getResource("classpath:/templates").getFile().getPath());
            beetlGroupUtilConfiguration.setResourceLoader(webAppResourceLoader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //��ȡ�����ļ���Ϣ
        return beetlGroupUtilConfiguration;

    }

    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }

    //���ð�ɨ��
    @Bean(name = "beetlSqlScannerConfigurer")
    public BeetlSqlScannerConfigurer getBeetlSqlScannerConfigurer() {
        BeetlSqlScannerConfigurer conf = new BeetlSqlScannerConfigurer();
        conf.setBasePackage("com.forezp.dao");
        conf.setDaoSuffix("Dao");
        conf.setSqlManagerFactoryBeanName("sqlManagerFactoryBean");
        return conf;
    }

    @Bean(name = "sqlManagerFactoryBean")
    @Primary
    public SqlManagerFactoryBean getSqlManagerFactoryBean(@Qualifier("datasource") DataSource datasource) {
        SqlManagerFactoryBean factory = new SqlManagerFactoryBean();

        BeetlSqlDataSource source = new BeetlSqlDataSource();
        source.setMasterSource(datasource);
        factory.setCs(source);
        factory.setDbStyle(new MySqlStyle());
        factory.setInterceptors(new Interceptor[]{new DebugInterceptor()});
        factory.setNc(new UnderlinedNameConversion());//�����շ�
        factory.setSqlLoader(new ClasspathLoader("/sql"));//sql�ļ�·��
        return factory;
    }


    //�������ݿ�
    @Bean(name = "datasource")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().url("jdbc:mysql://127.0.0.1:3306/test").username("root").password("123456").build();
    }

    //��������
    @Bean(name = "txManager")
    public DataSourceTransactionManager getDataSourceTransactionManager(@Qualifier("datasource") DataSource datasource) {
        DataSourceTransactionManager dsm = new DataSourceTransactionManager();
        dsm.setDataSource(datasource);
        return dsm;
    }



��resouces���£���META_INF�ļ��У��ļ����м���spring-devtools.properties:

restart.include.beetl=/beetl-2.3.2.jar
restart.include.beetlsql=/beetlsql-2.3.1.jar



��templates�¼�һ��index.btl�ļ���

����jar������beatlsql����Щbean���Լ�resources��Щ����֮��springboot���ܹ����ʵ����ݿ��ࡣ
�ٸ�restful������
��ʼ�����ݿ�ı�

# DROP TABLE `account` IF EXISTS
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `money` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
INSERT INTO `account` VALUES ('1', 'aaa', '1000');
INSERT INTO `account` VALUES ('2', 'bbb', '1000');
INSERT INTO `account` VALUES ('3', 'ccc', '1000');

   

bean

public class Account {
    private int id ;
    private String name ;
    private double money;

    getter...

    setter...

  }  


���ݷ���dao��

public interface AccountDao extends BaseMapper<Account> {

    @SqlStatement(params = "name")
    Account selectAccountByName(String name);
}



�ӿڼ̳�BaseMapper�����ܻ�ȡ�����ѯ��һЩ���ʣ�������Ҫ�Զ���sql��ʱ��ֻ��Ҫ��resouses/sql/account.md�ļ�����д�ļ���


selectAccountByName
===
*����name��account

    select * from account where name= #name#

   

���С�=== ��������Ψһ��ʶ����Ӧ�ڽӿڵķ���������* ��������ע�ͣ�����������Զ����sql��䣬����ļ��ٷ��ĵ���
web��

����ʡ����service�㣬ʵ�ʿ������ϡ�


@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountDao accountDao;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public  List<Account> getAccounts(){
       return accountDao.all();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public  Account getAccountById(@PathVariable("id") int id){
        return accountDao.unique(id);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public  Account getAccountById(@RequestParam("name") String name){
        return accountDao.selectAccountByName(name);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public  String updateAccount(@PathVariable("id")int id , @RequestParam(value = "name",required = true)String name,
    @RequestParam(value = "money" ,required = true)double money){
        Account account=new Account();
        account.setMoney(money);
        account.setName(name);
        account.setId(id);
        int t=accountDao.updateById(account);
        if(t==1){
            return account.toString();
        }else {
            return "fail";
        }
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public  String postAccount( @RequestParam(value = "name")String name,
                                 @RequestParam(value = "money" )double money) {
        Account account = new Account();
        account.setMoney(money);
        account.setName(name);
        KeyHolder t = accountDao.insertReturnKey(account);
        if (t.getInt() > 0) {
            return account.toString();
        } else {
            return "fail";
        }
    }
}   

ͨ��postman ���ԣ�������ȫ��ͨ����

����ʹ�ø��ܣ�ʹ��bealsql����һЩ��Ŀ�����飬����û���������������������������������ǳ���ˬ������springbootû���ṩ�Զ�װ���ֱ��֧�֣���Ҫ�Լ�ע��bean������ʹ�����orm���˲�̫�࣬��ľ�пӲ�֪��������ʹ�õĹ�����û������ʲô���⡣�������������ĵ��Ƚ��Ѻá�