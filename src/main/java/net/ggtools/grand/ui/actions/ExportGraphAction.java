// $Id$
/*
 * ====================================================================
 * Copyright (c) 2002-2003, Christophe Labouisse All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package net.ggtools.grand.ui.actions;

import java.io.IOException;

import net.ggtools.grand.ui.graph.GraphControllerProvider;
import net.ggtools.grand.ui.image.ImageSaver;
import net.ggtools.grand.ui.widgets.ExceptionDialog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Christophe Labouisse
 * @see org.eclipse.jface.action.Action
 */
public class ExportGraphAction extends GraphControllerAction {

    /**
     * Field DEFAULT_ACTION_NAME.
     * (value is {@value #DEFAULT_ACTION_NAME})
     */
    private static final String DEFAULT_ACTION_NAME = "Export Graph as Image";

    /**
     * Field log.
     */
    private static final Log LOG = LogFactory.getLog(ExportGraphAction.class);

    /**
     * Creates a new ReloadGraphAction object.
     *
     * @param parent GraphControllerProvider
     */
    public ExportGraphAction(final GraphControllerProvider parent) {
        this(parent, DEFAULT_ACTION_NAME);
    }

    /**
     * Creates a new QuickOpenFileAction object with specific name.
     *
     * @param parent GraphControllerProvider
     * @param name String
     */
    public ExportGraphAction(final GraphControllerProvider parent, final String name) {
        super(parent, name);
        setAccelerator(SWT.SHIFT | (SWT.getPlatform().equals("cocoa") ? SWT.MOD1 : SWT.CONTROL) | 'P');
    }

    /**
     * Method run.
     * @see org.eclipse.jface.action.IAction#run()
     */
    @Override
    public final void run() {
        final Shell parentShell = getGraphController().getWindow().getShell();
        final FileDialog dialog = new FileDialog(parentShell, SWT.SAVE);
        final ImageSaver imageSaver = new ImageSaver();
        dialog.setFilterExtensions(imageSaver.getSupportedExtensions());
        dialog.setText("Export graph as image");
        final String fileName = dialog.open();
        LOG.debug("Dialog returned " + fileName);
        if (fileName != null) {
            Image image = null;
            try {
                image = getGraphController().createImageForGraph();
                imageSaver.saveImage(image, fileName);
            } catch (final IllegalArgumentException e) {
                ExceptionDialog.openException(parentShell, "Cannot export image", e);

            } catch (final IOException e) {
                ExceptionDialog.openException(parentShell, "Cannot export image", e);
            } finally {
                if (image != null) {
                    image.dispose();
                }
            }
        }
    }
}
