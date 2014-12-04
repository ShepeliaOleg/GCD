package utils.core;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import utils.WebDriverUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

public class Listener extends TestListenerAdapter{

    private static PrintWriter output;
    private static String folder;
    private static String outFolder;
    private static String indexFilename;
    private static final String INDEX = "/index.html";
    private static final String CUSTOM = "target/custom";
    private static final String COLOR_GREEN = "#B2F5A6";
    private static final String COLOR_RED = "#FF9763";
    private static final String ENV_REPLACER = "ENVIRONMENT";
    private static final String SKIP_EXCEPTION = "org.testng.SkipException";
    private static final String RUNTIME_EXCEPTION = "java.lang.RuntimeException";

    String[] list = {"BannerGameLaunchTest","BannerTest","BannerProfileIDTest","BingoScheduleTest",
            "ChangeMyDetailsTest","ChangeMyDetailsValidationTest", "ChangeMyPasswordTest","CashierAddCardTest", "CashierEnvoyTest",
            "CashierMasterCardTest", "CashierMoneyBookersTest", "CashierNetellerTest", "CashierPayPalTest",
            "CashierPaySafeCardTest", "CashierQIWITest", "CashierVisaTest", "ForgotPasswordTest", "ForgotUsernameTest",
            "GamesPortletTest","GeneralTest","InboxTest","InternalTagsTest", "LanguageTest",
            "LiveTableFinderTest","LoginTest","LoginLogoutConfirmationTest","LoginValidationTest",
            "PermissionsTest", "PushMessagesBonusTest","LoginMessagesTest", "ReferAFriendTest","RegistrationAffiliateTest",
            "RegistrationTest", "RegistrationUsernameSuggestionTest","RegistrationValidationTest",
            "ResponsibleGamingTest", "SelfExclusionTest", "WebContentTest"};

    @Override
    public void onTestFailure(ITestResult iTestResult){
        System.out.println(iTestResult.getName() + "--Test method failed\n");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult){
        AbstractTest.validate();
        createScreenshot(iTestResult);
        System.out.println(iTestResult.getName()+ "--Test method passed\n");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult){
        createScreenshot(iTestResult);
        System.out.println(iTestResult.getName()+ "--Test method skipped\n");
    }

    @Override
    public void onStart(org.testng.ITestContext testContext){
        if(folder==null){
            folder = "out/";
            outFolder = CUSTOM+"/"+folder+"/";
            indexFilename = CUSTOM+INDEX;
            File index= new File(indexFilename);
            if(index.exists()){
                index.delete();
            }
        }
        try{
            File index= new File(indexFilename);
            if(!index.exists()){
                createFolder();
                output = new PrintWriter(indexFilename);
                startHtmlPage("Test report");
                createIndex();
                endHtmlPage();
                output.flush();
                output.close();
                System.out.println("Created page for index");
            }
        }catch(Exception e){
            System.out.println("Exception! But it's okay.");
        }
    }


