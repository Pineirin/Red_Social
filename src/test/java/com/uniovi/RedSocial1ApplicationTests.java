package com.uniovi;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.pageobjects.PO_LoginView;
import com.uniovi.pageobjects.PO_NavView;
import com.uniovi.pageobjects.PO_Properties;
import com.uniovi.pageobjects.PO_PublicationView;
import com.uniovi.pageobjects.PO_RegisterView;
import com.uniovi.pageobjects.PO_SearchTextView;
import com.uniovi.pageobjects.PO_View;
import com.uniovi.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedSocial1ApplicationTests {

	// En Windows (Debe ser la versión 46.0 y desactivar las actualizacioens
	// automáticas)):
	// static String PathFirefox =
	// "C:\\Users\\Packard\\Desktop\\SDI\\PL-SDI-Material5\\Firefox46.win\\FirefoxPortable.exe";
	static String PathFirefox = "C:\\Firefox46Portable\\FirefoxPortable.exe";
	// En MACOSX (Debe ser la versión 46.0 y desactivar las actualizaciones
	// automáticas):
	// static String PathFirefox =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox) {
		// Firefox (Versión 46.0) sin geckodriver para Selenium 2.x.
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// PR01_1. Registro de Usuario con datos válidos
	@Test
	public void PR01() {
		// Vamos al formulario de registro
		PO_NavView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "77777778A@uniovi.es", "Josefo", "77777", "77777");
		// Comprobamos que entramos en la sección privada
		PO_RegisterView.checkKey(driver, "users.show.text",
				PO_Properties.getSPANISH());
		//PO_View.checkElement(driver, "text", "Los usuarios de la aplicación son los siguientes");
	}

	// PR01_2.Registro de Usuario con datos inválidos (repetición de contraseña
	// invalida).
	@Test
	public void PR02() {

		// Vamos al formulario de registro
		PO_NavView.clickOption(driver, "signup", "class", "btn btn-primary");

		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990B@uniovi.es", "Josefo", "7777", "7778");

		// COmprobamos el error de password no coincidente .
		 PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence",
		 PO_Properties.getSPANISH() );
		//PO_RegisterView.checkElement(driver, "text", "Las contraseñas no coinciden");
	}

	// PR02_1 Inicio de sesión con datos válidos
	@Test
	public void PR03() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "77777778A@uniovi.es", "77777");
		// COmprobamos que entramos en la pagina privada de Alumno
		//PO_View.checkElement(driver, "text", "Los usuarios de la aplicación son los siguientes");
		PO_RegisterView.checkKey(driver, "users.show.text",
				PO_Properties.getSPANISH());
	}

	// PR02_2 Inicio de sesión con datos inválidos (usuario no existente en la aplicación).
	@Test
	public void PR04() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "ZZZZZZZZA@uniovi.es", "123456");
		// COmprobamos que no entramos en la pagina privada del Usuario
		//PO_View.checkElement(driver, "text", "Usuario o contraseña inválidos");
		PO_RegisterView.checkKey(driver, "Error.login",
				PO_Properties.getSPANISH());
	}

	// PR03_1 Acceso al listado de usuarios desde un usuario en sesión.
	@Test
	public void PR05() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");
		// Comprobamos que estamos en la vista lista usuarios.
		//PO_View.checkElement(driver, "text", "Los usuarios de la aplicación son los siguientes:");
		PO_RegisterView.checkKey(driver, "users.show.text",
				PO_Properties.getSPANISH());
	}

	// PR03_2 Intento de acceso con URL desde un usuario no identificado al listado de
	// usuarios desde un usuario en sesión. Debe producirse un acceso no permitido a
	// vistas privadas.
	@Test
	public void PR06() {
		// Tratamos de acceder a la vista lista usuarios.
		driver.navigate().to(URL + "/user/list");
		// Comprobamos que nos redirige al login.
		//PO_View.checkElement(driver, "text", "Login");
		PO_RegisterView.checkKey(driver, "login.message",
				PO_Properties.getSPANISH());

	}

	// PR04_1 Realizar una búsqueda valida en el listado de usuarios desde un usuario en
	// sesión.
	@Test
	public void PR07() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");
		// Rellenamos el formulario de busqueda
		PO_SearchTextView.fillForm(driver, "ran");
		// Comprobamos que aparece el usuario filtrado
		PO_View.checkElement(driver, "text", "francisco12@live.com");
	}

	// PR04_2 Intento de acceso con URL a la búsqueda de usuarios desde un usuario no
	// identificado. Debe producirse un acceso no permitido a vistas privadas.
	@Test
	public void PR08() {
		// Tratamos de acceder a la vista lista usuarios.
		driver.navigate().to(URL + "/user/list");
		// Comprobamos que nos redirige al login.
		PO_View.checkElement(driver, "text", "Login");
	}

	// PR05_1 Enviar una invitación de amistad a un usuario de forma valida.
	@Test
	public void PR09() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");
		// Enviamos petición de amistad
		driver.findElement(By.id("sendPetitionButton2")).click();
	}

	// PR05_2 Enviar una invitación de amistad a un usuario al que ya le habíamos invitado
	// la invitación previamente. No debería dejarnos enviar la invitación, se
	// podría ocultar el botón de enviar invitación o notificar que ya había sido
	// enviada previamente.
	@Test
	public void PR10() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");
		// Enviamos petición de amistad
		driver.findElement(By.id("cancelPetitionButton2")).click();
		// Comprobamos que ahora la finalidad del boton es cancelar petición.
		//driver.findElement(By.id("sendPetitionButton2")).getText();
		//PO_View.checkElement(driver, "text", "Cancelar petición");
		PO_RegisterView.checkKey(driver, "petition.cancel",
				PO_Properties.getSPANISH());
	}

	// PR06_1 Listar las invitaciones recibidas por un usuario, realizar la comprobación
	// con una lista que al menos tenga una invitación recibida.
	@Test
	public void PR11() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");
		// Enviamos petición de amistad
		//PO_NavView.clickOption(driver, "user/petitions", "class", "btn btn-primary");
		driver.findElement(By.id("sendPetitionButton2")).click();
		// Cerramos sesión
		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");

		// Juan@hotmail.com se logea
		PO_LoginView.fillForm(driver, "Juan@hotmail.com", "123456");

		// Juan@hotmail.com mira sus peticiones de amistad
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();
		// Sacamos la pestaña para ver las peticiones
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/petitions')]");
		// Pinchamos en la pestaña para ver las peticiones
		elementos.get(0).click();
		//PO_View.checkElement(driver, "text", "Aceptar petición");
		PO_RegisterView.checkKey(driver, "petition.accept",
				PO_Properties.getSPANISH());

	}

	// PR07_1 Aceptar una invitación recibida
	@Test
	public void PR12() {

		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");

		// Juan@hotmail.com se logea
		PO_LoginView.fillForm(driver, "Juan@hotmail.com", "123456");

		// Juan@hotmail.com mira sus peticiones de amistad
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();
		// Sacamos la pestaña para ver las peticiones
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/petitions')]");
		// Pinchamos en la pestaña para ver las peticiones
		elementos.get(0).click();
		//PO_View.checkElement(driver, "text", "Aceptar petición");
		PO_RegisterView.checkKey(driver, "petition.accept",
				PO_Properties.getSPANISH());

		// Damos a aceptar la petición
		driver.findElement(By.id("acceptPetitionButton1")).click();

		// Miramos que ha desparecido la invitación
		//SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Aceptar petición", PO_View.getTimeout());
		PO_RegisterView.checkNoKey(driver, "petition.accept",
				PO_Properties.getSPANISH());
	}

	// PR08_1 Listar los amigos de un usuario, realizar la comprobación con una lista que
	// al menos tenga un amigo
	@Test
	public void PR13() {
		
		//Iniciamos sesión, este ya tiene una amigo Juan@hotmail.com
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");
		
		// adripc2live.com entra en gestión de usuarios
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();
		// Sacamos la pestaña para ver los amigos
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/friends')]");
		// Pinchamos en la pestaña para ver los amigos
		elementos.get(0).click();
		
		//PO_View.checkElement(driver, "text", "Usted tiene los siguientes amigos");
		PO_RegisterView.checkKey(driver, "friends.text",
				PO_Properties.getSPANISH());
		PO_View.checkElement(driver, "text", "Juan@hotmail.com");
		
	}
	
		// PR09_1 Crear una publicación con datos válidos
		@Test
		public void PR14() {
			
			//Iniciamos sesión, este ya tiene una amigo Juan@hotmail.com
			PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "adripc@live.com", "123456");
			
			// adripc2live.com entra en Publicaciones
			List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'publications-menu')]/a");
			elementos.get(0).click();
			// Sacamos la pestaña para crear una publicación
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/create')]");
			// Pinchamos en la pestaña para crear una punlicación
			elementos.get(0).click();
			
			//Relleno la publicación
			PO_PublicationView.fillForm(driver, "Hola", "Que tal");
			
			//Aparece la lista de publicciones
			PO_View.checkElement(driver, "text", "adripc@live.com");
			PO_View.checkElement(driver, "text", "Hola");
			PO_View.checkElement(driver, "text", "Que tal");
			
	}
	
	// PR10_1 Acceso al listado de publicaciones desde un usuario en sesión
	@Test
	public void PR15() {

		// Iniciamos sesión, este ya tiene una amigo Juan@hotmail.com
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");
		
		// adripc2live.com entra en Publicaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'publications-menu')]/a");
		elementos.get(0).click();
					
		// Sacamos la pestaña para ver las publicaciónes
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/list')]");
		// Pinchamos en la pestaña para ver las publicaciones
		elementos.get(0).click();

		// Aparece la lista de publicciones
		PO_View.checkElement(driver, "text", "adripc@live.com");
		
		PO_View.checkElement(driver, "text", "Hola");
		PO_View.checkElement(driver, "text", "Que tal");
		
		//Se mira que exista una publicacion
		List<WebElement> publicaciones =SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",PO_View.getTimeout()); 
		assertTrue(publicaciones.size() == 1); 

	}
	
	//11.1 [LisPubAmiVal] Listar las publicaciones de un usuario amigo
	@Test
	public void PR16() {
		
	}
	
	//11.2 [LisPubAmiInVal] Utilizando un acceso vía URL tratar de listar las publicaciones de un usuario que
	//no sea amigo del usuario identificado en sesión.
	@Test
	public void PR17() {
		
	}
	
	//12.1 [PubFot1Val] Crear una publicación con datos válidos y una foto adjunta. 
	@Test
	public void PR18() {
		
	}
	
	//12.1 [PubFot2Val] Crear una publicación con datos válidos y sin una foto adjunta
	@Test
	public void PR19() {
		
	}
	
	//13.1 [AdInVal] Inicio de sesión como administrador con datos válidos.
	@Test
	public void PR21() {
		driver.navigate().to(URL+"/admin/login");
		PO_LoginView.fillFormAdmin(driver, "adripc@live.com", "123456");
		
		PO_RegisterView.checkKey(driver, "users.show.text",
		PO_Properties.getSPANISH());
	}
	
	//13.2 [AdInInVal] Inicio de sesión como administrador con datos inválidos (usar los datos de un usuario
	//que no tenga perfil administrador).
	@Test
	public void PR22() {
		
		driver.navigate().to(URL+"/admin/login");
		PO_LoginView.fillFormAdmin(driver, "a1@live.com", "123456");
		
		PO_RegisterView.checkKey(driver, "Error.admin.login",
		PO_Properties.getSPANISH());
	}
	
	//14.1 [AdLisUsrVal] Desde un usuario identificado en sesión como administrador listar a todos los
	//usuarios de la aplicación
	@Test
	public void PR23() {
		

	}
	
	//15.1 [AdBorUsrVal] Desde un usuario identificado en sesión como administrador eliminar un usuario
	//existente en la aplicación.
	@Test
	public void PR24() {
		
		//Inicio sesion con admin
		driver.navigate().to(URL+"/admin/login");
		PO_LoginView.fillFormAdmin(driver, "adripc@live.com", "123456");
				
		//Comprobamos que Juan@hotmail.com existe
		PO_View.checkElement(driver, "text", "Juan@hotmail.com");
				
		//Borramos el segundo usuario que es Juan@hotmail.com
		driver.findElement(By.id("deleteButton2")).click();
				
		//Comprobamos que Juan@hotmail.com ya no existe
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Juan@hotmail.com", PO_View.getTimeout());
	}
	
	//15.2 [AdBorUsrInVal] Intento de acceso vía URL al borrado de un usuario existente en la aplicación.
	//Debe utilizarse un usuario identificado en sesión pero que no tenga perfil de administrador.
	@Test
	public void PR25() {
		
	}

	// Identificación inválida con usuario de ROL alumno, 99999990A/123456
	/*
	 * @Test public void PR010() { //Vamos al formulario de logueo.
	 * PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
	 * //Rellenamos el formulario PO_LoginView.fillForm(driver, "99999990A" ,
	 * "123456" ); //COmprobamos que entramos en la pagina privada del Usuario
	 * PO_View.checkElement(driver, "text", "Notas del usuario"); }
	 */

	// Identificación válida y desconexión con usuario de ROL usuario,
	// 99999990A/123456
	/*
	 * @Test public void PR011() { //Vamos al formulario de logueo.
	 * PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
	 * //Rellenamos el formulario PO_LoginView.fillForm(driver, "99999990A" ,
	 * "123456" ); //COmprobamos que entramos en la pagina privada del Usuario
	 * PO_View.checkElement(driver, "text", "Notas del usuario");
	 * 
	 * //Vamos a desconectar. PO_NavView.clickOption(driver, "logout", "class",
	 * "btn btn-primary"); PO_View.checkElement(driver, "text", "Login"); }
	 */

	// PR12. Loguearse, comprobar que se visualizan 4 filas de notas y desconectarse
	// usando el rol de
	// estudiante.
	/*
	 * @Test public void PR12() { //Vamos al formulario de logueo.
	 * PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
	 * //Rellenamos el formulario PO_LoginView.fillForm(driver, "99999990A" ,
	 * "123456" ); //COmprobamos que entramos en la pagina privada de Alumno
	 * PO_View.checkElement(driver, "text", "Notas del usuario"); //Contamos el
	 * número de filas de notas List<WebElement> elementos =
	 * SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
	 * PO_View.getTimeout()); assertTrue(elementos.size() == 4); //Ahora nos
	 * desconectamos PO_PrivateView.clickOption(driver, "logout", "text",
	 * "Identifícate"); }
	 */

	// PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion
	// = Nota A2.
	// P13. Ver la lista de Notas.
	/*
	 * @Test public void PR13() { //Vamos al formulario de logueo.
	 * PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
	 * //Rellenamos el formulario PO_LoginView.fillForm(driver, "99999990A" ,
	 * "123456" ); //COmprobamos que entramos en la pagina privada de Alumno
	 * PO_View.checkElement(driver, "text", "Notas del usuario");
	 * SeleniumUtils.esperarSegundos(driver, 1); //Contamos las notas By enlace =
	 * By.xpath("//td[contains(text(), 'Nota A2')]/following-sibling::*[2]");
	 * driver.findElement(enlace).click();------------------------------------------
	 * --------------------------- SeleniumUtils.esperarSegundos(driver, 1);
	 * //Esperamos por la ventana de detalle PO_View.checkElement(driver, "text",
	 * "Detalles de la nota"); SeleniumUtils.esperarSegundos(driver, 1); //Ahora nos
	 * desconectamos PO_PrivateView.clickOption(driver, "logout", "text",
	 * "Identifícate"); }
	 */

	// P14. Loguearse como profesor y Agregar Nota A2.
	// P14. Esta prueba podría encapsularse mejor ...
	/*
	 * @Test public void PR14() { //Vamos al formulario de logueo.
	 * PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
	 * //Rellenamos el formulario PO_LoginView.fillForm(driver, "99999993D" ,
	 * "123456" ); //COmprobamos que entramos en la pagina privada del Profesor
	 * PO_View.checkElement(driver, "text", "99999993D"); //Pinchamos en la opción
	 * de menu de Notas: //li[contains(@id, 'marks-menu')]/a List<WebElement>
	 * elementos = PO_View.checkElement(driver, "free",
	 * "//li[contains(@id,'marks-menu')]/a"); elementos.get(0).click(); //Esperamos
	 * a aparezca la opción de añadir nota: //a[contains(@href, 'mark/add')]
	 * elementos = PO_View.checkElement(driver, "free",
	 * "//a[contains(@href, 'mark/add')]"); //Pinchamos en agregar Nota.
	 * elementos.get(0).click(); //Ahora vamos a rellenar la nota.
	 * //option[contains(@value, '4')] PO_PrivateView.fillFormAddMark(driver, 3,
	 * "Nota Nueva 1", "8"); //Esperamos a que se muestren los enlaces de paginación
	 * la lista de notas elementos = PO_View.checkElement(driver, "free",
	 * "//a[contains(@class, 'page-link')]"); //Nos vamos a la última página
	 * elementos.get(3).click(); //Comprobamos que aparece la nota en la pagina
	 * elementos = PO_View.checkElement(driver, "text", "Nota Nueva 1"); //Ahora nos
	 * desconectamos PO_PrivateView.clickOption(driver, "logout", "text",
	 * "Identifícate"); }
	 */

	// PRN. Loguearse como profesor, vamos a la ultima página y Eliminamos la Nota
	// Nueva 1.
	// PRN. Ver la lista de Notas.
	/*
	 * @Test public void PR15() { //Vamos al formulario de logueo.
	 * PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
	 * //Rellenamos el formulario PO_LoginView.fillForm(driver, "99999993D" ,
	 * "123456" ); //COmprobamos que entramos en la pagina privada del Profesor
	 * PO_View.checkElement(driver, "text", "99999993D"); //Pinchamos en la opción
	 * de menu de Notas: //li[contains(@id, 'marks-menu')]/a List<WebElement>
	 * elementos = PO_View.checkElement(driver, "free",
	 * "//li[contains(@id, 'marks-menu')]/a"); elementos.get(0).click(); //Pinchamos
	 * en la opción de lista de notas. elementos = PO_View.checkElement(driver,
	 * "free", "//a[contains(@href,'mark/list')]"); elementos.get(0).click();
	 * //Esperamos a que se muestren los enlaces de paginacion la lista de notas
	 * elementos = PO_View.checkElement(driver, "free",
	 * "//a[contains(@class, 'page-link')]"); //Nos vamos a la última página
	 * elementos.get(3).click(); //Esperamos a que aparezca la Nueva nota en la
	 * ultima pagina //Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
	 * //td[contains(text(), 'Nota Nueva
	 * 1')]/following-sibling::a[contains(text(),'mark/delete')]" elementos =
	 * PO_View.checkElement(driver, "free",
	 * "//td[contains(text(), 'Nota Nueva 1')]/following-sibling::a[contains(@href, 'mark/delete')]"
	 * ); elementos.get(0).click(); //Volvemos a la última pagina elementos =
	 * PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
	 * elementos.get(3).click(); //Y esperamos a que NO aparezca la ultima
	 * "Nueva Nota 1" SeleniumUtils.EsperaCargaPaginaNoTexto(driver,
	 * "Nota Nueva 1",PO_View.getTimeout() ); //Ahora nos desconectamos
	 * PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate"); }
	 */

}
