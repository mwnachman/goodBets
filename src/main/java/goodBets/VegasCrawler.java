package goodBets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VegasCrawler {

	public static void main(String[] args) {
		String address = "http://www.vegasinsider.com/mlb/odds/las-vegas/?s=538";
		URL pageLocation = null;
		
		try {
			pageLocation = new URL(address);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		Scanner in = null;
		try {
			in = new Scanner(pageLocation.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String htmlText = null;
		while (in.hasNextLine()) {
			htmlText += in.nextLine();
		}
		
		Document document = Jsoup.parse(htmlText);
        Elements allElements = 
            document.getAllElements();
        for (Element element : allElements) {
            System.out.println(element.nodeName() 
            + " " + element.ownText());
        }
	}

}
