swagger,���ġ�ק������˼������һ������ǿ���api��ܣ����ļ��ɷǳ��򵥣������ṩ�������ĵ��Ĳ��ģ����һ��ṩ�������ĵ��Ĳ��ԡ�����swagger�����׹���restful����api��������˧���������������֡�

һ����������

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.6.1</version>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.6.1</version>
        </dependency>


����д������

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.forezp.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot����swagger����api�ĵ�")
                .description("�����ŵ�restfun���http://blog.csdn.net/forezp")
                .termsOfServiceUrl("http://blog.csdn.net/forezp")
                .version("1.0")
                .build();
    }
}



ͨ��@Configurationע�⣬��������һ�������࣬@EnableSwagger2����swagger2��apiINfo()����һЩ��������Ϣ��apis()ָ��ɨ��İ��������ĵ���

����д�����ĵ���ע��

swaggerͨ��ע������ýӿڻ������ĵ��������ӿ��������󷽷���������������Ϣ�ĵȵȡ�

    @Api�����������࣬����Controller������
    @ApiOperation������һ�����һ������������˵һ���ӿ�
    @ApiParam��������������
    @ApiModel���ö��������ղ���
    @ApiProperty���ö�����ղ���ʱ�����������һ���ֶ�
    @ApiResponse��HTTP��Ӧ����1������
    @ApiResponses��HTTP��Ӧ��������
    @ApiIgnore��ʹ�ø�ע��������API
    @ApiError ���������󷵻ص���Ϣ
    @ApiParamImplicitL��һ���������
    @ApiParamsImplicit ����������

����ͨ��һ��������˵����

package com.forezp.controller;

import com.forezp.entity.Book;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * �û�����ĳ��ͼ�� POST    /books/
 * �û��޸Ķ�ĳ��ͼ��    PUT /books/:id/
 * �û�ɾ����ĳ��ͼ��    DELETE  /books/:id/
 * �û���ȡ���е�ͼ�� GET /books
 *  �û���ȡĳһͼ��  GET /Books/:id
 * Created by fangzhipeng on 2017/4/17.
 * �ٷ��ĵ���http://swagger.io/docs/specification/api-host-and-base-path/
 */
@RestController
@RequestMapping(value = "/books")
public class BookContrller {

    Map<Long, Book> books = Collections.synchronizedMap(new HashMap<Long, Book>());

    @ApiOperation(value="��ȡͼ���б�", notes="��ȡͼ���б�")
    @RequestMapping(value={""}, method= RequestMethod.GET)
    public List<Book> getBook() {
        List<Book> book = new ArrayList<>(books.values());
        return book;
    }

    @ApiOperation(value="����ͼ��", notes="����ͼ��")
    @ApiImplicitParam(name = "book", value = "ͼ����ϸʵ��", required = true, dataType = "Book")
    @RequestMapping(value="", method=RequestMethod.POST)
    public String postBook(@RequestBody Book book) {
        books.put(book.getId(), book);
        return "success";
    }
    @ApiOperation(value="��ͼ��ϸ��Ϣ", notes="����url��id����ȡ��ϸ��Ϣ")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Book getBook(@PathVariable Long id) {
        return books.get(id);
    }

    @ApiOperation(value="������Ϣ", notes="����url��id��ָ������ͼ����Ϣ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ͼ��ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "book", value = "ͼ��ʵ��book", required = true, dataType = "Book")
    })
    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @RequestBody Book book) {
        Book book1 = books.get(id);
        book1.setName(book.getName());
        book1.setPrice(book.getPrice());
        books.put(id, book1);
        return "success";
    }
    @ApiOperation(value="ɾ��ͼ��", notes="����url��id��ָ��ɾ��ͼ��")
    @ApiImplicitParam(name = "id", value = "ͼ��ID", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        books.remove(id);
        return "success";
    }

    @ApiIgnore//ʹ�ø�ע��������API
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String  jsonTest() {
        return " hi you!";
    }
}



ͨ�����ע�⣬�Ϳ�����swagger2������Ӧ���ĵ�������㲻��Ҫĳ�ӿ������ĵ���ֻ��Ҫ�ڼ�@ApiIgnoreע�⼴�ɡ���Ҫ˵�����ǣ�������������url�ϣ�@ApiImplicitParam �ϼ�paramType = ��path�� ��

�������̣����ʣ�http://localhost:8080/swagger-ui.html ���Ϳ���swagger-ui:

Paste_Image.png

�������ɹ��̷ǳ��򵥣������ҿ�����ص����ϣ�swaggerû������ȫ����ķ�����������Ҫ�����Լ�����صĹ�����
�ġ��ο�����
Spring Boot��ʹ��Swagger2����ǿ���RESTful API�ĵ�
http://blog.didispace.com/springbootswagger2/
