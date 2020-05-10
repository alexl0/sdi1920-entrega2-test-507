/* 
 * MIT License
 * 
 * Copyright (c) 2020 Alejandro León Pereira
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FriendsManagerTests {
    // En Windows (Debe ser la versiÃ³n 65.0.1 y desactivar las actualizacioens
    // automÃ¡ticas)):
    static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox6501\\firefox.exe";
    static String Geckdriver024 = "C:\\Users\\UO258774\\git\\SDI\\entrega2\\geckodriver024win64.exe";
    // En MACOSX (Debe ser la versiÃ³n 65.0.1 y desactivar las actualizacioens
    // automÃ¡ticas):
    // static String PathFirefox65 = "/Applications/Firefox
    // 2.app/Contents/MacOS/firefox-bin";
    // static String PathFirefox64 =
    // "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
    // static String Geckdriver024 =
    // "/Users/delacal/Documents/SDI1718/firefox/geckodriver024mac";
    // static String Geckdriver022 =
    // "/Users/delacal/Documents/SDI1718/firefox/geckodriver023mac";
    // ComÃºn a Windows y a MACOSX
    static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
    static String URL = "https://localhost:8081";

    public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
	System.setProperty("webdriver.firefox.bin", PathFirefox);
	System.setProperty("webdriver.gecko.driver", Geckdriver);
	WebDriver driver = new FirefoxDriver();
	return driver;
    }

    @Before
    public void setUp() {
	driver.navigate().to(URL);
    }

    @After
    public void tearDown() {
	driver.manage().deleteAllCookies();
    }

    @BeforeClass
    static public void begin() {
	// COnfiguramos las pruebas.
	// Fijamos el timeout en cada opción de carga de una vista.
	// Pongo tanto tiempo porque a veces el tiempo de carga
	// depende de la base de datos mongodb
	PO_View.setTimeout(30);

	// #####################################################################
	// ########################## ATENCIÓN #################################
	// #####################################################################
	// En una aplicación comercial no se haría esto bajo ninguna
	// circunstancia. Solo tiene la intención de facilitar la
	// ejecución de los test.
	driver.navigate().to(URL + "/borrarUsuarios");

	// Crear de nuevo los usuarios de prueba
	for (int i = 1; i < 13; i++) {
	    PO_NavView.clickOption(driver, "registrarse", "class", "btn btn-primary");
	    PO_RegisterView.fillForm(driver, "user" + i + "@email.com", "user" + i, "user" + i, "123456", "123456");
	}

    }

    @AfterClass
    static public void end() {
	// Cerramos el navegador al finalizar las pruebas
	driver.quit();
    }

    /**
     * #######################################
     * ###### Registrarse como usuario #######
     * #######################################
     */

    /**
     * Registro de Usuario con datos válidos
     */
    @Test
    public void PR01() {
	PO_NavView.clickOption(driver, "registrarse", "class", "btn btn-primary");
	PO_RegisterView.fillForm(driver, "prueba1@email.com", "Nombre1", "Apellido1 Apellido1", "123456", "123456");
	PO_View.checkElement(driver, "text", "Nuevo usuario registrado");
    }

    /**
     * Registro de Usuario con datos inválidos 
     * (email vacío, nombre vacío, apellidos vacíos).
     */
    @Test
    public void PR02() {
	PO_NavView.clickOption(driver, "registrarse", "class", "btn btn-primary");
	PO_RegisterView.fillForm(driver, "", "", "", "123456", "123456");
    }

    /**
     * Registro de Usuario con datos inválidos 
     * (repetición de contraseña inválida).
     */
    @Test
    public void PR03() {
	PO_NavView.clickOption(driver, "registrarse", "class", "btn btn-primary");
	PO_RegisterView.fillForm(driver, "prueba2@email.com", "Nombre1", "Apellido1 Apellido1", "1234567", "123456");
	PO_View.checkElement(driver, "text", "Las contraseñas no coinciden");
    }

    /**
     * Registro de Usuario con datos inválidos (email existente).
     */
    @Test
    public void PR04() {
	PO_NavView.clickOption(driver, "registrarse", "class", "btn btn-primary");
	PO_RegisterView.fillForm(driver, "prueba1@email.com", "Nombre1", "Apellido1 Apellido1", "123456", "123456");
	PO_View.checkElement(driver, "text", "Este email ya está registrado");
    }

    /**
     * #######################################
     * ###### Iniciar sesión #################
     * #######################################
     */

    /**
     * Inicio de sesión con datos válidos (usuario estándar).
     */
    @Test
    public void PR05() {
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuarios");
	PO_NavView.clickOption(driver, "desconectarse", "class", "btn btn-primary");
    }

    /**
     * Inicio de sesión con datos inválidos 
     * (usuario estándar, campo email y contraseña vacíos).
     */
    @Test
    public void PR06() {
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "", "");
	PO_View.checkElement(driver, "text", "Identificación de usuario");
    }

    /**
     * Iniciodesesión  con  datos inválidos
     * (usuario  estándar,  email  existente,  pero  contraseña incorrecta).
     */
    @Test
    public void PR07() {
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "prueba1@email.com", "1234567");
	PO_View.checkElement(driver, "text", "Email o password incorrecto");
    }

    /**
     * Inicio de sesión con datos inválidos 
     * (usuario estándar, email no existentey contraseña no vacía)
     */
    @Test
    public void PR08() {
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "prueba1noexistente@email.com", "1234567");
	PO_View.checkElement(driver, "text", "Email o password incorrecto");
    }


    /**
     * #######################################
     * ###### Fin de sesión ##################
     * #######################################
     */

    /**
     * Hacer click en la opción de salir de sesión y comprobar 
     * que se redirige a la página de inicio de sesión (Login).
     */
    @Test
    public void PR09() {
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuarios");
	PO_NavView.clickOption(driver, "desconectarse", "class", "btn btn-primary");
	PO_View.checkElement(driver, "text", "Se ha desconectado con éxito.");
	PO_View.checkElement(driver, "text", "Identificación de usuario");
    }

    /**
     * Comprobar que el botón cerrar sesión no está 
     * visible si el usuario no está autenticado.
     */
    @Test
    public void PR10() {
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Desconectarse", PO_View.getTimeout());
    }

    /**
     * #######################################
     * ###### Listar todos los usuarios ######
     * ###### de la aplicación ###############
     * #######################################
     */

    /**
     * Mostrar el listado de usuarios y comprobar que se 
     * muestran todos los que existen enel sistema.
     */
    @Test
    public void PR11() {
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
	PO_View.checkElement(driver, "text", "user1@email.com");
	PO_View.checkElement(driver, "text", "user2@email.com");
	PO_View.checkElement(driver, "text", "user3@email.com");
	PO_View.checkElement(driver, "text", "user4@email.com");
	PO_View.checkElement(driver, "text", "user5@email.com");
	PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]").get(1).click();
	PO_View.checkElement(driver, "text", "user6@email.com");
	PO_View.checkElement(driver, "text", "user7@email.com");
	PO_View.checkElement(driver, "text", "user8@email.com");
	PO_View.checkElement(driver, "text", "user9@email.com");
	PO_View.checkElement(driver, "text", "user10@email.com");
	PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]").get(2).click();
	PO_View.checkElement(driver, "text", "user11@email.com");
	PO_View.checkElement(driver, "text", "user12@email.com");
    }

    /**
     * ###########################################
     * ###### Buscar entre todos los usuarios ####
     * ###### de la aplicación ###################
     * ###########################################
     */

    /**
     * Hacer  una  búsqueda  con  el  campo  vacío 
     * y  comprobar  que  se  muestra  la  página
     * que corresponde con el listado usuarios existentes
     * en el sistema.
     */
    @Test
    public void PR12() {
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");
	// Escribir en la barra de búsqueda
	PO_View.checkElement(driver, "free", "//*[@id=\"searchText\"]").get(0).sendKeys("");
	// Ahora hacemos click en buscar
	PO_View.checkElement(driver, "free", "//*[@id=\"searchButton\"]").get(0).click();
	PO_View.checkElement(driver, "text", "user2@email.com");
	PO_View.checkElement(driver, "text", "user3@email.com");
	PO_View.checkElement(driver, "text", "user4@email.com");
	PO_View.checkElement(driver, "text", "user5@email.com");
	PO_View.checkElement(driver, "text", "user6@email.com");
    }

    /**
     * Hacer  una  búsqueda  escribiendo  en
     * el  campo  un  texto  que  no  exista
     * y  comprobar  que  se muestra la página
     * que corresponde, con la lista de usuarios vacía
     */
    @Test
    public void PR13() {
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");
	// Escribir en la barra de búsqueda
	PO_View.checkElement(driver, "free", "//*[@id=\"searchText\"]").get(0).sendKeys("userNoExiste");
	// Ahora hacemos click en buscar
	PO_View.checkElement(driver, "free", "//*[@id=\"searchButton\"]").get(0).click();
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user1@email.com", PO_View.getTimeout());
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user2@email.com", PO_View.getTimeout());
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user3@email.com", PO_View.getTimeout());
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user4@email.com", PO_View.getTimeout());
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user5@email.com", PO_View.getTimeout());
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user5@email.com", PO_View.getTimeout());
    }

    /**
     * Hacer  una  búsquedacon  un texto  específico y
     * comprobar  que  se  muestra  la  página  que corresponde,
     * con la lista de usuarios en los que el texto especificado
     * sea parte de su nombre, apellidos o de su email.
     */
    @Test
    public void PR14() {
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
	// Escribir en la barra de búsqueda
	PO_View.checkElement(driver, "free", "//*[@id=\"searchText\"]").get(0).sendKeys("user1");
	// Ahora hacemos click en buscar
	PO_View.checkElement(driver, "free", "//*[@id=\"searchButton\"]").get(0).click();
	PO_View.checkElement(driver, "text", "user1@email.com");
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user2@email.com", PO_View.getTimeout());
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user3@email.com", PO_View.getTimeout());
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user4@email.com", PO_View.getTimeout());
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user5@email.com", PO_View.getTimeout());
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user5@email.com", PO_View.getTimeout());
    }

    /**
     * ###########################################
     * ###### Enviar una invitación de ###########
     * ###### amistad a un usuario ###############
     * ###########################################
     */

    /**
     * Desde el listado de usuarios de la aplicación,
     * enviar una invitación de amistad a un usuario.
     * Comprobar que la solicitud de amistad aparece
     * en el listado de invitaciones (punto siguiente).
     */
    @Test
    public void PR15() {
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");

	driver.navigate().to("https://localhost:8081/usuario/invitar/user1@email.com");
	PO_View.checkElement(driver, "text", "Invitación enviada correctamente");
	PO_NavView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");
	// driver.navigate().to("https://localhost:8081/invitaciones");
	PO_NavView.clickOption(driver, "invitaciones", "class", "btn btn-primary");
	PO_View.checkElement(driver, "text", "prueba1@email.com");
    }

    /**
     * Desde el listado de usuarios de la aplicación,
     * enviar una invitación de amistad a un usuario al
     * que ya le habíamos enviado la invitación previamente.
     * No debería dejarnos enviar la invitación,
     * se podría ocultar el botón de enviar invitación
     * onotificar que ya había sido enviada previamente
     */
    @Test
    public void PR16() {
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");

	// Se hace por url, no se puede calcar el botón porque está bloqueado
	driver.navigate().to("https://localhost:8081/usuario/invitar/user1@email.com");
	PO_View.checkElement(driver, "text", "Hay invitaciones pendientes entre usted y esa persona");
    }

    /**
     * ###########################################
     * ###### Listar las invitaciones ############
     * ######  de amistad recibidas ##############
     * ###########################################
     */

    /**
     * Mostrar  el  listado  de  invitaciones  de  amistad
     * recibidas.  Comprobar con  un  listado  que contenga
     * varias invitacionesrecibidas.
     */
    @Test
    public void PR17() {

	// Enviar más peticiones a user1 desde otros usuarios
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user2@email.com", "123456");
	driver.navigate().to("https://localhost:8081/usuario/invitar/user1@email.com");
	PO_View.checkElement(driver, "text", "Invitación enviada correctamente");
	PO_NavView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user3@email.com", "123456");
	driver.navigate().to("https://localhost:8081/usuario/invitar/user1@email.com");
	PO_View.checkElement(driver, "text", "Invitación enviada correctamente");
	PO_NavView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user4@email.com", "123456");
	driver.navigate().to("https://localhost:8081/usuario/invitar/user1@email.com");
	PO_View.checkElement(driver, "text", "Invitación enviada correctamente");
	PO_NavView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user5@email.com", "123456");
	driver.navigate().to("https://localhost:8081/usuario/invitar/user1@email.com");
	PO_View.checkElement(driver, "text", "Invitación enviada correctamente");
	PO_NavView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

	// Comprobar que las ha recibido user1
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");
	// driver.navigate().to("https://localhost:8081/invitaciones");
	PO_NavView.clickOption(driver, "invitaciones", "class", "invitaciones");
	PO_View.checkElement(driver, "text", "prueba1@email.com");
	PO_View.checkElement(driver, "text", "user2@email.com");
	PO_View.checkElement(driver, "text", "user3@email.com");
	PO_View.checkElement(driver, "text", "user4@email.com");
	PO_View.checkElement(driver, "text", "user5@email.com");
    }

    /**
     * ###################################################
     * ###### Aceptar una invitación recibida ############
     * ###################################################
     */

    /**
     * Sobre  el  listado  de  invitaciones  recibidas.
     * Hacer  click  en  el  botón/enlace  de  una  de  ellas
     * y comprobar que dicha solicitud desaparece del listado
     * de invitaciones.
     */
    @Test
    public void PR18() {
	// Se loguea user1
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");

	// Acepta la petición de prueba1
	PO_NavView.clickOption(driver, "invitaciones", "class", "invitaciones");
	driver.navigate().to("https://localhost:8081/usuario/aceptar/prueba1@email.com");
	PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");
    }

    /**
     * Envia invitación de amistad a un amigo
     */
    @Test
    public void PR18_1() {
	// Se loguea user1
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");

	// Le intenta volver a invitar a prueba1
	driver.navigate().to("https://localhost:8081/usuario/invitar/prueba1@email.com");
	PO_View.checkElement(driver, "text", "Ya es amigo de esa persona");
    }

    /**
     * Intentar aceptar a un usuario que no te ha invitado
     */
    @Test
    public void PR18_2() {
	// Se loguea user1
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");

	driver.navigate().to("https://localhost:8081/usuario/aceptar/user10@email.com");
	PO_View.checkElement(driver, "text", "No tiene una invitación de esa persona");

    }

    /**
     * Intentar agregarse a si mismo
     */
    @Test
    public void PR18_3() {
	// Se loguea user1
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
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
	// Se loguea user1
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");

	driver.navigate().to("https://localhost:8081/usuario/aceptar/userNoExistep14909472@email.com");
	PO_View.checkElement(driver, "text", "No existe el usuario que se desea agregar");

    }

    /**
     * ###################################################
     * ###### Listar los usuarios amigos #################
     * ###################################################
     */

    /**
     * Mostrar el listado de amigos de un usuario. Comprobar 
     * que el listado contiene los amigos que deben ser.
     */
    @Test
    public void PR19() {
	// Se loguea user1
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");

	// Acepta al resto de amigos que le han invitado
	PO_NavView.clickOption(driver, "invitaciones", "class", "invitaciones");
	driver.navigate().to("https://localhost:8081/usuario/aceptar/user2@email.com");
	PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");
	driver.navigate().to("https://localhost:8081/usuario/aceptar/user3@email.com");
	PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");
	driver.navigate().to("https://localhost:8081/usuario/aceptar/user4@email.com");
	PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");
	driver.navigate().to("https://localhost:8081/usuario/aceptar/user5@email.com");
	PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");

	// Va al listado de amigos
	PO_NavView.clickOption(driver, "amigos", "class", "amigos");

	// Comprobar que están los amigos
	PO_View.checkElement(driver, "text", "prueba1@email.com");
	PO_View.checkElement(driver, "text", "user2@email.com");
	PO_View.checkElement(driver, "text", "user3@email.com");
	PO_View.checkElement(driver, "text", "user4@email.com");
	PO_View.checkElement(driver, "text", "user5@email.com");

    }

    /**
     * ###################################################
     * ###### Seguridad ##################################
     * ###################################################
     */

    /**
     * Intentar acceder sin estar autenticado a la opción de
     * listado de usuarios. Se deberá volver al formulario de
     * login
     */
    @Test
    public void PR20() {
	// No se puede calcar el botón porque no sale en pantalla, se intenta por url
	driver.navigate().to("https://localhost:8081/amigos");
	PO_View.checkElement(driver, "text", "Identificación de usuario");
    }

    /**
     * Intentar acceder sin estar autenticado 
     * a la opción de listado de invitaciones de amistad
     * recibida de un usuario estándar. Se deberá volver
     * al formulario de login
     */
    @Test
    public void PR21() {
	// No se puede calcar el botón porque no sale en pantalla, se intenta por url
	driver.navigate().to("https://localhost:8081/invitaciones");
	PO_View.checkElement(driver, "text", "Identificación de usuario");
    }

    /**
     * PR22. No se puede hacer eso en la aplicación, ya que el usuario en sesión se
     * detecta automáticamente, no se le pasa como parámetro, por tanto no hay
     * manera fácil de falsificarlo.
     */
    /**
     * Intentar  acceder estando  autenticado  como
     * usuario  standard  a la  lista  de  amigos  de  
     * otro usuario. Se deberá mostrar un mensaje 
     * de acción indebida.
     */
    @Test
    public void PR22() {
	assertTrue("No se puede hacer eso en la aplicación.", true);
    }

    /**
     * ###################################################
     * ###### Cliente ####################################
     * ###################################################
     */

    /**
     * ###################################################
     * ###### Autenticación del usuario ##################
     * ###################################################
     */

    /**
     * Inicio de sesión con datos válidos.
     */
    @Test
    public void PR23() {
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuario en sesión: user1@email.com");
    }

    /**
     * Inicio de sesión con datos inválidos 
     * (usuario no existente en la aplicación).
     */
    @Test
    public void PR24() {
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "userNoExisteEnLaAplicacion8r1u91u982rh@email.com", "1234567890");
	PO_View.checkElement(driver, "text", "Usuario o contraseña incorrectos");
    }

    // PR24. Contraseña vacía
    @Test
    public void PR24_1() {
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "userNoExisteEnLaAplicacion8r1u91u982rh@email.com", "");
	PO_View.checkElement(driver, "text", "Contraseña requerida");
    }

    // PR24. Email vacío
    @Test
    public void PR24_2() {
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "", "12345678");
	PO_View.checkElement(driver, "text", "Email requerido");
    }

    /**
     * ###################################################
     * ###### Autenticación del usuario ##################
     * ###################################################
     */

    /**
     * Acceder a la lista de amigos de un usuario,
     * que al menos tenga tres amigos.
     */
    @Test
    public void PR25() {
	// Loguearse
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuario en sesión: user1@email.com");

	// Comprobar que salen sus amigos
	PO_View.checkElement(driver, "text", "prueba1@email.com");
	PO_View.checkElement(driver, "text", "user2@email.com");
	PO_View.checkElement(driver, "text", "user3@email.com");
	PO_View.checkElement(driver, "text", "user4@email.com");
	PO_View.checkElement(driver, "text", "user5@email.com");
    }

    /**
     * Acceder a la lista de amigos de un usuario, 
     * y realizar un filtrado para encontrar a un amigo concreto, 
     * el nombre a buscar debe coincidir con el de un amigo.
     */
    @Test
    public void PR26() {
	// Loguearse
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuario en sesión: user1@email.com");

	// Comprobar que se ha cargado la lista de usuarios, comprobando si
	// hay al menos uno
	PO_View.checkElement(driver, "text", "prueba1@email.com");

	// Escribir en la barra de búsqueda el nombre de un amigo (no es case sensitive)
	PO_View.checkElement(driver, "free", "//*[@id=\"filtro-nombre\"]").get(0).sendKeys("nombre1");

	// Comprobar que se lista en la tabla el amigo con ese nombre
	PO_View.checkElement(driver, "text", "prueba1@email.com");

	// Y el resto de amigos no
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user2@email.com", PO_View.getTimeout());
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user3@email.com", PO_View.getTimeout());
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user4@email.com", PO_View.getTimeout());
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "user5@email.com", PO_View.getTimeout());
    }

    /**
     * ###################################################
     * ###### Autenticación del usuario ##################
     * ###################################################
     */

    /**
     * Acceder  a  la  lista  de  mensajes  de  un  amigo“chat”,  
     * la  lista  debe  contener  almenos  tres mensajes
     */
    @Test
    public void PR27() {
	// Loguearse
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuario en sesión: user1@email.com");

	// Clickar en el amigo correspondiente
	PO_View.checkElement(driver, "text", "prueba1@email.com");
	List<WebElement> elementsList = driver.findElements(By.xpath("//*[contains(text(),'prueba1@email.com')]"));
	elementsList.get(0).click();

	// Escribir 4 mensajes para que haya mensajes que mostrar
	escribirMensajeYEnviar(1);
	escribirMensajeYEnviar(2);
	escribirMensajeYEnviar(3);
	escribirMensajeYEnviar(4);

	// Esperar a que salga en pantalla
	PO_View.checkElement(driver, "text", "Mensaje de prueba 1");
	PO_View.checkElement(driver, "text", "Mensaje de prueba 2");
	PO_View.checkElement(driver, "text", "Mensaje de prueba 3");
	PO_View.checkElement(driver, "text", "Mensaje de prueba 4");
    }

    /**
     * Método auxiliar para escribir mensajes de prueba y no repetir código más de
     * lo necesario
     * 
     * @param i numero del mensaje
     */
    private void escribirMensajeYEnviar(int i) {
	// Escribir el mensaje
	PO_View.checkElement(driver, "free", "//*[@id=\"escribir-mensaje\"]").get(0).sendKeys("Mensaje de prueba " + i);

	// Enviar el mensaje
	PO_View.checkElement(driver, "free", "//*[@id=\"botonEnviarMensaje\"]").get(0).click();
    }

    /**
     * ###################################################
     * ###### Crear mensaje ##############################
     * ###################################################
     */

    /**
     * Acceder a la lista de mensajes de un amigo“chat” 
     * y crear un nuevo mensaje, validar que el mensaje
     * aparece en la lista de mensajes.
     */
    @Test
    public void PR28() {
	// Loguearse
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuario en sesión: user1@email.com");

	// Clickar en el amigo correspondiente
	PO_View.checkElement(driver, "text", "prueba1@email.com");
	List<WebElement> elementsList = driver.findElements(By.xpath("//*[contains(text(),'prueba1@email.com')]"));
	elementsList.get(0).click();

	// Escribir el mensaje
	PO_View.checkElement(driver, "free", "//*[@id=\"escribir-mensaje\"]").get(0).sendKeys("Hola! Soy user1!");

	// Enviar el mensaje
	PO_View.checkElement(driver, "free", "//*[@id=\"botonEnviarMensaje\"]").get(0).click();

	// Esperar a que salga en pantalla
	PO_View.checkElement(driver, "text", "Hola! Soy user1!");

    }

    /**
     * PR028. Probar que no se pueden enviar mensajes de más de 400 caracteres No se
     * puede probar enviar el mensaje vacío porque el botón está bloqueado a no ser
     * que se introduzca texto de más de 1 caracter y menos de 400.
     */
    @Test
    public void PR28_1() {
	// Loguearse
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuario en sesión: user1@email.com");

	// Clickar en el amigo correspondiente
	PO_View.checkElement(driver, "text", "prueba1@email.com");
	List<WebElement> elementsList = driver.findElements(By.xpath("//*[contains(text(),'prueba1@email.com')]"));
	elementsList.get(0).click();

	// Escribir el mensaje de 401 caracteres (intentarlo,
	// ya que se borra automaticamente)
	PO_View.checkElement(driver, "free", "//*[@id=\"escribir-mensaje\"]").get(0)
	.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaa");

	// Esperar a que salga en pantalla el error
	PO_View.checkElement(driver, "text", "No se pueden enviar mensajes de más de 400 caracteres");

	// Escribir el mensaje
	PO_View.checkElement(driver, "free", "//*[@id=\"escribir-mensaje\"]").get(0)
	.sendKeys("Mensaje normal, con un numero de caracteres normal");

	// Comprobar que se ha quitado el error
	SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "No se pueden enviar mensajes de más de 400 caracteres",
		PO_View.getTimeout());
    }

    /**
     * ###################################################
     * ###### Marcar mensajes como leídos ################
     * ###### de forma automática ########################
     * ###################################################
     */

    /**
     * Identificarse en la aplicación y enviar un mensaje a un amigo, 
     * validar que el mensaje enviado aparece  en  el  chat.  
     * Identificarse  después con  el  usuario  que  recibido  
     * el  mensaje  y  validar  que  tiene  un mensaje sin leer, 
     * entrar en el chat y comprobar que el mensaje pasa a tener 
     * el estado leído.
     */
    @Test
    public void PR29() {
	/**
	 * En las pruebas anteriores se han enviado mensajes a prueba1@email.com Ahora
	 * se loguea prueba1@email.com, y comprueba que los mensajes se marcan como
	 * leídos.
	 */
	// Loguearse
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuario en sesión: prueba1@email.com");

	// Clickar en el amigo correspondiente
	PO_View.checkElement(driver, "text", "user1@email.com");
	List<WebElement> elementsList = driver.findElements(By.xpath("//*[contains(text(),'user1@email.com')]"));
	elementsList.get(0).click();

	// Esperar a que salga en pantalla un mensaje con el símbolo leido ✔
	PO_View.checkElement(driver, "text", "Mensaje de prueba 1 ✔");
	PO_View.checkElement(driver, "text", "Mensaje de prueba 2 ✔");
	PO_View.checkElement(driver, "text", "Mensaje de prueba 3 ✔");
	PO_View.checkElement(driver, "text", "Mensaje de prueba 4 ✔");
	PO_View.checkElement(driver, "text", "Hola! Soy user1! ✔");
    }


    /**
     * ###################################################
     * ###### Mostrar el número de mensajes sin leer #####
     * ###################################################
     */

    /**
     * Identificarse en la aplicación y enviar tres 
     * mensajes a un amigo, validar que los mensajes 
     * enviados aparecen en el chat. Identificarse 
     * después con el usuario que recibido el mensaje 
     * y validar que el número de mensajes sin 
     * leer aparece en la propia lista de amigos.
     */
    @Test
    public void PR30() {
	// Loguearse con user1@email.com
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "user1@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuario en sesión: user1@email.com");

	// Clickar en el amigo correspondiente
	PO_View.checkElement(driver, "text", "prueba1@email.com");
	List<WebElement> elementsList = driver.findElements(By.xpath("//*[contains(text(),'prueba1@email.com')]"));
	elementsList.get(0).click();

	// Escribir 4 mensajes para que haya mensajes que mostrar
	escribirMensajeYEnviar(5);
	escribirMensajeYEnviar(6);
	escribirMensajeYEnviar(7);

	// Validar que los mensajes se han enviado correctamente
	PO_View.checkElement(driver, "text", "Mensaje de prueba 5");
	PO_View.checkElement(driver, "text", "Mensaje de prueba 6");
	PO_View.checkElement(driver, "text", "Mensaje de prueba 7");

	// Loguearse con prueba1@email.com
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "prueba1@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuario en sesión: prueba1@email.com");

	/**
	 * PR030 Ver que tiene 3 mensajes nuevos de user1 (el único amigo que tiene)
	 */
	PO_View.checkElement(driver, "text", "3 mensajes nuevos");

    }

    /**
     * ###################################################
     * ###### Ordenar la lista de amigo ##################
     * ###### por último mensaje #########################
     * ###################################################
     */

    /**
     * Identificarse con un usuario A que al menos tenga 3 
     * amigos, ir al chat del ultimo amigo de la lista 
     * y enviarle un mensaje, volver a la lista de amigos 
     * y comprobar que el usuario al que se le ha enviado 
     * el  mensaje  esta  en primera  posición.  
     * Identificarse  con  el  usuario  B y  enviarle  
     * un mensaje  al  usuario  A. Volver a identificarse 
     * con el usuario A y ver que el usuario que acaba de 
     * mandarle el mensaje es el primero en su lista de amigos.
     */

    /**
     * El teset pr031 se ha dividido en dos porque tiene dos claras partes. PR031
     * parte 1 -preprar para que user2 tenga 3 amigos -Loguearse con user2@email.com
     * (tiene 3 amigos) -Enviarle un mensaje al amigo que está más abajo -Volver a
     * la vista de amigos y comprobar que ahora está arriba del todo.
     */
    @Test
    public void PR31_parte1() {

	prepararPR31_parte1();

	// Loguearse
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "user2@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuario en sesión: user2@email.com");

	// Averiguar cual es el amigo que está más abajo
	List<WebElement> elementsListAbajo = PO_View.checkElement(driver, "free",
		"//tbody/tr[4]/td[1][contains(text(),'@')]");
	String emailMasAbajo = elementsListAbajo.get(0).getText();

	// Clickar en el amigo de más abajo
	PO_View.checkElement(driver, "text", emailMasAbajo);
	List<WebElement> elementsList = driver.findElements(By.xpath("//*[contains(text(),'" + emailMasAbajo + "')]"));
	elementsList.get(0).click();

	// Escribir el mensaje
	String mensaje = "Hola " + emailMasAbajo + "! Soy user2! Hacía tiempo que no hablabamos.";
	PO_View.checkElement(driver, "free", "//*[@id=\"escribir-mensaje\"]").get(0).sendKeys(mensaje);

	// Enviar el mensaje
	PO_View.checkElement(driver, "free", "//*[@id=\"botonEnviarMensaje\"]").get(0).click();

	// Esperar a que salga en pantalla
	PO_View.checkElement(driver, "text", mensaje);

	// Volver a amigos
	List<WebElement> elementsList2 = driver.findElements(By.xpath("//*[contains(text(),'Amigos')]"));
	elementsList2.get(0).click();

	// Esperar a que cargue la página
	PO_View.checkElement(driver, "text", emailMasAbajo);

	// Averiguar cual es el amigo que está más arriba
	// Comprobamos que ahora el que está arriba, era el que estaba antes abajo.
	PO_View.checkElement(driver, "free", "//tbody/tr[1]/td[1][contains(text(),'" + emailMasAbajo + "')]");
    }

    /**
     * Crea los usuarios necesarios para ejecutar PR31_parte1() Realmente no se
     * prueba nada en éste método. Solo se prepara el entorno de pruebas
     */
    private void prepararPR31_parte1() {

	driver.navigate().to("https://localhost:8081");
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user3@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuarios");
	driver.navigate().to("https://localhost:8081/usuario/invitar/user2@email.com");
	PO_NavView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

	driver.navigate().to("https://localhost:8081");
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user4@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuarios");
	driver.navigate().to("https://localhost:8081/usuario/invitar/user2@email.com");
	PO_NavView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

	driver.navigate().to("https://localhost:8081");
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user5@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuarios");
	driver.navigate().to("https://localhost:8081/usuario/invitar/user2@email.com");
	PO_NavView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

	// Se loguea user2
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user2@email.com", "123456");

	// Aceptar las invitaciones
	PO_NavView.clickOption(driver, "invitaciones", "class", "invitaciones");
	driver.navigate().to("https://localhost:8081/usuario/aceptar/user3@email.com");
	PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");
	driver.navigate().to("https://localhost:8081/usuario/aceptar/user4@email.com");
	PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");
	driver.navigate().to("https://localhost:8081/usuario/aceptar/user5@email.com");
	PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");

    }

    /**
     * PR031 parte 2 -Preparar para que user 6 tenga 3 amigos -Loguearse con
     * user6@email.com (tiene varios amigos) -Ver cual es el email del amigo que
     * está más abajo -Loguearse con el email del amigo que estaba abajo del todo
     * -Enviarle un mensaje a user6@email.com -Loguearse con user1 y ver que el
     * amigo que antes aparecía abajo ahora aparece arriba del todo
     */
    @Test
    public void PR31_parte2() {

	prepararPR31_parte2();

	// Loguearse con user1@email.com
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "user6@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuario en sesión: user6@email.com");

	// Averiguar cual es el amigo que está más abajo
	List<WebElement> elementsListAbajo = PO_View.checkElement(driver, "free",
		"//tbody/tr[3]/td[1][contains(text(),'@')]");
	String emailMasAbajo = elementsListAbajo.get(0).getText();

	// Loguearse con el email del que estaba más abajo
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, emailMasAbajo, "123456");
	PO_View.checkElement(driver, "text", "Usuario en sesión: " + emailMasAbajo);

	// Clickar en user6@email.com
	PO_View.checkElement(driver, "text", "user6@email.com");
	List<WebElement> elementsList = driver
		.findElements(By.xpath("//*[contains(text(),'" + "user6@email.com" + "')]"));
	elementsList.get(0).click();

	// Escribir el mensaje
	String mensaje = "Hola " + "user6@email.com" + "! Soy " + emailMasAbajo + "! Hacía tiempo que no hablabamos.";
	PO_View.checkElement(driver, "free", "//*[@id=\"escribir-mensaje\"]").get(0).sendKeys(mensaje);

	// Enviar el mensaje
	PO_View.checkElement(driver, "free", "//*[@id=\"botonEnviarMensaje\"]").get(0).click();

	// Esperar a que salga en pantalla
	PO_View.checkElement(driver, "text", mensaje);

	// Loguearse con user1@email.com de nuevo
	driver.navigate().to(URL + "/cliente.html");
	PO_View.checkElement(driver, "text", "Email");
	PO_LoginView.fillForm(driver, "user6@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuario en sesión: user6@email.com");

	// Esperar a que cargue la página
	PO_View.checkElement(driver, "text", emailMasAbajo);

	// Averiguar cual es el amigo que está más arriba
	// Comprobamos que ahora el que está arriba, era el que estaba antes abajo.
	PO_View.checkElement(driver, "free", "//tbody/tr[1]/td[1][contains(text(),'" + emailMasAbajo + "')]");
    }

    /**
     * Crea los usuarios necesarios para ejecutar PR31_parte2() Realmente no se
     * prueba nada en éste método. Solo se prepara el entorno de pruebas
     */
    private void prepararPR31_parte2() {

	driver.navigate().to("https://localhost:8081");
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user7@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuarios");
	driver.navigate().to("https://localhost:8081/usuario/invitar/user6@email.com");
	PO_NavView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

	driver.navigate().to("https://localhost:8081");
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user8@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuarios");
	driver.navigate().to("https://localhost:8081/usuario/invitar/user6@email.com");
	PO_NavView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

	driver.navigate().to("https://localhost:8081");
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user9@email.com", "123456");
	PO_View.checkElement(driver, "text", "Usuarios");
	driver.navigate().to("https://localhost:8081/usuario/invitar/user6@email.com");
	PO_NavView.clickOption(driver, "desconectarse", "class", "btn btn-primary");

	// Se loguea user6
	PO_NavView.clickOption(driver, "identificarse", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "user6@email.com", "123456");

	// Aceptar las invitaciones
	PO_NavView.clickOption(driver, "invitaciones", "class", "invitaciones");
	driver.navigate().to("https://localhost:8081/usuario/aceptar/user7@email.com");
	PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");
	driver.navigate().to("https://localhost:8081/usuario/aceptar/user8@email.com");
	PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");
	driver.navigate().to("https://localhost:8081/usuario/aceptar/user9@email.com");
	PO_View.checkElement(driver, "text", "Invitación aceptada correctamente");

    }

}
