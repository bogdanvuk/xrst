package rs.demsys.rst.ui.spelling;

import static rs.demsys.rst.ui.spelling.RstTerminalsTokenTypeToPartitionMapper.TEXT_PARTITION;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.reconciler.XtextSpellingReconcileStrategy;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import rs.demsys.rst.rst.FileName;
import rs.demsys.rst.rst.Text;

import com.google.inject.Inject;

public class RstSpellingReconcileStrategy extends
		XtextSpellingReconcileStrategy {

	private IDocument fDocument;
//	@Inject EObjectAtOffsetHelper eObjectAtOffsetHelper;
	
	public static class factory extends XtextSpellingReconcileStrategy.Factory {
		@Override
		public XtextSpellingReconcileStrategy create(ISourceViewer sourceViewer) {
			return new RstSpellingReconcileStrategy(sourceViewer);
		}
	}
	
	protected RstSpellingReconcileStrategy(ISourceViewer viewer) {
		super(viewer);
	}

	@Override
	public void setDocument(IDocument document) {
		super.setDocument(document);
		fDocument = document;
	}
	
	@Override
	protected boolean shouldProcess(final ITypedRegion typedRegion) {
		if (super.shouldProcess(typedRegion)
				|| TEXT_PARTITION.equals(typedRegion.getType())) {
			String word;
//			try {
				IXtextDocument doc = ((IXtextDocument) fDocument);
				final int offset = typedRegion.getOffset();
				boolean dospell = doc.readOnly(new IUnitOfWork<Boolean, XtextResource>(){
					@Override
					public Boolean exec(XtextResource state) throws Exception {
						// TODO Auto-generated method stub
//						EObject obj = eObjectAtOffsetHelper.resolveContainedElementAt(state, 4);
						IParseResult parseResult = state.getParseResult();
						if (parseResult != null) {
							ILeafNode leaf = NodeModelUtils.findLeafNodeAtOffset(parseResult.getRootNode(), offset);
							EObject obj = NodeModelUtils.findActualSemanticObjectFor(leaf);
//							NodeModeUtils.findNodesForFeature
							return (obj instanceof Text);
						}
//						return (obj instanceof FileName);
						return false;
					}
				});
				
				return dospell;
				
//				word = fDocument.get(typedRegion.getOffset(), typedRegion.getLength());
//				if (word.indexOf('-') == -1)
//					return true;
//			} catch (BadLocationException e) {
//				// TODO Auto-generated catch block
//				return false;
//			}
		}
		return false;
	}
}
