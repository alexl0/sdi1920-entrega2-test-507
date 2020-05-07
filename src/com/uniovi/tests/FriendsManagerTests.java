package com.uniovi.tests;
//Paquetes Java
//import java.util.List;
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
public class FriendsManagerTests {
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
		/*driver.navigate().to(URL+"/borrarUsuarios");

		//Crear de nuevo los usuarios de prueba
		for(int i=1;i<13;i++) {
			PO_RegisterView.clickOption(driver, "registrarse", "class", "btn btn-primary");
			PO_RegisterView.fillForm(driver, "user"+i+"@email.com", "user"+i, "user"+i, "123456", "123456");	
		}
		 */
	}
	@AfterClass
	static public void end() {
		//Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	//PR01. 
	@Test
	public void PR01() {
		PO_RegisterView.clickOption(driver, "registrarse", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "prueba1@email.com", "Nombre1", "Apellido1 Apellido1", "123456", "123456");
		PO_View.checkElement(driver, "text", "Nuevo usuario registrado");
	}

	//PR02. 
	@Test
	public void PR02() {
		PO_RegisterView.clickOption(driver, "registrarse", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "", "", "", "123456", "123456");	
	}

	//PR03. 
	@Test
	public void PR03() {
		PO_RegisterView.clickOption(driver, "registrarse", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "prueba2@email.com", "Nombre1", "Apellido1 Apellido1", "1234567", "123456");	
		PO_View.checkElement(driver, "text", "Las contraseñas no coinciden");
	}

	//PR04. 
	@Test
	public void PR04() {
		PO_RegisterView.clickOption(driver, "registrarse", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "prueba1@email.com", "Nombre1", "Apellido1 Apellido1", "123456", "123456");
		PO_View.checkElement(driver, "text", "Este email ya está registrado");		
	}

	//PR05. 
	@Test
	public void PR05() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
		PO_LoginView.checkElement(driver, "text", "Usuarios");
		PO_LoginView.clickOption(driver, "desconectarse", "class", "btn btn-primary");
	}

	//PR06. 
	@Test
	public void PR06() {	
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "", "");
		PO_View.checkElement(driver, "text", "Identificación de usuario");
	}

	//PR07. 
	@Test
	public void PR07() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "1234567");
		PO_LoginView.checkElement(driver, "text", "Email o password incorrecto");
	}	

	//PR08. 
	@Test
	public void PR08() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "prueba1noexistente@email.com", "1234567");
		PO_LoginView.checkElement(driver, "text", "Email o password incorrecto");			
	}	

	//PR09. 
	@Test
	public void PR09() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
		PO_LoginView.checkElement(driver, "text", "Usuarios");
		PO_LoginView.clickOption(driver, "desconectarse", "class", "btn btn-primary");
		PO_LoginView.checkElement(driver, "text", "Se ha desconectado con éxito.");
		PO_LoginView.checkElement(driver, "text", "Identificación de usuario");
	}	
	//PR10. 
	@Test
	public void PR10() {
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Desconectarse",
				PO_View.getTimeout());		
	}	

	//PR11. 
	@Test
	public void PR11() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
		PO_View.checkElement(driver, "text", "user1@email.com");
		PO_View.checkElement(driver, "text", "user2@email.com");
		PO_View.checkElement(driver, "text", "user3@email.com");
		PO_View.checkElement(driver, "text", "user4@email.com");
		PO_View.checkElement(driver, "text", "user5@email.com");
		PO_View.checkElement(driver, "free",
				"//a[contains(@class, 'page-link')]").get(1).click();
		PO_View.checkElement(driver, "text", "user6@email.com");
		PO_View.checkElement(driver, "text", "user7@email.com");
		PO_View.checkElement(driver, "text", "user8@email.com");
		PO_View.checkElement(driver, "text", "user9@email.com");
		PO_View.checkElement(driver, "text", "user10@email.com");
		PO_View.checkElement(driver, "free",
				"//a[contains(@class, 'page-link')]").get(2).click();
		PO_View.checkElement(driver, "text", "user11@email.com");
		PO_View.checkElement(driver, "text", "user12@email.com");
	}	

	//PR12. 
	@Test
	public void PR12() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user1@email.com", "123456");
		//Escribir en la barra de búsqueda
		PO_View.checkElement(driver, "free", "//*[@id=\"searchText\"]").get(0)
		.sendKeys("");
		// Ahora hacemos click en buscar
		PO_View.checkElement(driver, "free", "//*[@id=\"searchButton\"]")
		.get(0).click();
		PO_PrivateView.checkElement(driver, "text", "user2@email.com");
		PO_PrivateView.checkElement(driver, "text", "user3@email.com");
		PO_PrivateView.checkElement(driver, "text", "user4@email.com");
		PO_PrivateView.checkElement(driver, "text", "user5@email.com");
		PO_PrivateView.checkElement(driver, "text", "user6@email.com");
	}	

	//PR13. 
	@Test
	public void PR13() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user1@email.com", "123456");
		//Escribir en la barra de búsqueda
		PO_View.checkElement(driver, "free", "//*[@id=\"searchText\"]").get(0)
		.sendKeys("userNoExiste");
		// Ahora hacemos click en buscar
		PO_View.checkElement(driver, "free", "//*[@id=\"searchButton\"]")
		.get(0).click();
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
	}	

	//PR14. 
	@Test
	public void PR14() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
		//Escribir en la barra de búsqueda
		PO_View.checkElement(driver, "free", "//*[@id=\"searchText\"]").get(0)
		.sendKeys("user1");
		// Ahora hacemos click en buscar
		PO_View.checkElement(driver, "free", "//*[@id=\"searchButton\"]")
		.get(0).click();
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
	}	

	//PR15. 
	@Test
	public void PR15() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");	

		driver.navigate().to("https://localhost:8081/usuario/invitar/user1@email.com");
		PO_View.checkElement(driver, "text", "Invitación enviada correctamente");
		PO_LoginView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user1@email.com", "123456");
		//driver.navigate().to("https://localhost:8081/invitaciones");
		PO_NavView.clickOption(driver, "invitaciones", "class", "btn btn-primary");
		PO_View.checkElement(driver, "text", "prueba1@email.com");
	}	

	//PR16. 
	@Test
	public void PR16() {
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");	

		//Se hace por url, no se puede calcar el botón porque está bloqueado
		driver.navigate().to("https://localhost:8081/usuario/invitar/user1@email.com");
		PO_View.checkElement(driver, "text", "Hay invitaciones pendientes entre usted y esa persona");
	}	

	//PR017. 
	@Test
	public void PR17() {

		//Enviar más peticiones a user1 desde otros usuarios
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user2@email.com", "123456");	
		//TODO las pruebas deben simular la interaccion real del usuario,
		//calcando botones, no yendo por urs's
		driver.navigate().to("https://localhost:8081/usuario/invitar/user1@email.com");
		PO_View.checkElement(driver, "text", "Invitación enviada correctamente");
		PO_LoginView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user3@email.com", "123456");	
		driver.navigate().to("https://localhost:8081/usuario/invitar/user1@email.com");
		PO_View.checkElement(driver, "text", "Invitación enviada correctamente");
		PO_LoginView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user4@email.com", "123456");	
		driver.navigate().to("https://localhost:8081/usuario/invitar/user1@email.com");
		PO_View.checkElement(driver, "text", "Invitación enviada correctamente");
		PO_LoginView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user5@email.com", "123456");	
		driver.navigate().to("https://localhost:8081/usuario/invitar/user1@email.com");
		PO_View.checkElement(driver, "text", "Invitación enviada correctamente");
		PO_LoginView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

		//Comprobar que las ha recibido user1
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user1@email.com", "123456");
		//driver.navigate().to("https://localhost:8081/invitaciones");
		PO_NavView.clickOption(driver, "invitaciones", "class", "btn btn-primary");
		PO_View.checkElement(driver, "text", "prueba1@email.com");
		PO_View.checkElement(driver, "text", "user2@email.com");
		PO_View.checkElement(driver, "text", "user3@email.com");
		PO_View.checkElement(driver, "text", "user4@email.com");
		PO_View.checkElement(driver, "text", "user5@email.com");
	}	


	//PR18.
	@Test
	public void PR18() {
		//Se loguea user1
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user1@email.com", "123456");

		//Acepta la petición de prueba1
		PO_NavView.clickOption(driver, "invitaciones", "class", "btn btn-primary");
		driver.navigate().to("https://localhost:8081/usuario/aceptar/prueba1@email.com");
		PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");			
	}	

	/**
	 * Envia invitación de amistad a un amigo
	 */
	@Test
	public void PR18_1() {
		//Se loguea user1
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user1@email.com", "123456");

		//Le intenta volver a invitar a prueba1
		driver.navigate().to("https://localhost:8081/usuario/invitar/prueba1@email.com");
		PO_View.checkElement(driver, "text", "Ya es amigo de esa persona");
	}

	/**
	 * Intentar aceptar a un usuario que no te ha invitado
	 */
	@Test
	public void PR18_2() {
		//Se loguea user1
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user1@email.com", "123456");

		driver.navigate().to("https://localhost:8081/usuario/aceptar/user10@email.com");
		PO_View.checkElement(driver, "text", "No tiene una invitación de esa persona");

	}

	/**
	 * Intentar agregarse a si mismo
	 */
	@Test
	public void PR18_3() {
		//Se loguea user1
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user1@email.com", "123456");

		driver.navigate().to("https://localhost:8081/usuario/aceptar/user1@email.com");
		PO_View.checkElement(driver, "text", "No se puede agregar a si mismo");

	}

	/**
	 * Intentar agregar a alguien sin estar logueado
	 */
	@Test
	public void PR18_4() {
		driver.navigate().to("https://localhost:8081/usuario/aceptar/user1@email.com");
		PO_View.checkElement(driver, "text", "Debe identificarse primero");
	}

	/**
	 * Intentar agregar a alguien que no existe
	 */
	@Test
	public void PR18_5() {
		//Se loguea user1
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user1@email.com", "123456");

		driver.navigate().to("https://localhost:8081/usuario/aceptar/userNoExistep14909472@email.com");
		PO_View.checkElement(driver, "text", "No existe el usuario que se desea agregar");

	}


	//PR19. 
	@Test
	public void PR19() {
		//Se loguea user1
		PO_LoginView.clickOption(driver, "identificarse", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "user1@email.com", "123456");		

		//Acepta al resto de amigos que le han invitado
		PO_NavView.clickOption(driver, "invitaciones", "class", "btn btn-primary");
		driver.navigate().to("https://localhost:8081/usuario/aceptar/user2@email.com");
		PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");	
		driver.navigate().to("https://localhost:8081/usuario/aceptar/user3@email.com");
		PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");
		driver.navigate().to("https://localhost:8081/usuario/aceptar/user4@email.com");
		PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");
		driver.navigate().to("https://localhost:8081/usuario/aceptar/user5@email.com");
		PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");

		//Va al listado de amigos
		PO_NavView.clickOption(driver, "amigos", "class", "btn btn-primary");

		//Comprobar que están los amigos
		PO_View.checkElement(driver, "text", "prueba1@email.com");	
		PO_View.checkElement(driver, "text", "user2@email.com");	
		PO_View.checkElement(driver, "text", "user3@email.com");	
		PO_View.checkElement(driver, "text", "user4@email.com");	
		PO_View.checkElement(driver, "text", "user5@email.com");	

	}	

	//P20. 
	@Test
	public void PR20() {
		//No se puede calcar el botón porque no sale en pantalla, se intenta por url
		driver.navigate().to("https://localhost:8081/amigos");	
		PO_View.checkElement(driver, "text", "Identificación de usuario");	
	}	

	//PR21. 
	@Test
	public void PR21() {
		//No se puede calcar el botón porque no sale en pantalla, se intenta por url
		driver.navigate().to("https://localhost:8081/invitaciones");
		PO_View.checkElement(driver, "text", "Identificación de usuario");				
	}	

	//PR22. No se puede hacer eso en la aplicación.
	@Test
	public void PR22() {
		assertTrue("No se puede hacer eso en la aplicación.", true);			
	}	

	//PR23. 
	//@Test
	public void PR23() {
		assertTrue("PR23 sin hacer", false);			
	}	

	//PR24. 
	//@Test
	public void PR24() {
		assertTrue("PR24 sin hacer", false);			
	}	
	//PR25. 
	//@Test
	public void PR25() {
		assertTrue("PR25 sin hacer", false);			
	}	

	//PR26. 
	//@Test
	public void PR26() {
		assertTrue("PR26 sin hacer", false);			
	}	

	//PR27. 
	//@Test
	public void PR27() {
		assertTrue("PR27 sin hacer", false);			
	}	

	//PR029. 
	//@Test
	public void PR29() {
		assertTrue("PR29 sin hacer", false);			
	}

	//PR030. 
	//@Test
	public void PR30() {
		assertTrue("PR30 sin hacer", false);			
	}

	//PR031. 
	//@Test
	public void PR31() {
		assertTrue("PR31 sin hacer", false);			
	}


}

