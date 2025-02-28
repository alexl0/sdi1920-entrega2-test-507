package com.uniovi.tests.pageobjects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.uniovi.tests.util.SeleniumUtils;

public class PO_View {

    // Pongo tanto tiempo porque a veces el tiempo de carga
    // depende de la base de datos mongodb
    protected static int timeout = 20;

    public static int getTimeout() {
	return timeout;
    }

    public static void setTimeout(int timeout) {
	PO_View.timeout = timeout;
    }

    /**
     * Espera por la visibilidad de un elemento/s en la vista actualmente cargandose
     * en driver..
     * 
     * @param driver: apuntando al navegador abierto actualmente.
     * @param type:
     * @param text:
     * @return Se retornará la lista de elementos resultantes de la búsqueda.
     */
    static public List<WebElement> checkElement(WebDriver driver, String type, String text) {
	List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, type, text, getTimeout());
	return elementos;
    }
}
