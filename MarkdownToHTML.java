import java.util.*;
import java.io.*;

public class MarkdownToHTML {
	
	public static String translateEmphasis(String text) {
		return "<em>" + text + "</em>";
	}
	
	public static String translateStrongEmphasis(String text) {
		return "<strong>" + text + "</strong>";
	}
	
	public static String translateHyperlink(String link, String URL) {
		return "<a href=\\\"" + URL + "\\>" + link + "</a>";
	}
	
	public static String translateImage(String altText, String imgPath, String imgTitle) {
		return "<img src=\\\"" + imgPath + "\\\" alt=\\\"" + altText + 
				"\\\" title=\\\"" + imgTitle + "\\\">\"";
	}
	
	public static String translateCode(String text) {
		return "<code>" + text + "</code>";
	}
	
	public static String translateListItem(String text) {
		return "<li>" + text + "</li>";
	}
	
	public static void main (String [] args) {
		boolean list = false;
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Enter input file name: ");
		String inputFilename = scan.nextLine();
		
		System.out.print("Enter output file name: ");
		String outputFilename = scan.nextLine();
		File output = new File(outputFilename);
		
		try {
			File inputFile = new File(inputFilename);
			Scanner fs = new Scanner(inputFile);
			PrintWriter pw = new PrintWriter(output);
			pw.write("<!DOCTYPE html> \n <html> \n <head> \n <title>Results of Markdown Translation</title> \n </head> \n <body> \n");
			while(fs.hasNextLine()) {
				while(fs.hasNext()) {
				String line = fs.next();
				
				if(line.equals("")) {
					pw.write("<p>");
				}
				
				else if(line.contains("*")) {
					String s = line.substring(line.indexOf("*") + 1);
					s = line.substring(0, line.indexOf("*"));
					pw.write(translateEmphasis(s));
				}
				
				else if(line.contains("**")) {
					String s = line.substring(line.indexOf("**") + 1);
					s = line.substring(0, line.indexOf("**"));
					pw.write(translateStrongEmphasis(s));
				}
				
				else if(line.contains("[") && line.contains("(")) {
					String link = line.substring(line.indexOf("[") + 1);
					link = line.substring(0, line.indexOf("]"));
					
					String URL = line.substring(line.indexOf("(") + 1);
					URL = line.substring(0, line.indexOf("("));
					
					pw.write(translateHyperlink(link, URL));
				}
				
				else if(line.contains("![") && line.contains("(") && line.contains("\"")) {
					String altText = line.substring(line.indexOf("[") + 1);
					altText = line.substring(0, line.indexOf("]"));
					
					String imgPath = line.substring(line.indexOf("(") + 1);
					imgPath = line.substring(0, line.indexOf("])"));
					
					String imgTitle = line.substring(line.indexOf("\"") + 1);
					imgTitle = line.substring(0, line.indexOf("\""));
					
					pw.write(translateImage(altText, imgPath, imgTitle));
				}
				
				else if(line.contains("'")) {
					String s = line.substring(line.indexOf("'") + 1);
					s = line.substring(0, line.indexOf("'"));
					pw.write(translateCode(s));
				}
				
				if(line.contains("+ ") && list == false) {
					pw.write("<ul>");
				}
				
				if(line.contains("+ ")) {
					pw.write("<ul>");
					list = true;
					
					if(list == true) {
						String s = line.substring(line.indexOf("+") + 2);
						s = line.substring(0, line.indexOf(" "));
						pw.write(translateListItem(s));
					}
				}
				
				else if(list == false) {
					pw.write("</ul>");
				}
				
				else {
					pw.write(line);
				}
			}
			}
			
			pw.write("</body> \n </html>");
		}
		
		catch(IOException e) {
			
		}
	}
}
