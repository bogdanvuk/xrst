package rs.demsys.rst.ui.highlighting;

import java.util.Iterator;
import java.util.List;

import rs.demsys.rst.rst.*;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.impl.TerminalRuleImpl;
import org.eclipse.xtext.nodemodel.*;
import org.eclipse.xtext.nodemodel.impl.*;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.syntaxcoloring.*;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.impl.RuleCallImpl;

import static rs.demsys.rst.ui.highlighting.RstHighlightingConfiguration.*;

public class RstHighlightingCalculator implements
        ISemanticHighlightingCalculator {
	
	public void provideHighlightingFor(final XtextResource resource, IHighlightedPositionAcceptor acceptor) {
		if (resource == null || resource.getContents().isEmpty())
			return;
		TreeIterator<EObject> iterator = resource.getAllContents();
		while (iterator.hasNext()) {
			EObject eObject = (EObject) iterator.next();
			INode node = NodeModelUtils.findActualNodeFor(eObject);
//			System.out.println(node.getText());
			highlightingFor.invoke(eObject, node, acceptor);
		}
	}
    
	private PolymorphicDispatcher<Void> highlightingFor = PolymorphicDispatcher.createForSingleTarget("highlight", 3, 3, this);

	protected void highlight(EObject obj, INode node, IHighlightedPositionAcceptor acceptor) {
		
	}

	protected void highlight(Directive obj, INode node, IHighlightedPositionAcceptor acceptor) {
		Iterator<ILeafNode> leaves = node.getLeafNodes().iterator();
		while(leaves.hasNext())
		{
			ILeafNode n = leaves.next();
			acceptor.addPosition(n.getOffset(), n.getLength(), DIRECTIVE);
			
			if (n.getText().endsWith("::"))
				break;
		}
	}

	protected void highlight(DirectiveOption obj, INode node, IHighlightedPositionAcceptor acceptor) {
		INode nameNode = NodeModelUtils.findNodesForFeature(obj, RstPackage.eINSTANCE.getDirectiveOption_Name()).get(0);
		acceptor.addPosition(nameNode.getOffset(), nameNode.getLength(), DIRECTIVE);
		
		INode textNode = NodeModelUtils.findNodesForFeature(obj, RstPackage.eINSTANCE.getDirectiveOption_Value()).get(0);
		acceptor.addPosition(textNode.getOffset(), textNode.getLength(), INTERPRETED_TEXT);
////		Iterator<ILeafNode> leaves = node.getLeafNodes().iterator();
//		Iterator<INode> leaves = node.getAsTreeIterable().iterator();
//		while(leaves.hasNext())
//		{
////			ILeafNode n = leaves.next();
//			INode n = leaves.next();
//			
//			if (n.getGrammarElement() instanceof RuleCall)
//			{
//				String ruleName = ((RuleCall)n.getGrammarElement()).getRule().getName();
//				
//				if (ruleName.equals("SimpleText"))
//					break;
//				else if (ruleName.equals("DirectiveOption"))
//					continue;
//			}
//			
//			acceptor.addPosition(n.getOffset(), n.getLength(), DIRECTIVE);
//		}
	}
	
	
	protected void highlight(Replacement obj, INode node, IHighlightedPositionAcceptor acceptor) {
		Iterator<ILeafNode> leaves = node.getLeafNodes().iterator();
		while(leaves.hasNext())
		{
			ILeafNode n = leaves.next();
			acceptor.addPosition(n.getOffset(), n.getLength(), DIRECTIVE);
			
			if (n.getText().equals("replace::"))
				break;
//			if (n.getGrammarElement() instanceof Keyword)
//			{
//				acceptor.addPosition(n.getOffset(), n.getLength(), DIRECTIVE);
//			}
		}
	}
	
	protected void highlight(ReplacementRef obj, INode node, IHighlightedPositionAcceptor acceptor) {
		acceptor.addPosition(node.getOffset(), node.getLength(), DIRECTIVE);
	}
	
	protected void highlight(CiteRoleEntryList obj, INode node, IHighlightedPositionAcceptor acceptor) {
		acceptor.addPosition(node.getOffset(), node.getLength(), INTERPRETED_TEXT);
	}
	
	protected void highlight(RawText obj, INode node, IHighlightedPositionAcceptor acceptor) {
		acceptor.addPosition(node.getOffset(), node.getLength(), INTERPRETED_TEXT);
	}

	protected void highlight(MathRole obj, INode node, IHighlightedPositionAcceptor acceptor) {
		List<INode> textNodes = NodeModelUtils.findNodesForFeature(obj, RstPackage.eINSTANCE.getMathRole_Text());
		acceptor.addPosition(textNodes.get(0).getOffset(), textNodes.get(0).getLength(), INTERPRETED_TEXT);
	}
	
	protected void highlight(NumfigRole obj, INode node, IHighlightedPositionAcceptor acceptor) {
		INode textNode = NodeModelUtils.findNodesForFeature(obj, RstPackage.eINSTANCE.getNumfigRole_Text()).get(0);
		acceptor.addPosition(textNode.getOffset(), textNode.getLength(), INTERPRETED_TEXT);
		
		INode refNode = NodeModelUtils.findNodesForFeature(obj, RstPackage.eINSTANCE.getNumfigRole_Ref()).get(0);
		acceptor.addPosition(refNode.getOffset(), refNode.getLength(), FILE_NAME);
	}

	protected void highlight(Bold obj, INode node, IHighlightedPositionAcceptor acceptor) {
		acceptor.addPosition(node.getOffset(), node.getLength(), BOLD_TEXT);
	}
	
	protected void highlight(Italic obj, INode node, IHighlightedPositionAcceptor acceptor) {
		acceptor.addPosition(node.getOffset(), node.getLength(), ITALIC_TEXT);
	}
	
	protected void highlight(FileName obj, INode node, IHighlightedPositionAcceptor acceptor) {
		acceptor.addPosition(node.getOffset(), node.getLength(), FILE_NAME);
	}
	
	protected void highlight(SectionTitle obj, INode node, IHighlightedPositionAcceptor acceptor) {
		acceptor.addPosition(node.getOffset(), node.getLength(), HEADING);
	}
	
	protected void highlight(NamedSectionTitle obj, INode node, IHighlightedPositionAcceptor acceptor) {
		acceptor.addPosition(node.getOffset(), node.getLength(), HEADING);
	}
	
//    void setStyles(IHighlightedPositionAcceptor acceptor,
//            BidiIterator<INode> it, String... styles) {
//        for (String s : styles) {
//            if (!it.hasNext())
//                return;
//            INode n = skipWhiteSpace(acceptor, it);
//            if (n != null && s != null)
//                acceptor.addPosition(n.getOffset(), n.getLength(), s);
//        }
//    }
//
//    INode skipWhiteSpace(IHighlightedPositionAcceptor acceptor,
//            BidiIterator<INode> it) {
//        INode n = null;
//        while (it.hasNext()
//                && (n = it.next()).getClass() == HiddenLeafNode.class)
//            processHiddenNode(acceptor, (HiddenLeafNode) n);
//        return n;
//    }
//
//    INode skipWhiteSpaceBackwards(IHighlightedPositionAcceptor acceptor,
//            BidiIterator<INode> it) {
//        INode n = null;
//        while (it.hasPrevious()
//                && (n = it.previous()).getClass() == HiddenLeafNode.class)
//            processHiddenNode(acceptor, (HiddenLeafNode) n);
//        return n;
//    }
//
//    void processHiddenNode(IHighlightedPositionAcceptor acceptor,
//            HiddenLeafNode node) {
//        if (node.getGrammarElement() instanceof TerminalRuleImpl) {
//            TerminalRuleImpl ge = (TerminalRuleImpl) node.getGrammarElement();
//            if (ge.getName().equalsIgnoreCase("GUESS_COMMENT"))
//                acceptor.addPosition(node.getOffset(), node.getLength(),
//                        DIRECTIVE);
//        }
//
//    }

}