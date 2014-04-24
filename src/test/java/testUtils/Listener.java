package testUtils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import utils.core.WebDriverFactory;

import java.io.*;
import java.util.Calendar;

/**
 * User: sergiich
 * Date: 4/9/14
 */
public class Listener extends TestListenerAdapter{

	private static PrintWriter output;
	private static final String OUT_FOLDER            = "target/custom";
    private static final String INDEX_FILENAME = OUT_FOLDER+"/index.html";
	String[] list = {"BingoScheduleTest", "HomePageTest", "ChangeMyDetailsTest", "ChangeMyPasswordTest",
			"ForgotPasswordTest", "GamesPortletTest", "InboxTest", "InternalTagsTest",
			"LiveTableFinderTest", "LoginTest", "PushMessagesTest", "ReferAFriendTest",
			"RegistrationTest", "ResponsibleGamingTest"};


	@Override
	public void onTestFailure(ITestResult iTestResult){
		createScreenshot(iTestResult);
		System.out.println(iTestResult.getName() + "--Test method failed\n");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult){
		createScreenshot(iTestResult);
		System.out.println(iTestResult.getName()+ "--Test method passed\n");
	}

	@Override
	public void onFinish(ITestContext testContext){
        int passed = testContext.getPassedTests().getAllResults().size();
        int failed = testContext.getFailedTests().getAllResults().size();
        int total = passed+failed;
        int ims = 0;

		String classname=null;
		if(!testContext.getPassedTests().getAllResults().isEmpty()){
			for(ITestResult iTestResult:testContext.getPassedTests().getAllResults()){
				classname = iTestResult.getTestClass().getName();
			break;
			}
		}else if(!testContext.getFailedTests().getAllResults().isEmpty()){
			for(ITestResult iTestResult:testContext.getFailedTests().getAllResults()){
				classname = iTestResult.getTestClass().getName();
				break;
			}
        }
        try{
            File index= new File(INDEX_FILENAME);
            if(!index.exists()){
                output = new PrintWriter(INDEX_FILENAME);
                startHtmlPage("Test report");
                createIndex();
                endHtmlPage();
                output.flush();
                output.close();
                System.out.println("created page for index");
            }
        }catch(Exception e){
            System.out.println("Exception! But it's okay.");
        }
        if(classname!=null){
            writeToIndex(classname, total, passed, failed, ims);
            try{
                output = new PrintWriter(OUT_FOLDER+"/"+classname+".html");
            }catch(Exception e){
                e.printStackTrace();
            }
            startHtmlPage(classname + " report");
            output.println("<h1>Test area: "+classname+"</h1>");
            createTable(testContext, total, passed, failed, ims);

            endHtmlPage();
            output.flush();
            output.close();
        }

	}

    private void writeToIndex(String classname, int total, int passed, int failed, int ims){
        String line;
        String report="";
        try{
            File index = new File(INDEX_FILENAME);
            FileReader fileReader = new FileReader(index);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) !=null){
                line = replaceStringWithInt(line, classname + "Total", total);
                line = replaceStringWithInt(line, classname + "Passed", passed);
                line = replaceStringWithInt(line, classname + "Failed", failed);
                line = replaceStringWithInt(line, classname + "Ims", ims);
                line = replaceStringWithString(line, " style=\"display:none;\"><td><a href ='"+classname, "><td><a href ='"+classname);
                line = replaceTotal(line, "id='total'>", "total", 11, total);
                line = replaceTotal(line, "id='passed'>", "passed", 12, passed);
                line = replaceTotal(line, "id='failed'>", "failed", 12, failed);
                line = replaceTotal(line, "id='ims'>", "ims", 9, ims);
                report = report.concat(line);
            }
            bufferedReader.close();
            fileReader.close();
            FileWriter fileWriter = new FileWriter(index);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(report);
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    private String replaceStringWithInt(String line, String tag, int number){
        if(line.contains(tag)){
            line=line.replace(tag, Integer.toString(number));
        }
        return line;
    }

    private String replaceStringWithString(String line, String tag, String replacer){
        if(line.contains(tag)){
            line=line.replace(tag, replacer);
        }
        return line;
    }

    private String replaceTotal(String line, String tag, String id, int outSet, int number){
        String total;
        int sum;
        int index = line.indexOf(tag);
        if(line.contains(tag)){
            total = line.substring(index+outSet, line.indexOf("<", index));
            sum = Integer.parseInt(total);
            sum = sum+number;
            line = line.replaceAll("id='"+id+"'>\\d{1,}<", tag + Integer.toString(sum) + "<");
        }
        return line;
    }


	private void createTable(ITestContext iTestContext, int total, int passed, int failed, int ims){
        output.println("<h2>Total: " + total + " Passed: " + passed + " Failed: " + failed + " IMS Issues: " + ims + "</h1>");
        output.println("<table border=\"1\" style=\"background-color:yellow;border:1px black;width:80%;border-collapse:collapse;\">");
        output.println("<tr style=\"background-color:orange;color:white;\"><td>Area</td><td>Status</td><td>Screenshot</td><td>Error</td></tr>");
		addRows(iTestContext);
        output.println("</table>");
        output.println("<br>");
	}

