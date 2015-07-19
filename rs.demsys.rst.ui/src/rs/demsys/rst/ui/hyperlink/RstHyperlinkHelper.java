package rs.demsys.rst.ui.hyperlink;

import java.util.List;

import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jface.text.Region;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.AbstractNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.scoping.impl.ImportUriGlobalScopeProvider;
import org.eclipse.xtext.scoping.impl.ImportUriResolver;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkHelper;
import org.eclipse.xtext.ui.editor.hyperlinking.XtextHyperlink;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

import rs.demsys.rst.rst.*;
import rs.demsys.rst.ui.RstUiUtils;
import rs.demsys.rst.services.RstGrammarAccess;

public class RstHyperlinkHelper extends HyperlinkHelper {
	@Inject
    RstGrammarAccess ga;
    
    @Inject
    ImportUriResolver r;
    
    @Inject
    private Provider<XtextHyperlink> hyperlinkProvider;
    
    @Inject
    private Provider<RstGeneralHyperlink> rstGenHyperlinkProvider;
    
    @Inject
    ImportUriGlobalScopeProvider gsp;
    
    @Inject private EObjectAtOffsetHelper eObjectAtOffsetHelper;
    
    private PolymorphicDispatcher<Void> createHypelinkFor = PolymorphicDispatcher.createForSingleTarget("createHyperlink", 3, 3, this);
    
    public void createHyperlink(EObject obj, XtextResource resource, IHyperlinkAcceptor acceptor)
    {
    }
    
    public RstGeneralHyperlink createGeneralHyperlink(String text, IFile file, int offset, int length)
    {
    	RstGeneralHyperlink result = rstGenHyperlinkProvider.get();
        //result.setURI(URI.createURI(file.getLocation().toString()));
    	result.setURI(URI.createURI(file.getLocationURI().toString()));
        Region region = new Region(offset, length);
        result.setHyperlinkRegion(region);
        result.setHyperlinkText(text);
        result.setFile(file);
        
        return result;
    }
    
    public XtextHyperlink createXtextHyperlink(String text, IFile file, int offset, int length)
    {
    	XtextHyperlink result = hyperlinkProvider.get();
        result.setURI(URI.createURI(file.getLocationURI().toString()));
    	//result.setURI(file.getLocationURI());
        Region region = new Region(offset, length);
        result.setHyperlinkRegion(region);
        result.setHyperlinkText(text);
        
        return result;
    }
    
    public void createHyperlink(FileName obj, XtextResource resource, IHyperlinkAcceptor acceptor) {
    	INode node = NodeModelUtils.findActualNodeFor(obj);
    	String uriString = node.getText().trim();
    	
        IFile file = RstUiUtils.findFileFromRelativePath(resource, uriString);
		
		if (file.isAccessible()) {
		    acceptor.accept(createXtextHyperlink(uriString, file, node.getOffset(), node.getLength()));
		}
//    	INode node = NodeModelUtils.findActualNodeFor(obj);
    }
      
    @Override
    public void createHyperlinksByOffset(XtextResource resource, int offset,
                    IHyperlinkAcceptor acceptor) {

    	EObject eObject = eObjectAtOffsetHelper.resolveElementAt(resource, offset);
    	
    	if (eObject != null)
    	{
    		createHypelinkFor.invoke(eObject, resource, acceptor);
    	}
    	
//    	if (eObject instanceof IncludeDirective) {
//    		
//    	}
    	super.createHyperlinksByOffset(resource, offset, acceptor);
    }
}
