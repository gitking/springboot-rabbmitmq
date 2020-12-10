package com.self.learnjava.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.self.learnjava.entity.User;
import com.self.learnjava.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/*
 * 集成第三方组件
 * 和Spring相比，使用Spring Boot通过自动配置来集成第三方组件通常来说更简单。
 * 我们将详细介绍如何通过Spring Boot集成常用的第三方组件，包括：
 * Open API
 * Redis
 * Artemis
 * RabbitMQ
 * Kafka
 * 集成Open API
 * Open API(https://www.openapis.org/)是一个标准，它的主要作用是描述REST API，既可以作为文档给开发者阅读，又可以让机器根据这个文档自动生成客户端代码等。
 * 在Spring Boot应用中，假设我们编写了一堆REST API，如何添加Open API的支持？
 * 我们只需要在pom.xml中加入以下依赖：
 * <dependency>
		<groupId>org.springdoc</groupId>
		<artifactId>springdoc-openapi-ui</artifactId>
		<version>${openapi.version}</version>
	</dependency>
 * 然后呢？没有然后了，直接启动应用，打开浏览器输入http://localhost:8080/swagger-ui.html：
 * 立刻可以看到自动生成的API文档，这里列出了3个API，来自api-controller（因为定义在ApiController这个类中），点击某个API还可以交互，即输入API参数，点“Try it out”按钮，获得运行结果。
 * 是不是太方便了！
 * 因为我们引入springdoc-openapi-ui这个依赖后，它自动引入Swagger UI用来创建API文档。可以给API加入一些描述信息，例如：
 *  @Operation(summary = "Get specific user object by it's id.")
	@GetMapping("/users/{id}")
	public User user(@Parameter(description = "id of the user.") @PathVariable("id") long id) {
		return userService.getUserById(id);
	}
 * @Operation可以对API进行描述，@Parameter可以对参数进行描述，它们的目的是用于生成API文档的描述信息。添加了描述的API文档如下：
 * 大多数情况下，不需要任何配置，我们就直接得到了一个运行时动态生成的可交互的API文档，该API文档总是和代码保持同步，大大简化了文档的编写工作。
 * 要自定义文档的样式、控制某些API显示等，请参考springdoc文档(https://springdoc.org/)。
 * 小结
 * 使用springdoc让其自动创建API文档非常容易，引入依赖后无需任何配置即可访问交互式API文档。
 * 可以对API添加注解以便生成更详细的描述。
 */
@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/users")
	public List<User> users() {
		return userService.getUsers();
	}
	
	@Operation(summary="OpenAPI,根据用户ID获取指定的用户信息")
	@GetMapping("/user/{id}")
	public User user(@Parameter(description="OpenAPI自动生成文档,用户ID")@PathVariable("id") long id) {
		return userService.getUserById(id);
	}
	
	@PostMapping("/signin")
	public Map<String, Object> signin(@RequestBody SignInRequest signinRequest) {
		try {
			User user = userService.signin(signinRequest.email, signinRequest.password);
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("user", user);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> error = new HashMap<String, Object>();
			error.put("error", "SINGIN_FAILED");
			error.put("message", e.getMessage());
			return error;
		}
	}
	
	public static class SignInRequest {
		public String email;
		public String password;
	}
}
