
package trw.jroom;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidation {

    final String nameReg = "((^(Mr||Ms||Miss||Mrs||Dr).)?[A-Z][a-z-A-Z]+ ?(?:[A-Z]\\W)?)+";
    final String emailReg = "[A-Za-z]([\\w_.]+)?@[A-Za-z](\\w+)?\\.\\w{3,4}";
    final String movieReg = "[\\w -]+[(]([0-9]{4})[)]";
    final String ssnReg = "^(?!(000|666|9))\\d{3}-(?!00)\\d{2}-(?!0000)\\d{4}$";

    public boolean matchesRegex(String content, String javaRegex) {
        if (content == null || javaRegex == null) {
            return false;
        }
        Matcher m = regexMatch(content, javaRegex);
        return m.find();

    }

    public boolean isValidHumanName(String name) {
        if (name == null) {
            return false;
        }
        Matcher m = regexMatch(name, nameReg);
        return m.find() && m.group().length() == name.length();
    }

    public boolean isValidEmailAddress(String email) {
        if (email == null) {
            return false;
        }
        Matcher m = regexMatch(email, emailReg);
        boolean found = m.find();
        if (!found){
            return false;
        }
        String group = m.group();
        return group.length() == email.length();
    }


    public boolean isValidMovieBefore1995(String movie) {
        if (movie == null || movie.isBlank()) {
            return false;
        }
        Matcher m = regexMatch(movie, movieReg);
        return m.find() && Integer.parseInt(m.group(1)) < 1995 && m.group().length() == movie.length();
    }


    public boolean isValidSSN(String ssn) {
        if (ssn == null || ssn.isBlank()) {
            return false;
        }
        Matcher m = regexMatch(ssn, ssnReg);
        return m.find();
    }

    public boolean validatePasswordComplexity(String password, int minLength, int minUpper, int minLower, int minNumeric, int minSymbols) {
        String lengthRegex = String.format("(?=.{%d,}).+", minLength);
        String upper = "[A-Z]", lower = "[a-z]", numeric = "[0-9]";
        String specialCharReg = "[!@#$%^&*()\\[\\]{};:'\\\"<>,./?]";
        boolean correctLength = regexMatch(password, lengthRegex).find();
        boolean correctNumberOfUpper = regexMatchCount(password, upper) >= minUpper;
        boolean correctNumberOfLower = regexMatchCount(password, lower) >= minLower;
        boolean correctNumberOfNumeric = regexMatchCount(password, numeric) >= minNumeric;
        boolean correctNumberOfSpecial = regexMatchCount(password, specialCharReg) >= minSymbols;
        return correctLength && correctNumberOfUpper &&  correctNumberOfLower && correctNumberOfNumeric && correctNumberOfSpecial ;
    }

    public String[] getHTMLTagsContents(String html, String tagName) {
        if (html == null || tagName == null){
            return null;
        }
        String htmlTagReg = String.format("<%s>([\\w\\W]+?)</%s>",tagName, tagName);
        return getStringArrayOfGroup1Match(html, htmlTagReg);
    }

    public String[] getHTMLLinkURL(String html) {
        String hrefReg = "<a.+?href=\"([\\w\\W]+?)\"";
        if (html == null || html.isBlank()){
            return null;
        }
        return getStringArrayOfGroup1Match(html, hrefReg);
    }

    public Matcher regexMatch(String content, String regex) {
        Pattern p;
        Matcher m;
        p = Pattern.compile(regex);
        m = p.matcher(content);
        return m;
    }

    public int regexMatchCount(String content, String regex){
        Matcher m = regexMatch(content, regex);
        int matchCount = 0;
        while(m.find()){
            matchCount++;
        }
        return matchCount;
    }

    public String[] getStringArrayOfGroup1Match(String content, String regex){
        Matcher m = regexMatch(content,regex);
        ArrayList<String> contents = new ArrayList<>();
        while(m.find()){
            contents.add(m.group(1));
        }
        if (contents.isEmpty()){
            return null;
        }
        return contents.toArray(new String[0]);
    }

}
