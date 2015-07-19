package rs.demsys.rst.ui.hover;

import org.eclipse.core.internal.utils.FileUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.internal.text.html.HTMLPrinter;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import rs.demsys.rst.bib.Entry;
import rs.demsys.rst.rst.FileName;
import rs.demsys.rst.rst.Label;
import rs.demsys.rst.rst.Replacement;
import rs.demsys.rst.rst.ReplacementRef;
import rs.demsys.rst.ui.RstUiUtils;

public class RstEObjectDocumentationProvider implements
		IEObjectDocumentationProvider {

	private PolymorphicDispatcher<String> getDocumentationDispatch = PolymorphicDispatcher.createForSingleTarget("getDocumentationFor", 1, 1, this);
	
	@Override
	public String getDocumentation(EObject o) {
		if (o != null)
    	{
			return getDocumentationDispatch.invoke(o);
    	}
		return "";
	}
	
	public String getDocumentationFor(EObject o) {
		return "";
	}
	
	public String getDocumentationFor(FileName obj) {
		INode node = NodeModelUtils.findActualNodeFor(obj);
    	String uriString = node.getText().trim();
    	
        IFile file = RstUiUtils.findFileFromRelativePath((XtextResource) obj.eResource(), uriString);
        
//		return "<p style=\"font-family: times, serif; font-size:14pt; font-style:italic\">   Success </p>";
		String res = "An entity represents real business objects. It <strong> proba </strong>";
//                + "<li> can <code>extend</code> another entity,i.e. inherit the features of another entity." 
//                + "<li> has attributes, specification syntax <pre>&lt;name&gt; : &lt;type&gt;</pre>"
//                + "<li> has operations, specification syntax <code>op &lt;name&gt; (&lt;List of Parameters&gt;)) : &lt;Returntype&gt;</code>"
//                + "</ul>";
		
        try {
        	return "<br><pre>" + convertStreamToString(file.getContents()) + "</pre>";
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			return "";
		}
//		return "&nbsp;&nbsp;Sample<br>&#160;&#160;Sample";
	}
	
//	public String getDocumentationFor(ReplacementRef obj) {
//		Replacement repl = (Replacement) obj.eCrossReferences().get(0);
//		return NodeModelUtils.findActualNodeFor(repl.getText()).getText();
//	}
	
	public String getDocumentationFor(Replacement obj) {
		return NodeModelUtils.findActualNodeFor(obj.getText()).getText();
	}
	
	public String getDocumentationFor(Label obj) {
		INode node = NodeModelUtils.findActualNodeFor(obj);
		INode nextNode = node.getNextSibling();
//		return stringToHTMLString(nextNode.getText());
		return "<br><pre>" + nextNode.getText() + "</pre>";
//		return nextNode.getText();
	}
	
	public String getDocumentationFor(Entry obj) {
		return NodeModelUtils.findActualNodeFor(obj).getText();
	}

	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is);
		s.useDelimiter("\\A");
	    String text = s.hasNext() ? s.next() : "";
	    s.close();
	    return text;
	}
	
	public static String stringToHTMLString(String string) {
	    StringBuffer sb = new StringBuffer(string.length());
	    // true if last char was blank
	    boolean lastWasBlankChar = false;
	    int len = string.length();
	    char c;

	    for (int i = 0; i < len; i++)
	    	
	        {
	        c = string.charAt(i);
	        if (c == ' ') {
	            // blank gets extra work,
	            // this solves the problem you get if you replace all
	            // blanks with &nbsp;, if you do that you loss 
	            // word breaking
	        	sb.append("&nbsp;");
//	            if (lastWasBlankChar) {
//	                lastWasBlankChar = false;
//	                sb.append("&nbsp;");
//	                }
//	            else {
//	                lastWasBlankChar = true;
//	                sb.append(' ');
//	                }
	            }
	        else {
	            lastWasBlankChar = false;
	            //
	            // HTML Special Chars
	            if (c == '"')
	                sb.append("&quot;");
	            else if (c == '&')
	                sb.append("&amp;");
	            else if (c == '<')
	                sb.append("&lt;");
	            else if (c == '>')
	                sb.append("&gt;");
	            else if (c == '\n')
	                // Handle Newline
	                sb.append("<br/>");
	            else {
	                int ci = 0xffff & c;
	                if (ci < 160 )
	                    // nothing special only 7 Bit
	                    sb.append(c);
	                else {
	                    // Not 7 Bit use the unicode system
	                    sb.append("&#");
	                    sb.append(new Integer(ci).toString());
	                    sb.append(';');
	                    }
	                }
	            }
	        }
	    return sb.toString();
	}

}
