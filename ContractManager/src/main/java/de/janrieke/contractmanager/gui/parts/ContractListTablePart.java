/*
 *   This file is part of ContractManager for Jameica.
 *   Copyright (C) 2010-2014  Jan Rieke
 *
 *   ContractManager is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   ContractManager is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.janrieke.contractmanager.gui.parts;

import java.rmi.RemoteException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableItem;

import de.janrieke.contractmanager.Settings;
import de.janrieke.contractmanager.rmi.Contract;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.parts.TableChangeListener;
import de.willuhn.jameica.gui.parts.TablePart;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

public class ContractListTablePart extends TablePart {

	private double sum;

	public ContractListTablePart(List<Contract> list, Action action) {
		super(list, action);
	    setRememberColWidths(true);
		this.addChangeListener(new TableChangeListener() {

			@Override
			public void itemChanged(Object object, String attribute, String newValue)
					throws ApplicationException {
				assert object instanceof Contract;
				try {
					((Contract)object).setDoNotRemind(!"\u2611".equals(newValue));
					((Contract)object).store();
				} catch (RemoteException e) {
					Logger.error("error while storing contract", e);
					GUI.getStatusBar().setErrorText(
							Settings.i18n().tr("Error while storing contract"));
				}
			}
		});
	}

	@Override
	protected String getControlValue(Control control) {
		if (control instanceof Button) {
			return ((Button) control).getSelection()?"\u2611":"\u2610";
		} else {
			return super.getControlValue(control);
		}
	}

	@Override
	protected Control getEditorControl(int row, TableItem item, String oldValue) {
		if (item.getData() instanceof Contract && row == 7) {
			Button newButton = new Button(item.getParent(), SWT.CHECK);
			newButton.setSelection("\u2611".equals(oldValue));
			newButton.setFocus();
		    return newButton;
		} else {
			return super.getEditorControl(row, item, oldValue);
		}
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	@Override
	protected String getSummary() {
		return Settings.i18n().tr("Total in this month") + ": " + Settings.DECIMALFORMAT.format(Math.round(sum*100d)/100d) + " EUR";
	}
}
