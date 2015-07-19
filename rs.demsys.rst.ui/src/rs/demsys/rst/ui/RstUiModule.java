/*
 * generated by Xtext
 */
package rs.demsys.rst.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.ui.editor.contentassist.ITemplateProposalProvider;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkHelper;
import org.eclipse.xtext.ui.editor.model.PartitionTokenScanner;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;
import org.eclipse.xtext.ui.editor.reconciler.XtextSpellingReconcileStrategy;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;

import rs.demsys.rst.ui.highlighting.RstHighlightingCalculator;
import rs.demsys.rst.ui.highlighting.RstHighlightingConfiguration;
import rs.demsys.rst.ui.hover.RstEObjectDocumentationProvider;
import rs.demsys.rst.ui.hover.RstEObjectHoverProvider;
import rs.demsys.rst.ui.hyperlink.RstHyperlinkHelper;
import rs.demsys.rst.ui.spelling.RstSpellingReconcileStrategy;
import rs.demsys.rst.ui.spelling.RstTerminalsTokenTypeToPartitionMapper;
import rs.demsys.rst.ui.spelling.RstPartitionTokenScanner;
import rs.demsys.rst.ui.templates.RstTemplateProposalProvider;

/**
 * Use this class to register components to be used within the IDE.
 */
public class RstUiModule extends rs.demsys.rst.ui.AbstractRstUiModule {
	public RstUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration () {
        return RstHighlightingConfiguration.class;
    }
	
	public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator(){
        return RstHighlightingCalculator.class;
    }
	
	@Override
    public Class<? extends IHyperlinkHelper> bindIHyperlinkHelper() {
        return RstHyperlinkHelper.class;
    }
    
	public Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
        return RstEObjectHoverProvider.class;
    }
 
    public Class<? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProviderr() {
        return RstEObjectDocumentationProvider.class;
    }
    
    public Class<? extends XtextSpellingReconcileStrategy.Factory> bindXtextSpellingReconcileStrategy$Factory() {
		return RstSpellingReconcileStrategy.factory.class;
	}
      
	public Class<? extends TerminalsTokenTypeToPartitionMapper> bindTerminalsTokenTypeToPartitionMapper(){
		return RstTerminalsTokenTypeToPartitionMapper.class;
	}
	
	public Class<? extends PartitionTokenScanner> bindXdocPartitionTokenScanner() {
		return RstPartitionTokenScanner.class;
	}
	
	@Override
    public Class<? extends ITemplateProposalProvider> bindITemplateProposalProvider() {
      return RstTemplateProposalProvider.class;
    }
}