    @Override
    public void onFinish(ITestContext testContext){
        int passed = testContext.getPassedTests().getAllResults().size();
        int failed = testContext.getFailedTests().getAllResults().size();
        int ims = testContext.getSkippedTests().getAllResults().size();
        int total = passed+failed+ims;
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
        }else if(!testContext.getSkippedTests().getAllResults().isEmpty()){
            for(ITestResult iTestResult:testContext.getSkippedTests().getAllResults()){
                classname = iTestResult.getTestClass().getName();
                break;
            }
        }
        if(classname!=null){
            writeToIndex(classname, total, passed, failed, ims);
            try{
                output = new PrintWriter(outFolder+classname+".html");
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
        ArrayList<String> report = new ArrayList<>();
        try{
            File index = new File(indexFilename);
            FileReader fileReader = new FileReader(index);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) !=null){
                line = replaceStringWithInt(line, classname + "Total", total);
                line = replaceStringWithInt(line, classname + "Passed", passed);
                line = replaceStringWithInt(line, classname + "Failed", failed);
                line = replaceStringWithInt(line, classname + "Ims", ims);
                line = replaceStringWithString(line, " style='display:none;'><td><a href ='"+folder+classname, "><td><a href ='"+folder+classname);
                if (failed>0){
                    line = paintRed(line, classname);
                }
                if (ims>0){
                    line = paintYellow(line, classname);
                }
                line = replaceTotal(line, "id='total'>", "total", 11, total);
                line = replaceTotal(line, "id='passed'>", "passed", 12, passed);
                line = replaceTotal(line, "id='failed'>", "failed", 12, failed);
                line = replaceTotal(line, "id='ims'>", "ims", 9, ims);
                line = addEnv(line);
                report.add(line);
            }
            bufferedReader.close();
            fileReader.close();
            FileWriter fileWriter = new FileWriter(index);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String writeLine:report){
                bufferedWriter.write(writeLine+"\n");
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private String addEnv(String line){
        if(line.contains(ENV_REPLACER)){
            line = line.replace(ENV_REPLACER, DataContainer.getDriverData().getCurrentUrl());
        }
        return line;
    }

    private String paintRed(String line, String classname){
        if (line.contains(classname)){
            line = line.replace("<tr>", "<tr style='background-color:"+COLOR_RED+"'>");
        }
        return line;
    }

    private String paintYellow(String line, String classname){
        if (line.contains(classname)){
            line = line.replace("<tr>", "<tr style='background-color:yellow'>");
        }
        return line;
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
        output.println("<h2>Total:" + total + "; Passed:" + passed + "; Failed:" + failed + "; IMS Registration/login issues(skipped):" + ims + "</h2>");
        output.println("<h2>Env: "+DataContainer.getDriverData().getCurrentUrl()+"</h2>");
        output.println("<table border='1' style='background-color:yellow;border:1px black;width:100%;border-collapse:collapse;'>");
        output.println("<tr align='center' valign='middle' style='background-color:orange;color:white;'><td width='20%'>Area</td><td width='5%'>Status</td><td>Result</td></tr>");
        addRows(iTestContext);
        output.println("</table>");
        output.println("<br>");
    }

    private void createIndex(){
        output.println("<h2>Env: "+ENV_REPLACER+"</h2>");
        output.println("<table border='1' style='background-color:"+COLOR_GREEN+";border:1px black;width:90%;border-collapse:collapse;'>");
        output.println("<tr style='background-color:orange;color:white;'><td>Area</td><td>Total</td><td>Passed</td><td>Failed</td><td>IMS Registration/Login Issues(Skipped)</td></tr>");
        for(String area:list){
            output.println("<tr style='display:none;'><td><a href ='"+folder+ area + ".html'>" + area + "</a></td><td>" + area + "Total</td><td>" + area + "Passed</td><td>" + area + "Failed</td><td>" + area + "Ims</td></tr>");
        }
        output.println("<tr><td>TOTAL</td><td id='total'>0</td><td id='passed'>0</td><td id='failed'>0</td><td id='ims'>0</td></tr>");
        output.println("</table>");
        output.println("<br>");
    }

    private void addRows(ITestContext iTestContext){
        if(!iTestContext.getPassedTests().getAllResults().isEmpty()){
            output.println("<tr align='center' style='background-color:"+COLOR_GREEN+"'><td colspan='3'>Passed tests</td></tr>");
            for(ITestResult test:iTestContext.getPassedTests().getAllResults()){
                String name = test.getName();
                output.println("<tr style='background-color:"+COLOR_GREEN+"'><td>" + name + "</td> ");
                output.println("<td align='center' valign='middle' class='passed'>passed</td> ");
                output.println("<td align='center' valign='middle'><a href='"+ScreenOrientation.LANDSCAPE.value()+name
                        +".jpg'>Landscape</a>, <a href='"+ScreenOrientation.PORTRAIT.value()+name+".jpg'>Portrait</a></td></tr>");
            }
        }
        if(!iTestContext.getFailedTests().getAllResults().isEmpty()){
            output.println("<tr align='center' style='background-color:"+COLOR_RED+"'><td colspan='3'>Failed tests</td></tr>");
            for(ITestResult test:iTestContext.getFailedTests().getAllResults()){
                String name = test.getName();
                output.println("<tr style='background-color:"+COLOR_RED+"'><td>" + name + "</td> ");
                output.println("<td align='center' valign='middle' class='failed'>failed</td>");
                output.println("<td>" + createSpoiler(test.getThrowable(), name)+"</td></tr>");
            }
        }
        if(!iTestContext.getSkippedTests().getAllResults().isEmpty()){
            output.println("<tr align='center'><td colspan='3'>Skipped Tests</td></tr>");
            for(ITestResult test:iTestContext.getSkippedTests().getAllResults()){
                String name = test.getName();
                output.println("<tr><td>" + name + "</td> ");
                output.println("<td align='center' valign='middle'>Skipped</td> ");
                output.println("<td>"+createSpoiler(test.getThrowable(), name)+"</td></tr>");
            }
        }
    }

    private void startHtmlPage(String pageName){
        output.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.1//EN http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd'>");
        output.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
        output.println("<head>");
        output.println("<title>"+pageName+"</title>" );
        output.println("<style media='screen' type='text/css'>table {table-layout:fixed;overflow:hidden;word-wrap:break-word;}" +
                ".td1 {width:100px;}" +
                ".td2 {width:50px;}" +
                ".td3 {width:50px;}</style>");
        output.println("</head>");
        output.println("<body><br/>");
        Calendar cal = Calendar.getInstance();
        output.println("<br/><div align='right'>Test finished on: " + cal.getTime() + "</div><br/><br/>");
    }

    private void endHtmlPage()
    {
        output.println("</div></div></div></div>");
        output.println("</body></html>");
    }

    private void createFolder(){
        File file = new File(outFolder);
        file.mkdirs();
    }

    public void createScreenshot(ITestResult iTestResult){
        createScreenshot(iTestResult.getName());
    }

    public static String[] createScreenshot(String name){
        String imageName = name+".jpg";
        String landscape = ScreenOrientation.LANDSCAPE.value() + imageName;
        String portrait = ScreenOrientation.PORTRAIT.value() + imageName;
        if(!DataContainer.getDriverData().getBrowser().equals("native")) {
            writeScreenshot(portrait);
            writeScreenshot(landscape);
        }else {
            ScreenOrientation initialOrientation = WebDriverUtils.getOrientation();
            writeScreenshot(initialOrientation.value()+imageName);
            if(initialOrientation.equals(ScreenOrientation.LANDSCAPE)){
                WebDriverUtils.setOrientation(ScreenOrientation.PORTRAIT);
                writeScreenshot(portrait);
            }else {
                WebDriverUtils.setOrientation(ScreenOrientation.LANDSCAPE);
                writeScreenshot(landscape);
            }
            WebDriverUtils.setOrientation(initialOrientation);
        }
        return new String[] {portrait, landscape};
    }

    private static void writeScreenshot(String imageName){
        File file = new File(outFolder +imageName);
        WebDriver webDriver = WebDriverFactory.getPortalDriver();
        if(webDriver!=null){
            File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
            try  {
                FileUtils.copyFile(scrFile, file);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    private String createSpoiler(Throwable exception, String name){
        String exc = exception.toString();
        if(exc.startsWith(SKIP_EXCEPTION)){
            exc=exc.replace(SKIP_EXCEPTION, "Skipped");
        }else if(exc.startsWith(RUNTIME_EXCEPTION)){
            exc=exc.replace(RUNTIME_EXCEPTION, "Error");
        }
        if(exc.contains("%$%")){
            String[] fullException = exc.split("%\\$%");
            if(fullException.length>1){
                return fullException[0]+ spoilerText(name, fullException[1]);
            }else{
                return fullException[0];
            }
        }else{
            return exc;
        }
    }

    private String spoilerText(String name, String message){
        String showId = "show_id_"+name+"";
        String spoilerId = "spoiler_id_"+name+"";
        return " <a id='"+showId+"' onclick='document.getElementById('"+spoilerId+"').style.display='';" +
                " document.getElementById('"+showId+"').style.display='none';' class='link'>[More]</a>" +
                "<div id='"+spoilerId+"' style='display: none'><a onclick='document.getElementById('"+spoilerId+"').style.display='none';" +
                " document.getElementById('"+showId+"').style.display='';' class='link'>[Hide]</a><br>"+message+"</div>";
    }
}