package rs.demsys.rst.ui.autoedit;

import org.eclipse.jface.text.IDocument;
import org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider;
import org.eclipse.xtext.ui.editor.autoedit.DefaultAutoEditStrategyProvider;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;

public class RstAutoEditStrategyProvider extends DefaultAutoEditStrategyProvider {
	
	@Override
	public void configureStringLiteral(AbstractEditStrategyProvider.IEditStrategyAcceptor acceptor) {
		acceptor.accept(partitionInsert.newInstance("\"","\""),IDocument.DEFAULT_CONTENT_TYPE);
		acceptor.accept(partitionInsert.newInstance("|","|"),IDocument.DEFAULT_CONTENT_TYPE);
		// The following two are registered for the default content type, because on deletion 
		// the command.offset is cursor-1, which is outside the partition of terminals.length = 1.
		// How crude is that?
		// Note that in case you have two string literals following each other directly, the deletion strategy wouldn't apply.
		// One could add the same strategy for the STRING partition in addition to solve this
		acceptor.accept(partitionDeletion.newInstance("\"","\""),IDocument.DEFAULT_CONTENT_TYPE);
		acceptor.accept(partitionDeletion.newInstance("|","|"),IDocument.DEFAULT_CONTENT_TYPE);
		acceptor.accept(partitionEndSkippingEditStrategy.get(),TerminalsTokenTypeToPartitionMapper.STRING_LITERAL_PARTITION);
	}
}
