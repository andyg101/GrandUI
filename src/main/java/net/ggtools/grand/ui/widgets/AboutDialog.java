// $Id$
/*
 * ====================================================================
 * Copyright (c) 2002-2004, Christophe Labouisse All rights reserved.
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
package net.ggtools.grand.ui.widgets;

import java.io.IOException;

import net.ggtools.grand.Configuration;
import net.ggtools.grand.ui.Application;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Christophe Labouisse
 */
public class AboutDialog extends Dialog {
    /**
     * Field log.
     */
    private static final Log LOG = LogFactory.getLog(AboutDialog.class);

    /**
     * Field coreConfiguration.
     */
    private Configuration coreConfiguration;

    /**
     * @param parentShell Shell
     */
    public AboutDialog(final Shell parentShell) {
        super(parentShell);
        try {
            coreConfiguration = Configuration.getConfiguration();
        } catch (final IOException e) {
            LOG.error("Cannot load core configuration", e);
            coreConfiguration = null;
        }
    }

    /**
     * Method configureShell.
     * @param newShell Shell
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
    protected final void configureShell(final Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("About Grand");
    }

    /**
     * Method createButtonsForButtonBar.
     * @param parent Composite
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected final void createButtonsForButtonBar(final Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    }

    /**
     * Method createDialogArea.
     * @param parent Composite
     * @return Control
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected final Control createDialogArea(final Composite parent) {
        final Composite composite = (Composite) super.createDialogArea(parent);
        final RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
        rowLayout.justify = true;
        rowLayout.spacing = 10;
        composite.setLayout(rowLayout);
        final Label image = new Label(composite, SWT.NONE);
        image.setImage(Application.getInstance().getImage(Application.ABOUT_DIALOG_IMAGE));
        final Label message = new Label(composite, SWT.NONE);
        final StringBuilder messageBuffer = new StringBuilder(
                "Grand (C)2004-2019 Christophe Labouisse, distributed under BSD License\nUi: ");
        messageBuffer.append(Application.getInstance().getVersionString());
        if (coreConfiguration != null) {
            messageBuffer.append("\nCore: ").append(coreConfiguration.getVersionString());
        }
        message.setText(messageBuffer.toString());
        return composite;
    }
}
