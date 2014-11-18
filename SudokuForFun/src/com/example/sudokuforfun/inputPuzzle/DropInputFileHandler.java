package com.example.sudokuforfun.inputPuzzle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.vaadin.annotations.Theme;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.StreamVariable;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.Html5File;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TabSheet;

@Theme("sudokuforfun")
public class DropInputFileHandler extends DragAndDropWrapper implements
		DropHandler {
	private static final long serialVersionUID = 1L;
	private static final long FILE_SIZE_LIMIT = 2 * 1024 * 1024; // 2MB

	private ProgressBar progress;
	private Label label;
	private PopulateBoard puzzle;
	private final TabSheet tabSheet;

	public DropInputFileHandler(final TabSheet tab, final Component root) {
		super(root);
		setDropHandler(this);
		this.tabSheet = tab;
	}

	public DropInputFileHandler(final TabSheet tab, final Component root,
			PopulateBoard puzzle, Label lab, ProgressBar prog) {
		super(root);
		setDropHandler(this);
		this.puzzle = puzzle;
		this.progress = prog;
		this.tabSheet = tab;
		this.label = lab;
	}

	@Override
	public void drop(final DragAndDropEvent dropEvent) {
		final WrapperTransferable tr = (WrapperTransferable) dropEvent
				.getTransferable();
		final Html5File[] files = tr.getFiles();
		if (files != null) {
			for (final Html5File html5File : files) {
				if (html5File.getFileSize() > FILE_SIZE_LIMIT) {
					Notification
							.show("File rejected. Max 2Mb files are accepted by Sampler",
									Notification.Type.WARNING_MESSAGE);
				} else {
					final StreamVariable streamVariable = new StreamVariable() {
						private static final long serialVersionUID = 1L;

						@Override
						public OutputStream getOutputStream() {
							final FileOutputStream bas;
							try {
								bas = new FileOutputStream(
										PopulateBoard.REMOTE_INPUT);

								return new OutputStream() {

									@Override
									public void write(int b) throws IOException {
										bas.write(b);
									}

									@Override
									public void close() throws IOException {
										super.close();
										bas.close();
									}

								};
							} catch (FileNotFoundException e) {
								e.printStackTrace();
								return null;
							}

						}

						@Override
						public boolean listenProgress() {
							return false;
						}

						@Override
						public void onProgress(
								final StreamingProgressEvent event) {
						}

						@Override
						public void streamingStarted(
								final StreamingStartEvent event) {
						}

						@Override
						public void streamingFinished(
								final StreamingEndEvent event) {
							progress.setVisible(false);
							label.setVisible(true);
							puzzle.populateBoard(PopulateBoard.REMOTE_INPUT);
							// advance to the next tab, so the game start
							tabSheet.setSelectedTab(tabSheet.getTabIndex() + 1);
						}

						@Override
						public void streamingFailed(
								final StreamingErrorEvent event) {
							progress.setVisible(false);
						}

						@Override
						public boolean isInterrupted() {
							return false;
						}
					};
					html5File.setStreamVariable(streamVariable);
					progress.setVisible(true);
					label.setVisible(false);
				}
			}
		}
	}

	@Override
	public AcceptCriterion getAcceptCriterion() {
		return AcceptAll.get();
	}

}