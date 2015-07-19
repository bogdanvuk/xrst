package rs.demsys.rst.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.XtextResource;

public class RstUiUtils {
    public static IFile findFileFromRelativePath(XtextResource resource, String relFileName)
    {
    	String platformString = resource.getURI().toPlatformString(true);
		IFile curFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformString));

		URI uri = URI.createURI(relFileName);
    	URI resFile = URI.createURI(curFile.getProjectRelativePath().toString());
    	uri = resFile.trimSegments(1).appendSegments(uri.segments());
		
		//IFile file = curFile.getProject().getFile(new Path(uriFile.toString()));
    	IFile file = curFile.getProject().getFile(new Path(uri.toString()));
		return file;
    }

}