    private void createIndex(){
        output.println("<table border=\"1\" style=\"background-color:yellow;border:1px black;width:80%;border-collapse:collapse;\">");
        output.println("<tr style=\"background-color:orange;color:white;\"><td>Area</td><td>Total</td><td>Passed</td><td>Failed</td><td>Ims issues</td></tr>");
        for(String area:list){
            output.println("<tr style=\"display:none;\"><td><a href ='" + area + ".html'>" + area + "</a></td><td>" + area + "Total</td><td>" + area + "Passed</td><td>" + area + "Failed</td><td>" + area + "Ims</td></tr>");
        }
        output.println("<tr><td>TOTAL</td><td id='total'>0</td><td id='passed'>0</td><td id='failed'>0</td><td id='ims'>0</td></tr>");
        output.println("</table>");
        output.println("<br>");
    }

    private String printList(){
        String result = "[";
        for(String a:list){
            result = result+"'"+a+"'";
            if (!a.equals("ResponsibleGamingTest")){
                result=result+",";
            }
        }
        result=result+"]";
        return result;
    }


	private void addRows(ITestContext iTestContext){
        if(!iTestContext.getPassedTests().getAllResults().isEmpty()){
            output.println("<tr align='center' style='background-color:green'><td colspan='4'>Passed tests</td></tr>");
            for(ITestResult test:iTestContext.getPassedTests().getAllResults()){
                output.println("<tr style='background-color:green'><td>" + test.getName() + "</td> ");
                output.println("<td class='passed'>passed</td> ");
                output.println("<td><a href='" + test.getName() + ".jpg'>Screenshot</a></td><td></td></tr>");
            }
        }
        if(!iTestContext.getFailedTests().getAllResults().isEmpty()){
            output.println("<tr align='center' style='background-color:red'><td colspan='4'>Failed tests</td></tr>");
            for(ITestResult test:iTestContext.getFailedTests().getAllResults()){
                output.println("<tr style='background-color:red'><td>" + test.getName() + "</td> ");
                output.println("<td class='failed'>failed</td> ");
                output.println("<td><a href='" + test.getName() + ".jpg'>Screenshot</a></td>");
                output.println("<td>" + createSpoiler(test.getThrowable()) + "</td></tr>");
            }
        }
		if(!iTestContext.getSkippedTests().getAllResults().isEmpty()){
            output.println("<tr align='center'><td colspan='4'>Skipped Tests</td></tr>");
            for(ITestResult test:iTestContext.getSkippedTests().getAllResults()){
                output.println("<tr><td>" + test.getName() + "</td> ");
                output.println("<td>Skipped</td> ");
                output.println("<td>N/A</td>");
                output.println("<td>N/A</td></tr>");
            }

        }

	}

	private void startHtmlPage(String pageName)
	{
        output.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        output.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        output.println("<head>");
        output.println("<title>"+pageName+"</title>" );
		/* Include Java Script and JQuery */
//		out.println("<script type=\"text/javascript\" src=\"stylesheets/jquery-latest.js\"></script>");
		/* Include Style Sheets */
//		out.println("<link rel=\"stylesheet\" href=\"stylesheets/sexybuttons.css\" type=\"text/css\" />");
//		out.println("<link rel=\"stylesheet\" href=\"stylesheets/custom-report-stylesheet.css\" type=\"text/css\" />");
        output.println("<style media=\"screen\" type=\"text/css\">table {table-layout:fixed;overflow:hidden;word-wrap:break-word;}" +
				".td1 {width:100px;}" +
				".td2 {width:50px;}" +
				".td3 {width:50px;}</style>");
        output.println("</head>");
        output.println("<body><br/>");
		Calendar cal = Calendar.getInstance();
        output.println("<br/><div align=\"right\">Test finished on: " + cal.getTime() + "</div><br/><br/>");
	}

	private void endHtmlPage()
	{
        output.println("</div></div></div></div>");
        output.println("</body></html>");
	}

	private void createFolder(){
		File file = new File(OUT_FOLDER);
		file.mkdirs();
	}

	private void createScreenshot(ITestResult iTestResult){
		WebDriver webDriver = WebDriverFactory.getWebDriver();
		createFolder();
		String imageName = "/"+iTestResult.getName()+".jpg";
		File file = new File(OUT_FOLDER+imageName);
		File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
		try  {
			FileUtils.copyFile(scrFile, file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

    private String createSpoiler(Throwable exception){
        return exception.toString()/*.substring(0, exception.toString().indexOf(": "))*/+
                " <a id=\"show_id\" onclick=\"document.getElementById('spoiler_id').style.display='';" +
                " document.getElementById('show_id').style.display='none';\" class=\"link\">[Show]</a>" +
                "<span id=\"spoiler_id\" style=\"display: none\"><a onclick=\"document.getElementById('spoiler_id').style.display='none';" +
                " document.getElementById('show_id').style.display='';\" class=\"link\">[Hide]</a><br>"+exception.getMessage()+"</span>";
    }
}
