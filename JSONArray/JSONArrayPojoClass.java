package JSONArray;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import POJO.Employee;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class JSONArrayPojoClass {

	@Test
	public void CreateEmployeesJSONArrays() throws JsonProcessingException {

		// Create First Employee Object

		Employee emp1 = new Employee();
		emp1.setFirstname("Shamshad");
		emp1.setLastname("Alam");
		emp1.setGender("Male");
		emp1.setAge(23);
		emp1.setSalary(45000.00);

		// Create Second Employee Object

		Employee emp2 = new Employee();
		emp2.setFirstname("Zoya");
		emp2.setLastname("Hussain");
		emp2.setGender("Female");
		emp2.setAge(21);
		emp2.setSalary(40000.00);

		// Create Third Employee Object

		Employee emp3 = new Employee();
		emp3.setFirstname("Zeenat");
		emp3.setLastname("Parveen");
		emp3.setGender("Female");
		emp3.setAge(19);
		emp3.setSalary(50000.00);

		// Create Fourth Employee Object

		Employee emp4 = new Employee();
		emp4.setFirstname("Md Asif");
		emp4.setLastname("Hussain");
		emp4.setGender("Male");
		emp4.setAge(20);
		emp4.setSalary(1000000.00);
		
		//Create List Of Employee
		List<Employee> listOfEmp = new ArrayList<Employee>();
		listOfEmp.add(emp1);
		listOfEmp.add(emp2);
		listOfEmp.add(emp3);
		listOfEmp.add(emp4);
		
		//Convert employee Class object to json array payload
		
		ObjectMapper objMapper = new ObjectMapper();
		String JsonArrayPayload = objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfEmp);
		System.out.println("Employee class Object to Json Array Payload");
		System.out.println(JsonArrayPayload);
		
		// Create Request Specification 
		
		RequestSpecification reqspeci = new RestAssured().given();
		reqspeci.baseUri("http://httpbin.org/post");
		reqspeci.contentType(ContentType.JSON);
		reqspeci.body(JsonArrayPayload);
		
		//Perform POST Request
		Response response  = reqspeci.post();
		System.out.println("------------ResponseBody-----------");
		response.prettyPrint();
		
		//Verify Status Code
		
		Assert.assertEquals(response.statusCode(),200,"Check for Staus code.");
		
		ResponseBody responseBody = response.getBody();
		JsonPath jsonPathView = responseBody.jsonPath();
		
		List<Employees> AllEmployees = jsonPathView.getList("json",Employees.class);
		
		System.out.println("------------Employee Objects in ResponseBody-----------");
		
		for(Employees emp : AllEmployees) {
			System.out.println(emp.getFirstname()+ " "+emp.getLastname());
		}
		
	}

}
