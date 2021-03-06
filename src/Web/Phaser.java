package Web;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Phaser {

	// Page에서 조건에 해당하는 태그내용을 phasing
	public String phase(String source, String startSplit, String endSplit) {
		return phase(source, startSplit, endSplit, false, false);
	}
	public String phase(String source, String startSplit, String endSplit, boolean doFilter) {
		return phase(source, startSplit, endSplit, doFilter, false);
	}
	public String phase(String source, String startSplit, String endSplit, boolean doFilter, boolean doExtractText) {
		String temp = "";

		try{
			if (source.contains(endSplit)) {
				if (source.contains(startSplit)) {
					temp = source.substring(source.indexOf(startSplit) + startSplit.length());
					temp = temp.substring(0, temp.indexOf(endSplit));
				} else
					temp = source.substring(0, source.indexOf(endSplit));
				if (doFilter)
					temp = removeTag(temp);
				if (doExtractText)
					temp = extractText(temp);
			} 
		}
		catch(Exception e){
			System.err.println("Split : " + startSplit + ", " + endSplit + " Filter : " + doFilter + " Extract : " + doExtractText);
			System.err.println("Source : " + source);
			System.err.println("Temp : " + temp);
			e.printStackTrace();
		}
		return storyFilter(temp);
	}
	public ArrayList<String> phase(String source, String subStartSplit, String subEndSplit, String mainSplit) {
		return phase(source, subStartSplit, subEndSplit, mainSplit, false, false);
	}
	public ArrayList<String> phase(String source, String subStartSplit, String subEndSplit, String mainSplit,
			boolean doFilter) {
		return phase(source, subStartSplit, subEndSplit, mainSplit, doFilter, false);
	}
	public ArrayList<String> phase(String source, String subStartSplit, String subEndSplit, String mainSplit,
			boolean doFilter, boolean doExtractText) {
		ArrayList<String> tagList = new ArrayList<String>();

		String[] token = source.split(mainSplit);
		for (int i = 0; i < token.length; i++)
			tagList.add(phase(token[i], subStartSplit, subEndSplit, doFilter, doExtractText));

		return tagList;
	}
	public ArrayList<String> phase(ArrayList<String> source, String subStartSplit, String subEndSplit) {
		return phase(source, subStartSplit, subEndSplit, false, false);
	}
	public ArrayList<String> phase(ArrayList<String> source, String subStartSplit, String subEndSplit, boolean doFilter) {
		return phase(source, subStartSplit, subEndSplit, doFilter, false);
	}
	public ArrayList<String> phase(ArrayList<String> source, String subStartSplit, String subEndSplit,
			boolean doFilter, boolean doExtractText) {
		ArrayList<String> tagList = new ArrayList<String>();

		for(int i = 0; i < source.size(); i++)
			tagList.add(phase(source.get(i), subStartSplit, subEndSplit, doFilter, doExtractText));
		
		return tagList;
	}
	public ArrayList<String> phaseSourcesOfComments(String source, String subStartSplit, String subEndSplit, String mainSplit) {
		return phaseSourcesOfComments(source, subStartSplit, subEndSplit, mainSplit, false, false);
	}
	public ArrayList<String> phaseSourcesOfComments(String source, String subStartSplit, String subEndSplit, String mainSplit, boolean doFilter) {
		return phaseSourcesOfComments(source, subStartSplit, subEndSplit, mainSplit, doFilter, false);
	}
	public ArrayList<String> phaseSourcesOfComments(String source, String subStartSplit, String subEndSplit, String mainSplit, boolean doFilter, boolean doExtractText) {
		String temp = source.substring(source.indexOf(mainSplit)+mainSplit.length());
		int commentEndIndex;
		ArrayList<String> sourcesOfComments = new ArrayList<>();
		while(temp.contains(subEndSplit) && temp.contains(subStartSplit))
		{
			temp = temp.substring(temp.indexOf(subStartSplit)+subStartSplit.length());
			commentEndIndex = temp.indexOf(subEndSplit);
			String sourceOfComments = temp.substring(0, commentEndIndex);
			if(doFilter)
				sourceOfComments = removeTag(sourceOfComments);
			if(doExtractText)
				sourceOfComments = extractText(sourceOfComments);
			
			sourcesOfComments.add(sourceOfComments);
			temp = temp.substring(commentEndIndex + subEndSplit.length());
		}
		
		return sourcesOfComments;
	}
	
	public String extractText(String textWithTag)
	{
		String result = textWithTag;
		result = extractText(result, "<", ">");
		
		return result;
	}
	private String extractText(String text, String startTag, String endTag)
	{
		String result = "";
		String temp = text;
		while (temp.contains(endTag)) {
			if (temp.contains(startTag))
			{
				if(temp.indexOf(startTag) < temp.indexOf(endTag))
				{
					result = result + temp.substring(0, temp.indexOf(startTag));
					temp = temp.substring(temp.indexOf(endTag)+endTag.length());
				}
				else if(text.indexOf(endTag) < text.indexOf(startTag))
				{
					temp = temp.substring(temp.indexOf(endTag)+endTag.length());
					result = result + temp.substring(0, temp.indexOf(startTag));
				}
			}
			else
				temp = temp.substring(temp.indexOf(endTag) + endTag.length());
		}
		if(temp.contains(startTag))
			result = result + temp.substring(0, temp.indexOf(startTag));
		else
			result = result + temp;
		
		return result;
	}
	public ArrayList<String> split(String str, String con) {
		ArrayList<String> strList = new ArrayList<String>();
		String[] temp = str.split(con);
		for (int i = 0; i < temp.length; i++)
			strList.add(temp[i]);

		return strList;
	}
	public String removeTag(String sentenceInTag) {
		String temp = sentenceInTag;
		temp = temp.replace(" ", "");
		temp = temp.replace("\t", "");
		temp = temp.replace("amp;", "");
		temp = temp.replace("[", "");
		temp = temp.replace("]", "");
		return temp;
	}
	public String storyFilter(String sentence) {
		String temp = sentence;
		temp = temp.replace("\n", "");
		temp = temp.replace("<br />", "\n");
		temp = temp.replace("&it;", "<");
		temp = temp.replace("&gt;", ">");
		temp = temp.replace("&quot;", "\"");
		return temp;
	}
	public int searchDigit(String strContainsDigit){
		String digit = "", temp;
		boolean start = false;
		for(int i = 0; i < strContainsDigit.length(); i++)
		{
			temp = (i+1)>=strContainsDigit.length()?strContainsDigit.substring(i):strContainsDigit.substring(i, i+1);
			if(Pattern.matches("^[0-9]", temp))
			{
				digit = digit + temp;
				start = true;
			}
			else if(start)
				break;
		}
		if(!start)
			return -1;
		
		return Integer.valueOf(digit);
	}
}
