package com.uniovi.tests;
//Paquetes Java
import java.util.List;
//Paquetes JUnit 
import org.junit.*;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.assertTrue;
//Paquetes Selenium 
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
//Paquetes Utilidades de Testing Propias
import com.uniovi.tests.util.SeleniumUtils;
//Paquetes con los Page Object
import com.uniovi.tests.pageobjects.*;


//Ordenamos las pruebas por el nombre del mÃ©todo
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class NotaneitorTests {
	//En Windows (Debe ser la versiÃ³n 65.0.1 y desactivar las actualizacioens automÃ¡ticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox6501\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\UO258774\\git\\SDI\\entrega2\\geckodriver024win64.exe";
	//En MACOSX (Debe ser la versiÃ³n 65.0.1 y desactivar las actualizacioens automÃ¡ticas):
	//static String PathFirefox65 = "/Applications/Firefox 2.app/Contents/MacOS/firefox-bin";
	//static String PathFirefox64 = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	//static String Geckdriver024 = "/Users/delacal/Documents/SDI1718/firefox/geckodriver024mac";
	//static String Geckdriver022 = "/Users/delacal/Documents/SDI1718/firefox/geckodriver023mac";
	//ComÃºn a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024); 
	static String URL = "https://localhost:8081";


	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}


	@Before
	public void setUp(){
		driver.navigate().to(URL);
	}
	@After
	public void tearDown(){
		driver.manage().deleteAllCookies();
	}
	@BeforeClass 
	static public void begin() {
		//COnfiguramos las pruebas.
		//Fijamos el timeout en cada opción de carga de una vista.
		//Pongo tanto tiempo porque a veces el tiempo de carga
		//depende de la base de datos mongodb		
		PO_View.setTimeout(30);


		//#####################################################################
		//########################## ATENCIÓN #################################
		//#####################################################################
		//En una aplicación comercial no se haría esto bajo ninguna
		//circunstancia. Solo tiene la intención de facilitar la
		//ejecución de los test.
		driver.navigate().to(URL+"/borrarUsuarios");

		//Crear de nuevo los usuarios de prueba
		for(int i=1;i<13;i++) {
			PO_RegisterView.clickOption(driver, "registrarse", "class", "btn btn-primary");
			PO_RegisterView.fillForm(driver, "user"+i+"@email.com", "user"+i, "user"+i, "123456", "123456");	
		}
	}
	@AfterClass
	static public void end() {
		//Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	//PR01. Sin hacer /
	@Test
	public void PR01() {
		PO_RegisterView.clickOption(driver, "registrarse", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "prueba1@email.com", "Nombre1", "Apellido1 Apellido1", "123456", "123456");
		PO_View.checkElement(driver, "text", "Nuevo usuario registrado");
	}

	//PR02. Sin hacer /
	@Test
	public void PR02() {
		PO_RegisterView.clickOption(driver, "registrarse", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "", "", "", "123456", "123456");	
	}

	//PR03. Sin hacer /
	@Test
	public void PR03() {
		PO_RegisterView.clickOption(driver, "registrarse", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "prueba2@email.com", "Nombre1", "Apellido1 Apellido1", "1234567", "123456");	
		PO_View.checkElement(driver, "text", "Las contraseñas no coinciden");
	}

	//PR04. Sin hacer /
	@Test
	public void PR04() {
		PO_RegisterView.clickOption(driver, "registrarse", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "prueba1@email.com", "Nombre1", "Apellido1 Apellido1", "123456", "123456");
		PO_View.checkElement(driver, "text", "Este email ya está registrado");		
	}

	//PR05. Sin hacer /
	@Test
	public void PR05() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
		PO_LoginView.checkElement(driver, "text", "Usuarios");
		PO_LoginView.clickOption(driver, "desconectarse", "class", "btn btn-primary");
	}

	//PR06. Sin hacer /
	@Test
	public void PR06() {	
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "", "");
		PO_View.checkElement(driver, "text", "Identificación de usuario");
	}

	//PR07. Sin hacer /
	@Test
	public void PR07() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "1234567");
		PO_LoginView.checkElement(driver, "text", "Email o password incorrecto");
	}	

	//PR08. Sin hacer /
	@Test
	public void PR08() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "prueba1noexistente@email.com", "1234567");
		PO_LoginView.checkElement(driver, "text", "Email o password incorrecto");			
	}	

	//PR09. Sin hacer /
	@Test
	public void PR09() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
		PO_LoginView.checkElement(driver, "text", "Usuarios");
		PO_LoginView.clickOption(driver, "desconectarse", "class", "btn btn-primary");
		PO_LoginView.checkElement(driver, "text", "Se ha desconectado con éxito.");
		PO_LoginView.checkElement(driver, "text", "Identificación de usuario");
	}	
	//PR10. Sin hacer /
	@Test
	public void PR10() {
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Desconectarse",
				PO_View.getTimeout());		
	}	

	//PR11. Sin hacer /
	@Test
	public void PR11() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
		PO_View.checkElement(driver, "text", "user1@email.com");
		PO_View.checkElement(driver, "text", "user2@email.com");
		PO_View.checkElement(driver, "text", "user3@email.com");
		PO_View.checkElement(driver, "text", "user4@email.com");
		PO_View.checkElement(driver, "text", "user5@email.com");
		driver.navigate().to("https://localhost:8081/verUsuarios?pg=2");
		PO_View.checkElement(driver, "text", "user6@email.com");
		PO_View.checkElement(driver, "text", "user7@email.com");
		PO_View.checkElement(driver, "text", "user8@email.com");
		PO_View.checkElement(driver, "text", "user9@email.com");
		PO_View.checkElement(driver, "text", "user10@email.com");
		driver.navigate().to("https://localhost:8081/verUsuarios?pg=3");
		PO_View.checkElement(driver, "text", "user11@email.com");
		PO_View.checkElement(driver, "text", "user12@email.com");
	}	

	//PR12. Sin hacer /
	@Test
	public void PR12() {
		driver.navigate().to("https://localhost:8081/identificarse");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
		driver.navigate().to("https://localhost:8081/verUsuarios?busqueda="+"");
		PO_PrivateView.checkElement(driver, "text", "user1@email.com");
		PO_PrivateView.checkElement(driver, "text", "user2@email.com");
		PO_PrivateView.checkElement(driver, "text", "user3@email.com");
		PO_PrivateView.checkElement(driver, "text", "user4@email.com");
		PO_PrivateView.checkElement(driver, "text", "user5@email.com");
	}	

	//PR13. Sin hacer /
	@Test
	public void PR13() {
		driver.navigate().to("https://localhost:8081/identificarse");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
		driver.navigate().to("https://localhost:8081/verUsuarios?busqueda="+"userNoExiste");
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user1@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user2@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user3@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user4@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user5@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user5@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user6@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user7@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user8@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user9@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user10@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user11@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user12@email.com",
				PO_View.getTimeout());	
	}	

	//PR14. Sin hacer /
	@Test
	public void PR14() {
		driver.navigate().to("https://localhost:8081/identificarse");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
		driver.navigate().to("https://localhost:8081/verUsuarios?busqueda="+"user1");
		PO_PrivateView.checkElement(driver, "text", "user1@email.com");
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user2@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user3@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user4@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user5@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user5@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user6@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user7@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user8@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user9@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user10@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user11@email.com",
				PO_View.getTimeout());	
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user12@email.com",
				PO_View.getTimeout());			
	}	

	//PR15. Sin hacer /
	//@Test
	public void PR15() {
		assertTrue("PR15 sin hacer", false);			
	}	

	//PR16. Sin hacer /
	//@Test
	public void PR16() {
		assertTrue("PR16 sin hacer", false);			
	}	

	//PR017. Sin hacer /
	//@Test
	public void PR17() {
		assertTrue("PR17 sin hacer", false);			
	}	

	//PR18. Sin hacer /
	//@Test
	public void PR18() {
		assertTrue("PR18 sin hacer", false);			
	}	

	//PR19. Sin hacer /
	//@Test
	public void PR19() {
		assertTrue("PR19 sin hacer", false);			
	}	

	//P20. Sin hacer /
	//@Test
	public void PR20() {
		assertTrue("PR20 sin hacer", false);			
	}	

	//PR21. Sin hacer /
	//@Test
	public void PR21() {
		assertTrue("PR21 sin hacer", false);			
	}	

	//PR22. Sin hacer /
	//@Test
	public void PR22() {
		assertTrue("PR22 sin hacer", false);			
	}	

	//PR23. Sin hacer /
	//@Test
	public void PR23() {
		assertTrue("PR23 sin hacer", false);			
	}	

	//PR24. Sin hacer /
	//@Test
	public void PR24() {
		assertTrue("PR24 sin hacer", false);			
	}	
	//PR25. Sin hacer /
	//@Test
	public void PR25() {
		assertTrue("PR25 sin hacer", false);			
	}	

	//PR26. Sin hacer /
	//@Test
	public void PR26() {
		assertTrue("PR26 sin hacer", false);			
	}	

	//PR27. Sin hacer /
	//@Test
	public void PR27() {
		assertTrue("PR27 sin hacer", false);			
	}	

	//PR029. Sin hacer /
	//@Test
	public void PR29() {
		assertTrue("PR29 sin hacer", false);			
	}

	//PR030. Sin hacer /
	//@Test
	public void PR30() {
		assertTrue("PR30 sin hacer", false);			
	}

	//PR031. Sin hacer /
	//@Test
	public void PR31() {
		assertTrue("PR31 sin hacer", false);			
	}


}

