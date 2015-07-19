package rs.demsys.rst.ui.hover;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import rs.demsys.rst.rst.FileName;

public class RstEObjectHoverProvider extends DefaultEObjectHoverProvider{

	private PolymorphicDispatcher<String> getFirstLineDispatch = PolymorphicDispatcher.createForSingleTarget("getFirstLineFor", 1, 1, this);
	
	@Override
	protected String getFirstLine(EObject o) {
		if (o != null)
    	{
			return getFirstLineDispatch.invoke(o);
    	}
		return "";
	}
	
//	@Override
//	protected String getHoverInfoAsHtml(EObject o) {
//		if (!hasHover(o))
//			return null;
//		StringBuffer buffer = new StringBuffer();
//		buffer.append (getFirstLine(o));
//		String documentation = getDocumentation(o);
//		if (documentation!=null && documentation.length()>0) {
//			buffer.append(documentation);
//		}
//		return buffer.toString();
//	}
	
	public String getFirstLineFor(EObject obj) {
		return super.getFirstLine(obj);
	}
	
	public String getFirstLineFor(FileName obj) {
		return "Success First Line";
	}
}
